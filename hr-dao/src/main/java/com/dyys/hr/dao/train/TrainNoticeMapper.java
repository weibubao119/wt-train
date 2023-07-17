package com.dyys.hr.dao.train;

import cn.hutool.core.date.DateTime;
import com.dagongma.mybatis.core.dao.ICrudMapper;
import com.dyys.hr.entity.train.TrainNotice;
import com.dyys.hr.vo.train.EmployeeParticipantsNoticeListVO;
import com.dyys.hr.vo.train.EmployeeToDoNoticeListVO;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface TrainNoticeMapper extends ICrudMapper<TrainNotice> {
    void deleteByParams(Map<String,Object> params);

    List<EmployeeParticipantsNoticeListVO> participantsNoticeList(Map<String,Object> params);

    List<EmployeeToDoNoticeListVO> toDoNoticeList(Map<String,Object> params);

    TrainNotice getByQuery(Map<String,Object> params);

    /**
     * 新增员工自助平台代办通知
     */
    void createEmployeeSelfNotice(Map<String,Object> dataParams);

    /**
     * 自主模块插入代办通知
     * @param systemNam
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
    Void insertHcmPortalMessage(String systemNam, String messageModule, String employeeNumber,
                                Integer messageJump, String messageUrl, DateTime messageDate,
                                String messageContent, Integer messageRead, String creator,
                                String createName);
}