package com.jonas.yun_library.error;

/**
 * @author yun.
 * @date 2016/12/21
 * @des [一句话描述]
 * @since [https://github.com/ZuYun]
 * <p><a href="https://github.com/ZuYun">github</a>
 */
public class ErrorMsg {
    public static final String DEFAULTSTR = "--";
    public static interface ErrorCode {
        int HTTP404 = -404;
        int CONNECT404 = 404;
    }
    public static interface ErrorString {
        String HTTP404 = "网络异常";
        String CONNECT404 = "连接异常";
    }
}
