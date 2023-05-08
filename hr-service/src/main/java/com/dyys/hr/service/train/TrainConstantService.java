package com.dyys.hr.service.train;

import com.dagongma.mybatis.core.service.ICrudService;
import com.dyys.hr.dto.train.TrainConstantDTO;
import com.dyys.hr.entity.train.TrainConstant;
import com.dyys.hr.entity.train.excel.ConstantExcel;
import com.dyys.hr.vo.train.LearnMapSblSelctVO;
import com.dyys.hr.vo.train.TrainConstantPageVO;
import com.dyys.hr.vo.train.TrainConstantVO;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

public interface TrainConstantService extends ICrudService<TrainConstant, Integer> {
        List<TrainConstantVO> selectList(Map<String, Object> params);

        PageInfo<TrainConstantPageVO> pageList(Map<String, Object> params);

        Integer save(TrainConstantDTO dto, String loginUserId);

        Integer changeStatus(Map<String, Object> params);

        /**
         * 基础档案下拉框选项
         * @param type
         * @param pid
         * @return
         */
        List<String> selectBoxList(Integer type, String pid);

    /**
     * 学习地图学习方向选择列表
     * @param pid
     * @return
     */
    List<LearnMapSblSelctVO> selectListByMap(String pid);

    /**
     * 根据名称获得详情
     * @param type
     * @param pid
     * @param name
     * @return
     */
    TrainConstant getInfoByName(Integer type, String pid, String name);

    /**
     * 根据学习方向ID获取详情
     * @param id
     * @param type
     * @param pid
     * @return
     */
    TrainConstant getInfoBySblId(Integer id, Integer type, String pid);

    /**
     * 删除档案
     * @param id
     * @param loginEmplId
     * @return
     */
    Integer delById(Integer id, String loginEmplId);

    /**
     * 基础设置导出
     * @param params
     * @return
     */
    List<ConstantExcel> constantExp(Map<String, Object> params);
}