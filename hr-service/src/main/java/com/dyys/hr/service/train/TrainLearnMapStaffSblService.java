package com.dyys.hr.service.train;

import com.dagongma.mybatis.core.service.ICrudService;
import com.dyys.hr.entity.train.TrainLearnMapStaffSbl;

import java.util.List;

/**
 * <p>
 * 学员地图-学习方向 服务类
 * </p>
 *
 * @author yangye
 * @since 2022-09-06
 */
public interface TrainLearnMapStaffSblService extends ICrudService<TrainLearnMapStaffSbl, Long> {
    /**
     * 取员工学习方向名称
     * @param emplId
     * @return
     */
    List<String> querySblNameByEmplId(String emplId);

    /**
     * 删除员工的学习方向
     * @param emplId
     * @return
     */
    Boolean deleteByEmplId(String emplId);

    /**
     * 取员工学习方向ID
     * @param emplId
     * @return
     */
    List<Integer> querySblIdByEmplId(String emplId);
}
