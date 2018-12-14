package org.ncu.dao;

import org.ncu.pojo.ClassCurse;
import org.ncu.pojo.Teacher;

import java.util.ArrayList;

public interface ClassCurseDao {
    public ArrayList<ClassCurse> findCurseInfo(String major,String classname);

    public int queryClassCurseCount(ClassCurse classCurse);
}
