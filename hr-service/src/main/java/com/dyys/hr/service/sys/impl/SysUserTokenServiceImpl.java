package com.dyys.hr.service.sys.impl;


import com.dagongma.mybatis.core.service.impl.AbstractCrudService;
import com.dyys.hr.dao.sys.SysUserTokenMapper;
import com.dyys.hr.entity.sys.SysUserToken;
import com.dyys.hr.service.sys.ISysUserTokenService;

import com.dyys.hr.utils.TokenUtil;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * <p>
 * 系统用户Token 服务实现类
 * </p>
 *
 * @author lidaan
 * @since 2022-05-05
 */
@Service
public class SysUserTokenServiceImpl extends AbstractCrudService<SysUserToken, Long> implements ISysUserTokenService {
    @Autowired
    private SysUserTokenMapper sysUserTokenMapper;

    @Override
    public SysUserToken findUserToken(String emplId) {
        SysUserToken userToken = new SysUserToken();
        userToken.setUserId(emplId);
        return sysUserTokenMapper.selectOne(userToken);
    }

    /**
     * 生成新员工token记录
     * @param emplId
     * @return
     */
    @Override
    public SysUserToken createEmplToken(String emplId) {
        long stamp = System.currentTimeMillis(); // 当前时间戳
        long expireTime = stamp + 30 * 60 * 1000L; // 半小时有效期
        String token = TokenUtil.generateValue(); // 生成token
        SysUserToken userToken = findUserToken(emplId);
        // 不存在则生成
        if (userToken == null) {
            userToken = new SysUserToken();
            userToken.setUserId(emplId);
            userToken.setUserName(emplId);
            userToken.setAccountId(emplId);
            userToken.setToken(token);
            userToken.setExpireDate(new Date(expireTime));
            userToken.setUpdateDate(new Date(stamp));
            insertSelective(userToken);
        } else {
            userToken.setToken(token);
            userToken.setExpireDate(new Date(expireTime));
            userToken.setUpdateDate(new Date(stamp));
            updateSelective(userToken);
        }
        return userToken;
    }
}
