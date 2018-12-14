package org.ncu.dao;


public interface NumSignDao {
    int findTempNumSign(String temp_index);
    void numSignIn(String temp_sign_people, String temp_index);
    String findNumSignedPeo(String temp_index);
}
