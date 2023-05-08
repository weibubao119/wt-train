package com.dyys.hr.service.train.impl;

import cn.hutool.core.convert.Convert;
import com.dagongma.kernel.core.entity.PageView;
import com.dagongma.mybatis.core.service.impl.AbstractCrudService;
import com.dyys.hr.dao.train.TrainInstitutionMapper;
import com.dyys.hr.dto.train.TrainInstitutionDTO;
import com.dyys.hr.entity.train.TrainConstant;
import com.dyys.hr.entity.train.TrainInstitution;
import com.dyys.hr.entity.train.TrainInstitutionGrade;
import com.dyys.hr.entity.train.excel.InstitutionExcel;
import com.dyys.hr.service.train.TrainConstantService;
import com.dyys.hr.service.train.TrainInstitutionGradeService;
import com.dyys.hr.service.train.TrainInstitutionService;
import com.dyys.hr.service.train.TrainProgramsPlanService;
import com.dyys.hr.vo.train.InstitutionExcelVO;
import com.dyys.hr.vo.train.TrainInstitutionHistoryServiceVO;
import com.dyys.hr.vo.train.TrainInstitutionVO;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 培训机构 服务实现类
 * </p>
 *
 * @author yangye
 * @since 2022-05-11
 */
@Service
@Slf4j
public class TrainInstitutionServiceImpl extends AbstractCrudService<TrainInstitution, Long> implements TrainInstitutionService {
    @Autowired
    private TrainInstitutionMapper trainInstitutionMapper;
    @Autowired
    private TrainProgramsPlanService trainProgramsPlanService;
    @Autowired
    private TrainConstantService trainConstantService;
    @Autowired
    private TrainInstitutionGradeService trainInstitutionGradeService;

    /**
     * 培训机构分页列表
     * @param params
     * @return
     */
    @Override
    public PageView<TrainInstitutionVO> institutionListPage(Map<String, Object> params) {
        PageMethod.startPage(Convert.toInt(params.get("page")), Convert.toInt(params.get("limit")));
        List<TrainInstitutionVO> institutionVOList = trainInstitutionMapper.institutionListPage(params);
        return PageView.build(institutionVOList);
    }

    /**
     * 培训机构详情
     * @param institutionId
     * @return
     */
    @Override
    public TrainInstitutionVO institutionInfo(Long institutionId) {
        return trainInstitutionMapper.getInfoById(institutionId);
    }

    /**
     * 校验培训机构名称/地址
     * @param institutionDTO
     * @param checkType
     * @return
     */
    @Override
    public String checkNameAndAddress(TrainInstitutionDTO institutionDTO, String checkType) {
        Long institutionId = null;
        if (checkType.equals("update")) {
            institutionId = institutionDTO.getId();
        }
        int nameCount = trainInstitutionMapper.selectCountByName(institutionDTO.getName(), institutionId);
        if (nameCount > 0) {
            return "当前培训机构名称已存在，请更换";
        }
        /*int addressCount = trainInstitutionMapper.selectCountByAddress(institutionDTO.getAddress(), institutionId);
        if (addressCount > 0) {
            return "当前培训机构地址已存在，请更换";
        }*/
        return "";
    }

    /**
     * 培训机构添加保存
     * @param institutionDTO
     * @param loginEmplId
     * @return
     */
    @Override
    public Long institutionAdd(TrainInstitutionDTO institutionDTO, String loginEmplId) {
        TrainInstitution trainInstitution = new TrainInstitution();
        BeanUtils.copyProperties(institutionDTO,trainInstitution);
        trainInstitution.setIsImport(0);
        trainInstitution.setCreateUser(loginEmplId);
        trainInstitution.setCreateTime(System.currentTimeMillis()/1000);
        trainInstitution.setUpdateUser(loginEmplId);
        trainInstitution.setUpdateTime(System.currentTimeMillis()/1000);
        return insertSelective(trainInstitution);
    }

