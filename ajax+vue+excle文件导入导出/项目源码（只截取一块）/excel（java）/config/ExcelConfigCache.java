package com.szkingdom.kamm.core.excel.config;

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
 * 文 件 名：ExcelConfigCache.java
 * 模块名称：
 * 功能说明：
 * 作    者：xiongwei@szkingdom.com
 * 创建日期：2015-05-23 14:12
 * 版 本 号：1.0
 * 修改历史：
 * <p/>
 * 修改日期                  姓名                         内容
 * ---------------------------------------------------------------------------
 * 2015-05-23 14:12     xiongwei@szkingdom.com          初始版本
 * /*****************************************************************************
 */
public class ExcelConfigCache {
    public static Map<String, ExcelConfig> importConfig = new HashMap<String, ExcelConfig>();

    public static Map<String, ExcelConfig> exportConfig = new HashMap<String, ExcelConfig>();

    public static ExcelConfig getImportConfig(String excelId){
        return importConfig.get(excelId);
    }

    public static ExcelConfig getExportConfig(String excelId){
        return exportConfig.get(excelId);
    }

    public static void addImportConfig(String excelId, ExcelConfig excelConfig){
        importConfig.put(excelId,excelConfig);
    }

    public static void addExportConfig(String excelId, ExcelConfig excelConfig){
        exportConfig.put(excelId,excelConfig);
    }

    public static void clearImportConfig(String excelId){
        importConfig.clear();
    }

    public static void clearExportConfig(String excelId){
        exportConfig.clear();
    }
}
