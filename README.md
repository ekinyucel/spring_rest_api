# Spring boot REST API [![Build Status](http://139.59.147.2:8080/job/spring_rest_api/job/master/badge/icon)](http://139.59.147.2:8080/job/spring_rest_api/job/master/)

Concurrent REST API example using spring boot powered by MongoDB. 
For learning purposes proper concurrency is being implemented.

```
## How to build
$ docker build -p 8090:8090 -t spring_rest .

## How to run
$ docker run -t spring_rest
```



## Functionalities
-   Basic CRUD operations which are being consumed by an Angular application
-   Retrieval of flight details and member enrollment
-   Authentication backed by JWT (not yet fully implemented)

## TO-DO list
-   Storing invalid tokens using redis until their expiration dates in order to avoid illegal access.
-   Full API testing results will be provided once the application will have been completed
-   Performance tests will be planned to do using JMeter
-   Building service, database and UI using Docker from scratch
-   Automatic deployment will be handled by Jenkins

