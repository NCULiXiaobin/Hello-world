package com.ncu.pojo;

/**
 * Created by LiXiaobin on 2017/12/25.
 */
public class Friend {
    private int friendId;
    private int firstUserId;
    private int SecondUserId;

    public int getFirstUserId() {
        return firstUserId;
    }

    public void setFirstUserId(int firstUserId) {
        this.firstUserId = firstUserId;
    }

    public int getFriendId() {
        return friendId;
    }

    public void setFriendId(int friendId) {
        this.friendId = friendId;
    }

    public int getSecondUserId() {
        return SecondUserId;
    }

    public void setSecondUserId(int secondUserId) {
        SecondUserId = secondUserId;
    }
}
