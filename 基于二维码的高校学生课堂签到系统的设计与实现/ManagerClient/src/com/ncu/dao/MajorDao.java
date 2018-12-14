package com.ncu.dao;

import com.ncu.pojo.Major;
import com.ncu.service.ConnectMsql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class MajorDao {
    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;

    public ArrayList<Major> FindAllMajor(){
        ArrayList<Major> list = new ArrayList<>();
        String sql = "SELECT * FROM signdb.t_major";
        try {
            connection = ConnectMsql.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            while (resultSet.next()){
                Major major = new Major();
                major.setMajor_id(resultSet.getInt(1));
                major.setMajor_name(resultSet.getString(2));
                list.add(major);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
