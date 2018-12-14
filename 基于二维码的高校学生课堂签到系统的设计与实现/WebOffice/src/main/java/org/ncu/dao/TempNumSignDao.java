package org.ncu.dao;

import org.ncu.pojo.TempNumSign;

public interface TempNumSignDao {
    public void insertTempNumSign(String temp_index,String temp_num_index);
    public TempNumSign findByTempIndex(String temp_index);
    public void deleteTempNumSign(String temp_index);
}
