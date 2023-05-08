package com.dyys.hr.service.train.impl;

import cn.hutool.core.convert.Convert;
import com.dagongma.mybatis.core.service.impl.AbstractCrudService;
import com.dyys.hr.dao.train.TrainBaseCourseTeacherMapper;
import com.dyys.hr.dto.train.TeacherDTO;
import com.dyys.hr.entity.train.TrainBaseCourseTeacher;
import com.dyys.hr.service.train.TrainBaseCourseTeacherService;
import com.dyys.hr.vo.train.TeacherCanTeachingRecordsVO;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class TrainBaseCourseTeacherServiceImpl extends AbstractCrudService<TrainBaseCourseTeacher, Long> implements TrainBaseCourseTeacherService{
    @Autowired
    private TrainBaseCourseTeacherMapper trainBaseCourseTeacherMapper;

    @Override
    public List<TeacherDTO> getSelectByCourseId(Long courseId){
        return trainBaseCourseTeacherMapper.getSelectByCourseId(courseId);
    }

    @Override
    public void deleteByCourseId(Long courseId){
        trainBaseCourseTeacherMapper.deleteByCourseId(courseId);
    }

    @Override
    public List<TeacherCanTeachingRecordsVO> canTeachingRecords(Map<String, Object> params){
        return trainBaseCourseTeacherMapper.canTeachingRecords(params);
    }

    @Override
    public PageInfo<Map<String,Object>> selectList(Map<String, Object> params){
        int page = Convert.toInt(params.get("page"));
        int limit = Convert.toInt(params.get("limit"));
        PageMethod.startPage(page, limit);
        List<Map<String,Object>> voList = trainBaseCourseTeacherMapper.selectList(params);
        return new PageInfo<>(voList);
    }


}