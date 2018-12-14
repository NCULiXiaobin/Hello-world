package org.ncu.pojo;

import java.sql.Timestamp;

public class TempNumSign {
    String temp_index;
    Timestamp temp_start_time;
    String temp_sign_people;

    public String getTemp_index() {
        return temp_index;
    }

    public void setTemp_index(String temp_index) {
        this.temp_index = temp_index;
    }

    public Timestamp getTemp_start_time() {
        return temp_start_time;
    }

    public void setTemp_start_time(Timestamp temp_start_time) {
        this.temp_start_time = temp_start_time;
    }

    public String getTemp_sign_people() {
        return temp_sign_people;
    }

    public void setTemp_sign_people(String temp_sign_people) {
        this.temp_sign_people = temp_sign_people;
    }
}
