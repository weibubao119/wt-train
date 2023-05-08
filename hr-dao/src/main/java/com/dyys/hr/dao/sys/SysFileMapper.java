package com.dyys.hr.dao.sys;

import com.dagongma.mybatis.core.dao.ICrudMapper;
import com.dyys.hr.entity.sys.SysFile;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 上传文件 Mapper 接口
 * </p>
 *
 * @author lidaan
 * @since 2022-05-07
 */
@Mapper
public interface SysFileMapper extends ICrudMapper<SysFile> {

}
