use items;

CREATE TABLE product(
                        ID INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
                        Name VARCHAR(100) NOT NULL,
                        Unit VARCHAR(4) NOT NULL,
                        Cost FLOAT,
                        Description TEXT(1000)
);

CREATE TABLE live_inventory(
    ID INT NOT NULL AUTO_INCREMENT KEY,
    Quantity FLOAT,
    Previous_Stock1 FLOAT,
    Previous_Stock2 FLOAT
);

CREATE TABLE orders(
    ID INT NOT NULL AUTO_INCREMENT KEY,
    Purchase INT,
    Suggested_Purchase INT,
    Purchase_Previous1 INT,
    Purchase_Previous2 INT
);

ALTER TABLE product
    AUTO_INCREMENT=100001;

ALTER TABLE orders
    AUTO_INCREMENT=100001;

ALTER TABLE live_inventory
    AUTO_INCREMENT=100001;

CREATE VIEW stock AS
SELECT product.ID, product.Name, live_inventory.Quantity, product.Unit
FROM product
         INNER JOIN live_inventory ON product.ID = live_inventory.ID;

CREATE VIEW order_purchase AS
SELECT product.ID, product.Name, live_inventory.Quantity, orders.Purchase,
       orders.Suggested_Purchase, orders.Purchase_Previous1, orders.Purchase_Previous2
FROM product
         INNER JOIN live_inventory ON product.ID = live_inventory.ID
         INNER JOIN orders ON live_inventory.ID = orders.ID;

CREATE TABLE names(
    ID INT NOT NULL AUTO_INCREMENT KEY,
    Name VARCHAR(100) NOT NULL
);