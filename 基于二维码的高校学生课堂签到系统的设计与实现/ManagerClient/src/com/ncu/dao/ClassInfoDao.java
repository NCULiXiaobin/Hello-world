package com.ncu.dao;

import com.ncu.pojo.ClassInfo;
import com.ncu.service.ConnectMsql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ClassInfoDao {
    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;

    public ArrayList<ClassInfo> findClassByMajor(String majorName){
        ArrayList<ClassInfo> list  = new ArrayList<>();
        String sql = "SELECT * FROM signdb.t_classinfo WHERE major_name ="+"'"+majorName+"'";
        try {
            connection = ConnectMsql.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            while (resultSet.next()){
                ClassInfo classInfo = new ClassInfo();
                classInfo.setClass_id(resultSet.getInt(1));
                classInfo.setMajor_name(resultSet.getString(2));
                classInfo.setClass_name(resultSet.getString(3));
                list.add(classInfo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            ConnectMsql.releaseResources(resultSet,statement,connection);
        }
        return list;
    }
    public int insertClassInfo(ClassInfo classInfo){
        int i = 0;
        String sql = "insert into signdb.t_classinfo(major_name,class_name) " +
                " values "+"("+"'"+classInfo.getMajor_name()+"'"+","+"'"+classInfo.getClass_name()+"'"+")";
        try {
            connection = ConnectMsql.getConnection();
            statement = connection.createStatement();
            i = statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return i;
    }
    public int findClassPeopleCount(String major,String className){
        int count = 0;
        String sql = "SELECT count(*) counts FROM signdb.t_student WHERE student_major = "+"'"+major+"'"+"AND student_class = "+"'"+className+"'";
        try {
            connection = ConnectMsql.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            while (resultSet.next()){
                count = resultSet.getInt("counts");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            ConnectMsql.releaseResources(resultSet,statement,connection);
        }
        return count;
    }
    public int deleteClassInfo(String className){
        int  i = 0;
        String sql = "DELETE FROM signdb.t_classinfo WHERE class_name = "+"'"+className+"'";
        try {
            connection = ConnectMsql.getConnection();
            statement =connection.createStatement();
            i = statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return i;
    }
}
