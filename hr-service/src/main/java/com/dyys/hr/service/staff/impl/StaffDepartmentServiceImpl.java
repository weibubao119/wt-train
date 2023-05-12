package com.dyys.hr.service.staff.impl;

import cn.hutool.core.convert.Convert;
import cn.hutool.json.JSONUtil;
import com.dagongma.mybatis.core.service.impl.AbstractCrudService;
import com.dagongma.redis.common.util.RedisUtil;
import com.dyys.hr.constants.Constant;
import com.dyys.hr.dao.staff.StaffDepartmentMapper;
import com.dyys.hr.entity.staff.StaffDepartment;
import com.dyys.hr.entity.sys.SysUserToken;
import com.dyys.hr.entity.train.excel.DeptTerseExcel;
import com.dyys.hr.service.staff.IStaffDepartmentService;
import com.dyys.hr.vo.common.PsDepartmentVO;
import com.dyys.hr.vo.common.StaffDepartmentVO;
import com.dyys.hr.vo.common.StaffDeptTerseVO;
import com.dyys.hr.vo.train.CommonDepartmentVO;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 部门表 服务实现类
 * </p>
 *
 * @author yangye
 * @since 2022-08-23
 */
@Service
public class StaffDepartmentServiceImpl extends AbstractCrudService<StaffDepartment, String> implements IStaffDepartmentService {
    @Autowired
    private StaffDepartmentMapper staffDepartmentMapper;

    @Autowired
    private RedisUtil redisUtil;

    /**
     * 部门筛选列表
     * @param params
     * @return
     */
    @Override
    public List<PsDepartmentVO> selectList(Map<String, Object> params) {
        return staffDepartmentMapper.selectList(params);
    }

    /**
     * 部门组织树（通用版）
     * @param params
     * @return
     */
    @Override
    public List<StaffDepartmentVO> departmentCrumb(Map<String, Object> params){
        String deptId = "";
        String mapKeyName = "deptId";
        if(params.containsKey(mapKeyName) && params.get(mapKeyName) != null && !params.get(mapKeyName).equals("")){
            deptId = params.get(mapKeyName).toString();
        }
        String key = String.format(Constant.DEPT_CRUMB, deptId);
        String jsonStr = (String) redisUtil.get(key);
        List<StaffDepartmentVO> departmentCache;
        if (jsonStr == null) {
            departmentCache = staffDepartmentMapper.deptTreeList(deptId);
            String deptJsonStr = JSONUtil.toJsonStr(departmentCache);
            redisUtil.set(key, deptJsonStr,24 * 60 * 60 * 1000L);
        } else {
            departmentCache = JSONUtil.toList(jsonStr, StaffDepartmentVO.class);
        }
        return departmentCache;
    }

    /**
     * 部门组织树（员工版）
     * @param params
     * @param user
     * @return
     */
    @Override
    public List<StaffDepartmentVO> deptEmplCrumb(Map<String, Object> params, SysUserToken user) {
        List<String> userDeptList = user.getDeptList(); // 员工权限部门ID集
        String deptId = "";
        String mapKeyName = "deptId";
        if(params.containsKey(mapKeyName) && params.get(mapKeyName) != null && !params.get(mapKeyName).equals("")){
            deptId = params.get(mapKeyName).toString();
        }
        String emplId = user.getUserId();
        String redisKey = String.format(Constant.DEPT_CRUMB, deptId + "-" + emplId);
        String jsonStr = (String) redisUtil.get(redisKey);
        List<StaffDepartmentVO> deptEmplCache;
        if (jsonStr == null) {
            deptEmplCache = this.departmentCrumb(params);
            if (!deptEmplCache.isEmpty()) {
                List<StaffDepartmentVO> deptEmplList = handleChoiceFlag(deptEmplCache, userDeptList);
                String deptJsonStr = JSONUtil.toJsonStr(deptEmplList);
                redisUtil.set(redisKey, deptJsonStr,24 * 60 * 60 * 1000L);
                return deptEmplList;
            }
        } else {
            deptEmplCache = JSONUtil.toList(jsonStr, StaffDepartmentVO.class);
        }
        return deptEmplCache;
    }

