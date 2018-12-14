DROP DATABASE IF EXISTS signdb;
CREATE DATABASE signdb DEFAULT CHARACTER SET utf8;
USE signdb;

##创建教师信息表
DROP TABLE IF EXISTS t_teacher;
CREATE TABLE t_teacher(
  teacher_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY ,
  teacher_account INT NOT NULL UNIQUE ,
  teacher_password VARCHAR(30) NOT NULL DEFAULT 123456,
  teacher_name VARCHAR(30) NOT NULL ,
  teacher_sex ENUM('男','女'),
  teacher_age INT(3) UNSIGNED,
  teacher_major VARCHAR(30),
  teacher_phone VARCHAR(30),
  teacher_phoneex VARCHAR(30),
  teacher_emile VARCHAR(20),
  teacher_extra VARCHAR(30) DEFAULT "暂无描述"
)ENGINE = InnoDB;

INSERT INTO t_teacher(teacher_account, teacher_name, teacher_sex, teacher_age, teacher_major,teacher_phone,teacher_phoneex,teacher_emile) VALUES
  (8888,'胡勇','男',30,'软件工程','13934924332','暂无','907933970@qq.com');
