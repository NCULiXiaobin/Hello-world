package org.ncu.dao;

import org.apache.ibatis.annotations.Param;
import org.ncu.pojo.Sign;

import java.sql.Timestamp;
import java.util.ArrayList;

public interface SignDao {
    public void insertSignMes(Sign sign);
    public ArrayList<Sign> selectSignMes(String teacherName);
    public Sign selectMesByTime(@Param("teacherAccount")String teacherAccount, @Param("timestamp")String timestamp);
}
