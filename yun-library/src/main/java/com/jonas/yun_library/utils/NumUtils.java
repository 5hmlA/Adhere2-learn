package com.jonas.yun_library.utils;

/**
 * @author yun.
 * @date 2016/6/22
 * @des [一句话描述]
 * @since [产品/模版版本]
 */

public class NumUtils {

    /**
     *
     * @param num
     * @return  最接近num的数 同时这个数是5的倍数
     */
    public static int getRound5(float num) {
        return ((int) (num + 2.5f)) / 5 * 5;
    }
    /**
     * @param num
     * @return  根据num向上取数 同时这个数是5的倍数
     */
    public static int getCeuk5(float num) {
        return ((int) (num + 5f)) / 5 * 5;
    }



    /**
     * @param num
     * @return  最接近num的数 同时这个数是10的倍数
     */
    public static int getRound10(float num) {
        return ((int) (num + 5f)) / 10 * 10;
    }

    /**
     * @param num
     * @return 根据num向上取数 这个数是10的倍数
     */
    private int getCeil10(float num) {
        return ((int) (num + 9.99f)) / 10 * 10;
    }

}
