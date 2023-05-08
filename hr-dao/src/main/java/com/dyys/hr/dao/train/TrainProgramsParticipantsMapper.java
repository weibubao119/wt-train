package com.dyys.hr.dao.train;

import com.dagongma.mybatis.core.dao.ICrudMapper;
import com.dyys.hr.entity.train.TrainProgramsParticipants;
import com.dyys.hr.entity.train.excel.ProgramsParticipantsExcel;
import com.dyys.hr.entity.train.excel.SummaryExcel;
import com.dyys.hr.vo.train.EmployeeTrainingScheduleVO;
import com.dyys.hr.vo.train.TrainProgramsParticipantsDetailVO;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


@Repository
public interface TrainProgramsParticipantsMapper extends ICrudMapper<TrainProgramsParticipants> {
    List<TrainProgramsParticipantsDetailVO> getDetailList(Map<String, Object> params);

    void deleteByParams(Map<String,Object> params);

    List<ProgramsParticipantsExcel> pageList(Map<String, Object> params);

    /**
     * 培训数量统计(管理员端：进行中、已完成、总计、按月统计；员工端：进行中、已完成)
     * @param params
     * @return
     */
    Integer getProgramsCountByQuery(Map<String, Object> params);

    /**
     * 已完成培训总人次统计
     * @param params
     * @return
     */
    Integer getProgramsParticipantsCountByQuery(Map<String, Object> params);


    List<EmployeeTrainingScheduleVO> programsMonthSchedule(Map<String, Object> params);

    List<String> getUserIdsByQuery(Map<String, Object> params);

    List<ProgramsParticipantsExcel> exportList(String programsId);

    /**
     * 培训总结excel模板获取培训项目的参训人员
     * @param programsId
     * @return
     */
    List<SummaryExcel> getParticipantListTml(Long programsId);

    /**
     * 获取某个项目已报名参训人员
     * @param programsId
     * @return
     */
    Integer getApplyNum(Long programsId);
}