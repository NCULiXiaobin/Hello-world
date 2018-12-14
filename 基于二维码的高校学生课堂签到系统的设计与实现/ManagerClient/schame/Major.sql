USE signdb;

DROP TABLE IF EXISTS t_major;
CREATE TABLE t_major(
  major_id INT PRIMARY KEY AUTO_INCREMENT,
  major_name VARCHAR(30)
)ENGINE ="innoDB";


INSERT INTO t_major(major_name) VALUES ('软件工程'),('信息安全'),('计算机科学'),('电子商务');
