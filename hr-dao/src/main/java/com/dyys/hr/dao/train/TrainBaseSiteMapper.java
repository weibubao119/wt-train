package com.dyys.hr.dao.train;

import com.dagongma.mybatis.core.dao.ICrudMapper;
import com.dyys.hr.dto.train.TrainBaseSiteDTO;
import com.dyys.hr.entity.train.TrainBaseSite;
import com.dyys.hr.entity.train.excel.TrainSiteExcel;
import com.dyys.hr.vo.train.TrainBaseSiteVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 
 * 场地接口
 * @author JUCHUAN LI
 * @date 2019/06/26
 */ 
@Repository
public interface TrainBaseSiteMapper extends ICrudMapper<TrainBaseSite> {
    List<TrainBaseSiteVO> pageList(Map<String, Object> params);

    TrainBaseSiteDTO selectBySiteId(@Param("id") Integer siteId);

    List<Map<String,Object>> selectList(Map<String, Object> params);

    /**
     * 同一场地名称的总量
     * @param siteName
     * @param siteId
     * @return
     */
    Integer selectCountByName(@Param("siteName") String siteName, @Param("siteId") Integer siteId);

    /**
     * 导出培训场地
     * @param params
     * @return
     */
    List<TrainSiteExcel> exportSiteList(Map<String, Object> params);
}