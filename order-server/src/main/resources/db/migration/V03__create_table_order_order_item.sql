CREATE TABLE order_order_item(
  order_id BIGINT NOT NULL,
  order_item_id BIGINT NOT NULL,

  PRIMARY KEY (order_id, order_item_id),
  CONSTRAINT fk_order FOREIGN KEY (order_id) REFERENCES tb_order(id),
  CONSTRAINT fk_order_item FOREIGN KEY (order_item_id) REFERENCES order_item(id)

)ENGINE=InnoDB DEFAULT CHARSET=utf8;