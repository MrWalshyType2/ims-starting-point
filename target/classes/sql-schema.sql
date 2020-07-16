create database if not exists ims_test1;
create table if not exists ims_test1.customers(id int primary key auto_increment not null unique, first_name varchar(32) not null, surname varchar(32));
create table if not exists ims_test1.orders(id int primary key auto_increment not null unique, fk_customer_id int not null, foreign key (fk_customer_id) references customers(id));
create table if not exists ims_test1.items(id int primary key auto_increment not null unique, item_name varchar(32) not null, value int not null, amount int not null);
create table if not exists ims_test1.order_items(fk_order_id int not null, fk_item_id int not null, quantity int not null);