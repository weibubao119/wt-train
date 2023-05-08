package com.dyys.hr.dao.train;

import com.dagongma.mybatis.core.dao.ICrudMapper;
import com.dyys.hr.entity.train.TrainInstitution;
import com.dyys.hr.entity.train.excel.InstitutionExcel;
import com.dyys.hr.vo.train.TrainInstitutionVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 培训机构 Mapper 接口
 * </p>
 *
 * @author yangye
 * @since 2022-05-11
 */
@Repository
public interface TrainInstitutionMapper extends ICrudMapper<TrainInstitution> {
    /**
     * 培训机构分页列表
     * @param params
     * @return
     */
    List<TrainInstitutionVO> institutionListPage(Map<String, Object> params);

    /**
     * 培训机构名称存在条数
     * @param name
     * @param institutionId
     * @return
     */
    Integer selectCountByName(@Param("name") String name, @Param("institutionId") Long institutionId);

    /**
     * 培训机构地址存在条数
     * @param address
     * @param institutionId
     * @return
     */
    Integer selectCountByAddress(@Param("address") String address, @Param("institutionId") Long institutionId);

    List<Map<String,Object>> selectList(Map<String, Object> params);

    /**
     * 培训机构详情
     * @param institutionId
     * @return
     */
    TrainInstitutionVO getInfoById(Long institutionId);

    /**
     * 培训机构导出数据
     * @param params
     * @return
     */
    List<InstitutionExcel> exportInst(Map<String, Object> params);
}
