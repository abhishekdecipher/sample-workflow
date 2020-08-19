## Spring Boot Workflow Application

##Description

This app is used to show the dummy data templates on ui as notifications on given scheduled time.
Notification will be pushed to UI till the user enter some input and send it, then
the notifications will be aborted.



## Requirements

1. Java - 1.8.x

2. Maven - 3.x.x

## Steps to Setup

**1. Clone the application**


**2. Build and run the app using maven**

```bash
cd sample-workflow
mvn clean package
java -jar target/dz-0.0.1-SNAPSHOT.jar
```
**3. Got to the browser**

```$xslt
Hit the url -> http://localhost:8888
```

Alternatively, you can run the app directly without packaging it like so -

```bash
mvn spring-boot:run
```
