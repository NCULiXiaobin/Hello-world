USE signdb;

DROP TABLE IF EXISTS t_maneger;
CREATE TABLE t_maneger(
  manager_id INT PRIMARY KEY AUTO_INCREMENT,
  manager_account VARCHAR(30),
  manager_password VARCHAR(30),
  manager_name VARCHAR(30),
  manager_power ENUM('1','2','3')
)ENGINE ="innoDB";

INSERT INTO t_maneger
    ( manager_account, manager_password, manager_name, manager_power)
    VALUES
      ('8888','8888','一级管理员','1');
