package com.dyys.hr.dao.train;
import com.dagongma.mybatis.core.dao.ICrudMapper;
import com.dyys.hr.entity.train.TrainData;
import com.dyys.hr.vo.statis.PosnGradeCostVO;
import com.dyys.hr.vo.train.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 培训集合数据表
 *
 * @author sie sie
 * @since 1.0.0 2022-09-01
 */
@Repository
public interface TrainDataMapper extends ICrudMapper<TrainData> {
    List<ELearningPageVO> eLearningPageList(Map<String, Object> params);

    List<HistoryDataPageVO> historyDataPageList(Map<String, Object> params);

    List<String> groupInfoByQuery(Map<String, Object> params);

    List<EmplTrainListVO> getGroupTrainList(Map<String, Object> params);

    List<EmplTrainCourseListVO> getTrainCourseList(Map<String, Object> params);

    List<EmplELearningListVO> emplELearningList(Map<String, Object> params);

    /**
     * 员工完成地图推荐指定课程或同等课程的数量
     * @param courseNums
     * @param emplId
     * @return
     */
    Integer countFinishCourse(@Param("courseNums") List<String> courseNums, @Param("emplId") String emplId);

    List<TrainData> getList(Map<String, Object> params);

    /**
     * 历史数据同步-获取同一培训项目同一课程的最小开始时间
     * @param params
     * @return
     */
    Date getMinCourseStartTime(Map<String, Object> params);

    /**
     * 历史数据同步-获取同一培训项目同一课程的最大结束时间
     * @param params
     * @return
     */
    Date getMaxCourseEndTime(Map<String, Object> params);

    /**
     * 获取培训数据中历史数据同一培训项目的最大培训费用
     * @param trainNumber
     * @param type
     * @return
     */
    Float getMaxOutlay(@Param("trainNumber") String trainNumber, @Param("type") Integer type);

    /**
     * 获取培训数据中历史数据同一培训项目参与人数
     * @param trainNumber
     * @param type
     * @return
     */
    Integer getPartTol(@Param("trainNumber") String trainNumber, @Param("type") Integer type);

    /**
     * 按职级参训人员人次统计
     * @param mapParams
     * @return
     */
    PosnGradeCostVO countNumByGradeCode(Map<String, Object> mapParams);
}