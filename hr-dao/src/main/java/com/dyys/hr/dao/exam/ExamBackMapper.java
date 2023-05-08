package com.dyys.hr.dao.exam;

import com.dagongma.mybatis.core.dao.ICrudMapper;
import com.dyys.hr.entity.exam.ExamBack;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 回退重考 Mapper 接口
 * </p>
 *
 * @author lidaan
 * @since 2022-04-22
 */
@Mapper
public interface ExamBackMapper extends ICrudMapper<ExamBack> {

}
