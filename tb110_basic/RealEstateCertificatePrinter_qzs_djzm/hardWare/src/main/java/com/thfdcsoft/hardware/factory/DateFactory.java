package com.thfdcsoft.hardware.factory;

import java.text.DateFormat;
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

}
