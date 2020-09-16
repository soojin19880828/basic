package com.thfdcsoft.framework.business.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.thfdcsoft.gov.dto.qzs.HandleResult;
import com.thfdcsoft.gov.service.IConnectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thfdcsoft.framework.business.entity.UsageRecord;
import com.thfdcsoft.framework.business.service.QueryService;
import com.thfdcsoft.framework.common.file.FileFactory;
import com.thfdcsoft.framework.common.json.jackson.JacksonFactory;

@Service
public class QueryServiceImpl implements QueryService {

	@Autowired
	private IConnectService connectService;

	@Override
	public HandleResult queryRealEstate(UsageRecord usageRecord) {
		//将查询出的使用记录写入日志
		File textFile = new File(usageRecord.getUserLogPath());
		String content = "查询使用记录：" + JacksonFactory.writeJson(usageRecord);
		FileFactory.writeTXTFile(textFile, content);
		//查询不动产权证书信息
		HandleResult result = connectService.getRealEstateListByIdNumberAndName(usageRecord);
		return result;
	}
}
