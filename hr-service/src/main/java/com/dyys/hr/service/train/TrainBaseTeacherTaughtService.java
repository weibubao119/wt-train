package com.dyys.hr.service.train;

import com.dagongma.mybatis.core.service.ICrudService;
import com.dyys.hr.entity.train.TrainBaseTeacherTaught;
import com.dyys.hr.vo.train.TeacherHaveTaughtRecordsVO;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师已授课程记录 服务类
 * </p>
 *
 * @author yangye
 * @since 2023-01-10
 */
public interface TrainBaseTeacherTaughtService extends ICrudService<TrainBaseTeacherTaught, Long> {
    /**
     * 获取某个讲师的已授课程记录
     * @param params
     * @return
     */
    List<TeacherHaveTaughtRecordsVO> taughtCourseList(Map<String, Object> params);
}
