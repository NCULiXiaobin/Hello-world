package com.szkingdom.kamm.core.excel.exception;

/**
 * ***************************************************************************
 * Copyright (C) 1998-至今, SHENZHEN KINGDOM Co., Ltd.
 * <p/>
 * All Rights Reserved.
 * <p/>
 * ===========================================================================
 * <p/>
 * 文 件 名：ValidateException.java
 * 模块名称：
 * 功能说明：
 * 作    者：xiongwei@szkingdom.com
 * 创建日期：2015-07-03 10:55
 * 版 本 号：1.0
 * 修改历史：
 * <p/>
 * 修改日期                  姓名                         内容
 * ---------------------------------------------------------------------------
 * 2015-07-03 10:55     xiongwei@szkingdom.com          初始版本
 * /*****************************************************************************
 */
public class ValidateException extends Exception {
    protected String errorCode = "-999999";

    public String getErrorCode() {
        return this.errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public ValidateException(String message) {
        super(message);
    }

    public ValidateException(String message, Throwable cause) {
        super(message, cause);
    }

    public ValidateException(String errorCode, String message) {
        this(message);
        this.errorCode = errorCode;
    }

    public ValidateException(String errorCode, String message, Throwable cause) {
        this(message, cause);
        this.errorCode = errorCode;
    }

    public ValidateException(Throwable cause) {
        super(cause);
    }
}
