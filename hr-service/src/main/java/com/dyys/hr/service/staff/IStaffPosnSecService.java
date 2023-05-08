package com.dyys.hr.service.staff;


import com.dagongma.mybatis.core.service.ICrudService;
import com.dyys.hr.entity.staff.StaffPosnSec;
import com.dyys.hr.vo.common.PsPosnSecVO;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 职序管理表 服务类
 * </p>
 *
 * @author yangye
 * @since 2022-08-23
 */
public interface IStaffPosnSecService extends ICrudService<StaffPosnSec, String> {
    /**
     * 职序筛选列表
     * @param params
     * @return
     */
    List<PsPosnSecVO> selectList(Map<String, Object> params);

    /**
     * 根据职序编码获取职序的名称
     * @param posnSecCode
     * @return
     */
    PsPosnSecVO getBasicInfoByCode(String posnSecCode);
}
