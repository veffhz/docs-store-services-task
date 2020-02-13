# Document store web app with Spring 
#### Allows safely upload, store & view files 

### based on
Java, Gradle, Apache Derby, Bouncy Castle.

#### run
* `./docs-server/gradlew -p docs-server bootRun`
* `./docs-client/gradlew -p docs-client bootRun`


Задание:


Сделать maven или gradle проект сервера.
Сервер должен быть с авторизацией по PKI (RSA) сертификату. Выпустить самоподписаный серт можно утилитой keytool из jdk.
Сервер должен иметь Restful API. 
Запросы: 
- GET просмотр документа по ID,
- GET получение списка ID всех документов в виде json,
- POST отправка post запроса с документом в PKCS7 конверте (выпустить также самоподписаный RSA).  На сервере нужно проверять подпись и сохранять в БД, если подпись валидна.
Проект сделать на Spring Boot.
БД Embedded Derby. Использовать Spring Data JPA

 
Сделать maven или gradle проект клиента.
Клиент должен быть с авторизацией по логину и паролю (хранить в бд). Пароль шифровать конечно же (BCrypt).
Клиент должен по SSL(TLS) конектиться к серверу.
Клиент должен иметь возможность:
- выводить список документов,
- открывать конкретный документ при выборе его из списка,
- отправлять документ в формате PKCS7 на сервер (оптравка через GUI, путем выбора файла на диске).
Проект сделать на Spring Boot.
БД Embedded Derby. Использовать Spring Data JPA

Для gui использовать thymeleaf + material.
Исходники проектов должны быть на GitHub.
