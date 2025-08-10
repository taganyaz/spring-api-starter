CREATE TABLE carts(
    id BINARY(16) PRIMARY KEY,
    date_created DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE cart_items(
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    cart_id BINARY(16) NOT NULL,
    product_id BIGINT NOT NULL,
    quantity TINYINT NOT NULL,
    CONSTRAINT cart_items_carts_fk
        FOREIGN KEY(cart_id) REFERENCES carts(id),
    CONSTRAINT cart_items_products_fk
        FOREIGN KEY (product_id) REFERENCES products(id)
);