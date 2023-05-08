package com.dyys.hr.service.train.impl;

import cn.hutool.core.convert.Convert;
import com.dagongma.kernel.core.entity.PageView;
import com.dagongma.mybatis.core.service.impl.AbstractCrudService;
import com.dyys.hr.dao.train.TrainLearnMapStaffMapper;
import com.dyys.hr.dto.train.LearnMapStaffDTO;
import com.dyys.hr.dto.train.LearnMapStaffSblDTO;
import com.dyys.hr.entity.staff.StaffJob;
import com.dyys.hr.entity.staff.StaffPosnGrade;
import com.dyys.hr.entity.train.*;
import com.dyys.hr.entity.train.excel.MapStuExcel;
import com.dyys.hr.service.staff.IStaffJobService;
import com.dyys.hr.service.staff.IStaffPosnGradeService;
import com.dyys.hr.service.train.*;
import com.dyys.hr.vo.train.LearnMapStaffInfoVO;
import com.dyys.hr.vo.train.LearnMapStaffVO;
import com.dyys.hr.vo.train.LearnMapStuCourseInfoVO;
import com.github.pagehelper.page.PageMethod;
import com.google.common.base.Joiner;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 学员地图 服务实现类
 * </p>
 *
 * @author yangye
 * @since 2022-09-06
 */
@Service
public class TrainLearnMapStaffServiceImpl extends AbstractCrudService<TrainLearnMapStaff, Long> implements TrainLearnMapStaffService {
    @Autowired
    private TrainLearnMapStaffMapper trainLearnMapStaffMapper;
    @Autowired
    private TrainLearnMapStaffSblService trainLearnMapStaffSblService;
    @Autowired
    private TrainLearnMapCourseService trainLearnMapCourseService;
    @Autowired
    private TrainLearnMapPosnGradeService trainLearnMapPosnGradeService;
    @Autowired
    private IStaffJobService staffJobService;
    @Autowired
    private TrainLearnMapService trainLearnMapService;
    @Autowired
    private TrainConstantService trainConstantService;
    @Autowired
    private IStaffPosnGradeService iStaffPosnGradeService;

    /**
     * 学员地图分页列表
     * @param params
     * @return
     */
    @Override
    public PageView<LearnMapStaffVO> studentMapPageList(Map<String, Object> params) {
        PageMethod.startPage(Convert.toInt(params.get("page")), Convert.toInt(params.get("limit")));
        List<LearnMapStaffVO> mapVOList = trainLearnMapStaffMapper.studentMapList(params);
        for (LearnMapStaffVO vo : mapVOList) {
            List<String> sblNameList = trainLearnMapStaffSblService.querySblNameByEmplId(vo.getEmplId()); // 学习方向名称
            vo.setSblName(!sblNameList.isEmpty() ? Joiner.on(",").join(sblNameList) : "");
        }
        return PageView.build(mapVOList);
    }

