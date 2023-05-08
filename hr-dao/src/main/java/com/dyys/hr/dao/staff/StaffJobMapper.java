package com.dyys.hr.dao.staff;

import com.dagongma.mybatis.core.dao.ICrudMapper;
import com.dyys.hr.entity.staff.StaffJob;
import com.dyys.hr.vo.common.PsDictVO;
import com.dyys.hr.vo.train.EmplInfoVO;
import com.dyys.hr.vo.train.EmplTrainListVO;
import com.dyys.hr.vo.train.StudentFilesPageVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.web.bind.annotation.RequestParam;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 职务数据表 Mapper 接口
 * </p>
 *
 * @author yangye
 * @since 2022-08-23
 */
@Mapper
public interface StaffJobMapper extends ICrudMapper<StaffJob> {
    /**
     * 员工字典列表
     * @param params
     * @return
     */
    List<PsDictVO> employeeDictList(Map<String, Object> params);

    /**
     * 学员档案分页列表
     * @param params
     * @return
     */
    List<StudentFilesPageVO> studentsList(Map<String, Object> params);

    /**
     * 学员基本信息
     * @param params
     * @return
     */
    EmplInfoVO emplInfo(@ApiIgnore @RequestParam Map<String, Object> params);

    List<EmplTrainListVO> trainList(@ApiIgnore @RequestParam Map<String, Object> params);
}
