package com.dyys.hr.service.train;

import com.dagongma.mybatis.core.service.ICrudService;
import com.dyys.hr.dto.train.IdDTO;
import com.dyys.hr.dto.train.TrainAttendancePersonDTO;
import com.dyys.hr.entity.train.TrainAttendancePerson;
import com.dyys.hr.vo.train.TrainAttendancePersonVO;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

public interface TrainAttendancePersonService extends ICrudService<TrainAttendancePerson, Long> {
    List<TrainAttendancePersonVO> getListByQuery(Map<String,Object> params);

    Boolean addStudents(List<TrainAttendancePersonDTO> dtoList, String loginUserId);

    Integer removeStudents(List<IdDTO> dtoList, String loginUserId);

    PageInfo<TrainAttendancePersonVO> pageList(Map<String,Object> params);


}