DROP TABLE IF EXISTS Carts;
CREATE TABLE Carts(
ID INT PRIMARY KEY NOT Null,
TotalPrice INT
);

DROP TABLE IF EXISTS Items;
CREATE TABLE Items(
ID INT PRIMARY KEY NOT Null,
Name VARCHAR(50),
Price INT,
Description VARCHAR(100)
);

DROP TABLE IF EXISTS SoldItems;
CREATE TABLE SoldItems(
CartId INT,
ItemId INT
);


insert into Carts VALUES (1,2);
insert into SoldItems VALUES (1,2);