    /**
     * 培训机构更新保存
     * @param institutionDTO
     * @param loginEmplId
     * @return
     */
    @Override
    public Integer institutionUpdate(TrainInstitutionDTO institutionDTO, String loginEmplId) {
        TrainInstitution trainInstitution = new TrainInstitution();
        BeanUtils.copyProperties(institutionDTO,trainInstitution);
        trainInstitution.setUpdateUser(loginEmplId);
        trainInstitution.setUpdateTime(System.currentTimeMillis()/1000);
        return updateSelective(trainInstitution);
    }

    /**
     * 培训机构选择列表
     * @param params
     * @return
     */
    @Override
    public PageInfo<Map<String,Object>> selectList(Map<String, Object> params){
        int page = Convert.toInt(params.get("page"));
        int limit = Convert.toInt(params.get("limit"));
        PageMethod.startPage(page, limit);
        List<Map<String,Object>> voList = trainInstitutionMapper.selectList(params);
        return new PageInfo<>(voList);
    }

    /**
     * 服务历史分页列表
     * @param params
     * @return
     */
    @Override
    public PageView<TrainInstitutionHistoryServiceVO> historyServicePageList(Map<String, Object> params) {
        int page = Convert.toInt(params.get("page"));
        int limit = Convert.toInt(params.get("limit"));
        PageMethod.startPage(page, limit);
        List<TrainInstitutionHistoryServiceVO> list = trainProgramsPlanService.ProgramsListByInstitutionId(params);
        return PageView.build(list);
    }

    /**
     * 更新机构年度评级
     * @param institutionId
     * @param yearly
     * @param gradeId
     * @return
     */
    @Override
    public Boolean updateGrade(Long institutionId, String yearly, Integer gradeId) {
        TrainInstitution info = selectById(institutionId);
        Integer currentYearly = info.getRatingYear() != null ? Integer.parseInt(info.getRatingYear()) : 0;
        Integer newYearly = Integer.parseInt(yearly);
        if (currentYearly <= newYearly) {
            TrainInstitution institution = new TrainInstitution();
            institution.setId(institutionId);
            institution.setRatingYear(yearly);
            institution.setGradeId(gradeId);
            int res = this.updateSelective(institution);
            return res > 0;
        }
        return true;
    }

    /**
     * 培训机构excel模板下拉选项
     * @return
     */
    @Override
    public Map<Integer, List<String>> excelSelectMap() {
        Map<Integer, List<String>> selectMap = new HashMap<>();

        // 培训机构类型下拉项
        List<String> cateList = trainConstantService.selectBoxList(5, "0");
        selectMap.put(1, cateList);

        // 培训机构等级下拉项
        List<String> gradeList = trainConstantService.selectBoxList(9, "0");
        selectMap.put(6, gradeList);

        return selectMap;
    }

