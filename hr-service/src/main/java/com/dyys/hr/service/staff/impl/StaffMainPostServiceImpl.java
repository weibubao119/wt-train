package com.dyys.hr.service.staff.impl;


import cn.hutool.core.convert.Convert;
import com.dagongma.mybatis.core.service.impl.AbstractCrudService;
import com.dyys.hr.dao.staff.StaffMainPostMapper;
import com.dyys.hr.entity.staff.StaffMainPost;
import com.dyys.hr.service.staff.IStaffMainPostService;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 标准岗位表 服务实现类
 * </p>
 *
 * @author yangye
 * @since 2022-08-23
 */
@Service
public class StaffMainPostServiceImpl extends AbstractCrudService<StaffMainPost, String> implements IStaffMainPostService {
    @Autowired
    private StaffMainPostMapper staffMainPostMapper;
    /**
     * 标准岗位筛选列表
     * @param params
     * @return
     */
    @Override
    public PageInfo<Map<String, Object>> selectList(Map<String, Object> params) {
        int page = Convert.toInt(params.get("page"));
        int limit = Convert.toInt(params.get("limit"));
        PageMethod.startPage(page, limit);
        List<Map<String,Object>> voList = staffMainPostMapper.selectList(params);
        return new PageInfo<>(voList);
    }
}
