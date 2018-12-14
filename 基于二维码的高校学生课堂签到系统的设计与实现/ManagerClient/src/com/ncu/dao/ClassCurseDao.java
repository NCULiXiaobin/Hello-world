package com.ncu.dao;

import com.ncu.pojo.ClassCurse;
import com.ncu.service.ConnectMsql;

import java.sql.*;
import java.util.ArrayList;
import java.util.Vector;

public class ClassCurseDao {
    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;
    private PreparedStatement preparedStatement;

    public int insertClassCurse(ArrayList<ClassCurse> list){
        int count = 0;
        String sql = "INSERT INTO signdb.t_class_curse(class_curse_major," +
                " class_curse_name, class_curse_classname, class_curse_day, class_curse_index, class_curse_teacher,class_curse_teacher_name) VALUES " +
                "(?,?,?,?,?,?,?)";

        try {
            connection = ConnectMsql.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            connection.setAutoCommit(false);
            for (int i = 0;i<list.size();i++){
                preparedStatement.setString(1,list.get(i).getClass_curse_major());
                preparedStatement.setString(2,list.get(i).getClass_curse_name());
                preparedStatement.setString(3,list.get(i).getClass_curse_classname());
                preparedStatement.setString(4,list.get(i).getClass_curse_day());
                preparedStatement.setString(5,list.get(i).getClass_curse_index());
                preparedStatement.setString(6,list.get(i).getClass_curse_teacher());
                preparedStatement.setString(7,list.get(i).getClass_curse_teacher_name());
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

    public int updateClassCurse(ArrayList<ClassCurse> list,String major,String classname){
        int count = 0;
        String sql = "UPDATE signdb.t_class_curse SET " +
                "class_curse_name = ?," +
                "class_curse_teacher_name=?," +
                "class_curse_teacher=?," +
                "class_curse_day=?," +
                "class_curse_index=?" +
                "WHERE class_curse_major=?"+
                " AND class_curse_classname=?";
        try {
            connection = ConnectMsql.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            connection.setAutoCommit(false);
            for (int i = 0;i<list.size();i++){
                preparedStatement.setString(1,list.get(i).getClass_curse_name());
//                System.out.println(list.get(i).getClass_curse_name());
                preparedStatement.setString(2,list.get(i).getClass_curse_teacher_name());
                preparedStatement.setString(3,list.get(i).getClass_curse_teacher());
                preparedStatement.setString(4,list.get(i).getClass_curse_day());
                System.out.println(list.get(i).getClass_curse_day());
                preparedStatement.setString(5,list.get(i).getClass_curse_index());
                preparedStatement.setString(6,list.get(i).getClass_curse_major());
                preparedStatement.setString(7,list.get(i).getClass_curse_classname());
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

    public int findClassCurseCount(String major,String classname){
        int count = 0;
        String sql = "SELECT count(*) counts FROM signdb.t_class_curse WHERE " +
                " class_curse_major ='"+major+"'AND class_curse_classname = '"+classname+"'";
        try {
            connection = ConnectMsql.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            while (resultSet.next()){
                count = resultSet.getInt("counts");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    public ArrayList<ClassCurse> findClassCurse(String major,String classname){
        ArrayList<ClassCurse> curse = new ArrayList<>();
        String sql = "SELECT * FROM signdb.t_class_curse WHERE class_curse_major='"+major+"'"+"AND class_curse_classname = '"+classname+"'";
        try {
            connection = ConnectMsql.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            while (resultSet.next()){
                ClassCurse classCurse = new ClassCurse();
                classCurse.setClass_curse_id(resultSet.getInt(1));
                classCurse.setClass_curse_major(resultSet.getString(2));
                classCurse.setClass_curse_name(resultSet.getString(3));
                classCurse.setClass_curse_classname(resultSet.getString(4));
                classCurse.setClass_curse_day(resultSet.getString(5));
                classCurse.setClass_curse_index(resultSet.getString(6));
                classCurse.setClass_curse_teacher(resultSet.getString(7));
                classCurse.setClass_curse_teacher_name(resultSet.getString(8));
                curse.add(classCurse);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  curse;
    }

    public void deleteClassCurse(String major,String classname){
        String sql = "DELETE FROM signdb.t_class_curse WHERE class_curse_major = '"+major+"'" +
                " AND class_curse_classname = '"+classname+"'";
        try {
            connection = ConnectMsql.getConnection();
            statement = connection.createStatement();
            statement.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean deleteClassCurseImp(String major,String classname,String day,String index){
        String sql = "DELETE FROM signdb.t_class_curse WHERE class_curse_major='"+major+"'"+
                " AND class_curse_classname='"+classname+"'"+" AND class_curse_day= '"+day+"'"+
                " AND class_curse_index='"+index+"'";
        try {
            connection = ConnectMsql.getConnection();
            statement = connection.createStatement();
            statement.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }
}
