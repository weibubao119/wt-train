package com.dyys.hr.controller.train;

import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.dagongma.kernel.annotation.ResponseResult;
import com.dagongma.kernel.commons.enums.ResultCode;
import com.dagongma.kernel.commons.exceptions.BusinessException;
import com.dyys.hr.constants.Constant;
import com.dyys.hr.dto.train.TrainBaseCourseDTO;
import com.dyys.hr.entity.train.TrainBaseCourse;
import com.dyys.hr.entity.train.excel.AbleTeacherExcel;
import com.dyys.hr.entity.train.excel.BaseCourseExcel;
import com.dyys.hr.entity.train.excel.BaseCourseListExcel;
import com.dyys.hr.examUtils.EasyExcelListener;
import com.dyys.hr.helper.UserHelper;
import com.dyys.hr.service.train.TrainBaseCourseService;
import com.dyys.hr.service.train.TrainMaterialsLearningRecordService;
import com.dyys.hr.utils.Result;
import com.dyys.hr.utils.SelectSheetWriteHandler;
import com.dyys.hr.vo.train.TrainBaseCourseVO;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/train/course")
@Api(value = "课程管理", tags = {"【培训】【资源管理】课程管理-已完成"})
public class TrainBaseCourseController {
    @Autowired
    private TrainBaseCourseService trainBaseCourseService;
    @Autowired
    private TrainMaterialsLearningRecordService trainMaterialsLearningRecordService;

    @Autowired
    private UserHelper userHelper;

