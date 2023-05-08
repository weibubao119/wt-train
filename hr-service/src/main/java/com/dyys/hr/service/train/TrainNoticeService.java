package com.dyys.hr.service.train;

import com.dagongma.mybatis.core.service.ICrudService;
import com.dyys.hr.entity.train.TrainNotice;
import com.dyys.hr.vo.train.EmployeeParticipantsNoticeListVO;
import com.dyys.hr.vo.train.EmployeeToDoNoticeListVO;
import com.github.pagehelper.PageInfo;

import java.util.Map;

public interface TrainNoticeService extends ICrudService<TrainNotice, Long> {
    void deleteByParams(Map<String,Object> params);

    PageInfo<EmployeeParticipantsNoticeListVO> participantsNoticeList(Map<String,Object> params);

    PageInfo<EmployeeToDoNoticeListVO> toDoNoticeList(Map<String,Object> params);

    TrainNotice getByQuery(Map<String,Object> params);

    /**
     * 删除参加人员消息记录
     * @param typeId
     * @param type
     * @return
     */
    Integer delByCondition(Long typeId, Integer type);
}