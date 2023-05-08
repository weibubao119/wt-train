package com.dyys.hr.dao.train;

import com.dagongma.mybatis.core.dao.ICrudMapper;
import com.dyys.hr.entity.train.TrainLearnMap;
import com.dyys.hr.vo.train.LearnMapInfoVO;
import com.dyys.hr.vo.train.LearnMapSelectVO;
import com.dyys.hr.vo.train.LearnMapVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 学习地图 Mapper 接口
 * </p>
 *
 * @author yangye
 * @since 2022-09-06
 */
@Repository
public interface TrainLearnMapMapper extends ICrudMapper<TrainLearnMap> {
    /**
     * 学习地图列表
     * @param params
     * @return
     */
    List<LearnMapVO> mapList(Map<String, Object> params);

    /**
     * 校验学习地图编码唯一性
     * @param mapCode
     * @param mapId
     * @return
     */
    Integer countByMapCode(@Param("mapCode") String mapCode, @Param("mapId") Long mapId);

    /**
     * 学习地图详情
     * @param id
     * @return
     */
    LearnMapInfoVO queryInfoById(Long id);

    /**
     * 学习地图选择列表
     * @param params
     * @return
     */
    List<LearnMapSelectVO> mapSelectList(Map<String, Object> params);
}
