package com.thfdcsoft.framework.business.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.thfdcsoft.framework.business.contant.StaticConstant;
import com.thfdcsoft.framework.business.entity.UsageRecord;
import com.thfdcsoft.framework.business.service.ManageService;
import com.thfdcsoft.framework.common.http.HttpClientFactory;
import com.thfdcsoft.framework.common.http.def.DefHttpClientConfig;
import com.thfdcsoft.framework.common.http.def.DefHttpClientFactory;
import com.thfdcsoft.framework.common.http.dto.BaseHttpRspn;
import com.thfdcsoft.framework.common.json.jackson.JacksonFactory;

@Service
public class ManageServiceImpl implements ManageService {

	private static final Logger logger = LoggerFactory.getLogger(ManageServiceImpl.class);

//	@Override
//	public UsageRecord queryRecord(String recordId) {
//		String manageurl = StaticConstant.MANAGE_URL + "business/queryRecord";
//		logger.info(manageurl);
//		try {
//			String respValue = DefHttpClientFactory.getInstance().doPost(recordId, manageurl);
//			BaseHttpRspn resp = JacksonFactory.readJson(respValue, BaseHttpRspn.class);
//			if(!resp.isResult()) {
//				logger.info("使用记录查询出现异常");
//				//返回查询失败页面
//				return null;
//			}else {
//				UsageRecord record = JacksonFactory.readJson(resp.getRespJson(), UsageRecord.class);
//				return record;
//			}
//		} catch (Exception e) {
//			System.out.println(e);
//			e.printStackTrace();
//		}
//		return null;
//	}

	/**
	 * 请求Manage
	 *
	 * @author ww
	 * @date 2019年5月15日 09:21:40
	 * @param url 请求地址(方法内已加StaticConstant.MANAGE_URL)
	 * @param requestJson 请求的json字符串
	 * @return manage返回的数据(若为null则代表发生异常)
	 */
	@Override
	public String post2Manage(String url, String requestJson) {
		try {
			System.out.println(StaticConstant.MANAGE_URL + url);
			return DefHttpClientFactory.getInstance().doPost(requestJson, StaticConstant.MANAGE_URL + url);
		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 请求Manage并转换为指定类型
	 *
	 * @author ww
	 * @date 2019年5月15日 09:21:55
	 * @param url 请求地址(方法内已加StaticConstant.MANAGE_URL)
	 * @param requestJson 请求的json字符串
	 * @param tClass 返回的类型
	 * @return manage返回的数据(若为null则代表发生异常)
	 *
	 */
    @Override
	public <T> T post2ManageAndTrans(String url, String requestJson, Class<T> tClass) {
        String str = post2Manage(url, requestJson);
        return JacksonFactory.readJson(str, tClass);
    }


	
}
