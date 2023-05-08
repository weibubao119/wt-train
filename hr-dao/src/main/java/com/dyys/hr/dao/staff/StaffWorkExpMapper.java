package com.dyys.hr.dao.staff;


import com.dagongma.mybatis.core.dao.ICrudMapper;
import com.dyys.hr.entity.staff.StaffWorkExp;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 工作履历信息 - 外部工作经历表 Mapper 接口
 * </p>
 *
 * @author yangye
 * @since 2022-08-31
 */
@Mapper
public interface StaffWorkExpMapper extends ICrudMapper<StaffWorkExp> {

}
