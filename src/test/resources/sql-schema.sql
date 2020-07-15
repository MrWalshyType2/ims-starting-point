drop ims_test2 if exists;
create database if not exists ims_test2;
create table if not exists ims_test2.customers(id int primary key auto_increment not null unique, first_name varchar(32) not null, surname varchar(32) not null, email varchar(64) not null, username varchar(16) not null unique, password varchar(16) not null);
create table if not exists ims_test2.orders(id int primary key auto_increment not null unique, fk_customer_id int not null, foreign key (fk_customer_id) references customers(id));
create table if not exists ims_test2.items(id int primary key auto_increment not null unique, item_name varchar(32) not null, value int not null, amount int not null);
create table if not exists ims_test2.order_items(fk_order_id int not null, fk_item_id int not null, quantity int not null);
insert into ims_test2.customers(first_name, surname) values("Bob", "Perry");
insert into ims_test2.customers(first_name, surname) values("Friedrick", "Eisenhoff");