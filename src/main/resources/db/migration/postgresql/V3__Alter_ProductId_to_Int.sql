ALTER TABLE product
ALTER COLUMN product_id TYPE SMALLINT USING product_id::smallint;