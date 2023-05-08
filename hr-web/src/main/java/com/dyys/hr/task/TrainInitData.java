package com.dyys.hr.task;

import cn.hutool.json.JSONUtil;
import com.dagongma.redis.common.util.RedisUtil;
import com.dyys.hr.constants.Constant;
import com.dyys.hr.dao.staff.StaffDepartmentMapper;
import com.dyys.hr.vo.common.StaffDepartmentVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author wsj
 * @projectName hr-recruit
 * @date 2023/3/715:03
 */
/*@Component*/
@Order(1)
public class TrainInitData implements ApplicationRunner {
    @Autowired
    private StaffDepartmentMapper staffDepartmentMapper;
    @Autowired
    private RedisUtil redisUtil;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        // 全集团部门组织树
        String deptId = "";
        String key = String.format(Constant.DEPT_CRUMB, deptId);
        List<StaffDepartmentVO> departmentCache = staffDepartmentMapper.deptTreeList(deptId);
        String deptJsonStr = JSONUtil.toJsonStr(departmentCache);
        redisUtil.set(key,deptJsonStr, 24 * 60 * 60 * 1000L);

        // 全集团公司级部门组织树
        String comKey = String.format(Constant.DEPT_COMPANY_CRUMB, deptId);
        List<StaffDepartmentVO> deptCompanyCache = staffDepartmentMapper.deptCompanyTreeList(deptId);
        String deptComJsonStr = JSONUtil.toJsonStr(deptCompanyCache);
        redisUtil.set(comKey,deptComJsonStr, 24 * 60 * 60 * 1000L);
        System.out.println("deal dept cache success");
    }
}
