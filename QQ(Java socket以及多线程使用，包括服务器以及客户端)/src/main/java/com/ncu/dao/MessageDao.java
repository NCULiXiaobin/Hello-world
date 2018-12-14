package com.ncu.dao;

import com.ncu.pojo.HistoryMessage;
import sun.plugin2.message.Message;

import java.sql.*;
import java.util.ArrayList;

/**
 * Created by LiXiaobin on 2017/12/19.
 */
public class MessageDao {
    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;
    private PreparedStatement preparedStatement;

    //插入历史信息
    public void InsertMessage(HistoryMessage message){
        String sql = "INSERT INTO t_h_message(user_send_id,user_rec_id,message_info,message_state) VALUES " +
                " (?,?,?,?)";
        try {
            connection = ConnectMsql.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,message.getUserSendId());
            preparedStatement.setInt(2,message.getUserRecId());
            preparedStatement.setString(3,message.getMessageInfo());
            preparedStatement.setString(4,message.getMessage_state());
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            ConnectMsql.releaseResources(resultSet,statement,connection);
        }
    }

    //查询所有历史信息
    public  ArrayList FindMessqge(int receiveID){
        String sql = "SELECT  * FROM t_h_message WHERE user_rec_id = "+ receiveID;
        ArrayList<HistoryMessage> arrayList = new ArrayList<>();

        try {
            connection = ConnectMsql.getConnection();
            statement  = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            while (resultSet.next()){
                HistoryMessage historyMessage = new HistoryMessage();
                historyMessage.setMessageId(resultSet.getInt(1));
                historyMessage.setMessageDate(resultSet.getTimestamp(2));
                historyMessage.setUserSendId(resultSet.getInt(3));
                historyMessage.setUserRecId(resultSet.getInt(4));
                historyMessage.setMessageInfo(resultSet.getString(5));
                historyMessage.setMessage_state(resultSet.getString(6));
                arrayList.add(historyMessage);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            ConnectMsql.releaseResources(resultSet,statement,connection);
        }
        return arrayList;
    }
}