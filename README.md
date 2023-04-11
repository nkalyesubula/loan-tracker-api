# loan-tracker-api

REST API for tracking a customer loan status.

Created using Spring Boot, PostgreSQL as the relational database and JdbcTemplate to interact with it.
JSON Web Token (JWT) is used for authentication. Some endpoints require that user must be logged-in to access them.

## Setup and Installation

1. **Clone the repo from GitHub**
   ```sh
   git clone https://github.com/nkalyesubula/loan-tracker-api.git
   cd loan-tracker-api
   ```
2. **Spin-up PostgreSQL database instance**

   You can use either of the below 2 options:
    - one way is to download from [here](https://www.postgresql.org/download) and install locally on the machine
    - another option is by running a postgres docker container:
      ```sh
      docker container run --name postgresdb -e POSTGRES_PASSWORD=admin -d -p 5432:5432 postgres
      ```
3. **Create database objects**

   In the root application directory (loan-tracker-api), SQL script file (loantracker_db.sql) is present for creating all database objects
    - if using docker (else skip this step), first copy this file to the running container using below command and then exec into the running container:
      ```
      docker container cp loantracker_db.sql postgresdb:/
      docker container exec -it postgresdb bash
      ```
    - run the script using psql client:
      ```
      psql -U postgres --file loantracker_db.sql
      ```
4. **(Optional) Update database configurations in application.properties**

   If your database is hosted at some cloud platform or if you have modified the SQL script file with some different username and password, update the src/main/resources/application.properties file accordingly:
   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/loantrackerdb
   spring.datasource.username=loantracker
   spring.datasource.password=password
   ```
5. **Run the spring boot application**
   ```sh
   ./mvnw spring-boot:run
   ```
   this runs at port 8080 and hence all endpoints can be accessed starting from http://localhost:8080


7. **API REQUESTS**
   1. *To retrieve information about a loan, use the endpoint below*
   ```
   GET http://localhost:8080/api/v1/loans/{accountNumber}
   ```
   But for this request to be successful we need to have some data about the customer
   
   2. *Create user* 
   ```
   POST http://localhost:8080/api/v1/users/register
   {
   "firstName":"John",
   "lastName": "Doe",
   "email": "john3@testmail.com",
   "password": "test123",
   "isAdmin": false
   }
   ```
   3. *Sign in User*
   ```
   POST http://localhost:8080/api/v1/users/login
   {
   "email": "john3@testmail.com",
   "password": "test123"
   }
   ```
   The following end-points can only be accessed by authenticated users. Add a header in POSTMAN with the details below
   ```
   Key: Authorization and Value: Bearer {token}
   ```
   4. *Create an account*
   ```
   POST  http://localhost:8080/api/v1/accounts
   {
   "opening_balance": 10000
   }
   ```
   5. *Get Account details*
   ```
   GET http://localhost:8080/api/v1/accounts/{accountNumber}
   ```
   6. *Update account details*
   ```
   { 
    "openingBalance": 50000
   }
   ```
   7. *Create a loan*
   ```
   POST http://localhost:8080/api/v1/loans
   {
   "loanAmount":1200000
   }
   ```
   Now, we can retrieve information about a loan, by using the endpoint below
   ```
   GET http://localhost:8080/api/v1/loans/{accountNumber}
   ```
   8. *Make a loan payment*
   ```
   POST http://localhost:8080/api/v1/loans/{loanId}/payments
   {
   "amount": 55000,
   "note": "Loan Deposit for Feb"
   }
   ```
   
   
   
      
 