USE signdb;

##创建学生信息表
DROP TABLE IF EXISTS t_student;
CREATE TABLE t_student(
  student_id INT PRIMARY KEY AUTO_INCREMENT,
  student_account VARCHAR(30) UNIQUE ,
  student_password VARCHAR(30) DEFAULT 123123,
  student_cardid VARCHAR(30),
  student_name VARCHAR(30),
  student_sex ENUM('男','女'),
  student_joinyear VARCHAR(30),
  student_stuyear INT,
  student_major VARCHAR(30),
  student_class VARCHAR(30),
  student_phone VARCHAR(30),
  student_emile VARCHAR(30),
  student_adress VARCHAR(50),
  student_extra VARCHAR(50)
)ENGINE = InnoDB;
INSERT INTO t_student(student_account, student_cardid, student_name, student_sex, student_joinyear,
                      student_stuyear, student_major, student_class, student_phone, student_emile,
                      student_adress,student_extra) VALUES ('2424','140202199809025014','李晓斌','男','2017.10.12',4,'软件工程'
                      ,'142','1515515155','907933970@qq.com','asdadasdadasa','暂无描述');