package com.dyys.hr.dao.staff;

import com.dagongma.mybatis.core.dao.ICrudMapper;
import com.dyys.hr.entity.staff.StaffPosnSec;
import com.dyys.hr.vo.common.PsPosnSecVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 职序管理表 Mapper 接口
 * </p>
 *
 * @author yangye
 * @since 2022-08-23
 */
@Mapper
public interface StaffPosnSecMapper extends ICrudMapper<StaffPosnSec> {
    /**
     * 职序筛选列表
     * @param params
     * @return
     */
    List<PsPosnSecVO> selectList(Map<String, Object> params);

    /**
     * 根据职序编码获取职序的名称
     * @param posnSecCode
     * @return
     */
    PsPosnSecVO basicInfoByCode(@Param("posnSecCode") String posnSecCode);
}
