package com.szkingdom.kamm.core.excel.config.service.impl;

import com.szkingdom.kamm.core.excel.config.ColumnConfig;
import com.szkingdom.kamm.core.excel.config.ExcelConfig;
import com.szkingdom.kamm.core.excel.config.ExcelConfigCache;
import com.szkingdom.kamm.core.excel.config.service.ExcelConfigService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;
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
 * 文 件 名：ExcelConfigServiceImp.js
 * 模块名称：
 * 功能说明：
 * 作    者：xiongwei@szkingdom.com
 * 创建日期：2015-05-23 14:03
 * 版 本 号：1.0
 * 修改历史：
 * <p/>
 * 修改日期                  姓名                         内容
 * ---------------------------------------------------------------------------
 * 2015-05-23 14:03     xiongwei@szkingdom.com          初始版本
 * /*****************************************************************************
 */
public class ExcelConfigServiceImpl  implements ExcelConfigService{
    private Logger log = LoggerFactory.getLogger(getClass());
    private Resource resource;

    public Map<String, Object> getDacPublicParams() {
        return dacPublicParams;
    }

    public void setDacPublicParams(Map<String, Object> dacPublicParams) {
        this.dacPublicParams = dacPublicParams;
    }

    private Map<String, Object> dacPublicParams;

    public Resource getResource() {
        return resource;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }



    @Override
    public void readConfig() {
        InputStream is = null;
        Document doc=null;
        try{
            log.info("解析Excel导入导出配置...");
            DocumentBuilderFactory dbf=DocumentBuilderFactory.newInstance();
            DocumentBuilder db=dbf.newDocumentBuilder();
            is = resource.getInputStream();
            doc=db.parse(is);
            Element root=doc.getDocumentElement();//取得根目錄
            NodeList excels = root.getElementsByTagName("excel");//取得公共參數
            for(int i=0, len=excels.getLength(); i<len; i++){
                Element excel=(Element)excels.item(i);
                ExcelConfig excelConfig = new ExcelConfig();
                String excelId = excel.getAttribute("id");
                //导入 or 导出
                String type = excel.getAttribute("type");
                if(StringUtils.isEmpty(type)) {
                    excelConfig.setValidFlag(false);
                    excelConfig.setVliadMsg("导入导出类型未配置！");
                }
                //任务名称
                String name = excel.getAttribute("name");
                excelConfig.setName(name);
                //数据开始行
                String dataStartRow = excel.getAttribute("data_start_row");
                if(StringUtils.isEmpty(dataStartRow)) {
                    excelConfig.setDataStartRow(1);
                }else{
                    excelConfig.setDataStartRow(Integer.parseInt(dataStartRow));
                }
                //功能号
                String lbmCode = excel.getAttribute("lbmcode");
                if(StringUtils.isEmpty(lbmCode)) {
                    excelConfig.setValidFlag(false);
                    excelConfig.setVliadMsg("导入导出功能号未配置！");
                }
                excelConfig.setLbmCode(lbmCode);
                //导出模板
                String template = excel.getAttribute("template");
                if("2".equals(type) && StringUtils.isEmpty(template)) {
                    excelConfig.setValidFlag(false);
                    excelConfig.setVliadMsg("导出模板未配置！");
                }else{
                    excelConfig.setTemplate(StringUtils.isEmpty(template)?"":template);
                }
                //导入模式
                String model = excel.getAttribute("model");
                if(StringUtils.isEmpty(model)) {
                    excelConfig.setValidFlag(false);
                    excelConfig.setVliadMsg("导入模式未配置！");
                }else{
                    excelConfig.setModel(StringUtils.isEmpty(model)?"1":model);
                }
                //导入临时表
                String tableName = excel.getAttribute("tablename");
                if("1".equals(type) && "2".equals(model) && StringUtils.isEmpty(tableName)) {
                    excelConfig.setValidFlag(false);
                    excelConfig.setVliadMsg("导入临时表未配置！");
                }else{
                    excelConfig.setTableName(StringUtils.isEmpty(tableName)?"":tableName);
                }

                NodeList columnNodes =  excel.getElementsByTagName("col");
                for(int j=0, size= columnNodes.getLength(); j<size; j++){
                    ColumnConfig columnConfig = new ColumnConfig();
                    Element column = (Element)columnNodes.item(j);
                    String columnName = column.getAttribute("field");
                    columnConfig.setField(columnName);

                    String index = column.getAttribute("index");
                    columnConfig.setIndex(index);

                    String dataType = column.getAttribute("type");
                    columnConfig.setType(StringUtils.isEmpty(dataType)?"string":dataType);

                    String dataFormat = column.getAttribute("format");
                    columnConfig.setFormat(StringUtils.isEmpty(dataFormat)?"":dataFormat);

                    String regExp = column.getAttribute("regexp");
                    columnConfig.setRegExp(StringUtils.isEmpty(regExp)?"":regExp);

                    String require = column.getAttribute("require");
                    columnConfig.setRequire(StringUtils.isEmpty(require)?"":require);

                    String width = column.getAttribute("width");
                    columnConfig.setWidth(StringUtils.isEmpty(width)?"0": width);

                    excelConfig.getColumnsMap().put(index, columnConfig);
                }
                if("1".equals(type)){//导入
                    ExcelConfigCache.addImportConfig(excelId, excelConfig);
                }else{//导出
                    ExcelConfigCache.addExportConfig(excelId, excelConfig);
                }
            }
            log.info("解析Excel导入导出配置完成");
        }catch (Exception e){
            throw new RuntimeException("解析Excel导入导出配置失败");
        }
    }
}
