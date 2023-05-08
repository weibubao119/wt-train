package com.dyys.hr.dao.train;

import com.dagongma.mybatis.core.dao.ICrudMapper;
import com.dyys.hr.entity.train.TrainProgramsTeacher;
import com.dyys.hr.vo.train.TrainProgramsTeacherDetailVO;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface TrainProgramsTeacherMapper extends ICrudMapper<TrainProgramsTeacher> {
    List<TrainProgramsTeacherDetailVO> getDetailList(Map<String, Object> params);

    void deleteByParams(Map<String,Object> params);
}