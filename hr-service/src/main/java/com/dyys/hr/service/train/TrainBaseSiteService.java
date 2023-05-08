package com.dyys.hr.service.train;

import com.dagongma.mybatis.core.service.ICrudService;
import com.dyys.hr.dto.train.TrainBaseSiteDTO;
import com.dyys.hr.entity.train.TrainBaseSite;
import com.dyys.hr.entity.train.excel.TrainSiteExcel;
import com.dyys.hr.vo.train.TrainBaseSiteUsageVO;
import com.dyys.hr.vo.train.TrainBaseSiteVO;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

/**
 * 
 * 场地接口实现
 * @author WSJ
 */
public interface TrainBaseSiteService extends ICrudService<TrainBaseSite, Integer> {
    PageInfo<TrainBaseSiteVO> pageList(Map<String, Object> params);

    TrainBaseSiteDTO selectBySiteId(Integer siteId);

    PageInfo<Map<String,Object>> selectList(Map<String, Object> params);

    /**
     * 保存校验场地数据
     * @param trainBaseSiteDTO
     * @param checkType
     * @return
     */
    String checkData(TrainBaseSiteDTO trainBaseSiteDTO, String checkType);

    /**
     * 场地详情-场地使用情况
     * @param params
     * @return
     */
    PageInfo<TrainBaseSiteUsageVO> usageList(Map<String, Object> params);

    /**
     * 导入培训场地
     * @param excelList
     * @param loginEmplId
     * @return
     */
    List<TrainSiteExcel> importSite(List<TrainSiteExcel> excelList, String loginEmplId);

    /**
     * 导出培训场地
     * @param params
     * @return
     */
    List<TrainSiteExcel> exportSite(Map<String, Object> params);
}