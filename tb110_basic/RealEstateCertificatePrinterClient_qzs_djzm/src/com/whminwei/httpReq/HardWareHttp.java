package com.whminwei.httpReq;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.whminwei.constant.Constant;
import com.whminwei.controller.FaceRecognitionController;
import com.whminwei.dto.rspn.HttpReturnDto;
import com.whminwei.util.DateFactory;
import com.whminwei.util.HttpClientFactory;
import com.whminwei.util.JacksonFactory;
import com.whminwei.view.MainView;
import com.whminwei.view.pages.IdReadInformation;
import com.whminwei.view.pages.LoadPanes;
import com.whminwei.view.plugin.MediaPlug;

/**
 * @author 肖银
 * 2020年8月14日
 */
public class HardWareHttp {

	private static final Logger logger = LoggerFactory.getLogger(HardWareHttp.class);

	/**
	 * Description:初始化身份证读卡器
	 * @Author 肖银  
	 * @Date 2020年9月14日 下午11:15:32
	 */
	public void initIdCardreader() {
		String url = Constant.HardWare.HARDWARE_URL + "initIdCardreader";
		String value = HttpClientFactory.getInstance().doGet(url);
		HttpReturnDto result = JacksonFactory.readJson(value, HttpReturnDto.class);
		logger.info("身份证读卡器初始化结果: " + result.isResult());
		if (result.isResult()) {
			// 清空硬件平台中保存的身份证图片
			String cleanUrl = Constant.HardWare.HARDWARE_URL + "cleanIdCardPic";
			HttpClientFactory.getInstance().doGet(cleanUrl);
		} else {
			MainView.initStatus = false;
			MainView.errorMessage = "身份证读卡器初始化失败，请联系现场工作人员!";
		}
	}
	
	/**
	 * @description: 发送读卡请求
	 * @author: 肖银
	 * @date: 2020年9月2日上午8:56:48
	 */
	public void getIdCardInfo() {
		logger.info("in getIdCardInfo...");
		IdReadInformation idInformation = null;
		if (Constant.IDReader.Mode.DISABLE.equals(Constant.IDReader.IDREADER_MODE)) {
			idInformation = new IdReadInformation("张三", "男", "汉", Constant.IDReader.TEST_ID, "D:/test.jpg",
					"生日2008 12 12");
			logger.info("测试初始化数据姓名:{},身份证号:{}", idInformation.getFullName(), idInformation.getIdNumber());
			FaceRecognitionController.idPicPath = idInformation.getIdPicPath();
			MainView.idInformation = idInformation;
			LoadPanes.getIdCardInfoPane();
		} else {
			// 初始化数据
			String url = Constant.HardWare.HARDWARE_URL + "readIdCard";
			String returnvalue = HttpClientFactory.getInstance().doGet(url);
			HttpReturnDto returndto = JacksonFactory.readJson(returnvalue, HttpReturnDto.class);
			logger.info("请求returndto = {}", returndto);
			if (returndto.isResult()) {
				idInformation = JacksonFactory.readJson(returndto.getRespJson(), IdReadInformation.class);
				String expireDate = idInformation.getExpireDate();
				// 检查身份证是否过期
				boolean value = DateFactory.checkDate(expireDate,"yyyyMMdd");
				if (value) {
					// 跳转到读卡信息界面
					FaceRecognitionController.idPicPath = idInformation.getIdPicPath();
					MainView.idInformation = idInformation;
					LoadPanes.getIdCardInfoPane();
				} else {
					LoadPanes.showMessagePane("此身份证已过期，无法进行业务办理！！！", 15);
					MediaPlug.IDPastDue();
				}
			} else {
				logger.info("请求异常msg = {}", returndto.getRespMsg());
				LoadPanes.showMessagePane("硬件平台中身份证读取存在故障，请联系工作人员！！！", 10);
			}
		}
		logger.info("out getIdCardInfo...");
	}
}
