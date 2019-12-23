DROP SCHEMA IF EXISTS CTSHOP;
CREATE SCHEMA CTSHOP;
USE CTSHOP;

CREATE TABLE category (
	id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL
    );

CREATE TABLE product (
	id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(20) NOT NULL,
    region VARCHAR(20) NOT NULL,
    description TEXT,
    price DECIMAL(8,2) NOT NULL DEFAULT 0.0,
    quantity INT NOT NULL DEFAULT 0,
    category_id INT NOT NULL,
    FOREIGN KEY (category_id) REFERENCES category (id)
    );

CREATE TABLE user (
	id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    active INT
    );
    
CREATE TABLE user_order (
	id INT AUTO_INCREMENT PRIMARY KEY,
    status TINYINT(1) NOT NULL DEFAULT 0,
    user_id INT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES user (id)
    );    
    
CREATE TABLE order_product (
	order_id INT NOT NULL,
    product_id INT NOT NULL,
    quantity_in_order INT NOT NULL DEFAULT 0,
    PRIMARY KEY (order_id, product_id),
    FOREIGN KEY (order_id) REFERENCES user_order (id),
    FOREIGN KEY (product_id) REFERENCES product (id)
    );

