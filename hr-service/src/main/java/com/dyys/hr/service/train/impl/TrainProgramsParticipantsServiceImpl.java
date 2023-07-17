package com.dyys.hr.service.train.impl;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateTime;
import com.dagongma.mybatis.core.service.impl.AbstractCrudService;
import com.dyys.hr.dao.train.TrainProgramsParticipantsMapper;
import com.dyys.hr.dto.train.IdDTO;
import com.dyys.hr.dto.train.TrainProgramsParticipantsDTO;
import com.dyys.hr.entity.train.TrainNotice;
import com.dyys.hr.entity.train.TrainProgramsParticipants;
import com.dyys.hr.entity.train.excel.ProgramsParticipantsExcel;
import com.dyys.hr.entity.train.excel.SummaryExcel;
import com.dyys.hr.service.staff.IStaffUserInfoService;
import com.dyys.hr.service.train.TrainNoticeService;
import com.dyys.hr.service.train.TrainProgramsParticipantsService;
import com.dyys.hr.service.train.TrainProgramsService;
import com.dyys.hr.vo.train.EmployeeTrainingScheduleVO;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Condition;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
@Slf4j
public class TrainProgramsParticipantsServiceImpl extends AbstractCrudService<TrainProgramsParticipants, Long> implements TrainProgramsParticipantsService {
    @Autowired
    private TrainProgramsParticipantsMapper trainProgramsParticipantsMapper;
    @Autowired
    private TrainNoticeService trainNoticeService;
    @Autowired
    private TrainProgramsService trainProgramsService;
    @Autowired
    private IStaffUserInfoService iStaffUserInfoService;
    @Value("${portal-config.domain}")
    private String jumpDomain;

    @Override
    public void deleteByParams(Map<String,Object> params){
        trainProgramsParticipantsMapper.deleteByParams(params);
    }

