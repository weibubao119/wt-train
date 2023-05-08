/**
 * Copyright (c) 2018 CoolChange All rights reserved.
 * <p>
 * https://www.coolchange.tech
 * <p>
 * 版权所有，侵权必究！
 */

package com.dyys.hr.oss;

import com.dyys.hr.exception.ErrorCode;
import com.dyys.hr.exception.RenException;
import org.apache.commons.io.FileUtils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * 本地上传
 *
 * @author
 */
public class LocalCloudStorageService extends AbstractCloudStorageService {

    public LocalCloudStorageService(CloudStorageConfig config) {
        this.config = config;
    }

    @Override
    public String upload(byte[] data, String path) {
        return upload(new ByteArrayInputStream(data), path);
    }

    @Override
    public String upload(InputStream inputStream, String path) {

        File file = new File(config.getLocalPath() + File.separator + path);
        try {
            FileUtils.copyInputStreamToFile(inputStream, file);
        } catch (IOException e) {
            throw new RenException(ErrorCode.OSS_UPLOAD_FILE_ERROR, e, "");
        }
        return config.getLocalDomain() + "/" + path;
    }

    @Override
    public String uploadSuffix(byte[] data, String suffix) {
        return upload(data, getPath(config.getLocalPrefix(), suffix));
    }

    @Override
    public String uploadSuffixNewPath(byte[] data, String suffix, String newPath) {
        InputStream inputStream = new ByteArrayInputStream(data);
        String path = getPath(newPath, suffix);
        File file = new File(path);
        try {
            FileUtils.copyInputStreamToFile(inputStream, file);
        } catch (IOException e) {
            throw new RenException(ErrorCode.OSS_UPLOAD_FILE_ERROR, e, "");
        }
        return path;
    }

    @Override
    public String uploadSuffix(InputStream inputStream, String suffix) {
        return upload(inputStream, getPath(config.getLocalPrefix(), suffix));
    }

    @Override
    public byte[] download(String fileUrl) {
        try {
            File file = new File(config.getLocalPath() + fileUrl);

            byte[] bytes = FileUtils.readFileToByteArray(file);
            return bytes;
        } catch (IOException ex) {
            throw new RuntimeException("文件下载出错", ex);
        }
        //  return null;
    }
}
