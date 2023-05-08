package com.dyys.hr.task;

import cn.hutool.json.JSONUtil;
import com.dyys.hr.constants.Constant;
import com.dyys.hr.dao.staff.StaffDepartmentMapper;
import com.dyys.hr.vo.common.StaffDepartmentVO;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;
import com.dagongma.redis.common.util.RedisUtil;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Desc
 * Create by yangye
 * Date 2023/3/15 8:53
 */
@Slf4j
@RestController
@Component
@Api(tags="部门组织树redis缓存")
public class DeptTreeTask {
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private StaffDepartmentMapper staffDepartmentMapper;

    /**
     * redis部门组织树和公司级部门组织树
     */
    @Scheduled(cron = "0 0 2 * * ?")

    /**
     * 把组织树存入Redis，有效期24小时
     */
    public void deptTreeRedis(){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date start = new Date();
        System.out.println("tree start------" + df.format(start));

        String deptId = "";
        // redis部门组织树
        String key = String.format(Constant.DEPT_CRUMB, deptId);
        redisUtil.remove(key);
        System.out.println("delete old dept-tree：" + key);
        List<StaffDepartmentVO> deptTreeCache = staffDepartmentMapper.deptTreeList(deptId);
        String deptJsonStr = JSONUtil.toJsonStr(deptTreeCache);
        redisUtil.set(key, deptJsonStr,24 * 60 * 60 * 1000L);
        System.out.println("success dept-tree：" + key);

        // redis公司级部门组织树
        String companyKey = String.format(Constant.DEPT_COMPANY_CRUMB, deptId);
        redisUtil.remove(companyKey);
        System.out.println("delete old dept-company-tree：" + companyKey);
        List<StaffDepartmentVO> deptCompanyTreeCache = staffDepartmentMapper.deptCompanyTreeList(deptId);
        String deptComJsonStr = JSONUtil.toJsonStr(deptCompanyTreeCache);
        redisUtil.set(companyKey, deptComJsonStr,24 * 60 * 60 * 1000L);
        System.out.println("success dept-company-tree：" + companyKey);

        Date end = new Date();
        System.out.println("tree end------" + df.format(end));
    }
}
