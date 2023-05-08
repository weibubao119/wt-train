package com.dyys.hr.controller.train;

import cn.hutool.core.date.DateUtil;
import com.dagongma.kernel.annotation.ResponseResult;
import com.dagongma.kernel.commons.enums.ResultCode;
import com.dagongma.kernel.commons.exceptions.BusinessException;
import com.dyys.hr.constants.Constant;
import com.dyys.hr.constants.QuestionnaireScoreObjectData;
import com.dyys.hr.dto.train.QuestionnaireDTO;
import com.dyys.hr.dto.train.QuestionnaireUserSubmitDTO;
import com.dyys.hr.entity.train.Questionnaire;
import com.dyys.hr.helper.UserHelper;
import com.dyys.hr.service.train.QuestionnaireService;
import com.dyys.hr.utils.Result;
import com.dyys.hr.vo.train.QuestionnaireScoreObjectTypeVO;
import com.dyys.hr.vo.train.QuestionnaireTemplatePageVo;
import com.dyys.hr.vo.train.QuestionnaireUserPageVo;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *
 * 评估问卷操作接口
 * @author WSJ
 */
@RestController
@ResponseResult
@Slf4j
@RequestMapping("/train/questionnaire")
@Api(value = "评估问卷管理",tags={"【培训】【评估问卷】评估问卷管理-已完成"})
public class QuestionnaireController {
    @Autowired
    private QuestionnaireService questionnaireService;

    @Autowired
    private UserHelper userHelper;

    @GetMapping("pageList")
    @ApiOperation(value = "分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = Constant.PAGE, value = "当前页码，从1开始", paramType = "query", required = true, dataType="int") ,
            @ApiImplicitParam(name = Constant.LIMIT, value = "每页显示记录数", paramType = "query",required = true, dataType="int") ,
            @ApiImplicitParam(name = "title", value = "问卷名称", paramType = "query", dataType="String") ,
            @ApiImplicitParam(name = "code", value = "问卷编号", paramType = "query", dataType="String") ,
            @ApiImplicitParam(name = "instructions", value = "问卷描述", paramType = "query", dataType="String") ,
            @ApiImplicitParam(name = "createUser", value = "创建人", paramType = "query", dataType="String") ,
            @ApiImplicitParam(name = "status", value = "状态 1.已发布 0.未发布", paramType = "query",dataType="int")
    })
    public PageInfo<QuestionnaireTemplatePageVo> pageList(@ApiIgnore @RequestParam Map<String, Object> params){
        return questionnaireService.pageList(params);
    }

    @GetMapping("scoreObjectSelectList")
    @ApiOperation(value = "评分表评分对象筛选列表")
    public List<QuestionnaireScoreObjectTypeVO> typeSelectList(){
        List<QuestionnaireScoreObjectTypeVO> typeList = new ArrayList<>();
        Map<Integer, String> map = QuestionnaireScoreObjectData.typeMap();
        for (int i = 1; i <= map.size(); i++) {
            QuestionnaireScoreObjectTypeVO vo = new QuestionnaireScoreObjectTypeVO();
            vo.setType(i);
            vo.setName(map.get(i));
            typeList.add(vo);
        }
        return typeList;
    }

    @PostMapping("save")
    @ApiOperation(value = "创建问卷")
    public Long save(@RequestBody @Validated(QuestionnaireDTO.Insert.class) QuestionnaireDTO dto) {
        return questionnaireService.save(dto,userHelper.getLoginEmplId());
    }

    @GetMapping("find")
    @ApiOperation(value = "问卷详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "问卷ID", paramType = "path", required = true, dataType="int") ,
    })
    public QuestionnaireDTO find(@ApiIgnore @RequestParam Map<String, Object> params) {
        return questionnaireService.getDetail(params);
    }

    @GetMapping("copy")
    @ApiOperation(value = "复制问卷")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "问卷ID", paramType = "path", required = true, dataType="int") ,
    })
    public Long copy(@ApiIgnore @RequestParam Map<String, Object> params) {
        QuestionnaireDTO detail = questionnaireService.getDetail(params);
        Questionnaire questionnaire = new Questionnaire();
        BeanUtils.copyProperties(detail,questionnaire);
        String code = DateUtil.format(new Date(), "yyyyMMddHHmmssSSS");
        questionnaire.setId(null);
        questionnaire.setCode(code);
        questionnaire.setStatus(0);
        questionnaire.setCreateUser(userHelper.getLoginEmplId());
        questionnaire.setCreateTime(System.currentTimeMillis()/1000);
        return questionnaireService.insertSelective(questionnaire);
    }

    @PutMapping("update")
    @ApiOperation(value = "修改问卷")
    public Integer update(@RequestBody @Validated(QuestionnaireDTO.Update.class) QuestionnaireDTO dto) {
        return questionnaireService.update(dto,userHelper.getLoginEmplId());
    }


    @GetMapping("userFillPageList")
    @ApiOperation(value = "用户填写问卷分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = Constant.PAGE, value = "当前页码，从1开始", paramType = "query", required = true, dataType="int") ,
            @ApiImplicitParam(name = Constant.LIMIT, value = "每页显示记录数", paramType = "query",required = true, dataType="int") ,
            @ApiImplicitParam(name = "status", value = "状态 1.已填写 0.未填写", paramType = "query",dataType="int"),
            @ApiImplicitParam(name = "title", value = "问卷名称", paramType = "query", dataType="String") ,
    })
    public PageInfo<QuestionnaireUserPageVo> userFillPageList(@ApiIgnore @RequestParam Map<String, Object> params){
        params.put("userId",userHelper.getLoginEmplId());
        return questionnaireService.userFillPageList(params);
    }


    @PostMapping("userSubmit")
    @ApiOperation(value = "用户提交问卷评估")
    public Integer userSubmit(@RequestBody @Validated(QuestionnaireUserSubmitDTO.Insert.class) QuestionnaireUserSubmitDTO dto) {
        return questionnaireService.userSubmit(dto,userHelper.getLoginEmplId());
    }

    @GetMapping("getUserSubmitDetail")
    @ApiOperation(value = "获取用户问卷提交详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户问卷记录表id", paramType = "path", required = true, dataType="int") ,
    })
    public QuestionnaireUserSubmitDTO getUserSubmitDetail(@ApiIgnore @RequestParam Map<String, Object> params) {
        return questionnaireService.getUserSubmitDetail(params);
    }

    @PostMapping("/delete")
    @ApiOperation(value = "删除问卷")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "问卷id", paramType = "path", required = true, dataType="long") ,
    })
    public Result<Boolean> delete(@ApiIgnore @RequestBody Map<String, Object> params) {
        Long id = Long.parseLong(params.get("id").toString());
        Questionnaire questionnaire = questionnaireService.selectById(id);
        if(questionnaire.getStatus() == 1){
            throw new BusinessException(ResultCode.EXCEPTION,"问卷已发布不能删除!");
        }
        questionnaireService.deleteById(id);
        return new Result<Boolean>().success("删除成功");
    }

}
