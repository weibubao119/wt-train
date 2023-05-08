package com.dyys.hr.helper;
import cn.hutool.core.util.StrUtil;
import com.dagongma.kernel.commons.constants.HeaderConstants;
import com.dagongma.kernel.commons.util.RequestContextUtil;
import com.dagongma.redis.common.util.RedisUtil;
import com.dyys.hr.constants.Constant;
import com.dyys.hr.entity.sys.SysUserToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;


/**
 * 用户帮助类
 *
 * @author ZHIQIANG LI
 * @date 2019/7/28 12:40
 **/
@Component
@Slf4j
public class UserHelper {

    @Autowired
    private RedisUtil redisUtil;


    /**
     * 得到当前登录员工ID
     *
     * @return
     */
    public String getLoginEmplId() {
        String token = RequestContextUtil.getRequest().getHeader(HeaderConstants.X_TOKEN);
        if (StrUtil.isBlank(token)) {
            token = RequestContextUtil.getRequest().getParameter(Constant.TOKEN).trim();
        }

        if (token != null) {
            String tokenStr = String.format(Constant.REDIS_TOKEN, token);
            SysUserToken userToken = (SysUserToken) redisUtil.get(tokenStr);

            if (userToken != null) {
                return userToken.getUserId();
            }
        }

        return null;
    }

    /**
     * 获取用户姓名
     *
     * @param userId 用户ID
     * @return 用户姓名
     * @author ZHIQIANG LI
     * @date 2019/9/5 0:29
     **/
    public String getUserName(Long userId) {
        Object shortName = redisUtil.hget(String.format(Constant.REDIS_USER_INFO, userId), "userName");

        return String.valueOf(shortName);
    }

    /**
     * 得到当前登录员工的权限部门ID列表
     *
     * @return
     */
    public List<String> getLoginDeptList() {
        String token = RequestContextUtil.getRequest().getHeader(HeaderConstants.X_TOKEN);
        if (StrUtil.isBlank(token)) {
            token = RequestContextUtil.getRequest().getParameter(Constant.TOKEN).trim();
        }

        if (token != null) {
            String tokenStr = String.format(Constant.REDIS_TOKEN, token);
            SysUserToken userToken = (SysUserToken) redisUtil.get(tokenStr);

            if (userToken != null) {
                return userToken.getDeptList();
            }
        }

        return null;
    }

    /**
     * 得到当前登录员工的信息
     * @return
     */
    public SysUserToken getLoginUser() {
        String token = RequestContextUtil.getRequest().getHeader(HeaderConstants.X_TOKEN);
        if (StrUtil.isBlank(token)) {
            token = RequestContextUtil.getRequest().getParameter(Constant.TOKEN).trim();
        }
        if (token != null) {
            String tokenStr = String.format(Constant.REDIS_TOKEN, token);
            SysUserToken userToken = (SysUserToken) redisUtil.get(tokenStr);
            if (userToken != null) {
                return userToken;
            }
        }
        return null;
    }
}
