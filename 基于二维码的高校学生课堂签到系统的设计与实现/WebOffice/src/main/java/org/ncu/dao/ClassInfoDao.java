package org.ncu.dao;

import org.ncu.pojo.ClassInfo;

import java.util.ArrayList;

public interface ClassInfoDao {
    public ArrayList<ClassInfo> findClassByMajor(String major);
    public ClassInfo findClassByName(String major,String className);
}
