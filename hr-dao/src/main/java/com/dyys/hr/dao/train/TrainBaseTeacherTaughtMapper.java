package com.dyys.hr.dao.train;

import com.dagongma.mybatis.core.dao.ICrudMapper;
import com.dyys.hr.entity.train.TrainBaseTeacherTaught;
import com.dyys.hr.vo.train.TeacherHaveTaughtRecordsVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师已授课程记录 Mapper 接口
 * </p>
 *
 * @author yangye
 * @since 2023-01-10
 */
@Mapper
public interface TrainBaseTeacherTaughtMapper extends ICrudMapper<TrainBaseTeacherTaught> {
    /**
     * 获取某个讲师的已授课程记录
     * @param params
     * @return
     */
    List<TeacherHaveTaughtRecordsVO> taughtCourseList(Map<String, Object> params);
}
