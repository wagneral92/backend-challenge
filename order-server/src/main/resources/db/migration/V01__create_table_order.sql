CREATE TABLE tb_order(
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    store_id BIGINT NOT NULL,
    address VARCHAR(255) NOT NULL,
    confirmation_date TIMESTAMP NULL,
    status VARCHAR(60) NOT NULL

)ENGINE=InnoDB DEFAULT CHARSET=utf8;