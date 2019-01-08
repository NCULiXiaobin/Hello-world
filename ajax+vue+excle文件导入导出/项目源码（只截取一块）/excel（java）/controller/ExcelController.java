package com.szkingdom.kamm.core.excel.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.szkingdom.jros.core.context.property.PropertyPlaceholder;
import com.szkingdom.jros.core.util.ContextUtil;
import com.szkingdom.kamm.core.excel.config.ColumnConfig;
import com.szkingdom.kamm.core.excel.config.ExcelConfig;
import com.szkingdom.kamm.core.excel.config.ExcelConfigCache;
import com.szkingdom.kamm.core.excel.convert.ExcelConvert;
import com.szkingdom.kamm.core.excel.service.ExcelService;
import com.szkingdom.kamm.core.excel.service.impl.ExcelServiceImpl;
import com.szkingdom.kamm.core.excel.style.ExcelStyle;
import com.szkingdom.kamm.core.excel.util.ExcelUtil;
import com.szkingdom.kamm.core.excel.util.LogUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * ***************************************************************************
 * <p/>
 * <p/>
 * Copyright (C) 1998-至今, SHENZHEN KINGDOM Co., Ltd.
 * <p/>
 * All Rights Reserved.
 * <p/>
 * ===========================================================================
 * <p/>
 * 文 件 名：ExcelController.java
 * 模块名称：
 * 功能说明：
 * 作    者：xiongwei@szkingdom.com
 * 创建日期：2015-05-23 13:40
 * 版 本 号：1.0
 * 修改历史：
 * <p/>
 * 修改日期                  姓名                         内容
 * ---------------------------------------------------------------------------
 * 2015-05-23 13:40     xiongwei@szkingdom.com          初始版本
 * /*****************************************************************************
 */
@Controller
public class ExcelController {
    private Logger log = LoggerFactory.getLogger(getClass());

    @RequestMapping({"/excel.up"})
    public void excelImport(HttpServletRequest request, HttpServletResponse response){

        try {
            request.setCharacterEncoding("UTF-8");
            response.setContentType("text/html; charset=utf-8");
            Map<String, Object> parameters = getParameters(request);

            Object fileUplodPathConfig = PropertyPlaceholder.getCtxProperties().get("file.uploadpath");
            String uploadPath = StringUtils.isNotEmpty((String) fileUplodPathConfig)?fileUplodPathConfig.toString():"";
            File dir = new File(uploadPath);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest)request;
            CommonsMultipartFile cmf = (CommonsMultipartFile)multipartRequest.getFile("file");
            String ofn = cmf.getOriginalFilename();
            String fileName = FilenameUtils.getName(ofn);
            File file = new File(dir, fileName);
            cmf.getFileItem().write(file);

            ExcelService excelService = (ExcelService) ContextUtil.getBean("excelService");
            excelService.importExcel(parameters, file, request, response);
        } catch (Exception e) {
            e.printStackTrace();
            Map<String, Object> result = new HashMap<String, Object>();
            result.put("code","2");
            result.put("msg","上传文件出错:"+e.getMessage());
            result.put("percent","0");
            //LogUtil.sendMsg(result, response);
        }
    }
    /**
     * 解析请求中的参数
     * @param request
     * @return
     */
    private Map<String, Object> getParameters(HttpServletRequest request){
        Map<String, Object> parameters = new HashMap<String, Object>();

        Enumeration parameterNames =request.getParameterNames();

        while (parameterNames.hasMoreElements()) {
            String paramName = (String) parameterNames.nextElement();
            String paramValue = request.getParameter(paramName);
            parameters.put(paramName, paramValue);
        }
        return parameters;
    }
}
