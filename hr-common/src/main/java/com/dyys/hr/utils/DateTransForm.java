package com.dyys.hr.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 业务系统 时间与时间戳相互转换
 *
 * @author sy yang
 * @date 2019/7/1 0:53
 **/
public class DateTransForm {
    /**
     * yyyy-MM-dd
     */
    public final static String YYYY_MM_DD = "yyyy-MM-dd";

    /*
     * 将时间转换为时间戳
     */
    public static Long dateToStamp(Date date) {
        Long ts = 0L;
        ts = date.getTime();
        return ts / 1000;
    }

    /*
     * 将时间转换为时间格式
     */
    public static String getDateToStr(Date date) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String str = format.format(date);
        return str;
    }

    /**
     * 返回日期格式(yyyy-MM-dd)
     *
     * @param date
     * @return
     */
    public static String getDate(Date date) {
        SimpleDateFormat gSimpleDateFormatI = new SimpleDateFormat(
                "yyyy-MM-dd");
        return gSimpleDateFormatI.format(date);
    }

    /**
     * <P>获取指定日期前几天或后几天</P>
     *
     * @param beginDate
     * @param days
     * @return
     */
    public static Date getAddDays(Date beginDate, int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(beginDate);
        calendar.add(Calendar.DAY_OF_YEAR, days);
        Date date = calendar.getTime();
        date = clearDateTimes(date);
        return date;
    }

    /**
     * 对日期的时间清零，返回yyyy-MM-dd
     *
     * @param date
     * @return
     */
    public static Date clearDateTimes(Date date) {
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        Date newDate = java.sql.Date.valueOf(localDate);
        return newDate;
    }

    /*
     * 将时间转换为时间戳
     */
    public static String dateToStamp(String s) throws ParseException {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = simpleDateFormat.parse(s);
        long ts = date.getTime() / 1000;
        res = String.valueOf(ts);
        return res;
    }

    /*
     * 将时间转换为时间戳
     */
    public static Long strToLong(String s) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = simpleDateFormat.parse(s);
        long ts = date.getTime();
        return ts / 1000;
    }

    /*
     * 将时间戳转换为时间
     */
    public static String stampToDate(Long time) {
        String res = null;
        if (time == null || time.intValue() == 0) {
            return res;
        }
        if (time.toString().length() == 10) {
            time = time * 1000;
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        res = simpleDateFormat.format(time);
        return res;
    }

    /*
     * 获取传入日期所在月的最后一天
     */
    public static Date getLastDayOfMonth(final Date date) {
        final Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        final int last = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        cal.set(Calendar.DAY_OF_MONTH, last);
        return cal.getTime();
    }

    /*
     * 获取到秒的时间戳
     */
    public static Long getCurrentTimeMillis() {
        return System.currentTimeMillis() / 1000;
    }

    /**
     * <p>
     * 将字符串类型的日期格式 转换为 符合要求的 Date类型的日期格式
     * </P>
     *
     * @param date
     * @return
     */
    public static Date getDate4StrDate(String date) {
        if (date == null || "".equals(date.trim())) {
            return null;
        } else {
            SimpleDateFormat df = new SimpleDateFormat(YYYY_MM_DD);
            try {
                return df.parse(date);
            } catch (ParseException e) {
                return null;
            }
        }
    }

    /**
     * <p>
     * 返回当前传入时间获取当月最后一天时间戳
     * </P>
     *
     * @param date
     * @return
     */
    public static Long getLastTimeMillis(String date) {
        Date theDate = getDate4StrDate(date);
        theDate = getLastDayOfMonth(theDate);
        return theDate.getTime() / 1000;
    }

    /*
     * 字符串去重
     */
    public static String strWay(String text) {
        String[] str = text.split(",");
        if(str.length == 0) {
            return null;
        }
        List<String> list = new ArrayList<String>();
        StringBuffer sb = new StringBuffer();
        for(int i = 0;i < str.length; i++) {
            if(!list.contains(str[i])){
                list.add(str[i]);
                sb.append(str[i]+",");
            }
        }
        return sb.toString().substring(0,sb.toString().length()-1);
    }
}
