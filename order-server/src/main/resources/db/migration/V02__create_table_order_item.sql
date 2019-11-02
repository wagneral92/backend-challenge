CREATE TABLE order_item(
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    description VARCHAR(250) NOT NULL,
    unit_price decimal(15,2) NOT NULL,
    quantity int NOT NULL

)ENGINE=InnoDB DEFAULT CHARSET=utf8;