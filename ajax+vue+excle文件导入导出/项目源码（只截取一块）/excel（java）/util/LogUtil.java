package com.szkingdom.kamm.core.excel.util;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

/**
 * ***************************************************************************
 * Copyright (C) 1998-至今, SHENZHEN KINGDOM Co., Ltd.
 * <p/>
 * All Rights Reserved.
 * <p/>
 * ===========================================================================
 * <p/>
 * 文 件 名：writeLog.java
 * 模块名称：
 * 功能说明：
 * 作    者：xiongwei@szkingdom.com
 * 创建日期：2015-05-25 14:04
 * 版 本 号：1.0
 * 修改历史：
 * <p/>
 * 修改日期                  姓名                         内容
 * ---------------------------------------------------------------------------
 * 2015-05-25 14:04     xiongwei@szkingdom.com          初始版本
 * /*****************************************************************************
 */
public class LogUtil {
    public static void sendMsg(Map<String, Object> result, HttpServletResponse response) {
        String jsonResult = object2Json(result);
        synchronized (response) {
            try{
//                response.getWriter().println(htmlEscape(jsonResult));
//                response.getWriter().flush();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public static String object2Json(Object object) {
        String json = "";
        try {
        ObjectMapper mapper2 = new ObjectMapper();
        StringWriter sw = new StringWriter();
        JsonGenerator gen = new JsonFactory().createGenerator(sw);
            mapper2.writeValue(gen, object);
            gen.close();
            json = sw.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return json;
    }

    public static String htmlEscape(String message) {
        String msg = "<script type='text/javascript'>window.parent.reciveResult("
                + message.replaceAll("\\n\\r", "<br/>").replaceAll("\\r\\n", "<br/>").replaceAll("\\n", "").replaceAll("\\r", "")
                + ");</script>";
        return msg;
    }
}
