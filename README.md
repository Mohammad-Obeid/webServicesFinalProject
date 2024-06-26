# Hotel Management System

## Project Overview
The Hotel Management System is designed to streamline operations for both hotel employees and guests. It offers functionalities for customers such as searching available rooms, reservations, check-ins and check-outs, and invoice generation. Employees can manage room availability, maintain customer profiles, and oversee housekeeping schedules. This project implements a set of RESTful APIs based on Spring Boot to enable the development of the required system frontend (web and mobile interfaces).

## Project Resources

### Customer Management
- Register: Allows new users to create an account.
- Login: Authenticates users and provides a token for access.
- Profile Management: Enables users to view, update, and change their passwords.

### Employee Management
- Admin functionalities to manage hotel employees and staff details.

### Search Functionality
- Reservations: Search by customer name or ID and date.
- Customer Info: Retrieve customer details.
- Available Rooms: Search for rooms based on availability, with details such as price, facilities, capacity, size, and features.

### Reservation Management
- Booking: Customers can book room(s).
- Modification: Customers can modify their reservations.
- Cancellation: Reservation cancellation requires admin approval.

### Room Management
- Admin functionalities to manage room types, availability, and status.

### Check-In/Check-Out Process
- Manage customer arrivals and departures by admin users.

### Housekeeping Management
- Schedule and track housekeeping tasks and employees.

### Billing Management
- Generate and manage invoices for customer reservations.

### Role-Based Access Control
- Different functionalities are available based on user roles (admin, customer).

## ER Diagram


![ERD_HotelMS (2)](https://github.com/Mohammad-Obeid/webServicesFinalProject/assets/147950746/5b5537a4-2731-4a25-9c75-03550c5708e8)


## How to Build, Package, and Run the Application
- ### To build the application, we should run the following commands on terminal:
    - ./mvnw clean 
    - ./mvnw install
- ### To package the application into a JAR file, we should run the following command on terminal:
   - ./mvnw package
- ### To run the application using the generated JAR file, we should run the following command on terminal:
   - java -jar target/hotel-management-system-0.0.1-SNAPSHOT.jar

- ### To build and run the Docker image:
   - Build the Docker image: docker build -t hotel-management-system .
   - Run the Docker container: docker run -p 8080:8080 hotel-management-system
   - ### The Docker image for this application can be found on DockerHub at the following link: https://hub.docker.com/repository/docker/salahm2002/project2-hotel-management-system-image/general

## What We Have Learned
- Plan, design, develop and document an end-to-end backend application with exposing all resources through RESTful APIs with adopting the microservice architecture. 
- Designing and implementing RESTful APIs using Spring Boot.
- How to implement the entity relations in the DAO (Data Access Object) layer, so we can now implementing and managing relationships between entities (one-to-one, one-to-many, many-to-many) in the DAO layer. And we using ORM tools like Hibernate or JPA to map database tables to Java objects.
- Applying best practices in coding, and now we can implementing security best practices, performance optimizations, and ensuring code quality.
- Documenting APIs using OAS standards to ensure they are well-defined and easily understandable by other developers.
- Using Swaager UI. To see that go in ---> http://localhost:8080/swagger-ui/index.html
- API security with JWT.
  - JWT Authentication and Authorization:
    - Securing APIs using JSON Web Tokens (JWT) for authentication and authorization.
    - Implementing role-based access control (RBAC) to manage permissions for different user roles (customer and admin).
    - Developing secure signup and authentication endpoints.
- Versioning a RESTful web API.
  - API Versioning:
    - Understanding different API versioning strategies (URL versioning, query parameter versioning, and header versioning).
    - Implementing and managing multiple versions of APIs to ensure backward compatibility and smooth upgrades.
- Database Initialization and Data Population.
  - Using Spring Boot frameworks to initialize the database schema, populate initial data, and generate sample data automatically.
  - Ensuring the database is ready for use without manual intervention.
- Data Validation and Exception Handling.
  - Implementing robust data validation to ensure the integrity and correctness of the data being processed.
  - Using validation frameworks and libraries to streamline validation logic.
  - Implementing comprehensive exception handling to manage and respond to errors gracefully.
  - Using custom exceptions, global exception handlers, and proper logging mechanisms.   
- Using Docker to containerize the application for easy deployment and scalability.
- Using Postman to test APIs.
- Version Control with Git: Understanding the fundamental concepts of version control systems and how to effectively use Git to track changes in my project.
- Proper Git Commands: Using Git commands effectively to stage changes, commit them, push to remote repositories, pull updates, and manage branches.
  
