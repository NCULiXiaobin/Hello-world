package com.szkingdom.kamm.core.excel.config;

/**
 * ***************************************************************************
 * Copyright (C) 1998-至今, SHENZHEN KINGDOM Co., Ltd.
 * <p/>
 * All Rights Reserved.
 * <p/>
 * ===========================================================================
 * <p/>
 * 文 件 名：ColumnConfig.java
 * 模块名称：
 * 功能说明：
 * 作    者：xiongwei@szkingdom.com
 * 创建日期：2015-05-27 12:57
 * 版 本 号：1.0
 * 修改历史：
 * <p/>
 * 修改日期                  姓名                         内容
 * ---------------------------------------------------------------------------
 * 2015-05-27 12:57     xiongwei@szkingdom.com          初始版本
 * /*****************************************************************************
 */
public class ColumnConfig {
    /**
     * 列名
     */
    private String field;
    /**
     * 在Excel中的索引值
     */
    private String index;
    /**
     * 数据类型(date、 number)
     */
    private String type;
    /**
     * 格式化
     */
    private String format;
    /**
     * 数据格式验证(正则表达式)
     */
    private String regExp;
    /**
     * 必填项
     */
    private String require;
    /**
     * 字段长度
     */
    private String width;

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getRegExp() {
        return regExp;
    }

    public void setRegExp(String regExp) {
        this.regExp = regExp;
    }

    public String getRequire() {
        return require;
    }

    public void setRequire(String require) {
        this.require = require;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }
}
