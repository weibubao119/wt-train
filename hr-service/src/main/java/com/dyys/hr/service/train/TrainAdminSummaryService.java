package com.dyys.hr.service.train;

import com.dagongma.mybatis.core.service.ICrudService;
import com.dyys.hr.dto.train.TrainAdminSummaryDTO;
import com.dyys.hr.entity.train.TrainAdminSummary;
import com.dyys.hr.vo.train.TrainAdminSummaryDetailVO;

public interface TrainAdminSummaryService extends ICrudService<TrainAdminSummary, Long> {
    TrainAdminSummaryDetailVO getDetail(Long programsId);

    Integer update(TrainAdminSummaryDTO dto, String loginUserId);
}