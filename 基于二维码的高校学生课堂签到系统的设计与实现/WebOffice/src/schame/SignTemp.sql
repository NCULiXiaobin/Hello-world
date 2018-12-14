USE signdb;

DROP TABLE IF EXISTS t_temp_num_sign;
CREATE TABLE t_temp_num_sign(
  temp_index VARCHAR(20),
  temp_num_index JSON,
  temp_start_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  temp_sign_people VARCHAR(100)
)ENGINE = InnoDB;

DROP TABLE IF EXISTS t_temp_img_sign;
CREATE TABLE t_temp_img_sign(
  temp_img_key VARCHAR(30),
  temp_img_index JSON,
  temp_img_start_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  temp_img_sign_people VARCHAR(100)
)ENGINE = InnoDB;

SELECT json_extract(temp_img_index,'$.teacher'),json_extract(temp_img_index,'$.week') FROM t_temp_img_sign;  /*查询数据库中json数据*/


SELECT temp_img_start_time FROM t_temp_img_sign;