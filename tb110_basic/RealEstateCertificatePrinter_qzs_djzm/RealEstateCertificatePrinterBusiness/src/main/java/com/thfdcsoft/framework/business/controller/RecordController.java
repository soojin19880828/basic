//package com.thfdcsoft.framework.business.controller;
//
//import java.io.File;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
////import javax.servlet.http.HttpServletRequest;
////import javax.servlet.http.HttpServletResponse;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.thfdcsoft.framework.business.contant.RecordConstant;
//import com.thfdcsoft.framework.business.dao.TerminalInfoMapper;
//import com.thfdcsoft.framework.business.dao.UsageRecordMapper;
//import com.thfdcsoft.framework.business.dto.SubmitUsageReq;
//import com.thfdcsoft.framework.business.dto.SubmitUsageRspn;
//import com.thfdcsoft.framework.business.entity.TerminalInfo;
//import com.thfdcsoft.framework.business.entity.UsageRecord;
//import com.thfdcsoft.framework.business.util.AutoIdFactory;
//import com.thfdcsoft.framework.common.date.DateFormatConstant;
//import com.thfdcsoft.framework.common.date.DateFormatFactory;
//import com.thfdcsoft.framework.common.file.FileFactory;
//import com.thfdcsoft.framework.common.http.dto.BaseHttpRspn;
//import com.thfdcsoft.framework.common.image.ImageFactory;
//import com.thfdcsoft.framework.common.json.jackson.JacksonFactory;
//
//@RestController
//public class RecordController {
//
//	public static String hzdh="";//回执单号
//	@Autowired
//	private UsageRecordMapper usageRecordMapper;
//
////	@Autowired
////	private PrintRecordMapper printRecordMapper;
//
//	@Autowired
//	private TerminalInfoMapper terminalInfoMapper;
//
//
//
////	@RequestMapping(value = "choice", method = RequestMethod.POST)
////	@ResponseBody
////	public String choice(HttpServletRequest request, HttpServletResponse response,String ywbh) {
////		System.out.println("ywbh");
////		hzdh=ywbh;
////		return ywbh;
////	}
//
//
//	/**
//	 * 提交终端使用记录<br>
//	 * 1.保存代理人身份证照片及现场照片<br>
//	 * 2.生成代理人操作日志文件<br>
//	 * 3.保存领证代理人信息<br>
//	 * 4.返回usageId和剩余纸张数量用于后续业务
//	 *
//	 * @param request
//	 * @param response
//	 * @param agent
//	 * @return
//	 */
//	@RequestMapping(value = "submitUsageRecord", method = RequestMethod.POST)
//	@ResponseBody
//	public String submitUsageRecord(HttpServletRequest request, HttpServletResponse response,
//			@RequestBody SubmitUsageReq agent) {
//		BaseHttpRspn result = new BaseHttpRspn(false);
//		// 1.保存代理人身份证照片及现场照片
//		boolean flag = false;
//		String recordId = AutoIdFactory.getAutoId();
//		String userIdPicPath = RecordConstant.FileDir.ID_PIC_DIR + recordId + RecordConstant.SuffixName.JPG;
//		flag = ImageFactory.generateImage(agent.getIdPic(), userIdPicPath);
////		if (!flag) {
////			result.setResult(flag);
////			result.setRespMsg("身份证图片保存失败！");
////			return JacksonFactory.writeJson(result);
////		}
//		String userDetPicPath = RecordConstant.FileDir.DET_PIC_DIR + recordId + RecordConstant.SuffixName.JPG;
//		flag = ImageFactory.generateImage(agent.getDetPic(), userDetPicPath);
////		if (!flag) {
////			result.setResult(flag);
////			result.setRespMsg("现场图片保存失败！");
////			return JacksonFactory.writeJson(result);
////		}
//
//		// 2.生成代理人操作日志文件
//		String userLogPath = RecordConstant.FileDir.LOG_DIR + recordId + RecordConstant.SuffixName.LOG;
//		File log = FileFactory.buildFile(userLogPath);
//		if (!log.exists()) {
//			result.setResult(false);
//			result.setRespMsg("生成日志文件失败！");
//			return JacksonFactory.writeJson(result);
//		}
//
//		// 3.保存领证代理人信息
//		UsageRecord usage = new UsageRecord();
//		usage.setRecordId(recordId);
//		usage.setDeviceNumber(agent.getDeviceNumber());
//		usage.setUserIdnumber(agent.getIdNumber());
//		usage.setUserFullname(agent.getFullName());
//		usage.setUserIdPicPath(userIdPicPath);
//		usage.setUserDetPicPath(userDetPicPath);
//		usage.setUserLogPath(userLogPath);
//		usage.setUsageTime(
//				DateFormatFactory.getCurrentDateString(DateFormatConstant.SymbolFormat.YYYY_MM_DD_HH_MM_SS));
//		int r = this.usageRecordMapper.insertSelective(usage);
//		if (r > 0) {
//			// 4.返回recordId
//			SubmitUsageRspn rspn = new SubmitUsageRspn();
//			// 获取设备剩余纸张数量
//			TerminalInfo terminal = this.terminalInfoMapper.selectByDeployNumber(usage.getDeviceNumber());
//			if (terminal != null) {
//				rspn.setRemaining(terminal.getRemainingPaper());
//				rspn.setUsageId(recordId);
//				result.setResult(true);
//				result.setRespJson(JacksonFactory.writeJson(rspn));
//			} else {
//				result.setRespMsg("终端设备信息获取失败");
//			}
//		} else {
//			result.setRespMsg("终端设备使用记录保存失败");
//		}
//		return JacksonFactory.writeJson(result);
//	}
//
//
//
//}
