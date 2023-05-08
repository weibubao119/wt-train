package com.dyys.hr.service.train.impl;

import cn.hutool.core.convert.Convert;
import com.dagongma.mybatis.core.service.impl.AbstractCrudService;
import com.dyys.hr.dao.train.TrainLearnMapCourseMapper;
import com.dyys.hr.dto.train.LearnMapCourseDTO;
import com.dyys.hr.dto.train.LearnMapCourseTantaDTO;
import com.dyys.hr.entity.staff.StaffPosnGrade;
import com.dyys.hr.entity.train.*;
import com.dyys.hr.entity.train.excel.MapCourseExcel;
import com.dyys.hr.service.staff.IStaffPosnGradeService;
import com.dyys.hr.service.train.*;
import com.dyys.hr.vo.train.*;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 学习地图-课程 服务实现类
 * </p>
 *
 * @author yangye
 * @since 2022-09-06
 */
@Service
public class TrainLearnMapCourseServiceImpl extends AbstractCrudService<TrainLearnMapCourse, Long> implements TrainLearnMapCourseService {
    @Autowired
    private TrainLearnMapCourseMapper trainLearnMapCourseMapper;
    @Autowired
    private TrainLearnMapCourseTantaService trainLearnMapCourseTantaService;
    @Autowired
    private TrainLearnMapService trainLearnMapService;
    @Autowired
    private IStaffPosnGradeService iStaffPosnGradeService;
    @Autowired
    private TrainDataService trainDataService;
    @Autowired
    private TrainConstantService trainConstantService;
    @Autowired
    private TrainBaseCourseService trainBaseCourseService;
    @Autowired
    private TrainLearnMapPosnGradeService trainLearnMapPosnGradeService;
    @Autowired
    private TrainLearnMapStaffService trainLearnMapStaffService;
    @Autowired
    private TrainLearnMapStaffSblService trainLearnMapStaffSblService;

    /**
     * 学习地图详情-职级课程列表
     * @param params
     * @return
     */
    @Override
    public List<LearnMapCourseVO> mapInfoCourseList(Map<String, Object> params) {
        List<LearnMapCourseVO> courseList = trainLearnMapCourseMapper.queryList(params);
        if (null != courseList) {
            for (LearnMapCourseVO courseVO : courseList) {
                // 同等课程列表
                courseVO.setCourseTantaList(trainLearnMapCourseTantaService.queryListByMapCourseId(courseVO.getId()));
            }
        }
        return courseList;
    }

    /**
     * 校验学习地图课程唯一性数据
     * @param learnMapCourseDTO
     * @param checkType
     * @return
     */
    @Override
    public String checkUniqueData(LearnMapCourseDTO learnMapCourseDTO, String checkType) {
        Long courseId = null;
        if (checkType.equals("update")) {
            courseId = learnMapCourseDTO.getId();
        }
        Map<String, Object> map = new HashMap<>();
        map.put("courseId", courseId); // 当前课程记录ID
        map.put("mapId", learnMapCourseDTO.getMapId()); // 地图ID
        map.put("posnGradeCode", learnMapCourseDTO.getPosnGradeCode()); // 职级ID
        map.put("sblId", learnMapCourseDTO.getSblId()); // 方向ID
        map.put("courseNumber", learnMapCourseDTO.getCourseNumber()); // 课程编号
        int mapCourseCount = trainLearnMapCourseMapper.countByCourseNumber(map);
        if (mapCourseCount > 0) {
            return "当前地图当前职级当前方向已存在此课程，请更换";
        }
        return "";
    }

    /**
     * 学习地图课程添加
     * @param learnMapCourseDTO
     * @param loginEmplId
     * @param sourceType
     * @return
     */
    @Override
    public Long mapCourseAdd(LearnMapCourseDTO learnMapCourseDTO, String loginEmplId, Integer sourceType) {
        Long stamp = System.currentTimeMillis()/1000; // 系统当前时间戳
        List<LearnMapCourseTantaDTO> courseTantaList = learnMapCourseDTO.getCourseTantaList(); // 同等课程列表
        TrainLearnMap mapInfo = trainLearnMapService.selectById(learnMapCourseDTO.getMapId()); // 地图信息

        TrainLearnMapCourse trainLearnMapCourse = new TrainLearnMapCourse();
        BeanUtils.copyProperties(learnMapCourseDTO, trainLearnMapCourse);
        trainLearnMapCourse.setMapCode(mapInfo.getMapCode());
        trainLearnMapCourse.setSourceType(sourceType);
        trainLearnMapCourse.setCreateUser(loginEmplId);
        trainLearnMapCourse.setUpdateUser(loginEmplId);
        trainLearnMapCourse.setCreateTime(stamp);
        trainLearnMapCourse.setUpdateTime(stamp);
        Long courseId = insertSelective(trainLearnMapCourse);
        if (null != courseId && courseId > 0L && !courseTantaList.isEmpty()) {
            List<TrainLearnMapCourseTanta> entityList = this.assembleTantaCourseData(courseTantaList, courseId, learnMapCourseDTO.getCourseNumber(), loginEmplId);
            trainLearnMapCourseTantaService.insertBatchSelective(entityList);
        }
        return courseId;
    }

