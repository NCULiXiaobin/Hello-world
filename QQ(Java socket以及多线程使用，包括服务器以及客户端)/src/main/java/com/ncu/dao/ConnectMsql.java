package com.ncu.dao;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

/**
 * Created by LiXiaobin on 2017/11/9.
 */
public class ConnectMsql {
    private static String driverName;
    private static String url;
    private static String user;
    private static String password;
    static {
        try {
            driverName = "com.mysql.jdbc.Driver";
            url = "jdbc:mysql://127.0.0.1:3306/chatdb";
            user = "root";
            password = "Lxb090288";

            Class.forName(driverName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
        //获取连接
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    //释放资源
    public static void releaseResources(ResultSet resultSet, Statement statement, Connection connection) {
        try {
            if (resultSet != null)
                resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            resultSet = null;
            try {
                if (statement != null)
                    statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                statement = null;
                try {
                    if (connection != null)
                        connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                } finally {
                    connection = null;
                }
            }
        }
    }
}