    /**
     * 学员地图更新
     * @param learnMapStaffDTO
     * @param loginEmplId
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long studentMapUpdate(LearnMapStaffDTO learnMapStaffDTO, String loginEmplId) {
        Long stamp = System.currentTimeMillis()/1000; // 系统当前时间戳
        TrainLearnMapStaff mapStaff = new TrainLearnMapStaff();
        mapStaff.setEmplId(learnMapStaffDTO.getEmplId());
        TrainLearnMapStaff mapStaff1 = selectOne(mapStaff);
        Long mapStaffId;
        if (null == mapStaff1) { // 新增
            BeanUtils.copyProperties(learnMapStaffDTO, mapStaff);
            mapStaff.setSourceType(0);
            mapStaff.setCreateTime(stamp);
            mapStaff.setCreateUser(loginEmplId);
            mapStaff.setUpdateUser(loginEmplId);
            mapStaff.setUpdateTime(stamp);
            mapStaffId = insertSelective(mapStaff);
            if (null != mapStaffId && mapStaffId > 0L) {
                List<TrainLearnMapStaffSbl> entityList = this.assembleMapStaffData(learnMapStaffDTO.getStaffSblList(), mapStaffId, learnMapStaffDTO.getEmplId(), loginEmplId);
                trainLearnMapStaffSblService.insertBatchSelective(entityList);
            }
        } else { // 更新
            BeanUtils.copyProperties(learnMapStaffDTO, mapStaff1);
            mapStaff1.setUpdateUser(loginEmplId);
            mapStaff1.setUpdateTime(stamp);
            int res = updateSelective(mapStaff1);
            if (res > 0) {
                boolean delRes = trainLearnMapStaffSblService.deleteByEmplId(learnMapStaffDTO.getEmplId());
                if (delRes) {
                    List<TrainLearnMapStaffSbl> entityList = this.assembleMapStaffData(learnMapStaffDTO.getStaffSblList(), mapStaff1.getId(), learnMapStaffDTO.getEmplId(), loginEmplId);
                    trainLearnMapStaffSblService.insertBatchSelective(entityList);
                }
            }
            mapStaffId = mapStaff1.getId();
        }
        return mapStaffId;
    }

    /**
     * 学员学习地图详情(含课程列表)
     * @param emplId
     * @return
     */
    @Override
    public LearnMapStaffInfoVO studentMapInfo(String emplId) {
        LearnMapStaffInfoVO infoVO = trainLearnMapStaffMapper.queryInfoByEmplId(emplId);
        TrainLearnMapStaff mapStaff = new TrainLearnMapStaff();
        mapStaff.setEmplId(emplId);
        TrainLearnMapStaff staffInfo = selectOne(mapStaff); // 学员地图信息
        List<Integer> sblIdList = trainLearnMapStaffSblService.querySblIdByEmplId(emplId); // 学员学习方向ID集
        String posnGradeCode = null;
        String highPosnGradeCode = null;
        String lowPosnGradeCode = null;
        Long mapId = null;
        if (null != staffInfo) {
            mapId = staffInfo.getMapId(); // 地图ID
            posnGradeCode = staffInfo.getPosnGradeCode(); // 当前地图职级编码
            // 根据学员当前地图ID和职级ID获取高一级职级
            highPosnGradeCode = trainLearnMapPosnGradeService.getHighOrLowGradeCode(staffInfo.getMapId(), posnGradeCode, "High");
            // 根据学员当前地图ID和职级ID获取低一级职级
            lowPosnGradeCode = trainLearnMapPosnGradeService.getHighOrLowGradeCode(staffInfo.getMapId(), posnGradeCode, "Low");
        }
        // 当前职级推荐课程信息
        LearnMapStuCourseInfoVO stuCourseVO = trainLearnMapCourseService.studentInfoCourseList(mapId, posnGradeCode, sblIdList, emplId);
        infoVO.setMapGradeCodeName(stuCourseVO.getGradeCodeName()); // 当前职级编码名称
        infoVO.setFinishRatio(stuCourseVO.getFinishRatio()); // 当前职级课程完成比例
        infoVO.setFinishRate(stuCourseVO.getFinishRate()); // 当前职级课程完成率
        infoVO.setCurrentList(stuCourseVO.getCourseList());
        // 高一级职级推荐课程信息
        LearnMapStuCourseInfoVO highStuCourseVO = trainLearnMapCourseService.studentInfoCourseList(mapId, highPosnGradeCode, sblIdList, emplId);
        infoVO.setHighMapGradeCodeName(highStuCourseVO.getGradeCodeName()); // 高一级职级编码名称
        infoVO.setHighFinishRatio(highStuCourseVO.getFinishRatio()); // 高一级职级课程完成比例
        infoVO.setHighFinishRate(highStuCourseVO.getFinishRate()); // 高一级职级课程完成率
        infoVO.setHighList(highStuCourseVO.getCourseList());
        // 低一级职级推荐课程信息
        LearnMapStuCourseInfoVO lowStuCourseVO = trainLearnMapCourseService.studentInfoCourseList(mapId, lowPosnGradeCode, sblIdList, emplId);
        infoVO.setLowMapGradeCodeName(lowStuCourseVO.getGradeCodeName()); // 低一级职级编码名称
        infoVO.setLowFinishRatio(lowStuCourseVO.getFinishRatio()); // 低一级职级课程完成比例
        infoVO.setLowList(lowStuCourseVO.getCourseList());
        return infoVO;
    }

