package com.ncu.pojo;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by LiXiaobin on 2017/12/18.
 */
public class User implements Serializable{
    private int userId;
    private int userAccount;
    private String userPassword;
    private String userName;
    private String userSex;
    private Date lastVisit;
    private String lastIp;
    private String state;
    private Date regieterDate;

    public String getLastIp() {
        return lastIp;
    }

    public void setLastIp(String lastIp) {
        this.lastIp = lastIp;
    }

    public Date getLastVisit() {
        return lastVisit;
    }

    public void setLastVisit(Date lastVisit) {
        this.lastVisit = lastVisit;
    }

    public int getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(int userAccount) {
        this.userAccount = userAccount;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserSex() {
        return userSex;
    }

    public void setUserSex(String userSex) {
        this.userSex = userSex;
    }

    public Date getRegieterDate() {
        return regieterDate;
    }

    public void setRegieterDate(Date regieterDate) {
        this.regieterDate = regieterDate;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
