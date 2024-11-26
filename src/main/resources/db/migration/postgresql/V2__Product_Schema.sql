DROP TABLE IF EXISTS product;

CREATE TABLE IF NOT EXISTS product (
  product_id VARCHAR(255) NOT NULL,
   name VARCHAR(255) NOT NULL,
   description VARCHAR(255),
   price DOUBLE PRECISION NOT NULL,
   currency VARCHAR(255) NOT NULL,
   CONSTRAINT pk_product PRIMARY KEY (product_id)
);

ALTER TABLE product ADD CONSTRAINT uc_product_name UNIQUE (name);