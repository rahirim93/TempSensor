package com.example.example

import android.bluetooth.BluetoothSocket
import android.os.Handler
import android.util.Log
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.lang.StringBuilder

    class ConnectedThread (mmSocket: BluetoothSocket?, myHandler: Handler) : Thread() {
        private val mmInStream: InputStream? = mmSocket?.inputStream
        private val mmOutStream: OutputStream? = mmSocket?.outputStream
        private val mmBuffer: ByteArray = ByteArray(1024) // mmBuffer store for the stream
        private var handler = myHandler

        override fun run() {
            var numBytes: Int // bytes returned from read()
            var stringBuilder = StringBuilder()
            lateinit var stringTemperature: String
            lateinit var stringHumidity: String

            // Keep listening to the InputStream until an exception occurs.
            while (true) {
                // Read from the InputStream.
                try {
                    numBytes = mmInStream?.read(mmBuffer)!!
                    var stringIncome = String(mmBuffer,0, numBytes)
                    stringBuilder.append(stringIncome)


                    var endLineIndex1 = stringBuilder.indexOf("A")
                    var endLineIndex = stringBuilder.indexOf("\r\n")
                    if (endLineIndex > 0) {
                        stringTemperature = stringBuilder.substring(0, endLineIndex1)
                        stringHumidity = stringBuilder.substring(endLineIndex1 + 1, endLineIndex)
                        Log.d("myLog", "Temp $stringTemperature")
                        Log.d("myLog", "Hum $stringHumidity")
                        //Log.d("MyLog", "Message: $stringToPrint")
                        stringBuilder.delete(0, stringBuilder.length)

                        var msg = handler.obtainMessage(1, stringTemperature)
                        var msg2 = handler.obtainMessage(2, stringHumidity)

                        handler.sendMessage(msg)
                        handler.sendMessage(msg2)
                    }
                } catch (e: IOException) {
                    Log.d("MyLog", "Input stream was disconnected", e)
                    break
                }
            }
        }

        fun write(bytes: ByteArray) {
            try {
                mmOutStream?.write(bytes)
            } catch (e: Exception) {

            }
        }
    }
