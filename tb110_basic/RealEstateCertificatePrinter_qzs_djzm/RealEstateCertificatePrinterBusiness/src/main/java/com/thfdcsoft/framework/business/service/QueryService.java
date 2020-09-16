package com.thfdcsoft.framework.business.service;

import com.thfdcsoft.gov.dto.qzs.HandleResult;
import com.thfdcsoft.framework.business.entity.UsageRecord;

public interface QueryService {

	/**
	 * 查询不动产
	 *
	 * @param reocrd 使用记录
	 * @return FullRealEstateInfo的json字符串
	 */
	HandleResult queryRealEstate(UsageRecord reocrd);

}
