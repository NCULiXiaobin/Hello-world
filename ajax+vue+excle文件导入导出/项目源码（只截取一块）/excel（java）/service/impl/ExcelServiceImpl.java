package com.szkingdom.kamm.core.excel.service.impl;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.org.apache.xpath.internal.operations.Bool;
import com.szkingdom.jros.core.engine.common.AuthParam;
import com.szkingdom.jros.core.engine.handler.LbmHandlerAbstract;
import com.szkingdom.kamm.core.excel.config.ColumnConfig;
import com.szkingdom.kamm.core.excel.config.ExcelConfig;
import com.szkingdom.kamm.core.excel.config.ExcelConfigCache;
import com.szkingdom.kamm.core.excel.exception.ValidateException;
import com.szkingdom.kamm.core.excel.service.ExcelService;
import com.szkingdom.kamm.core.excel.util.ExcelUtil;
import com.szkingdom.kamm.core.excel.util.LogUtil;
import com.szkingdom.jros.core.security.common.CommonParam;
import com.szkingdom.jros.core.service.lbm.LbmService;
import org.apache.commons.lang.StringUtils;
import com.szkingdom.kcbpcli.LbmResult;
import com.szkingdom.kcbpcli.LbmResultSet;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.text.*;
import java.util.*;

/**
 * ***************************************************************************
 * Copyright (C) 1998-至今, SHENZHEN KINGDOM Co., Ltd.
 * <p/>
 * All Rights Reserved.
 * <p/>
 * ===========================================================================
 * <p/>
 * 文 件 名：ExcelServiceImpl.java
 * 模块名称：
 * 功能说明：
 * 作    者：xiongwei@szkingdom.com
 * 创建日期：2015-05-23 14:45
 * 版 本 号：1.0
 * 修改历史：
 * <p/>
 * 修改日期                  姓名                         内容
 * ---------------------------------------------------------------------------
 * 2015-05-23 14:45     xiongwei@szkingdom.com          初始版本
 * /*****************************************************************************
 */
@Service(value="excelService")
public class ExcelServiceImpl extends LbmHandlerAbstract implements ExcelService {
    private Logger log = LoggerFactory.getLogger(getClass());

    private JdbcTemplate jdbcTemplate;

    @Resource(name = "lbmDefaultService")
    private LbmService lbmService;

    @Resource(name = "lbmPublicParams")
    private Map<String, Object> publicParams;

    private static String LOG_INFO  = "0";
    private static String LOG_WARN  = "1";
    private static String LOG_ERROR = "2";

    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
    private static DecimalFormat df = new DecimalFormat("0");// 格式化 number String 字符
    private static DecimalFormat nf = new DecimalFormat();// 格式化数字

    @Override
    public void importExcel(Map params, File file, HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("code",LOG_INFO);
        result.put("msg","开始解析文件...");
        result.put("percent","0");
//        LogUtil.sendMsg(result, response);

        boolean succFlag = true;
        long begintime = System.currentTimeMillis();
        long endtime;

        ExcelConfig excelConfig = ExcelConfigCache.getImportConfig((String)params.get("excelid"));

        if(excelConfig == null){
            result.put("code",LOG_ERROR);
            result.put("msg","模板转换参数未配置");
            result.put("percent", "0");
            //LogUtil.sendMsg(result, response);
            succFlag = false;
        }else if(!excelConfig.isValidFlag()){
            result.put("code",LOG_ERROR);
            result.put("msg",excelConfig.getVliadMsg());
            result.put("percent", "0");
            //LogUtil.sendMsg(result, response);
            succFlag = false;
        }
        if(succFlag){
            String model = excelConfig.getModel();
            if("1".equals(model)){
                succFlag = ImportMode1(params, file, request, response, excelConfig);
            }else if("2".equals(model)){
                succFlag = ImportMode2(params, file, request, response, excelConfig);
            }else{
                result.put("code",LOG_ERROR);
                result.put("msg","未匹配的导入模式");
                result.put("percent","0");
                //LogUtil.sendMsg(result, response);
                succFlag = false;
            }
        }

        if(succFlag){
            result.put("code",LOG_INFO);
            result.put("percent",100);
            result.put("msg","导入完成");
        }else{
            result.put("code",LOG_WARN);
            result.put("msg","导入完成，过程中出现错误，请检查！");
        }
        //LogUtil.sendMsg(result, response);

        endtime = System.currentTimeMillis();
        float costtime = endtime - begintime;
        //获取任务耗时并返回
        HashMap timeMap = new HashMap(4);
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        timeMap.put("begintime", format.format(begintime));
        timeMap.put("endtime", format.format(endtime));
        timeMap.put("costtime", new BigDecimal(costtime / 1000f).setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue());
        timeMap.put("code",LOG_INFO);
        timeMap.put("msg","耗时："+timeMap.get("costtime")+"秒");
        timeMap.put("success",succFlag?"1":0);
        String str = "<script type='text/javascript'>window.parent.afterTaskFinished(" + LogUtil.object2Json(timeMap) + " );</script>";
        synchronized (response) {
//            response.getWriter().println(str);
//            response.getWriter().flush();
        }
    }

