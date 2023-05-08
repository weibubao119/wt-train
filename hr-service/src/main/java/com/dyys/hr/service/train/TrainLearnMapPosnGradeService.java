package com.dyys.hr.service.train;

import com.dagongma.mybatis.core.service.ICrudService;
import com.dyys.hr.entity.train.TrainLearnMapPosnGrade;
import com.dyys.hr.vo.train.LearnMapPosnGradeSelectVO;
import com.dyys.hr.vo.train.LearnMapPosnGradeVO;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 学习地图-职级 服务类
 * </p>
 *
 * @author yangye
 * @since 2022-09-06
 */
public interface TrainLearnMapPosnGradeService extends ICrudService<TrainLearnMapPosnGrade, Long> {
    /**
     * 删除学习地图下的职级
     * @param mapId
     * @return
     */
    Boolean deleteByMapId(Long mapId);

    /**
     * 查询学习地图下的职级列表
     * @param mapId
     * @return
     */
    List<LearnMapPosnGradeVO> queryListByMapId(Long mapId);

    /**
     * 根据学员当前地图ID和职级ID获取高一级职级或低一级职级
     * @param mapId
     * @param posnGradeCode
     * @param gradeType
     * @return
     */
    String getHighOrLowGradeCode(Long mapId, String posnGradeCode, String gradeType);

    /**
     * 学习地图职级选择列表
     * @param mapId
     * @return
     */
    List<LearnMapPosnGradeSelectVO> mapGradeListByMapId(Long mapId);

    /**
     * 根据地图ID和职级编码获得
     * @param mapId
     * @param posnGradeCode
     * @return
     */
    TrainLearnMapPosnGrade getInfoByMap(Long mapId, String posnGradeCode);

    /**
     * Excel地图职级下拉项
     * @param mapId
     * @return
     */
    List<String> selectBoxList(Long mapId);
}
