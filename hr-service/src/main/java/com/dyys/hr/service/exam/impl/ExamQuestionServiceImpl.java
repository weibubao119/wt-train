package com.dyys.hr.service.exam.impl;

import cn.hutool.core.lang.Assert;
import com.dagongma.kernel.commons.enums.ResultCode;
import com.dagongma.kernel.commons.exceptions.BusinessException;
import com.dagongma.kernel.core.entity.PageQuery;
import com.dagongma.kernel.core.entity.PageView;
import com.dagongma.mybatis.core.service.impl.AbstractCrudService;
import com.dyys.hr.dao.exam.ExamPaperQuestionMapper;
import com.dyys.hr.dao.exam.ExamQuestionAnswerMapper;
import com.dyys.hr.dao.exam.ExamQuestionMapper;
import com.dyys.hr.entity.exam.ExamPaperQuestion;
import com.dyys.hr.entity.exam.ExamQuestion;
import com.dyys.hr.entity.exam.ExamQuestionAnswer;
import com.dyys.hr.entity.exam.ExamQuestionExcel;
import com.dyys.hr.service.exam.IExamQuestionService;
import com.dyys.hr.vo.exam.ExamQuDetialsVO;
import com.dyys.hr.vo.exam.ExamQuestionVO;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Condition;

import java.util.*;

/**
 * <p>
 * 试题信息 服务实现类
 * </p>
 *
 * @author lidaan
 * @since 2022-04-20
 */
@Service
@Slf4j
public class ExamQuestionServiceImpl extends AbstractCrudService<ExamQuestion, Long> implements IExamQuestionService {
    @Autowired
    ExamQuestionMapper examQuestionMapper;
    @Autowired
    private ExamQuestionAnswerMapper examQuestionAnswerMapper;
    @Autowired
    private ExamPaperQuestionMapper examPaperQuestionMapper;

    /**
     * 分页获取问题列表
     *
     * @param pageQuery 查询条件
     * @return 分页结果
     */
    @Override
    public PageView<ExamQuestion> pageExamQuestion(PageQuery<Map<String, Object>> pageQuery) {
        Assert.notNull(pageQuery, "pageQuery is not null");
        Page page = PageHelper.startPage(pageQuery.getPageNum(), pageQuery.getPageSize());
        try {
            examQuestionMapper.listExamQuestion(pageQuery.getCondition());
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            throw new BusinessException(ResultCode.INTERFACE_INNER_INVOKE_ERROR);
        } finally {
            page.close();
        }

        return PageView.build(page);
    }

    /**
     * 查询问题列表
     *
     * @param params 查询条件
     * @return
     */
    @Override
    public List<ExamQuestion> listExamQuestion(Map<String, Object> params) {
        return examQuestionMapper.listExamQuestion(params);
    }

    /**
     * 获取试题详情
     *
     * @param id 主键id
     * @return 试题详情
     */
    @Override
    public ExamQuDetialsVO getExamQuestion(Long id) {
        return examQuestionMapper.getExamQuestion(id);
    }

    /**
     * Excel题目类型下拉项
     * @return
     */
    @Override
    public Map<Integer, List<String>> excelSelectMap() {
        Map<Integer, List<String>> selectMap = new HashMap<>();
        // 题目类型下拉项：1-单选，2-多选，3-判断，4-主观
        List<String> teacherTypeList = new ArrayList<>();
        teacherTypeList.add("1");
        teacherTypeList.add("2");
        teacherTypeList.add("3");
        teacherTypeList.add("4");
        selectMap.put(0, teacherTypeList);
        return selectMap;
    }

