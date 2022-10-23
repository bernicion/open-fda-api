# openFDA-API
REST API sample project to manage Drug Applications(OpenAPI 3.0, Spring 2.7.3, MySQL 8, Java 17)

### Requirements to run the application on local environment
1. **Start the docker container that with store the MySQL database**
    * From project root directory, locate file **docker-compose.yml**
    * To start application and db server need to run **docker compose up**

2.**Check if the application is up**
   Access **http://localhost:8040/actuator/health** and check if status is UP

3.**Access the Swagger UI to study the REST API endpoint**
   Access **http://localhost:8040/swagger-ui/index.html** and check end-points definition
