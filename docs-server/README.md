# Document store service

* Spring mvc, data, security (x509 auth)
* Bouncy Castle
* Apache Derby (Embedded)

#### requirements:
java 8-11, gradle.

#### build
`./gradlew clean build`

#### run
`./gradlew bootRun` or `java -jar build/libs/docs-server.jar`

#### documentation

Domain model:
* Document

Rest apis (x509 auth):
* /api/document GET (DB contains several test documents)
* /api/document/{id} GET
* /api/document/{id}/content GET
* /api/document POST
