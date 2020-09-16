package com.whminwei.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 时间日期格式化工厂类
 *
 * @author 张嘉琪
 * @date 2017年9月18日 下午3:49:28
 */
public final class DateFactory {

    // 初始化Dataformat
    private static Map<String, DateFormat> dateFormatMap = new HashMap<String, DateFormat>();

    /**
     * 获取DateFormat
     *
     * @param dateTimeStr
     * @param formatStr
     * @return
     */
    public static DateFormat getDateFormat(String formatStr) {
        DateFormat df = dateFormatMap.get(formatStr);
        if (df == null) {
            df = new SimpleDateFormat(formatStr);
            dateFormatMap.put(formatStr, df);
        }
        return df;
    }

    /**
     * 将Date转换成formatStr格式的字符串
     *
     * @param date
     * @param formatStr
     * @return
     */
    public static String dateToDateString(Date date, String formatStr) {
        DateFormat df = getDateFormat(formatStr);
        return df.format(date);
    }

    /**
     * 获取formatStr格式的当前时间字符串
     *
     * @param formatStr
     * @return
     */
    public static String getCurrentDateString(String formatStr) {
        return dateToDateString(new Date(), formatStr);
    }

    /**
     * 根据时间字符串获取date
     * SimpleDateFormat("yyyy-MM-dd");
     *
     * @param dateStr
     * @return
     */
    public static Date getDate(String dateStr) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = sdf.parse(dateStr);
            return date;
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据时间获取星期几
     *
     * @param date
     * @return
     */
    public static String getWeekOfDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
        String week = sdf.format(date);
        return week;
    }

    /**
     * 根据时间获取年
     * 2019年5月5日 10点44分
     * 田伟
     *
     * @param date
     * @return
     */
    public static String getYearOfDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        String year = sdf.format(date);
        return year;
    }

    /**
     * 根据时间获取月
     * 2019年5月5日 10点44分
     * 田伟
     *
     * @param date
     * @return
     */
    public static String getMonthOfDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM");
        String month = sdf.format(date);
        return month;
    }

    /**
     * 根据时间获取日
     * 2019年5月5日 10点44分
     * 田伟
     *
     * @param date
     * @return
     */
    public static String getDayOfDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd");
        String day = sdf.format(date);
        return day;
    }


    /**
     * 根据指定的Dataformat 和指定的dateStr 与当前日期进行比较
     * 指定日期未过期返回true
     *
     * @param dateStr
     * @param Dataformat
     * @return
     */
    public static boolean checkDate(String dateStr, String Dataformat) {
        SimpleDateFormat sdf = new SimpleDateFormat(Dataformat);
        try {
            if ("长期".equalsIgnoreCase(dateStr)) {
				return true;
            } else {
				Date date = sdf.parse(dateStr);
				Date now = new Date();

				if (date.getTime() > now.getTime()) {
					return true;
				}
			}
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

}
