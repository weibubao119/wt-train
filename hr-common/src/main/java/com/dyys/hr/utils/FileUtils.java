package com.dyys.hr.utils;

import com.dagongma.kernel.commons.util.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 文件操作
 *
 * @author ZHIQIANG LI
 * @date 2019/6/28 23:57
 **/
@Slf4j
public class FileUtils {

    /**
     * 上传附件
     *
     * @param path 路径
     * @param nginxUrl nginx代理路径
     * @param file 文件
     * @return 上传后的附件名称
     */
    public static Map<String, String> upload(String path,String nginxUrl, MultipartFile file) throws Exception{
        BufferedOutputStream stream = null;

        String fileName = file.getOriginalFilename();
        String extName = fileName.substring(fileName.lastIndexOf(".") + 1);

        String date = DateUtils.format(new Date(), DateUtils.YYYYMMDD);

        StringBuilder sb = new StringBuilder()
                .append(path)
                .append("/")
                .append(date);

        File rootPath = new File(ResourceUtils.getURL("classpath:").getPath());

        if (!rootPath.exists()){
            rootPath = new File("");
        }

        File upload = new File(rootPath.getAbsolutePath(),sb.toString());

        if (!upload.exists()){
            upload.mkdirs();
        }

        String tempName = String.format("%s%d",fileName.replace("."+extName,""),System.currentTimeMillis()/1000);
        StringBuffer filePath = new StringBuffer().append(upload.getAbsolutePath()).append("/").append(tempName).append(".").append(extName);
        sb.append("/").append(tempName).append(".").append(extName);

        StringBuilder url = new StringBuilder();
        url.append(nginxUrl).append(sb.toString());

        try {
            byte[] bytes = file.getBytes();
            stream = new BufferedOutputStream(new FileOutputStream(
                    new File(filePath.toString())));
            stream.write(bytes);

            Map<String, String> map = new HashMap<>(0);
            map.put("fileName", tempName);
            map.put("filePath", url.toString());

            return map;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new Exception(e);
        } finally {
            try {
                if (stream != null) {
                    stream.close();
                }
            } catch (IOException e) {
                log.error(e.getMessage(), e);
            }
        }
    }
}
