package com.dyys.hr.utils;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.dagongma.kernel.commons.util.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.util.Assert;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 功能描述：
 *
 * @author syyang
 * @date 2019-08-13
 */
@Slf4j
public class ExcelUtils {
    /**
     * Excel表格导出
     *
     * @param response    HttpServletResponse对象
     * @param excelData   Excel表格的数据，封装为List<List<String>>
     * @param sheetName   sheet的名字
     * @param fileName    导出Excel的文件名
     * @param columnWidth Excel表格的宽度，建议为15
     * @throws IOException 抛IO异常
     */
    public static void exportExcel(HttpServletResponse response,
                                   List<List<String>> excelData,
                                   String sheetName,
                                   String fileName,
                                   int columnWidth) throws IOException {

        //声明一个工作簿
        HSSFWorkbook workbook = new HSSFWorkbook();

        //生成一个表格，设置表格名称
        HSSFSheet sheet = workbook.createSheet(sheetName);

        //设置表格列宽度
        sheet.setDefaultColumnWidth(columnWidth);

        //写入List<List<String>>中的数据
        int rowIndex = 0;
        for (List<String> data : excelData) {
            //创建一个row行，然后自增1
            HSSFRow row = sheet.createRow(rowIndex++);

            //遍历添加本行数据
            for (int i = 0; i < data.size(); i++) {
                //创建一个单元格
                HSSFCell cell = row.createCell(i);

                //创建一个内容对象
                HSSFRichTextString text = new HSSFRichTextString(data.get(i));

                //将内容对象的文字内容写入到单元格中
                cell.setCellValue(text);
            }
        }

        //清空response
        response.reset();
//        response.setContentType("application/vnd.ms-excel");
        response.setContentType("application/x-msdownloadoctet-stream;charset=utf-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes(), "ISO8859-1"));
        OutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        outputStream.flush();
        outputStream.close();
        //关闭workbook
        workbook.close();


    }

    /**
     * hutool excelUtils 文件导出
     *
     * @param response  HttpServletResponse对象
     * @param list      Excel表格的数据
     * @param texts     excel标题头
     * @param values    excel与标题头对应的数据字段
     * @param fileName  文件名
     * @param sheetName sheet名称
     */
    public static void export(HttpServletResponse response, List<?> list, String texts, String values, String fileName, String sheetName) {
        // 通过工具类创建writer，默认创建xls格式
        ExcelWriter writer = ExcelUtil.getWriter(true);

        String[] textArr = texts.split(",");
        String[] valueArr = values.split(",");

        Assert.isTrue(!(textArr.length == 0 || valueArr.length == 0 || textArr.length != valueArr.length), "text或者value配置错误");

        for (int i = 0; i < textArr.length; i++) {
            writer.addHeaderAlias(valueArr[i], textArr[i]);
        }

        if (StrUtil.isNotBlank(sheetName)) {
            writer.setSheet(sheetName);
        }

        // 一次性写出内容，使用默认样式
        writer.write(list);

        ServletOutputStream out = null;
        try {
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
            String wordName = URLEncoder.encode(fileName, "UTF-8");
            response.setHeader("Content-Disposition", "attachment;filename=" + wordName + ".xlsx");

            //out为OutputStream，需要写出到的目标流
            out = response.getOutputStream();
            writer.flush(out);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        } finally {
            if (writer != null) {
                // 关闭writer，释放内存
                writer.close();
            }
        }
    }


    /**
     * @param response HttpServletResponse对象
     * @param list     Excel表格的数据
     * @param texts    excel标题头
     * @param values   excel与标题头对应的数据字段
     * @param fileName 文件名
     * @author ZHIQIANG LI
     * @date 2019/9/8 18:49
     **/
    public static void excel(HttpServletResponse response, List<?> list, String texts, String values, String fileName) {

        //缓存
        SXSSFWorkbook workbook = new SXSSFWorkbook(1000);
        workbook.setCompressTempFiles(true);

        // 将文件上传到页面
        OutputStream ouputStream = null;
        try {
            /**
             * 写入数据
             */
            createExcel(workbook, list, texts, values, fileName);

            response.setContentType("application/x-msdownloadoctet-stream;charset=utf-8");
            String wordName = URLEncoder.encode(fileName, "UTF-8");
            response.setHeader("Content-disposition",
                    "attachment;filename=" + wordName + ".xlsx");
            ouputStream = response.getOutputStream();
            workbook.write(ouputStream);
        } catch (Exception e1) {
            log.error(e1.getMessage(), e1);
        } finally {
            try {
                if (ouputStream != null) {
                    ouputStream.flush();
                    ouputStream.close();
                }
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }

    }

    /**
     * 创建Excel文件
     *
     * @param workbook excel画板
     * @param data     数据
     * @param fileName excel文件名称
     * @param texts    表格名称
     * @param values   表格值
     * @return
     * @author ZHIQIANG LI
     * @date 2017/12/12 19:24
     **/
    private static void createExcel(SXSSFWorkbook workbook, List<?> data, String texts, String values, String fileName) {
        //表头样式
        CellStyle titleStyle = workbook.createCellStyle();
        titleStyle.setAlignment(HorizontalAlignment.CENTER);
        Font titleFont = workbook.createFont();
        titleFont.setFontHeightInPoints((short) 20);
        titleStyle.setFont(titleFont);

        // 列头样式
        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setBorderBottom(BorderStyle.THIN);
        headerStyle.setBorderLeft(BorderStyle.THIN);
        headerStyle.setBorderRight(BorderStyle.THIN);
        headerStyle.setBorderTop(BorderStyle.THIN);
        headerStyle.setAlignment(HorizontalAlignment.CENTER);

        Font headerFont = workbook.createFont();
        headerFont.setFontHeightInPoints((short) 12);
        //加粗
        headerFont.setBold(true);

        headerStyle.setFont(headerFont);
        // 单元格样式
        CellStyle cellStyle = workbook.createCellStyle();
///        cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);

        Font cellFont = workbook.createFont();
        cellFont.setFontHeightInPoints((short) 12);
        cellFont.setBold(false);
        cellStyle.setFont(cellFont);
        // 生成一个(带标题)表格
        SXSSFSheet sheet = (SXSSFSheet) workbook.createSheet();
        //单元格自适应
///        sheet.autoSizeColumn(1, true);

        //设置列宽
        String[] textArr = texts.split(",");
        String[] valueArr = values.split(",");

        //至少字节数
        int minBytes = 20;
        int[] arrColWidth = new int[textArr.length];
        // 产生表格标题行,以及设置列宽
        String[] properties = new String[textArr.length];
        String[] headers = new String[textArr.length];
        for (int i = 0; i < textArr.length; i++) {

            log.info("i | " + i);
            properties[i] = valueArr[i];
            headers[i] = textArr[i];

            int bytes = textArr[i].getBytes().length;
            arrColWidth[i] = bytes < minBytes ? minBytes : bytes;
            sheet.setColumnWidth(i, arrColWidth[i] * 256);
        }

        int rowIndex = 0;

        for (int j = 0; j < data.size(); j++) {

            Map<String, Object> map = BeanUtil.beanToMap(data.get(j));

            if (rowIndex == 65535 || rowIndex == 0) {
                /**
                 * 画表头
                 */
                //表头 rowIndex=0
                SXSSFRow titleRow2 = (SXSSFRow) sheet.createRow(0);
                titleRow2.createCell(0).setCellValue(fileName);
                titleRow2.getCell(0).setCellStyle(titleStyle);
                sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, textArr.length - 1));
                /**
                 * 画列头
                 */
                //列头 rowIndex =1
                SXSSFRow headerRow2 = (SXSSFRow) sheet.createRow(1);
                for (int i = 0; i < headers.length; i++) {
                    headerRow2.createCell(i).setCellValue(headers[i]);
                    headerRow2.getCell(i).setCellStyle(headerStyle);

                }
                //数据从rowIndex = 2开始
                rowIndex = 2;
            }
            /**
             * 填充数据
             */
            SXSSFRow dataRow = (SXSSFRow) sheet.createRow(rowIndex);

            for (int i = 0; i < properties.length; i++) {
                SXSSFCell newCell = (SXSSFCell) dataRow.createCell(i);

                Object o = map.get(properties[i]);
                String cellValue = getCellValue(o);

                newCell.setCellValue(cellValue);
                newCell.setCellStyle(cellStyle);
            }
            rowIndex++;
        }
    }

    /**
     * 设置excel单元格数值
     *
     * @param o
     * @return
     */
    private static String getCellValue(Object o) {
        String cellValue = "";
        if (o == null) {
            cellValue = "";
        } else if (o instanceof Date) {
            cellValue = new SimpleDateFormat(DateUtils.SDF_YMDHMS).format(o);
            String[] values = cellValue.split(" ");
            if (values.length > 1 && "00:00:00".equals(values[1])) {
                cellValue = values[0];
            }
        } else if (o instanceof Float || o instanceof Double) {
            cellValue = new BigDecimal(o.toString()).setScale(2, BigDecimal.ROUND_HALF_UP).toString();
        } else {
            cellValue = o.toString();
        }
        return cellValue;
    }
}
