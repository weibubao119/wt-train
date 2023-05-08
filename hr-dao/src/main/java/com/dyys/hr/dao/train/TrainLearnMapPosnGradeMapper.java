package com.dyys.hr.dao.train;

import com.dagongma.mybatis.core.dao.ICrudMapper;
import com.dyys.hr.entity.train.TrainLearnMapPosnGrade;
import com.dyys.hr.vo.train.LearnMapPosnGradeSelectVO;
import com.dyys.hr.vo.train.LearnMapPosnGradeVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 学习地图-职级 Mapper 接口
 * </p>
 *
 * @author yangye
 * @since 2022-09-06
 */
@Repository
public interface TrainLearnMapPosnGradeMapper extends ICrudMapper<TrainLearnMapPosnGrade> {
    /**
     * 统计学习地图下职级数量
     * @param mapId
     * @return
     */
    Integer queryTotalByMapId(Long mapId);

    /**
     * 查询学习地图下的职级列表
     * @param mapId
     * @return
     */
    List<LearnMapPosnGradeVO> queryListByMapId(Long mapId);

    /**
     * 获取低一级职级
     * @param mapId
     * @param posnGradeCode
     * @return
     */
    LearnMapPosnGradeVO queryLowInfo(@Param("mapId") Long mapId, @Param("posnGradeCode") String posnGradeCode);

    /**
     * 学习地图职级选择列表
     * @param mapId
     * @return
     */
    List<LearnMapPosnGradeSelectVO> mapGradeListByMapId(Long mapId);
}
