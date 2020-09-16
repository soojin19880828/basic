package com.thfdcsoft.framework.manage.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thfdcsoft.framework.common.date.DateFormatConstant;
import com.thfdcsoft.framework.common.date.DateFormatFactory;
import com.thfdcsoft.framework.common.http.dto.HttpReturnDto;
import com.thfdcsoft.framework.common.image.ImageFactory;
import com.thfdcsoft.framework.manage.constant.RecordConstant;
import com.thfdcsoft.framework.manage.constant.StaticConstant;
import com.thfdcsoft.framework.manage.dao.UsageRecordMapper;
import com.thfdcsoft.framework.manage.dto.*;
import com.thfdcsoft.framework.manage.entity.*;
import com.thfdcsoft.framework.manage.util.AutoIdFactory;
import com.thfdcsoft.framework.manage.util.HttpUtil;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.thfdcsoft.framework.common.http.dto.BaseHttpRspn;
import com.thfdcsoft.framework.common.json.jackson.JacksonFactory;
import com.thfdcsoft.framework.manage.dao.PrintRecordMapper;
import com.thfdcsoft.framework.manage.dao.TerminalInfoMapper;
import com.thfdcsoft.framework.manage.service.BusinessService;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 接收业务层请求 查询数据
 * @author weaso
 *
 */
@Controller
@RequestMapping("/business")
public class BusinessController extends BaseController {

	@Autowired
	private BusinessService businessService;
	
	@Autowired
	private TerminalInfoMapper terminalInfoMapper;
	
	@Autowired
	private PrintRecordMapper printRecordMapper;

	@Autowired
	private UsageRecordMapper usageRecordMapper;

	/**
	 * 通过recordId查询使用记录
	 *
	 * @param data
	 * @return
	 */
	@RequestMapping(value = "/selectByDeployNumberAndTernimalId", method = RequestMethod.POST)
	@ResponseBody
	public String selectByDeployNumber(@RequestBody String data) {
		System.out.println("接收到business传递的参数=" + data);
		BaseHttpRspn result = new BaseHttpRspn();
		result.setResult(false);
		
		ChkRemainingReq remain = JacksonFactory.readJson(data, ChkRemainingReq.class);
		
		TerminalInfo terminal = this.terminalInfoMapper.selectByDeployNumberAndTernimalId(remain.getDeviceNumber(),remain.getTerminalId());
		if (terminal != null) {
			result.setRespJson(terminal.getRemainingPaper());
			result.setResult(true);
		}
		return JacksonFactory.writeJson(result);
	}

	/**
	　　* @description: 获取某一台机器的terminalInfoList
	　　* @params
	　　* @return
	　　* @author 高拓
	　　* @date 2020/9/7 11:18
	　　*/
	@RequestMapping(value = "/listByDeployNumber", method = RequestMethod.POST)
	@ResponseBody
	public String listByDeployNumber(@RequestBody String data) {
		System.out.println("接收到business传递的参数=" + data);
		BaseHttpRspn result = new BaseHttpRspn();
		TerminalInfoExample terminalInfoExample = new TerminalInfoExample();
		TerminalInfoExample.Criteria criteria = terminalInfoExample.createCriteria();
		criteria.andDeployNumberEqualTo(data);
		List<TerminalInfo> terminalInfoList = this.terminalInfoMapper.selectByExample(terminalInfoExample);
		if(!CollectionUtils.isEmpty(terminalInfoList)) {
			result.setRespJson(JacksonFactory.writeJson(terminalInfoList));
			result.setResult(true);
		}
		return JacksonFactory.writeJson(result);
	}

