package com.dyys.hr.service.train;

import com.dagongma.mybatis.core.service.ICrudService;
import com.dyys.hr.dto.train.ELearningDTO;
import com.dyys.hr.entity.train.TrainData;
import com.dyys.hr.entity.train.excel.TrainHistoryDataExcel;
import com.dyys.hr.vo.statis.PosnGradeCostVO;
import com.dyys.hr.vo.train.*;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.RequestParam;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;
import java.util.Map;

/**
 * 培训集合数据表
 *
 * @author sie sie
 * @since 1.0.0 2022-09-01
 */
public interface TrainDataService extends ICrudService<TrainData, Long> {
    /**
     * e-learning分页数据
     * @param params
     * @return
     */
    PageInfo<ELearningPageVO> eLearningPageList(Map<String, Object> params);

    /**
     * e-learning数据同步
     * @param dataList
     * @return
     */
    Boolean eLearningSave(List<ELearningDTO> dataList);

    /**
     * 培训历史分页数据
     * @param params
     * @return
     */
    PageInfo<HistoryDataPageVO> historyDataPageList(Map<String, Object> params);

    /**
     * 同步培训历史数据
     * @return
     * @param loginEmplId
     */
    Boolean updateHistoryData(String loginEmplId);


    /**
     * 培训记录列表
     * @param params
     * @return
     */
    List<EmplTrainListVO> trainList(@ApiIgnore @RequestParam Map<String, Object> params);

    /**
     * 学员e-learning学习记录
     * @param params
     * @return
     */
    List<EmplELearningListVO> emplELearningList(Map<String, Object> params);

    /**
     * 员工完成地图推荐指定课程或同等课程的数量
     * @param courseNums
     * @param emplId
     * @return
     */
    Integer countFinishCourse(List<String> courseNums, String emplId);

    /**
     * 员工培训数据
     * @param emplId
     * @return
     */
    EmployeeTrainDataVO employTrainData(String emplId);

    /**
     * 培训历史数据excel模板下拉项
     * @return
     */
    Map<Integer, List<String>> excelSelectMap();

    /**
     * 培训历史数据导入
     * @param excelList
     * @param loginEmplId
     * @return
     */
    List<TrainHistoryDataExcel> importExl(List<TrainHistoryDataExcel> excelList, String loginEmplId);

    /**
     * 按职级参训人员人次统计
     * @param mapParams
     * @return
     */
    PosnGradeCostVO countNumByGradeCode(Map<String, Object> mapParams);
}