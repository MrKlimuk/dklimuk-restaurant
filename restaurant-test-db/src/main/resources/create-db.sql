DROP TABLE IF EXISTS ordertable;
CREATE TABLE ordertable (
  order_id INT NOT NULL AUTO_INCREMENT,
  order_name VARCHAR(20) NOT NULL UNIQUE,
  order_price INT DEFAULT 0,
  PRIMARY KEY (order_id)
);

DROP TABLE IF EXISTS item;
CREATE TABLE item(
    item_id INT NOT NULL AUTO_INCREMENT,
    item_name VARCHAR(20) NOT NULL UNIQUE,
    item_price INT NOT NULL,
    PRIMARY KEY (item_id)
);



DROP TABLE IF EXISTS position;
CREATE TABLE position (
position_id INT NOT NULL AUTO_INCREMENT,
position_order_id INT NOT NULL,
position_name VARCHAR(255) NOT NULL,
position_price INT DEFAULT 0,
position_count INT NOT NULL,
PRIMARY KEY (position_id),
FOREIGN KEY (position_order_id) REFERENCES ordertable(order_id) ON DELETE CASCADE
);