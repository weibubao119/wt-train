package com.dyys.hr.service.train.impl;

import cn.hutool.core.convert.Convert;
import com.dagongma.kernel.core.entity.PageView;
import com.dagongma.mybatis.core.service.impl.AbstractCrudService;
import com.dyys.hr.dao.train.TrainInstitutionGradeMapper;
import com.dyys.hr.dto.train.TrainInstitutionGradeDTO;
import com.dyys.hr.entity.train.TrainInstitutionGrade;
import com.dyys.hr.service.train.TrainInstitutionGradeService;
import com.dyys.hr.service.train.TrainInstitutionService;
import com.dyys.hr.vo.train.TrainInstitutionGradeVO;
import com.github.pagehelper.page.PageMethod;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 培训机构等级 服务实现类
 * </p>
 *
 * @author yangye
 * @since 2022-05-23
 */
@Service
@Slf4j
public class TrainInstitutionGradeServiceImpl extends AbstractCrudService<TrainInstitutionGrade, Long> implements TrainInstitutionGradeService {
    @Autowired
    private TrainInstitutionGradeMapper trainInstitutionGradeMapper;
    @Autowired
    private TrainInstitutionService trainInstitutionService;

    /**
     * 培训机构等级分页列表
     * @param params
     * @return
     */
    @Override
    public PageView<TrainInstitutionGradeVO> institutionGradePageList(Map<String, Object> params) {
        int page = Convert.toInt(params.get("page"));
        int limit = Convert.toInt(params.get("limit"));
        long institutionId = Convert.toLong(params.get("institutionId"));
        PageMethod.startPage(page, limit);
        List<TrainInstitutionGradeVO> institutionGradeVOList = trainInstitutionGradeMapper.institutionGradeList(institutionId);
        return PageView.build(institutionGradeVOList);
    }

    /**
     * 校验同一个机构设置等级的年度
     * @param gradeDTO
     * @param checkType
     * @return
     */
    @Override
    public String checkYearly(TrainInstitutionGradeDTO gradeDTO, String checkType) {
        Map<String, Object> map = new HashMap<>();
        Long id = null;
        if (checkType.equals("update")) {
            id = gradeDTO.getId();
        }
        map.put("id", id);
        map.put("yearly", gradeDTO.getYearly());
        map.put("institutionId", gradeDTO.getInstitutionId());
        int yearlyCount = trainInstitutionGradeMapper.selectCountByYearly(map);
        if (yearlyCount > 0) {
            return "该年度等级已存在！";
        }
        return "";
    }

    /**
     * 机构等级添加保存
     * @param gradeDTO
     * @param loginEmplId
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long institutionGradeAdd(TrainInstitutionGradeDTO gradeDTO, String loginEmplId) {
        TrainInstitutionGrade institutionGrade = new TrainInstitutionGrade();
        BeanUtils.copyProperties(gradeDTO, institutionGrade);
        institutionGrade.setCreateUser(loginEmplId);
        institutionGrade.setCreateTime(System.currentTimeMillis()/1000);
        institutionGrade.setUpdateUser(loginEmplId);
        institutionGrade.setUpdateTime(System.currentTimeMillis()/1000);
        Long gradeId = insertSelective(institutionGrade);
        // 更新机构年度评级
        trainInstitutionService.updateGrade(gradeDTO.getInstitutionId(), gradeDTO.getYearly(), gradeDTO.getGradeId());
        return gradeId;
    }

    /**
     * 机构等级更新保存
     * @param gradeDTO
     * @param loginEmplId
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer institutionGradeUpdate(TrainInstitutionGradeDTO gradeDTO, String loginEmplId) {
        TrainInstitutionGrade trainInstitutionGrade = new TrainInstitutionGrade();
        BeanUtils.copyProperties(gradeDTO, trainInstitutionGrade);
        trainInstitutionGrade.setUpdateUser(loginEmplId);
        trainInstitutionGrade.setUpdateTime(System.currentTimeMillis()/1000);
        Integer res = updateSelective(trainInstitutionGrade);
        // 更新机构年度评级
        trainInstitutionService.updateGrade(gradeDTO.getInstitutionId(), gradeDTO.getYearly(), gradeDTO.getGradeId());
        return res;
    }


}
