DROP DATABASE IF EXISTS `wish_list_app`;
CREATE DATABASE `wish_list_app`; 
USE `wish_list_app`;

SET NAMES utf8 ;
SET character_set_client = utf8mb4 ;

CREATE TABLE `users` (
	`user_id` int(255) NOT NULL AUTO_INCREMENT,
    `user_name` varchar(255),
    `user_email` varchar(255),
    `user_password` varchar(255),
    PRIMARY KEY (`user_id`)
);

CREATE TABLE `wish_lists` (
	`wish_list_id` int NOT NULL AUTO_INCREMENT,
    `user_id` int NOT NULL,
    `title` varchar(255) NOT NULL,
    `description` varchar(255),
    PRIMARY KEY (`wish_list_id`),
    KEY `user_id_idx` (`user_id`),
	CONSTRAINT `user_id` 
	  FOREIGN KEY (`user_id`) 
	  REFERENCES `users` (`user_id`) 
	  ON DELETE CASCADE 
	  ON UPDATE CASCADE
);

CREATE TABLE `wishes` (
  `wish_id` int NOT NULL AUTO_INCREMENT,
  `wish_list_id` int NOT NULL,
  `title` varchar(45) DEFAULT NULL,
  `description` varchar(45) DEFAULT NULL,
  `price` int DEFAULT NULL,
  `url_link` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`wish_id`),
  KEY `wish_list_id_idx` (`wish_list_id`),
  CONSTRAINT `wish_list_id` 
	  FOREIGN KEY (`wish_list_id`) 
	  REFERENCES `wish_lists` (`wish_list_id`) 
	  ON DELETE CASCADE 
	  ON UPDATE CASCADE
);