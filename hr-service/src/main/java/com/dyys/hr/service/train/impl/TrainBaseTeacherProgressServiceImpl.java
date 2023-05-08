package com.dyys.hr.service.train.impl;

import com.dagongma.mybatis.core.service.impl.AbstractCrudService;
import com.dyys.hr.dao.train.TrainBaseTeacherProgressMapper;
import com.dyys.hr.entity.train.TrainBaseTeacherProgress;
import com.dyys.hr.entity.train.excel.BaseTeacherProgressExcel;
import com.dyys.hr.service.train.TrainBaseTeacherProgressService;
import com.dyys.hr.vo.train.TrainBaseTeacherProgressVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class TrainBaseTeacherProgressServiceImpl extends AbstractCrudService<TrainBaseTeacherProgress, Long> implements TrainBaseTeacherProgressService {
    @Autowired
    private TrainBaseTeacherProgressMapper trainBaseTeacherProgressMapper;

    @Override
    public  List<TrainBaseTeacherProgressVO> getProgressByTeacherId(Long teacherId){
        return trainBaseTeacherProgressMapper.getProgressByTeacherId(teacherId);
    }

    @Override
    public void deleteByTeacherId(Long teacherId){
        trainBaseTeacherProgressMapper.deleteByTeacherId(teacherId);
    }
}