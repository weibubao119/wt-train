package com.dyys.hr.service.staff;


import com.dagongma.mybatis.core.service.ICrudService;
import com.dyys.hr.entity.staff.StaffProfession;
import com.dyys.hr.vo.common.PsProfessionVO;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 职族管理表 服务类
 * </p>
 *
 * @author yangye
 * @since 2022-08-23
 */
public interface IStaffProfessionService extends ICrudService<StaffProfession, Long> {
    /**
     * 职族筛选列表
     * @param params
     * @return
     */
    List<PsProfessionVO> selectList(Map<String, Object> params);
}
