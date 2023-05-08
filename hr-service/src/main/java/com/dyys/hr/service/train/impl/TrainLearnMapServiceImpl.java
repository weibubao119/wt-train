package com.dyys.hr.service.train.impl;

import cn.hutool.core.convert.Convert;
import com.dagongma.kernel.core.entity.PageView;
import com.dagongma.mybatis.core.service.impl.AbstractCrudService;
import com.dyys.hr.dao.train.TrainLearnMapMapper;
import com.dyys.hr.dto.train.LearnMapDTO;
import com.dyys.hr.dto.train.LearnMapPosnGradeDTO;
import com.dyys.hr.entity.train.TrainLearnMap;
import com.dyys.hr.entity.train.TrainLearnMapPosnGrade;
import com.dyys.hr.service.train.TrainConstantService;
import com.dyys.hr.service.train.TrainLearnMapPosnGradeService;
import com.dyys.hr.service.train.TrainLearnMapService;
import com.dyys.hr.vo.train.LearnMapInfoVO;
import com.dyys.hr.vo.train.LearnMapSelectVO;
import com.dyys.hr.vo.train.LearnMapVO;
import com.github.pagehelper.page.PageMethod;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 学习地图 服务实现类
 * </p>
 *
 * @author yangye
 * @since 2022-09-06
 */
@Service
@Slf4j
public class TrainLearnMapServiceImpl extends AbstractCrudService<TrainLearnMap, Long> implements TrainLearnMapService {
    @Autowired
    private TrainLearnMapMapper trainLearnMapMapper;
    @Autowired
    private TrainLearnMapPosnGradeService trainLearnMapPosnGradeService;
    @Autowired
    private TrainConstantService trainConstantService;

    /**
     * 学习地图分页列表
     * @param params
     * @return
     */
    @Override
    public PageView<LearnMapVO> mapPageList(Map<String, Object> params) {
        PageMethod.startPage(Convert.toInt(params.get("page")), Convert.toInt(params.get("limit")));
        List<LearnMapVO> mapVOList = trainLearnMapMapper.mapList(params);
        return PageView.build(mapVOList);
    }

    /**
     * 校验学习地图唯一性数据
     * @param learnMapDTO
     * @param checkType
     * @return
     */
    @Override
    public String checkUniqueData(LearnMapDTO learnMapDTO, String checkType) {
        Long mapId = null;
        if (checkType.equals("update")) {
            mapId = learnMapDTO.getId();
        }
        int mapCodeCount = trainLearnMapMapper.countByMapCode(learnMapDTO.getMapCode(), mapId);
        if (mapCodeCount > 0) {
            return "当前地图编码已存在，请更换";
        }
        return "";
    }

    /**
     * 学习地图添加
     * @param learnMapDTO
     * @param loginEmplId
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long mapAdd(LearnMapDTO learnMapDTO, String loginEmplId) {
        Long stamp = System.currentTimeMillis()/1000; // 系统当前时间戳
        List<LearnMapPosnGradeDTO> posnGradeList = learnMapDTO.getPosnGradeList(); // 职级列表
        TrainLearnMap trainLearnMap = new TrainLearnMap();
        BeanUtils.copyProperties(learnMapDTO,trainLearnMap);
        trainLearnMap.setCreateUser(loginEmplId);
        trainLearnMap.setCreateTime(stamp);
        trainLearnMap.setUpdateUser(loginEmplId);
        trainLearnMap.setUpdateTime(stamp);
        Long mapId = this.insertSelective(trainLearnMap);
        if (null != mapId && mapId > 0L) {
            List<TrainLearnMapPosnGrade> entityList = this.assembleMapData(posnGradeList, mapId, learnMapDTO.getMapCode(), loginEmplId);
            boolean res = trainLearnMapPosnGradeService.insertBatchSelective(entityList);
            if (res) {
                return mapId;
            }
        }
        return null;
    }

    /**
     * 学习地图更新
     * @param learnMapDTO
     * @param loginEmplId
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer mapUpdate(LearnMapDTO learnMapDTO, String loginEmplId) {
        TrainLearnMap trainLearnMap = new TrainLearnMap();
        BeanUtils.copyProperties(learnMapDTO,trainLearnMap);
        trainLearnMap.setUpdateUser(loginEmplId);
        trainLearnMap.setUpdateTime(System.currentTimeMillis()/1000);
        int res = this.updateSelective(trainLearnMap);
        if (res > 0) {
            boolean delRes = trainLearnMapPosnGradeService.deleteByMapId(learnMapDTO.getId());
            if (delRes) {
                List<TrainLearnMapPosnGrade> entityList = this.assembleMapData(learnMapDTO.getPosnGradeList(), learnMapDTO.getId(), learnMapDTO.getMapCode(), loginEmplId);
                trainLearnMapPosnGradeService.insertBatchSelective(entityList);
            }
        }
        return res;
    }

    /**
     * 学习地图详情
     * @param id
     * @return
     */
    @Override
    public LearnMapInfoVO mapInfo(Long id) {
        LearnMapInfoVO mapInfoVO = trainLearnMapMapper.queryInfoById(id);
        mapInfoVO.setPosnGradeList(trainLearnMapPosnGradeService.queryListByMapId(id));
        return mapInfoVO;
    }

