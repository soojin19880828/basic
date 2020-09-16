package com.thfdcsoft.framework.manage.constant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class StaticConstant {

	// 领证代理人记录根目录
	public static String RECORD_ROOT;

	//manage地址
	public static String BUSINESS_URL;

	// 登记证明手动回传地址
	public static String WRITE_BACK_URL;


	@Value("${record.root}")
	public void setRECORD_ROOT(String RECORD_ROOT) {
		StaticConstant.RECORD_ROOT = RECORD_ROOT;
	}

	@Value("${business.url}")
	public void setBusinessUrl(String BUSINESS_URL) {
		StaticConstant.BUSINESS_URL = BUSINESS_URL;
	}

	@Value("${writeBackUrl}")
	public void writeBackUrl(String writeBackUrl) {
		StaticConstant.WRITE_BACK_URL = writeBackUrl;
	}

}
