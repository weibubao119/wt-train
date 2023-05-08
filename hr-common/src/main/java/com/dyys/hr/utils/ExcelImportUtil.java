package com.dyys.hr.utils;

import com.dyys.hr.annotation.IsNeeded;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Desc
 * Create by yangye
 * Date 2023/4/27 11:13
 */
public class ExcelImportUtil {
    private static int totalRows = 0; // 总行数

    private static int totalCells = 0; // 总列数

    private static String errorInfo; // 错误信息

    /** 无参构造方法 */
    public ExcelImportUtil() {
    }

    /** 总行数*/
    public static int getTotalRows() {
        return totalRows;
    }

    /** 总列数*/
    public static int getTotalCells() {
        return totalCells;
    }

    /** 错误信息*/
    public static String getErrorInfo() {
        return errorInfo;
    }

    /**
     * 根据流读取Excel文件
     * @param inputStream
     * @param isExcel2003
     * @return
     * @see [类、类#方法、类#成员]
     */
    public List<List<String>> read(InputStream inputStream, boolean isExcel2003) throws IOException {
        List<List<String>> dataList = null;
        // 根据版本选择创建Workbook的方式
        Workbook wb = null;
        if (isExcel2003) {
            wb = new HSSFWorkbook(inputStream);
        } else {
            wb = new XSSFWorkbook(inputStream);
        }
        dataList = readDate(wb);
        return dataList;
    }

    /**
     * 读取数据
     * @param wb
     * @return
     * @see [类、类#方法、类#成员]
     */
    private List<List<String>> readDate(Workbook wb) {
        List<List<String>> dataList = new ArrayList<>();
        // 获取该工作表中的第一个工作表
        Sheet sheet = wb.getSheetAt(0);

        // 得到Excel的行数
        totalRows = sheet.getPhysicalNumberOfRows();

        // 得到Excel的列数
        if (totalRows >= 1 && sheet.getRow(0) != null) {
            totalCells = sheet.getRow(0).getPhysicalNumberOfCells();
        }

        // 循环Excel的行
        for (int r = 0; r < totalRows; r++) {
            Row row = sheet.getRow(r);
            if (row == null) {
                continue;
            }
            List<String> rowLst = new ArrayList<>();
            // 循环Excel的列
            for (int c = 0; c < getTotalCells(); c++) {
                Cell cell = row.getCell(c);
                String cellValue = getCellValueByCell(cell);
                rowLst.add(cellValue);
            }
            // 保存第r行的第c列
            dataList.add(rowLst);
        }
        return dataList;
    }

    /**
     * 按指定坐标读取实体数据-按顺序放入带有注解的实体成员变量中
     * @param wb 工作簿
     * @param t 实体
     * @param in 输入流
     * @param integers 指定需要解析的坐标
     * @return T 相应实体
     * @throws IOException
     * @throws Exception
     * @see [类、类#方法、类#成员]
     */
    @SuppressWarnings("unused")
    public static <T> T readDateT(Workbook wb, T t, InputStream in, Integer[]... integers) throws IOException, Exception {
        // 获取该工作表中的第一个工作表
        Sheet sheet = wb.getSheetAt(0);
        // 成员变量的值
        Object entityMemberValue = "";
        // 获取所有成员变量
        Field[] fields = t.getClass().getDeclaredFields();
        // 列开始下标
        int startCell = 0;

        // 循环出需要的成员
        for (int f = 0; f < fields.length; f++) {
            fields[f].setAccessible(true);
            String fieldName = fields[f].getName();
            boolean fieldHasAnno = fields[f].isAnnotationPresent(IsNeeded.class);
            // 有注解
            if (fieldHasAnno) {
                IsNeeded annotation = fields[f].getAnnotation(IsNeeded.class);
                boolean isNeeded = annotation.isNeeded();

                // Excel需要赋值的列
                if (isNeeded) {
                    // 获取行和列
                    int x = integers[startCell][0] - 1;
                    int y = integers[startCell][1] - 1;

                    Row row = sheet.getRow(x);
                    if (row == null) {
                        continue;
                    }
                    Cell cell = row.getCell(y);
                    // Excel中解析的值
                    String cellValue = getCellValueByCell(cell);
                    // 需要赋给成员变量的值
                    entityMemberValue = getEntityMemberValue(entityMemberValue, fields, f, cellValue);
                    PropertyUtils.setProperty(t, fieldName, entityMemberValue); // 赋值
                    startCell++; // 列的下标加1
                }
            }
        }

        return t;
    }

