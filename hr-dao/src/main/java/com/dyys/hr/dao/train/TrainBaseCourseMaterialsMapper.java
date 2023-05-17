package com.dyys.hr.dao.train;

import com.dagongma.mybatis.core.dao.ICrudMapper;
import com.dyys.hr.dto.train.TrainBaseCourseMaterialsDTO;
import com.dyys.hr.entity.train.TrainBaseCourseMaterials;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface TrainBaseCourseMaterialsMapper extends ICrudMapper<TrainBaseCourseMaterials> {
    List<TrainBaseCourseMaterialsDTO> getSelectByCourseId(Map<Object, Object> params);

    void deleteByCourseId(Long courseId);

    Integer totalLearningNumByCourseId(Long courseId);
}