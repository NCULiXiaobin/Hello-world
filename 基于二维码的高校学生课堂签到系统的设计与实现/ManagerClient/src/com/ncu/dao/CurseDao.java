package com.ncu.dao;

import com.ncu.pojo.Curse;
import com.ncu.service.ConnectMsql;

import java.sql.*;
import java.util.ArrayList;

public class CurseDao {

    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;
    private PreparedStatement preparedStatement;

    public ArrayList<Curse> selectCurse(String major){
        ArrayList<Curse> list = new ArrayList<>();
        String sql = "SELECT * FROM signdb.t_curse WHERE curse_major='"+major+"'";
        try {
            connection = ConnectMsql.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            while (resultSet.next()){
                Curse curse = new Curse();
                curse.setCurse_id(resultSet.getInt("curse_id"));
                curse.setCurse_major(resultSet.getString("curse_major"));
                curse.setCurse_name(resultSet.getString("curse_name"));
                list.add(curse);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public int insertCurse(ArrayList<String> list,String major){
        int count = 0;
        String sql = "INSERT INTO signdb.t_curse(curse_major, curse_name) VALUES (?,?)";
        try {
            connection = ConnectMsql.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            connection.setAutoCommit(false);   //关闭自动提交，批量处理必须关闭这个
            for (int i = 0;i<list.size();i++){
                preparedStatement.setString(1,major);
                preparedStatement.setString(2,list.get(i));
                preparedStatement.addBatch();
            }
            int []num = preparedStatement.executeBatch();
            connection.commit();
            count = num.length;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    public int deleteCurse(ArrayList<String> list,String major){
        int count = 0;
        String sql = "DELETE FROM signdb.t_curse WHERE curse_major = ? AND curse_name = ?";
        try {
            connection = ConnectMsql.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            connection.setAutoCommit(false);   //关闭自动提交，批量处理必须关闭这个
            for (int i = 0;i<list.size();i++){
                preparedStatement.setString(1,major);
                preparedStatement.setString(2,list.get(i));
                preparedStatement.addBatch();
            }
            int []num = preparedStatement.executeBatch();
            connection.commit();
            count = num.length;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }
}
