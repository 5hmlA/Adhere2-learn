package com.jonas.yun_library.helper;

import java.util.Collection;

import static com.jonas.yun_library.error.ErrorMsg.DEFAULTSTR;

/**
 * @author yun.
 * @date 2017/1/19
 * @des [一句话描述]
 * @since [https://github.com/mychoices]
 * <p><a href="https://github.com/mychoices">github</a>
 */
public class CheckHelper {

    /**
     * 检查对象是否为空
     *
     * @param objects
     * @return true 安全的对象 都不为空
     */
    public static boolean checkObjects(Object... objects){
        for(Object object : objects) {
            if(object == null) {
                return false;
            }
        }
        return true;
    }

    /**
     * 检查List集合是否 有效
     *
     * @param lists
     * @return true 安全的Collection 都不为空
     */
    public static boolean checkLists(Collection... lists){
        for(Collection list : lists) {
            if(list == null || list.size()<=0) {
                return false;
            }
        }
        return true;
    }

    /**
     * 返回 安全的字符串
     * @param str
     * @return 为空则返回“”
     */
    public static String safeString(Object str){
        if(str != null) {
            return str.toString().trim();
        }else {
            return DEFAULTSTR;
        }
    }
}

