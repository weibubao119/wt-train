package com.dyys.hr.dao.staff;

import com.dagongma.mybatis.core.dao.ICrudMapper;
import com.dyys.hr.entity.staff.StaffPosnGrade;
import com.dyys.hr.vo.common.PsPosnGradeVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 职级管理表 Mapper 接口
 * </p>
 *
 * @author yangye
 * @since 2022-08-23
 */
@Mapper
public interface StaffPosnGradeMapper extends ICrudMapper<StaffPosnGrade> {
    /**
     * 职级筛选列表
     * @param params
     * @return
     */
    List<PsPosnGradeVO> selectList(Map<String, Object> params);
}
