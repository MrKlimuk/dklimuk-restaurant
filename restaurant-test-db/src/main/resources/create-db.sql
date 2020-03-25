DROP TABLE IF EXISTS ordertable;
CREATE TABLE ordertable (
  order_id INT NOT NULL AUTO_INCREMENT,
  order_name VARCHAR(20) NOT NULL UNIQUE,
  order_price INT,
  PRIMARY KEY (order_id)
);

DROP TABLE IF EXISTS item;
CREATE TABLE item(
    item_id INT NOT NULL AUTO_INCREMENT,
    item_name VARCHAR(20) NOT NULL UNIQUE,
    item_price INT NOT NULL,
    PRIMARY KEY (item_id)
);


