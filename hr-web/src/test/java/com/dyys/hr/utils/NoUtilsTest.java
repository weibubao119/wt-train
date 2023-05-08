package com.dyys.hr.utils;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.Date;

//@RunWith(SpringRunner.class)
//@SpringBootTest
public class NoUtilsTest {

    @Autowired
    private NoUtils noUtils;

    @Test
    public void test(){
//        System.out.println("开始打印培训班申请编号：" + noUtils.createNo(Constant.APPLY_NO_PREFIX));
        Date t = new Date();

        SimpleDateFormat df = new SimpleDateFormat("HH:mm");
        System.out.println(df.format(t));
    }

}