    /**
     * excel批量导入考试题
     * @param excelList
     * @param paperId
     * @param loginEmplId
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<ExamQuestionExcel> importExl(List<ExamQuestionExcel> excelList, Long paperId, String loginEmplId) {
        String letterStr = "a,b,c,d,e,f,g";
        List<String> optionFlags = Arrays.asList(letterStr.split(","));
        List<ExamQuestionExcel> errorList = new ArrayList<>();
        Map<String, Integer> checkMap = new HashMap<>();
        Map<String, ExamQuestion> questionMap = new HashMap<>();
        Map<String, List<ExamQuestionAnswer>> answerListMap = new HashMap<>();
        int i = 0;
        int errNum = 0;
        for (ExamQuestionExcel excel : excelList) {
            i++;
            ExamQuestionExcel errVO = new ExamQuestionExcel();
            // 判断题目类型是否为空
            if (excel.getQuType() == null) {
                BeanUtils.copyProperties(excel, errVO);
                errVO.setErrMsg("第" + i + "条记录，题目类型为空");
                errorList.add(errVO);
                errNum++;
                continue;
            }
            // 判断题目类型是否正确
            if (!excel.getQuType().equals(1) && !excel.getQuType().equals(2)
                    && !excel.getQuType().equals(3) && !excel.getQuType().equals(4)) {
                BeanUtils.copyProperties(excel, errVO);
                errVO.setErrMsg("第" + i + "条记录，题目类型不正确");
                errorList.add(errVO);
                errNum++;
                continue;
            }
            // 判断题目内容(标题)是否为空
            if (excel.getContent() == null || excel.getContent().trim().equals("")) {
                BeanUtils.copyProperties(excel, errVO);
                errVO.setErrMsg("第" + i + "条记录，题目内容(标题)为空");
                errorList.add(errVO);
                errNum++;
                continue;
            }
            // 判断题目类型和题目内容(标题)是否有重复数据
            String mapKey = excel.getQuType() + "-" + excel.getContent();
            if (checkMap.containsKey(mapKey)) {
                BeanUtils.copyProperties(excel, errVO);
                errVO.setErrMsg("第" + i + "条记录与第" + checkMap.get(mapKey) + "条记录，题目类型、题目内容(标题)等信息重复");
                errorList.add(errVO);
                errNum++;
                continue;
            }
            checkMap.put(mapKey, i);
            // 判断本试卷中是否存在同样的题目类型和题目内容(标题)
            ExamQuestionVO vo = new ExamQuestionVO();
            vo.setPaperId(String.valueOf(paperId));
            vo.setQuType(excel.getQuType());
            vo.setContent(excel.getContent());
            boolean res = this.checkUniqueData(vo);
            if (res) {
                BeanUtils.copyProperties(excel, errVO);
                errVO.setErrMsg("第" + i + "条记录，同样的题目类型和题目内容(标题)已存在当前试卷中");
                errorList.add(errVO);
                errNum++;
                continue;
            }
            // 判断题目分数
            if (!excel.getQuType().equals(4) && excel.getScore() == null) {
                BeanUtils.copyProperties(excel, errVO);
                errVO.setErrMsg("第" + i + "条记录，题目分数为空");
                errorList.add(errVO);
                errNum++;
                continue;
            }
            // 判断选项标识和选项内容
            if ((excel.getQuType().equals(1) || excel.getQuType().equals(2))
                    && (excel.getAnswers() == null || excel.getAnswers().trim().equals(""))) {
                BeanUtils.copyProperties(excel, errVO);
                errVO.setErrMsg("第" + i + "条记录，选项标识和选项内容为空");
                errorList.add(errVO);
                errNum++;
                continue;
            }
            // 判断正确答案
            if (!excel.getQuType().equals(4) && (excel.getRightAnswer() == null || excel.getRightAnswer().trim().equals(""))) {
                BeanUtils.copyProperties(excel, errVO);
                errVO.setErrMsg("第" + i + "条记录，正确答案为空");
                errorList.add(errVO);
                errNum++;
                continue;
            }
            // 判断单选题正确答案
            if (excel.getQuType().equals(1) && !optionFlags.contains(excel.getRightAnswer())) {
                BeanUtils.copyProperties(excel, errVO);
                errVO.setErrMsg("第" + i + "条记录，单选题的正确答案不正确");
                errorList.add(errVO);
                errNum++;
                continue;
            }
            // 判断判断题正确答案
            if (excel.getQuType().equals(3) && !excel.getRightAnswer().equals("0") && !excel.getRightAnswer().equals("1")) {
                BeanUtils.copyProperties(excel, errVO);
                errVO.setErrMsg("第" + i + "条记录，判断题的正确答案不正确");
                errorList.add(errVO);
                errNum++;
                continue;
            }
            // 处理试题数据
            if (errNum == 0) {
                // 处理题目数据
                ExamQuestion question = new ExamQuestion();
                BeanUtils.copyProperties(excel, question);
                // 处理答案选项
                List<ExamQuestionAnswer> answerList = new ArrayList<>();
                if (excel.getQuType() != 4) {
                    // 判断题
                    if (excel.getQuType().equals(3)) {
                        ExamQuestionAnswer answer = new ExamQuestionAnswer();
                        answer.setIsRight(Integer.parseInt(excel.getRightAnswer()));
                        answer.setSort(1);
                        answerList.add(answer);
                    } else { // 单选或多选
                        String[] optionArr = excel.getAnswers().split("\n");
                        List<String> rightAnswerList = Arrays.asList(excel.getRightAnswer().split("、"));
                        // 处理选项
                        int answerSort = 1;
                        for (String str : optionArr) {
                            ExamQuestionAnswer answer = new ExamQuestionAnswer();
                            String newStr = str.trim();
                            String itemIndex = newStr.substring(0,1); // 选项标识a~g
                            answer.setIsRight(0);
                            if (rightAnswerList.contains(itemIndex)) {
                                answer.setIsRight(1); // 该选项是答案
                            }
                            answer.setContent(newStr.substring(2)); // 选项内容
                            answer.setItemIndex(itemIndex);
                            answer.setSort(answerSort);
                            answerList.add(answer);
                            answerSort++;
                        }
                    }
                } else {
                    question.setScore(null);
                    question.setRightAnswer(null);
                }
                questionMap.put(mapKey, question);
                answerListMap.put(mapKey, answerList);
            }
        }
        // 数据入库
        if (errNum == 0) {
            this.batchDataSave(questionMap, answerListMap, paperId, loginEmplId);
        }
        return errorList;
    }

    /**
     * 批量导入数据入库
     * @param questionMap
     * @param answerListMap
     * @param paperId
     * @param loginEmplId
     */
    private void batchDataSave(Map<String, ExamQuestion> questionMap, Map<String, List<ExamQuestionAnswer>> answerListMap, Long paperId, String loginEmplId) {
        Date now = new Date(); // 系统当前时间
        int questionSort = 1;
        for (Map.Entry<String, ExamQuestion> entry : questionMap.entrySet()) {
            // 保存试题数据
            String key = entry.getKey();
            ExamQuestion questionEntity = entry.getValue();
            questionEntity.setIsDeleted(false);
            questionEntity.setCreator(loginEmplId);
            questionEntity.setCreateDate(now);
            Long quId = insertSelective(questionEntity);
            // 保存试题选项
            List<ExamQuestionAnswer> questionAnswerList = answerListMap.get(key);
            if (!questionAnswerList.isEmpty()) {
                for (ExamQuestionAnswer answer : questionAnswerList) {
                    answer.setQuId(String.valueOf(quId));
                }
                examQuestionAnswerMapper.insertBatchSelective(questionAnswerList);
            }
            ExamPaperQuestion paperQuestion = new ExamPaperQuestion();
            paperQuestion.setPaperId(String.valueOf(paperId));
            paperQuestion.setQuId(String.valueOf(quId));
            paperQuestion.setSort(questionSort);
            examPaperQuestionMapper.insertSelective(paperQuestion);
            questionSort++;
        }
    }

    /**
     * 校验试卷中的同题目类型的题目内容是否唯一
     * @param vo
     * @return
     */
    @Override
    public Boolean checkUniqueData(ExamQuestionVO vo) {
        Condition condition = new Condition(ExamQuestion.class);
        if (vo.getId() != null) {
            condition.createCriteria().andNotEqualTo("id", vo.getId())
                    .andEqualTo("quType", vo.getQuType())
                    .andEqualTo("content", vo.getContent())
                    .andEqualTo("isDeleted", 0);
        } else {
            condition.createCriteria().andEqualTo("quType", vo.getQuType())
                    .andEqualTo("content", vo.getContent())
                    .andEqualTo("isDeleted", 0);
        }
        int res = examQuestionMapper.selectCountByCondition(condition);
        return res > 0;
    }


}
