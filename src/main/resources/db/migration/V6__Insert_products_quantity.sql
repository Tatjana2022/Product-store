/*
INSERT INTO store_items(product_id, quantity)
VALUES (1, 7),
       (2, 6),
       (3, 4),
       (4, 6),
       (5, 8),
       (6, 9),
       (7, 1),
       (8, 0),
       (9, 4),
       (10, 2)
;

 */



INSERT INTO store_items (product_id, quantity)
SELECT product_id, CAST(REPLACE(name, 'NUMBER ') AS INT) AS quantity FROM store_products;