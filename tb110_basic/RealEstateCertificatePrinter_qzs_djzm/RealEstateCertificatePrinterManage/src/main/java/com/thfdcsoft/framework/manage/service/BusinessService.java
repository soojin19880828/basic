package com.thfdcsoft.framework.manage.service;

import com.thfdcsoft.framework.manage.dto.QueryPaperReq;
import com.thfdcsoft.framework.manage.entity.PrintRecord;
import com.thfdcsoft.framework.manage.entity.SubmitUsageReq;
import com.thfdcsoft.framework.manage.entity.TerminalInfo;
import com.thfdcsoft.framework.manage.entity.UsageRecord;

import java.util.List;

public interface BusinessService {
	
	/**
	 * 保存客户端传来的使用记录数据
	 */
	public UsageRecord savaUsageRecord(SubmitUsageReq usage);

	/**
	 * 获取设备剩余纸张数量
	 */
	public TerminalInfo queryPaper(String deviceNum);

	public TerminalInfo queryPaperByTerminalId(QueryPaperReq queryPaperReq);

	/**
	 * 通过recordId查询使用记录
	 *
	 * @param recordId 记录编号
	 * @return 使用记录
	 */
	UsageRecord selectByRecordId(String recordId);

	/**
	 * 通过身份证号查询打印记录
	 *
	 * @author ww
	 * @date 2019年5月18日 13:12:59
	 * @param idNumber 身份证号
	 * @return 打印记录列表
	 */
	List<PrintRecord> selectPrintRecordListByIdNumber(String idNumber);
	
}
