DROP DATABASE IF EXISTS chatdb;
CREATE DATABASE chatdb DEFAULT CHARACTER SET utf8;
USE chatdb;

##创建用户表
DROP TABLE IF EXISTS t_user;
CREATE TABLE t_user (
   user_id   INT AUTO_INCREMENT PRIMARY KEY,
   user_account INT NOT NULL UNIQUE,
   password  VARCHAR(32) NOT NULL ,
   user_name VARCHAR(30) NOT NULL ,
   user_sex VARCHAR(5) NOT NULL ,
   register_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
   last_visit TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   state ENUM('在线','离线'),
   last_ip  VARCHAR(23)
)ENGINE=InnoDB;

##创建管理员表
DROP TABLE IF EXISTS t_manager;
CREATE TABLE t_manager (
   manager_id   INT AUTO_INCREMENT PRIMARY KEY,
   manager_account INT UNIQUE,
   manager_password  VARCHAR(32),
   manager_name VARCHAR(30)
)ENGINE=InnoDB;

##创建消息记录表
DROP TABLE IF EXISTS t_h_message;
CREATE TABLE t_h_message (
   message_id  INT AUTO_INCREMENT PRIMARY KEY,
   message_date TIMESTAMP,
   user_send_id int NOT NULL ,
   user_rec_id int NOT NULL ,
   message_info VARCHAR(50),
   message_state VARCHAR(5)
)ENGINE=InnoDB;

##创建好友列表功能
DROP TABLE IF EXISTS t_friend;
CREATE TABLE t_friend(
   friend_id INT AUTO_INCREMENT PRIMARY KEY,
   user_first_id INT,
   user_second_id INT
)ENGINE=InnoDB;

##插入初始化数据
INSERT INTO t_user (user_account,password,user_name)
             VALUES('907933970','123456','li');
INSERT INTO t_manager(manager_account,manager_password,manager_name)
            VALUES ('8888','8888','管理员');
COMMIT;