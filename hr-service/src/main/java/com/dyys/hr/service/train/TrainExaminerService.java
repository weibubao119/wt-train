package com.dyys.hr.service.train;

import com.dagongma.kernel.core.entity.PageView;
import com.dagongma.mybatis.core.service.ICrudService;
import com.dyys.hr.dto.train.IdDTO;
import com.dyys.hr.entity.train.TrainExaminer;
import com.dyys.hr.entity.train.excel.OfflineExamResultsExcel;
import com.dyys.hr.entity.train.excel.TrainExamResultImportExcel;
import com.dyys.hr.vo.exam.ExamCenterVO;
import com.dyys.hr.vo.train.TrainExamResultImportExcelVO;
import com.dyys.hr.vo.train.TrainExaminerVO;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

public interface TrainExaminerService extends ICrudService<TrainExaminer, Long> {
    List<TrainExaminerVO> getListByQuery(Map<String,Object> params);

    void deleteByParams(Map<String,Object> params);

    //退回重考
    Integer restExam(Map<String,Object> params);

    /**
     * 分页获取考试中心列表
     * @return
     */
    PageView<ExamCenterVO> pageExamCenter(Map<String, Object> params);

    /**
     * 根据考试id和用户id得到考试人员主键
     * @param examId 考试id
     * @param userId 用户id
     * @return 考试人员表主键
     */
    TrainExaminer getExaminer(String examId, String userId);

    /**
     * 获取所有参考人员id
     * @param examId
     * @return
     */
    List<String> allExamUserIds(Long examId);

    /**
     * 导入成绩模板-获取某个考试的参考人员
     * @param examId
     * @return
     */
    List<OfflineExamResultsExcel> getExaminerListTml(Long examId);

    /**
     * 导入线下考试成绩
     * @param excelList
     * @param examId
     * @param loginEmplId
     * @return
     */
    List<OfflineExamResultsExcel> importExl(List<OfflineExamResultsExcel> excelList, Long examId, String loginEmplId);

    /**
     * 取员工在某个培训项目某个课程的最后一次考试成绩
     * @param userId
     * @param programsId
     * @param courseId
     * @return
     */
    TrainExaminer getFinalExamResults(String userId, Long programsId, Long courseId);

    /**
     * 批量通知考试
     * @param dtoList
     * @param loginUserId
     * @return
     */
    Boolean batchExamNotice(List<IdDTO> dtoList, String loginUserId);


    /**
     * 考试结果导入数据处理
     * @param excelList
     * @param examId
     * @return
     */
    TrainExamResultImportExcelVO handleResultImportExcel(List<TrainExamResultImportExcel> excelList, Long examId, String loginUserId) throws ParseException;

}