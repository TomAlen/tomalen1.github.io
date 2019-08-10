package com.zwhzzz.util;

import java.util.Date;
import java.util.List;

/**
 * 工具类，用于删除操作传入的ids
 */
public class join {

    public static String joinString(List<Long> list,String split) {
        String result = "";
        for(Long l : list) {
            result += l + split;
        }
        //当返回的数据不为空时
        if(!"".equals(result)) {
            //split.length() == 1
            result = result.substring(0,result.length()-split.length());
        }
        return result;
    }

    //设置学号，利用时间戳进行设置
    public static String generateSn (String prefix,String suffix) {
        return prefix + new Date().getTime() + suffix;
    }

    //设置教师工号
    public static String generateTn (String prifix,String suffix) {
        return prifix + new Date().getTime() + suffix;
    }
}
