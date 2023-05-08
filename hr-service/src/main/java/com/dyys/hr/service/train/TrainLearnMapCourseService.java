package com.dyys.hr.service.train;

import com.dagongma.mybatis.core.service.ICrudService;
import com.dyys.hr.dto.train.LearnMapCourseDTO;
import com.dyys.hr.entity.train.TrainLearnMapCourse;
import com.dyys.hr.entity.train.excel.MapCourseExcel;
import com.dyys.hr.vo.train.EmployeeRecommendCourseListVO;
import com.dyys.hr.vo.train.LearnMapCourseVO;
import com.dyys.hr.vo.train.LearnMapStuCourseInfoVO;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 学习地图-课程 服务类
 * </p>
 *
 * @author yangye
 * @since 2022-09-06
 */
public interface TrainLearnMapCourseService extends ICrudService<TrainLearnMapCourse, Long> {
    /**
     * 学习地图详情-职级课程列表
     * @param params
     * @return
     */
    List<LearnMapCourseVO> mapInfoCourseList(Map<String, Object> params);

    /**
     * 校验学习地图课程唯一性数据
     * @param learnMapCourseDTO
     * @param checkType
     * @return
     */
    String checkUniqueData(LearnMapCourseDTO learnMapCourseDTO, String checkType);

    /**
     * 学习地图课程添加
     * @param learnMapCourseDTO
     * @param loginEmplId
     * @param sourceType
     * @return
     */
    Long mapCourseAdd(LearnMapCourseDTO learnMapCourseDTO, String loginEmplId, Integer sourceType);

    /**
     * 学习地图课程更新
     * @param learnMapCourseDTO
     * @param loginEmplId
     * @return
     */
    Integer mapCourseUpdate(LearnMapCourseDTO learnMapCourseDTO, String loginEmplId);

    /**
     * 学员推荐课程
     * @param mapId
     * @param posnGradeCode
     * @param sblIdList
     * @param emplId
     * @return
     */
    LearnMapStuCourseInfoVO studentInfoCourseList(Long mapId, String posnGradeCode, List<Integer> sblIdList, String emplId);

    /**
     * 批量导入课程
     * @param excelList
     * @param mapId
     * @param loginEmplId
     * @return
     */
    List<MapCourseExcel> importCourse(List<MapCourseExcel> excelList, Long mapId, String loginEmplId);

    /**
     * 学习地图课程删除
     * @param id
     * @return
     */
    Integer mapCourseDelById(Long id);

    /**
     * 获取员工推荐课程
     * @param params
     * @param loginEmplId
     * @return
     */
    PageInfo<EmployeeRecommendCourseListVO> recommendCourseList(Map<String, Object> params, String loginEmplId);
}