    /**
     *
     * 读取列表数据-按顺序放入带有注解的实体成员变量中
     * @param wb 工作簿
     * @param clazz 实体类
     * @param beginLine 开始行数
     * @param totalcut 结束行数减去相应行数
     * @return List<T> 实体列表
     * @throws Exception
     * @see [类、类#方法、类#成员]
     */
    @SuppressWarnings("unchecked")
    public static <T> List<T> readDateListT (Workbook wb, final Class<?> clazz, int beginLine, int totalcut) throws Exception {
        List<T> list = new ArrayList<>();
        // 得到第一个shell
        Sheet sheet = wb.getSheetAt(0);

        // 得到Excel的行数
        totalRows = sheet.getPhysicalNumberOfRows();

        // 得到Excel的列数
        if (totalRows >= 1 && sheet.getRow(0) != null) {
            totalCells = sheet.getRow(0).getPhysicalNumberOfCells();
        }

        // 循环Excel的行
        for (int r = beginLine - 1; r < totalRows - totalcut; r++) {
            Row row = sheet.getRow(r);
            if (row == null) {
                continue;
            }
            // 实体类
            Object newInstance = clazz.newInstance();
            // 成员变量的值
            Object entityMemberValue = "";
            // 所有成员变量
            Field[] fields = clazz.getDeclaredFields();
            // 列开始下标
            int startCell = 0;
            // 按实体成员循环取值
            for (int f = 0; f < fields.length; f++) {
                fields[f].setAccessible(true);
                String fieldName = fields[f].getName();
                boolean fieldHasAnno = fields[f].isAnnotationPresent(IsNeeded.class);
                // 有注解
                if (fieldHasAnno) {
                    IsNeeded annotation = fields[f].getAnnotation(IsNeeded.class);
                    boolean isNeeded = annotation.isNeeded();
                    // Excel需要赋值的列
                    if (isNeeded) {
                        Cell cell = row.getCell(startCell);
                        // Excel列中解析的值
                        String cellValue = getCellValueByCell(cell);
                        // 需要赋给成员变量的值
                        entityMemberValue = getEntityMemberValue(entityMemberValue, fields, f, cellValue);
                        PropertyUtils.setProperty(newInstance, fieldName, entityMemberValue); // 赋值
                        startCell++; // 列的下标加1
                    }
                }
            }
            list.add((T)newInstance);
        }
        return list;
    }

    /**
     * 根据Excel表格中的数据判断类型得到值
     * @param cell
     * @return
     * @see [类、类#方法、类#成员]
     */
    private static String getCellValueByCell (Cell cell)
    {
        String cellValue = "";

        if (cell != null && !cell.toString().trim().equals("")) {
            //  以下是判断数据的类型
            CellType cellType = cell.getCellType();

            switch (cellType) {
                // 数字
                case NUMERIC: // 数字
                    short format = cell.getCellStyle().getDataFormat();
                    if (DateUtil.isCellDateFormatted(cell)) {
                        SimpleDateFormat sdf = null;
                        if (format == 20 || format == 32) {
                            sdf = new SimpleDateFormat("HH:mm");
                        } else if (format == 14 || format == 31 || format == 57 || format == 58) {
                            // 处理自定义日期格式：m月d日(通过判断单元格的格式id解决，id的值是58)
                            sdf = new SimpleDateFormat("yyyy-MM-dd");
                            double value = cell.getNumericCellValue();
                            Date date = DateUtil.getJavaDate(value);
                            cellValue = sdf.format(date);
                        } else {
                            sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // 日期
                        }
                        try {
                            // 日期
                            cellValue = sdf.format(cell.getDateCellValue());
                        } catch (Exception e) {
                            try {
                                throw new Exception("exception on get date data !".concat(e.toString()));
                            } catch (Exception e1) {
                                e1.printStackTrace();
                            }
                        } finally {
                            sdf = null;
                        }
                    } else {
                        // BigDecimal bd = new BigDecimal(cell.getNumericCellValue());
                        // cellValue = bd.toPlainString();// 数值 这种用BigDecimal包装再获取plainString，可以防止获取到科学计数值
                        DecimalFormat decimalFormat = new DecimalFormat("####################.###########");
                        Object objCell = decimalFormat.format(cell.getNumericCellValue());
                        String strCell = String.valueOf(objCell);
                        BigDecimal bd = new BigDecimal(strCell).stripTrailingZeros();
                        cellValue = bd.toPlainString();
                    }
                    break;
                // 字符串
                case STRING:
                    cellValue = cell.getStringCellValue();
                    break;
                // Boolean
                case BOOLEAN:
                    cellValue = cell.getBooleanCellValue() + "";
                    break;
                // 公式
                case FORMULA:
                    cellValue = cell.getCellFormula();
                    break;
                // 空值
                case BLANK:
                    cellValue = "";
                    break;
                // 故障
                case ERROR:
                    cellValue = "ERROR VALUE"; // 非法字符
                    break;
                default:
                    cellValue = "UNKNOW VALUE"; // 未知类型
                    break;
            }

        }
        return cellValue;
    }

