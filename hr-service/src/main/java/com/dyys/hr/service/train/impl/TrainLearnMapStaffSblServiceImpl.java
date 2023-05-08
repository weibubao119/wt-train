package com.dyys.hr.service.train.impl;

import com.dagongma.mybatis.core.service.impl.AbstractCrudService;
import com.dyys.hr.dao.train.TrainLearnMapStaffSblMapper;
import com.dyys.hr.entity.train.TrainLearnMapStaffSbl;
import com.dyys.hr.service.train.TrainLearnMapStaffSblService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 学员地图-学习方向 服务实现类
 * </p>
 *
 * @author yangye
 * @since 2022-09-06
 */
@Service
public class TrainLearnMapStaffSblServiceImpl extends AbstractCrudService<TrainLearnMapStaffSbl, Long> implements TrainLearnMapStaffSblService {
    @Autowired
    private TrainLearnMapStaffSblMapper trainLearnMapStaffSblMapper;

    /**
     * 取员工学习方向名称
     * @param emplId
     * @return
     */
    @Override
    public List<String> querySblNameByEmplId(String emplId) {
        return trainLearnMapStaffSblMapper.querySblNameByEmplId(emplId);
    }

    /**
     * 删除员工的学习方向
     * @param emplId
     * @return
     */
    @Override
    public Boolean deleteByEmplId(String emplId) {
        int total = trainLearnMapStaffSblMapper.queryTotalByEmplId(emplId);
        if (total > 0) {
            TrainLearnMapStaffSbl staffSbl = new TrainLearnMapStaffSbl();
            staffSbl.setEmplId(emplId);
            int res = trainLearnMapStaffSblMapper.delete(staffSbl);
            return res == total;
        }
        return true;
    }

    /**
     * 取员工学习方向ID
     * @param emplId
     * @return
     */
    @Override
    public List<Integer> querySblIdByEmplId(String emplId) {
        return trainLearnMapStaffSblMapper.querySblIdByEmplId(emplId);
    }
}
