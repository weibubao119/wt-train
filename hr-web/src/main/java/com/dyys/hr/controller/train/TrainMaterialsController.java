package com.dyys.hr.controller.train;

import com.dagongma.kernel.annotation.ResponseResult;
import com.dyys.hr.constants.Constant;
import com.dyys.hr.dto.train.IdDTO;
import com.dyys.hr.dto.train.TrainMaterialsDTO;
import com.dyys.hr.helper.UserHelper;
import com.dyys.hr.service.train.TrainMaterialsLearningRecordService;
import com.dyys.hr.service.train.TrainMaterialsService;
import com.dyys.hr.vo.train.TrainBaseCourseVO;
import com.dyys.hr.vo.train.TrainMaterialsLearnVO;
import com.dyys.hr.vo.train.TrainMaterialsVO;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;
import java.util.Map;

/**
 * 培训管理资料接口
 * @author WSJ
 * @date 2022/05/22
 */
@ResponseResult
@Slf4j
@RestController
@RequestMapping("/train/materials")
@Api(value = "项目管理接口", tags = {"【培训】【过程管理】培训项目-培训资料管理-已完成"})
public class TrainMaterialsController {
    @Autowired
    private TrainMaterialsService trainMaterialsService;
    @Autowired
    private TrainMaterialsLearningRecordService trainMaterialsLearningRecordService;
    @Autowired
    private UserHelper userHelper;

    @GetMapping("pageList")
    @ApiOperation(value = "资料列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = Constant.PAGE, value = "当前页码，从1开始", paramType = "query", required = true, dataType="int") ,
            @ApiImplicitParam(name = Constant.LIMIT, value = "每页显示记录数", paramType = "query",required = true, dataType="int") ,
            @ApiImplicitParam(name = "programsId", value = "项目id", paramType = "query", required = true,dataType="int"),
    })
    public PageInfo<TrainMaterialsVO> pageList(@ApiIgnore @RequestParam Map<String, Object> params){
        return trainMaterialsService.pageList(params);
    }

    @PostMapping("save")
    @ApiOperation(value = "新增资料")
    public Long save(@RequestBody @Validated(TrainMaterialsDTO.Insert.class) TrainMaterialsDTO dto) {
        return trainMaterialsService.save(dto,userHelper.getLoginEmplId());
    }

    @DeleteMapping("delete")
    @ApiOperation(value = "移除资料")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "资料id", paramType = "path", required = true, dataType="int") ,
    })
    public Integer delete(@ApiIgnore @RequestParam Long id) {
        return trainMaterialsService.deleteById(id);
    }

    @PostMapping("batchChangeStatus")
    @ApiOperation(value = "批量发布")
    public Boolean batchChangeStatus(@RequestBody @Validated(IdDTO.Insert.class) List<IdDTO> dtoList) {
        return trainMaterialsService.batchChangeStatus(dtoList,userHelper.getLoginEmplId());
    }

    @PostMapping("pushLearningNotice")
    @ApiOperation(value = "推送学习")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "programsId", value = "培训班id", paramType = "path", required = true, dataType="int") ,
    })
    public Boolean pushLearningNotice(@RequestBody @RequestParam Long programsId) {
        return trainMaterialsService.pushLearningNotice(programsId,userHelper.getLoginEmplId());
    }

    @ResponseResult
    @GetMapping("materialsLearningPageData")
    @ApiOperation(value = "培训材料学习页数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "programsId", value = "培训班ID", paramType = "path", required = true, dataType="int") ,
    })
    public TrainMaterialsLearnVO materialsLearningPageData(@ApiIgnore @RequestParam Long programsId) {
        return trainMaterialsService.materialsLearningPageData(programsId, userHelper.getLoginEmplId());
    }

    @ResponseResult
    @PostMapping("/materialsLearningRecord")
    @ApiOperation(value = "培训材料学习记录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "材料ID", paramType = "path", required = true, dataType="int") ,
            @ApiImplicitParam(name = "materialsType", value = "材料类型 1.音视频 2.其他", paramType = "path", required = true, dataType="int"),
            @ApiImplicitParam(name = "duration", value = "学习时长", paramType = "path", dataType="string"),
    })
    public Integer materialsLearningRecord(@ApiIgnore @RequestBody Map<String, Object> params) {
        params.put("userId",userHelper.getLoginEmplId());
        params.put("type",2);
        return trainMaterialsLearningRecordService.materialsLearningRecord(params);
    }
}