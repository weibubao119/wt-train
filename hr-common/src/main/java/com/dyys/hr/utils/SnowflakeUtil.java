package com.dyys.hr.utils;

import cn.hutool.core.lang.Snowflake;

/**
 * @author ZHIQIANG LI
 * @date 2019/8/4 12:56
 **/
public class SnowflakeUtil {

    private static Snowflake snowflake;

    public SnowflakeUtil() {
        snowflake = new Snowflake(1, 1);
    }

    public  Long nextId() {
        return snowflake.nextId();
    }
}
