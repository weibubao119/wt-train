package com.dyys.hr.service.train;

import com.dagongma.kernel.core.entity.PageView;
import com.dagongma.mybatis.core.service.ICrudService;
import com.dyys.hr.dto.train.TrainInstitutionDTO;
import com.dyys.hr.entity.train.TrainInstitution;
import com.dyys.hr.entity.train.excel.InstitutionExcel;
import com.dyys.hr.vo.train.InstitutionExcelVO;
import com.dyys.hr.vo.train.TrainInstitutionHistoryServiceVO;
import com.dyys.hr.vo.train.TrainInstitutionVO;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 培训机构 服务类
 * </p>
 *
 * @author yangye
 * @since 2022-05-11
 */
public interface TrainInstitutionService extends ICrudService<TrainInstitution, Long> {
    /**
     * 培训机构分页列表
     * @param params
     * @return
     */
    PageView<TrainInstitutionVO> institutionListPage(Map<String, Object> params);

    /**
     * 培训机构详情
     * @param institutionId
     * @return
     */
    TrainInstitutionVO institutionInfo(Long institutionId);

    /**
     * 校验培训机构名称/地址
     * @param institutionDTO
     * @param checkType
     * @return
     */
    String checkNameAndAddress(TrainInstitutionDTO institutionDTO, String checkType);

    /**
     * 培训机构添加保存
     * @param institutionDTO
     * @param loginEmplId
     * @return
     */
    Long institutionAdd(TrainInstitutionDTO institutionDTO, String loginEmplId);

    /**
     * 培训机构更新保存
     * @param institutionDTO
     * @param loginEmplId
     * @return
     */
    Integer institutionUpdate(TrainInstitutionDTO institutionDTO, String loginEmplId);

    /**
     * 培训机构选择列表
     * @param params
     * @return
     */
    PageInfo<Map<String,Object>> selectList(Map<String, Object> params);

    /**
     * 服务历史分页列表
     * @param params
     * @return
     */
    PageView<TrainInstitutionHistoryServiceVO> historyServicePageList(Map<String, Object> params);

    /**
     * 更新机构年度评级
     * @param institutionId
     * @param yearly
     * @param gradeId
     * @return
     */
    Boolean updateGrade(Long institutionId, String yearly, Integer gradeId);

    /**
     * 培训机构excel模板下拉选项
     * @return
     */
    Map<Integer, List<String>> excelSelectMap();

    /**
     * 批量导入培训机构
     * @param excelList
     * @param loginEmplId
     * @return
     */
    List<InstitutionExcel> importInst(List<InstitutionExcel> excelList, String loginEmplId);

    /**
     * 根据名称或地址查询机构信息
     * @param nameOrAddr
     * @param checkType
     * @return
     */
    TrainInstitution infoByNameOrAddr(String nameOrAddr, Integer checkType);

    /**
     * 培训机构导出数据
     * @param params
     * @return
     */
    List<InstitutionExcel> exportInst(Map<String, Object> params);
}
