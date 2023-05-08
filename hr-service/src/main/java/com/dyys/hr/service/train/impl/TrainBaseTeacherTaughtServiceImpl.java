package com.dyys.hr.service.train.impl;

import com.dagongma.mybatis.core.service.impl.AbstractCrudService;
import com.dyys.hr.dao.train.TrainBaseTeacherTaughtMapper;
import com.dyys.hr.entity.train.TrainBaseTeacherTaught;
import com.dyys.hr.service.train.TrainBaseTeacherTaughtService;
import com.dyys.hr.vo.train.TeacherHaveTaughtRecordsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师已授课程记录 服务实现类
 * </p>
 *
 * @author yangye
 * @since 2023-01-10
 */
@Service
public class TrainBaseTeacherTaughtServiceImpl extends AbstractCrudService<TrainBaseTeacherTaught, Long> implements TrainBaseTeacherTaughtService {
    @Autowired
    private TrainBaseTeacherTaughtMapper trainBaseTeacherTaughtMapper;

    /**
     * 获取某个讲师的已授课程记录
     * @param params
     * @return
     */
    @Override
    public List<TeacherHaveTaughtRecordsVO> taughtCourseList(Map<String, Object> params) {
        return trainBaseTeacherTaughtMapper.taughtCourseList(params);
    }
}
