package com.dyys.hr.utils;

import java.util.UUID;

/**
 * @author sword
 * @create_time 2020年11月29日23:44:14
 */
public class RandomUUIDUtils {
    public static String UUID(){
        //替换斜杠
        String uuid = UUID.randomUUID().toString().replace("-", "");
        return uuid;
    }
    public static void main(String[] args) {
        String uuid = UUID();
        System.out.println(uuid);
    }
}
