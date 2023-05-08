package com.dyys.hr.dao.train;

import com.dagongma.mybatis.core.dao.ICrudMapper;
import com.dyys.hr.entity.train.TrainInstitutionGrade;
import com.dyys.hr.vo.train.TrainInstitutionGradeVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 培训机构等级 Mapper 接口
 * </p>
 *
 * @author yangye
 * @since 2022-05-23
 */
@Repository
public interface TrainInstitutionGradeMapper extends ICrudMapper<TrainInstitutionGrade> {
    /**
     * 培训机构等级列表
     * @param institutionId
     * @return
     */
    List<TrainInstitutionGradeVO> institutionGradeList(@Param("institutionId") Long institutionId);

    /**
     * 同一个机构设置等级的年度总数
     * @param map
     * @return
     */
    Integer selectCountByYearly(@Param("map") Map<String, Object> map);
}
