package com.thfdcsoft.framework.business.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;

public class CheckIsNullFactory {

	private static Logger logger = LoggerFactory.getLogger(CheckIsNullFactory.class);

	/**
	 * 判断对象所有属性是否有空值
	 * 
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 */
	public boolean CheckObjectProperyIsNull(Object obj) {
		boolean flag = false;
		for (Field f : obj.getClass().getDeclaredFields()) {
			/** 若是私有属性，必须设置 */
			f.setAccessible(true);
			try {
				if (f.get(obj) == null || "".equals(f.get(obj).toString().trim())) {
					// 判断字段是否为空，并且对象属性中的基本都会转为对象类型来判断
					logger.info("校验对象数据： " + obj.toString());
					logger.info("为空字段名称： " + f.getName());
					flag = true;
					break;
				}
			} catch (IllegalArgumentException | IllegalAccessException e) {
				logger.error("数据非空校验异常,", e);
			}
		}
		return flag;
	}
}
