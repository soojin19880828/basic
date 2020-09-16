package com.thfdcsoft.framework.manage.util;

import com.thfdcsoft.framework.common.date.DateFormatConstant;
import com.thfdcsoft.framework.common.date.DateFormatFactory;
import com.thfdcsoft.framework.common.number.AutoInNoGenerator;
import com.thfdcsoft.framework.common.number.IAutoInNoConfig;
import com.thfdcsoft.framework.common.number.def.DefAutoInNoConfig;

/**
 * 自增长主键生成器
 * 
 * @author 张嘉琪
 * @date 2017年11月29日上午9:50:04
 */
public final class AutoIdFactory {

	private static AutoInNoGenerator generator;

	/**
	 * 获取20位自增号码<br>
	 * 格式：时间日期[14位|yyyyMMddHHmmss]+自增编号[6位]
	 * 
	 * @return
	 */
	public static String getAutoId() {
		if (generator == null) {
			IAutoInNoConfig config = new DefAutoInNoConfig();
			generator = new AutoInNoGenerator(config);
		}
		StringBuilder sb = new StringBuilder(
				DateFormatFactory.getCurrentDateString(DateFormatConstant.SimpleFormat.YYYY_MM_DD_HH_MM_SS));
		sb.append(generator.next(6));
		return sb.toString();
	}
}