    /**
     * 批量导入培训机构
     * @param excelList
     * @param loginEmplId
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<InstitutionExcel> importInst(List<InstitutionExcel> excelList, String loginEmplId) {
        Long stamp = System.currentTimeMillis()/1000; // 当前时间戳
        List<InstitutionExcel> errorList = new ArrayList<>();
        Map<String, Integer> checkMap = new HashMap<>();
        Map<String, TrainInstitution> instMap = new HashMap<>();
        Map<String, List<TrainInstitutionGrade>> gradeListMap = new HashMap<>();
        Map<String, Integer> gradeIdMap = new HashMap<>();
        Map<String, String> yearlyMap = new HashMap<>();
        List<String> cateList = trainConstantService.selectBoxList(5, "0"); // 机构类型集
        List<String> gradeList = trainConstantService.selectBoxList(9, "0"); // 机构等级集
        int i = 0;
        int errNum = 0;
        for (InstitutionExcel excel : excelList) {
            i++;
            InstitutionExcel errVO = new InstitutionExcel();
            // 判断机构名称是否为空
            if (excel.getName() == null || excel.getName().trim().equals("")) {
                BeanUtils.copyProperties(excel, errVO);
                errVO.setErrMsg("第" + i + "条记录，机构名称为空");
                errorList.add(errVO);
                errNum++;
                continue;
            }
            // 判断机构名称是否已存在
            TrainInstitution instInfo = this.infoByNameOrAddr(excel.getName(), 1);
            if (instInfo != null) {
                BeanUtils.copyProperties(excel, errVO);
                errVO.setErrMsg("第" + i + "条记录，该机构名称已存在系统中");
                errorList.add(errVO);
                errNum++;
                continue;
            }
            // 判断机构类型是否为空
            if (excel.getCateIdName() == null || excel.getCateIdName().trim().equals("")) {
                BeanUtils.copyProperties(excel, errVO);
                errVO.setErrMsg("第" + i + "条记录，机构类型为空");
                errorList.add(errVO);
                errNum++;
                continue;
            }
            // 判断机构类型是否正确
            if (!cateList.contains(excel.getCateIdName())) {
                BeanUtils.copyProperties(excel, errVO);
                errVO.setErrMsg("第" + i + "条记录，机构类型不正确");
                errorList.add(errVO);
                errNum++;
                continue;
            }
            // 判断机构联系人姓名是否为空
            if (excel.getPrincipalName() == null || excel.getPrincipalName().trim().equals("")) {
                BeanUtils.copyProperties(excel, errVO);
                errVO.setErrMsg("第" + i + "条记录，机构联系人姓名为空");
                errorList.add(errVO);
                errNum++;
                continue;
            }
            // 判断机构联系人电话是否为空
            if (excel.getPrincipalPhone() == null || excel.getPrincipalPhone().trim().equals("")) {
                BeanUtils.copyProperties(excel, errVO);
                errVO.setErrMsg("第" + i + "条记录，机构联系人电话为空");
                errorList.add(errVO);
                errNum++;
                continue;
            }
            // 判断机构地址是否为空
            /*if (excel.getAddress() == null || excel.getAddress().trim().equals("")) {
                BeanUtils.copyProperties(excel, errVO);
                errVO.setErrMsg("第" + i + "条记录，机构地址为空");
                errorList.add(errVO);
                errNum++;
                continue;
            }*/
            // 判断机构地址是否已存在
            /*TrainInstitution instAddrInfo = this.infoByNameOrAddr(excel.getAddress(), 2);
            if (instAddrInfo != null) {
                BeanUtils.copyProperties(excel, errVO);
                errVO.setErrMsg("第" + i + "条记录，机构地址已存在系统中");
                errorList.add(errVO);
                errNum++;
                continue;
            }*/
            // 判断机构等级数据
            if ((excel.getRatingYear() != null && !excel.getRatingYear().trim().equals(""))
                    || excel.getGradeIdName() != null && !excel.getGradeIdName().trim().equals("")) {
                if (excel.getRatingYear() == null || excel.getRatingYear().trim().equals("")) {
                    BeanUtils.copyProperties(excel, errVO);
                    errVO.setErrMsg("第" + i + "条记录，评级年度为空");
                    errorList.add(errVO);
                    errNum++;
                    continue;
                }
                if (excel.getGradeIdName() == null || excel.getGradeIdName().trim().equals("")) {
                    BeanUtils.copyProperties(excel, errVO);
                    errVO.setErrMsg("第" + i + "条记录，评级等级为空");
                    errorList.add(errVO);
                    errNum++;
                    continue;
                }
                if (!gradeList.contains(excel.getGradeIdName())) {
                    BeanUtils.copyProperties(excel, errVO);
                    errVO.setErrMsg("第" + i + "条记录，评级等级不正确");
                    errorList.add(errVO);
                    errNum++;
                    continue;
                }
            }
            // 判断导入数据是否有重复
            String mapDataKey = excel.getName() + "-" + excel.getAddress() + "-" + excel.getRatingYear();
            if (checkMap.containsKey(mapDataKey)) {
                BeanUtils.copyProperties(excel, errVO);
                errVO.setErrMsg("第" + i + "条记录与第" + checkMap.get(mapDataKey) + "条记录的机构名称、机构地址、评级年度等信息重复");
                errorList.add(errVO);
                errNum++;
                continue;
            }
            checkMap.put(mapDataKey, i);

