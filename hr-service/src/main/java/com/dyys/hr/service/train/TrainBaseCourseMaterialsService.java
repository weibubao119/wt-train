package com.dyys.hr.service.train;

import com.dagongma.mybatis.core.service.ICrudService;
import com.dyys.hr.dto.train.TrainBaseCourseMaterialsDTO;
import com.dyys.hr.entity.train.TrainBaseCourseMaterials;
import java.util.List;

public interface TrainBaseCourseMaterialsService extends ICrudService<TrainBaseCourseMaterials, Long> {
    /**
     * 课程材料列表
     * @param courseId
     * @return
     */
    List<TrainBaseCourseMaterialsDTO> getSelectByCourseId(Long courseId);

    /**
     * 删除课程材料
     * @param courseId
     */
    void deleteByCourseId(Long courseId);

    /**
     * 课程已学人数总计
     * @param courseId
     * @return
     */
    Integer totalLearningNumByCourseId(Long courseId);
}