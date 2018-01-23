DROP TABLE IF EXISTS Carts;
CREATE TABLE Carts(
id INT  IDENTITY(1,1) PRIMARY KEY ,
totalPrice INT
);

DROP TABLE IF EXISTS WebsiteItems;
CREATE TABLE WebsiteItems(
id INT PRIMARY KEY NOT Null,
name VARCHAR(50),
price INT,
description VARCHAR(100)
);

DROP TABLE IF EXISTS SoldItems;
CREATE TABLE SoldItems(
id INT IDENTITY(1,1) PRIMARY KEY ,
cartId INT,
itemId INT
);

DROP TABLE IF EXISTS Users;
CREATE TABLE Users(
id INT  IDENTITY(1,1) PRIMARY KEY ,
name VARCHAR(50),
email VARCHAR(50),
password VARCHAR(50)
);

insert into Users (name ,email,password) VALUES ('khaled','k.gamal@itsans.com','123456');
insert into Carts (totalPrice) VALUES (6);
insert into SoldItems (cartId,itemId)VALUES (1,2);
insert into SoldItems (cartId,itemId)VALUES (1,4);
insert into WebsiteItems VALUES (1,'Item 1',1,'des 1');
insert into WebsiteItems VALUES (2,'Item 2',2,'des 2');
insert into WebsiteItems VALUES (3,'Item 3',3,'des 3');
insert into WebsiteItems VALUES (4,'Item 4',4,'des 4');
insert into WebsiteItems VALUES (5,'Item 5',5,'des 5');
insert into WebsiteItems VALUES (6,'Item 6',6,'des 6');
insert into WebsiteItems VALUES (7,'Item 7',7,'des 7');
insert into WebsiteItems VALUES (8,'Item 8',8,'des 8');
insert into WebsiteItems VALUES (9,'Item 9',9,'des 9');
insert into WebsiteItems VALUES (10,'Item 10',10,'des 10');


