package com.dyys.hr.service.train;

import com.dagongma.mybatis.core.service.ICrudService;
import com.dyys.hr.entity.train.TrainBaseTeacherProgress;
import com.dyys.hr.entity.train.excel.BaseTeacherProgressExcel;
import com.dyys.hr.vo.train.TrainBaseTeacherProgressVO;

import java.util.List;
import java.util.Map;

public interface TrainBaseTeacherProgressService extends ICrudService<TrainBaseTeacherProgress, Long> {
    /**
     * 讲师历程列表
     * @param teacherId
     * @return
     */
    List<TrainBaseTeacherProgressVO> getProgressByTeacherId(Long teacherId);

    /**
     * 删除
     * @param teacherId
     */
    void deleteByTeacherId(Long teacherId);
}