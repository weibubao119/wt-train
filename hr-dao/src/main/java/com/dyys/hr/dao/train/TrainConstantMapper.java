package com.dyys.hr.dao.train;

import com.dagongma.mybatis.core.dao.ICrudMapper;
import com.dyys.hr.entity.train.TrainConstant;
import com.dyys.hr.entity.train.excel.ConstantExcel;
import com.dyys.hr.vo.train.LearnMapSblSelctVO;
import com.dyys.hr.vo.train.TrainConstantPageVO;
import com.dyys.hr.vo.train.TrainConstantVO;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface TrainConstantMapper extends ICrudMapper<TrainConstant> {
    List<TrainConstantVO> selectList(Map<String, Object> params);

    List<TrainConstantPageVO> pageList(Map<String, Object> params);

    /**
     * 学习地图学习方向选择列表
     * @param pid
     * @return
     */
    List<LearnMapSblSelctVO> selectListByMap(String pid);

    /**
     * 基础设置导出列表
     * @param params
     * @return
     */
    List<ConstantExcel> constantExp(Map<String, Object> params);
}