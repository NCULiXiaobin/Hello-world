package org.ncu.pojo;

import java.io.Serializable;
import java.sql.Timestamp;

public class Sign implements Serializable {
    private int sign_id;
    private String sign_teacher;
    private String sign_major;
    private String sign_class;
    private Timestamp sign_time;
    private String sign_people;

    public int getSign_id() {
        return sign_id;
    }

    public void setSign_id(int sign_id) {
        this.sign_id = sign_id;
    }

    public String getSign_teacher() {
        return sign_teacher;
    }

    public void setSign_teacher(String sign_teacher) {
        this.sign_teacher = sign_teacher;
    }

    public String getSign_major() {
        return sign_major;
    }

    public void setSign_major(String sign_major) {
        this.sign_major = sign_major;
    }

    public String getSign_class() {
        return sign_class;
    }

    public void setSign_class(String sign_class) {
        this.sign_class = sign_class;
    }

    public Timestamp getSign_time() {
        return sign_time;
    }

    public void setSign_time(Timestamp sign_time) {
        this.sign_time = sign_time;
    }

    public String getSign_people() {
        return sign_people;
    }

    public void setSign_people(String sign_people) {
        this.sign_people = sign_people;
    }
}
