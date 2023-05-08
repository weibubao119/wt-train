package com.dyys.hr.service.staff.impl;


import com.dagongma.mybatis.core.service.impl.AbstractCrudService;
import com.dyys.hr.dao.staff.StaffPosnSecMapper;
import com.dyys.hr.entity.staff.StaffPosnSec;
import com.dyys.hr.service.staff.IStaffPosnSecService;
import com.dyys.hr.vo.common.PsPosnSecVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 职序管理表 服务实现类
 * </p>
 *
 * @author yangye
 * @since 2022-08-23
 */
@Service
public class StaffPosnSecServiceImpl extends AbstractCrudService<StaffPosnSec, String> implements IStaffPosnSecService {
    @Autowired
    private StaffPosnSecMapper staffPosnSecMapper;

    /**
     * 职序筛选列表
     * @param params
     * @return
     */
    @Override
    public List<PsPosnSecVO> selectList(Map<String, Object> params) {
        return staffPosnSecMapper.selectList(params);
    }

    /**
     * 根据职序编码获取职序的名称
     * @param posnSecCode
     * @return
     */
    @Override
    public PsPosnSecVO getBasicInfoByCode(String posnSecCode) {
        return staffPosnSecMapper.basicInfoByCode(posnSecCode);
    }
}
