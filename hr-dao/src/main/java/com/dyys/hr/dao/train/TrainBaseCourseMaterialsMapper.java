package com.dyys.hr.dao.train;

import com.dagongma.mybatis.core.dao.ICrudMapper;
import com.dyys.hr.dto.train.TrainBaseCourseMaterialsDTO;
import com.dyys.hr.entity.train.TrainBaseCourseMaterials;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrainBaseCourseMaterialsMapper extends ICrudMapper<TrainBaseCourseMaterials> {
    List<TrainBaseCourseMaterialsDTO> getSelectByCourseId(Long courseId);

    void deleteByCourseId(Long courseId);

    Integer totalLearningNumByCourseId(Long courseId);
}