package com.dyys.hr.service.train.impl;

import com.dagongma.mybatis.core.service.impl.AbstractCrudService;
import com.dyys.hr.dao.exam.ExamUserAnswerMapper;
import com.dyys.hr.dao.train.TrainExaminerDetailMapper;
import com.dyys.hr.entity.train.TrainExaminerDetail;
import com.dyys.hr.service.train.TrainExaminerDetailService;
import com.dyys.hr.vo.exam.ExamAnswerDetailsVO;
import com.dyys.hr.vo.exam.ExamResultDetailsVO;
import com.dyys.hr.vo.train.TrainExaminerDetailVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
@Slf4j
public class TrainExaminerDetailServiceImpl extends AbstractCrudService<TrainExaminerDetail, Long> implements TrainExaminerDetailService {
    @Autowired
    private TrainExaminerDetailMapper trainExaminerDetailMapper;
    @Autowired
    private ExamUserAnswerMapper examUserAnswerMapper;

    public List<TrainExaminerDetailVO> getListByQuery(Map<String, Object> params) {
        return trainExaminerDetailMapper.getListByQuery(params);
    }

    /**
     * 查看考试结果详情
     *
     * @param detailsId 考试记录id
     * @return 结果详情
     */
    @Override
    public ExamResultDetailsVO getExamDetails(String detailsId) {
        ExamResultDetailsVO examDetails = trainExaminerDetailMapper.getExamDetails(detailsId);
        List<ExamAnswerDetailsVO> examAnswerDetails = examUserAnswerMapper.listByDetailsId(detailsId);
        examDetails.setQuesCount(examAnswerDetails.size());
        examDetails.setAnswerDetailsList(examAnswerDetails);

        return examDetails;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void restExamDeleteByQuery(Map<String,Object> params){
        //删除用户考试试卷答案表数据
        List<String> detailIds = trainExaminerDetailMapper.getIdsByQuery(params);
        HashMap<String, Object> query = new HashMap<>();
        query.put("detailsIds",detailIds);
        examUserAnswerMapper.deleteByQuery(query);

        //删除项目考试明细表数据
        trainExaminerDetailMapper.deleteByQuery(query);
    }

}