package com.dyys.hr.controller.common;

import com.dyys.hr.entity.sys.SysFile;
import com.dyys.hr.exception.ErrorCode;
import com.dyys.hr.helper.UserHelper;
import com.dyys.hr.oss.OSSFactory;
import com.dyys.hr.service.sys.ISysFileService;
import com.dyys.hr.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;
import ws.schild.jave.MultimediaObject;
import ws.schild.jave.info.MultimediaInfo;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author: Daan li
 * @Date: 2022/5/7 11:07
 */
@RestController
@RequestMapping("common/oss")
@Api(tags = "文件上传操作")
public class OssController {
    @Autowired
    private ISysFileService sysFileService;
    @Autowired
    private UserHelper userHelper;

    /**
     * 单文件上传
     *
     * @param file
     * @return
     * @throws IOException
     */
    @PostMapping("upload")
    @ApiOperation(value = "单文件上传")
    @Transactional(rollbackFor = Exception.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "file", value = "上传文件", dataTypeClass = MultipartFile.class, required = true, allowMultiple = true),
            @ApiImplicitParam(name = "type", value = "文件类型 1.音视频 2.其他", paramType = "query", dataType="int")
    })
    public Result<Map<String, Object>> upload(@RequestParam("file") MultipartFile file, @ApiIgnore @RequestParam Map<String, Object> params) throws IOException {
        if (file.isEmpty()) {
            return new Result<Map<String, Object>>().error(ErrorCode.UPLOAD_FILE_EMPTY);
        }

        //文件后缀
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        SysFile sysFile = new SysFile();
        sysFile.setFileName(file.getOriginalFilename());
        sysFile.setFileSize(file.getSize());
        sysFile.setFileSuffix(extension);
        sysFile.setStatus(1);
        sysFile.setCreateDate(new Date());
        sysFile.setCreator(userHelper.getLoginEmplId());

        //上传文件,根目录
        String path = Objects.requireNonNull(OSSFactory.build()).uploadSuffix(file.getBytes(), extension);
        sysFile.setFileNameEncode(path.substring(path.lastIndexOf("/") + 1));
        sysFile.setServerPath(path);
        sysFile.setWebPath(path);

        //获取上传文件时长
        int type = 2;
        if (params.containsKey("type") && !StringUtils.isEmpty(params.get("type").toString())) {
            type = Integer.parseInt(params.get("type").toString());
        }
        sysFile.setType(type);
        sysFile.setDuration(getDurationBackString(file));

        //保存文件信息
        if (sysFileService.insertSelective(sysFile) > 0) {
            Map<String, Object> data = new HashMap<>(3);
            data.put("src", path);
            data.put("fileId", sysFile.getId());
            data.put("filename", file.getOriginalFilename());
            data.put("duration", sysFile.getDuration());
            return new Result<Map<String, Object>>().ok(data);
        }
        return new Result<Map<String, Object>>().error("上传失败，请重试");
    }

