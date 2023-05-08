package com.dyys.hr.interceptor;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.BCrypt;
import cn.hutool.json.JSONUtil;
import com.dagongma.kernel.commons.constants.HeaderConstants;
import com.dagongma.kernel.commons.exceptions.UserNotLoginException;
import com.dagongma.kernel.web.result.DefaultErrorResult;
import com.dagongma.redis.common.util.RedisUtil;
import com.dyys.hr.constants.BpmConstant;
import com.dyys.hr.constants.Constant;
import com.dyys.hr.entity.sys.SysUserToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.Map;

/**
 * 登陆校验
 *
 * @author ZHIQIANG LI
 * @date 2019/7/28 12:44
 **/
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {

    private RedisUtil redisUtil;

    public LoginInterceptor(RedisUtil redisUtil) {
        this.redisUtil = redisUtil;
    }


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {

        String bpmToken = request.getHeader(BpmConstant.HEADER);
        System.out.println("bpmToken | "+bpmToken);
        if (StrUtil.isNotBlank(bpmToken)) {
            if (!BCrypt.checkpw(BpmConstant.TOKEN, bpmToken)) {
                return responseNotLogin(response);
            }else{
                return true;
            }
        }

        String token = request.getHeader(HeaderConstants.X_TOKEN);
        if (StrUtil.isBlank(token)) {
            return responseNotLogin(response);
        }

//        Object tokenJsn = redisUtil.get(String.format(Constant.REDIS_TOKEN, token));
//        if (ObjectUtil.isNull(tokenJsn)) {
//            return responseNotLogin(response);
//        }

        //ldaan 修改
//        Map<String, Object> map = JSONUtil.toBean(tokenJsn.toString(), Map.class);
//        if (CollUtil.isEmpty(map)) {
//            return responseNotLogin(response);
//        }
//
//        Object uid = map.get("userId");
//        if (ObjectUtil.isNull(uid)) {
//            return responseNotLogin(response);
//        }
//
//        Object expireDate = map.get("expireDate");
//        if (ObjectUtil.isNotNull(expireDate)) {
//            Long expireTime = Long.valueOf(String.valueOf(expireDate));
//            if (expireTime - System.currentTimeMillis() <= 5 * 60 * 1000) {
//                redisUtil.expire(String.format(Constant.REDIS_TOKEN, token), 30 * 60 * 1000L);
//            }
//        }

       SysUserToken userToken = (SysUserToken)redisUtil.get(String.format(Constant.REDIS_TOKEN, token));
        if (ObjectUtil.isNull(userToken)) {
            return responseNotLogin(response);
        }

        if (userToken.getExpireDate() != null){
            Long subMs = DateUtil.millisecond(userToken.getExpireDate())-System.currentTimeMillis();
            //在有效时间内
            if (subMs <= 5 * 60 * 1000){
                redisUtil.expire(String.format(Constant.REDIS_TOKEN, token), 30 * 60 * 1000L);
                Long addMs = System.currentTimeMillis() + 30 * 60 * 1000L;
                userToken.setExpireDate(new Date(addMs));
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception e) throws Exception {

    }

    private Boolean responseNotLogin(HttpServletResponse response) throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        response.setStatus(401);
        response.getWriter().write(JSONUtil.toJsonPrettyStr(DefaultErrorResult.failure(new UserNotLoginException())));
        return false;
    }

    public static void main(String [] args){
        System.out.println(BCrypt.hashpw(BpmConstant.TOKEN,BCrypt.gensalt()));
    }
}