    /**
     * Excel导出地图课程模板下拉选项
     * @param mapId
     * @return
     */
    @Override
    public Map<Integer, List<String>> excelSelectMap(Long mapId) {
        TrainLearnMap learnMap = this.selectById(mapId);
        Map<Integer, List<String>> selectMap = new HashMap<>();
        // 地图职级下拉项
        List<String> gradeList = trainLearnMapPosnGradeService.selectBoxList(mapId);
        selectMap.put(0, gradeList);

        // 学习方向下拉项
        List<String> sblList = trainConstantService.selectBoxList(7, learnMap.getPosnSecCode());
        selectMap.put(1, sblList);

        // 必修标识下拉项
        List<String> requiredFlagList = new ArrayList<>();
        requiredFlagList.add("0");
        requiredFlagList.add("1");
        selectMap.put(5, requiredFlagList);

        return selectMap;
    }

    /**
     * 学习地图选择列表
     * @param params
     * @return
     */
    @Override
    public List<LearnMapSelectVO> mapSelectList(Map<String, Object> params) {
        List<LearnMapSelectVO> mapSelectList = trainLearnMapMapper.mapSelectList(params);
        for (LearnMapSelectVO vo : mapSelectList) {
            vo.setMapGradeList(trainLearnMapPosnGradeService.mapGradeListByMapId(vo.getId()));
            vo.setMapSblList(trainConstantService.selectListByMap(vo.getPosnSecCode()));
        }
        return mapSelectList;
    }

    /**
     * 根据地图编码获取地图信息
     * @param mapCode
     * @return
     */
    @Override
    public TrainLearnMap mapInfoByCode(String mapCode) {
        TrainLearnMap mapQuery = new TrainLearnMap();
        mapQuery.setMapCode(mapCode);
        return selectOne(mapQuery);
    }

    /**
     * 组装学习地图职级数据
     * @param posnGradeList
     * @param mapId
     * @param mapCode
     * @param loginEmplId
     * @return
     */
    private List<TrainLearnMapPosnGrade> assembleMapData(List<LearnMapPosnGradeDTO> posnGradeList, Long mapId, String mapCode, String loginEmplId) {
        Long stamp = System.currentTimeMillis()/1000; // 系统当前时间戳
        List<TrainLearnMapPosnGrade> gradeList = new ArrayList<>();
        for (LearnMapPosnGradeDTO dto : posnGradeList) {
            TrainLearnMapPosnGrade posnGrade = new TrainLearnMapPosnGrade();
            BeanUtils.copyProperties(dto, posnGrade);
            posnGrade.setMapId(mapId);
            posnGrade.setMapCode(mapCode);
            posnGrade.setCreateUser(loginEmplId);
            posnGrade.setUpdateUser(loginEmplId);
            posnGrade.setCreateTime(stamp);
            posnGrade.setUpdateTime(stamp);
            gradeList.add(posnGrade);
        }
        return gradeList;
    }
}