    /**
     * 学习地图更新
     * @param learnMapCourseDTO
     * @param loginEmplId
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer mapCourseUpdate(LearnMapCourseDTO learnMapCourseDTO, String loginEmplId) {
        List<LearnMapCourseTantaDTO> courseTantaList = learnMapCourseDTO.getCourseTantaList(); // 同等课程列表
        TrainLearnMapCourse trainLearnMapCourse = new TrainLearnMapCourse();
        BeanUtils.copyProperties(learnMapCourseDTO,trainLearnMapCourse);
        trainLearnMapCourse.setUpdateUser(loginEmplId);
        trainLearnMapCourse.setUpdateTime(System.currentTimeMillis()/1000);
        Integer res = updateSelective(trainLearnMapCourse);
        if (res > 0) {
            boolean delRes = trainLearnMapCourseTantaService.deleteByMapCourseId(learnMapCourseDTO.getId());
            if (delRes && !courseTantaList.isEmpty()) {
                List<TrainLearnMapCourseTanta> entityList = this.assembleTantaCourseData(courseTantaList, learnMapCourseDTO.getId(), learnMapCourseDTO.getCourseNumber(), loginEmplId);
                trainLearnMapCourseTantaService.insertBatchSelective(entityList);
            }
        }
        return res;
    }

    /**
     * 学习地图课程删除
     * @param id
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer mapCourseDelById(Long id) {
        boolean delRes = trainLearnMapCourseTantaService.deleteByMapCourseId(id);
        if (delRes) {
            return deleteById(id);
        }
        return 0;
    }

    /**
     * 获取员工推荐课程
     * @param params
     * @param loginEmplId
     * @return
     */
    @Override
    public PageInfo<EmployeeRecommendCourseListVO> recommendCourseList(Map<String, Object> params, String loginEmplId) {
        TrainLearnMapStaff mapStaff = new TrainLearnMapStaff();
        mapStaff.setEmplId(loginEmplId);
        TrainLearnMapStaff staffMapInfo = trainLearnMapStaffService.selectOne(mapStaff); // 学员地图信息
        List<Integer> sblIdList = trainLearnMapStaffSblService.querySblIdByEmplId(loginEmplId); // 学员学习方向ID集
        List<EmployeeRecommendCourseListVO> voList;
        int page = Convert.toInt(params.get("page"));
        int limit = Convert.toInt(params.get("limit"));
        PageMethod.startPage(page, limit);
        if (staffMapInfo != null) {
            params.put("mapId", staffMapInfo.getMapId()); // 学习地图ID
            params.put("posnGradeCode", staffMapInfo.getPosnGradeCode()); // 当前学习职级编码
            params.put("sblIdList", sblIdList);
            voList = trainLearnMapCourseMapper.recommendCourseList(params);
        } else {
            voList = new ArrayList<>();
        }
        return new PageInfo<>(voList);
    }

