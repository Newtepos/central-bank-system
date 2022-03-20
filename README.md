# Central Bank System
## Project Short Description
This Project used Java Spring for create basic Bank System. it consist of 2 application
1. Central Bank Tracking System (CBTS)
2. Branch Bank System (BBS)

### Central Bank Tracking System Function
- Create Branch Bank
- Display All Bank in system
- MoneyTruck Tracking
- Display Recent Location of MoneyTruck
- Create CashPackage for sending to Branch Bank
- Update Status CashPackage from Central to Branch Bank and vice versa
- Read Cash inside CashPackage that genrated from Central or Branch Bank (Microservice)

### Branch Bank System Function
- Create CashPackage for sending to Central Bank
- Read Cash inside CashPackage that genrated from Central or Branch Bank (Microservice)
<br/><br/>

## InitialSetup
- Terminate port at 8080, 8081 for run Central-Bank-System Application
<br/><br/>


## Run command
### CBTS App
```
$cd cbts
$./mvnw clean package
$java -jar target/xxxx.java
```

### BBS App
```
$cd bbs
$./mvnw clean package
$java -jar target/xxxx.java
```
<br/><br/>

## API List
| Method |                       Url                      |                       Description                       | Sample Valid Request Body | Samplat Valid Response Body |
|:------:|:----------------------------------------------:|:-------------------------------------------------------:|:-------------------------:|:---------------------------:|
|  POST  |                     /login                     |                        login user                       |            JSON           |             JSON            |
|   GET  |               /product?p=product               |                Search products in market                |            JSON           |             JSON            |
|   GET  |                  /product/{id}                 |                   Search product by id                  |            JSON           |             JSON            |
|  POST  | /user/{userId}/cart/add/{productId}/{quantity} | Add Product to User cart by UserId, ProductId, quantity |            JSON           |             JSON            |
|   GET  |               /user/{userId}/cart              |     Reterive Product Items in User's Cart by UserId     |            JSON           |             JSON            |
|   GET  |          /user/{userId}/cart/checkout          |              Checkout Items in User's Cart              |            JSON           |             JSON            |

### 1. Login
`POST /login/`

Body
```JSON
{
    "username": "johndoe",
    "password" : "1234"
}
```
Response
```JSON
{
    "message": "login success"
}
```  




