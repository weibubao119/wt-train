package com.dyys.hr.dao.staff;

import com.dagongma.mybatis.core.dao.ICrudMapper;
import com.dyys.hr.entity.staff.StaffProject;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 项目经历表 Mapper 接口
 * </p>
 *
 * @author yangye
 * @since 2022-08-23
 */
@Mapper
public interface StaffProjectMapper extends ICrudMapper<StaffProject> {

}
