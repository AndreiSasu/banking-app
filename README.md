# Banking App

## Backend 
#### Spring Boot: REST, JPA (H2 db), Username & Password authentication + JWT  

#### Prerequisites: Java 11 and Maven installed

```
cd backend
mvn clean install
mvn spring-boot:run
```

Login as default username: `user` with password: `password123`

```
curl -X POST -H "Content-Type: application/json" -d "{\"userName\": \"user\",\"password\": \"password123\"}" http://localhost:8080/api/v1/login 
```
You should get a similar response: 

`{"id":1,"username":"user","roles":[],"accessToken":"eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ1c2VyIiwiaWF0IjoxNTkzMjYzNzI3LCJleHAiOjE1OTMzNTAxMjd9.I3hqOMgCdbgvdCFKK-j5vEjGjFIiuSqLEgwWHCw5dp8pieInD5iUlvCom5v2O2JFg3wyfePsZ0J1xROL6lrkVQ","tokenType":"Bearer"}`

Use the `accessToken` for every subsequent request: 

```
curl -X POST -H "Content-Type: application/json" -H "Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ1c2VyIiwiaWF0IjoxNTkzMjYzNzI3LCJleHAiOjE1OTMzNTAxMjd9.I3hqOMgCdbgvdCFKK-j5vEjGjFIiuSqLEgwWHCw5dp8pieInD5iUlvCom5v2O2JFg3wyfePsZ0J1xROL6lrkVQ" -d "{\"accountType\": \"SAVINGS\",\"currency\": \"USD\"}" http://localhost:8080/api/v1/accounts


Successful Response: 

{"iban":"IS817587120112338450608879","currency":"USD","accountType":"SAVINGS"}


User already has savings account response: 

{"message":"createAccount.createAccountRequest: Only one savings account is allowed per user.","timestamp":1593264430,"statusCode":400,"statusReason":"Bad Request"}

Invalid working hours response: 

{"message":"createAccount.createAccountRequest: Invalid working hours.","timestamp":1593264090,"statusCode":400,"statusReason":"Bad Request"}
```


## Frontend
#### Angular 9 + Bootstrap
#### Prerequisites: Node and Npm installed

```
cd frontend
npm install 
npm start
```

Open http://localhost:4200

#### Login page (user / password123): 
<img src="img/login.png" width=480 height=200></img>

####  Successful account creation:
<img src="img/successful.png" width=480 height=200></img>

#### Error message example:
<img src="img/invalid.png" width=480 height=200></img>

