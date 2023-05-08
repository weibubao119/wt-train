package com.dyys.hr.service.staff.impl;


import com.dagongma.mybatis.core.service.impl.AbstractCrudService;
import com.dyys.hr.dao.staff.StaffProfessionMapper;
import com.dyys.hr.entity.staff.StaffProfession;
import com.dyys.hr.service.staff.IStaffProfessionService;
import com.dyys.hr.vo.common.PsProfessionVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 职族管理表 服务实现类
 * </p>
 *
 * @author yangye
 * @since 2022-08-23
 */
@Service
public class StaffProfessionServiceImpl extends AbstractCrudService<StaffProfession, Long> implements IStaffProfessionService {
    @Autowired
    private StaffProfessionMapper staffProfessionMapper;

    /**
     * 职族筛选列表
     * @param params
     * @return
     */
    @Override
    public List<PsProfessionVO> selectList(Map<String, Object> params) {
        return staffProfessionMapper.selectList(params);
    }
}
