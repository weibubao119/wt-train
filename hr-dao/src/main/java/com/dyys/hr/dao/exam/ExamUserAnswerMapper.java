package com.dyys.hr.dao.exam;

import com.dagongma.mybatis.core.dao.ICrudMapper;
import com.dyys.hr.entity.exam.ExamUserAnswer;
import com.dyys.hr.entity.train.excel.UserExamDetailExcel;
import com.dyys.hr.vo.exam.ExamAnswerDetailsVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 员工答卷备选答案 Mapper 接口
 * </p>
 *
 * @author lidaan
 * @since 2022-04-20
 */
@Mapper
public interface ExamUserAnswerMapper extends ICrudMapper<ExamUserAnswer> {
    /**
     *
     * @param detailsId 根据考试记录id得到考试结果
     * @return 考试记录id
     */
    List<ExamAnswerDetailsVO> listByDetailsId(@Param("detailsId") String detailsId);

    void deleteByQuery(Map<String, Object> query);

    List<UserExamDetailExcel> listUserExamDetail(@Param("detailsId") String detailsId);
}
