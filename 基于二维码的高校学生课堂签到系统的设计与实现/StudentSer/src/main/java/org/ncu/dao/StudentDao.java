package org.ncu.dao;

import org.ncu.pojo.Student;

import java.util.ArrayList;

public interface StudentDao {
    ArrayList<Student> findStudentByClass(String major, String className);
    int countStudentByClass(String major, String className);
    int countStudentByAccount(int account);
    int makeSureStudentPass(int account, String password);
    Student findStudentByAccAndPass(int account, String password);
}
