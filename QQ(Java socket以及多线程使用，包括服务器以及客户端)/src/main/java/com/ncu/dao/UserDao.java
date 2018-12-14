package com.ncu.dao;

import com.ncu.pojo.User;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

/**
 * Created by LiXiaobin on 2017/12/20.
 */
public class UserDao {
    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;
    private PreparedStatement preparedStatement;


    //登陆验证
    public int FindCounts(int account, String password) {
        String sql = "select count(*) as count from t_user "
                + "where user_account = " + account + " and password = " + password;
        int count = 0;
        try {
            connection = ConnectMsql.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                count = resultSet.getInt("count");
            }
            ConnectMsql.releaseResources(resultSet, statement, connection);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectMsql.releaseResources(resultSet, statement, connection);
        }
        return count;
    }

    //注册验证
    public int FindCounts(int account) {
        String sql = " select count(*) as count from t_user" +
                " where user_account = " + account;
        int count = 0;
        try {
            connection = ConnectMsql.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                count = resultSet.getInt("count");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectMsql.releaseResources(resultSet, statement, connection);
        }
        return count;
    }

    //通过id查找用户
    public User FindById(int id) throws SQLException {
        User user = new User();
        String sql ="SELECT user_name,user_sex FROM t_user" +
                " WHERE user_id = "+id;
        connection = ConnectMsql.getConnection();
        statement = connection.createStatement();
        resultSet = statement.executeQuery(sql);
        while (resultSet.next()){
            user.setUserId(id);
            user.setUserName(resultSet.getString("user_name"));
            user.setUserSex(resultSet.getString("user_sex"));
        }
        ConnectMsql.releaseResources(resultSet,statement,connection);
        return user;
    }

    //通过昵称查找用户
    public User FindByName(String name) throws SQLException {
        User user = new User();
        String sql ="SELECT state,user_sex,user_id FROM t_user" +
                " WHERE user_name = "+ "'"+ name+ "'";
        System.out.println(sql);
        connection = ConnectMsql.getConnection();
        statement = connection.createStatement();
        resultSet = statement.executeQuery(sql);
        while (resultSet.next()){
            user.setState(resultSet.getString("state"));
            user.setUserName(name);
            user.setUserSex(resultSet.getString("user_sex"));
            user.setUserId(resultSet.getInt("user_id"));
        }
        ConnectMsql.releaseResources(resultSet,statement,connection);
        return user;
    }

    //更新用户状态
    public void UpdateLog(int id,String state){
        String sql = "UPDATE t_user SET state = "+ "'"+state+"'"
                +" WHERE user_id = "+id;
        try {
            connection = ConnectMsql.getConnection();
            statement = connection.createStatement();
            int i = statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            ConnectMsql.releaseResources(resultSet,statement,connection);
        }
    }

    //查找用户信息
    public User FindUserMes(int account, String password){
        User user = new User();
        String sql = "select * from t_user "
                + "where user_account = " + account + " and password = " + password;
        try {
            connection = ConnectMsql.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            while (resultSet.next()){
                user.setUserId(resultSet.getInt("user_id"));
                user.setUserName(resultSet.getString("user_name"));
                user.setUserSex(resultSet.getString("user_sex"));
                user.setState(resultSet.getString("state"));
                user.setRegieterDate(resultSet.getDate("register_time"));
                user.setLastVisit(resultSet.getTimestamp("last_visit"));
                user.setLastIp(resultSet.getString("last_ip"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            ConnectMsql.releaseResources(resultSet,statement,connection);
        }
        return user;
    }

    //用户注册
    public void InsertUser(int account, String password, String name, String sex,String ip){
        String sql = " insert into t_user(user_account, password, user_name, user_sex, register_time,last_ip) values " +
                "(?,?,?,?,?,?)";
        try {
            String pattern = "%tY-%<tm-%<td %<tT";
            String s = String.format(pattern,new Date());
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss", Locale.ENGLISH);
            dateFormat.setLenient(false);;
            java.util.Date timeDate = dateFormat.parse(s);//util类型
            java.sql.Timestamp dateTime = new java.sql.Timestamp(timeDate.getTime());
            connection = ConnectMsql.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,account);
            preparedStatement.setString(2,password);
            preparedStatement.setString(3,name);
            preparedStatement.setString(4,sex);
            preparedStatement.setTimestamp(5,dateTime);
            preparedStatement.setString(6,ip);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            ConnectMsql.releaseResources(resultSet,statement,connection);
        }
    }
}


