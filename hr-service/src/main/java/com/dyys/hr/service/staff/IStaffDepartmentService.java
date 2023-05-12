package com.dyys.hr.service.staff;


import com.dagongma.mybatis.core.service.ICrudService;
import com.dyys.hr.entity.staff.StaffDepartment;
import com.dyys.hr.entity.sys.SysUserToken;
import com.dyys.hr.entity.train.excel.DeptTerseExcel;
import com.dyys.hr.vo.common.PsDepartmentVO;
import com.dyys.hr.vo.common.StaffDepartmentVO;
import com.dyys.hr.vo.common.StaffDeptTerseVO;
import com.dyys.hr.vo.train.CommonDepartmentVO;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 部门表 服务类
 * </p>
 *
 * @author yangye
 * @since 2022-08-23
 */
public interface IStaffDepartmentService extends ICrudService<StaffDepartment, String> {
    /**
     * 部门筛选列表
     * @param params
     * @return
     */
    List<PsDepartmentVO> selectList(Map<String, Object> params);

    /**
     * 部门组织树（通用版）
     * @param params
     * @return
     */
    List<StaffDepartmentVO> departmentCrumb(Map<String, Object> params);

    /**
     * 部门组织树（员工版）
     * @param params
     * @param user
     * @return
     */
    List<StaffDepartmentVO> deptEmplCrumb(Map<String, Object> params, SysUserToken user);

    /**
     * 公司级部门组织树（通用版）
     * @return
     */
    List<StaffDepartmentVO> deptCompanyList(Map<String, Object> params);

    /**
     * 公司级部门组织树（员工版）
     * @param params
     * @param user
     * @return
     */
    List<StaffDepartmentVO> deptCompanyEmplList(Map<String, Object> params, SysUserToken user);

    /**
     * 获取当前部门的下一级部门列表
     * @param deptId
     * @param selfFlag 是否报含当前部门：1包含
     * @return
     */
    List<StaffDeptTerseVO> getChildDeptById(String deptId, Integer selfFlag);

    /**
     * 组织单位列表
     * @return
     */
    List<DeptTerseExcel> getDeptList();

    /**
     * 组织架构列表
     * @param params
     * @return
     */
    PageInfo<CommonDepartmentVO> departmentList(Map<String, Object> params);
}