            // 处理数据
            if (errNum == 0) {
                String[] cateIdName = excel.getCateIdName().split("-"); // 分出机构类型ID和名称
                int cateId = Integer.parseInt(cateIdName[0]); // 机构类型ID
                String mapInstKey = excel.getName() + "-" + excel.getAddress();
                TrainInstitution institution = new TrainInstitution();
                TrainInstitutionGrade grade = new TrainInstitutionGrade();
                List<TrainInstitutionGrade> instGradeList;
                // 处理机构信息
                if (instMap.containsKey(mapInstKey)) {
                    // 已存在当前机构map，则取对应的机构评级数据
                    instGradeList = gradeListMap.get(mapInstKey);
                } else {
                    BeanUtils.copyProperties(excel, institution);
                    institution.setCateId(cateId);
                    institution.setIsImport(1);
                    institution.setCreateUser(loginEmplId);
                    institution.setCreateTime(stamp);
                    institution.setUpdateUser(loginEmplId);
                    institution.setUpdateTime(stamp);
                    instMap.put(mapInstKey, institution);
                    // 初始化当前机构的评级数据
                    instGradeList = new ArrayList<>();
                }
                // 评级信息不为空，则处理评级数据
                if (excel.getRatingYear() != null && !excel.getRatingYear().trim().equals("")) {
                    String[] gradeIdName = excel.getGradeIdName().split("-"); // 分出机构等级ID和名称
                    int gradeId = Integer.parseInt(gradeIdName[0]); // 机构等级ID
                    grade.setYearly(excel.getRatingYear());
                    grade.setGradeId(gradeId);
                    grade.setMemo(excel.getMemo());
                    grade.setCreateUser(loginEmplId);
                    grade.setCreateTime(stamp);
                    grade.setUpdateUser(loginEmplId);
                    grade.setUpdateTime(stamp);
                    instGradeList.add(grade);

                    // 获取同一个机构的评级数据中，最新的年度的等级作为机构信息中的等级
                    if (yearlyMap.containsKey(mapInstKey)) {
                        Integer yearly = Integer.parseInt(yearlyMap.get(mapInstKey)); // 前一个评级年度值
                        Integer currYearly = Integer.parseInt(excel.getRatingYear()); // 当前评级年度值
                        if (yearly < currYearly) {
                            // 如果前一个评级年度值小于当前评级年度值，则更换一下评级等级
                            gradeIdMap.put(mapInstKey, gradeId);
                            yearlyMap.put(mapInstKey, excel.getRatingYear());
                        }
                    } else {
                        yearlyMap.put(mapInstKey, excel.getRatingYear());
                        gradeIdMap.put(mapInstKey, gradeId);
                    }
                }
                gradeListMap.put(mapInstKey, instGradeList);
            }
        }
        // 处理数据入库
        if (errNum == 0) {
            for (Map.Entry<String, TrainInstitution> entry : instMap.entrySet()) {
                // 保存机构信息
                String key = entry.getKey();
                TrainInstitution instEntity = entry.getValue();
                instEntity.setRatingYear(yearlyMap.get(key));
                instEntity.setGradeId(gradeIdMap.get(key));
                Long institutionId = this.insertSelective(instEntity);
                // 保存机构等级数据
                List<TrainInstitutionGrade> instGradeList = gradeListMap.get(key);
                if (!instGradeList.isEmpty()) {
                    for (TrainInstitutionGrade gradeEntity : instGradeList) {
                        gradeEntity.setInstitutionId(institutionId);
                    }
                    trainInstitutionGradeService.insertBatchSelective(instGradeList);
                }
            }
        }
        return errorList;
    }

    /**
     * 根据名称或地址查询机构信息
     * @param nameOrAddr
     * @param checkType
     * @return
     */
    @Override
    public TrainInstitution infoByNameOrAddr(String nameOrAddr, Integer checkType) {
        TrainInstitution institution = new TrainInstitution();
        if (checkType.equals(1)) {
            institution.setName(nameOrAddr); // 机构名称
        } else {
            institution.setAddress(nameOrAddr); // 机构地址
        }
        return selectOne(institution);
    }

    /**
     * 培训机构导出数据
     * @param params
     * @return
     */
    @Override
    public List<InstitutionExcel> exportInst(Map<String, Object> params) {
        return trainInstitutionMapper.exportInst(params);
    }
}
