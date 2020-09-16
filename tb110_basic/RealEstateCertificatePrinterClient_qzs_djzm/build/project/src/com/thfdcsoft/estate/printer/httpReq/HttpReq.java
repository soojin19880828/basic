package com.thfdcsoft.estate.printer.httpReq;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.thfdcsoft.estate.printer.constant.Constant;
import com.thfdcsoft.estate.printer.dto.HttpReturnDto;
import com.thfdcsoft.estate.printer.dto.Number;
import com.thfdcsoft.estate.printer.dto.req.SubmitPrintReq;
import com.thfdcsoft.estate.printer.dto.req.UpdatePaperCountReq;
import com.thfdcsoft.estate.printer.dto.rspn.Certnumber;
import com.thfdcsoft.estate.printer.util.HttpClientFactory;
import com.thfdcsoft.estate.printer.util.ImageFactory;
import com.thfdcsoft.estate.printer.util.JacksonFactory;
import com.thfdcsoft.estate.printer.view.MainView;
import com.thfdcsoft.estate.printer.view.pages.SmNumberPage;
import com.thfdcsoft.estate.printer.view.plugin.CountDownPlug;
import com.thfdcsoft.estate.printer.view.plugin.MediaPlug;
import com.thfdcsoft.estate.printer.view.script.ClientScript;

import javafx.application.Platform;
/**
 * 登记证明http请求操作
 * @author GT
 *
 */
public class HttpReq {
	
	public static Logger loger = LoggerFactory.getLogger(HttpReq.class);
	
	/**
	 * 修改证明数
	 * @return
	 */
	public static boolean updateRemainPager() {
		UpdatePaperCountReq updatePaperCountReq = new UpdatePaperCountReq();
		updatePaperCountReq.setUsageId(MainView.usageId);
		updatePaperCountReq.setTerminalId(Constant.TerminalInfo.DJZM_TYPE);
		try {
			String rspnJson = HttpClientFactory.getInstance().doPost(JacksonFactory.writeJson(updatePaperCountReq), Constant.Manage.MANAGE_URL + "business/updateRemainPager");
			HttpReturnDto result = JacksonFactory.readJson(rspnJson, HttpReturnDto.class);
//			MainView.lastPageCode = result.getRespJson();
			return result.isResult();
		} catch (KeyManagementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * 保存扫描件
	 * @param path
	 * @param certNumber
	 * @param busiNumber
	 * @param ocr
	 * @return
	 */
	public static boolean saveScanCopy(String path, String certNumber, String busiNumber, String ocr) {
		System.out.println("in saveScanCopy...........");
		SubmitPrintReq cert = new SubmitPrintReq();
		cert.setBusiNumber(busiNumber);
		cert.setCertNumber(certNumber);
		cert.setSequNumber(ocr);
		cert.setCertScan(ImageFactory.getImageStr(path));
		cert.setUsageId(MainView.usageId);

		String reqJson = JacksonFactory.writeJson(cert);
		loger.info("进入扫描仪发送打印记录请求");
		try {
			String rspnJson = HttpClientFactory.getInstance().doPost(reqJson,
					Constant.Manage.MANAGE_URL + "business/submitPrintRecord");
			loger.info("进入扫描仪发送打印记录返回【{}】",rspnJson);
			HttpReturnDto result = JacksonFactory.readJson(rspnJson, HttpReturnDto.class);
			return result.isResult();
		} catch (KeyManagementException | NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		System.out.println("out saveScanCopy...........");
		return false;
	}

	/**
	 * 查询当前打印记录去重
	 * @return
	 */
	public static List<Certnumber> hasPrinted() {
		System.out.println("in hasPrinted..............");
		List<Certnumber> certnumbers = null;
		HttpReturnDto httpReturnDto = null;
		String reqStr = "";
		try {
			String rspnStr = HttpClientFactory.getInstance().doPost(reqStr,
					Constant.Manage.MANAGE_URL + "business/printedRecord");
			httpReturnDto = JacksonFactory.readJson(rspnStr, HttpReturnDto.class);
		} catch (KeyManagementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		certnumbers = JacksonFactory.readJsonList(httpReturnDto.getRespJson(), Certnumber.class);
		System.out.println("out hasPrinted..............");
		return certnumbers;
	}
	
	/**
	 * 扫码查询
	 */
	public static void forwardSmcx() {
		//传递查询列表
		HttpReturnDto httpReq = new HttpReturnDto();
		httpReq.setRespObj(SmNumberPage.smNumber);
		httpReq.setRespJson(MainView.usageId);
		try {
			HttpClientFactory.getInstance().doPost(JacksonFactory.writeJson(httpReq),Constant.Business.BUSINESS_URL + "client/smcxData");
		} catch (KeyManagementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//请求数据
		ClientScript.forwardPrint();
	}

	//发送扫描请求
	/*public static void scaner() {
		try {
			String rspnJson = HttpClientFactory.getInstance().doGet(Constant.Sealmachine.SEALMACHINE_URL + "readScanInfo");
			HttpReturnDto result = JacksonFactory.readJson(rspnJson, HttpReturnDto.class);
			if(result.isResult()) {
				System.out.println("扫描成功"+result.getRespJson());
				CountDownPlug.resetCountDown();
				if(SmNumberPage.smNumber.contains(result.getRespJson())){
					//重复扫描
					MediaPlug.scanRepeat();
				}else{
					MediaPlug.scanSuccess();
					SmNumberPage.smNumber.add(result.getRespJson());
					Number number = new Number(String.valueOf(SmNumberPage.smNumber.size()), result.getRespJson());
					SmNumberPage.data.add(number);
					SmNumberPage.qrCodeTF.setText(result.getRespJson());
				}
				//继续发送
				System.out.println("继续扫描.....");
				scaner();
			}else{
				if(!result.getRespMsg().isEmpty()){
					Platform.runLater(() -> {
						MainView.web.getEngine().executeScript("printBreak("+ result.getRespMsg() +")");
					});
				}
			}
		} catch (KeyManagementException | NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}*/
	
}
