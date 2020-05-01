# dklimuk-restaurant
Restaurant demo application


## How build
Setup java 8 and Maven, see [enviroment_setup.md](enviroment_setup.md) 
 
## Build project 
Goto Project folder and execute

    mvn clean install
    

## Rest server

### Start Rest using Maven Jetty plugin 
    
To start Rest using Maven Jetty plugin use:


    cd restaurant-rest-app
    mvn jetty:run


## Available REST endpoints    

### version

    curl --request GET 'http://localhost:8088/version'


### orders

#### findAll

```
curl --request GET 'http://localhost:8088/orders' | json_pp
```

#### findById

```
curl --request GET 'http://localhost:8088/orders/1' | json_pp
```
#### create

```
curl --request POST 'http://localhost:8088/orders' \
--header 'Accept: application/json' \
--header 'Content-Type: application/json' \
--data-raw '{
    "orderName": "New Order",
    "orderDate": "2020-05-01"
}'
```

#### update

```
curl --request PUT 'http://localhost:8088/orders' \
--header 'Content-Type: application/json' \
--data-raw '{
    "orderId": 1,
    "orderName": "Update Table #1",
    "orderDate": "2020-05-01"
}'
```

#### delete

```
curl --request DELETE 'http://localhost:8088/orders/1'
```


### Items

#### findAll

```
curl --request GET 'http://localhost:8088/items' | json_pp
```

#### findById

```
curl --request GET 'http://localhost:8088/items/1' | json_pp
```
#### create

```
curl --request POST 'http://localhost:8088/items' \
--header 'Accept: application/json' \
--header 'Content-Type: application/json' \
--data-raw '{
    "itemName": "New Item",
    "itemPrice": 100
}'
```

#### update

```
curl --request PUT 'http://localhost:8088/items' \
--header 'Content-Type: application/json' \
--data-raw '{
    "itemId": 1,
    "itemName": "Update Item",
    "itemPrice": 50
}'
```

#### delete

```
curl --request DELETE 'http://localhost:8088/items/1'
```

