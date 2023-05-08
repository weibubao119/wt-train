package com.dyys.hr.service.train.impl;

import cn.hutool.core.convert.Convert;
import com.dagongma.mybatis.core.service.impl.AbstractCrudService;
import com.dyys.hr.dao.train.TrainExamMapper;
import com.dyys.hr.dao.train.TrainExaminerMapper;
import com.dyys.hr.dto.train.*;
import com.dyys.hr.entity.train.*;
import com.dyys.hr.entity.train.excel.ExamDetailListExcel;
import com.dyys.hr.service.train.TrainExamService;
import com.dyys.hr.service.train.TrainExaminerService;
import com.dyys.hr.service.train.TrainNoticeService;
import com.dyys.hr.vo.train.EmployeeExamPageVO;
import com.dyys.hr.vo.train.TrainExamCheckDetailListVO;
import com.dyys.hr.vo.train.TrainExamDetailVO;
import com.dyys.hr.vo.train.TrainExamVO;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
@Slf4j
public class TrainExamServiceImpl extends AbstractCrudService<TrainExam, Long> implements TrainExamService {
    @Autowired
    private TrainExamMapper trainExamMapper;
    @Autowired
    private TrainExaminerMapper trainExaminerMapper;
    @Autowired
    private TrainExaminerService trainExaminerService;
    @Autowired
    private TrainNoticeService trainNoticeService;

