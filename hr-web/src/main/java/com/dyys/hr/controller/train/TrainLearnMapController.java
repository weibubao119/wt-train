package com.dyys.hr.controller.train;

import cn.hutool.json.JSONUtil;
import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.dagongma.kernel.annotation.ResponseResult;
import com.dagongma.kernel.commons.enums.ResultCode;
import com.dagongma.kernel.commons.exceptions.BusinessException;
import com.dagongma.kernel.core.entity.PageView;
import com.dyys.hr.constants.Constant;
import com.dyys.hr.dto.train.LearnMapCourseDTO;
import com.dyys.hr.dto.train.LearnMapDTO;
import com.dyys.hr.dto.train.LearnMapStaffDTO;
import com.dyys.hr.entity.train.excel.MapCourseExcel;
import com.dyys.hr.entity.train.excel.MapStuExcel;
import com.dyys.hr.examUtils.EasyExcelListener;
import com.dyys.hr.helper.UserHelper;
import com.dyys.hr.service.train.TrainLearnMapCourseService;
import com.dyys.hr.service.train.TrainLearnMapService;
import com.dyys.hr.service.train.TrainLearnMapStaffService;
import com.dyys.hr.utils.Result;
import com.dyys.hr.utils.SelectSheetWriteHandler;
import com.dyys.hr.vo.train.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
 * 学习地图
 * Create by yangye
 * Date 2022/9/6 14:03
 */
@Slf4j
@RestController
@RequestMapping("/train/map")
@Api(value = "地图",tags={"【培训】【系统配置】地图管理-已完成"})
public class TrainLearnMapController {
    @Autowired
    private UserHelper userHelper;
    @Autowired
    private TrainLearnMapService trainLearnMapService;
    @Autowired
    private TrainLearnMapCourseService trainLearnMapCourseService;
    @Autowired
    private TrainLearnMapStaffService trainLearnMapStaffService;

