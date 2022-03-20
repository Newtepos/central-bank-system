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
- Create QR Code from CashaPackage (*Incomplete)

### Branch Bank System Function
- Create CashPackage for sending to Central Bank
- Read Cash inside CashPackage that genrated from Central or Branch Bank (Microservice)
- Create QR Code from CashaPackage (*Incomplete)
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
### CBTS Api List
| Method |            Url            |                   Description                  | Sample Valid Request Body | Samplat Valid Response Body |
|:------:|:-------------------------:|:----------------------------------------------:|:-------------------------:|:---------------------------:|
|  POST  |           /bank           |               Create Branch Bank               |            JSON           |             JSON            |
|   GET  |           /bank           |               Get All Bank Detail              |            JSON           |             JSON            |
|   GET  |         /bank/{id}        |              Get Bank Detail By Id             |            JSON           |             JSON            |
|  POST  |       /cbts-package       | create cash-package for sending to Branch Bank |            JSON           |             JSON            |
|   PUT  | /cbts-package/{packageId} |   update cbts package status for driver scan   |            JSON           |             JSON            |
|   Get  | /cbts-package/{packageId} |          peek cash inside cbts package         |            JSON           |             JSON            |
|   GET  |       /cbts-package       |          Get all cbts package in CBTS          |            JSON           |             JSON            |
|   GET  |        /bbs-package       |           Get all bbs package in CBTS          |            JSON           |             JSON            |
|   PUT  |  /bbs-package/{packageId} |    update bbs package status for driver scan   |            JSON           |             JSON            |
|   Get  |  /bbs-package/{packageId} |          peek cash inside bbs package          |            JSON           |             JSON            |
|  POST  |        /money-truck       |         create new money truck to CBTS         |            JSON           |             JSON            |
|   PUT  |        /money-truck       |           update money truck location          |            JSON           |             JSON            |
|   GET  |    /money-truck/current   |       Get All moneytruck recent location       |            JSON           |             JSON            |
|   GET  | /money-truck/{id}/current |      Get Moneytruck recent location by ID      |            JSON           |             JSON            |

### BBS Api List
| Method |            Url            |                   Description                   | Sample Valid Request Body | Samplat Valid Response Body |
|:------:|:-------------------------:|:-----------------------------------------------:|:-------------------------:|:---------------------------:|
|  POST  |        /bbs-package       | create cash-package for sending to Central Bank |            JSON           |             JSON            |
|   GET  |  /bbs-package/{packageId} |           peek cash inside bbs package          |            JSON           |             JSON            |
|   Get  | /cbts-package/{packageId} |          peek cash inside cbts package          |            JSON           |             JSON            |

<br/><br/>
### CBTS API Detail
#### 1. Create Branch Bank
`POST /bank/`

Body
```JSON
    {
        "bankName": "Bank",
        "latitude": 12.0,
        "longitude": 12.0,
        "balance": [
            {
                "amount": 20000000.00,
                "currency": "USD"
            },
            {
                "amount": 40000000000.00,
                "currency": "THB"
            }
        ],
        "url": "http://localhost:8081"
    }
```
Response
```JSON
    {
        "bankName": "Bank",
        "latitude": 12.0,
        "longitude": 12.0,
        "balance": [
            {
                "amount": 20000000.00,
                "currency": "USD"
            },
            {
                "amount": 40000000000.00,
                "currency": "THB"
            }
        ],
        "url": "http://localhost:8081"
    }
```
#### 2. Get All Bank Detail
`GET /bank/`

Body
```JSON
```
Response
```JSON
[
    {
        "id": 1,
        "bankName": "CentralBank",
        "latitude": 13.9106,
        "longitude": 100.5515,
        "balance": [
            {
                "amount": 2000000000.00,
                "currency": "THB"
            },
            {
                "amount": 500000000.00,
                "currency": "USD"
            }
        ],
        "url": null
    },
    {
        "id": 7,
        "bankName": "Bank",
        "latitude": 12.0,
        "longitude": 12.0,
        "balance": [
            {
                "amount": 20000000.00,
                "currency": "USD"
            },
            {
                "amount": 40000000000.00,
                "currency": "THB"
            }
        ],
        "url": null
    }
]
```




