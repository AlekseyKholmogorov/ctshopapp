CREATE DATABASE CTSHOP;

USE CTSHOP;

CREATE TABLE category (
	id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL
    );

CREATE TABLE product (
	id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(20) NOT NULL,
    region VARCHAR(20) NOT NULL,
    description TEXT NOT NULL,
    price DECIMAL(8,2) NOT NULL,
    quantity INT NOT NULL DEFAULT 0,
    category_id INT NOT NULL,
    FOREIGN KEY (category_id) REFERENCES category (id)
    );

CREATE TABLE user (
	id INT AUTO_INCREMENT PRIMARY KEY,
    login VARCHAR(20) NOT NULL UNIQUE,
    password VARCHAR(20) NOT NULL,
    email VARCHAR(50) NOT NULL UNIQUE
    );
    
CREATE TABLE u_order (
	id INT AUTO_INCREMENT PRIMARY KEY,
    paid TINYINT(1) NOT NULL DEFAULT 0,
    user_id INT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES user (id)
    );    
    
CREATE TABLE order_product (
	order_id INT NOT NULL,
    product_id INT NOT NULL,
    PRIMARY KEY (order_id, product_id),
    FOREIGN KEY (order_id) REFERENCES u_order (id),
    FOREIGN KEY (product_id) REFERENCES product (id)
    );

INSERT INTO category VALUES(null, "coffee");
INSERT INTO category VALUES(null, "tea");
INSERT INTO category VALUES(null, "accessories");
INSERT INTO product VALUES(null, "assam", "india", "black tea from india", 21.95, 3, 2)
INSERT INTO product VALUES(null, "lapsang souchong", "china", "black tea from china", 9.98, 0, 2)
INSERT INTO product VALUES(null, "bucaramanga", "colombia", "coffee from colombia", 26.40, 0, 1)
INSERT INTO product VALUES(null, "amecafe", "brazil", "coffee from brazil", 18.75, 2, 1)
