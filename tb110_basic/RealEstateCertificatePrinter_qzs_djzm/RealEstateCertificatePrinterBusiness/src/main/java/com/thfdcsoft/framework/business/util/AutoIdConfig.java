package com.thfdcsoft.framework.business.util;

import com.thfdcsoft.framework.common.number.IAutoInNoConfig;

/**
 * 自增长主键配置
 * 
 * @author 张嘉琪
 * @date 2017年11月29日上午9:49:48
 */
public final class AutoIdConfig implements IAutoInNoConfig {

	private static int INITIAL = 1;

	private static int MAX = 999999999;

	private static int ROLLING_INTERVAL = 1000;

	@Override
	public int getInitial() {
		return AutoIdConfig.INITIAL;
	}

	@Override
	public int getMax() {
		return AutoIdConfig.MAX;
	}

	@Override
	public int getRollingInterval() {
		return AutoIdConfig.ROLLING_INTERVAL;
	}
}