    /**
     * 导入模式1：解析Excel后 循环调用后台功能号进行处理，进度信息由java层进行处理
     * @param params
     * @param file
     * @param request
     * @param response
     * @param excelConfig
     * @throws IOException
     */
    private boolean ImportMode1(Map params, File file, HttpServletRequest request, HttpServletResponse response, ExcelConfig excelConfig) {
        boolean succFlag = true;
        boolean endFlag = false;
        Map<String, Object> result = new HashMap<String, Object>();
        ArrayList<Map<String, Object>> resultList = new ArrayList();

        AuthParam authParam = ((CommonParam)request.getSession().getAttribute("common_param")).getAuthParam();
        Map<String, Object> userInfo = new HashMap<String, Object>();
        userInfo.put("g_funcid", excelConfig.getLbmCode());
        userInfo.put("g_userid", authParam.getUserId());
        userInfo.put("g_userpwd", authParam.getPassword());
        userInfo.put("g_orgid", authParam.getOrgId());

        FileInputStream input = null;
        try{
            input = new FileInputStream(file);
            Workbook workbook = WorkbookFactory.create(input);
            Sheet sheet = workbook.getSheetAt(0);

            int count = 1;
            Iterator<Row> rows = sheet.rowIterator();
            Iterator<Row> rows1 = sheet.rowIterator();
            int totalNum = sheet.getLastRowNum();
            succFlag = ValidateImportData(response, rows, excelConfig);
            // 存储表头信息
            Map<String, Object> fieldNameMaps = new HashMap<String, Object>();
            // 如果数据合法性校验通过
            if (succFlag) {
                while (rows1.hasNext()) {
                    Row row = rows1.next();
                    if (row.getRowNum() < excelConfig.getDataStartRow()) {
                        fieldNameMaps = convertRow(excelConfig, row);
                        continue;
                    }
                    Map<String, Object> rowMap = convertRow(excelConfig, row);
                    for (Map.Entry entry: fieldNameMaps.entrySet()) {
                        if (!rowMap.containsKey(entry.getKey())) {
                            rowMap.put(entry.getKey().toString(), new String(""));
                        }
                    }

                    // 解析excel数据
                    for (Map.Entry entry: rowMap.entrySet()) {
                        String key = entry.getKey().toString();
                        String value = entry.getValue().toString();
                        if (value.trim().equals("END")) {
                            endFlag = true;
                            break;
                        }
                        if (value.indexOf("|") != -1) {
                            value = value.substring(0, value.indexOf("|"));
                            rowMap.put(key, value);
                        }
                    }
                    if (endFlag) break;

                    if(rowMap.containsKey("error")){
                        result.put("code",LOG_ERROR);
                        result.put("msg",rowMap.get("error"));
                        //LogUtil.sendMsg(result,response);
                        succFlag = false;
                        count++;
                    }else if(!isEmptyRow(rowMap)) {
                        resultList.add(rowMap);
                        rowMap.putAll(publicParams);
                        rowMap.putAll(userInfo);
                        rowMap.put("batchflag",params.get("batchflag"));
                        result = callLbm("123456", rowMap, count);
                        if(!"0".equals(result.get("code"))) succFlag = false;
                        result.put("percent", 100*count/totalNum +"");
                        LogUtil.sendMsg(result, response);
                        count++;
                    }
                }
            }
            response.getWriter().write(JSON.toJSONString(resultList));
            response.getWriter().flush();
            //删除文件
            if(file.exists()) file.delete();
        }catch (Exception e){
            result.put("code",LOG_ERROR);
            result.put("msg","解析模板出错，错误信息："+e.getMessage());
            result.put("percent","0");
            //LogUtil.sendMsg(result, response);
            succFlag = false;
            e.printStackTrace();
        }finally {
            try {
                input.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return succFlag;
    }

    /**
     * 导入模式2：解析Excel后， 先将数据保存到临时表，然后调用功能号进行处理
     * @param params
     * @param file
     * @param request
     * @param response
     * @param excelConfig
     * @throws IOException
     */
    private boolean ImportMode2(Map params, File file, HttpServletRequest request, HttpServletResponse response, ExcelConfig excelConfig) {
        boolean succFlag = true;
        //TODO:解析Excel数据行并保存到临时表
        succFlag = importToTemporaryTable(params,file,response,excelConfig);

        //TODO:调功能号，并进行日志推送
        succFlag = succFlag?callImportLbm(params,request,response,excelConfig):false;
        //删除文件
        if(file.exists()) file.delete();
        return succFlag;
    }

    /**
     * 调用LBM
     * @param lbmCode
     * @param inParam
     * @param count
     * @return
     */
    public Map<String, Object> callLbm(String lbmCode, Map<String,Object> inParam, int count){
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("code", LOG_INFO);
        result.put("msg", "处理第"+count+"笔成功:");
        LbmResult lbmResult = lbmService.lbmDefaultExecute(lbmCode, inParam, 10000);
        toBexDataResult(lbmResult);
        if(lbmResult.getReturnCode()==0){
            result.put("code", LOG_INFO);
            result.put("msg", "处理第"+count+"笔成功:"+lbmResult.getReturnMessage());
        }else{
            result.put("code", LOG_ERROR);
            result.put("msg", "处理第"+count+"笔失败:"+lbmResult.getReturnMessage());
        }
        return result;
    }

    /**
     * 判断一行是否为空行
     * @param rowMap
     * @return
     */
    public boolean isEmptyRow(Map<String, Object> rowMap){
        boolean retFlag = true;
        StringBuffer sb = new StringBuffer("");
        Iterator<String> it = rowMap.keySet().iterator();
        while(it.hasNext()){
            String key = it.next();
            if("rownum".equals(key) || "error".equals(key)) continue;
            sb.append(rowMap.get(key));
        }
        return StringUtils.isEmpty(sb.toString());
    }

    @Override
    public void exportExcel(Map params,  HttpServletRequest request, HttpServletResponse response) {

    }

    public Map<String, Object> convertRow(ExcelConfig excelConfig, Row row) {
        Map<String, Object> rowMap = new HashMap<String, Object>();
        Map<String, ColumnConfig> columnConfigs = excelConfig.getColumnsMap();
        Iterator<String> iterator = columnConfigs.keySet().iterator();
        String cellType = "";
        while(iterator.hasNext()){
            String index = iterator.next();
            Cell cell = row.getCell(Integer.parseInt(index));

            ColumnConfig columnConfig = columnConfigs.get(index);
            if(cell != null){
                try {
                    String cellValue = "";
                    if (row.getRowNum() < excelConfig.getDataStartRow()) {
                        cellValue = getCellValue(cell).toString();
                    } else {
                        switch (cell.getCellType()) {
                            case Cell.CELL_TYPE_NUMERIC:
                                if ("@".equals(cell.getCellStyle().getDataFormatString())
                                        || "General".equals(cell.getCellStyle().getDataFormatString())
                                        || cell.getCellStyle().getDataFormatString().indexOf("#") != -1
                                        || cell.getCellStyle().getDataFormatString().indexOf("0") != -1) {
                                    cellType = "Number";
                                } else if (cell.getCellStyle().getDataFormatString().indexOf("y") != -1){
                                    cellType = "Date";
                                }
                                break;
                            case Cell.CELL_TYPE_STRING:
                                cellType = "String";
                                break;
                            case Cell.CELL_TYPE_FORMULA:
                                cellType = "Formula";
                                break;
                            case Cell.CELL_TYPE_BOOLEAN:
                                cellType = "Boolean";
                                break;
                        }
                        cellValue = ExcelUtil.validateExcelCell(getCellValue(cell), columnConfig.getType(), columnConfig.getFormat(), columnConfig.getRegExp(), cellType);
                    }
                    rowMap.put(columnConfig.getField(), cellValue);
                } catch (ValidateException e) {
                    rowMap.put("error", "第" + (row.getRowNum() + 1 - excelConfig.getDataStartRow()) + "行第" + (Integer.parseInt(index) + 1) + "列的数据格式错误,错误原因:" + e.getMessage() + "！");
                    return rowMap;
                }
            }else{
                rowMap.put(columnConfig.getField(), "");
            }
        }
        rowMap.put("rownum", row.getRowNum());
        return rowMap;
    }

    /**
     * 获取单元格中的值
     * @param cell
     * @return
     */
    private Object getCellValue(Cell cell){
        Object value = null;
        int cellType = cell.getCellType();
        switch(cellType){
            case Cell.CELL_TYPE_NUMERIC:
                if ("@".equals(cell.getCellStyle().getDataFormatString())
                        || "General".equals(cell.getCellStyle().getDataFormatString())
                        || cell.getCellStyle().getDataFormatString().indexOf("#") != -1
                        || cell.getCellStyle().getDataFormatString().indexOf("0") != -1) {
                    value = new BigDecimal(cell.getNumericCellValue()).toPlainString();
                } else if (cell.getCellStyle().getDataFormatString().indexOf("y") != -1){
                    value = HSSFDateUtil.getJavaDate(cell.getNumericCellValue());
                }
                break;
            case Cell.CELL_TYPE_STRING:
                value = cell.getStringCellValue();
                break;
            case Cell.CELL_TYPE_FORMULA:
                value = cell.getCellFormula();
                break;
            case Cell.CELL_TYPE_BOOLEAN:
                value = cell.getBooleanCellValue();
                break;
            default:
                value = "";
        }
        return value;
    }

    /**
     * 解析Excel,并将数据保存到临时表
     * @param file
     * @param response
     * @param excelConfig
     * @throws IOException
     */
    private Boolean importToTemporaryTable(Map params, File file, HttpServletResponse response, ExcelConfig excelConfig) {
        boolean succFlag = true;
        //TODO:解析Excel,并将数据保存到临时表
        Map<String, Object> result = new HashMap<String, Object>();
        FileInputStream input = null;
        List<String> sqls = new ArrayList<String>();
        try{
            input = new FileInputStream(file);
            Workbook workbook = WorkbookFactory.create(input);
            Sheet sheet = workbook.getSheetAt(0);

            int count = 1;
            Iterator<Row> rows = sheet.rowIterator();
            //TODO:批量生成insert语句，之后调用jdbcTemplate.batchUpdate()批量保存到临时表
            while (rows.hasNext()) {
                Row row = rows.next();
                if(row.getRowNum()<excelConfig.getDataStartRow()) continue;
                Map<String, Object> rowMap = convertRow(excelConfig, row);
                if(rowMap.containsKey("error")){
                    result.put("code",LOG_ERROR);
                    result.put("msg",rowMap.get("error"));
                    //LogUtil.sendMsg(result,response);
                    succFlag = false;
                }else if(!isEmptyRow(rowMap)) {
                    rowMap.put("batchflag",params.get("batchflag"));
                    rowMap.put("rowno",rowMap.get("rownum"));
                    String keys = "";
                    String values = "";
                    for(String key : rowMap.keySet()){
                        if("rownum".equals(key)){
                            continue;
                        }
                        if("".equals(keys)){
                            keys +=  key;
                            values += "'" +rowMap.get(key) +"'";
                        }else{
                            keys +=  "," + key;
                            values += "," + "'" +rowMap.get(key) +"'";
                        }
                    }
                    String sql = "insert into " + excelConfig.getTableName() + "(" + keys + ") values (" + values + ")";
                    sqls.add(sql);
                }
                count++;
            }
            //提交数据库
            if(sqls.size()>0){
                jdbcTemplate.getDataSource().getConnection().setAutoCommit(true);
                jdbcTemplate.batchUpdate(sqls.toArray(new String[sqls.size()]));
                jdbcTemplate.getDataSource().getConnection().commit();
            }
        }catch (Exception e){
            result.put("code",LOG_ERROR);
            result.put("msg","解析模板出错，错误信息："+e.getMessage());
            result.put("percent","0");
            //LogUtil.sendMsg(result, response);

            succFlag = false;
            e.printStackTrace();
        }finally {
            try {
                input.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return succFlag;
    }

    /**
     * 调功能号，并进行日志推送
     * @param params
     * @param request
     * @param response
     * @param excelConfig
     * @throws IOException
     */
    private Boolean callImportLbm(Map params, HttpServletRequest request, HttpServletResponse response, ExcelConfig excelConfig) {
        boolean succFlag = true;
        Map<String, Object> result = new HashMap<String, Object>();
        Map aParam = new HashMap();
        AuthParam authParam = ((CommonParam)request.getSession().getAttribute("common_param")).getAuthParam();
        Map<String, Object> userInfo = new HashMap<String, Object>();
        userInfo.put("g_funcid", excelConfig.getLbmCode());
        userInfo.put("g_userid", authParam.getUserId());
        userInfo.put("g_userpwd", authParam.getPassword());
        userInfo.put("g_orgid", authParam.getOrgId());
        aParam.putAll(this.publicParams);
        aParam.putAll(userInfo);
        aParam.put("batchflag", params.get("batchflag"));
//
//        cometLbmService.setPageSize(1);
//
//        cometLbmService.beginLbmExec(excelConfig.getLbmCode(), aParam, new HashSet());
//        cometLbmService.getResultHeader();
//        try {

//            while (cometLbmService.isResultActive()) {
//                LbmResult lbmResult = cometLbmService.getResultBody();
//                if(lbmResult.getRecordSize()<=0){
//                    continue;
//                }
//                List<Map<String, Object>> recordArray = lbmResult.getRecordArray().get(0).getRecordArray();
//                //TODO:遍历recordArray，并向前端推送消息
//                for(Map<String, Object> resultSet : recordArray){
//                    if("100".equals(resultSet.get("code"))|| "200".equals(resultSet.get("code"))){
//                        resultSet.put("code",LOG_INFO);
//                    }else if("110".equals(resultSet.get("code"))|| "210".equals(resultSet.get("code"))){
//                        resultSet.put("code",LOG_WARN);
//                    }else if("120".equals(resultSet.get("code"))|| "220".equals(resultSet.get("code"))){
//                        resultSet.put("code",LOG_ERROR);
//                        succFlag = false;
//                    }
//                    LogUtil.sendMsg(resultSet, response);
//                }
//
//                //返回功能号执行失败
//                if (lbmResult.getReturnCode() != 0) {
//                    result.put("code",LOG_ERROR);
//                    result.put("msg","执行功能号[" + excelConfig.getLbmCode() + "]失败");
//                    result.put("percent","0");
//                    LogUtil.sendMsg(result, response);
//                    succFlag = false;
//                    break;
//                }
//            }
//            cometLbmService.endLbmExec();
//        } catch (Exception e){
//            result.put("code",LOG_ERROR);
//            result.put("msg","解析模板出错，错误信息："+e.getMessage());
//            result.put("percent","0");
//            LogUtil.sendMsg(result, response);
//            succFlag = false;
//            e.printStackTrace();
//        }
        return succFlag;
    }

    /**
     * 校验待导入数据合法性
     * @param response
     * @param rowIterator
     * @param excelConfig
     * @return
     */
    private boolean ValidateImportData(HttpServletResponse response, Iterator<Row> rowIterator, ExcelConfig excelConfig) {
        boolean isPassed = true;
        int count = 1;
        Map<String, Object> result = new HashMap<String, Object>();

        // 解析数据，进行数据合法性校验
        Map<String, ColumnConfig> columnConfigMap= excelConfig.getColumnsMap();
        // Excel 表头信息映射
        Map<String, Object> fieldNameMaps = new HashMap<String, Object>();
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            if(row.getRowNum()<excelConfig.getDataStartRow()) {
                fieldNameMaps = convertRow(excelConfig, row);
                continue;
            }

            Map<String, Object> rowMap = convertRow(excelConfig, row);
            if(isEmptyRow(rowMap)) continue;
            else{
                for (Map.Entry<String, ColumnConfig> configEntry : columnConfigMap.entrySet()) {
                    ColumnConfig columnConfig = configEntry.getValue();
                    String require = columnConfig.getRequire();
                    int width = 0;
                    if(null != columnConfig.getWidth()) {
                        width = Integer.parseInt(columnConfig.getWidth());
                    }
                    for (Map.Entry entry: rowMap.entrySet()) {
                        String fieldValue = entry.getValue().toString();
                        if(fieldValue.indexOf("|") != -1) {
                            fieldValue = fieldValue.substring(0, fieldValue.indexOf("|"));
                        }

                        String msg = "";
                        if (require.equals("true")) {
                            if (entry.getKey() == columnConfig.getField() && fieldValue.equals("")) {
                                isPassed = false;
                                msg = "第" + count + "行数据字段[ " + fieldNameMaps.get(entry.getKey()) + ":" + entry.getKey() + " ]是必填项！";
                                result.put("code", LOG_ERROR);
                                result.put("msg", msg);
                                //LogUtil.sendMsg(result, response);
                            }
                        }
                        if (entry.getKey() == columnConfig.getField() && width != 0 && fieldValue.length() > width) {
                            isPassed = false;
                            msg += "第" + count + "行数据字段[ " + fieldNameMaps.get(entry.getKey()) + ":" + entry.getKey() + " ]长度大于" + width;
                            result.put("code", LOG_ERROR);
                            result.put("msg", msg);
                            //LogUtil.sendMsg(result, response);
                        }
                    }
                }
            }
            count++;
        }
        return isPassed;
    }
}
