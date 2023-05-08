package com.dyys.hr.service.staff.impl;


import com.dagongma.mybatis.core.service.impl.AbstractCrudService;
import com.dyys.hr.dao.staff.StaffPosnGradeMapper;
import com.dyys.hr.entity.staff.StaffPosnGrade;
import com.dyys.hr.service.staff.IStaffPosnGradeService;
import com.dyys.hr.vo.common.PsPosnGradeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 职级管理表 服务实现类
 * </p>
 *
 * @author yangye
 * @since 2022-08-23
 */
@Service
public class StaffPosnGradeServiceImpl extends AbstractCrudService<StaffPosnGrade, String> implements IStaffPosnGradeService {
    @Autowired
    private StaffPosnGradeMapper staffPosnGradeMapper;

    /**
     * 职级筛选列表
     * @param params
     * @return
     */
    @Override
    public List<PsPosnGradeVO> selectList(Map<String, Object> params) {
        return staffPosnGradeMapper.selectList(params);
    }
}