    /**
     * 公司级部门组织树（通用版）
     * @param params
     * @return
     */
    @Override
    public List<StaffDepartmentVO> deptCompanyList(Map<String, Object> params) {
        String deptId = "";
        String mapKeyName = "deptId";
        if(params.containsKey(mapKeyName) && params.get(mapKeyName) != null && !params.get(mapKeyName).equals("")){
            deptId = params.get(mapKeyName).toString();
        }
        String key = String.format(Constant.DEPT_COMPANY_CRUMB, deptId);
        String jsonStr = (String) redisUtil.get(key);
        List<StaffDepartmentVO> deptCompanyCache;
        if (jsonStr == null) {
            deptCompanyCache = staffDepartmentMapper.deptCompanyTreeList(deptId);
            String deptJsonStr = JSONUtil.toJsonStr(deptCompanyCache);
            redisUtil.set(key, deptJsonStr,24 * 60 * 60 * 1000L);
        } else {
            deptCompanyCache = JSONUtil.toList(jsonStr, StaffDepartmentVO.class);
        }
        return deptCompanyCache;
    }

    /**
     * 公司级部门组织树（员工版）
     * @param params
     * @param user
     * @return
     */
    @Override
    public List<StaffDepartmentVO> deptCompanyEmplList(Map<String, Object> params, SysUserToken user) {
        List<String> userDeptList = user.getDeptList(); // 员工权限部门ID集
        String deptId = "";
        String mapKeyName = "deptId";
        if(params.containsKey(mapKeyName) && params.get(mapKeyName) != null && !params.get(mapKeyName).equals("")){
            deptId = params.get(mapKeyName).toString();
        }
        String emplId = user.getUserId();
        String redisKey = String.format(Constant.DEPT_COMPANY_CRUMB, deptId + "-" + emplId);
        String jsonStr = (String) redisUtil.get(redisKey);
        List<StaffDepartmentVO> deptEmplCache;
        if (jsonStr == null) {
            deptEmplCache = this.deptCompanyList(params);
            if (!deptEmplCache.isEmpty()) {
                List<StaffDepartmentVO> deptEmplList = handleChoiceFlag(deptEmplCache, userDeptList);
                String deptJsonStr = JSONUtil.toJsonStr(deptEmplList);
                redisUtil.set(redisKey, deptJsonStr,24 * 60 * 60 * 1000L);
                return deptEmplList;
            }
        } else {
            deptEmplCache = JSONUtil.toList(jsonStr, StaffDepartmentVO.class);
        }
        return deptEmplCache;
    }

    /**
     * 获取当前部门的下一级部门列表
     * @param deptId
     * @param selfFlag 是否报含当前部门：1包含
     * @return
     */
    @Override
    public List<StaffDeptTerseVO> getChildDeptById(String deptId, Integer selfFlag) {
        List<StaffDeptTerseVO> list = staffDepartmentMapper.getChildDeptById(deptId);
        if (selfFlag != null && selfFlag.equals(1)) {
            StaffDepartment department = selectById(deptId);
            StaffDeptTerseVO vo = new StaffDeptTerseVO();
            vo.setDeptId(department.getId());
            vo.setDeptName(department.getDescr());
            list.add(0, vo);
        }
        return list;
    }

    /**
     * 组织单位列表
     * @return
     */
    @Override
    public List<DeptTerseExcel> getDeptList() {
        return staffDepartmentMapper.getDeptList();
    }

    /**
     * 处理组织树可选标识
     * @param deptEmpl
     * @param userDeptList
     * @return
     */
    private List<StaffDepartmentVO> handleChoiceFlag(List<StaffDepartmentVO> deptEmpl, List<String> userDeptList) {
        for (StaffDepartmentVO vo : deptEmpl) {
            // 若员工权限部门不为空且不包括当前部门ID，则不可选
            if (!userDeptList.isEmpty() && !userDeptList.contains(vo.getDeptId())) {
                vo.setDisabled(true);
            }
            List<StaffDepartmentVO> childrenList = vo.getChildren();
            if (!childrenList.isEmpty()) {
                vo.setChildren(handleChoiceFlag(childrenList, userDeptList));
            }
        }
        return deptEmpl;
    }


    /**
     * 组织架构列表
     * @param params
     * @return
     */
    public PageInfo<CommonDepartmentVO> departmentList(Map<String, Object> params){
        int page = Convert.toInt(params.get("page"));
        int limit = Convert.toInt(params.get("limit"));
        PageMethod.startPage(page, limit);
        List<CommonDepartmentVO> voList = staffDepartmentMapper.departmentList(params);
        return new PageInfo<>(voList);
    }
}
