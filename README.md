# Transport Server
Transport system server based on the REST API standard.

### About
It is a server realized mostly using Java and the Spring framework. It was created for the purpose of the thesis and to learn a new technology.</br>
The server handles requests from the [desktop application](https://github.com/M4zek/TransportDesktopApp) that implements the management of a transportation company. 
It uses Hibernate to communicate with the MySQL database server.</br> 

#### At the moment the server has implemented:
- Logging support 
- Handling management of employees, vehicles
- Account data management
- Encrypted connection (SSL handshake)
- User Authentication (JSON Web Token)

#### To be implemented in the future:
- Support for chat with employees
- Handling order and driver management

## Technologies used
- Java 
- Spring
- Hibernate 

## Instalation
1. Download the repository and import the database, which is located in the /src/main/resources/db to the MySQL database server.
2. Create a jar file
3. Run with the command java -jar TransportServer.jar


## Authors
- [@M4zek](https://github.com/M4zek)
