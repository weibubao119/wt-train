package com.dyys.hr.dao.staff;


import com.dagongma.mybatis.core.dao.ICrudMapper;
import com.dyys.hr.entity.staff.StaffUserInfo;
import com.dyys.hr.vo.common.PsPersionVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 员工基本信息表 Mapper 接口
 * </p>
 *
 * @author yangye
 * @since 2022-08-31
 */
@Mapper
public interface StaffUserInfoMapper extends ICrudMapper<StaffUserInfo> {
    /**
     * 员工筛选列表
     * @param params
     * @return
     */
    List<Map<String, Object>> selectList(Map<String, Object> params);

    /**
     * 获取用户信息
     * @param loginUserId
     * @return
     */
    PsPersionVO getUserInfoById(String loginUserId);

    PsPersionVO getSingleUserInfoById(String loginUserId);

    /**
     * 员工详细信息筛选列表
     * @param params
     * @return
     */
    List<PsPersionVO> detailsSelectList(Map<String, Object> params);
}
