![Java CI with Maven](https://github.com/Brest-Java-Course-2020/dklimuk-restaurant/workflows/Java%20CI%20with%20Maven/badge.svg)
[![Build Status](https://travis-ci.org/Brest-Java-Course-2020/dklimuk-restaurant.svg?branch=master)](https://travis-ci.org/Brest-Java-Course-2020/dklimuk-restaurant)


# dklimuk-restaurant
Restaurant demo application


## How build
Setup java 8 and Maven, see [enviroment_setup.md](enviroment_setup.md) 
 
## Build project 
Goto Project folder and execute

    mvn clean install
    

## Report 

Report compilation:
````
$ mvn site
$ mvn site:stage
````
Open report:
````
/Restaurant/target/site/apidocs/index.html
````


## Start application on Tomcat server

First, compile the project:

    $ mvn clean install
    
Then, copy war-files from directory: 

    /dklimuk-restaurant/restaurant-rest-app/target/restaurant-rest.war
    /dklimuk-restaurant/restaurant-web-app/target/restaurant-web.war

and past this files to next directory:

    apache-tomcat/webapps/
    
To open the application, go to the following address

    /http://localhost:8080/restaurant-web/
    /http://localhost:8080/restaurant-rest/

    
For project management open:

    http://localhost:8080/manager/html

## Start application on Jetty server

#### To run Rest-app, use the commands below:

    cd restaurant-rest-app
    mvn -pl restaurant-rest-app/ jetty:run -P jetty

#### For Web-app:
 
    cd restaurant-web-app
    mvn -pl restaurant-web-app/ jetty:run -P jetty
    

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



### Position

#### findAll

```
curl --request GET 'http://localhost:8088/positions' | json_pp
```


#### findById

```
curl --request GET 'http://localhost:8088/positions/1' | json_pp
```

#### findPositionByOrderId

````
curl --request GET 'http://localhost:8088/positions/orderId/1' | json_pp

````


#### create

```
curl --request POST 'http://localhost:8088/positions' \
--header 'Accept: application/json' \
--header 'Content-Type: application/json' \
--data-raw '{
    "positionOrderId": 1,
    "positionName": "New Position",
    "positionPrice": 100,
    "positionCount": 1
}'
```

#### update

```
curl --request PUT 'http://localhost:8088/positions' \
--header 'Content-Type: application/json' \
--data-raw '{
    "positionId": 8,
    "positionOrderId": 1,
    "positionName": "Update position",
    "positionPrice": 100,
    "positionCount": 1
}'
```

#### delete

```
curl --request DELETE 'http://localhost:8088/positions/1'
```
