package com.dyys.hr.service.staff;


import com.dagongma.mybatis.core.service.ICrudService;
import com.dyys.hr.entity.staff.StaffMainPost;
import com.github.pagehelper.PageInfo;

import java.util.Map;

/**
 * <p>
 * 标准岗位表 服务类
 * </p>
 *
 * @author yangye
 * @since 2022-08-23
 */
public interface IStaffMainPostService extends ICrudService<StaffMainPost, String> {
    /**
     * 标准岗位筛选列表
     * @param params
     * @return
     */
    PageInfo<Map<String, Object>> selectList(Map<String, Object> params);
}
