USE signdb;

DROP TABLE IF EXISTS t_curse;
CREATE TABLE t_curse(
  curse_id INT PRIMARY KEY AUTO_INCREMENT ,
  curse_major VARCHAR(30),
  curse_name VARCHAR(30)
)ENGINE ="innoDB";
