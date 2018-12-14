package com.ncu.pojo;

import java.util.Date;

/**
 * Created by LiXiaobin on 2017/12/19.
 */
public class HistoryMessage {
    private int MessageId;
    private Date MessageDate;
    private String MessageInfo;
    private int userSendId;
    private int userRecId;
    private String message_state;

    public String getMessage_state() {
        return message_state;
    }

    public void setMessage_state(String message_state) {
        this.message_state = message_state;
    }

    public Date getMessageDate() {
        return MessageDate;
    }

    public void setMessageDate(Date messageDate) {
        MessageDate = messageDate;
    }

    public int getMessageId() {
        return MessageId;
    }

    public void setMessageId(int messageId) {
        MessageId = messageId;
    }

    public String getMessageInfo() {
        return MessageInfo;
    }

    public void setMessageInfo(String messageInfo) {
        MessageInfo = messageInfo;
    }

    public int getUserRecId() {
        return userRecId;
    }

    public void setUserRecId(int userRecId) {
        this.userRecId = userRecId;
    }

    public int getUserSendId() {
        return userSendId;
    }

    public void setUserSendId(int userSendId) {
        this.userSendId = userSendId;
    }
}
