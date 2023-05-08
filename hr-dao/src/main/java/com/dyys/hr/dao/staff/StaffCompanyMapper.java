package com.dyys.hr.dao.staff;

import com.dagongma.mybatis.core.dao.ICrudMapper;
import com.dyys.hr.entity.staff.StaffCompany;
import com.dyys.hr.vo.common.PsCompanyVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 公司表 Mapper 接口
 * </p>
 *
 * @author yangye
 * @since 2022-08-23
 */
@Mapper
public interface StaffCompanyMapper extends ICrudMapper<StaffCompany> {
    /**
     * 公司/单位筛选列表
     * @param params
     * @return
     */
    List<PsCompanyVO> selectList(Map<String, Object> params);
}
