package com.dyys.hr.service.train;

import com.dagongma.mybatis.core.service.ICrudService;
import com.dyys.hr.dto.train.TrainAttendanceRulesDTO;
import com.dyys.hr.entity.train.TrainAttendanceRules;
import com.dyys.hr.entity.train.excel.TrainAttendanceRulesExcel;
import com.dyys.hr.vo.train.TrainAttendanceRulesDetailVO;
import com.dyys.hr.vo.train.TrainAttendanceRulesVO;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

public interface TrainAttendanceRulesService extends ICrudService<TrainAttendanceRules, Long> {
    PageInfo<TrainAttendanceRulesVO> pageList(Map<String, Object> params);

    Long save(TrainAttendanceRulesDTO dto, String loginUserId);

    TrainAttendanceRulesDetailVO getDetail(Long rulesId);

    Integer update(TrainAttendanceRulesDTO dto, String loginUserId);

    List<TrainAttendanceRulesExcel> ruleListExport(Map<String, Object> params);

    /**
     * 校验考勤规则数据
     * @param dto
     * @return
     */
    String checkData(TrainAttendanceRulesDTO dto);
}