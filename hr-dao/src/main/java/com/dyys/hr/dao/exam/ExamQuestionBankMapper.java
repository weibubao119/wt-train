package com.dyys.hr.dao.exam;

import com.dagongma.mybatis.core.dao.ICrudMapper;
import com.dyys.hr.entity.exam.ExamQuestionBank;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 题库信息 Mapper 接口
 * </p>
 *
 * @author lidaan
 * @since 2022-04-20
 */
@Mapper
public interface ExamQuestionBankMapper extends ICrudMapper<ExamQuestionBank> {

}
