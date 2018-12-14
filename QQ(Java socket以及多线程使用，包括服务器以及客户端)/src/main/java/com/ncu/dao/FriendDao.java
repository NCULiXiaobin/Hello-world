package com.ncu.dao;

import com.ncu.pojo.Friend;

import java.sql.*;
import java.util.ArrayList;

/**
 * Created by LiXiaobin on 2017/12/21.
 */
public class FriendDao {
    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;
    private PreparedStatement preparedStatement;

    //好友查找
    public ArrayList FindFriend(int firstUserId) throws SQLException {
        String sql = "SELECT user_second_id FROM chatdb.t_friend" +
                " WHERE user_first_id = "+firstUserId;
        ArrayList<Friend> list = new ArrayList<>();
        connection = ConnectMsql.getConnection();
        statement  =connection.createStatement();
        resultSet = statement.executeQuery(sql);
        ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
        while (resultSet.next()){
            Friend friend = new Friend();
            friend.setFirstUserId(firstUserId);
            friend.setSecondUserId(resultSet.getInt("user_second_id"));
            list.add(friend);
        }
        return list;
    }
}