	/**
	 * 通过不动产单元号查询打印记录
	 * @param request
	 * @param response
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/getQzsRecordsByCertNums", method = RequestMethod.POST)
	@ResponseBody
	public String getQzsRecordsByCertNums(HttpServletRequest request, HttpServletResponse response,
									  @RequestBody PrintRecordBycerNumReq req) throws UnsupportedEncodingException {
		// 获取传入的不动产单元号集合
		List<String> certNumList = req.getCerNumsList();
		List<PrintRecord> hasPrintList = this.printRecordMapper.getQzsRecordsByCertNums(certNumList);
		PrintRecordRsp result = new PrintRecordRsp(false);
		if (hasPrintList.size() > 0) {
			result.setResult(true);
			result.setPrintList(hasPrintList);
		}
		return JacksonFactory.writeJson(result);
	}

	/**
	 * 通过recordId查询使用记录
	 */
	@RequestMapping(value = "/queryRecord", method = RequestMethod.POST)
	@ResponseBody
	public String queryRecord(HttpServletRequest request, HttpServletResponse response, @RequestBody String recordId) {
		System.out.println("接收到business传递的参数=" + recordId);
		BaseHttpRspn result = new BaseHttpRspn();
		result.setResult(false);
		
		UsageRecord record = businessService.selectByRecordId(recordId);
		if(record != null) {
			result.setResult(true);
			String recordJson = JacksonFactory.writeJson(record);
			result.setRespJson(recordJson);
		}
		return JacksonFactory.writeJson(result);
	}
	
	/**
	 * 设备权证书数量减一
	 *
	 * @param request
	 * @param response
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/updatePaper", method = RequestMethod.POST)
	@ResponseBody
	public String updatePaper(HttpServletRequest request, HttpServletResponse response,@RequestBody SubmitPrintReq req) {
		BaseHttpRspn result = new BaseHttpRspn(false);
		
		UsageRecord record = businessService.selectByRecordId(req.getUsageId());
		
		int res = 0;
		if(record != null) {
			TerminalInfo terminal = this.terminalInfoMapper.selectByDeployNumberAndTernimalId(record.getDeviceNumber(),req.getTerminalId());
			if(terminal != null) {
				int remaining = Integer.parseInt(terminal.getRemainingPaper());
				remaining--;
				terminal.setRemainingPaper(String.valueOf(remaining));
				res = this.terminalInfoMapper.updateByPrimaryKeySelective(terminal);
			}
		}
		if(res > 0) {
			result.setResult(true);
		}
		return JacksonFactory.writeJson(result);
	}
	
	/**
	 * 保存打印记录
	 *
	 * @param request
	 * @param response
	 * @param savePrintRecord
	 * @return
	 */
	@RequestMapping(value = "/savePrintRecord", method = RequestMethod.POST)
	@ResponseBody
	public String savePrintRecord(HttpServletRequest request, HttpServletResponse response,@RequestBody String savePrintRecord) {
		BaseHttpRspn result = new BaseHttpRspn(false);
		PrintRecord record = JacksonFactory.readJson(savePrintRecord, PrintRecord.class);
		int r = printRecordMapper.insertSelective(record);
		if(r > 0) {
			result.setResult(true);
			result.setRespJson(savePrintRecord);
		}
		return JacksonFactory.writeJson(result);
	}


	/**
	 *
	 *
	 * @param request
	 * @param response
	 * @param remain
	 * @return
	 */
	@RequestMapping(value = "/updateRemaining", method = RequestMethod.POST)
	@ResponseBody
	public String updateRemaining(HttpServletRequest request, HttpServletResponse response, @RequestBody SubmitRemainingReq remain) {
		BaseHttpRspn result = new BaseHttpRspn(false);
		System.out.println(remain.getDeviceNumber());
		
		TerminalInfo terminal = this.terminalInfoMapper.selectByDeployNumberAndTernimalId(remain.getDeviceNumber(),remain.getTerminalId());
		if (terminal != null) {
			terminal.setRemainingPaper(remain.getRemaining());
			int res = this.terminalInfoMapper.updateByPrimaryKeySelective(terminal);
			if (res > 0) {
				result.setResult(true);
			}
		}	
		return JacksonFactory.writeJson(result);
	}

	/**
	 * 获取使用信息
	 *
	 * @author ww
	 * @date 2019年5月14日 15:03:02
	 * @param usageId
	 * @return
	 */
	@RequestMapping(value = "/getUsageRecord", method = RequestMethod.POST)
	@ResponseBody
	public String getUsageRecord(@RequestBody String usageId){
		return JacksonFactory.writeJson(usageRecordMapper.selectByPrimaryKey(usageId));
	}

