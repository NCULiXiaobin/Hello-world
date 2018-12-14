package com.ncu.dao;

import com.ncu.pojo.Teacher;
import com.ncu.service.ConnectMsql;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class TeacherDao {
    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;
    private PreparedStatement preparedStatement;

    public int findTeacherCounts(int account){
        int count = 0;
        String sql = "SELECT count(*) counts FROM signdb.t_teacher WHERE teacher_account="+account;
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
    public ArrayList<Teacher> findAllTeacher(){
        List  list = new ArrayList<Teacher>();
        String sql = "select * from signdb.t_teacher";
        try {
            connection = ConnectMsql.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            while (resultSet.next()){
                Teacher teacher = new Teacher();
                teacher.setTeacher_id(resultSet.getInt(1));
                teacher.setTeacher_account(resultSet.getInt(2));
                teacher.setTeacher_password(resultSet.getString(3));
                teacher.setTeacher_name(resultSet.getString(4));
                teacher.setTeacher_sex(resultSet.getString(5));
                teacher.setTeacher_age(resultSet.getInt(6));
                teacher.setTeacher_job(resultSet.getString(7));
                teacher.setTeacher_phone(resultSet.getString(8));
                teacher.setTeacher_phoneex(resultSet.getString(9));
                teacher.setTeacher_emile(resultSet.getString(10));
                teacher.setTeacher_extra(resultSet.getString(11));
                list.add(teacher);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return (ArrayList<Teacher>) list;
    }
    public Vector<Vector> findTeacherByAccount(int account){
        Vector<Vector> vectors = new Vector<>();
        String sql = "select * from signdb.t_teacher" +
                " where teacher_account = "+ account;
        try {
            connection = ConnectMsql.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            while (resultSet.next()){
                Vector<String>  vector = new Vector<>();
                vector.add(String.valueOf(resultSet.getInt(2)));
                vector.add(resultSet.getString(3));
                vector.add(resultSet.getString(4));
                vector.add(resultSet.getString(5));
                vector.add(String.valueOf(resultSet.getInt(6)));
                vector.add(resultSet.getString(7));
                vector.add(resultSet.getString(8));
                vector.add(resultSet.getString(9));
                vector.add(resultSet.getString(10));
                vector.add(resultSet.getString(11));
                vectors.add(vector);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            ConnectMsql.releaseResources(resultSet,statement,connection);
        }
        return vectors;
    }
    public ArrayList<Teacher> findTeacherByAccountImp(int account){
        ArrayList<Teacher> arrayList = new ArrayList<>();
        String sql = "select * from signdb.t_teacher" +
                " where teacher_account = "+ account;
        try {
            connection = ConnectMsql.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            while (resultSet.next()){
                Teacher teacher = new Teacher();
                teacher.setTeacher_id(resultSet.getInt(1));
                teacher.setTeacher_account(resultSet.getInt(2));
                teacher.setTeacher_password(resultSet.getString(3));
                teacher.setTeacher_name(resultSet.getString(4));
                teacher.setTeacher_sex(resultSet.getString(5));
                teacher.setTeacher_age(resultSet.getInt(6));
                teacher.setTeacher_job(resultSet.getString(7));
                teacher.setTeacher_phone(resultSet.getString(8));
                teacher.setTeacher_phoneex(resultSet.getString(9));
                teacher.setTeacher_emile(resultSet.getString(10));
                teacher.setTeacher_extra(resultSet.getString(11));
                arrayList.add(teacher);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return arrayList;
    }
    public Vector<Vector> findTeacherByName(String name){
        Vector<Vector> vectors = new Vector<>();
        String sql = "select * from signdb.t_teacher" +
                " where teacher_name = "+ "'"+name+"'";
        try {
            connection = ConnectMsql.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            while (resultSet.next()){
                Vector<String>  vector = new Vector<>();
                vector.add(String.valueOf(resultSet.getInt(2)));
                vector.add(resultSet.getString(3));
                vector.add(resultSet.getString(4));
                vector.add(resultSet.getString(5));
                vector.add(String.valueOf(resultSet.getInt(6)));
                vector.add(resultSet.getString(7));
                vector.add(resultSet.getString(8));
                vector.add(resultSet.getString(9));
                vector.add(resultSet.getString(10));
                vector.add(resultSet.getString(11));
                vectors.add(vector);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            ConnectMsql.releaseResources(resultSet,statement,connection);
        }
        return vectors;
    }
    public ArrayList<Teacher> findTeacherByNameImp(String name){
        ArrayList<Teacher> arrayList = new ArrayList<>();
        String sql = "select * from signdb.t_teacher" +
                " where teacher_name = "+ "'"+name+"'";
        try {
            connection = ConnectMsql.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            while (resultSet.next()){
                Teacher teacher = new Teacher();
                teacher.setTeacher_id(resultSet.getInt(1));
                teacher.setTeacher_account(resultSet.getInt(2));
                teacher.setTeacher_password(resultSet.getString(3));
                teacher.setTeacher_name(resultSet.getString(4));
                teacher.setTeacher_sex(resultSet.getString(5));
                teacher.setTeacher_age(resultSet.getInt(6));
                teacher.setTeacher_job(resultSet.getString(7));
                teacher.setTeacher_phone(resultSet.getString(8));
                teacher.setTeacher_phoneex(resultSet.getString(9));
                teacher.setTeacher_emile(resultSet.getString(10));
                teacher.setTeacher_extra(resultSet.getString(11));
                arrayList.add(teacher);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return arrayList;
    }
    public Vector<Vector> findTeacherByJob(String job) {
        Vector<Vector> vectors = new Vector<>();
        String sql = "select * from signdb.t_teacher" +
                " where teacher_major = " + "'" + job + "'";
        try {
            connection = ConnectMsql.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                Vector<String> vector = new Vector<>();
                vector.add(String.valueOf(resultSet.getInt(2)));
                vector.add(resultSet.getString(3));
                vector.add(resultSet.getString(4));
                vector.add(resultSet.getString(5));
                vector.add(String.valueOf(resultSet.getInt(6)));
                vector.add(resultSet.getString(7));
                vector.add(resultSet.getString(8));
                vector.add(resultSet.getString(9));
                vector.add(resultSet.getString(10));
                vector.add(resultSet.getString(11));
                vectors.add(vector);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectMsql.releaseResources(resultSet, statement, connection);
        }
        return vectors;
    }
    public ArrayList<Teacher> findTeacherByJobImp(String job){
        ArrayList<Teacher> arrayList = new ArrayList<>();
        String sql = "select * from signdb.t_teacher" +
                " where teacher_major = "+ "'"+ job+"'";
        try {
            connection = ConnectMsql.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            while (resultSet.next()){
                Teacher teacher = new Teacher();
                teacher.setTeacher_id(resultSet.getInt(1));
                teacher.setTeacher_account(resultSet.getInt(2));
                teacher.setTeacher_password(resultSet.getString(3));
                teacher.setTeacher_name(resultSet.getString(4));
                teacher.setTeacher_sex(resultSet.getString(5));
                teacher.setTeacher_age(resultSet.getInt(6));
                teacher.setTeacher_job(resultSet.getString(7));
                teacher.setTeacher_phone(resultSet.getString(8));
                teacher.setTeacher_phoneex(resultSet.getString(9));
                teacher.setTeacher_emile(resultSet.getString(10));
                teacher.setTeacher_extra(resultSet.getString(11));
                arrayList.add(teacher);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return arrayList;
    }
    public int updateTeacherInfo(Teacher teacher){
        int i = 0;
        String sql = "update signdb.t_teacher set " +
                " teacher_account = "+teacher.getTeacher_account()+ ","+
                "teacher_password = "+"'"+teacher.getTeacher_password()+"'"+","+
                "teacher_name = "+"'"+teacher.getTeacher_name()+"'"+","+
                "teacher_age = "+teacher.getTeacher_age()+","+
                "teacher_major = "+"'"+teacher.getTeacher_job()+"'"+","+
                "teacher_phone = "+"'"+teacher.getTeacher_phone()+"'"+","+
                "teacher_phoneex = "+"'"+teacher.getTeacher_phoneex()+"'"+","+
                "teacher_emile = "+"'"+teacher.getTeacher_emile()+"'"+","+
                "teacher_extra = "+"'"+teacher.getTeacher_extra()+"'"+
                " where teacher_id = "+teacher.getTeacher_id();
        try {
            connection = ConnectMsql.getConnection();
            statement = connection.createStatement();
            i = statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  i;
    }
    public int insertTeacherInfo(Teacher teacher){
        int i = 0;
        String sql = "insert into signdb.t_teacher(teacher_account, teacher_name, teacher_sex, teacher_age," +
                " teacher_major, teacher_phone, teacher_phoneex, teacher_emile,teacher_extra) values " +
                "("+teacher.getTeacher_account()+","+"'"+teacher.getTeacher_name()+"'"+","+"'"+
                teacher.getTeacher_sex()+"'"+","+teacher.getTeacher_age()+","+"'"+teacher.getTeacher_job()+"'"+","+"'"+
                teacher.getTeacher_phone()+"'"+","+"'"+teacher.getTeacher_phoneex()+"'"+","+"'"+teacher.getTeacher_emile()+"'"+","+"'"+
                teacher.getTeacher_extra()+"'"+")";
        System.out.println(sql);
        try {
            connection = ConnectMsql.getConnection();
            statement = connection.createStatement();
            i = statement.executeUpdate(sql);
        } catch (SQLException e) {
            i=0;
        }
        return i;
    }
    public int deleteTeacherInfo(int account){
        int i = 0;
        String sql= "delete from signdb.t_teacher" +
                " where teacher_account = "+account;
        try {
            connection = ConnectMsql.getConnection();
            statement = connection.createStatement();
            i = statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            ConnectMsql.releaseResources(resultSet,statement,connection);
        }
        return i;
    }
}
