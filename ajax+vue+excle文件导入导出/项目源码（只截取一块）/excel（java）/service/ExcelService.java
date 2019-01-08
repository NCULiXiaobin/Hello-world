package com.szkingdom.kamm.core.excel.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * ***************************************************************************
 * <p/>
 * 配置管理平台(KFAMS) V1.0
 * <p/>
 * Copyright (C) 1998-至今, SHENZHEN KINGDOM Co., Ltd.
 * <p/>
 * All Rights Reserved.
 * <p/>
 * ===========================================================================
 * <p/>
 * 文 件 名：ExcelService.js
 * 模块名称：
 * 功能说明：
 * 作    者：xiongwei@szkingdom.com
 * 创建日期：2015-05-23 14:42
 * 版 本 号：1.0
 * 修改历史：
 * <p/>
 * 修改日期                  姓名                         内容
 * ---------------------------------------------------------------------------
 * 2015-05-23 14:42     xiongwei@szkingdom.com          初始版本
 * /*****************************************************************************
 */
public interface ExcelService {
    void importExcel(Map params, File file, HttpServletRequest request, HttpServletResponse response) throws IOException;

    void exportExcel(Map params, HttpServletRequest request, HttpServletResponse response);
}
