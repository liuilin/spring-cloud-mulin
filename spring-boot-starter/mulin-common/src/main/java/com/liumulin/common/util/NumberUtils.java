package com.gmrfid.utils;

import java.math.BigDecimal;

/**
 * 金额工具类
 *
 * @author liuqiang
 * @date 2022/06/24
 */
public class NumberUtils {

    /**
     * 元转分
     *
     * @param amount 金额
     * @return 转换后的金额（单位分）
     */
    public static long yuanToFen(String amount) {
        BigDecimal bigDecimal = new BigDecimal(amount).setScale(2);
        return bigDecimal.multiply(new BigDecimal(100)).longValue();
    }

    /**
     * 分转元
     *
     * @param amount 金额
     * @return 转换后的金额（单位元）
     */
    public static String fenToYuan(long amount) {
        return BigDecimal.valueOf(amount).divide(new BigDecimal(100)).toString();
    }

}