    /**
     * 根据实体成员变量的类型得到成员变量的值
     * @param realValue
     * @param fields
     * @param f
     * @param cellValue
     * @return
     * @see [类、类#方法、类#成员]
     */
    private static Object getEntityMemberValue(Object realValue, Field[] fields, int f, String cellValue) {
        String type = fields[f].getType().getName();
        switch (type) {
            case "char":
            case "java.lang.Character":
            case "java.lang.String":
                realValue = cellValue;
                break;
            case "java.util.Date":
                realValue = StringUtils.isBlank(cellValue) ? null : DateUtils.strToDate(cellValue, DateUtils.YYYY_MM_DD);
                break;
            case "java.lang.Integer":
                realValue = StringUtils.isBlank(cellValue) ? null : Integer.valueOf(cellValue);
                break;
            case "int":
            case "float":
            case "double":
            case "java.lang.Double":
            case "java.lang.Float":
            case "java.lang.Long":
            case "java.lang.Short":
            case "java.math.BigDecimal":
                realValue = StringUtils.isBlank(cellValue) ? null : new BigDecimal(cellValue);
                break;
            default:
                break;
        }
        return realValue;
    }

    /**
     * 根据路径或文件名选择Excel版本
     * @param filePathOrName
     * @param in
     * @return
     * @throws IOException
     * @see [类、类#方法、类#成员]
     */
    public static Workbook chooseWorkbook(String filePathOrName, InputStream in) throws IOException
    {
        // 根据版本选择创建Workbook的方式
        Workbook wb = null;
        boolean isExcel2003 = ExcelVersionUtil.isExcel2003(filePathOrName);

        if (isExcel2003) {
            wb = new HSSFWorkbook(in);
        } else {
            wb = new XSSFWorkbook(in);
        }
        return wb;
    }

    public static class ExcelVersionUtil {
        /**
         * 是否是2003的excel，返回true是2003
         * @param filePath
         * @return
         * @see [类、类#方法、类#成员]
         */
        public static boolean isExcel2003(String filePath) {
            return filePath.matches("^.+\\.(?i)(xls)$");
        }

        /**
         * 是否是2007的excel，返回true是2007
         * @param filePath
         * @return
         * @see [类、类#方法、类#成员]
         */
        public static boolean isExcel2007(String filePath) {
            return filePath.matches("^.+\\.(?i)(xlsx)$");
        }

    }

    public static class DateUtils {
        // ======================日期格式化常量=====================//
        public static final String YYYY_MM_DDHHMMSS = "yyyy-MM-dd HH:mm:ss";
        public static final String YYYY_MM_DD = "yyyy-MM-dd";
        public static final String YYYY_MM = "yyyy-MM";
        public static final String YYYY = "yyyy";
        public static final String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";
        public static final String YYYYMMDD = "yyyyMMdd";
        public static final String YYYYMM = "yyyyMM";
        public static final String YYYYMMDDHHMMSS_1 = "yyyy/MM/dd HH:mm:ss";
        public static final String YYYY_MM_DD_1 = "yyyy/MM/dd";
        public static final String YYYY_MM_1 = "yyyy/MM";

        /**
         * 自定义取值，Date类型转为String类型
         * @param date 日期
         * @param pattern 格式化常量
         * @return java.lang.String
         * @see [类、类#方法、类#成员]
         */
        public static String dateToStr(Date date, String pattern) {
            SimpleDateFormat format = null;
            if (null == date) {
                return null;
            }
            format = new SimpleDateFormat(pattern, Locale.getDefault());
            return format.format(date);
        }

        /**
         * 将字符串转换成Date类型的时间
         * @param s 日期类型的字符串
         * @param pattern 格式化常量
         * @return java.util.Date
         */
        public static Date strToDate(String s, String pattern) {
            if (s == null) {
                return null;
            }
            Date date = null;
            SimpleDateFormat sdf = new SimpleDateFormat(pattern);
            try {
                date = sdf.parse(s);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return date;
        }
    }
}
