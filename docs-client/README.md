# Document store client

* Spring mvc, data, security
* Bouncy Castle
* Apache Derby (Embedded)

#### requirements:
java 8-11, gradle.

#### build
`./gradlew clean build`

#### run
`./gradlew bootRun` or `java -jar build/libs/docs-client.jar`

#### documentation

Domain model:
* DocumentDto
* User

Pages:
* /login - Default login page (`user`, `password`)
* /index - List documents ids, service contains several test documents
* /details/{id} - Document detail
