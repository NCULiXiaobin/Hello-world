package org.ncu.dao;

import org.ncu.pojo.Teacher;

public interface TeacherDao {
    public int getTeacherCount(int account);
    public Teacher getTeacherMes(int account,String password);
    public int updateMyInfo(Teacher teacher);
    public int makeSurePassword(int account,String password);
    public Teacher findTeacherByAccount(int teacherAccount);
}
