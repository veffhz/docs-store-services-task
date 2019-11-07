Сделать maven или gradle проект сервера.

Сервер должен быть с авторизацией по PKI (RSA) сертификату. Выпустить самоподписаный серт можно утилитой keytool из jdk.
Сервер должен иметь Restful API. 

Запросы: 
- GET просмотр документа по ID,
- GET получение списка ID всех документов в виде json,
- POST отправка post запроса с документом в PKCS7 конверте (выпустить также самоподписаный RSA).  

На сервере нужно проверять подпись и сохранять в БД, если подпись валидна.

Проект сделать на Spring Boot.

БД Embedded Derby. Использовать Spring Data JPA