package com.dyys.hr.service.train;

import com.dagongma.kernel.core.entity.PageView;
import com.dagongma.mybatis.core.service.ICrudService;
import com.dyys.hr.dto.train.LearnMapDTO;
import com.dyys.hr.entity.train.TrainLearnMap;
import com.dyys.hr.vo.train.LearnMapInfoVO;
import com.dyys.hr.vo.train.LearnMapSelectVO;
import com.dyys.hr.vo.train.LearnMapStaffInfoVO;
import com.dyys.hr.vo.train.LearnMapVO;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 学习地图 服务类
 * </p>
 *
 * @author yangye
 * @since 2022-09-06
 */
public interface TrainLearnMapService extends ICrudService<TrainLearnMap, Long> {
    /**
     * 学习地图分页列表
     * @param params
     * @return
     */
    PageView<LearnMapVO> mapPageList(Map<String, Object> params);

    /**
     * 校验学习地图唯一性数据
     * @param learnMapDTO
     * @param checkType
     * @return
     */
    String checkUniqueData(LearnMapDTO learnMapDTO, String checkType);

    /**
     * 学习地图添加
     * @param learnMapDTO
     * @param loginEmplId
     * @return
     */
    Long mapAdd(LearnMapDTO learnMapDTO, String loginEmplId);

    /**
     * 学习地图更新
     * @param learnMapDTO
     * @param loginEmplId
     * @return
     */
    Integer mapUpdate(LearnMapDTO learnMapDTO, String loginEmplId);

    /**
     * 学习地图详情
     * @param id
     * @return
     */
    LearnMapInfoVO mapInfo(Long id);

    /**
     * Excel导出地图课程模板下拉选项
     * @param mapId
     * @return
     */
    Map<Integer, List<String>> excelSelectMap(Long mapId);

    /**
     * 学习地图选择列表
     * @param params
     * @return
     */
    List<LearnMapSelectVO> mapSelectList(Map<String, Object> params);

    /**
     * 根据地图编码获取地图信息
     * @param mapCode
     * @return
     */
    TrainLearnMap mapInfoByCode(String mapCode);
}
