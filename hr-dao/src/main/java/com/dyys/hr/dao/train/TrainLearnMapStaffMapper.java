package com.dyys.hr.dao.train;

import com.dagongma.mybatis.core.dao.ICrudMapper;
import com.dyys.hr.entity.train.TrainLearnMapStaff;
import com.dyys.hr.vo.train.LearnMapStaffInfoVO;
import com.dyys.hr.vo.train.LearnMapStaffVO;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 学员地图 Mapper 接口
 * </p>
 *
 * @author yangye
 * @since 2022-09-06
 */
@Repository
public interface TrainLearnMapStaffMapper extends ICrudMapper<TrainLearnMapStaff> {
    /**
     * 学员地图列表
     * @param params
     * @return
     */
    List<LearnMapStaffVO> studentMapList(Map<String, Object> params);

    /**
     * 学员基本信息
     * @param emplId
     * @return
     */
    LearnMapStaffInfoVO queryInfoByEmplId(String emplId);
}
