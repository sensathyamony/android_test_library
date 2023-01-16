package com.bronx.bronx_helper.util;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class Constant {
    public static final int SUCCESS_CODE = 200;
    public static final int FAIL_CODE = 400;
    public static final int INVALID_TOKEN_CODE = 401;
    public static final int DEACTIVATE_USER = 406;
    public static final int SERVER_MAINTAINANCE = 503;
    @IntDef({SUCCESS_CODE, FAIL_CODE, INVALID_TOKEN_CODE, DEACTIVATE_USER, SERVER_MAINTAINANCE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface API_STATUS_CODE {
    }
}