    @Override
    public PageInfo<TrainExamVO> pageList(Map<String, Object> params){
        int page = Convert.toInt(params.get("page"));
        int limit = Convert.toInt(params.get("limit"));
        PageMethod.startPage(page, limit);
        List<TrainExamVO> voList = trainExamMapper.pageList(params);
        return new PageInfo<>(voList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long save(TrainExamDTO dto, String loginUserId){
        if(dto != null){
            //新增考试
            TrainExam examEntity = new TrainExam();
            BeanUtils.copyProperties(dto,examEntity);
            examEntity.setCreateUser(loginUserId);
            examEntity.setCreateTime(System.currentTimeMillis()/1000);
            Long examId = this.insertSelective(examEntity);

            List<TrainExaminerDTO> examinerDTOS = dto.getExaminerList();

            if(examinerDTOS != null && !examinerDTOS.isEmpty()){
                //批量插入考试人员和对应代办考试通知数据
                List<TrainExaminer> examinerList = new ArrayList<>();
                List<TrainNotice> noticeList = new ArrayList<>();
                HashMap<String, Object> map = new HashMap<>();
                for (TrainExaminerDTO examinerDTO :examinerDTOS){
                    if(map.get(examinerDTO.getUserId()) == null){
                        map.put(examinerDTO.getUserId(),1);
                        TrainExaminer examinerEntity = new TrainExaminer();
                        BeanUtils.copyProperties(examinerDTO,examinerEntity);
                        examinerEntity.setExamId(examId);
                        examinerEntity.setStatus(2);
                        examinerEntity.setExamNum(0);
                        examinerEntity.setCreateUser(loginUserId);
                        examinerEntity.setCreateTime(System.currentTimeMillis()/1000);
                        examinerList.add(examinerEntity);

                        //类型为线下考试时不发代办
                        if(dto.getType() == 1){
                            TrainNotice trainNotice = new TrainNotice();
                            trainNotice.setTypeId(examId);
                            trainNotice.setUserId(examinerDTO.getUserId());
                            trainNotice.setType(2);
                            trainNotice.setStatus(0);
                            trainNotice.setCreateUser(loginUserId);
                            trainNotice.setCreateTime(System.currentTimeMillis()/1000);
                            noticeList.add(trainNotice);
                        }
                    }
                }
                if(!examinerList.isEmpty()){
                    trainExaminerService.insertBatchSelective(examinerList);
                }
                if(!noticeList.isEmpty()){
                    trainNoticeService.insertBatchSelective(noticeList);
                }
            }
            return examId;
        }
        return null;
    }

    @Override
    public TrainExamDetailVO getDetail(Long examId){
        TrainExamDetailVO detail = trainExamMapper.getDetail(examId);
        if(detail != null){
            Map<String, Object> params = new HashMap<>();
            params.put("exam_id",examId);
            detail.setExaminerList(trainExaminerService.getListByQuery(params));
        }
        return detail;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer update(TrainExamDTO dto, String loginUserId){
        TrainExam examEntity = new TrainExam();
        BeanUtils.copyProperties(dto,examEntity);
        examEntity.setUpdateUser(loginUserId);
        examEntity.setUpdateTime(System.currentTimeMillis()/1000);
        Integer updateResult = this.updateSelective(examEntity);

        Long examId = examEntity.getId();
        Map<String, Object> params = new HashMap<>();
        params.put("exam_id",examId);
        //清空旧数据,插入考试人员
        trainExaminerService.deleteByParams(params);
        //清空旧通知数据
        params.put("type_id",examId);
        params.put("type",2);
        trainNoticeService.deleteByParams(params);
        //插入考试人员表
        List<TrainExaminerDTO> examinerDTOS = dto.getExaminerList();
        if(examinerDTOS != null && !examinerDTOS.isEmpty()){
            //批量插入考试人员和对应代办考试通知数据
            List<TrainExaminer> examinerList = new ArrayList<>();
            List<TrainNotice> noticeList = new ArrayList<>();
            HashMap<String, Object> map = new HashMap<>();
            for (TrainExaminerDTO examinerDTO :examinerDTOS){
                if(map.get(examinerDTO.getUserId()) == null){
                    map.put(examinerDTO.getUserId(),1);
                    TrainExaminer examinerEntity = new TrainExaminer();
                    BeanUtils.copyProperties(examinerDTO,examinerEntity);
                    examinerEntity.setExamId(examId);
                    examinerEntity.setStatus(0);
                    examinerEntity.setExamNum(0);
                    examinerEntity.setCreateUser(loginUserId);
                    examinerEntity.setCreateTime(System.currentTimeMillis()/1000);
                    examinerList.add(examinerEntity);

                    //类型为线下考试时不发代办
                    if(dto.getType() == 1){
                        TrainNotice trainNotice = new TrainNotice();
                        trainNotice.setTypeId(examId);
                        trainNotice.setUserId(examinerDTO.getUserId());
                        trainNotice.setType(2);
                        trainNotice.setStatus(0);
                        trainNotice.setCreateUser(loginUserId);
                        trainNotice.setCreateTime(System.currentTimeMillis()/1000);
                        noticeList.add(trainNotice);
                    }
                }
            }
            if(!examinerList.isEmpty()){
                trainExaminerService.insertBatchSelective(examinerList);
            }
            if(!noticeList.isEmpty()){
                trainNoticeService.insertBatchSelective(noticeList);
            }
        }
        this.updateSelective(examEntity);
        return updateResult;
    }

    @Override
    public PageInfo<EmployeeExamPageVO> myExamList(Map<String, Object> params){
        int page = Convert.toInt(params.get("page"));
        int limit = Convert.toInt(params.get("limit"));
        PageMethod.startPage(page, limit);
        List<EmployeeExamPageVO> voList = trainExamMapper.myExamList(params);
        return new PageInfo<>(voList);
    }

    @Override
    public PageInfo<TrainExamCheckDetailListVO> checkExamDetailsList(Map<String, Object> params){
        int page = Convert.toInt(params.get("page"));
        int limit = Convert.toInt(params.get("limit"));
        PageMethod.startPage(page, limit);
        List<TrainExamCheckDetailListVO> voList = trainExamMapper.checkExamDetailsList(params);
        return new PageInfo<>(voList);
    }

    @Override
    public List<ExamDetailListExcel> exportExamDetailsList(Map<String, Object> params){
        return trainExamMapper.exportExamDetailsList(params);
    }

    @Override
    public List<ExamDetailListExcel> exportThisExamDetailsList(Map<String, Object> params){
        return trainExamMapper.exportThisExamDetailsList(params);
    }

    /**
     * 考试成绩excel模板下拉项
     * @return
     */
    @Override
    public Map<Integer, List<String>> excelSelectMap() {
        Map<Integer, List<String>> selectMap = new HashMap<>();
        // 考试成绩是否通过：1是；0否
        List<String> statusList = new ArrayList<>();
        statusList.add("1");
        statusList.add("0");
        selectMap.put(3, statusList);
        return selectMap;
    }
}