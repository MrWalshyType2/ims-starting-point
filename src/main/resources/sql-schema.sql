CREATE SCHEMA IF NOT EXISTS ims;
USE ims;
CREATE TABLE IF NOT EXISTS ims.customers(id int primary key auto_increment not null unique, first_name varchar(32) not null, surname varchar(32), username varchar(32), password varchar(32));
CREATE TABLE IF NOT EXISTS ims.orders(id int primary key auto_increment not null unique, fk_customer_id int not null, foreign key (fk_customer_id) references customers(id));
CREATE TABLE IF NOT EXISTS ims.items(id int primary key auto_increment not null unique, item_name varchar(32) not null, value int not null, amount int not null);
CREATE TABLE IF NOT EXISTS ims.order_items(fk_order_id int not null, fk_item_id int not null, quantity int not null);