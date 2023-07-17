package com.dyys.hr.service.train.impl;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateTime;
import com.dagongma.mybatis.core.service.impl.AbstractCrudService;
import com.dyys.hr.dao.train.TrainAppraiseMapper;
import com.dyys.hr.dto.train.*;
import com.dyys.hr.entity.train.*;
import com.dyys.hr.service.staff.IStaffUserInfoService;
import com.dyys.hr.service.train.*;
import com.dyys.hr.vo.train.EmployeeAppraisePageVO;
import com.dyys.hr.vo.train.TrainAppraiseDetailVO;
import com.dyys.hr.vo.train.TrainAppraiseVO;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
@Slf4j
public class TrainAppraiseServiceImpl extends AbstractCrudService<TrainAppraise, Long> implements TrainAppraiseService {
    @Autowired
    private TrainAppraiseMapper trainAppraiseMapper;
    @Autowired
    private TrainAppraisePersonService trainAppraisePersonService;
    @Autowired
    private TrainNoticeService trainNoticeService;
    @Autowired
    private QuestionnaireUserService questionnaireUserService;
    @Autowired
    private QuestionnaireService questionnaireService;
    @Autowired
    private TrainAppraiseService trainAppraiseService;
    @Autowired
    private IStaffUserInfoService iStaffUserInfoService;
    @Value("${portal-config.domain}")
    private String jumpDomain;

