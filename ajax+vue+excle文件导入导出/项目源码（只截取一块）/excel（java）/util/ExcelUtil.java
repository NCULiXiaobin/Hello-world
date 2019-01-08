package com.szkingdom.kamm.core.excel.util;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.szkingdom.kamm.core.excel.exception.ValidateException;
import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * ***************************************************************************
 * Copyright (C) 1998-至今, SHENZHEN KINGDOM Co., Ltd.
 * <p/>
 * All Rights Reserved.
 * <p/>
 * ===========================================================================
 * <p/>
 * 文 件 名：ExcelUtil.java
 * 模块名称：
 * 功能说明：
 * 作    者：heshifeng@szkingdom.com
 * 创建日期：2015-06-11 16:27
 * 版 本 号：1.0
 * 修改历史：
 * <p/>
 * 修改日期                  姓名                         内容
 * ---------------------------------------------------------------------------
 * 2015-06-11 16:27     heshifeng@szkingdom.com          初始版本
 * /*****************************************************************************
 */
public class ExcelUtil {
    public static String validateExcelCell(Object cellValue, String type, String format, String regexp, String cellType) throws ValidateException {
        String retStr = null;
        if (StringUtils.isEmpty((String) cellValue)) return "";
        if ("string".equals(type)) {//字符串
            if (!"".equals(regexp) && !cellValue.toString().matches(regexp)) {
                throw new ValidateException("数据格式不正确【要求格式:" + regexp + ", 实际值:" + cellType + ":" + cellValue + "】");
            } else {
                retStr = cellValue.toString();
            }
        } else if ("number".equals(type)) {//数字
            if (cellValue.toString().matches("\\d+\\.{0,1}\\d*")) {
                DecimalFormat decimalFormat = new DecimalFormat(format);
                try {
                    decimalFormat.parse(cellValue.toString());
                    retStr = decimalFormat.format(new BigDecimal(cellValue.toString()));
                } catch (ParseException e) {
                    throw new ValidateException("数字格式不正确【要求格式：" + format + ", 实际值：" + cellType + ":" + cellValue + "】");
                }
            } else {
                throw new ValidateException("数字格式不正确【要求格式：" + format + ", 实际值：" + cellType + ":" + cellValue + "】");
            }
        } else if ("date".equals(type)) {//日期
            if (cellValue != null) {
                DateFormat dateFormat = new SimpleDateFormat(format);
                try {
                    retStr = dateFormat.format(cellValue);
                } catch (Exception e) {
                    throw new ValidateException("日期格式不正确【要求格式：" + format + ", 实际值：" + cellType + ":" + cellValue + "，该单元格必需为日期格式！】");
                }
            }
        }
        return retStr;
    }

    public static String formatExcelCell(String cellValue, String type, String format) {
        try {
            if ("number".equals(type)) {
                DecimalFormat decimalFormat = new DecimalFormat(format);
                cellValue = decimalFormat.format(decimalFormat.parse(cellValue));
            }
            if ("date".equals(type)) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
                cellValue = simpleDateFormat.format(simpleDateFormat.parse(cellValue));
            }
            return cellValue;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

}
