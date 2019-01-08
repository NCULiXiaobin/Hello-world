package com.szkingdom.kamm.core.excel.convert;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * ***************************************************************************
 * Copyright (C) 1998-至今, SHENZHEN KINGDOM Co., Ltd.
 * <p/>
 * All Rights Reserved.
 * <p/>
 * ===========================================================================
 * <p/>
 * 文 件 名：ExcelConvert.java
 * 模块名称：
 * 功能说明：
 * 作    者：xiongwei@szkingdom.com
 * 创建日期：2015-05-28 09:49
 * 版 本 号：1.0
 * 修改历史：
 * <p/>
 * 修改日期                  姓名                         内容
 * ---------------------------------------------------------------------------
 * 2015-05-28 09:49     xiongwei@szkingdom.com          初始版本
 * /*****************************************************************************
 */
public class ExcelConvert {
    public static String dataConvert(Object obj) {
        String pattern = "yyyy-MM-dd";
        String resultString;
        if (obj != null) {
            if ((obj instanceof Date)) {
                Date date = (Date) obj;
                SimpleDateFormat sdf = new SimpleDateFormat(pattern);
                resultString = sdf.format(date);
            } else {
                if ((obj instanceof Long)) {
                    Long LongData = (Long) obj;
                    resultString = LongData.toString();
                } else {
                    if ((obj instanceof BigDecimal)) {
                        BigDecimal bigValue = (BigDecimal) obj;
                        resultString = bigValue.toString();
                    } else {
                        if ((obj instanceof Double)) {
                            Double doubleValue = (Double) obj;
                            if (doubleValue.doubleValue() % 1.0D == 0.0D) {
                                resultString = String.valueOf(doubleValue.longValue());
                            } else {
                                resultString = String.valueOf(doubleValue);
                            }
                        } else {
                            resultString = obj.toString();
                        }
                    }
                }
            }
        } else {
            resultString = "";
        }
        return resultString;
    }
}
