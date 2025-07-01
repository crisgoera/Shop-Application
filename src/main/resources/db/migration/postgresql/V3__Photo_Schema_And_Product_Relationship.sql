CREATE TABLE IF NOT EXISTS photo (
    photo_id INTEGER NOT NULL,
    product_id INTEGER NOT NULL,
    name VARCHAR(255),
    url VARCHAR(255) NOT NULL,
    CONSTRAINT fk_product FOREIGN KEY(product_id) REFERENCES product(product_id)
);