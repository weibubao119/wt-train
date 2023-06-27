package com.dyys.hr.service.staff;


import com.dagongma.mybatis.core.service.ICrudService;
import com.dyys.hr.entity.staff.StaffJob;
import com.dyys.hr.entity.train.excel.ParticipantsExcel;
import com.dyys.hr.vo.common.PsDictVO;
import com.dyys.hr.vo.train.EmplInfoVO;
import com.dyys.hr.vo.train.StudentFilesPageVO;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.RequestParam;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 职务数据表 服务类
 * </p>
 *
 * @author yangye
 * @since 2022-08-23
 */
public interface IStaffJobService extends ICrudService<StaffJob, String> {
    /**
     * 员工字典列表
     * @param params
     * @return
     */
    List<PsDictVO> employeeDictList(Map<String, Object> params);

    /**
     * 学历等级列表
     * @param params
     * @return
     */
    List<PsDictVO> eduLevelList(Map<String, Object> params);

    /**
     * 学员档案分页列表
     * @param params
     * @return
     */
    PageInfo<StudentFilesPageVO> studentsList(Map<String, Object> params);

    /**
     * 学员基本信息
     * @param params
     * @return
     */
    EmplInfoVO emplInfo(Map<String, Object> params);

    /**
     * 员工职务信息
     * @param emplId
     * @return
     */
    StaffJob getInfoByEmplId(String emplId);

    /**
     * excel导入数据处理
     * @param excelList
     * @return
     */
    Map<String, List<ParticipantsExcel>> importExl(List<ParticipantsExcel> excelList);

    /**
     * 基础字典下拉选项
     * @param typeName
     * @return
     */
    List<String> selectDictBoxList(String typeName);
}
