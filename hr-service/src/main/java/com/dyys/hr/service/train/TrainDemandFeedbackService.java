package com.dyys.hr.service.train;

import com.dagongma.mybatis.core.service.ICrudService;
import com.dyys.hr.dto.train.TrainDemandAddFeedbackDTO;
import com.dyys.hr.entity.train.TrainDemandFeedback;
import com.dyys.hr.vo.train.TrainDemandFeedbackVO;
import com.dyys.hr.vo.train.TrainDemandUserFeedbackVO;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

public interface TrainDemandFeedbackService extends ICrudService<TrainDemandFeedback, Long> {
    List<TrainDemandFeedbackVO> feedbackList(Map<String, Object> params);

    Boolean addFeedBackUser(List<TrainDemandAddFeedbackDTO> dtoList, String loginUserId);

    Integer operateFeedBackData(Map<String, Object> params);

    PageInfo<TrainDemandUserFeedbackVO> userFeedBackPageList(Map<String, Object> params);

    void deleteFeedBackByQuery(Map<String, Object> params);
}