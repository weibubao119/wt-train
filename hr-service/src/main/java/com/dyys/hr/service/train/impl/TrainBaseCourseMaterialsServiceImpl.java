package com.dyys.hr.service.train.impl;

import com.dagongma.mybatis.core.service.impl.AbstractCrudService;
import com.dyys.hr.dao.train.TrainBaseCourseMaterialsMapper;
import com.dyys.hr.dto.train.TrainBaseCourseMaterialsDTO;
import com.dyys.hr.entity.train.TrainBaseCourseMaterials;
import com.dyys.hr.service.train.TrainBaseCourseMaterialsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class TrainBaseCourseMaterialsServiceImpl extends AbstractCrudService<TrainBaseCourseMaterials, Long> implements TrainBaseCourseMaterialsService {
    @Autowired
    private TrainBaseCourseMaterialsMapper trainBaseCourseMaterialsMapper;

    @Override
    public List<TrainBaseCourseMaterialsDTO> getSelectByCourseId(Long courseId){
        return trainBaseCourseMaterialsMapper.getSelectByCourseId(courseId);
    }

    @Override
    public void deleteByCourseId(Long courseId){
        trainBaseCourseMaterialsMapper.deleteByCourseId(courseId);
    }

    /**
     * 课程已学人数总计
     * @param courseId
     * @return
     */
    @Override
    public Integer totalLearningNumByCourseId(Long courseId){
        return trainBaseCourseMaterialsMapper.totalLearningNumByCourseId(courseId);
    }

}