    /**
     * 学员推荐课程
     * @param mapId
     * @param posnGradeCode
     * @param sblIdList
     * @param emplId
     * @return
     */
    @Override
    public LearnMapStuCourseInfoVO studentInfoCourseList(Long mapId, String posnGradeCode, List<Integer> sblIdList, String emplId) {
        LearnMapStuCourseInfoVO stuCourseVO = new LearnMapStuCourseInfoVO();
        String gradeCodeName = "";
        List<LearnMapStuCourseVO> courseList = null;
        String finishRate = null;
        String finishRatio = null;
        if (null != posnGradeCode && !posnGradeCode.equals("")) {
            StaffPosnGrade posnGrade = new StaffPosnGrade();
            posnGrade.setId(posnGradeCode);
            StaffPosnGrade posnGradeInfo = iStaffPosnGradeService.selectOne(posnGrade);
            gradeCodeName = posnGradeCode + posnGradeInfo.getDescrShort();
            courseList = trainLearnMapCourseMapper.studentCourseList(mapId, posnGradeCode, sblIdList); // 学员推荐课程列表
            if (null != courseList && courseList.size() > 0) {
                int totalFinishNum = 0; // 学习课程数量
                for (LearnMapStuCourseVO courseVO : courseList) {
                    // 同等课程列表
                    List<LearnMapCourseTantaVO> courseTantaList = trainLearnMapCourseTantaService.queryListByMapCourseId(courseVO.getId());
                    courseVO.setCourseTantaList(courseTantaList);
                    // 课程ID集
                    List<String> courseNums = courseTantaList.stream().map(LearnMapCourseTantaVO::getTantaCourseNumber).collect(Collectors.toList());
                    courseNums.add(courseVO.getCourseNumber());
                    // 已完成学习课程数量
                    int courseFinishNum = trainDataService.countFinishCourse(courseNums, emplId);
                    if (courseFinishNum > 0) {
                        courseVO.setFinishName("已完成");
                        totalFinishNum++;
                    } else {
                        courseVO.setFinishName("待学习");
                    }
                }
                finishRate = totalFinishNum * 10000 / courseList.size() / 100.0 + "%"; // 学习完成率
                finishRatio = totalFinishNum + "/" + courseList.size(); // 课程完成比例
            }
        }
        stuCourseVO.setGradeCodeName(gradeCodeName);
        stuCourseVO.setCourseList(courseList);
        stuCourseVO.setFinishRatio(finishRatio);
        stuCourseVO.setFinishRate(finishRate);
        return stuCourseVO;
    }

