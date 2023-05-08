package com.dyys.hr.dao.train;

import com.dagongma.mybatis.core.dao.ICrudMapper;
import com.dyys.hr.entity.train.TrainBaseTeacherProgress;
import com.dyys.hr.entity.train.excel.BaseTeacherProgressExcel;
import com.dyys.hr.vo.train.TrainBaseTeacherProgressVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface TrainBaseTeacherProgressMapper extends ICrudMapper<TrainBaseTeacherProgress> {
    List<TrainBaseTeacherProgressVO> getProgressByTeacherId(@Param("teacherId") Long teacherId);

    void deleteByTeacherId(Long teacherId);
}