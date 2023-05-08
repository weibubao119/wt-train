package com.dyys.hr.utils;

import cn.hutool.core.util.ClassUtil;
import freemarker.cache.ClassTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.URLEncoder;
import java.util.Map;


/**
 * freemarket 文件处理
 *
 * @author ZHIQIANG LI
 * @date 2019/9/9 23:46
 **/
@Slf4j
public class FreemarketUtil {
    private static Configuration configuration = new Configuration(Configuration.getVersion());


    /**
     * freemarket附件下载
     *
     * @param fileName 附件名称
     * @param extName  附件拓展名
     * @param fileType freemarket模板类别
     * @param model    数据模型
     * @param response http响应
     * @author ZHIQIANG LI
     * @date 2019/9/10 0:21
     **/
    public static void download(String fileName, String extName, String fileType, Map<String, Object> model, HttpServletResponse response) {

        Writer w = null;
        // 将文件上传到页面
        try {
            configuration.setDefaultEncoding("utf-8");
            configuration.setTemplateLoader(new ClassTemplateLoader(ClassUtil.getClassLoader(),"templates"));
            Template template = configuration.getTemplate(fileType + ".ftl");

            response.setContentType("application/x-msdownloadoctet-stream;charset=utf-8");
            String wordName = URLEncoder.encode(fileName, "UTF-8");
            response.setHeader("Content-disposition",
                    "attachment;filename=" + wordName + "." + extName);
            w = new OutputStreamWriter(response.getOutputStream(), "utf-8");
            template.setClassicCompatible(true);
            template.process(model, w);
        } catch (Exception e1) {
            log.error(e1.getMessage(), e1);
            throw new RuntimeException(e1.getMessage());
        } finally {
            try {
                if (w != null) {
                    w.flush();
                    w.close();
                }
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }

    }
}