//    /**
//     * 多文件上传
//     *
//     * @param request
//     * @return
//     * @throws IOException
//     */
//    @PostMapping("uploadBatch")
//    @ApiOperation(value = "多文件上传")
//    @Transactional(rollbackFor = Exception.class)
//    public Result<Map<String, Object>> uploadBatch(@RequestParam("files") MultipartFile[] files) throws IOException {
//        // 判断是否为空
//        if (files == null) {
//            return new Result<Map<String, Object>>().error(ErrorCode.UPLOAD_FILE_EMPTY);
//        }
//
//        List<SysFile> fileList = new ArrayList<>();
//        for (MultipartFile file : files) {
//            //文件后缀
//            String extension = FilenameUtils.getExtension(file.getOriginalFilename());
//            SysFile sysFile = new SysFile();
//            sysFile.setFileName(file.getOriginalFilename());
//            sysFile.setFileSize(file.getSize());
//            sysFile.setFileSuffix(extension);
//            sysFile.setStatus(1);
//            sysFile.setCreateDate(new Date());
//            sysFile.setCreator(userHelper.getLoginEmplId());
//
//            //上传文件,根目录
//            String path = OSSFactory.build().uploadSuffix(file.getBytes(), extension);
//            sysFile.setFileNameEncode(path.substring(path.lastIndexOf("/") + 1));
//            sysFile.setServerPath(path);
//            sysFile.setWebPath(path);
//            fileList.add(sysFile);
//        }
//
//        if (sysFileService.insertBatch(fileList)) {
//            Map<String, Object> data = new HashMap<>(3);
//            data.put("fileId", StringUtils.join(fileList.stream().map(SysFile::getId).collect(Collectors.toList()), ","));
//            data.put("filename", fileList.stream().map(SysFile::getFileName).collect(Collectors.joining(",")));
//            data.put("src", fileList.stream().map(SysFile::getWebPath).collect(Collectors.joining(",")));
//            return new Result<Map<String, Object>>().ok(data);
//        }
//        return new Result<Map<String, Object>>().error("上传失败，请重试");
//    }

    @GetMapping("download/{fileId}")
    @ApiOperation(value = "文件下载带文件Id")
    public void fileDownload(@PathVariable("fileId") Long fileId, HttpServletResponse response, HttpServletRequest request) throws IOException {
        if (fileId == null) {
            return;
        }
        SysFile fileEntity = sysFileService.selectById(fileId);
        if (fileEntity == null) {
            return;
        }

        String fileName = fileEntity.getFileName();
        byte[] file;
        file = Objects.requireNonNull(OSSFactory.build()).download(fileEntity.getServerPath());

        response.setHeader("Cache-Control", "no-store, no-cache");
        response.setHeader("content-type", "application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=" + setFileDownloadHeader(request, fileName));
        response.addHeader("Access-Control-Expose-Headers", "Content-Disposition");

        ServletOutputStream out = response.getOutputStream();
        out.write(file);
        out.close();
    }

    /**
     * 下载文件名重新编码
     *
     * @param request  请求对象
     * @param fileName 文件名
     * @return 编码后的文件名
     */
    public static String setFileDownloadHeader(HttpServletRequest request, String fileName)
            throws UnsupportedEncodingException {
        final String agent = request.getHeader("USER-AGENT");
        String filename = fileName;
        if (agent.contains("MSIE")) {
            // IE浏览器
            filename = URLEncoder.encode(filename, "utf-8");
            filename = filename.replace("+", " ");
        } else if (agent.contains("Firefox")) {
            // 火狐浏览器
            filename = new String(fileName.getBytes(), "ISO8859-1");
        } else if (agent.contains("Chrome")) {
            // google浏览器
            filename = URLEncoder.encode(filename, "utf-8");
        } else {
            // 其它浏览器
            filename = URLEncoder.encode(filename, "utf-8");
        }
        return filename;
    }

    /**
     * 上传视频，获取视频时长，返回毫秒
     * @param multipartFile
     * @return
     */
    public static long getDurationBackMillis(MultipartFile multipartFile){
        if(multipartFile != null){
            try{
                // 根据上传的文件名字，构建初始化的文件对象（临时文件），这个文件是空的
                File file = new File(Objects.requireNonNull(multipartFile.getOriginalFilename()));
                // 通过工具类，将文件拷贝到空的文件对象中
                FileUtils.copyInputStreamToFile(multipartFile.getInputStream(), file);

                // 将普通文件对象转换为媒体对象
                MultimediaObject multimediaObject = new MultimediaObject(file);
                // 获取媒体对象的信息对象
                MultimediaInfo info = multimediaObject.getInfo();
                // 从媒体信息对象中获取媒体的时长，单位是：ms
                long duration = info.getDuration();
                // 删除临时文件
                file.delete();

                return duration;
            } catch(Exception e){
                return 0L;
            }
        }
        return 0L;
    }

    /**
     * 上传视频，获取视频时长，返回时分秒字符串
     * @param multipartFile
     * @return
     */
    public static String getDurationBackString(MultipartFile multipartFile){
        // 获取视频时长，返回毫秒
        long duration = getDurationBackMillis(multipartFile);
        // 毫秒转时分秒的转换
        // 日期格式化对象，给时分秒格式
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        // 这里很重要，如果不设置时区的话，输出结果就会是几点钟，而不是毫秒值对应的时分秒数量了。
        formatter.setTimeZone(TimeZone.getTimeZone("GMT+00:00"));
        // 毫秒转化为字符串
        return formatter.format(duration);
    }
}
