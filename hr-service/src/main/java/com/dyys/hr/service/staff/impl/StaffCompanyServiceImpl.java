package com.dyys.hr.service.staff.impl;


import com.dagongma.mybatis.core.service.impl.AbstractCrudService;
import com.dyys.hr.dao.staff.StaffCompanyMapper;
import com.dyys.hr.entity.staff.StaffCompany;
import com.dyys.hr.service.staff.IStaffCompanyService;
import com.dyys.hr.vo.common.PsCompanyVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 公司表 服务实现类
 * </p>
 *
 * @author yangye
 * @since 2022-08-23
 */
@Service
public class StaffCompanyServiceImpl extends AbstractCrudService<StaffCompany, Long> implements IStaffCompanyService {
    @Autowired
    private StaffCompanyMapper staffCompanyMapper;

    /**
     * 公司/单位筛选列表
     * @param params
     * @return
     */
    @Override
    public List<PsCompanyVO> selectList(Map<String, Object> params) {
        return  staffCompanyMapper.selectList(params);
    }
}