    /**
     * 批量导入课程
     * @param excelList
     * @param mapId
     * @param loginEmplId
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<MapCourseExcel> importCourse(List<MapCourseExcel> excelList, Long mapId, String loginEmplId) {
        List<MapCourseExcel> errorList = new ArrayList<>();
        Map<String, Integer> checkMap = new HashMap<>();
        Map<String, LearnMapCourseDTO> courseMap = new HashMap<>(); // 课程map
        Map<String, List<LearnMapCourseTantaDTO>> tantaMap = new HashMap<>(); // 同等课程map
        TrainLearnMap mapInfo = trainLearnMapService.selectById(mapId); // 地图详情
        List<String> gradeList = trainLearnMapPosnGradeService.selectBoxList(mapId);
        List<String> sblList = trainConstantService.selectBoxList(7, mapInfo.getPosnSecCode());
        // 导入的数据，同一个方向同一个课程编号的数据算一个课程
        int i = 0;
        int errNum = 0;
        for (MapCourseExcel excel : excelList) {
            i++;
            MapCourseExcel errVO = new MapCourseExcel();
            // 判断地图职级是否为空
            if (excel.getGradeCodeName() == null || excel.getGradeCodeName().trim().equals("")) {
                BeanUtils.copyProperties(excel, errVO);
                errVO.setErrMsg("第" + i + "条记录，地图职级为空");
                errorList.add(errVO);
                errNum++;
                continue;
            }
            // 判断地图职级是否正确
            if (!gradeList.contains(excel.getGradeCodeName())) {
                BeanUtils.copyProperties(excel, errVO);
                errVO.setErrMsg("第" + i + "条记录，地图职级不正确");
                errorList.add(errVO);
                errNum++;
                continue;
            }
            // 判断学习方向是否为空
            if (excel.getSblIdName() == null || excel.getSblIdName().trim().equals("")) {
                BeanUtils.copyProperties(excel, errVO);
                errVO.setErrMsg("第" + i + "条记录，学习方向为空");
                errorList.add(errVO);
                errNum++;
                continue;
            }
            // 判断学习方向是否正确
            if (!sblList.contains(excel.getSblIdName())) {
                BeanUtils.copyProperties(excel, errVO);
                errVO.setErrMsg("第" + i + "条记录，学习方向不正确");
                errorList.add(errVO);
                errNum++;
                continue;
            }
            // 判断课程模块名称是否为空
            if (excel.getModuleName() == null || excel.getModuleName().trim().equals("")) {
                BeanUtils.copyProperties(excel, errVO);
                errVO.setErrMsg("第" + i + "条记录，课程模块名称为空");
                errorList.add(errVO);
                errNum++;
                continue;
            }
            // 判断课程编码是否为空
            if (excel.getCourseNumber() == null || excel.getCourseNumber().trim().equals("")) {
                BeanUtils.copyProperties(excel, errVO);
                errVO.setErrMsg("第" + i + "条记录，课程编号为空");
                errorList.add(errVO);
                errNum++;
                continue;
            }
            // 判断课程编码是否正确
            TrainBaseCourse courseInfo = trainBaseCourseService.getInfoByNumber(excel.getCourseNumber());
            if (courseInfo == null) {
                BeanUtils.copyProperties(excel, errVO);
                errVO.setErrMsg("第" + i + "条记录，课程编号不正确");
                errorList.add(errVO);
                errNum++;
                continue;
            }
            // 判断课程名称是否为空
            if (excel.getCourseName() == null || excel.getCourseName().trim().equals("")) {
                BeanUtils.copyProperties(excel, errVO);
                errVO.setErrMsg("第" + i + "条记录，课程名称为空");
                errorList.add(errVO);
                errNum++;
                continue;
            }
            // 判断课程编码和名称是否一致
            if (!courseInfo.getName().equals(excel.getCourseName())) {
                BeanUtils.copyProperties(excel, errVO);
                errVO.setErrMsg("第" + i + "条记录，课程编号与名称不一致");
                errorList.add(errVO);
                errNum++;
                continue;
            }
            // 判断必修标识是否为空
            if (excel.getRequiredFlag() == null) {
                BeanUtils.copyProperties(excel, errVO);
                errVO.setErrMsg("第" + i + "条记录，必修标识为空");
                errorList.add(errVO);
                errNum++;
                continue;
            }
            // 判断必修标识是否正确
            if (!excel.getRequiredFlag().equals(1) && !excel.getRequiredFlag().equals(0)) {
                BeanUtils.copyProperties(excel, errVO);
                errVO.setErrMsg("第" + i + "条记录，必修标识不正确");
                errorList.add(errVO);
                errNum++;
                continue;
            }
            String[] gradeCodeName = excel.getGradeCodeName().split("-"); // 分出所选职级编码和名称
            String gradeCode = gradeCodeName[0]; // 职级编码
            String[] sblIdName = excel.getSblIdName().split("-"); // 分出所选学习方向ID和名称
            Integer sblId = Integer.parseInt(sblIdName[0]); // 学习方向ID
            // 判断该职级该方向该课程是否已存在当前地图中
            TrainLearnMapCourse mapCourseInfo = this.getInfo(mapId, gradeCode, sblId, excel.getCourseNumber());
            if (mapCourseInfo != null) {
                BeanUtils.copyProperties(excel, errVO);
                errVO.setErrMsg("第" + i + "条记录，该职级该课程该方向已存在当前地图课程中");
                errorList.add(errVO);
                errNum++;
                continue;
            }
            // 判断同等课程数据
            if ((excel.getTantaCourseNumber() != null && !excel.getTantaCourseNumber().trim().equals(""))
                    || excel.getTantaCourseName() != null && !excel.getTantaCourseName().trim().equals("")) {
                if (excel.getTantaCourseNumber() == null || excel.getTantaCourseNumber().trim().equals("")) {
                    BeanUtils.copyProperties(excel, errVO);
                    errVO.setErrMsg("第" + i + "条记录，同等课程编号为空");
                    errorList.add(errVO);
                    errNum++;
                    continue;
                }
                TrainBaseCourse tantaCourseInfo = trainBaseCourseService.getInfoByNumber(excel.getTantaCourseNumber());
                if (tantaCourseInfo == null) {
                    BeanUtils.copyProperties(excel, errVO);
                    errVO.setErrMsg("第" + i + "条记录，同等课程编号不正确");
                    errorList.add(errVO);
                    errNum++;
                    continue;
                }
                if (excel.getTantaCourseName() == null || excel.getTantaCourseName().trim().equals("")) {
                    BeanUtils.copyProperties(excel, errVO);
                    errVO.setErrMsg("第" + i + "条记录，同等课程名称为空");
                    errorList.add(errVO);
                    errNum++;
                    continue;
                }
                // 判断同等课程编码与同等课程名称是否一致
                if (!tantaCourseInfo.getName().equals(excel.getTantaCourseName())) {
                    BeanUtils.copyProperties(excel, errVO);
                    errVO.setErrMsg("第" + i + "条记录，同等课程编号与名称不一致");
                    errorList.add(errVO);
                    errNum++;
                    continue;
                }
            }
            // 判断是否有重复数据
            String mapCheckKey = excel.getGradeCodeName() + "-" + excel.getSblIdName() + "-" + excel.getCourseNumber()
                    + "-" + excel.getTantaCourseNumber();
            if (checkMap.containsKey(mapCheckKey)) {
                BeanUtils.copyProperties(excel, errVO);
                errVO.setErrMsg("第" + i + "条记录与第" + checkMap.get(mapCheckKey) + "条记录数据重复");
                errorList.add(errVO);
                errNum++;
                continue;
            }
            checkMap.put(mapCheckKey, i);

            // 处理课程数据
            if (errNum == 0) {
                String mapCourseKey = excel.getGradeCodeName() + "-" + excel.getSblIdName() + "-" + excel.getCourseNumber(); // 课程键值
                LearnMapCourseDTO courseDTO = new LearnMapCourseDTO();
                LearnMapCourseTantaDTO tantaDTO = new LearnMapCourseTantaDTO();
                List<LearnMapCourseTantaDTO> tantaList;
                // 课程已存在
                if (courseMap.containsKey(mapCourseKey)) {
                    tantaList = tantaMap.get(mapCourseKey);
                } else {
                    courseDTO.setMapId(mapId);
                    courseDTO.setPosnGradeCode(gradeCode);
                    courseDTO.setSblId(sblId);
                    courseDTO.setModuleName(excel.getModuleName());
                    courseDTO.setCourseNumber(excel.getCourseNumber());
                    courseDTO.setRequiredFlag(excel.getRequiredFlag());
                    courseMap.put(mapCourseKey, courseDTO);
                    tantaList = new ArrayList<>(); // 初始化同等课程数据
                }
                if (excel.getTantaCourseNumber() != null && !excel.getTantaCourseNumber().trim().equals("")) {
                    tantaDTO.setTantaCourseNumber(excel.getTantaCourseNumber());
                    tantaList.add(tantaDTO);
                }
                tantaMap.put(mapCourseKey, tantaList);
            }
        }
        // 处理数据入库
        if (errNum == 0) {
            for (Map.Entry<String, LearnMapCourseDTO> entry : courseMap.entrySet()) {
                String key = entry.getKey();
                LearnMapCourseDTO mapCourseDTO = entry.getValue();
                List<LearnMapCourseTantaDTO> tantaDTOList = tantaMap.get(key);
                mapCourseDTO.setCourseTantaList(tantaDTOList);
                this.mapCourseAdd(mapCourseDTO, loginEmplId, 1);
            }
        }
        return errorList;
    }

    /**
     * 判断该方向该课程是否已存在当前地图职级中
     * @param mapId
     * @param posnGradeCode
     * @param sblId
     * @param courseNumber
     * @return
     */
    private TrainLearnMapCourse getInfo(Long mapId, String posnGradeCode, Integer sblId, String courseNumber) {
        TrainLearnMapCourse mapCourseQuery = new TrainLearnMapCourse();
        mapCourseQuery.setMapId(mapId);
        mapCourseQuery.setPosnGradeCode(posnGradeCode);
        mapCourseQuery.setSblId(sblId);
        mapCourseQuery.setCourseNumber(courseNumber);
        return selectOne(mapCourseQuery);
    }

    /**
     * 组装同等课程数据
     * @param courseTantaList
     * @param courseId
     * @param courseNumber
     * @param loginEmplId
     * @return
     */
    private List<TrainLearnMapCourseTanta> assembleTantaCourseData(List<LearnMapCourseTantaDTO> courseTantaList, Long courseId, String courseNumber, String loginEmplId) {
        Long stamp = System.currentTimeMillis()/1000; // 系统当前时间戳
        List<TrainLearnMapCourseTanta> gradeList = new ArrayList<>();
        for (LearnMapCourseTantaDTO dto : courseTantaList) {
            TrainLearnMapCourseTanta courseTanta = new TrainLearnMapCourseTanta();
            BeanUtils.copyProperties(dto, courseTanta);
            courseTanta.setMapCourseId(courseId);
            courseTanta.setMapCourseNumber(courseNumber);
            courseTanta.setCreateUser(loginEmplId);
            courseTanta.setCreateTime(stamp);
            gradeList.add(courseTanta);
        }
        return gradeList;
    }
}
