package com.dyys.hr.dao.train;

import com.dagongma.mybatis.core.dao.ICrudMapper;
import com.dyys.hr.entity.train.TrainAttendancePerson;
import com.dyys.hr.vo.train.TrainAttendancePersonVO;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface TrainAttendancePersonMapper extends ICrudMapper<TrainAttendancePerson> {
    List<TrainAttendancePersonVO> getListByQuery(Map<String,Object> params);

    Boolean deleteByAttendanceRulesId(Long rulesId);
}