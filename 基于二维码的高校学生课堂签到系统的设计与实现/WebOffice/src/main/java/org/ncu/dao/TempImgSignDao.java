package org.ncu.dao;

import net.sf.json.JSON;
import org.ncu.pojo.TempImgSign;

public interface TempImgSignDao {
    public void insertTempImgSign(TempImgSign tempImgSign);
    public void deleteTempImgSign(String key1);
    public TempImgSign findImgIndexByKey(String temp_img_key);
}
