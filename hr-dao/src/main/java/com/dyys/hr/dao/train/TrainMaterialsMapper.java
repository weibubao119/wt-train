package com.dyys.hr.dao.train;

import com.dagongma.mybatis.core.dao.ICrudMapper;
import com.dyys.hr.entity.train.TrainMaterials;
import com.dyys.hr.vo.train.TrainMaterialsVO;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface TrainMaterialsMapper extends ICrudMapper<TrainMaterials> {
    List<TrainMaterialsVO> pageList(Map<String, Object> params);

    /**
     * 统计资料已学人数
     * @param programsId
     * @return
     */
    Integer totalLearningNumByProgramsId(Long programsId);


    /**
     * 获取已发布培训班资料列表
     * @param programsId
     * @return
     */
    List<TrainMaterialsVO> getMaterialsByProgramsId(Long programsId);

    /**
     * 根据条件获取所有材料文件名
     * @param params
     * @return
     */
    List<String> getMaterialsNamesByQuery(Map<String, Object> params);
}