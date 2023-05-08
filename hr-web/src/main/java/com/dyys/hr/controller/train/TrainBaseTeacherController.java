package com.dyys.hr.controller.train;

import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.dagongma.kernel.annotation.ResponseResult;
import com.dagongma.kernel.commons.enums.ResultCode;
import com.dagongma.kernel.commons.exceptions.BusinessException;
import com.dyys.hr.constants.Constant;
import com.dyys.hr.dto.train.TrainBaseTeacherDTO;
import com.dyys.hr.dto.train.TrainBaseTeacherProgressDTO;
import com.dyys.hr.entity.train.excel.*;
import com.dyys.hr.examUtils.EasyExcelListener;
import com.dyys.hr.helper.UserHelper;
import com.dyys.hr.service.train.*;
import com.dyys.hr.utils.Result;
import com.dyys.hr.utils.SelectSheetWriteHandler;
import com.dyys.hr.vo.train.TeacherCanTeachingRecordsVO;
import com.dyys.hr.vo.train.TeacherHaveTaughtRecordsVO;
import com.dyys.hr.vo.train.TrainBaseTeacherDetailVO;
import com.dyys.hr.vo.train.TrainBaseTeacherVO;
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

/**
 *
 * 讲师表操作接口
 * @author WSJ
 * @date 2022/04/27
 */
@Slf4j
@RestController
@RequestMapping("/train/teacher")
@Api(value = "讲师信息",tags={"【培训】【资源管理】讲师管理-已完成"})
public class TrainBaseTeacherController {
    @Autowired
    private TrainBaseTeacherService trainBaseTeacherService;
    @Autowired
    private TrainBaseCourseTeacherService trainBaseCourseTeacherService;
    @Autowired
    private TrainBaseTeacherTaughtService trainBaseTeacherTaughtService;
    @Autowired
    private UserHelper userHelper;

