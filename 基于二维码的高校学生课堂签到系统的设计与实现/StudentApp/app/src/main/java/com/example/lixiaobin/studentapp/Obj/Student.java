package com.example.lixiaobin.studentapp.Obj;

import java.io.Serializable;

public class Student implements Serializable{
    private String student_id;
    private int student_account;
    private String student_password;
    private String student_cardid;
    private String student_name;
    private String student_sex;
    private String student_joinyear;
    private int student_stuyear;
    private String student_major;
    private String student_class;
    private String student_phone;
    private String student_emile;
    private String student_adress;
    private String student_extra;

    public String getStudent_extra() {
        return student_extra;
    }

    public void setStudent_extra(String student_extra) {
        this.student_extra = student_extra;
    }

    public String getStudent_id() {
        return student_id;
    }

    public void setStudent_id(String student_id) {
        this.student_id = student_id;
    }

    public int getStudent_account() {
        return student_account;
    }

    public void setStudent_account(int student_account) {
        this.student_account = student_account;
    }

    public String getStudent_password() {
        return student_password;
    }

    public void setStudent_password(String student_password) {
        this.student_password = student_password;
    }

    public String getStudent_cardid() {
        return student_cardid;
    }

    public void setStudent_cardid(String student_cardid) {
        this.student_cardid = student_cardid;
    }

    public String getStudent_name() {
        return student_name;
    }

    public void setStudent_name(String student_name) {
        this.student_name = student_name;
    }

    public String getStudent_sex() {
        return student_sex;
    }

    public String getStudent_major() {
        return student_major;
    }

    public void setStudent_major(String student_major) {
        this.student_major = student_major;
    }

    public void setStudent_sex(String student_sex) {
        this.student_sex = student_sex;
    }

    public String getStudent_joinyear() {
        return student_joinyear;
    }

    public void setStudent_joinyear(String student_joinyear) {
        this.student_joinyear = student_joinyear;
    }

    public int getStudent_stuyear() {
        return student_stuyear;
    }

    public void setStudent_stuyear(int student_stuyear) {
        this.student_stuyear = student_stuyear;
    }

    public String getStudent_class() {
        return student_class;
    }

    public void setStudent_class(String student_class) {
        this.student_class = student_class;
    }

    public String getStudent_phone() {
        return student_phone;
    }

    public void setStudent_phone(String student_phone) {
        this.student_phone = student_phone;
    }

    public String getStudent_emile() {
        return student_emile;
    }

    public void setStudent_emile(String student_emile) {
        this.student_emile = student_emile;
    }

    public String getStudent_adress() {
        return student_adress;
    }

    public void setStudent_adress(String student_adress) {
        this.student_adress = student_adress;
    }
}
