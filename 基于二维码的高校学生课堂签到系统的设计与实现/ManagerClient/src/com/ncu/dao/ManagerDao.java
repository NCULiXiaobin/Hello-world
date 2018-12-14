package com.ncu.dao;

import com.ncu.pojo.Manager;
import com.ncu.service.ConnectMsql;

import java.sql.*;

public class ManagerDao {
    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;
    private PreparedStatement preparedStatement;

    //登陆验证
    public int findManagerCount(String account){
        String sql = "SELECT count(*) counts FROM signdb.t_maneger" +
                " WHERE manager_account = "+ account;
        int i = 0;
        try {
            connection = ConnectMsql.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            while (resultSet.next()){
                i = resultSet.getInt("counts");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            ConnectMsql.releaseResources(resultSet,statement,connection);
        }
        return i;
    }

    //密码验证
    public Manager findManager(String account,String password){
        String sql = "select * from signdb.t_maneger" +
                " where manager_account = "+account +" and manager_password = "+password;
        Manager manager = new Manager();
        try {
            connection = ConnectMsql.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            while (resultSet.next()){
                manager.setManagerAccount(account);
                manager.setManagerPassword(password);
                manager.setManagerId(resultSet.getInt("manager_id"));
                manager.setManagerName(resultSet.getString("manager_name"));
                manager.setManagerPower(resultSet.getString("manager_power"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            ConnectMsql.releaseResources(resultSet,statement,connection);
        }
        return manager;
    }
}
