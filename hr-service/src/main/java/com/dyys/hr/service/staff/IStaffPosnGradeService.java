package com.dyys.hr.service.staff;


import com.dagongma.mybatis.core.service.ICrudService;
import com.dyys.hr.entity.staff.StaffPosnGrade;
import com.dyys.hr.vo.common.PsPosnGradeVO;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 职级管理表 服务类
 * </p>
 *
 * @author yangye
 * @since 2022-08-23
 */
public interface IStaffPosnGradeService extends ICrudService<StaffPosnGrade, String> {
    /**
     * 职级筛选列表
     * @param params
     * @return
     */
    List<PsPosnGradeVO> selectList(Map<String, Object> params);
}
