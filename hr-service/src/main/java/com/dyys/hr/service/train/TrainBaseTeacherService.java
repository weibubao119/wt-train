package com.dyys.hr.service.train;

import com.dagongma.mybatis.core.service.ICrudService;
import com.dyys.hr.dto.train.TrainBaseTeacherDTO;
import com.dyys.hr.entity.train.TrainBaseTeacher;
import com.dyys.hr.entity.train.excel.*;
import com.dyys.hr.vo.train.TrainBaseTeacherDetailVO;
import com.dyys.hr.vo.train.TrainBaseTeacherVO;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

public interface TrainBaseTeacherService extends ICrudService<TrainBaseTeacher, Long> {
    /**
     * 讲师列表
     * @param params
     * @return
     */
    PageInfo<TrainBaseTeacherVO> pageList(Map<String, Object> params);

    /**
     * 新增
     * @param dto
     * @param loginUserId
     * @return
     */
    Long save(TrainBaseTeacherDTO dto,String loginUserId);

    /**
     * 讲师详情
     * @param teacherId
     * @return
     */
    TrainBaseTeacherDetailVO selectByTeacherId(Long teacherId);

    /**
     * 更新
     * @param dto
     * @param loginUserId
     * @return
     */
    Integer update(TrainBaseTeacherDTO dto,String loginUserId);

    /**
     * 公告基础讲师
     * @param params
     * @return
     */
    PageInfo<Map<String,Object>> selectList(Map<String, Object> params);

    /**
     * 根据讲师编码获取讲师信息
     * @param teacherNumber
     * @return
     */
    TrainBaseTeacher queryInfoByNumber(String teacherNumber);

    /**
     * 讲师管理列表导出
     * @param params
     * @return
     */
    List<BaseTeacherListExcel> teacherListExport(Map<String, Object> params);

    /**
     * excel模板中下拉项
     * @return
     */
    Map<Integer, List<String>> excelSelectMap();

    /**
     * 讲师批量导入
     * @param excelList
     * @param loginEmplId
     * @return
     */
    List<BaseTeacherExcel> importExl(List<BaseTeacherExcel> excelList, String loginEmplId);

    /**
     * 校验教师信息系统唯一
     * @param dto
     * @return
     */
    Boolean checkUniqueData(TrainBaseTeacherDTO dto);

    /**
     * 导出讲师成长历程数据
     * @param params
     * @return
     */
    List<BaseTeacherProgressExcel> progressListExport(Map<String, Object> params);

    /**
     * 导出讲师可授课程
     * @param params
     * @return
     */
    List<AbleCourseExcel> ableCourseListExport(Map<String, Object> params);

    /**
     * 导出讲师已授课程
     * @param params
     * @return
     */
    List<TaughtCourseExcel> taughtCourseListExport(Map<String, Object> params);
}