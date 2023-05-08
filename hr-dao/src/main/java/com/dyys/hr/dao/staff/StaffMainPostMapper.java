package com.dyys.hr.dao.staff;

import com.dagongma.mybatis.core.dao.ICrudMapper;
import com.dyys.hr.entity.staff.StaffMainPost;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 标准岗位表 Mapper 接口
 * </p>
 *
 * @author yangye
 * @since 2022-08-23
 */
@Mapper
public interface StaffMainPostMapper extends ICrudMapper<StaffMainPost> {
    /**
     * 标准岗位筛选列表
     * @param params
     * @return
     */
    List<Map<String, Object>> selectList(Map<String, Object> params);
}
