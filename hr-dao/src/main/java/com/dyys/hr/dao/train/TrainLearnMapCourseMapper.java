package com.dyys.hr.dao.train;

import com.dagongma.mybatis.core.dao.ICrudMapper;
import com.dyys.hr.entity.train.TrainLearnMapCourse;
import com.dyys.hr.vo.train.EmployeeRecommendCourseListVO;
import com.dyys.hr.vo.train.LearnMapCourseVO;
import com.dyys.hr.vo.train.LearnMapStuCourseVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 学习地图-课程 Mapper 接口
 * </p>
 *
 * @author yangye
 * @since 2022-09-06
 */
@Repository
public interface TrainLearnMapCourseMapper extends ICrudMapper<TrainLearnMapCourse> {
    /**
     * 学习地图详情-职级课程列表
     * @param params
     * @return
     */
    List<LearnMapCourseVO> queryList(Map<String, Object> params);

    /**
     * 当前地图当前职级当前方向存在此课程的数量
     * @param map
     * @return
     */
    Integer countByCourseNumber(Map<String, Object> map);

    /**
     * 学员推荐课程
     * @param mapId
     * @param posnGradeCode
     * @param sblIdList
     * @return
     */
    List<LearnMapStuCourseVO> studentCourseList(@Param("mapId") Long mapId, @Param("posnGradeCode") String posnGradeCode, @Param("sblIdList") List<Integer> sblIdList);

    /**
     * 获取员工推荐课程
     * @param params
     * @return
     */
    List<EmployeeRecommendCourseListVO> recommendCourseList(Map<String, Object> params);
}