    @ResponseResult
    @GetMapping("pageList")
    @ApiOperation(value = "分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = Constant.PAGE, value = "当前页码，从1开始", paramType = "query", required = true, dataType="int") ,
            @ApiImplicitParam(name = Constant.LIMIT, value = "每页显示记录数", paramType = "query",required = true, dataType="int") ,
            @ApiImplicitParam(name = "name", value = "课程名称", paramType = "query", dataType="String") ,
            @ApiImplicitParam(name = "category", value = "课程类别常量id", paramType = "query",dataType="Int") ,
            @ApiImplicitParam(name = "courseSource", value = "课程来源 1.外部 2.自有", paramType = "query",dataType="int")
    })
    public PageInfo<TrainBaseCourseVO> pageList(@ApiIgnore @RequestParam Map<String, Object> params){
        return trainBaseCourseService.pageList(params);
    }

    @ResponseResult
    @PostMapping("save")
    @ApiOperation(value = "保存数据")
    public Long save(@RequestBody @Validated(TrainBaseCourseDTO.Insert.class) TrainBaseCourseDTO dto) {
        boolean res = trainBaseCourseService.checkUniqueData(dto);
        if (res) {
            throw new BusinessException(ResultCode.EXCEPTION,"当前课程类别已存在该课程名称，请勿重复添加");
        }
        return trainBaseCourseService.save(dto,userHelper.getLoginEmplId());
    }

    @ResponseResult
    @GetMapping("find")
    @ApiOperation(value = "根据课程ID获取数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "课程ID", paramType = "path", required = true, dataType="int") ,
    })
    public TrainBaseCourseDTO find(@ApiIgnore @RequestParam Long id) {
        return trainBaseCourseService.selectByCourseId(id);
    }

    @ResponseResult
    @PostMapping("update")
    @ApiOperation(value = "更新数据")
    public Integer update(@RequestBody @Validated(TrainBaseCourseDTO.Update.class) TrainBaseCourseDTO dto) {
        boolean res = trainBaseCourseService.checkUniqueData(dto);
        if (res) {
            throw new BusinessException(ResultCode.EXCEPTION,"当前课程类别已存在该课程名称，请勿重复添加");
        }
        return trainBaseCourseService.update(dto,userHelper.getLoginEmplId());
    }

    @ResponseResult
    @GetMapping("courseListExport")
    @ApiOperation(value = "课程管理列表导出")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "课程名称", paramType = "query", dataType="String") ,
            @ApiImplicitParam(name = "category", value = "课程类别id", paramType = "query",dataType="int") ,
            @ApiImplicitParam(name = "courseSource", value = "课程来源 1.外部 2.自有", paramType = "query",dataType="int")
    })
    public void courseListExport(HttpServletResponse response, @ApiIgnore @RequestParam Map<String, Object> params) throws IOException {
        List<BaseCourseListExcel> courseList = trainBaseCourseService.courseListExport(params);
        List<AbleTeacherExcel> ableTeacherList = trainBaseCourseService.ableTeacherList(params);

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码 当然和easyExcel没有关系
        String fileName = URLEncoder.encode("课程数据导出", "UTF-8").replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ExcelTypeEnum.XLSX.getValue());

        // 创建临时文件
        ExcelWriter excelWriterBuilder = EasyExcelFactory.write(response.getOutputStream()).build();
        WriteSheet courseSheetWrite = EasyExcelFactory.writerSheet(0).sheetName("课程列表").head(BaseCourseListExcel.class).build();
        WriteSheet teacherSheetWrite = EasyExcelFactory.writerSheet(1).sheetName("可授课讲师").head(AbleTeacherExcel.class).build();
        //参数一为要导出的数据，参数二为配置信息
        excelWriterBuilder.write(courseList, courseSheetWrite);
        excelWriterBuilder.write(ableTeacherList, teacherSheetWrite);
        excelWriterBuilder.finish();
    }

    @ResponseResult
    @GetMapping("/exportTml")
    @ApiOperation(value = "下载课程导入模板")
    public void exportTml(HttpServletResponse response) throws IOException {
        List<BaseCourseExcel> excelList = new ArrayList<>();
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        String fileName = URLEncoder.encode("课程导入模板", "UTF-8").replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ExcelTypeEnum.XLSX.getValue());

        // 列下拉框数据
        Map<Integer, List<String>> selectMap = trainBaseCourseService.excelSelectMap();

        // 绕过了创建临时文件，直接将数据读到流中传递至客户端
        EasyExcelFactory.write(response.getOutputStream(), BaseCourseExcel.class)
                .registerWriteHandler(new SelectSheetWriteHandler(selectMap))
                .excelType(ExcelTypeEnum.XLSX)
                .sheet("课程列表")
                .doWrite(excelList);
    }

    @PostMapping(value = "/importCourse", headers = "content-type=multipart/form-data")
    @ApiOperation(value = "导入课程数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "file", value = "导入数据excel", dataTypeClass = MultipartFile.class, required = true, allowMultiple = true)
    })
    @Transactional(rollbackFor = Exception.class)
    public Result<List<BaseCourseExcel>> importCourse(@RequestBody MultipartFile file) throws IOException {
        if (file == null) {
            return new Result<List<BaseCourseExcel>>().error("请上传要导入的文件");
        }
        //定义最终入库的数据集合
        EasyExcelListener<BaseCourseExcel> listener = new EasyExcelListener<>();
        List<BaseCourseExcel> excelList =
                EasyExcelFactory.read(file.getInputStream(), BaseCourseExcel.class, listener)
                        .sheet().doReadSync();
        if (excelList.isEmpty()) {
            return new Result<List<BaseCourseExcel>>().error("导入文件课程数据不能为空");
        }
        List<BaseCourseExcel> errorList = trainBaseCourseService.importExl(excelList, userHelper.getLoginEmplId());
        if (errorList.isEmpty()) {
            return new Result<List<BaseCourseExcel>>().success("导入成功");
        }
        return new Result<List<BaseCourseExcel>>().error("数据校验不通过", errorList);
    }

    @ResponseResult
    @PostMapping("/changeStatus")
    @ApiOperation(value = "修改课程状态")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "课程ID", paramType = "path", required = true, dataType="int") ,
            @ApiImplicitParam(name = "status", value = "状态 1.已发布 0.未发布", paramType = "path", required = true, dataType="int") ,
    })
    public Integer changeStatus(@ApiIgnore @RequestBody Map<String, Object> params) {
        TrainBaseCourse baseCourse = new TrainBaseCourse();
        baseCourse.setId(Long.valueOf(params.get("id").toString()));
        baseCourse.setStatus(Integer.parseInt(params.get("status").toString()));
        baseCourse.setUpdateUser(userHelper.getLoginEmplId());
        baseCourse.setUpdateTime(System.currentTimeMillis()/1000);
        return trainBaseCourseService.updateSelective(baseCourse);
    }

    @ResponseResult
    @GetMapping("materialsLearningPageData")
    @ApiOperation(value = "课程材料学习页数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "课程ID", paramType = "path", required = true, dataType="int") ,
            @ApiImplicitParam(name = "userId", value = "员工ID", paramType = "path", required = true, dataType="int") ,
    })
    public TrainBaseCourseVO materialsLearningPageData(@ApiIgnore @RequestParam Map<String, Object> params) {
        return trainBaseCourseService.materialsLearningPageData(params);
    }

    @ResponseResult
    @PostMapping("/materialsLearningRecord")
    @ApiOperation(value = "课程材料学习记录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "材料ID", paramType = "path", required = true, dataType="int") ,
            @ApiImplicitParam(name = "materialsType", value = "材料类型 1.视频 2.音频 3.其他", paramType = "path", required = true, dataType="int"),
            @ApiImplicitParam(name = "duration", value = "学习时长", paramType = "path", dataType="string"),
            @ApiImplicitParam(name = "userId", value = "员工ID", paramType = "path", required = true, dataType="string") ,
    })
    public Integer materialsLearningRecord(@ApiIgnore @RequestBody Map<String, Object> params) {
        params.put("type",1);
        return trainMaterialsLearningRecordService.materialsLearningRecord(params);
    }
}