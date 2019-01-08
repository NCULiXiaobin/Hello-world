package com.szkingdom.kamm.core.excel.config;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * ***************************************************************************
 * Copyright (C) 1998-至今, SHENZHEN KINGDOM Co., Ltd.
 * <p/>
 * All Rights Reserved.
 * <p/>
 * ===========================================================================
 * <p/>
 * 文 件 名：ExcelConfig.js
 * 模块名称：
 * 功能说明：
 * 作    者：xiongwei@szkingdom.com
 * 创建日期：2015-05-23 14:08
 * 版 本 号：1.0
 * 修改历史：
 * <p/>
 * 修改日期                  姓名                         内容
 * ---------------------------------------------------------------------------
 * 2015-05-23 14:08     xiongwei@szkingdom.com          初始版本
 * /*****************************************************************************
 */
public class ExcelConfig {

    private String lbmCode;

    private int dataStartRow;

    private String name;

    private String template;

    private Map<String, ColumnConfig> columnsMap = new HashMap<String, ColumnConfig>();

    private String model;

    private String tableName;

    private boolean validFlag = true;

    private String vliadMsg = "";

    public String getLbmCode() {
        return lbmCode;
    }

    public void setLbmCode(String lbmCode) {
        this.lbmCode = lbmCode;
    }

    public int getDataStartRow() {
        return dataStartRow;
    }

    public void setDataStartRow(int dataStartRow) {
        this.dataStartRow = dataStartRow;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public Map<String, ColumnConfig> getColumnsMap() {
        return columnsMap;
    }

    public void setColumnsMap(Map<String, ColumnConfig> columnsMap) {
        this.columnsMap = columnsMap;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public boolean isValidFlag() {
        return validFlag;
    }

    public void setValidFlag(boolean validFlag) {
        this.validFlag = validFlag;
    }

    public String getVliadMsg() {
        return vliadMsg;
    }

    public void setVliadMsg(String vliadMsg) {
        this.vliadMsg = vliadMsg;
    }
}
