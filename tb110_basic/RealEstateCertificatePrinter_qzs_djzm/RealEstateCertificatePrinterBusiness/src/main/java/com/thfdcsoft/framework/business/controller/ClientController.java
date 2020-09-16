package com.thfdcsoft.framework.business.controller;

import com.thfdcsoft.framework.business.contant.RecordConstant;
import com.thfdcsoft.framework.business.contant.StaticConstant;
import com.thfdcsoft.framework.business.dto.*;
import com.thfdcsoft.framework.business.entity.PrintRecord;
import com.thfdcsoft.framework.business.entity.TerminalInfo;
import com.thfdcsoft.framework.business.entity.UsageRecord;
import com.thfdcsoft.framework.business.service.ManageService;
import com.thfdcsoft.framework.business.util.AutoIdFactory;
import com.thfdcsoft.framework.business.dto.HttpReturnDto;
import com.thfdcsoft.framework.common.date.DateFormatConstant;
import com.thfdcsoft.framework.common.date.DateFormatFactory;
import com.thfdcsoft.framework.common.file.FileFactory;
import com.thfdcsoft.framework.common.http.HttpClientFactory;
import com.thfdcsoft.framework.common.http.def.DefHttpClientFactory;
import com.thfdcsoft.framework.common.http.dto.BaseHttpRspn;
import com.thfdcsoft.framework.common.image.ImageFactory;
import com.thfdcsoft.framework.common.json.jackson.JacksonFactory;
import com.thfdcsoft.gov.dto.djzm.DjzmEstateResult;
import com.thfdcsoft.gov.dto.djzm.QueryEstateRspn;
import com.thfdcsoft.gov.service.IConnectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.List;


/**
 * 接收客户端请求 2019年4月29日 09点18分
 *
 * @author weaso
 */
@Controller
@RequestMapping(value = "/client")
public class ClientController {

    private static final Logger logger = LoggerFactory.getLogger(ClientController.class);

    private static List<String> listData; // 扫码查询参数

    private static UsageRecord usage;

    @Autowired
    private IConnectService connector;

    @Autowired
    private ManageService manageService;

    @RequestMapping(value = "/doPrint", method = RequestMethod.GET)
    public String doPrint() {
        return "doPrint";
    }

    /**
     * 接收客户端请求返回页面
     */
    @RequestMapping(value = "/queryHouse/{usageId}", method = RequestMethod.GET)
    public String choice(HttpServletRequest request, @PathVariable(value = "usageId") String usageId) {
        request.setAttribute("recordId", usageId);
        logger.info("接收数据 使用记录id: " + usageId);
        // 查询显示页面
        return "alreadyPay";
    }

    /**
     * 跳转密码登陆页面
     *
     * @return 密码登录地址
     */
    @RequestMapping(value = "/pwdlogin", method = RequestMethod.GET)
    public String pwdlogin() {
        return "passwordlogin";
    }


