package com.dyys.hr.dao.train;

import com.dagongma.mybatis.core.dao.ICrudMapper;
import com.dyys.hr.entity.train.TrainLearnMapStaffSbl;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 学员地图-学习方向 Mapper 接口
 * </p>
 *
 * @author yangye
 * @since 2022-09-06
 */
@Repository
public interface TrainLearnMapStaffSblMapper extends ICrudMapper<TrainLearnMapStaffSbl> {
    /**
     * 取员工学习方向名称
     * @param emplId
     * @return
     */
    List<String> querySblNameByEmplId(String emplId);

    /**
     * 员工学习方向记录数量
     * @param emplId
     * @return
     */
    Integer queryTotalByEmplId(String emplId);

    /**
     * 取员工学习方向ID
     * @param emplId
     * @return
     */
    List<Integer> querySblIdByEmplId(String emplId);
}
