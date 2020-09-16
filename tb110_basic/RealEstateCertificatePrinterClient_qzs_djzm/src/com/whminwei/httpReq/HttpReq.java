package com.whminwei.httpReq;

import java.io.File;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.whminwei.constant.Constant;
import com.whminwei.dto.Certnumber;
import com.whminwei.dto.TerminalInfo;
import com.whminwei.dto.req.SubmitPrintReq;
import com.whminwei.dto.req.UpdatePaperCountReq;
import com.whminwei.dto.rspn.HttpReturnDto;
import com.whminwei.util.HttpClientFactory;
import com.whminwei.util.ImageFactory;
import com.whminwei.util.JacksonFactory;
import com.whminwei.util.StringUtils;
import com.whminwei.view.MainView;
/**
 * 登记证明http请求操作
 */
public class HttpReq {
	
	public static Logger logger = LoggerFactory.getLogger(HttpReq.class);
	
	/**
	 * 修改登记证明数量
	 * @return
	 */
	public static boolean updateRemainPager() {
		UpdatePaperCountReq updatePaperCountReq = new UpdatePaperCountReq();
		updatePaperCountReq.setUsageId(MainView.usageId);
		updatePaperCountReq.setTerminalId(Constant.TerminalId.DJZM_PRINT);
		try {
			String rspnJson = HttpClientFactory.getInstance().doPost(JacksonFactory.writeJson(updatePaperCountReq), Constant.Manage.MANAGE_URL + "business/updateRemainPager");
			HttpReturnDto result = JacksonFactory.readJson(rspnJson, HttpReturnDto.class);
//			MainView.lastPageCode = result.getRespJson();
			return result.isResult();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * Description:保存登记证明打印记录
	 * @param path 源文件地址
	 * @param certNumber 权证号
	 * @param busiNumber 业务号
	 * @param ocr 证明印刷序列号
	 * @return
	 */
	public boolean saveDjzmPrintRecord(String path, String certNumber, String busiNumber, String ocr) { logger.info("发送打印记录请求");
		boolean saveFlag = false;
		SubmitPrintReq cert = new SubmitPrintReq();
		cert.setBusiNumber(busiNumber);
		cert.setCertNumber(certNumber);
		cert.setSequNumber(ocr);
		cert.setCertScan(ImageFactory.getImageStr(path));
		cert.setUsageId(MainView.usageId);
		String reqJson = JacksonFactory.writeJson(cert);
		logger.info("登记证明打印记录保存请求数据："+reqJson);
		String rspnJson = HttpClientFactory.getInstance().doPost(reqJson,
				Constant.Manage.MANAGE_URL + "business/submitPrintRecord");
		logger.info("登记证明打印记录返回【{}】",rspnJson);
		HttpReturnDto result = JacksonFactory.readJson(rspnJson, HttpReturnDto.class);
		if(result != null) {
			saveFlag =  result.isResult();
		}
		return saveFlag;
	}
	
	/**
     * Description:保存权证书打印记录
     * @param certNumber 证书权证号
     * @param unitNumber 不动产单元号
     * @param ocr 证书印刷序列号
     * @param destPath word文档路径
     * @param ocrPicPath 证书照片保存路径
     */
    public boolean saveQzsPrintRecord(String certNumber, String unitNumber, String ocr, String destPath, String ocrPicPath) {
    	boolean saveFlag = false;
        SubmitPrintReq cert = new SubmitPrintReq();
        cert.setCertNumber(certNumber);
        cert.setBusiNumber(unitNumber);
        cert.setSequNumber(ocr);
        cert.setUsageId(MainView.usageId);
        cert.setDestPath(destPath);
        cert.setCertScan(ImageFactory.getImageStr(ocrPicPath));
        cert.setOcrPicPath(ocrPicPath);
        cert.setTerminalId(Constant.TerminalId.QZS_PRINT);
        String reqJson = JacksonFactory.writeJson(cert);
        logger.info("权证书打印记录保存请求数据："+reqJson);
        String rspnJson = HttpClientFactory.getInstance().doPost(reqJson,
                Constant.Business.BUSINESS_URL + "client/submitPrintRecord");
        logger.info("权证书打印记录返回【{}】",rspnJson);
        HttpReturnDto result = JacksonFactory.readJson(rspnJson, HttpReturnDto.class);
        if(result != null) {
        	saveFlag = result.isResult();
        }
        return saveFlag;
    }


	/**
	 * Description:查询当前打印记录去重
	 * @return
	 * @Author 高拓  
	 * @Date 2020年9月13日 上午10:24:30
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
		} catch (Exception e) {
			e.printStackTrace();
		}
		certnumbers = JacksonFactory.readJsonList(httpReturnDto.getRespJson(), Certnumber.class);
		System.out.println("out hasPrinted..............");
		return certnumbers;
	}
	
	/**
	 * Description:查询指定设备编号的机器中证书、证明数量
	 * @return
	 * @Author 高拓  
	 * @Date 2020年9月14日 下午11:50:21
	 */
	public boolean getRemainingCount() {
		boolean getRemainingStatus = true;
		try {
			String rspnJson = HttpClientFactory.getInstance().doGet(Constant.Business.BUSINESS_URL + "getRemainingCount/"+Constant.DeviceInfo.DEVICE_NUMBER);
			HttpReturnDto rspn = JacksonFactory.readJson(rspnJson, HttpReturnDto.class);
			if(StringUtils.isNotNullAndEmpty(rspn.getRespJson())) {
				List<TerminalInfo> terminalInfoList = JacksonFactory.readJsonList(rspn.getRespJson(), TerminalInfo.class);
				for(TerminalInfo t : terminalInfoList) {
					if(Constant.TerminalId.DJZM_PRINT.equals(t.getTerminalId())) {
						logger.info("t.getTerminalId()--->"+t.getTerminalId()+" 剩余登记证明数量："+t.getRemainingPaper());
						MainView.paperRemaining = t.getRemainingPaper();
					}else if(Constant.TerminalId.QZS_PRINT.equals(t.getTerminalId())) {
						logger.info("t.getTerminalId()--->"+t.getTerminalId()+" 剩余权证书数量："+t.getRemainingPaper());
						MainView.certificateRemaining = t.getRemainingPaper();
					}
				}
			}
			// 设置首页证书、证明数量
			MainView.indexController.setPaperNum(MainView.paperRemaining);
			MainView.indexController.setCertificateNum(MainView.certificateRemaining);
		} catch (Exception e) {
			getRemainingStatus = false;
			MainView.errorMessage = "获取证书、证明数量失败，请联系现场工作人员!";
			e.printStackTrace();
		}
		return getRemainingStatus;
	}
	
}
