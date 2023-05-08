package com.dyys.hr.controller.common;

import com.dagongma.kernel.filter.XssHttpServletRequestWrapper;
import com.dyys.hr.entity.exam.ExamPaper;
import com.dyys.hr.entity.sys.SysFile;
import com.dyys.hr.exception.ErrorCode;
import com.dyys.hr.helper.UserHelper;
import com.dyys.hr.oss.OSSFactory;
import com.dyys.hr.service.sys.ISysFileService;
import com.dyys.hr.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;
import java.util.stream.Collectors;

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
    public Result<Map<String, Object>> upload(@RequestParam("file") MultipartFile file) throws IOException {
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
        String path = OSSFactory.build().uploadSuffix(file.getBytes(), extension);
        sysFile.setFileNameEncode(path.substring(path.lastIndexOf("/") + 1));
        sysFile.setServerPath(path);
        sysFile.setWebPath(path);

        //保存文件信息
        if (sysFileService.insertSelective(sysFile) > 0) {
            Map<String, Object> data = new HashMap<>(3);
            data.put("src", path);
            data.put("fileId", sysFile.getId());
            data.put("filename", file.getOriginalFilename());
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
        file = OSSFactory.build().download(fileEntity.getServerPath());

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
}
