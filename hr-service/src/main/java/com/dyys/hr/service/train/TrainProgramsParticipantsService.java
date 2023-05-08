package com.dyys.hr.service.train;

import com.dagongma.mybatis.core.service.ICrudService;
import com.dyys.hr.dto.train.IdDTO;
import com.dyys.hr.dto.train.TrainProgramsParticipantsDTO;
import com.dyys.hr.entity.train.TrainProgramsParticipants;
import com.dyys.hr.entity.train.excel.ProgramsParticipantsExcel;
import com.dyys.hr.entity.train.excel.SummaryExcel;
import com.dyys.hr.vo.train.EmployeeTrainingScheduleVO;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

public interface TrainProgramsParticipantsService extends ICrudService<TrainProgramsParticipants, Long> {
    void deleteByParams(Map<String,Object> params);

    PageInfo<ProgramsParticipantsExcel> pageList(Map<String, Object> params);

    List<ProgramsParticipantsExcel> exportList(String programsId);

    Boolean noticeSignUp(List<IdDTO> dtoList,String loginUserId);

    /**
     * 批量确认报名
     * @param dtoList
     * @param loginUserId
     * @return
     */
    Boolean confirmSignUp(List<IdDTO> dtoList,String loginUserId);

    Boolean addStudents(List<TrainProgramsParticipantsDTO> dtoList, String loginUserId);

    Integer removeStudents(List<IdDTO> dtoList,String loginUserId);

    // 培训数量统计(管理员端：进行中、已完成、总计、按月统计；员工端：进行中、已完成)
    Integer getProgramsCountByQuery(Map<String, Object> params);

    // 已完成培训总人次统计
    Integer getProgramsParticipantsCountByQuery(Map<String, Object> params);

    //获取项目月份课程排表
    List<EmployeeTrainingScheduleVO> programsMonthSchedule(Map<String, Object> params);

    /**
     * 根据项目ID和员工工号查询参训人员信息
     * @param programsId
     * @param emplId
     * @return
     */
    TrainProgramsParticipants getInfo(Long programsId, String emplId);

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