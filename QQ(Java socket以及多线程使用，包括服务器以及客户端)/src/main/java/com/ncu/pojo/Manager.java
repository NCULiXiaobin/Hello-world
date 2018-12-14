package com.ncu.pojo;

/**
 * Created by LiXiaobin on 2017/12/21.
 */
public class Manager {
    private int manegerId;
    private int managerAccount;
    private String managerPassword;
    private String managerName;

    public int getManagerAccount() {
        return managerAccount;
    }

    public void setManagerAccount(int managerAccount) {
        this.managerAccount = managerAccount;
    }

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    public int getManegerId() {
        return manegerId;
    }

    public void setManegerId(int manegerId) {
        this.manegerId = manegerId;
    }

    public String getManagerPassword() {
        return managerPassword;
    }

    public void setManagerPassword(String managerPassword) {
        this.managerPassword = managerPassword;
    }
}
