#include <SoftwareSerial.h>

SoftwareSerial mySerial(2, 3); // указываем пины rx и tx соответственно
//RX на модуле блютуз на пин 3, ТХ на пин 2

#include "DHT.h"
#define DHTPIN 7 // Тот самый номер пина, о котором упоминалось выше
// Одна из следующих строк закоментирована. Снимите комментарий, если подключаете датчик DHT11 к arduino
//DHT dht(DHTPIN, DHT22); //Инициация датчика
DHT dht(DHTPIN, DHT11);
void setup() {
  pinMode(2, INPUT);
  pinMode(3, OUTPUT);
  mySerial.begin(9600);
  Serial.begin(9600);
  dht.begin();
}
void loop() {
  delay(2000); // 2 секунды задержки
  float h = dht.readHumidity(); //Измеряем влажность
  float t = dht.readTemperature(); //Измеряем температуру
  if (isnan(h) || isnan(t)) {  // Проверка. Если не удается считать показания, выводится «Ошибка считывания», и программа завершает работу
    Serial.println("Ошибка считывания");
    return;
  }
  //Serial.print("Влажность: ");
  Serial.print(h);
  Serial.print(" %\t");
  Serial.print("Температура: ");
  Serial.print(t);
  Serial.println(" *C "); //Вывод показателей на экран
  mySerial.print(t);
  mySerial.print("A");
  mySerial.println(h);
}
