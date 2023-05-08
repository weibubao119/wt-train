package com.dyys.hr.service.staff;


import com.dagongma.mybatis.core.service.ICrudService;
import com.dyys.hr.entity.staff.StaffCompany;
import com.dyys.hr.vo.common.PsCompanyVO;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 公司表 服务类
 * </p>
 *
 * @author yangye
 * @since 2022-08-23
 */
public interface IStaffCompanyService extends ICrudService<StaffCompany, Long> {
    /**
     * 公司/单位筛选列表
     * @param params
     * @return
     */
    List<PsCompanyVO> selectList(Map<String, Object> params);
}
