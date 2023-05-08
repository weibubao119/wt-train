package com.dyys.hr.dao.staff;

import com.dagongma.mybatis.core.dao.ICrudMapper;
import com.dyys.hr.entity.staff.StaffProfession;
import com.dyys.hr.vo.common.PsProfessionVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 职族管理表 Mapper 接口
 * </p>
 *
 * @author yangye
 * @since 2022-08-23
 */
@Mapper
public interface StaffProfessionMapper extends ICrudMapper<StaffProfession> {
    /**
     * 职族筛选列表
     * @param params
     * @return
     */
    List<PsProfessionVO> selectList(Map<String, Object> params);
}
