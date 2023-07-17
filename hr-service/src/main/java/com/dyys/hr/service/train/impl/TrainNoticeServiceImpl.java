package com.dyys.hr.service.train.impl;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateTime;
import com.dagongma.mybatis.core.service.impl.AbstractCrudService;
import com.dyys.hr.dao.train.TrainNoticeMapper;
import com.dyys.hr.entity.train.TrainNotice;
import com.dyys.hr.service.train.TrainNoticeService;
import com.dyys.hr.vo.train.EmployeeParticipantsNoticeListVO;
import com.dyys.hr.vo.train.EmployeeToDoNoticeListVO;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Condition;

import java.util.List;
import java.util.Map;


@Service
@Slf4j
public class TrainNoticeServiceImpl extends AbstractCrudService<TrainNotice, Long> implements TrainNoticeService {
    @Autowired
    private TrainNoticeMapper trainNoticeMapper;
    @Override
    public void deleteByParams(Map<String,Object> params){
        trainNoticeMapper.deleteByParams(params);
    }

    @Override
    public PageInfo<EmployeeParticipantsNoticeListVO> participantsNoticeList(Map<String, Object> params){
        int page = Convert.toInt(params.get("page"));
        int limit = Convert.toInt(params.get("limit"));
        PageMethod.startPage(page, limit);
        List<EmployeeParticipantsNoticeListVO> voList = trainNoticeMapper.participantsNoticeList(params);
        return new PageInfo<>(voList);
    }

    @Override
    public PageInfo<EmployeeToDoNoticeListVO> toDoNoticeList(Map<String, Object> params){
        int page = Convert.toInt(params.get("page"));
        int limit = Convert.toInt(params.get("limit"));
        PageMethod.startPage(page, limit);
        List<EmployeeToDoNoticeListVO> voList = trainNoticeMapper.toDoNoticeList(params);
        return new PageInfo<>(voList);
    }

    @Override
    public TrainNotice getByQuery(Map<String, Object> params){
        return trainNoticeMapper.getByQuery(params);
    }

    /**
     * 删除参加人员消息记录
     * @param typeId
     * @param type
     * @return
     */
    @Override
    public Integer delByCondition(Long typeId, Integer type) {
        Condition condition = new Condition(TrainNotice.class);
        condition.createCriteria().andEqualTo("typeId", typeId)
                .andEqualTo("type", type);
        return trainNoticeMapper.deleteByCondition(condition);
    }


    /**
     * 自主模块插入代办通知
     * @param systemName
     * @param messageModule
     * @param employeeNumber
     * @param messageJump
     * @param messageUrl
     * @param messageDate
     * @param messageContent
     * @param messageRead
     * @param creator
     * @param createName
     * @return
     */
    @Override
    public Void insertHcmPortalMessage(String systemName, String messageModule, String employeeNumber,
                                   Integer messageJump, String messageUrl, DateTime messageDate,
                                   String messageContent, Integer messageRead, String creator,
                                   String createName){
       return trainNoticeMapper.insertHcmPortalMessage(systemName,messageModule,employeeNumber,messageJump,messageUrl,messageDate,messageContent,messageRead,creator,createName);
    }
}