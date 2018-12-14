package org.ncu.dao;

public interface ImgSignDao {
    int findCountImgIndexByKey(String temp_img_key);
    String findImgSignedPeo(String temp_img_key);
    void ImgSignIn(String temp_img_sign_people, String temp_img_key);
}
