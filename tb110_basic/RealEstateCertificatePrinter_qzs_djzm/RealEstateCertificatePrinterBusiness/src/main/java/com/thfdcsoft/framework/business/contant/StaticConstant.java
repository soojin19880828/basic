package com.thfdcsoft.framework.business.contant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class StaticConstant {

	// 领证代理人记录根目录
	public static String RECORD_ROOT;
	//manage地址
	public static String MANAGE_URL;

	public static String RUNMODE;

	public static String GET_DJZM_ESTATE_URL;

	public static String DJZM_WRITE_BACK_URL;

	public static String GET_QZS_ESTATE_URL;

	public static String QZS_WRITE_BACK_URL;

	@Value("${record.root}")
	public void setRECORD_ROOT(String RECORD_ROOT) {
		StaticConstant.RECORD_ROOT = RECORD_ROOT;
	}

	@Value("${manage.url}")
	public void manageUrl(String MANAGE_URL) {
		StaticConstant.MANAGE_URL = MANAGE_URL;
	}

	@Value("${runMode}")
	public void runMode(String RUNMODE) {
		StaticConstant.RUNMODE = RUNMODE;
	}

	@Value("${getDjzmEstateUrl}")
	public void getDjzmEstateUrl(String getDjzmEstateUrl) {
		StaticConstant.GET_DJZM_ESTATE_URL = getDjzmEstateUrl;
	}

	@Value("${djzmWriteBackUrl}")
	public void djzmWriteBackUrl(String djzmWriteBackUrl) {
		StaticConstant.DJZM_WRITE_BACK_URL = djzmWriteBackUrl;
	}

	@Value("${getQzsEstateUrl}")
	public void getQzsEstateUrl(String getQzsEstateUrl) {
		StaticConstant.GET_QZS_ESTATE_URL = getQzsEstateUrl;
	}

	@Value("${qzsWriteBackUrl}")
	public void qzsWriteBackUrl(String qzsWriteBackUrl) {
		StaticConstant.QZS_WRITE_BACK_URL = qzsWriteBackUrl;
	}


}
