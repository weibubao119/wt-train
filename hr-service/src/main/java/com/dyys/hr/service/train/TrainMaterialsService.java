package com.dyys.hr.service.train;

import com.dagongma.mybatis.core.service.ICrudService;
import com.dyys.hr.dto.train.TrainMaterialsDTO;
import com.dyys.hr.entity.train.TrainMaterials;
import com.dyys.hr.vo.train.TrainMaterialsLearnVO;
import com.dyys.hr.vo.train.TrainMaterialsVO;
import com.github.pagehelper.PageInfo;

import java.util.Map;

public interface TrainMaterialsService extends ICrudService<TrainMaterials, Long> {
    PageInfo<TrainMaterialsVO> pageList(Map<String, Object> params);

    Long save(TrainMaterialsDTO dto, String loginUserId);

    /**
     * 批量发布
     * @param programsId
     * @param loginUserId
     * @return
     */
    Integer batchChangeStatus(Long programsId, String loginUserId);

    /**
     * 培训班课程带出材料
     * @param programsId
     * @param loginUserId
     * @return
     */
    Boolean courseBroughtOut(Long programsId,String loginUserId);

    /**
     * 推送学习
     * @param programsId
     * @param loginUserId
     * @return
     */
    Boolean pushLearningNotice(Long programsId,String loginUserId);


    /**
     * 材料学习页数据
     * @param params
     * @return
     */
    TrainMaterialsLearnVO materialsLearningPageData(Map<String, Object> params);
}