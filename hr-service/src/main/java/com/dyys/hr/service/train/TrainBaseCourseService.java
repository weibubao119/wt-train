package com.dyys.hr.service.train;

import com.dagongma.mybatis.core.service.ICrudService;
import com.dyys.hr.dto.train.TrainBaseCourseDTO;
import com.dyys.hr.entity.train.TrainBaseCourse;
import com.dyys.hr.entity.train.excel.AbleTeacherExcel;
import com.dyys.hr.entity.train.excel.BaseCourseExcel;
import com.dyys.hr.entity.train.excel.BaseCourseListExcel;
import com.dyys.hr.vo.train.TrainBaseCourseVO;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

public interface TrainBaseCourseService extends ICrudService<TrainBaseCourse, Long> {
    /**
     * 课程管理分页
     * @param params
     * @return
     */
    PageInfo<TrainBaseCourseVO> pageList(Map<String, Object> params);

    /**
     * 导出课程列表
     * @param params
     * @return
     */
    List<BaseCourseListExcel> courseListExport(Map<String, Object> params);

    /**
     * 创建课程
     * @param dto
     * @param loginUserId
     * @return
     */
    Long save(TrainBaseCourseDTO dto,String loginUserId);

    /**
     * 课程详情
     * @param courseId
     * @return
     */
    TrainBaseCourseDTO selectByCourseId(Long courseId);

    Integer update(TrainBaseCourseDTO dto,String loginUserId);

    PageInfo<Map<String,Object>> selectList(Map<String, Object> params);

    /**
     * 获取所有课程
     * @return
     */
    List<TrainBaseCourse> allCourseList();

    /**
     * 根据课程编码查询可用课程信息
     * @param courseNumber
     * @return
     */
    TrainBaseCourse getInfoByNumber(String courseNumber);

    /**
     * 导出课程可授课讲师
     * @param params
     * @return
     */
    List<AbleTeacherExcel> ableTeacherList(Map<String, Object> params);

    /**
     * excel模板中下拉项
     * @return
     */
    Map<Integer, List<String>> excelSelectMap();

    /**
     * 课程批量导入
     * @param excelList
     * @param loginEmplId
     * @return
     */
    List<BaseCourseExcel> importExl(List<BaseCourseExcel> excelList, String loginEmplId);

    /**
     * 校验课程数据唯一
     * @param dto
     * @return
     */
    Boolean checkUniqueData(TrainBaseCourseDTO dto);

    /**
     * 课程材料学习页数据
     * @param params
     * @return
     */
    TrainBaseCourseVO materialsLearningPageData(Map<String, Object> params);
}