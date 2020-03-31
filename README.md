# Trains.project

Запуск: gradlew run -q

Сборка и запуск тестов: gradlew build

Каждая строка конфигурационного файла имеет следующий формат:
______________________________________________
train_number arrival_time time_to_unload price
______________________________________________
train_number -- номер поезда, целое число;

arrival_time -- время прибытия поезда с номером train_number в формате hh:mm;

time_to_unload -- время разгузки поезда train_number, целое число;

price -- стоимость разгузки поезда train_number, целое число;
