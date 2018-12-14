package com.ncu.dao;

import com.ncu.pojo.Student;
import com.ncu.pojo.Teacher;
import com.ncu.service.ConnectMsql;

import java.sql.*;
import java.util.ArrayList;
import java.util.Vector;

public class StudentDao {
    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;
    private PreparedStatement preparedStatement;

    public ArrayList<Student> findAllStudent(){
        ArrayList<Student> list = new ArrayList<>();
        String sql = "select * from signdb.t_student";
        try {
            connection = ConnectMsql.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            while (resultSet.next()){
                Student student = new Student();
                student.setStudent_id(resultSet.getInt(1));
                student.setStudent_account(resultSet.getString(2));
                student.setStudent_password(resultSet.getString(3));
                student.setStudent_cardid(resultSet.getString(4));
                student.setStudent_name(resultSet.getString(5));
                student.setStudent_sex(resultSet.getString(6));
                student.setStudent_joinyear(resultSet.getString(7));
                student.setStudent_stuyear(resultSet.getInt(8));
                student.setStudent_major(resultSet.getString(9));
                student.setStudent_class(resultSet.getString(10));
                student.setStudent_phone(resultSet.getString(11));
                student.setStudent_emile(resultSet.getString(12));
                student.setStudent_adress(resultSet.getString(13));
                student.setStudent_extra(resultSet.getString(14));
                list.add(student);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            ConnectMsql.releaseResources(resultSet,statement,connection);
        }
        return list;
    }
    public Vector<Vector> findStudentByAccount(String account){
        Vector<Vector> vectors = new Vector<>();
        String sql = "select * from signdb.t_student" +
                " where student_account = "+ "'"+account+"'";
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
                vector.add(resultSet.getString(6));
                vector.add(resultSet.getString(7));
                vector.add(String.valueOf(resultSet.getInt(8)));
                vector.add(resultSet.getString(9));
                vector.add(resultSet.getString(10));
                vector.add(resultSet.getString(11));
                vector.add(resultSet.getString(12));
                vector.add(resultSet.getString(13));
                vector.add(resultSet.getString(14));
                vectors.add(vector);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            ConnectMsql.releaseResources(resultSet,statement,connection);
        }
        return vectors;
    }
    public ArrayList<Student> findStudentByAccountImp(String  account){
        ArrayList<Student> list = new ArrayList<>();
        String sql = "select * from signdb.t_student" +
                " where student_account = "+"'" +account+"'";
        try {
            connection = ConnectMsql.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            while (resultSet.next()){
                Student student = new Student();
                student.setStudent_id(resultSet.getInt(1));
                student.setStudent_account(resultSet.getString(2));
                student.setStudent_password(resultSet.getString(3));
                student.setStudent_cardid(resultSet.getString(4));
                student.setStudent_name(resultSet.getString(5));
                student.setStudent_sex(resultSet.getString(6));
                student.setStudent_joinyear(resultSet.getString(7));
                student.setStudent_stuyear(resultSet.getInt(8));
                student.setStudent_major(resultSet.getString(9));
                student.setStudent_class(resultSet.getString(10));
                student.setStudent_phone(resultSet.getString(11));
                student.setStudent_emile(resultSet.getString(12));
                student.setStudent_adress(resultSet.getString(13));
                student.setStudent_extra(resultSet.getString(14));
                list.add(student);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            ConnectMsql.releaseResources(resultSet,statement,connection);
        }
        return list;
    }
    public Vector<Vector> findStudentByName(String name){
        Vector<Vector> vectors = new Vector<>();
        String sql = "select * from signdb.t_student" +
                " where student_name = "+"'"+ name+"'";
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
                vector.add(resultSet.getString(6));
                vector.add(resultSet.getString(7));
                vector.add(String.valueOf(resultSet.getInt(8)));
                vector.add(resultSet.getString(9));
                vector.add(resultSet.getString(10));
                vector.add(resultSet.getString(11));
                vector.add(resultSet.getString(12));
                vector.add(resultSet.getString(13));
                vector.add(resultSet.getString(14));
                vectors.add(vector);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            ConnectMsql.releaseResources(resultSet,statement,connection);
        }
        return vectors;
    }
    public ArrayList<Student> findStudentByNameImp(String name){
        ArrayList<Student> list = new ArrayList<>();
        String sql = "select * from signdb.t_student" +
                " where student_name = "+ "'"+name+"'";
        try {
            connection = ConnectMsql.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            while (resultSet.next()){
                Student student = new Student();
                student.setStudent_id(resultSet.getInt(1));
                student.setStudent_account(resultSet.getString(2));
                student.setStudent_password(resultSet.getString(3));
                student.setStudent_cardid(resultSet.getString(4));
                student.setStudent_name(resultSet.getString(5));
                student.setStudent_sex(resultSet.getString(6));
                student.setStudent_joinyear(resultSet.getString(7));
                student.setStudent_stuyear(resultSet.getInt(8));
                student.setStudent_major(resultSet.getString(9));
                student.setStudent_class(resultSet.getString(10));
                student.setStudent_phone(resultSet.getString(11));
                student.setStudent_emile(resultSet.getString(12));
                student.setStudent_adress(resultSet.getString(13));
                student.setStudent_extra(resultSet.getString(14));
                list.add(student);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            ConnectMsql.releaseResources(resultSet,statement,connection);
        }
        return list;
    }
    public Vector<Vector> findStudentByClass(String major,String className){
        Vector<Vector> vectors = new Vector<>();
        String sql = "select * from signdb.t_student" +
                " where student_class = "+ "'"+className+"'"+"and student_major = "+"'"+major+"'";
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
                vector.add(resultSet.getString(6));
                vector.add(resultSet.getString(7));
                vector.add(String.valueOf(resultSet.getInt(8)));
                vector.add(resultSet.getString(9));
                vector.add(resultSet.getString(10));
                vector.add(resultSet.getString(11));
                vector.add(resultSet.getString(12));
                vector.add(resultSet.getString(13));
                vector.add(resultSet.getString(14));
                vectors.add(vector);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            ConnectMsql.releaseResources(resultSet,statement,connection);
        }
        return vectors;
    }
    public ArrayList<Student> findStudentByClassImp(String major,String className){
        ArrayList<Student> list = new ArrayList<>();
        String sql = "select * from signdb.t_student" +
                " where student_class = "+ "'"+className+"'"+"and student_major = "+"'"+major+"'";
        try {
            connection = ConnectMsql.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            while (resultSet.next()){
                Student student = new Student();
                student.setStudent_id(resultSet.getInt(1));
                student.setStudent_account(resultSet.getString(2));
                student.setStudent_password(resultSet.getString(3));
                student.setStudent_cardid(resultSet.getString(4));
                student.setStudent_name(resultSet.getString(5));
                student.setStudent_sex(resultSet.getString(6));
                student.setStudent_joinyear(resultSet.getString(7));
                student.setStudent_stuyear(resultSet.getInt(8));
                student.setStudent_major(resultSet.getString(9));
                student.setStudent_class(resultSet.getString(10));
                student.setStudent_phone(resultSet.getString(11));
                student.setStudent_emile(resultSet.getString(12));
                student.setStudent_adress(resultSet.getString(13));
                student.setStudent_extra(resultSet.getString(14));
                list.add(student);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            ConnectMsql.releaseResources(resultSet,statement,connection);
        }
        return list;
    }
    public int deleteStudentInfo(int account){
        int i = 0;
        String sql = "DELETE FROM signdb.t_student WHERE student_account = "+"'"+account+"'";
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
    public int insertStudentInfo(Student student){
        int i = 0;
        String sql = "insert into signdb.t_student(student_account, student_cardid, student_name, student_sex, " +
                "student_joinyear, student_stuyear, student_major, student_class, student_phone, student_emile, " +
                "student_adress,student_extra) values " +
                "("+"'"+student.getStudent_account()+"'"+","+"'"+student.getStudent_cardid()+"'"+","+"'"+
                student.getStudent_name()+"'"+","+"'"+student.getStudent_sex()+"'"+","+"'"+student.getStudent_joinyear()+"'"+"," +
                student.getStudent_stuyear()+","+"'"+ student.getStudent_major()+"'"+","+"'"+student.getStudent_class()+"'"+","+"'"+
                student.getStudent_phone()+"'"+","+"'"+ student.getStudent_emile()+"'"+","+"'"+student.getStudent_adress()+"'"+
                ","+"'"+student.getStudent_extra()+"'"+")";
        try {
            connection = ConnectMsql.getConnection();
            statement = connection.createStatement();
            i = statement.executeUpdate(sql);
        } catch (SQLException e) {
            i=0;
        }
        return i;
    }
    public int updateStudentInfo(Student student){
        int i = 0;
        String sql = "update signdb.t_student set " +
                " student_account = "+"'"+student.getStudent_account()+"'"+ ","+
                "student_password = "+"'"+student.getStudent_password()+"'"+","+
                "student_cardid = "+"'"+student.getStudent_cardid()+"'"+","+
                "student_name = "+"'"+student.getStudent_name()+"'"+","+
                "student_sex = "+"'"+student.getStudent_sex()+"'"+","+
                "student_joinyear = "+"'"+student.getStudent_joinyear()+"'"+","+
                "student_stuyear = "+student.getStudent_stuyear()+","+
                "student_major = "+"'"+student.getStudent_major()+"'"+","+
                "student_class = "+"'"+student.getStudent_class()+"'"+","+
                "student_phone = "+"'"+student.getStudent_phone()+"'"+","+
                "student_emile = "+"'"+student.getStudent_emile()+"'"+","+
                "student_adress = "+"'"+student.getStudent_adress()+"'"+","+
                "student_extra = "+"'"+student.getStudent_extra()+"'"+
                " where student_id = "+student.getStudent_id();
        try {
            connection = ConnectMsql.getConnection();
            statement = connection.createStatement();
            i = statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  i;
    }
}
