INSERT INTO store_items (product_id, quantity)
    SELECT product_id, CAST(REPLACE(name, 'NUMBER ') AS INT) AS quantity FROM store_products;