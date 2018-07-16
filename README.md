# Mls Backend Test

На разработку было затрачено ~10 часов.

##Перед сборкой

В файле logback.xml укажите шаблон пути к файлу логов в параметре fileNamePattern.
 
Укажите имя properties-файла с настройками подключения к базе данных
в параметре контекста database.properties в web.xml. Пример файла:
```
jdbc.driver=org.postgresql.Driver
jdbc.url=jdbc:postgresql://localhost:5432/mlsbackendtest
jdbc.user=postgres
jdbc.password=root
```
Создайте базу данных с таблицей parts. Скрипт создания таблицы в файле parts.sql.
Заполните таблицу тестовыми данными, скрипт импорта в файле parts.import.sql.

## Сборка

Для создания war-файла выполните команду 
```mvn clean compile war:war``` в корневом каталоге проекта.

## Деплой

Выполните деплой файла mlsbackendtest-1.0-SNAPSHOT.war
с помощью Tomcat Manager App по ссылке http://localhost:8080/manager/html
