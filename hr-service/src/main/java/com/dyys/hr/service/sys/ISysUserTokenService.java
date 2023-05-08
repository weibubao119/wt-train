package com.dyys.hr.service.sys;

import com.dagongma.mybatis.core.service.ICrudService;
import com.dyys.hr.entity.sys.SysUserToken;


/**
 * <p>
 * 系统用户Token 服务类
 * </p>
 *
 * @author lidaan
 * @since 2022-05-05
 */
public interface ISysUserTokenService extends ICrudService<SysUserToken, Long> {

    SysUserToken findUserToken(String emplId);

    /**
     * 生成新员工token记录
     * @param emplId
     * @return
     */
    SysUserToken createEmplToken(String emplId);
}
