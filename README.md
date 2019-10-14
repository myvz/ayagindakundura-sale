# Ayaginda Kundura Sale Service

### Running with spring-boot
> $ ./mvnw spring-boot:run -Dspring-boot.run.profiles=dev

### Running Standalone
> $ ./mvnw package && java -jar -Dspring.profiles.active=dev target/sale-0.0.1-SNAPSHOT.jar

### Building Docker Image
> $ ./mvnw install dockerfile:build

### Running with Dockerfile
> $ docker run -e "SPRING_PROFILES_ACTIVE=dev" -p 8080:8080 -t ayagindakundura/sale

### Manipulate Data with H2 Console
> url       : http://localhost:8080/h2    
> jdbc url  : jdbc:h2:mem:testdb  
> username  : sa  
> password  : 

## Built With
* [H2](https://www.h2database.com) - Embedded Database
* [Spring-Boot](https://projects.spring.io/spring-boot) - IOC Container
* [Maven](https://maven.apache.org/) - Dependency Management

## Authors
Metin YAVUZ
