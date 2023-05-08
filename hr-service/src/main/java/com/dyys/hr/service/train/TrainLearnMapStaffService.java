package com.dyys.hr.service.train;

import com.dagongma.kernel.core.entity.PageView;
import com.dagongma.mybatis.core.service.ICrudService;
import com.dyys.hr.dto.train.LearnMapStaffDTO;
import com.dyys.hr.entity.train.TrainLearnMapStaff;
import com.dyys.hr.entity.train.excel.MapStuExcel;
import com.dyys.hr.vo.train.LearnMapStaffInfoVO;
import com.dyys.hr.vo.train.LearnMapStaffVO;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 学员地图 服务类
 * </p>
 *
 * @author yangye
 * @since 2022-09-06
 */
public interface TrainLearnMapStaffService extends ICrudService<TrainLearnMapStaff, Long> {
    /**
     * 学员地图分页列表
     * @param params
     * @return
     */
    PageView<LearnMapStaffVO> studentMapPageList(Map<String, Object> params);

    /**
     * 学员地图更新
     * @param learnMapStaffDTO
     * @param loginEmplId
     * @return
     */
    Long studentMapUpdate(LearnMapStaffDTO learnMapStaffDTO, String loginEmplId);

    /**
     * 学员学习地图详情(含课程列表)
     * @param emplId
     * @return
     */
    LearnMapStaffInfoVO studentMapInfo(String emplId);

    /**
     * 批量导入学员地图数据
     * @param excelList
     * @param loginEmplId
     * @return
     */
    List<MapStuExcel> batchImportStuMap(List<MapStuExcel> excelList, String loginEmplId);
}
