USE signdb;

DROP TABLE IF EXISTS t_sign;
CREATE TABLE t_sign(
  sign_id INT PRIMARY KEY AUTO_INCREMENT,
  sign_teacher VARCHAR(30),
  sign_major VARCHAR(30),
  sign_class VARCHAR(30),
  sign_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  sign_people VARCHAR(100)
)ENGINE = InnoDB;