    @Override
    public PageInfo<TrainAppraiseVO> pageList(Map<String, Object> params){
        int page = Convert.toInt(params.get("page"));
        int limit = Convert.toInt(params.get("limit"));
        PageMethod.startPage(page, limit);
        List<TrainAppraiseVO> voList = trainAppraiseMapper.pageList(params);
        return new PageInfo<>(voList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long save(TrainAppraiseDTO dto, String loginUserId){
        //新增评估
        TrainAppraise appraiseEntity = new TrainAppraise();
        BeanUtils.copyProperties(dto,appraiseEntity);
        appraiseEntity.setCreateUser(loginUserId);
        appraiseEntity.setCreateTime(System.currentTimeMillis()/1000);
        Long appraiseId = this.insertSelective(appraiseEntity);
        //批量插入参评人员表和待办通知表,用户评估数据记录表
        List<TrainAppraisePersonDTO> personDTOS = dto.getAppraisePersonList();
        if(personDTOS != null && !personDTOS.isEmpty()){
            List<TrainAppraisePerson> personList = new ArrayList<>();
            List<TrainNotice> noticeList = new ArrayList<>();
            List<QuestionnaireUser> questionnaireUserList = new ArrayList<>();
            HashMap<String, Object> map = new HashMap<>();
            for (TrainAppraisePersonDTO personDTO :personDTOS){
                if(map.get(personDTO.getUserId()) == null){
                    map.put(personDTO.getUserId(),1);
                    TrainAppraisePerson personEntity = new TrainAppraisePerson();
                    BeanUtils.copyProperties(personDTO,personEntity);
                    personEntity.setAppraiseId(appraiseId);
                    personEntity.setStatus(0);
                    personEntity.setCreateUser(loginUserId);
                    personEntity.setCreateTime(System.currentTimeMillis()/1000);
                    personList.add(personEntity);

                    TrainNotice trainNotice = new TrainNotice();
                    trainNotice.setTypeId(appraiseId);
                    trainNotice.setUserId(personDTO.getUserId());
                    trainNotice.setType(3);
                    trainNotice.setStatus(0);
                    trainNotice.setCreateUser(loginUserId);
                    trainNotice.setCreateTime(System.currentTimeMillis()/1000);
                    noticeList.add(trainNotice);

                    QuestionnaireUser questionnaireUser = new QuestionnaireUser();
                    questionnaireUser.setType(1); // 培训评估
                    questionnaireUser.setUserId(personDTO.getUserId());
                    questionnaireUser.setTrainAppraiseId(appraiseId);
                    questionnaireUser.setQuestionnaireId(dto.getQuestionnaireId());
                    questionnaireUser.setStatus(0);
                    questionnaireUser.setCreateUser(loginUserId);
                    questionnaireUser.setCreateTime(System.currentTimeMillis()/1000);
                    questionnaireUserList.add(questionnaireUser);
                }
            }
            trainAppraisePersonService.insertBatchSelective(personList);
            trainNoticeService.insertBatchSelective(noticeList);
            questionnaireUserService.insertBatchSelective(questionnaireUserList);
        }
        return appraiseId;
    }

    @Override
    public TrainAppraiseDetailVO getDetail(Long appraiseId){
        TrainAppraiseDetailVO detail = trainAppraiseMapper.getDetail(appraiseId);
        if(detail != null){
            Map<String, Object> params = new HashMap<>();
            params.put("appraise_id",appraiseId);
            detail.setAppraisePersonList(trainAppraisePersonService.getListByQuery(params));
        }
        return detail;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer  update(TrainAppraiseDTO dto, String loginUserId){
        TrainAppraise appraiseEntity = new TrainAppraise();
        BeanUtils.copyProperties(dto,appraiseEntity);
        appraiseEntity.setUpdateUser(loginUserId);
        appraiseEntity.setUpdateTime(System.currentTimeMillis()/1000);
        Integer updateResult = this.updateSelective(appraiseEntity);

        Long appraiseId = appraiseEntity.getId();
        Map<String, Object> params = new HashMap<>();
        params.put("appraiseId",appraiseId);
        //获取参训人员userId旧数据进行比对,新的插入,移除的删掉,list批量处理
        List<String> personIds = trainAppraisePersonService.getPersonIdsByQuery(params);
        List<TrainAppraisePersonDTO> personList = dto.getAppraisePersonList();
        if(personList != null &&!personList.isEmpty()) {
            List<TrainAppraisePerson> newPersonEntityList = new ArrayList<>();
            List<String> noChangePersonIds = new ArrayList<>();
            List<TrainNotice> noticeList = new ArrayList<>();
            List<QuestionnaireUser> questionnaireUserList = new ArrayList<>();
            for (TrainAppraisePersonDTO personDTO : personList) {
                if (personIds.contains(personDTO.getUserId())) {
                    noChangePersonIds.add(personDTO.getUserId());
                    continue;
                }
                TrainAppraisePerson personEntity = new TrainAppraisePerson();
                BeanUtils.copyProperties(personDTO, personEntity);
                personEntity.setAppraiseId(appraiseId);
                personEntity.setStatus(0);
                personEntity.setUserId(personDTO.getUserId());
                personEntity.setCreateUser(loginUserId);
                personEntity.setCreateTime(System.currentTimeMillis() / 1000);
                newPersonEntityList.add(personEntity);

                TrainNotice trainNotice = new TrainNotice();
                trainNotice.setTypeId(appraiseId);
                trainNotice.setUserId(personDTO.getUserId());
                trainNotice.setType(3);
                trainNotice.setStatus(0);
                trainNotice.setCreateUser(loginUserId);
                trainNotice.setCreateTime(System.currentTimeMillis() / 1000);
                noticeList.add(trainNotice);

                QuestionnaireUser questionnaireUser = new QuestionnaireUser();
                questionnaireUser.setType(1); // 培训评估
                questionnaireUser.setUserId(personDTO.getUserId());
                questionnaireUser.setTrainAppraiseId(appraiseId);
                questionnaireUser.setQuestionnaireId(dto.getQuestionnaireId());
                questionnaireUser.setStatus(0);
                questionnaireUser.setCreateUser(loginUserId);
                questionnaireUser.setCreateTime(System.currentTimeMillis() / 1000);
                questionnaireUserList.add(questionnaireUser);
            }
            //删除移除的用户数据
            params.put("noChangePersonIds", noChangePersonIds);
            trainAppraisePersonService.deleteByParams(params);

            if(!newPersonEntityList.isEmpty()){
                trainAppraisePersonService.insertBatchSelective(newPersonEntityList);
            }
            if(!noticeList.isEmpty()){
                trainNoticeService.insertBatchSelective(noticeList);
            }
            if(!questionnaireUserList.isEmpty()){
                questionnaireUserService.insertBatchSelective(questionnaireUserList);
            }
        }
        return updateResult;
    }

    @Override
    public PageInfo<EmployeeAppraisePageVO> myAppraiseList(Map<String, Object> params){
        int page = Convert.toInt(params.get("page"));
        int limit = Convert.toInt(params.get("limit"));
        PageMethod.startPage(page, limit);
        List<EmployeeAppraisePageVO> voList = trainAppraiseMapper.myAppraiseList(params);
        return new PageInfo<>(voList);
    }

    /**
     * 获取同一项目同一课程的课程评分的平均值
     * @param programsId
     * @param courseId
     * @return
     */
    @Override
    public BigDecimal getAvgCourseScore(Long programsId, Long courseId) {
        return trainAppraiseMapper.getAvgCourseScore(programsId, courseId);
    }

    @Override
    public List<TrainAppraise> finishedList(){
        return trainAppraiseMapper.finishedList();
    }

    @Override
    public QuestionnaireUserSubmitDTO mynQuestionnaireSubmitDetail(Map<String, Object> params){
        //获取用户填写问卷记录的主键id
        QuestionnaireUser userQuery = new QuestionnaireUser();
        userQuery.setStatus(1);
        userQuery.setUserId(params.get("user_id").toString());
        userQuery.setType(Integer.parseInt(params.get("type").toString()));
        userQuery.setTrainAppraiseId(Long.valueOf(params.get("appraiseId").toString()));
        QuestionnaireUser questionnaireUser = questionnaireUserService.selectOne(userQuery);
        if(questionnaireUser != null){
            HashMap<String, Object> map = new HashMap<>();
            map.put("id",questionnaireUser.getId());
            return questionnaireService.getUserSubmitDetail(map);
        }
        return null;
    }

    /**
     * 获取某个项目某个课程某个评分对象最后一次评分
     * @param programsId 培训项目ID
     * @param courseId 课程ID
     * @param scoreObject 1.课程 2.讲师 3.组织方
     * @return
     */
    @Override
    public TrainAppraise getFinalInfo(Long programsId, Long courseId, Integer scoreObject) {
        return trainAppraiseMapper.getFinalInfo(programsId, courseId, scoreObject);
    }

    /**
     * 批量通知评估
     * @param dtoList
     * @param loginUserId
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean batchAppraiseNotice(List<IdDTO> dtoList,String loginUserId){
        boolean result = false;
        if(dtoList != null && !dtoList.isEmpty()){
            //获取登陆人和评估名称
            String loginUserName = iStaffUserInfoService.getUserInfoById(loginUserId).getEmplName();
            Map<String, Object> query = new HashMap<>();
            for (IdDTO dto : dtoList){
                //获取所有参评人员
                query.put("appraiseId",dto.getId());
                List<String> allUserIds = trainAppraisePersonService.getPersonIdsByQuery(query);
                String appraiseTitle = trainAppraiseService.selectById(dto.getId()).getTitle();
                for (String userId : allUserIds){
                    //自助平台插入代办
                    trainNoticeService.insertHcmPortalMessage("培训系统","评估反馈",userId, 1,jumpDomain + "/kn-front/emp/center", DateTime.now(), appraiseTitle + "评估反馈",
                            0,loginUserId,loginUserName);
                }
            }
            result = true;
        }
        return result;
    }
}