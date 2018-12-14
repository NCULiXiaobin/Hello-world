package org.ncu.pojo;

import java.sql.Timestamp;

public class TempNumSign {
    private String temp_index;
    private String temp_num_index;
    private Timestamp temp_start_time;
    private String temp_sign_people;

    public String getTemp_index() {
        return temp_index;
    }

    public void setTemp_index(String temp_index) {
        this.temp_index = temp_index;
    }

    public String getTemp_num_index() {
        return temp_num_index;
    }

    public void setTemp_num_index(String temp_num_index) {
        this.temp_num_index = temp_num_index;
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
