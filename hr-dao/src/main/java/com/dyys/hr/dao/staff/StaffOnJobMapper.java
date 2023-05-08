package com.dyys.hr.dao.staff;

import com.dagongma.mybatis.core.dao.ICrudMapper;
import com.dyys.hr.entity.staff.StaffOnJob;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 在职信息表 Mapper 接口
 * </p>
 *
 * @author yangye
 * @since 2022-08-23
 */
@Mapper
public interface StaffOnJobMapper extends ICrudMapper<StaffOnJob> {

}
