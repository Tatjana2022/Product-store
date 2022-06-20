
--ALTER TABLE store_items
   -- ADD FOREIGN KEY (product_id) REFERENCES FK_store_products(product_id);

ALTER TABLE store_items
    ADD CONSTRAINT FK_ItemsProducts FOREIGN KEY (product_id) REFERENCES store_products(product_id);
