package com.dyys.hr.service.train.impl;

import com.dagongma.mybatis.core.service.impl.AbstractCrudService;
import com.dyys.hr.dao.train.TrainLearnMapPosnGradeMapper;
import com.dyys.hr.entity.train.TrainLearnMapPosnGrade;
import com.dyys.hr.service.train.TrainLearnMapPosnGradeService;
import com.dyys.hr.vo.train.LearnMapPosnGradeSelectVO;
import com.dyys.hr.vo.train.LearnMapPosnGradeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 学习地图-职级 服务实现类
 * </p>
 *
 * @author yangye
 * @since 2022-09-06
 */
@Service
public class TrainLearnMapPosnGradeServiceImpl extends AbstractCrudService<TrainLearnMapPosnGrade, Long> implements TrainLearnMapPosnGradeService {
    @Autowired
    private TrainLearnMapPosnGradeMapper trainLearnMapPosnGradeMapper;

    /**
     * 删除学习地图下的职级
     * @param mapId
     * @return
     */
    @Override
    public Boolean deleteByMapId(Long mapId) {
        int total = trainLearnMapPosnGradeMapper.queryTotalByMapId(mapId);
        if (total > 0) {
            TrainLearnMapPosnGrade trainLearnMapPosnGrade = new TrainLearnMapPosnGrade();
            trainLearnMapPosnGrade.setMapId(mapId);
            int res = trainLearnMapPosnGradeMapper.delete(trainLearnMapPosnGrade);
            return res == total;
        }
        return true;
    }

    /**
     * 查询学习地图下的职级列表
     * @param mapId
     * @return
     */
    @Override
    public List<LearnMapPosnGradeVO> queryListByMapId(Long mapId) {
        return trainLearnMapPosnGradeMapper.queryListByMapId(mapId);
    }

    /**
     * 根据学员当前地图ID和职级ID获取高一级职级或低一级职级
     * @param mapId
     * @param posnGradeCode
     * @param gradeType
     * @return
     */
    @Override
    public String getHighOrLowGradeCode(Long mapId, String posnGradeCode, String gradeType) {
        String gradeCode = "";
        if (gradeType.equals("High")) {
            TrainLearnMapPosnGrade posnGrade = new TrainLearnMapPosnGrade();
            posnGrade.setMapId(mapId);
            posnGrade.setPosnGradeCode(posnGradeCode);
            TrainLearnMapPosnGrade currentInfo = selectOne(posnGrade);
            if (null != currentInfo && !currentInfo.getPPosnGradeCode().equals(posnGradeCode)) {
                gradeCode = currentInfo.getPPosnGradeCode();
            }
        } else {
            LearnMapPosnGradeVO gradeVO = trainLearnMapPosnGradeMapper.queryLowInfo(mapId, posnGradeCode);
            if (null != gradeVO) {
                gradeCode = gradeVO.getPosnGradeCode();
            }
        }
        return gradeCode;
    }

    /**
     * 学习地图职级选择列表
     * @param mapId
     * @return
     */
    @Override
    public List<LearnMapPosnGradeSelectVO> mapGradeListByMapId(Long mapId) {
        return trainLearnMapPosnGradeMapper.mapGradeListByMapId(mapId);
    }

    /**
     * 根据地图ID和职级编码获得
     * @param mapId
     * @param posnGradeCode
     * @return
     */
    @Override
    public TrainLearnMapPosnGrade getInfoByMap(Long mapId, String posnGradeCode) {
        TrainLearnMapPosnGrade gradeQuery = new TrainLearnMapPosnGrade();
        gradeQuery.setMapId(mapId);
        gradeQuery.setPosnGradeCode(posnGradeCode);
        return selectOne(gradeQuery);
    }

    /**
     * Excel地图职级下拉项
     * @param mapId
     * @return
     */
    @Override
    public List<String> selectBoxList(Long mapId) {
        List<LearnMapPosnGradeSelectVO> list = this.mapGradeListByMapId(mapId);
        List<String> choiceList = new ArrayList<>();
        if (!list.isEmpty()) {
            for (LearnMapPosnGradeSelectVO vo : list) {
                choiceList.add(vo.getPosnGradeCodeName());
            }
        }
        return choiceList;
    }
}
