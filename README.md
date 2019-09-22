# SpringAppTest

# How to use this code?

1. Fork this repository and clone it
  
  ```
  $ git clone https://github.com/Klamanat59/SpringAppTest.git
  ```
  
2. Navigate into the folder  

  ```
  $ cd SpringAppTest
  ```

4. Install dependencies

  ```
  $ mvn install
  ```

5. Run the project

  ```
  $ mvn spring-boot:run
  ```

## Application config DB
```
application.properties

spring.datasource.username=sa // username
spring.datasource.password=***** // password
```

## Technology stack
- Spring Boot
- Spring Data
- Spring Security
- Sql Server / H2
- Maven
- jwt

## API Documentation

- [Signup](#signup)
    - [Success](#signup-success)
    - [Username already exists](#signup-username-already-exists)
    - [Salary invalid](#signup-salary-invalid)
- [Signin](#signin)
    - [Success](#signin-success)
    - [Unauthorized](#signin-unauthorized)
- [User Profile](#user-profile)
    - [Success](#current-user-success)
    - [Unauthorized](#user-profile---unauthorized)

## Signup

```
POST: http://localhost:8080/signup
```

Header
```
Content-Type: application/json
```

Body
``` json
{
	"username" : "username",
	"password" : "1234",
	"address" : "address",
	"phone" : "0123456789",
	"salary" : "15000"
}
```

## Signup success 
Status: 200

Body (Created user id)
```
{
  "id": 1
}
```

## Signup username already exists
Status: 500

Body (Created user id)
```
{
    "timestamp": "2019-09-22T14:26:10.294+0000",
    "status": 500,
    "error": "Internal Server Error",
    "message": "Username is already taken!",
    "path": "/signup"
}
```

## Signup salary invalid
Status: 500

Body
```
{
    "timestamp": "2019-09-22T14:31:26.204+0000",
    "status": 500,
    "error": "Internal Server Error",
    "message": "Invalid user salary.",
    "path": "/signup"
}
```

***

## Signin
```
POST: http://localhost:8080/signin
```

Header
```
Content-Type: application/json
```

Body
``` json
{
	"username": "username",
	"password": "password"
}
```

## Signin success 
Status: 200

Body
```
{
  "token": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNTQxNTA2MjA4LCJleHAiOjE1NDIxMTEwMDh9.xRU2vmXUvtHmrL_BDKm-rzTKd0Sv8BtPR3AmOO0ZgLnSXIT0EeDh1cN7lvrxM-H2fNuO4vJMJdOXV8By8E4BkQ",
  "tokenType": "Bearer"
}
```

## Signin unauthorized 
Status: 401

Body
```
{
  "timestamp": "2018-11-11T17:37:12.573+0000",
  "status": 401,
  "error": "Unauthorized",
  "message": "Sorry, You're not authorized to access this resource.",
  "path": "/api/auth/signin"
}
```
***

## User Profile
Logged in user credentials

```
GET: http://localhost:8080/getUser
```

Header
```
Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNTQxNTA2MjA4LCJleHAiOjE1NDIxMTEwMDh9.xRU2vmXUvtHmrL_BDKm-rzTKd0Sv8BtPR3AmOO0ZgLnSXIT0EeDh1cN7lvrxM-H2fNuO4vJMJdOXV8By8E4BkQ
```

## Current user success 
Status: 200

Body
```
{
    "id": 1,
    "username": "username",
    "password": "$2a$10$GILAswn76c31N/7Ss9zYN.3yyCvOpa36rTbPw1CpL/JkLFkqOjuQ.",
    "address": "address",
    "phone": "0123456789",
    "referenceCode": "201909226789",
    "type": "Silver"
}
```


## User Profile - unauthorized 
Status: 401

Body
```
{
    "timestamp": "2019-09-22T14:34:48.035+0000",
    "status": 401,
    "error": "Unauthorized",
    "message": "Sorry, You're not authorized to access this resource.",
    "path": "/getUser"
}
```
