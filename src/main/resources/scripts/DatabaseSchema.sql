DROP TABLE IF EXISTS Carts;
CREATE TABLE Carts(
id INT  IDENTITY(1,1) PRIMARY KEY ,
totalPrice INT
);

DROP TABLE IF EXISTS WebsiteItems;
CREATE TABLE WebsiteItems(
id INT IDENTITY(1,1) PRIMARY KEY NOT Null,
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
insert into WebsiteItems (name ,price , description) VALUES ('Item 1',1,'des 1');
insert into WebsiteItems (name ,price , description) VALUES ('Item 2',2,'des 2');
insert into WebsiteItems (name ,price , description) VALUES ('Item 3',3,'des 3');
insert into WebsiteItems (name ,price , description) VALUES ('Item 4',4,'des 4');
insert into WebsiteItems (name ,price , description) VALUES ('Item 5',5,'des 5');
insert into WebsiteItems (name ,price , description) VALUES ('Item 6',6,'des 6');
insert into WebsiteItems (name ,price , description) VALUES ('Item 7',7,'des 7');
insert into WebsiteItems (name ,price , description) VALUES ('Item 8',8,'des 8');
insert into WebsiteItems (name ,price , description) VALUES ('Item 9',9,'des 9');
insert into WebsiteItems (name ,price , description) VALUES ('Item 10',10,'des 10');


