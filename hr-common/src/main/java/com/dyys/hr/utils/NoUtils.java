package com.dyys.hr.utils;

import cn.hutool.core.date.DateUtil;
import com.dyys.hr.constants.Constant;
import lombok.extern.slf4j.Slf4j;
import com.dagongma.redis.common.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 生成编号规则工具类
 *
 * @author JUCHUAN LI
 * @date 2019/06/30
 */
@Slf4j
@Component
public class NoUtils {

    @Autowired
    RedisUtil redisUtil;

    /**
     * 创建编号 REDIS
     * @param prefix
     * @return
     */
    public String createNo(String prefix) {

        String dateFormat = DateUtil.format(new Date(), "yyMMdd");
        String noKey = String.format(Constant.REDIS_NO, prefix, dateFormat);
        Long num = redisUtil.incr(noKey);
        if (num == 1) {
            /** 设置redis的有效时间 **/
            long time = 60 * 60 * 24;
            redisUtil.expire(noKey, time);
        }
        String value = String.format("%s%02d", dateFormat, num);

        return value;

    }
}