	/**
	 * 获取设备信息
	 *
	 * @author ww
	 * @date 2019年5月14日 15:02:55
	 * @param submitPrintReq
	 * @return
	 */
	@RequestMapping(value = "/getTerminalInfo", method = RequestMethod.POST)
	@ResponseBody
	public String getTerminalInfo(@RequestBody SubmitPrintReq submitPrintReq){
		UsageRecord usageRecord = usageRecordMapper.selectByPrimaryKey(submitPrintReq.getUsageId());
		return JacksonFactory.writeJson(terminalInfoMapper.selectByDeployNumberAndTernimalId(usageRecord.getDeviceNumber(),submitPrintReq.getTerminalId()));
	}

	/**
	 * 更新回传状态
	 *
	 * @author ww
	 * @date 2019年5月14日 15:02:44
	 * @param printRecord_str 打印记录
	 * @return
	 */
	@RequestMapping(value = "/updateTranStatus", method = RequestMethod.POST)
	@ResponseBody
	public Integer updateTranStatus(@RequestBody String printRecord_str){
		PrintRecord printRecord = JacksonFactory.readJson(printRecord_str, PrintRecord.class);
		return printRecordMapper.updateByPrimaryKeySelective(printRecord);
	}

	/**
	 * 通过身份证号查询打印记录
	 *
	 * @author ww
	 * @date 2019年5月17日 18:18:51
	 * @param idNumber 身份证号
	 * @return
	 */
	@RequestMapping(value = "/selectPrintRecordListByIdNumber", method = RequestMethod.POST)
	@ResponseBody
	public List<PrintRecord> selectPrintRecordListByIdNumber(@RequestBody String idNumber){
		return businessService.selectPrintRecordListByIdNumber(idNumber);
	}
	
	//===================================== 客户端直接请求Manage的方法移到业务层由业务层请求Manage ========================================
	
	/**
	 * 业务系统请求保存超级密码
	 * 
	 * @param request
	 * @param response
	 * @param num
	 * @return
	 */
	@RequestMapping(value = "/supperlogin", method = RequestMethod.POST)
	@ResponseBody
	public String supperlogin(HttpServletRequest request, HttpServletResponse response,@RequestBody String num) {		
		System.out.println("获取的超级密码为:"+num);
		     			   
	    TerminalInfo terminalInfo = new TerminalInfo();
	    terminalInfo.setDeployNumber("zzdy1hj");
	    terminalInfo.setExtendField(num);
	   
	    BaseHttpRspn result = new BaseHttpRspn(false);
	    
	    int u = terminalInfoMapper.updateBydeploy_number(terminalInfo);
//		String writeJson = JacksonFactory.writeJson(terminalInfo);
	  
	    if(u==2) {
	    	 result.setResult(true);	
	    	 result.setRespJson(num);
	     }else {
	    	 result.setResult(false);
	    	 result.setRespMsg("数据库添加失败!");
	     }				
		return JacksonFactory.writeJson(result);
	}
	
	/**
	 * 业务系统请求保存使用记录
	 * @param request
	 * @param response
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/saveUsageRecord", method = RequestMethod.POST)
	@ResponseBody
	public String saveUsageRecord(HttpServletRequest request, HttpServletResponse response,@RequestBody SubmitUsageReq req) {
		BaseHttpRspn result = new BaseHttpRspn(false);
		UsageRecord record = businessService.savaUsageRecord(req);
		if(record != null) {
			result.setResult(true);
			String recordJson = JacksonFactory.writeJson(record);
			result.setRespJson(recordJson);
		}
		return JacksonFactory.writeJson(result);
	}

	/**
	 * 业务系统请求获取剩余纸张数量
	 * @param request
	 * @param response
	 * @param queryPaperReq
	 * @return
	 */
	@RequestMapping(value = "/querypaper", method = RequestMethod.POST)
	@ResponseBody
	public String querypaper(HttpServletRequest request, HttpServletResponse response, @RequestBody QueryPaperReq queryPaperReq) {
		BaseHttpRspn result = new BaseHttpRspn(false);
		// 获取设备剩余纸张数量
		TerminalInfo terminal = businessService.queryPaperByTerminalId(queryPaperReq);
		if(terminal != null) {
			result.setResult(true);
			String terminalJson = JacksonFactory.writeJson(terminal);
			result.setRespJson(terminalJson);
		}
		return JacksonFactory.writeJson(result);
	}

