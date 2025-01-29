# Kafka Exactly Once example

1. В корне проекта набрать ```docker-compose up```
2. В [Kafka UI](http://localhost:8090) создать топик ```persons```
3. Отправить POST запрос на ```localhost:8080/person``` с телом запроса: ```{"firstName": "Ivan","lastName": "Ivanovich"}```