    /**
     * 管理员验证
     *
     * @param idName 身份证号
     * @return 验证结果
     */
    @RequestMapping(value = "/manageYanzheng", method = RequestMethod.POST)
    @ResponseBody
    public String manageYanzheng(@RequestBody String idName) {
        logger.info("获取管理员的身份证为" + idName);

        BaseHttpRspn result = new BaseHttpRspn(false);

        String url = StaticConstant.MANAGE_URL + "users/manageYz";
        try {
            String returnValue = DefHttpClientFactory.getInstance().doPost(idName, url);
            BaseHttpRspn rspn = JacksonFactory.readJson(returnValue, BaseHttpRspn.class);
            if (rspn.isResult()) {
                result.setResult(true);
            } else {
                result.setResult(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return JacksonFactory.writeJson(result);
    }


    /**
     * 账号密码登陆
     *
     * @param name     用户名
     * @param password 密码
     * @return
     */
    @RequestMapping(value = "/AccountPwdLogin", method = RequestMethod.POST)
    @ResponseBody
    public Boolean AccountPwdLogin(String name, String password) {
    	BaseHttpRspn rspn = new BaseHttpRspn(false);
    	
        logger.info("loginname:" + name);
        logger.info("loginpassword:" + password);

        String url = StaticConstant.MANAGE_URL + "users/zhLogin";
        logger.info("url:" + url);

        Loginreq loginreq = new Loginreq();
        loginreq.setLoginname(name);
        loginreq.setLoginpassword(password);

        String writeJson = JacksonFactory.writeJson(loginreq);
        logger.info("writeJson:" + writeJson);
        try {
            String returnValue = DefHttpClientFactory.getInstance().doPost(writeJson, url);
            rspn = JacksonFactory.readJson(returnValue, BaseHttpRspn.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rspn.isResult();
    }


    /**
     * 权证书提交打印记录至Manage并通知不动产系统<br>
     *
     * @param certJson 证件信息
     * @return
     */
    @RequestMapping(value = "/submitPrintRecord", method = RequestMethod.POST)
    @ResponseBody
    public String submitPrintRecord(@RequestBody String certJson) {
        BaseHttpRspn result = new BaseHttpRspn(false);
        logger.info("接收客户端打印记录=" + certJson);
        //1. 通知manage
        SubmitPrintReq cert = JacksonFactory.readJson(certJson, SubmitPrintReq.class);
        PrintRecord printRecord = submit2Manage(cert);
        //2. 通知不动产系统
        if (!submit2EstateSystem(cert,printRecord)) {
            result.setResult(false);
            result.setRespMsg("通知不动产系统失败！");
            return JacksonFactory.writeJson(result);
        }
        result.setResult(true);
        return JacksonFactory.writeJson(result);
    }

    /**
     * 提交终端登记证明打印记录<br>
     * 1.保存不动产登记证明扫描件<br>
     * 2.保存登记证明信息
     *
     * @param cert 证件信息
     * @return
     */
    private PrintRecord submit2Manage(SubmitPrintReq cert) {

        // 更新设备剩余纸张数量
        String str_paperRspn = manageService.post2Manage("business/updatePaper",JacksonFactory.writeJson(cert));
        BaseHttpRspn paperReturn = JacksonFactory.readJson(str_paperRspn, BaseHttpRspn.class);
        if (!paperReturn.isResult()) {
            String content = "更新设备证书数量失败！";
            logger.error(content);
            FileFactory.writeTXTFile(cert.getLogFile(), content);
        }else {
        	 logger.info("更新设备剩余纸张完成");
        }
       
        // 2.保存权证信息
        boolean flag = ImageFactory.generateImage(cert.getCertScan(), cert.getOcrPicPath());
        if (!flag) {
            String content = "权证书扫描件保存失败！";
            FileFactory.writeTXTFile(cert.getLogFile(), content);
        }
        PrintRecord print = new PrintRecord();
        String recordId = AutoIdFactory.getAutoId();
        print.setRecordId(recordId);
        print.setBizNumber(cert.getBusiNumber());//单元号
        print.setCertNumber(cert.getCertNumber());//权证号
        print.setCertScanPath(cert.getOcrPicPath());//证书编号照片
        print.setTranStatusBiz(RecordConstant.TranStatus.WAITING);//回传状态初始值 等待回传
        print.setUsageId(cert.getUsageId());//使用记录id
        print.setPrintTime(DateFormatFactory.getCurrentDateString(DateFormatConstant.SymbolFormat.YYYY_MM_DD_HH_MM_SS));
        print.setSeqNumber(cert.getSequNumber());//证书编号
        // 设置打印类型为权证书类型
        print.setPrintType(RecordConstant.PrintType.PRINT_QZS);
        String printRecord = JacksonFactory.writeJson(print);
        String savePrintRspnJson = manageService.post2Manage("business/savePrintRecord", printRecord);
        BaseHttpRspn savePrintRspn = JacksonFactory.readJson(savePrintRspnJson, BaseHttpRspn.class);
        if (!savePrintRspn.isResult()) {
            String content = "保存打印记录失败！";
            FileFactory.writeTXTFile(cert.getLogFile(), content);
//			throw new PrintException(false, content);
        } else if (paperReturn.isResult() && savePrintRspn.isResult()) {
        	System.out.println("更新设备纸张、保存使用记录成功");
            return print;
        }
        return null;
    }

    /**
     * 通知不动产系统
     *
     * @param printRecord 打印记录
     * @return
     * @author ww
     * @date 2019年5月14日 15:33:16
     */
    public boolean submit2EstateSystem(SubmitPrintReq cert,PrintRecord printRecord) {

        // 获取领证人信息
        UsageRecord usage = manageService.post2ManageAndTrans("business/getUsageRecord", printRecord.getUsageId(), UsageRecord.class);
        if (usage == null) {
            logger.error("领证人信息获取失败！");
            return false;
        }
        logger.info("领证人信息获取成功");
        // 获取设备信息
        TerminalInfo terminal = manageService.post2ManageAndTrans("business/getTerminalInfo", JacksonFactory.writeJson(cert), TerminalInfo.class);
        if (terminal == null) {
            logger.error("设备信息获取失败！");
            return false;
        }
        logger.info("设备信息获取成功");

        // 通知不动产系统并更新通知状态
        Integer updateState = connector.notifyAndUpdate(printRecord, usage);
//        Integer updateState = 1;
        if (updateState > 0) {
            logger.info("通知状态更新成功");
            return true;
        } else {
            logger.error("通知状态更新成功");
            return false;
        }
    }

    /**
     * 跳转到无打印数据页面
     *
     * @return 页面地址
     */
    @RequestMapping(value = "/noPrint")
    public String noPrint() {
        return "noPrint";
    }

    /**
     * 跳转到打印完成页面
     *
     * @return 页面地址
     */
    @RequestMapping(value = "/printSuccess")
    public String printError() {
        return "printSuccess";
    }

    /**
     * 跳转到办事指南页面
     *
     * @return 页面地址
     */
    @RequestMapping(value = "/guidance")
    public String guidance() {
        return "guidance";
    }

    /**
     * 跳转到法律法规页面
     *
     * @return 页面地址
     */
    @RequestMapping(value = "/lawsAndRegulations")
    public String lawsAndRegulations() {
        return "lawsAndRegulations";
    }

    /**
     * 跳转到收费标准页面
     *
     * @return 页面地址
     */
    @RequestMapping(value = "/chargesNotes")
    public String chargesNotes() {
        return "chargesNotes";
    }

    /**
     * 跳转到等待跳转页面
     *
     * @return 页面地址
     */
    @RequestMapping(value = "/loadIndex")
    public String interim() {
        return "index";
    }
    
    
    //===================================== 客户端直接请求Manage的方法移到业务层由业务层请求Manage ========================================
    
    /**
     * 客户端请求保存超级密码
     * 
     * @param request
     * @param response
     * @param num
     * @return
     */
    @RequestMapping(value = "/supperlogin", method = RequestMethod.POST)
	@ResponseBody
	public String supperlogin(HttpServletRequest request, HttpServletResponse response,@RequestBody String num) {		
    	BaseHttpRspn result = new BaseHttpRspn(false);
    	// 请求后台服务
		BaseHttpRspn rspn = manageService.post2ManageAndTrans("business/supperlogin", num, BaseHttpRspn.class);		   
		if(rspn.isResult()) {
	    	 result.setResult(true);	
	    	 result.setRespJson(rspn.getRespJson());
	     }else {
	    	 result.setResult(false);
	    	 result.setRespMsg("数据库添加失败!");
	     }				
		return JacksonFactory.writeJson(result);
	}
    
    /**
	 * 客户端请求保存使用记录
	 * 
	 * @param request
	 * @param response
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/saveUsageRecord", method = RequestMethod.POST)
	@ResponseBody
	public String saveUsageRecord(HttpServletRequest request, HttpServletResponse response, @RequestBody SubmitUsageReq req) {
		BaseHttpRspn result = new BaseHttpRspn(false);
		// 请求后台服务
		BaseHttpRspn rspn = manageService.post2ManageAndTrans("business/saveUsageRecord", JacksonFactory.writeJson(req), BaseHttpRspn.class);
		if(rspn.isResult()) {
			result.setResult(true);
			result.setRespJson(rspn.getRespJson());
		}
		System.out.println("业务层保存完使用记录：" + JacksonFactory.writeJson(result));
		return JacksonFactory.writeJson(result);
	}
	
    /**
     * 客户端请求获取剩余纸张数量
     * @param request
     * @param response
     * @param queryPaperReq
     * @return
     */
	@RequestMapping(value = "/querypaper", method = RequestMethod.POST)
	@ResponseBody
	public String querypaper(HttpServletRequest request, HttpServletResponse response, @RequestBody QueryPaperReq queryPaperReq) {
		BaseHttpRspn result = new BaseHttpRspn(false);
		// 请求后台服务
		BaseHttpRspn rspn = manageService.post2ManageAndTrans("business/querypaper", JacksonFactory.writeJson(queryPaperReq), BaseHttpRspn.class);
		if(rspn.isResult()) {
			result.setResult(true);
			result.setRespJson(rspn.getRespJson());
		}
		return JacksonFactory.writeJson(result);
	}

    @RequestMapping(value = "/selectPrintType")
    public String businessType(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("跳转至选择打印类型页面");
        return "businessType";
    }

    /**
     * =====================================登记证明迁移代码===========================================
     */

    // 获取扫码查询参数
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "smcxData", method = RequestMethod.POST)
    @ResponseBody
    public void submitUsageRecord(HttpServletRequest request, HttpServletResponse response,
                                  @RequestBody HttpReturnDto httpReq) {
        System.out.println("in smcxData.....");
        listData = (List<String>) httpReq.getRespObj();
    }

    @RequestMapping(value = "/print/{usageId}", method = RequestMethod.GET)
    public String choice(Model model, @PathVariable(value = "usageId") String usageId) {
        HttpReturnDto queryResult = getEstateList(usageId);
        if(queryResult.isResult()) {
            model.addAttribute("certLists", JacksonFactory.readJsonList(queryResult.getRespJson(), DjzmEstateResult.class));
            model.addAttribute("bizLists", JacksonFactory.writeJson(queryResult.getRespJson()));
            return "print";
        }else {
            //跳转到失败页面
            model.addAttribute("errorMessage", queryResult.getRespMsg());
            return "error";
        }
    }

    public HttpReturnDto getEstateList(String usageId){
        HttpReturnDto result = new HttpReturnDto(false);
        // 1.根据身份证信息查询
        // 2.扫码查询 listData
        // 获取代理人信息
//		 usageId = "20180101151419000001";
        System.out.println("in getEstateList usageId=====" + usageId);
        // 管理系统获取用户记录
        String rspnJson = null;
        // 获取用户信息请求
        try {
            rspnJson = DefHttpClientFactory.getInstance().doGet(StaticConstant.MANAGE_URL  + "business/getUsage?usageId=" + usageId);
            usage = JacksonFactory.readJson(rspnJson, UsageRecord.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(usage == null) {
            result.setResult(false);
            result.setRespMsg("查询用户使用记录失败");
            return result;
        }
        // 获取日志文件
        File recordFile = new File(usage.getUserLogPath());
        // 查询数据
        QueryEstateRspn rspn = connector.getRealEstateListByIdNumber(usage.getDeviceNumber(), usage.getUserIdnumber(), recordFile);
        if(!StringUtils.isEmpty(rspn.getResultcode()) && RecordConstant.Code.SUCCESS.equals(rspn.getResultcode())) {
            System.out.println("list.size======="+rspn.getEstateResults().size());
            if (rspn.getEstateResults().size() > 0) {
                result.setResult(true);
                result.setRespJson(JacksonFactory.writeJson(rspn.getEstateResults()));
            } else {
                result.setResult(false);
                result.setRespMsg("未找到可打印的登记证明信息！");
            }
        }else{
            result.setResult(false);
            result.setRespMsg(rspn.getResultmsg());
        }
        return result;
    }

    @RequestMapping(value = "/printComplete", method = RequestMethod.POST)
    @ResponseBody
    public String printComplete(HttpServletRequest request, HttpServletResponse response, String usageId)
            throws UnsupportedEncodingException, KeyManagementException, NoSuchAlgorithmException {
        System.out.println("in printComplete usageId......." + usageId);
        HttpReturnDto result = new HttpReturnDto(false);
        if (usage == null) {
            result.setResult(false);
            result.setRespMsg("领证代理人信息获取失败！");
            return JacksonFactory.writeJson(result);
        }
        //后台管理系统获取已打印登记证明
        String rspnJson = null;
        rspnJson = HttpClientFactory.getInstance().doPost(JacksonFactory.writeJson(usage),
                StaticConstant.MANAGE_URL + "business/hasPrintCompleted");
        PrintRecordRsp rspn = JacksonFactory.readJson(rspnJson, PrintRecordRsp.class);
        // 获取日志文件
        File recordFile = new File(usage.getUserLogPath());
        // 回传不动产系统
        if (rspn.getPrintList() != null) {
            connector.printComplete(usage, rspn.getPrintList(), recordFile);
        } else {
            // 默认OCR没有识别成功
            System.out.println("没有满足条件的等待状态的证明....");
        }
        result.setResult(true);
        System.out.println("out  printComplete usageId.......");
        return JacksonFactory.writeJson(result);
    }

}
