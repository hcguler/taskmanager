## *BACKEND*

### PRE REQUIREMENT
- openjdk 11.0.8 2020-07-14
- Postgresql latest
- Docker version 20.10.2, build 2291f61
- Gradle 6.5.1

## BUILD AND DEPLOY

- Gradle install dependencies
- gradle build
- gradle bootJar
- change directory ./docker
- docker-compose up -d //for postgresql
- java -jar ./build/libs/simpletaskmanager-0.0.1-SNAPSHOT.jar

Application default port 8081

Browse http://localhost:8081/swagger-ui/index.html

Run test suite for all business logic tests

Postman collection directory ./postman-collection

## *UI*

## Project setup
```
npm install
```

### Compiles and hot-reloads for development
```
npm run serve
```

### Compiles and minifies for production
```
npm run build
```

If you want to change Rest Api url or port look this -> ./src/model/ServiceUrlConstants.ts

If you use intellij idea you can copy and paste your .idea/workspace.xml -> idea-config/workspace.xml
