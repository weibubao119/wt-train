package com.dyys.hr.dao.staff;

import com.dagongma.mybatis.core.dao.ICrudMapper;
import com.dyys.hr.entity.staff.StaffDepartment;
import com.dyys.hr.entity.train.excel.DeptTerseExcel;
import com.dyys.hr.vo.common.PsDepartmentVO;
import com.dyys.hr.vo.common.StaffDepartmentVO;
import com.dyys.hr.vo.common.StaffDeptTerseVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 部门表 Mapper 接口
 * </p>
 *
 * @author yangye
 * @since 2022-08-23
 */
@Mapper
public interface StaffDepartmentMapper extends ICrudMapper<StaffDepartment> {
    /**
     * 部门筛选列表
     * @param params
     * @return
     */
    List<PsDepartmentVO> selectList(Map<String, Object> params);

    /**
     * 获取详情
     * @param id
     * @return
     */
    StaffDepartmentVO selectById(String id);

    /**
     * 获取子部门
     * @param deptId
     * @return
     */
    List<StaffDepartmentVO> getDeptChildren(String deptId);

    /**
     * 查找部门负责人
     * @param emplid
     * @return
     */
    List<StaffDepartment> queryOrgByParams(String emplid);

    /**
     * 部门组织树
     * @param deptId
     * @return
     */
    List<StaffDepartmentVO> deptTreeList(String deptId);

    /**
     * 公司级部门组织树
     * @param deptId
     * @return
     */
    List<StaffDepartmentVO> deptCompanyTreeList(String deptId);

    /**
     * 获取当前部门的下一级部门列表
     * @param deptId
     * @return
     */
    List<StaffDeptTerseVO> getChildDeptById(String deptId);

    /**
     * 组织单位列表
     * @return
     */
    List<DeptTerseExcel> getDeptList();
}
