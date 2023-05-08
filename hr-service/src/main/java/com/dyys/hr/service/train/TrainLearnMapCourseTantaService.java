package com.dyys.hr.service.train;

import com.dagongma.mybatis.core.service.ICrudService;
import com.dyys.hr.dto.train.LearnMapCourseTantaDTO;
import com.dyys.hr.entity.train.TrainLearnMapCourseTanta;
import com.dyys.hr.vo.train.LearnMapCourseTantaVO;

import java.util.List;

/**
 * <p>
 * 学习地图-同等课程 服务类
 * </p>
 *
 * @author yangye
 * @since 2022-09-06
 */
public interface TrainLearnMapCourseTantaService extends ICrudService <TrainLearnMapCourseTanta, Long> {
    /**
     * 同等课程列表
     * @param mapCourseId
     * @return
     */
    List<LearnMapCourseTantaVO> queryListByMapCourseId(Long mapCourseId);

    /**
     * 删除当前课程的同等课程
     * @param courseId
     * @return
     */
    Boolean deleteByMapCourseId(Long courseId);
}
