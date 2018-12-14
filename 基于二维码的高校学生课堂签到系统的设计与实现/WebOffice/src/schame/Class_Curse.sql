USE signdb;

DROP TABLE IF EXISTS t_class_curse;
CREATE TABLE t_class_curse(
  class_curse_id INT PRIMARY KEY AUTO_INCREMENT ,
  class_curse_major VARCHAR(30),
  class_curse_name VARCHAR(30),
  class_curse_classname VARCHAR(30),
  class_curse_day VARCHAR(10),
  class_curse_index INT,
  class_curse_teacher VARCHAR(30),
  class_curse_teacher_name VARCHAR(30)
)ENGINE ="innoDB";
