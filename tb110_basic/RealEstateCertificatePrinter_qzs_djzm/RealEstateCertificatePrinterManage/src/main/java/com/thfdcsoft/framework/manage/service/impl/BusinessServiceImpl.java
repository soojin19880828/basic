package com.thfdcsoft.framework.manage.service.impl;

import com.thfdcsoft.framework.common.date.DateFormatConstant;
import com.thfdcsoft.framework.common.date.DateFormatFactory;
import com.thfdcsoft.framework.common.file.FileFactory;
import com.thfdcsoft.framework.common.image.ImageFactory;
import com.thfdcsoft.framework.common.json.jackson.JacksonFactory;
import com.thfdcsoft.framework.manage.constant.RecordConstant;
import com.thfdcsoft.framework.manage.dao.PrintRecordMapper;
import com.thfdcsoft.framework.manage.dao.TerminalInfoMapper;
import com.thfdcsoft.framework.manage.dao.UsageRecordMapper;
import com.thfdcsoft.framework.manage.dto.QueryPaperReq;
import com.thfdcsoft.framework.manage.entity.*;
import com.thfdcsoft.framework.manage.service.BusinessService;
import com.thfdcsoft.framework.manage.util.AutoIdFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.io.File;
import java.util.List;

@Service
@Transactional
public class BusinessServiceImpl implements BusinessService {

	@Autowired
	private UsageRecordMapper usageRecord;

	@Autowired
	private PrintRecordMapper printRecordMapper;
	
	@Autowired
	private TerminalInfoMapper terminalInfoMapper;
	
	@Override
	public UsageRecord savaUsageRecord(SubmitUsageReq usage) {
		// 1.保存代理人身份证照片及现场照片
		boolean flag = false;
		String recordId = AutoIdFactory.getAutoId();
		String userIdPicPath = RecordConstant.FileDir.ID_PIC_DIR + recordId + RecordConstant.SuffixName.JPG;
		flag = ImageFactory.generateImage(usage.getIdPic(), userIdPicPath);
		if (!flag) {
			return null;
		}
		String userDetPicPath = RecordConstant.FileDir.DET_PIC_DIR + recordId + RecordConstant.SuffixName.JPG;
		flag = ImageFactory.generateImage(usage.getDetPic(), userDetPicPath);
		if (!flag) {
			return null;
		}
		// 2.生成代理人操作日志文件
		String userLogPath = RecordConstant.FileDir.LOG_DIR + recordId + RecordConstant.SuffixName.LOG;
		File log = FileFactory.buildFile(userLogPath);
		if (!log.exists()) {
			return null;
		}
		UsageRecord record = new UsageRecord();
		record.setRecordId(recordId);
		record.setDeviceNumber(usage.getDeviceNumber());
		record.setUserIdnumber(usage.getIdNumber());
		record.setUserFullname(usage.getFullName());
		record.setUserIdPicPath(userIdPicPath);
		record.setUserDetPicPath(userDetPicPath);
		record.setUserLogPath(userLogPath);
		record.setUsageTime(
				DateFormatFactory.getCurrentDateString(DateFormatConstant.SymbolFormat.YYYY_MM_DD_HH_MM_SS));
		int n = usageRecord.insertSelective(record);
		if(n == 1) {
			return record;
		}
		return null;
	}

	@Override
	public TerminalInfo queryPaper(String deviceNum) {
		TerminalInfo terminal = terminalInfoMapper.selectByDeployNumber(deviceNum);
		if (terminal != null) {
			return terminal;
		} else {
			return null;
		}
	}

	@Override
	public TerminalInfo queryPaperByTerminalId(QueryPaperReq queryPaperReq) {
		TerminalInfoExample example = new TerminalInfoExample();
		TerminalInfoExample.Criteria criteria = example.createCriteria();
		criteria.andDeployNumberEqualTo(queryPaperReq.getDeviceNumber());
		criteria.andTerminalIdEqualTo(queryPaperReq.getTerminalId());
		List<TerminalInfo> terminalList = terminalInfoMapper.selectByExample(example);
		if (!CollectionUtils.isEmpty(terminalList)) {
			return terminalList.get(0);
		} else {
			return null;
		}
	}

	@Override
	public UsageRecord selectByRecordId(String recordId) {
	UsageRecord record = usageRecord.selectByPrimaryKey(recordId);
		return record;
	}

	@Override
	public List<PrintRecord> selectPrintRecordListByIdNumber(String idNumber) {
		return printRecordMapper.selectPrintRecordListByIdNumber(idNumber);
	}


}
