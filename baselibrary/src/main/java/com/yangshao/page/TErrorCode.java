package com.yangshao.page;

import java.util.Map;

/**
 * 创建人：$ gyymz1993
 * 创建时间：2017/6/8 9:33
 */

public class TErrorCode {
    static TErrorCode instance=new TErrorCode();
    public static TErrorCode getInstance() {
        return instance;
    }

    Map<Integer,String> initErrors() {
        return null;
    }

    static TErrorCode customErrorMsg(Map<Integer, String> msgs) {
        return null;
    }
}
