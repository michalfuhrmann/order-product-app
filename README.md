# General info
Order Product app that allows creating basic order and products.
Technologies: SpringBoot, Docker
Persistence layer: MongoDB

## Running

To run the app 
```
docker-compose up -d && mvn spring-boot:run
```

## Test

Tests run on embedded mongoDB
To run tests:
```
mvn clean verify
```

## Info
App by default starts on port 8080
Url: http://localhost:8080/swagger-ui.html
