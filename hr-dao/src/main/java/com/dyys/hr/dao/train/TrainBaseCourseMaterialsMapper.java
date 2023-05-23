package com.dyys.hr.dao.train;

import com.dagongma.mybatis.core.dao.ICrudMapper;
import com.dyys.hr.dto.train.TrainBaseCourseMaterialsDTO;
import com.dyys.hr.entity.train.TrainBaseCourseMaterials;
import com.dyys.hr.vo.train.TrainBaseCourseMaterialsVO;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface TrainBaseCourseMaterialsMapper extends ICrudMapper<TrainBaseCourseMaterials> {
    List<TrainBaseCourseMaterialsDTO> getSelectByCourseId(Long courseId);

    void deleteByCourseId(Long courseId);

    Integer totalLearningNumByCourseId(Long courseId);


    /**
     * 获取课程材料分类分组
     * @param courseId
     * @return
     */
    List<String> getGroupMaterialsCategory(Long courseId);


    /**
     * 根据条件获取课程资料列表
     * @param params
     * @return
     */
    List<TrainBaseCourseMaterialsVO> getMaterialsByQuery(Map<String, Object> params);
}