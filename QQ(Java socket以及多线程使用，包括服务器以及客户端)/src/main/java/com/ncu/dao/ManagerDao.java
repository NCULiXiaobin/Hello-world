package com.ncu.dao;

import com.ncu.pojo.Manager;
import com.ncu.pojo.User;

import java.sql.*;

/**
 * Created by LiXiaobin on 2017/12/21.
 */
public class ManagerDao {
    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;
    private PreparedStatement preparedStatement;

    //管理员登陆验证
    public int FindMaCounts(int account, String password) {
        String sql = "select count(*) as count from t_manager "
                + "where manager_account = " + account + " and manager_password = " + password;
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
        }finally {
            ConnectMsql.releaseResources(resultSet,statement,connection);
        }
        return count;
    }

    //验证管理员账号密码
    public Manager FindMaMes(int account, String password){
        Manager manager = new Manager();
        String sql = "select * from t_manager "
                + "where manager_account = " + account + " and manager_account = " + password;
        try {
            connection = ConnectMsql.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            while (resultSet.next()){
                manager.setManagerName(resultSet.getString("manager_name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            ConnectMsql.releaseResources(resultSet,statement,connection);
        }
        return manager;
    }
}
