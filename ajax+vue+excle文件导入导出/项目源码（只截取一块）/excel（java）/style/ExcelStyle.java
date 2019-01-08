package com.szkingdom.kamm.core.excel.style;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * ***************************************************************************
 * Copyright (C) 1998-至今, SHENZHEN KINGDOM Co., Ltd.
 * <p/>
 * All Rights Reserved.
 * <p/>
 * ===========================================================================
 * <p/>
 * 文 件 名：ExcelStyle.js
 * 模块名称：
 * 功能说明：
 * 作    者：xiongwei@szkingdom.com
 * 创建日期：2015-05-28 09:48
 * 版 本 号：1.0
 * 修改历史：
 * <p/>
 * 修改日期                  姓名                         内容
 * ---------------------------------------------------------------------------
 * 2015-05-28 09:48     xiongwei@szkingdom.com          初始版本
 * /*****************************************************************************
 */
public class ExcelStyle {
    public static HSSFCellStyle getStyle1(HSSFWorkbook workbook)
    {
        HSSFCellStyle style = workbook.createCellStyle();



        style.setFillForegroundColor((short)40);
        style.setFillPattern((short)1);
        style.setBorderBottom((short)1);
        style.setBorderLeft((short)1);
        style.setBorderRight((short)1);
        style.setBorderTop((short)1);
        style.setAlignment((short)2);

        HSSFFont font = workbook.createFont();
        font.setColor((short)20);
        font.setFontHeightInPoints((short)12);
        font.setBoldweight((short)700);

        style.setFont(font);
        return style;
    }

    public static HSSFCellStyle getStyle2(HSSFWorkbook workbook)
    {
        HSSFCellStyle style = workbook.createCellStyle();
        style.setDataFormat(HSSFDataFormat.getBuiltinFormat("@"));
        style.setFillForegroundColor((short)43);
        style.setFillPattern((short)1);
        style.setBorderBottom((short)1);
        style.setBorderLeft((short)1);
        style.setBorderRight((short)1);
        style.setBorderTop((short)1);
        style.setAlignment((short)2);
        style.setVerticalAlignment((short)1);

        HSSFFont font = workbook.createFont();
        font.setBoldweight((short)400);

        style.setFont(font);
        return style;
    }

    public static HSSFCellStyle getStyle3(HSSFWorkbook workbook)
    {
        HSSFCellStyle style = workbook.createCellStyle();
        HSSFFont font = workbook.createFont();
        font.setColor((short)10);
        font.setFontHeightInPoints((short)12);
        font.setBoldweight((short)700);

        style.setFont(font);
        return style;
    }

    public static HSSFCellStyle getStyle4(HSSFWorkbook workbook)
    {
        HSSFCellStyle style = workbook.createCellStyle();
        style.setFillForegroundColor((short)10);
        style.setFillPattern((short)1);
        style.setBorderBottom((short)1);
        style.setBorderLeft((short)1);
        style.setBorderRight((short)1);
        style.setBorderTop((short)1);
        style.setAlignment((short)3);
        return style;
    }

    public static HSSFCellStyle getStyle5(HSSFWorkbook workbook)
    {
        HSSFCellStyle style = workbook.createCellStyle();
        HSSFFont font = workbook.createFont();
        font.setColor((short)10);
        font.setFontHeightInPoints((short)10);
        font.setBoldweight((short)700);

        style.setFont(font);
        return style;
    }

    public static HSSFCellStyle getStyle6(HSSFWorkbook workbook)
    {
        HSSFCellStyle style = workbook.createCellStyle();

        style.setFillBackgroundColor((short)17);
        HSSFFont font = workbook.createFont();
        font.setColor((short)8);

        style.setFont(font);
        return style;
    }
}
