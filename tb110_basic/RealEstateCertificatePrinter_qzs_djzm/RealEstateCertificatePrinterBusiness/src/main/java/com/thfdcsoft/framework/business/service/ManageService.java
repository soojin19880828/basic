package com.thfdcsoft.framework.business.service;

import com.thfdcsoft.framework.business.entity.UsageRecord;

public interface ManageService {

//	UsageRecord queryRecord(String recordId);

	String post2Manage(String url, String requestJson);

	<T> T post2ManageAndTrans(String url, String requestJson, Class<T> tClass);

}
