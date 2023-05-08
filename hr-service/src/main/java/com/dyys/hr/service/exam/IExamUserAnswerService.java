package com.dyys.hr.service.exam;

import com.dagongma.mybatis.core.service.ICrudService;
import com.dyys.hr.entity.exam.ExamUserAnswer;
import com.dyys.hr.entity.train.TrainExaminer;
import com.dyys.hr.entity.train.TrainExaminerDetail;
import com.dyys.hr.entity.train.excel.UserExamDetailExcel;
import com.dyys.hr.vo.exam.ExaminationResultVO;

import java.util.List;

/**
 * <p>
 * 员工答卷备选答案 服务类
 * </p>
 *
 * @author lidaan
 * @since 2022-04-20
 */
public interface IExamUserAnswerService extends ICrudService<ExamUserAnswer, Long> {
    /**
     * 提交试题答案
     * @param examResult 试题答案VO
     */
    TrainExaminerDetail addExamResult(ExaminationResultVO examResult, TrainExaminer examiner);

    /**
     * 获取用户答题记录
     * @param detailId
     * @return
     */
    List<UserExamDetailExcel> listUserExamDetail(String detailId);
}
