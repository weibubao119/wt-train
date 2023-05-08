/**
 * Copyright (c) 2018 CoolChange All rights reserved.
 *
 * https://www.coolchange.tech
 *
 * 版权所有，侵权必究！
 */

package com.dyys.hr.oss;

import cn.hutool.extra.spring.SpringUtil;
import com.dyys.hr.oss.constants.Constant;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 文件上传Factory
 * @author
 */
public final class OSSFactory {
    public static AbstractCloudStorageService build(){
        CloudStorageConfig config = SpringUtil.getBean(CloudStorageConfig.class);

        if(config.getType() == Constant.CloudService.FASTDFS.getValue()){
            return new FastDFSCloudStorageService(config);
        }else if(config.getType() == Constant.CloudService.LOCAL.getValue()){
            return new LocalCloudStorageService(config);
        }

        return null;
    }
}