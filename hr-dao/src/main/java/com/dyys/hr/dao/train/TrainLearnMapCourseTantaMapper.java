package com.dyys.hr.dao.train;

import com.dagongma.mybatis.core.dao.ICrudMapper;
import com.dyys.hr.entity.train.TrainLearnMapCourseTanta;
import com.dyys.hr.vo.train.LearnMapCourseTantaVO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 学习地图-同等课程 Mapper 接口
 * </p>
 *
 * @author yangye
 * @since 2022-09-06
 */
@Repository
public interface TrainLearnMapCourseTantaMapper extends ICrudMapper<TrainLearnMapCourseTanta> {
    /**
     * 同等课程列表
     * @param mapCourseId
     * @return
     */
    List<LearnMapCourseTantaVO> queryListByMapCourseId(Long mapCourseId);

    /**
     * 查询课程的同等课程数量
     * @param courseId
     * @return
     */
    Integer queryTotalByCourseId(Long courseId);
}