	/**
	 * ------------------登记证明-------------------
	 */

	/**
	 * 获取用户记录
	 * @param request
	 * @param response
	 * @param usageId
	 * @return
	 */
	@RequestMapping(value = "/getUsage", method = RequestMethod.GET)
	@ResponseBody
	public String getUsage(HttpServletRequest request, HttpServletResponse response, @Param("usageId") String usageId) {
		System.out.println("in getUsage............" + usageId);
		UsageRecord usage = this.usageRecordMapper.selectByPrimaryKey(usageId);
		return JacksonFactory.writeJson(usage);
	}

	/**
	 * 通过不动产单元号查询登记证明打印记录
	 * @param request
	 * @param response
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/getDjzmRecordsByCertNums", method = RequestMethod.POST)
	@ResponseBody
	public String getDjzmRecordsByCertNums(HttpServletRequest request, HttpServletResponse response,
									  @RequestBody PrintRecordBycerNumReq req) throws UnsupportedEncodingException {
		// 获取传入的不动产单元号集合
		List<String> certNumList = req.getCerNumsList();
		List<PrintRecord> hasPrintList = this.printRecordMapper.getDjzmRecordsByCertNums(certNumList);
		PrintRecordRsp result = new PrintRecordRsp(false);
		if (hasPrintList.size() > 0) {
			result.setResult(true);
			result.setPrintList(hasPrintList);
		}
		return JacksonFactory.writeJson(result);
	}

	// 打印前查询当天打印数据去重
	@RequestMapping(value = "/printedRecord", method = RequestMethod.POST)
	@ResponseBody
	public String printedRecord(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("in manage printedRecord.....");
		SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd");
		String date = sdf.format(new Date());
		HttpReturnDto result = new HttpReturnDto(false);
		// 获取已经打印的信息
		PrintRecordExample example = new PrintRecordExample();
		PrintRecordExample.Criteria criteria = example.createCriteria();
		if (!date.isEmpty() && date != " ") {
			criteria.andPrintTimeLike("%" + date + "%");
		}
		List<PrintRecord> printRecords = this.printRecordMapper.selectByExample(example);
		if (printRecords.size() > 0) {
			result.setResult(true);
			result.setRespJson(JacksonFactory.writeJson(printRecords));
		}
		System.out.println("out manage printedRecord.....");
		return JacksonFactory.writeJson(result);
	}

	/**
	 * 获取当前记录已打印登记证明，回传不动产数据
	 * @param request
	 * @param response
	 * @param usage
	 * @return
	 */
	@RequestMapping(value = "/hasPrintCompleted", method = RequestMethod.POST)
	@ResponseBody
	public String hasPrintCompleted(HttpServletRequest request, HttpServletResponse response,
									@RequestBody UsageRecord usage) throws UnsupportedEncodingException {
		System.out.println("in hasPrintCompleted............");
		PrintRecordRsp result = new PrintRecordRsp(false);
		// 获取已打印登记证明
		PrintRecordExample example = new PrintRecordExample();
		PrintRecordExample.Criteria criteria = example.createCriteria();
		criteria.andUsageIdEqualTo(usage.getRecordId());
		criteria.andTranStatusBizEqualTo(RecordConstant.TranStatus.WAITING);
		criteria.andPrintTypeEqualTo(RecordConstant.PrintType.PRINT_DJZM);
		List<PrintRecord> printList = this.printRecordMapper.selectByExample(example);
		if (printList.size() > 0) {
			result.setPrintList(printList);
		} else {
			result.setPrintList(null);
		}
		result.setResult(true);
		System.out.println("out hasPrintCompleted............");
		return JacksonFactory.writeJson(result);
	}