    /**
     * 批量导入学员地图数据
     * @param excelList
     * @param loginEmplId
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<MapStuExcel> batchImportStuMap(List<MapStuExcel> excelList, String loginEmplId) {
        List<MapStuExcel> errorList = new ArrayList<>();
        Map<String, String> checkMap = new HashMap<>();
        Map<String, Integer> checkDataMap = new HashMap<>();
        Map<String, Integer> checkUniqueMap = new HashMap<>();
        Map<String, LearnMapStaffDTO> staffMap = new HashMap<>(); // 学员地图
        Map<String, List<LearnMapStaffSblDTO>> sblMap = new HashMap<>(); // 学习方向
        int i = 0;
        int errNum = 0;
        for (MapStuExcel excel : excelList) {
            i++;
            MapStuExcel errVO = new MapStuExcel();
            // 判断学员工号是否为空
            if (excel.getEmplId() == null || excel.getEmplId().trim().equals("")) {
                BeanUtils.copyProperties(excel, errVO);
                errVO.setErrMsg("第" + i + "条记录，学员工号为空");
                errorList.add(errVO);
                errNum++;
                continue;
            }
            // 判断学员工号是否正确
            StaffJob emplInfo = staffJobService.getInfoByEmplId(excel.getEmplId());
            if (emplInfo == null) {
                BeanUtils.copyProperties(excel, errVO);
                errVO.setErrMsg("第" + i + "条记录，学员工号不正确");
                errorList.add(errVO);
                errNum++;
                continue;
            }
            // 判断学员姓名是否为空
            if (excel.getEmplName() == null || excel.getEmplName().trim().equals("")) {
                BeanUtils.copyProperties(excel, errVO);
                errVO.setErrMsg("第" + i + "条记录，学员姓名为空");
                errorList.add(errVO);
                errNum++;
                continue;
            }
            // 判断学员工号与姓名是否一致
            if (!emplInfo.getEmplName().equals(excel.getEmplName())) {
                BeanUtils.copyProperties(excel, errVO);
                errVO.setErrMsg("第" + i + "条记录，学员工号与姓名不一致");
                errorList.add(errVO);
                errNum++;
                continue;
            }
            // 判断学习地图编码是否为空
            if (excel.getMapCode() == null || excel.getMapCode().trim().equals("")) {
                BeanUtils.copyProperties(excel, errVO);
                errVO.setErrMsg("第" + i + "条记录，学习地图编码为空");
                errorList.add(errVO);
                errNum++;
                continue;
            }
            //判断学习地图编码是否正确
            TrainLearnMap mapInfo = trainLearnMapService.mapInfoByCode(excel.getMapCode());
            if (mapInfo == null) {
                BeanUtils.copyProperties(excel, errVO);
                errVO.setErrMsg("第" + i + "条记录，学习地图编码不正确");
                errorList.add(errVO);
                errNum++;
                continue;
            }
            // 判断学习地图名称是否为空
            if (excel.getMapName() == null || excel.getMapName().trim().equals("")) {
                BeanUtils.copyProperties(excel, errVO);
                errVO.setErrMsg("第" + i + "条记录，学习地图名称为空");
                errorList.add(errVO);
                errNum++;
                continue;
            }
            // 判断学习地图编码与名称是否一致
            if (!mapInfo.getMapName().equals(excel.getMapName())) {
                BeanUtils.copyProperties(excel, errVO);
                errVO.setErrMsg("第" + i + "条记录，学习地图编码与名称不一致");
                errorList.add(errVO);
                errNum++;
                continue;
            }
            // 判断学习职级编码是否为空
            if (excel.getPosnGradeCode() == null || excel.getPosnGradeCode().trim().equals("")) {
                BeanUtils.copyProperties(excel, errVO);
                errVO.setErrMsg("第" + i + "条记录，学习职级编码为空");
                errorList.add(errVO);
                errNum++;
                continue;
            }
            // 判断学习职级编码是否正确
            TrainLearnMapPosnGrade mapPosnGradeInfo = trainLearnMapPosnGradeService.getInfoByMap(mapInfo.getId(), excel.getPosnGradeCode());
            if (mapPosnGradeInfo == null) {
                BeanUtils.copyProperties(excel, errVO);
                errVO.setErrMsg("第" + i + "条记录，学习地图的学习职级编码不正确");
                errorList.add(errVO);
                errNum++;
                continue;
            }
            // 判断学习职级名称是否为空
            if (excel.getPosnGradeName() == null || excel.getPosnGradeName().trim().equals("")) {
                BeanUtils.copyProperties(excel, errVO);
                errVO.setErrMsg("第" + i + "条记录，学习职级名称为空");
                errorList.add(errVO);
                errNum++;
                continue;
            }
            // 判断学习地图编码与名称是否一致
            StaffPosnGrade gradeInfo = iStaffPosnGradeService.selectById(excel.getPosnGradeCode());
            if (!gradeInfo.getDescrShort().equals(excel.getPosnGradeName())) {
                BeanUtils.copyProperties(excel, errVO);
                errVO.setErrMsg("第" + i + "条记录，学习职级编码与名称不一致");
                errorList.add(errVO);
                errNum++;
                continue;
            }
            // 判断学习方向ID是否为空
            if (excel.getSblId() == null) {
                BeanUtils.copyProperties(excel, errVO);
                errVO.setErrMsg("第" + i + "条记录，学习方向ID为空");
                errorList.add(errVO);
                errNum++;
                continue;
            }
            // 判断学习方向ID是否正确
            TrainConstant constantInfo = trainConstantService.getInfoBySblId(excel.getSblId(), 7, mapInfo.getPosnSecCode());
            if (constantInfo == null) {
                BeanUtils.copyProperties(excel, errVO);
                errVO.setErrMsg("第" + i + "条记录，学习方向ID不正确");
                errorList.add(errVO);
                errNum++;
                continue;
            }
            // 判断学习方向名称是否为空
            if (excel.getSblName() == null || excel.getSblName().trim().equals("")) {
                BeanUtils.copyProperties(excel, errVO);
                errVO.setErrMsg("第" + i + "条记录，学习方向名称为空");
                errorList.add(errVO);
                errNum++;
                continue;
            }
            // 判断学习方向ID与名称是否一致
            if (!constantInfo.getName().equals(excel.getSblName())) {
                BeanUtils.copyProperties(excel, errVO);
                errVO.setErrMsg("第" + i + "条记录，学习方向ID与名称不一致");
                errorList.add(errVO);
                errNum++;
                continue;
            }
            // 判断同一个员工只能选择一个地图一个学习职级
            String mapKey = excel.getEmplId();
            String mapVal = excel.getEmplId() + excel.getMapCode() + excel.getPosnGradeCode();
            if (checkMap.containsKey(mapKey) && !checkMap.get(mapKey).equals(mapVal)) {
                BeanUtils.copyProperties(excel, errVO);
                errVO.setErrMsg("第" + i + "条记录与第" + checkDataMap.get(mapKey) + "条记录，同一员工的学习地图学习职级信息不一致");
                errorList.add(errVO);
                errNum++;
                continue;
            }
            checkMap.put(mapKey, mapVal);
            checkDataMap.put(mapKey, i);
            // 判断是否有重复数据
            String mapUniqueKey = excel.getEmplId() + "-" + excel.getMapCode() + "-" + excel.getPosnGradeCode()
                    + excel.getSblId();
            if (checkUniqueMap.containsKey(mapUniqueKey)) {
                BeanUtils.copyProperties(excel, errVO);
                errVO.setErrMsg("第" + i + "条记录与第" + checkUniqueMap.get(mapUniqueKey) + "条记录，数据重复");
                errorList.add(errVO);
                errNum++;
                continue;
            }
            checkUniqueMap.put(mapUniqueKey, i);

            // 处理学员地图数据
            if (errNum == 0) {
                LearnMapStaffDTO staffDTO = new LearnMapStaffDTO();
                LearnMapStaffSblDTO staffSblDTO = new LearnMapStaffSblDTO();
                List<LearnMapStaffSblDTO> sblList;
                if (staffMap.containsKey(mapKey)) {
                    sblList = sblMap.get(mapKey);
                } else {
                    BeanUtils.copyProperties(excel, staffDTO);
                    staffDTO.setMapId(mapInfo.getId());
                    staffDTO.setPosnSecCode(mapInfo.getPosnSecCode());
                    staffMap.put(mapKey, staffDTO);
                    sblList = new ArrayList<>(); // 初始化学习方向数据
                }
                staffSblDTO.setSblId(constantInfo.getId());
                sblList.add(staffSblDTO);
                sblMap.put(mapKey, sblList);
            }
        }
        // 没有校验出错误信息
        if (errNum == 0) {
            for (Map.Entry<String, LearnMapStaffDTO> entry : staffMap.entrySet()) {
                String key = entry.getKey();
                LearnMapStaffDTO mapStaffDTO = entry.getValue();
                List<LearnMapStaffSblDTO> staffSblList = sblMap.get(key);
                mapStaffDTO.setStaffSblList(staffSblList);
                this.studentMapUpdate(mapStaffDTO, loginEmplId);
            }
        }
        return errorList;
    }

    /**
     * 组装员工学习方向数据
     * @param staffSblList
     * @param mapStaffId
     * @param emplId
     * @param loginEmplId
     * @return
     */
    private List<TrainLearnMapStaffSbl> assembleMapStaffData(List<LearnMapStaffSblDTO> staffSblList, Long mapStaffId, String emplId, String loginEmplId) {
        Long stamp = System.currentTimeMillis()/1000; // 系统当前时间戳
        List<TrainLearnMapStaffSbl> staffSbls = new ArrayList<>();
        for (LearnMapStaffSblDTO dto : staffSblList) {
            TrainLearnMapStaffSbl staffSbl = new TrainLearnMapStaffSbl();
            BeanUtils.copyProperties(dto, staffSbl);
            staffSbl.setMapStaffId(mapStaffId);
            staffSbl.setEmplId(emplId);
            staffSbl.setCreateUser(loginEmplId);
            staffSbl.setCreateTime(stamp);
            staffSbls.add(staffSbl);
        }
        return staffSbls;
    }
}
