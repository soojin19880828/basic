package com.whminwei.util;

/**
 * String操作工具类
 * @author 张嘉琪
 *
 */
public class StringUtils {
	
	public static boolean isNullOrEmpty(String str) {
		if((str == null) || str.isEmpty()) {
			return true;
		}
		return false;
	}
	
	public static boolean isNotNullAndEmpty(String str) {
		if((str != null) && !str.isEmpty()) {
			return true;
		}
		return false;
	}

}