	/**
	 * 更新打印状态
	 * @param request
	 * @param response
	 * @param respn
	 */
	@RequestMapping(value = "/completeSubmit", method = RequestMethod.POST)
	@ResponseBody
	public void completeSubmit(HttpServletRequest request, HttpServletResponse response,
							   @RequestBody PrintRecordRsp respn) throws UnsupportedEncodingException {
		// 获取已打印登记证明
		List<PrintRecord> completeSubmit = respn.getPrintList();
		for (PrintRecord complete : completeSubmit) {
			this.printRecordMapper.updateByPrimaryKeySelective(complete);
		}
	}

	/**
	 * 提交终端登记证明打印记录
	 * @param request
	 * @param response
	 * @param cert
	 * @return
	 */
	@RequestMapping(value = "/submitPrintRecord", method = RequestMethod.POST)
	@ResponseBody
	public String submitPrintRecord(HttpServletRequest request, HttpServletResponse response,
									@RequestBody SubmitPrintReq cert) {
		System.out.println("in manage submitPrintRecord...........");
		System.out.println("cert========"+cert);
		HttpReturnDto result = new HttpReturnDto(false);
		// 1.保存不动产登记证明扫描件
		String recordId = AutoIdFactory.getAutoId();
		String certScanPath = RecordConstant.FileDir.CERT_SCAN_DIR + recordId + RecordConstant.SuffixName.JPG;
		boolean flag = ImageFactory.generateImage(cert.getCertScan(), certScanPath);
		if (!flag) {
			result.setRespMsg("登记证明扫描件保存失败！");
			return JacksonFactory.writeJson(result);
		}
		// 2.保存登记证明信息
		PrintRecord print = new PrintRecord();
		print.setRecordId(recordId);
		print.setBizNumber(cert.getBusiNumber());
		print.setCertNumber(cert.getCertNumber());
		print.setCertScanPath(certScanPath);
		// 设置打印类型为登记证明
		print.setPrintType(RecordConstant.PrintType.PRINT_DJZM);
		// 如果识别失败就回传失败
		if ("00000000000".equals(cert.getSequNumber())) {
			print.setTranStatusBiz(RecordConstant.TranStatus.FAILURE);
		} else {
			print.setTranStatusBiz(RecordConstant.TranStatus.WAITING);
		}
		print.setTranStatusWin(RecordConstant.TranStatus.SUCCESS);
		System.out.println("回传不动产.....................");
		print.setUsageId(cert.getUsageId());
		print.setPrintTime(DateFormatFactory.getCurrentDateString(DateFormatConstant.SymbolFormat.YYYY_MM_DD_HH_MM_SS));
		print.setSeqNumber(cert.getSequNumber());
		System.out.println("录入的print====="+print);
		int r = this.printRecordMapper.insertSelective(print);
		if (r > 0) {
			result.setResult(true);
		} else {
			result.setRespMsg("登记证明打印记录保存失败");
		}
		System.out.println("out manage submitPrintRecord...........");
		return JacksonFactory.writeJson(result);
	}

	/**
	 * 打印完修改设备剩余纸张数量
	 * @param request
	 * @param response
	 * @param updatePaperCountReq
	 * @return
	 */
	@RequestMapping(value = "/updateRemainPager", method = RequestMethod.POST)
	@ResponseBody
	public String updateRemainPager(HttpServletRequest request, HttpServletResponse response,
									@RequestBody UpdatePaperCountReq updatePaperCountReq) {
		HttpReturnDto result = new HttpReturnDto(false);
		UsageRecord usage = this.usageRecordMapper.selectByPrimaryKey(updatePaperCountReq.getUsageId());
		int res = 0;
		if (usage != null) {
			TerminalInfo terminal = this.terminalInfoMapper.selectByDeployNumberAndTernimalId(usage.getDeviceNumber(), updatePaperCountReq.getTerminalId());
			if (terminal != null) {
				int remaining = Integer.parseInt(terminal.getRemainingPaper());
				remaining--;
				terminal.setRemainingPaper(String.valueOf(remaining));
				res = this.terminalInfoMapper.updateByPrimaryKeySelective(terminal);
			}
		}
		if (res <= 0) {
			result.setRespMsg("终端设备信息数据异常！");
			return JacksonFactory.writeJson(result);
		} else {
			result.setResult(true);
		}
		return JacksonFactory.writeJson(result);
	}

}