    @Override
    public PageInfo<ProgramsParticipantsExcel> pageList(Map<String, Object> params){
        int page = Convert.toInt(params.get("page"));
        int limit = Convert.toInt(params.get("limit"));
        PageMethod.startPage(page, limit);
        List<ProgramsParticipantsExcel> voList = trainProgramsParticipantsMapper.pageList(params);
        return new PageInfo<>(voList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean noticeSignUp(List<IdDTO> dtoList,String loginUserId){
        boolean result = false;
        if(dtoList != null && !dtoList.isEmpty()){
            List<TrainNotice> noticeList = new ArrayList<>();
            //获取登陆人和反馈需求名称
            String loginUserName = iStaffUserInfoService.getUserInfoById(loginUserId).getEmplName();
            String trainName = "";
            //循环给参训人员发送通知
            String ids = "";
            for (IdDTO dto : dtoList){
                TrainProgramsParticipants participants = this.selectById(dto.getId());
                TrainNotice trainNotice = new TrainNotice();
                trainNotice.setTypeId(participants.getProgramsId());
                trainNotice.setUserId(participants.getUserId());
                trainNotice.setType(1);
                trainNotice.setStatus(0);
                trainNotice.setCreateUser(loginUserId);
                trainNotice.setCreateTime(System.currentTimeMillis()/1000);
                noticeList.add(trainNotice);

                //自助平台插入代办
                if("".equals(trainName)){
                    trainName = trainProgramsService.selectById(participants.getProgramsId()).getTrainName();
                }

                trainNoticeService.insertHcmPortalMessage("培训系统","参训通知",participants.getUserId(),
                        1,jumpDomain + "/kn-front/emp/center", DateTime.now(), trainName + "培训项目参训通知",
                        0,loginUserId,loginUserName);

                ids = ids.concat("," + dto.getId().toString());
            }
            result = trainNoticeService.insertBatchSelective(noticeList);
            ids = ids.substring(1);
            //批量更新参训状态为待确认
            TrainProgramsParticipants updateParticipants = new TrainProgramsParticipants();
            updateParticipants.setStatus(0);
            Condition condition = new Condition(TrainProgramsParticipants.class);
            condition.createCriteria().andCondition("id in (" + ids + ")");
            this.updateByConditionSelective(updateParticipants,condition);
        }
        return result;
    }

    /**
     * 批量确认报名
     * @param dtoList
     * @param loginUserId
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean confirmSignUp(List<IdDTO> dtoList,String loginUserId){
        boolean result = false;
        if(dtoList != null && !dtoList.isEmpty()){
            //循环给参训人员发送通知
            String ids = "";
            for (IdDTO dto : dtoList){
                ids = ids.concat("," + dto.getId().toString());
            }
            ids = ids.substring(1);
            //批量更新参训状态为已报名
            TrainProgramsParticipants updateParticipants = new TrainProgramsParticipants();
            updateParticipants.setStatus(1);
            Condition condition = new Condition(TrainProgramsParticipants.class);
            condition.createCriteria().andCondition("id in (" + ids + ")");
            this.updateByConditionSelective(updateParticipants,condition);
        }
        return result;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean addStudents(List<TrainProgramsParticipantsDTO> dtoList, String loginUserId){
        boolean result = false;
        if(dtoList != null && !dtoList.isEmpty()){
            //获取该项目所有参训人员用户id,避免重复添加
            Map<String, Object> query = new HashMap<>();
            query.put("programsId",dtoList.get(0).getProgramsId());
            List<String> userIds = trainProgramsParticipantsMapper.getUserIdsByQuery(query);
            HashMap<String, Object> map = new HashMap<>();
            List<TrainProgramsParticipants> participantsList = new ArrayList<>();
            for (TrainProgramsParticipantsDTO dto : dtoList){
                if(map.get(dto.getUserId()) == null && !userIds.contains(dto.getUserId())){
                    map.put(dto.getUserId(),1);
                    TrainProgramsParticipants entity = new TrainProgramsParticipants();
                    BeanUtils.copyProperties(dto,entity);
                    entity.setStatus(2);
                    entity.setIsImport(0);
                    entity.setCreateUser(loginUserId);
                    entity.setCreateTime(System.currentTimeMillis()/1000);
                    participantsList.add(entity);
                }
            }
            if(!participantsList.isEmpty()){
                result = this.insertBatchSelective(participantsList);
            }
        }
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer removeStudents(List<IdDTO> dtoList,String loginUserId){
        ArrayList<Long> ids = new ArrayList<>();
        for (IdDTO dto : dtoList){
            ids.add(dto.getId());
        }
        return this.deleteByIds(ids);
    }

    /**
     * 培训数量统计(管理员端：进行中、已完成、总计、按月统计；员工端：进行中、已完成)
     * @param params
     * @return
     */
    @Override
    public Integer getProgramsCountByQuery(Map<String, Object> params){
        return trainProgramsParticipantsMapper.getProgramsCountByQuery(params);
    }

    /**
     * 已完成培训总人次统计
     * @param params
     * @return
     */
    @Override
    public Integer getProgramsParticipantsCountByQuery(Map<String, Object> params){
        return trainProgramsParticipantsMapper.getProgramsParticipantsCountByQuery(params);
    }



    @Override
    public List<EmployeeTrainingScheduleVO> programsMonthSchedule(Map<String, Object> params){
        return trainProgramsParticipantsMapper.programsMonthSchedule(params);
    }

    /**
     * 根据项目ID和员工工号查询参训人员信息
     * @param programsId
     * @param emplId
     * @return
     */
    @Override
    public TrainProgramsParticipants getInfo(Long programsId, String emplId) {
        TrainProgramsParticipants participants = new TrainProgramsParticipants();
        participants.setProgramsId(programsId);
        participants.setUserId(emplId);
        return selectOne(participants);
    }

    /**
     * 培训总结excel模板获取培训项目的参训人员
     * @param programsId
     * @return
     */
    @Override
    public List<SummaryExcel> getParticipantListTml(Long programsId) {
        return trainProgramsParticipantsMapper.getParticipantListTml(programsId);
    }

    /**
     * 获取某个项目已报名参训人员
     * @param programsId
     * @return
     */
    @Override
    public Integer getApplyNum(Long programsId) {
        return trainProgramsParticipantsMapper.getApplyNum(programsId);
    }

    @Override
    public List<ProgramsParticipantsExcel> exportList(String programsId){
        return trainProgramsParticipantsMapper.exportList(programsId);
    }

}