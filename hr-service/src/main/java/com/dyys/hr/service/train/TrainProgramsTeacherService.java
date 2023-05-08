package com.dyys.hr.service.train;

import com.dagongma.mybatis.core.service.ICrudService;
import com.dyys.hr.entity.train.TrainProgramsTeacher;

import java.util.Map;

public interface TrainProgramsTeacherService extends ICrudService<TrainProgramsTeacher, Long> {
    void deleteByParams(Map<String,Object> params);
}