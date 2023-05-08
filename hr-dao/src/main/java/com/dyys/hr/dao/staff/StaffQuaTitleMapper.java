package com.dyys.hr.dao.staff;

import com.dagongma.mybatis.core.dao.ICrudMapper;
import com.dyys.hr.entity.staff.StaffQuaTitle;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 资格等级信息 - 职称信息表 Mapper 接口
 * </p>
 *
 * @author yangye
 * @since 2022-08-23
 */
@Mapper
public interface StaffQuaTitleMapper extends ICrudMapper<StaffQuaTitle> {

}
