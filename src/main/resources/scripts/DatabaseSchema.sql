DROP TABLE IF EXISTS Carts;
CREATE TABLE Carts(
id INT PRIMARY KEY NOT Null,
totalPrice INT
);

DROP TABLE IF EXISTS Items;
CREATE TABLE Items(
id INT PRIMARY KEY NOT Null,
name VARCHAR(50),
price INT,
description VARCHAR(100)
);

DROP TABLE IF EXISTS SoldItems;
CREATE TABLE SoldItems(
cartId INT,
itemId INT
);


insert into Carts VALUES (1,2);
insert into SoldItems VALUES (1,2);

