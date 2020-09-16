package com.thfdcsoft.framework.business.util;

import java.util.Date;

import com.thfdcsoft.framework.business.contant.StaticConstant;
import com.thfdcsoft.framework.common.date.DateFormatFactory;

/**
 * 日志记录工具类
 * 
 * @author 张嘉琪
 *
 */
public class RecordUtil {
	
	private static final String DATA_PATH_FORMAT = "yyyy/MM/dd/HHmm_";
	
	public static String getRecordPath(Date date, String deviceNumber, String idNumber) {
		StringBuilder recordPath = new StringBuilder(StaticConstant.RECORD_ROOT);
		recordPath.append(deviceNumber).append("/");
		recordPath.append(DateFormatFactory.dateToDateString(date, DATA_PATH_FORMAT));
		recordPath.append(idNumber);
		return recordPath.toString();
	}
}