    @ResponseResult
    @GetMapping("pageList")
    @ApiOperation(value = "分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = Constant.PAGE, value = "当前页码，从1开始", paramType = "query", required = true, dataType="int") ,
            @ApiImplicitParam(name = Constant.LIMIT, value = "每页显示记录数", paramType = "query",required = true, dataType="int") ,
            @ApiImplicitParam(name = "type", value = "讲师类型", paramType = "query",dataType="int") ,
            @ApiImplicitParam(name = "name", value = "讲师名称", paramType = "query", dataType="String") ,
            @ApiImplicitParam(name = "organizationName", value = "所属公司/机构名称", paramType = "query", dataType="String") ,
            @ApiImplicitParam(name = "level", value = "讲师等级", paramType = "query",dataType="int")
    })
    public PageInfo<TrainBaseTeacherVO> pageList(@ApiIgnore @RequestParam Map<String, Object> params){
        return trainBaseTeacherService.pageList(params);
    }

    @ResponseResult
    @PostMapping("save")
    @ApiOperation(value = "保存数据")
    public Long save(@RequestBody @Validated(TrainBaseTeacherDTO.Insert.class) TrainBaseTeacherDTO dto) {
        validateParams(dto);
        return trainBaseTeacherService.save(dto,userHelper.getLoginEmplId());
    }

    private void validateParams(TrainBaseTeacherDTO dto){
        //根据讲师类型判断校验参数 1.内部讲师 2.外部讲师
        Integer type = dto.getType();
        if(type == 1 && (dto.getNumber() == null || dto.getNumber().trim().equals(""))){
            throw new BusinessException(ResultCode.EXCEPTION,"内部讲师工号不能为空");
        }
        // 若存在成长历程则需要判断成长历程
        List<TrainBaseTeacherProgressDTO> progressList = dto.getProgressList();
        if (!progressList.isEmpty()) {
            int i = 0;
            for (TrainBaseTeacherProgressDTO progressDTO : progressList) {
                i++;
                if (progressDTO.getLevel() == null) {
                    throw new BusinessException(ResultCode.EXCEPTION, "成长历程第" + i + "条，请选择讲师级别");
                }
                if (progressDTO.getStartTime() == null) {
                    throw new BusinessException(ResultCode.EXCEPTION, "成长历程第" + i + "条，请选择开始时间");
                }
                if (progressDTO.getEndTime() == null) {
                    throw new BusinessException(ResultCode.EXCEPTION, "成长历程第" + i + "条，请选择结束时间");
                }
                if (!progressDTO.getStartTime().before(progressDTO.getEndTime())) {
                    throw new BusinessException(ResultCode.EXCEPTION, "成长历程第" + i + "条，开始时间必须小于结束时间");
                }
            }
        }
        boolean res = trainBaseTeacherService.checkUniqueData(dto);
        if (res) {
            throw new BusinessException(ResultCode.EXCEPTION,"该讲师已存在，请勿重复添加");
        }
    }

    @ResponseResult
    @GetMapping("find")
    @ApiOperation(value = "根据讲师ID获取数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "讲师id", paramType = "path", required = true, dataType="int") ,
    })
    public TrainBaseTeacherDetailVO find(@ApiIgnore @RequestParam Long id) {
        return trainBaseTeacherService.selectByTeacherId(id);
    }

    @ResponseResult
    @PostMapping("update")
    @ApiOperation(value = "更新数据")
    public Integer update(@RequestBody @Validated(TrainBaseTeacherDTO.Update.class) TrainBaseTeacherDTO dto) {
        validateParams(dto);
        return trainBaseTeacherService.update(dto,userHelper.getLoginEmplId());
    }

    @ResponseResult
    @GetMapping("canTeachingRecords")
    @ApiOperation(value = "可授课记录列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "teacherId", value = "讲师id", paramType = "path", required = true, dataType="int"),
    })
    public List<TeacherCanTeachingRecordsVO> canTeachingRecords(@ApiIgnore @RequestParam Map<String, Object> params){
        return trainBaseCourseTeacherService.canTeachingRecords(params);
    }

    @ResponseResult
    @GetMapping("haveTaughtRecords")
    @ApiOperation(value = "已授课记录列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "teacherNumber", value = "讲师编号", paramType = "path", required = true, dataType="String"),
    })
    public List<TeacherHaveTaughtRecordsVO> haveTaughtRecords(@ApiIgnore @RequestParam Map<String, Object> params){
        return trainBaseTeacherTaughtService.taughtCourseList(params);
    }

    @ResponseResult
    @GetMapping("teacherListExport")
    @ApiOperation(value = "讲师管理列表导出")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "type", value = "讲师类型", paramType = "query",dataType="int") ,
            @ApiImplicitParam(name = "name", value = "讲师名称", paramType = "query", dataType="String") ,
            @ApiImplicitParam(name = "organizationName", value = "所属公司/机构名称", paramType = "query", dataType="String") ,
            @ApiImplicitParam(name = "level", value = "讲师等级", paramType = "query",dataType="int")
    })
    public void teacherListExport(HttpServletResponse response, @ApiIgnore @RequestParam Map<String, Object> params) throws IOException {
        List<BaseTeacherListExcel> teacherList = trainBaseTeacherService.teacherListExport(params); // 讲师列表
        List<BaseTeacherProgressExcel> progressList = trainBaseTeacherService.progressListExport(params); // 讲师成长历程
        List<AbleCourseExcel> ableCourseList = trainBaseTeacherService.ableCourseListExport(params); // 讲师可授课程
        List<TaughtCourseExcel> taughtCourseList = trainBaseTeacherService.taughtCourseListExport(params); // 讲师已授课程

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码 当然和easyExcel没有关系
        String fileName = URLEncoder.encode("讲师列表导出表", "UTF-8").replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ExcelTypeEnum.XLSX.getValue());

        // 创建临时文件
        ExcelWriter excelWriterBuilder = EasyExcelFactory.write(response.getOutputStream()).build();
        WriteSheet teacherSheetWrite = EasyExcelFactory.writerSheet(0).sheetName("讲师列表").head(BaseTeacherListExcel.class).build();
        WriteSheet progressSheetWrite = EasyExcelFactory.writerSheet(1).sheetName("成长历程").head(BaseTeacherProgressExcel.class).build();
        WriteSheet ableCourseSheetWrite = EasyExcelFactory.writerSheet(2).sheetName("可授课记录").head(AbleCourseExcel.class).build();
        WriteSheet taughtCourseSheetWrite = EasyExcelFactory.writerSheet(3).sheetName("已授课记录").head(TaughtCourseExcel.class).build();
        //参数一为要导出的数据，参数二为配置信息
        excelWriterBuilder.write(teacherList, teacherSheetWrite);
        excelWriterBuilder.write(progressList, progressSheetWrite);
        excelWriterBuilder.write(ableCourseList, ableCourseSheetWrite);
        excelWriterBuilder.write(taughtCourseList, taughtCourseSheetWrite);
        excelWriterBuilder.finish();
    }

    @GetMapping("/exportTml")
    @ApiOperation(value = "下载讲师导入模板")
    public void exportTml(HttpServletResponse response) throws IOException {
        List<BaseTeacherExcel> excelList = new ArrayList<>();
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        String fileName = URLEncoder.encode("讲师导入模板", "UTF-8").replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ExcelTypeEnum.XLSX.getValue());

        // 列下拉框数据
        Map<Integer, List<String>> selectMap = trainBaseTeacherService.excelSelectMap();

        // 绕过了创建临时文件，直接将数据读到流中传递至客户端
        EasyExcelFactory.write(response.getOutputStream(), BaseTeacherExcel.class)
                .registerWriteHandler(new SelectSheetWriteHandler(selectMap))
                .excelType(ExcelTypeEnum.XLSX)
                .sheet("讲师数据")
                .doWrite(excelList);
    }

    @PostMapping(value = "/importTeacher", headers = "content-type=multipart/form-data")
    @ApiOperation(value = "导入讲师数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "file", value = "导入数据excel", dataTypeClass = MultipartFile.class, required = true, allowMultiple = true)
    })
    @Transactional(rollbackFor = Exception.class)
    public Result<List<BaseTeacherExcel>> importTeacher(@RequestBody MultipartFile file) throws IOException {
        if (file == null) {
            return new Result<List<BaseTeacherExcel>>().error("请上传要导入的文件");
        }
        //定义最终入库的数据集合
        EasyExcelListener<BaseTeacherExcel> listener = new EasyExcelListener<>();
        List<BaseTeacherExcel> excelList =
                EasyExcelFactory.read(file.getInputStream(), BaseTeacherExcel.class, listener)
                        .sheet().doReadSync();
        if (excelList.isEmpty()) {
            return new Result<List<BaseTeacherExcel>>().error("导入文件讲师数据不能为空");
        }
        List<BaseTeacherExcel> errorList = trainBaseTeacherService.importExl(excelList, userHelper.getLoginEmplId());
        if (errorList.isEmpty()) {
            return new Result<List<BaseTeacherExcel>>().success("导入成功");
        }
        return new Result<List<BaseTeacherExcel>>().error("数据校验不通过", errorList);
    }
}