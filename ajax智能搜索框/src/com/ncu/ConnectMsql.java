package com.ncu;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by LiXiaobin on 2017/11/9.
 */
public class ConnectMsql{
    Connection con;
    String driver = "com.mysql.jdbc.Driver";
    String url = "jdbc:mysql://localhost:3306";
    String user = "root";
    String password = "Lxb090288";
    Statement statement;
    ResultSet rs;
    List list;
    public List<String> Search(String sql){
        try {
            Class.forName(driver);
            con = DriverManager.getConnection(url,user,password);
            statement = con.createStatement();
            rs = statement.executeQuery(sql);
            list = new ArrayList<String>();
            while (rs.next()){
                list.add(rs.getString("searchInfo"));
            }
            if (!con.isClosed()){
                System.out.println("Connect Success!!!!!!!!");
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
