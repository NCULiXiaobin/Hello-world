USE signdb;

DROP TABLE IF EXISTS t_classinfo;
CREATE TABLE t_classinfo(
  class_id INT PRIMARY KEY AUTO_INCREMENT,
  major_name VARCHAR(30),
  class_name VARCHAR(30)
)ENGINE ="innoDB";

CREATE TABLE `t_permission` (
  `p_id` int(12),
  `name` varchar(20) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`p_id`)
);