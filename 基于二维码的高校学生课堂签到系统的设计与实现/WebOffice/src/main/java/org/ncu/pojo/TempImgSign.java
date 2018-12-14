package org.ncu.pojo;

import net.sf.json.JSON;

import java.io.Serializable;
import java.sql.Timestamp;

public class TempImgSign implements Serializable {
    private String temp_img_key;
    private String temp_img_index;
    private Timestamp temp_img_start_time;
    private String temp_img_sign_people;

    public String getTemp_img_key() {
        return temp_img_key;
    }

    public void setTemp_img_key(String temp_img_key) {
        this.temp_img_key = temp_img_key;
    }

    public String getTemp_img_index() {
        return temp_img_index;
    }

    public void setTemp_img_index(String temp_img_index) {
        this.temp_img_index = temp_img_index;
    }

    public Timestamp getTemp_img_start_time() {
        return temp_img_start_time;
    }

    public void setTemp_img_start_time(Timestamp temp_img_start_time) {
        this.temp_img_start_time = temp_img_start_time;
    }

    public String getTemp_img_sign_people() {
        return temp_img_sign_people;
    }

    public void setTemp_img_sign_people(String temp_img_sign_people) {
        this.temp_img_sign_people = temp_img_sign_people;
    }
}
