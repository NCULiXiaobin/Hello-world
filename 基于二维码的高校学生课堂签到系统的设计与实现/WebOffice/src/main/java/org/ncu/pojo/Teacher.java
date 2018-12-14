package org.ncu.pojo;

import java.io.Serializable;

public class Teacher implements Serializable{
    private int teacher_id;
    private int teacher_account;
    private String teacher_password;
    private String teacher_name;
    private String teacher_sex;
    private int teacher_age;
    private String teacher_major;
    private String teacher_phone;
    private String teacher_phoneex;
    private String teacher_emile;
    private String teacher_extra;

    public int getTeacher_id() {
        return teacher_id;
    }

    public void setTeacher_id(int teacher_id) {
        this.teacher_id = teacher_id;
    }

    public int getTeacher_account() {
        return teacher_account;
    }

    public void setTeacher_account(int teacher_account) {
        this.teacher_account = teacher_account;
    }

    public String getTeacher_password() {
        return teacher_password;
    }

    public void setTeacher_password(String teacher_password) {
        this.teacher_password = teacher_password;
    }

    public String getTeacher_name() {
        return teacher_name;
    }

    public void setTeacher_name(String teacher_name) {
        this.teacher_name = teacher_name;
    }

    public String getTeacher_sex() {
        return teacher_sex;
    }

    public void setTeacher_sex(String teacher_sex) {
        this.teacher_sex = teacher_sex;
    }

    public int getTeacher_age() {
        return teacher_age;
    }

    public void setTeacher_age(int teacher_age) {
        this.teacher_age = teacher_age;
    }

    public String getTeacher_major() {
        return teacher_major;
    }

    public void setTeacher_major(String teacher_major) {
        this.teacher_major = teacher_major;
    }

    public String getTeacher_extra() {
        return teacher_extra;
    }

    public void setTeacher_extra(String teacher_extra) {
        this.teacher_extra = teacher_extra;
    }

    public String getTeacher_phone() {
        return teacher_phone;
    }

    public void setTeacher_phone(String teacher_phone) {
        this.teacher_phone = teacher_phone;
    }

    public String getTeacher_phoneex() {
        return teacher_phoneex;
    }

    public void setTeacher_phoneex(String teacher_phoneex) {
        this.teacher_phoneex = teacher_phoneex;
    }

    public String getTeacher_emile() {
        return teacher_emile;
    }

    public void setTeacher_emile(String teacher_emile) {
        this.teacher_emile = teacher_emile;
    }
}
