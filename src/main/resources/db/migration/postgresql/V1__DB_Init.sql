CREATE TABLE IF NOT EXISTS app_user(
  id UUID NOT NULL,
   email VARCHAR(255) NOT NULL,
   password VARCHAR(255) NOT NULL,
   first_name VARCHAR(255),
   last_name VARCHAR(255),
   created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
   last_modified TIMESTAMP WITHOUT TIME ZONE NOT NULL,
   role INTEGER NOT NULL,
   CONSTRAINT pk_app_user PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS token (
  user_id UUID NOT NULL,
   token VARCHAR(255) NOT NULL,
   CONSTRAINT pk_token PRIMARY KEY (user_id)
);

ALTER TABLE token ADD CONSTRAINT FK_TOKEN_ON_USER FOREIGN KEY (user_id) REFERENCES app_user (id);

CREATE TABLE IF NOT EXISTS product (
  product_id VARCHAR(255) NOT NULL,
   name VARCHAR(255),
   description VARCHAR(255),
   price DOUBLE PRECISION NOT NULL,
   currency VARCHAR(255),
   CONSTRAINT pk_product PRIMARY KEY (product_id)
);

ALTER TABLE product ADD CONSTRAINT uc_product_name UNIQUE (name);