    @ResponseResult
    @GetMapping("mapPageList")
    @ApiOperation(value = "学习地图分页列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = Constant.PAGE, value = "当前页码，从1开始", paramType = "query", required = true, dataType = "int"),
            @ApiImplicitParam(name = Constant.LIMIT, value = "每页显示记录数", paramType = "query", required = true, dataType = "int"),
            @ApiImplicitParam(name = "mapCode", value = "地图编号", paramType = "query", dataType="String") ,
            @ApiImplicitParam(name = "mapName", value = "地图名称", paramType = "query",dataType="String") ,
            @ApiImplicitParam(name = "posnSecCode", value = "职序", paramType = "query",dataType="String")
    })
    public PageView<LearnMapVO> mapPageList(@RequestParam @ApiIgnore Map<String, Object> params) {
        return trainLearnMapService.mapPageList(params);
    }

    @ResponseResult
    @PostMapping("mapAdd")
    @ApiOperation(value = "学习地图添加")
    public Long mapAdd(@RequestBody @Validated(LearnMapDTO.Insert.class) LearnMapDTO learnMapDTO) {
        String resultMsg = trainLearnMapService.checkUniqueData(learnMapDTO, "add");
        if (!resultMsg.equals("")) {
            throw new BusinessException(ResultCode.ERROR, resultMsg);
        }
        return trainLearnMapService.mapAdd(learnMapDTO, userHelper.getLoginEmplId());
    }

    @ResponseResult
    @PostMapping("mapUpdate")
    @ApiOperation(value = "学习地图更新")
    public Integer mapUpdate(@RequestBody @Validated(LearnMapDTO.Update.class) LearnMapDTO learnMapDTO) {
        String resultMsg = trainLearnMapService.checkUniqueData(learnMapDTO, "update");
        if (!resultMsg.equals("")) {
            throw new BusinessException(ResultCode.ERROR, resultMsg);
        }
        return trainLearnMapService.mapUpdate(learnMapDTO, userHelper.getLoginEmplId());
    }

    @ResponseResult
    @GetMapping("mapInfo")
    @ApiOperation(value = "学习地图详情")
    @ApiImplicitParam(name = "id", value = "地图ID", paramType = "path", required = true, dataType="Long")
    public LearnMapInfoVO mapInfo(@RequestParam Long id) {
        return trainLearnMapService.mapInfo(id);
    }

    @ResponseResult
    @GetMapping("mapInfoCourseList")
    @ApiOperation(value = "学习地图详情-职级课程列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "mapId", value = "地图ID", paramType = "query", required = true, dataType="String") ,
            @ApiImplicitParam(name = "posnGradeCode", value = "职级编码", paramType = "query", required = true,dataType="String")
    })
    public List<LearnMapCourseVO> mapInfoCourseList(@RequestParam @ApiIgnore Map<String, Object> params) {
        return trainLearnMapCourseService.mapInfoCourseList(params);
    }

    @ResponseResult
    @PostMapping("mapCourseAdd")
    @ApiOperation(value = "学习地图课程添加")
    public Long mapCourseAdd(@RequestBody @Validated(LearnMapCourseDTO.Insert.class) LearnMapCourseDTO learnMapCourseDTO) {
        String resultMsg = trainLearnMapCourseService.checkUniqueData(learnMapCourseDTO, "add");
        if (!resultMsg.equals("")) {
            throw new BusinessException(ResultCode.ERROR, resultMsg);
        }
        return trainLearnMapCourseService.mapCourseAdd(learnMapCourseDTO, userHelper.getLoginEmplId(), 0);
    }

    @ResponseResult
    @PostMapping("mapCourseUpdate")
    @ApiOperation(value = "学习地图课程更新")
    public Integer mapCourseUpdate(@RequestBody @Validated(LearnMapCourseDTO.Insert.class) LearnMapCourseDTO learnMapCourseDTO) {
        String resultMsg = trainLearnMapCourseService.checkUniqueData(learnMapCourseDTO, "update");
        if (!resultMsg.equals("")) {
            throw new BusinessException(ResultCode.ERROR, resultMsg);
        }
        return trainLearnMapCourseService.mapCourseUpdate(learnMapCourseDTO, userHelper.getLoginEmplId());
    }

    @ResponseResult
    @PostMapping("mapCourseDel")
    @ApiOperation(value = "学习地图课程删除")
    public Integer mapCourseDel(@RequestBody String id) {
        Map<String, Object> params = JSONUtil.parseObj(id);
        Long mapCourseId = Long.valueOf(params.get("id").toString());
        return trainLearnMapCourseService.mapCourseDelById(mapCourseId);
    }

    @ResponseResult
    @GetMapping("mapSelectList")
    @ApiOperation(value = "学习地图选择列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "mapCode", value = "地图编号", paramType = "query", dataType="String") ,
            @ApiImplicitParam(name = "mapName", value = "地图名称", paramType = "query",dataType="String")
    })
    public List<LearnMapSelectVO> mapSelectList(@RequestParam @ApiIgnore Map<String, Object> params) {
        return trainLearnMapService.mapSelectList(params);
    }

    @ResponseResult
    @GetMapping("studentMapPageList")
    @ApiOperation(value = "学员地图分页列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = Constant.PAGE, value = "当前页码，从1开始", paramType = "query", required = true, dataType = "int"),
            @ApiImplicitParam(name = Constant.LIMIT, value = "每页显示记录数", paramType = "query", required = true, dataType = "int"),
            @ApiImplicitParam(name = "emplId", value = "工号", paramType = "query", dataType="String") ,
            @ApiImplicitParam(name = "emplName", value = "姓名", paramType = "query",dataType="String") ,
            @ApiImplicitParam(name = "deptId", value = "部门编码", paramType = "query",dataType="String"),
            @ApiImplicitParam(name = "secCode", value = "职序编码", paramType = "query",dataType="String"),
            @ApiImplicitParam(name = "gradeCode", value = "职级编码", paramType = "query",dataType="String")
    })
    public PageView<LearnMapStaffVO> studentMapPageList(@RequestParam @ApiIgnore Map<String, Object> params) {
        params.put("deptList",userHelper.getLoginDeptList());
        return trainLearnMapStaffService.studentMapPageList(params);
    }

    @ResponseResult
    @PostMapping("studentMapUpdate")
    @ApiOperation(value = "学员地图更新")
    public Long studentMapUpdate(@RequestBody @Validated(LearnMapStaffDTO.Insert.class) LearnMapStaffDTO learnMapStaffDTO) {
        return trainLearnMapStaffService.studentMapUpdate(learnMapStaffDTO, userHelper.getLoginEmplId());
    }

    @ResponseResult
    @GetMapping("studentMapInfo")
    @ApiOperation(value = "学员学习地图详情(含课程列表)")
    @ApiImplicitParam(name = "emplId", value = "员工工号", paramType = "path", required = true, dataType="String")
    public LearnMapStaffInfoVO studentMapInfo(@RequestParam String emplId) {
        return trainLearnMapStaffService.studentMapInfo(emplId);
    }

    @GetMapping("exportTml")
    @ApiOperation(value = "下载学习地图课程导入模板")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "mapId", value = "地图ID", paramType = "query", dataType="Long", required = true)
    })
    public void exportTml(HttpServletResponse response, @RequestParam Long mapId) throws IOException {
        // 获取对应地图课程列表
        List<MapCourseExcel> excelList = new ArrayList<>();
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
        String fileName = URLEncoder.encode("学习地图课程导入模板", "UTF-8").replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ExcelTypeEnum.XLSX.getValue());
        // 列下拉框数据
        Map<Integer, List<String>> selectMap = trainLearnMapService.excelSelectMap(mapId);

        // 绕过了创建临时文件，直接将数据读到流中传递至客户端
        EasyExcelFactory.write(response.getOutputStream(), MapCourseExcel.class)
                .registerWriteHandler(new SelectSheetWriteHandler(selectMap))
                .excelType(ExcelTypeEnum.XLSX)
                .sheet("学习地图课程")
                .doWrite(excelList);
    }

    @PostMapping(value = "importCourse", headers = "content-type=multipart/form-data")
    @ApiOperation(value = "导入学习地图课程")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "mapId", value = "地图ID", paramType = "query", dataType="long", required = true),
            @ApiImplicitParam(name = "file", value = "学习地图课程excel", dataTypeClass = MultipartFile.class, required = true, allowMultiple = true)
    })
    public Result<List<MapCourseExcel>> importCourse(@RequestBody MultipartFile file, Long mapId) throws IOException {
        if (mapId == null) {
            return new Result<List<MapCourseExcel>>().error("缺少参数地图ID");
        }
        if (file == null) {
            return new Result<List<MapCourseExcel>>().error("请上传要导入的文件");
        }
        //定义最终入库的数据集合
        EasyExcelListener<MapCourseExcel> listener = new EasyExcelListener<>();
        List<MapCourseExcel> excelList =
                EasyExcelFactory.read(file.getInputStream(), MapCourseExcel.class, listener)
                        .sheet(0).doReadSync();
        if (excelList.isEmpty()) {
            return new Result<List<MapCourseExcel>>().error("导入学习地图课程数据不能为空");
        }
        List<MapCourseExcel> errorList = trainLearnMapCourseService.importCourse(excelList, mapId, userHelper.getLoginEmplId());
        if (errorList.isEmpty()) {
            return new Result<List<MapCourseExcel>>().success("导入成功");
        }
        return new Result<List<MapCourseExcel>>().error("学习地图课程数据校验不通过", errorList);
    }

    @GetMapping("exportStuMapTml")
    @ApiOperation(value = "下载学员地图导入模板")
    public void exportStuMapTml(HttpServletResponse response) throws IOException {
        // 获取对应地图课程列表
        List<MapStuExcel> excelList = new ArrayList<>();
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
        String fileName = URLEncoder.encode("学员地图导入模板", "UTF-8").replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ExcelTypeEnum.XLSX.getValue());

        // 绕过了创建临时文件，直接将数据读到流中传递至客户端
        EasyExcelFactory.write(response.getOutputStream(), MapStuExcel.class)
                .excelType(ExcelTypeEnum.XLSX)
                .sheet("学员地图")
                .doWrite(excelList);
    }

    @PostMapping(value = "importStuMap", headers = "content-type=multipart/form-data")
    @ApiOperation(value = "导入学员地图")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "file", value = "学员地图excel", dataTypeClass = MultipartFile.class, required = true, allowMultiple = true)
    })
    public Result<List<MapStuExcel>> importStuMap(@RequestBody MultipartFile file) throws IOException {
        if (file == null) {
            return new Result<List<MapStuExcel>>().error("请上传要导入的文件");
        }
        //定义最终入库的数据集合
        EasyExcelListener<MapStuExcel> listener = new EasyExcelListener<>();
        List<MapStuExcel> excelList =
                EasyExcelFactory.read(file.getInputStream(), MapStuExcel.class, listener)
                        .sheet(0).doReadSync();
        if (excelList.isEmpty()) {
            return new Result<List<MapStuExcel>>().error("导入学员地图数据不能为空");
        }
        List<MapStuExcel> errorList = trainLearnMapStaffService.batchImportStuMap(excelList, userHelper.getLoginEmplId());
        if (errorList.isEmpty()) {
            return new Result<List<MapStuExcel>>().success("导入成功");
        }
        return new Result<List<MapStuExcel>>().error("学员地图数据校验不通过", errorList);
    }
}
