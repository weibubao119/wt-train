package com.dyys.hr.service.train.impl;

import com.dagongma.mybatis.core.service.impl.AbstractCrudService;
import com.dyys.hr.dao.train.TrainProgramsTeacherMapper;
import com.dyys.hr.entity.train.TrainProgramsTeacher;
import com.dyys.hr.service.train.TrainProgramsTeacherService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service
@Slf4j
public class TrainProgramsTeacherServiceImpl extends AbstractCrudService<TrainProgramsTeacher, Long> implements TrainProgramsTeacherService {
    @Autowired
    private TrainProgramsTeacherMapper trainProgramsTeacherMapper;

    @Override
    public void deleteByParams(Map<String,Object> params){
        trainProgramsTeacherMapper.deleteByParams(params);
    }
}