package com.dyys.hr.service.train.impl;

import com.dagongma.mybatis.core.service.impl.AbstractCrudService;
import com.dyys.hr.dao.train.TrainLearnMapCourseTantaMapper;
import com.dyys.hr.entity.train.TrainLearnMapCourseTanta;
import com.dyys.hr.service.train.TrainLearnMapCourseTantaService;
import com.dyys.hr.vo.train.LearnMapCourseTantaVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 学习地图-同等课程 服务实现类
 * </p>
 *
 * @author yangye
 * @since 2022-09-06
 */
@Service
public class TrainLearnMapCourseTantaServiceImpl extends AbstractCrudService<TrainLearnMapCourseTanta, Long> implements TrainLearnMapCourseTantaService {
    @Autowired
    private TrainLearnMapCourseTantaMapper trainLearnMapCourseTantaMapper;

    /**
     * 同等课程列表
     * @param mapCourseId
     * @return
     */
    @Override
    public List<LearnMapCourseTantaVO> queryListByMapCourseId(Long mapCourseId) {
        return trainLearnMapCourseTantaMapper.queryListByMapCourseId(mapCourseId);
    }

    /**
     * 删除当前课程的同等课程
     * @param courseId
     * @return
     */
    @Override
    public Boolean deleteByMapCourseId(Long courseId) {
        int total = trainLearnMapCourseTantaMapper.queryTotalByCourseId(courseId);
        if (total > 0) {
            TrainLearnMapCourseTanta tanta = new TrainLearnMapCourseTanta();
            tanta.setMapCourseId(courseId);
            int res = trainLearnMapCourseTantaMapper.delete(tanta);
            return res == total;
        }
        return total == 0;
    }
}
