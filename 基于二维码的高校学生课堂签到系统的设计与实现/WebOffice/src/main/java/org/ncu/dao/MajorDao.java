package org.ncu.dao;

import org.ncu.pojo.Major;

import java.util.ArrayList;

public interface MajorDao {
    public ArrayList<Major> findAllMajor();
    public Major findMajorByName(String majorName);
}
