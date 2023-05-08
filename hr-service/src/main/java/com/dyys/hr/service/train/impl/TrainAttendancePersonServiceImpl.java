package com.dyys.hr.service.train.impl;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.PageUtil;
import com.dagongma.mybatis.core.service.impl.AbstractCrudService;
import com.dyys.hr.dao.train.TrainAttendancePersonMapper;
import com.dyys.hr.dto.train.IdDTO;
import com.dyys.hr.dto.train.TrainAttendancePersonDTO;
import com.dyys.hr.entity.train.TrainAttendancePerson;
import com.dyys.hr.service.train.TrainAttendancePersonService;
import com.dyys.hr.vo.train.TrainAttendancePersonVO;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
@Slf4j
public class TrainAttendancePersonServiceImpl extends AbstractCrudService<TrainAttendancePerson, Long> implements TrainAttendancePersonService {
    @Autowired
    private TrainAttendancePersonMapper trainAttendancePersonMapper;

    public List<TrainAttendancePersonVO> getListByQuery(Map<String,Object> params){
        return trainAttendancePersonMapper.getListByQuery(params);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean addStudents(List<TrainAttendancePersonDTO> dtoList, String loginUserId){
        boolean result = false;
        if(dtoList != null && !dtoList.isEmpty()){
            List<TrainAttendancePerson> personList = new ArrayList<>();
            for (TrainAttendancePersonDTO dto : dtoList){
                TrainAttendancePerson entity = new TrainAttendancePerson();
                BeanUtils.copyProperties(dto,entity);
                entity.setCreateUser(loginUserId);
                entity.setCreateTime(System.currentTimeMillis()/1000);
                personList.add(entity);
            }
            result = this.insertBatchSelective(personList);
        }
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer removeStudents(List<IdDTO> dtoList, String loginUserId){
        ArrayList<Long> ids = new ArrayList<>();
        for (IdDTO dto : dtoList){
            ids.add(dto.getId());
        }
        return this.deleteByIds(ids);
    }

    @Override
    public PageInfo<TrainAttendancePersonVO> pageList(Map<String, Object> params) {
        int page = Convert.toInt(params.get("page"));
        int limit = Convert.toInt(params.get("limit"));
        Page<Object> objects = PageMethod.startPage(page, limit);
        List<TrainAttendancePersonVO> voList = trainAttendancePersonMapper.getListByQuery(params);
        return new PageInfo<>(voList);
    }
}