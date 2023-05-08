package com.dyys.hr.dao.train;

import com.dagongma.mybatis.core.dao.ICrudMapper;
import com.dyys.hr.entity.train.TrainExaminer;
import com.dyys.hr.entity.train.excel.OfflineExamResultsExcel;
import com.dyys.hr.vo.exam.ExamCenterVO;
import com.dyys.hr.vo.train.TrainExaminerVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface TrainExaminerMapper extends ICrudMapper<TrainExaminer> {
    List<TrainExaminerVO> getListByQuery(Map<String,Object> params);

    void deleteByParams(Map<String,Object> params);

    Integer restExam(Map<String,Object> params);

    /**
     * 获取考试中心列表（进行中）
     * @param params 参数
     * @return
     */
    List<ExamCenterVO> listExamCenter(Map<String, Object> params);

    /**
     * 获取考试中心列表（提交的）
     * @param params 参数
     * @return
     */
    List<ExamCenterVO> listExamCommit(Map<String, Object> params);

    List<String> allExamUserIds(Long examId);

    /**
     * 导入成绩模板-获取某个考试的参考人员
     * @param examId
     * @return
     */
    List<OfflineExamResultsExcel> getExaminerListTml(Long examId);

    /**
     * 取员工在某个培训项目某个课程的最后一次考试成绩
     * @param userId
     * @param programsId
     * @param courseId
     * @return
     */
    TrainExaminer getFinalExamResults(@Param("userId") String userId, @Param("programsId") Long programsId, @Param("courseId") Long courseId);
}