/**
 * Copyright (c) 2018 CoolChange All rights reserved.
 *
 * https://www.coolchange.tech
 *
 * 版权所有，侵权必究！
 */

package com.dyys.hr.oss;

import cn.hutool.extra.spring.SpringUtil;
import com.dyys.hr.exception.ErrorCode;
import com.dyys.hr.exception.RenException;
import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.proto.storage.DownloadByteArray;
import com.github.tobato.fastdfs.service.DefaultGenerateStorageClient;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * FastDFS
 *
 * @author
 */
public class FastDFSCloudStorageService extends AbstractCloudStorageService {
    private static DefaultGenerateStorageClient defaultGenerateStorageClient;

    static {
        defaultGenerateStorageClient = (DefaultGenerateStorageClient) SpringUtil.getBean("defaultGenerateStorageClient");
    }

    public FastDFSCloudStorageService(CloudStorageConfig config){
        this.config = config;
    }

    @Override
    public String upload(byte[] data, String path) {
        return upload(new ByteArrayInputStream(data), path);
    }

    @Override
    public String upload(InputStream inputStream, String suffix) {
        StorePath storePath;
        try {
            storePath = defaultGenerateStorageClient.uploadFile("group1", inputStream, inputStream.available(), suffix);
        }catch (Exception ex){
            throw new RenException(ErrorCode.OSS_UPLOAD_FILE_ERROR, ex, ex.getMessage());
        }

        return storePath.getGroup() + "/" + storePath.getPath();
    }

    @Override
    public String uploadSuffix(byte[] data, String suffix) {
        return upload(data, suffix);
    }

    @Override
    public String uploadSuffixNewPath(byte[] data, String suffix, String newPath) {
        return null;
    }

    @Override
    public String uploadSuffix(InputStream inputStream, String suffix) {
        return upload(inputStream, suffix);
    }

    @Override
    public byte[] download(String filePath) {
        String group = filePath.substring(0, filePath.indexOf("/"));
        String path = filePath.substring(filePath.indexOf("/") + 1);
        DownloadByteArray downloadByteArray = new DownloadByteArray();
        byte[] bytes = defaultGenerateStorageClient.downloadFile(group, path, downloadByteArray);
        return bytes;
    }
}