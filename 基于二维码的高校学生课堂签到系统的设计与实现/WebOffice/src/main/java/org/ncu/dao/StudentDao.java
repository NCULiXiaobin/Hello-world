package org.ncu.dao;

import org.ncu.pojo.Student;

import java.util.ArrayList;

public interface StudentDao {
    public ArrayList<Student> findStudentByClass(String major,String className);
    public int countStudentByClass(String major,String className);
    public int countStudentByAccount(int account);
    public int makeSureStudentPass(int account,String password);
    public Student findStudentByAccAndPass(int account,String password);
}
