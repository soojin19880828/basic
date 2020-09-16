package com.whminwei.view.pages;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.whminwei.client.Main;
import com.whminwei.constant.Constant;
import com.whminwei.controller.IdCardInfoController;
import com.whminwei.util.OpenCVCamFactory;
import com.whminwei.view.MainView;
import com.whminwei.view.plugin.CountDownPlug;
import com.whminwei.view.plugin.MediaPlug;
import com.whminwei.view.script.ClientScript;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;


/**
 * 
 * @Description: FX页面跳转控制类
 * @Author 高拓
 * @Date 2020年9月1日 下午3:27:11
 */
public class LoadPanes {

	private static Logger logger = LoggerFactory.getLogger(LoadPanes.class);

	/**
	 * 跳转到读卡界面并开启摄像头
	 * 
	 * @return
	 */
	public void getReadIdCardPane() {
		logger.info("in getReadIdCardPane...");
		Platform.runLater(() -> {
			try {
				FXMLLoader readIdCard = new FXMLLoader();
				readIdCard.setLocation(Main.class.getResource("fxml/ReadIdCardPane.fxml"));
				AnchorPane readIdCardPane = readIdCard.load();
				MainView.border.setCenter(readIdCardPane);
			} catch (IOException e) {
				logger.error("读卡界面加载失败...... ", e);
			}
		});
		logger.info("out getReadIdCardPane...");
	}

	/**
	 * 跳转到读卡信息界面
	 * 
	 * @return
	 */
	public static void getIdCardInfoPane() {
		logger.info("in getIdCardInfoPane...");
		Platform.runLater(() -> {
			try {
				FXMLLoader idCardInfo = new FXMLLoader();
				idCardInfo.setLocation(Main.class.getResource("fxml/IdCardInfoPane.fxml"));
				AnchorPane idCardInfoPane = idCardInfo.load();
				MainView.border.setCenter(idCardInfoPane);
			} catch (IOException e) {
				logger.error("读卡信息界面加载失败...... ", e);
			}
		});
		logger.info("out getIdCardInfoPane...");
	}

	/**
	 * 跳转到人脸识别界面
	 * 
	 * @return
	 */
	public void getFaceRecognitionPane() {
		logger.info("in getFaceRecognitionPane...");
		MainView.topController.startCountDown(60);
		Platform.runLater(() -> {
			try {
				FXMLLoader faceRecognition = new FXMLLoader();
				faceRecognition.setLocation(Main.class.getResource("fxml/FaceRecognitionPane.fxml"));
				AnchorPane faceRecognitionPane = faceRecognition.load();
				MainView.border.setCenter(faceRecognitionPane);
			} catch (IOException e) {
				logger.error("读卡信息界面加载失败...... ", e);
			}
		});
		logger.info("out getFaceRecognitionPane...");
	}
	
	/**
	 * 
	 * Description: 程序初始化跳转首页FX页面
	 * @Author 高拓  
	 * @Date 2020年9月1日 下午3:28:23
	 */
	public void initIndexPane() {
		logger.info("in initIndexPane...");
		Platform.runLater(() -> {
			try {
				FXMLLoader indexInit = new FXMLLoader();
				indexInit.setLocation(Main.class.getResource("fxml/index2.fxml"));
				AnchorPane indexInitPane = indexInit.load();
				MainView.border.getStyleClass().add("index-bg");
				MainView.border.setCenter(indexInitPane);
			} catch (Exception e) {
				TransitPage.attentionPage("首页加载失败，请联系工作人员。", -1);
				e.printStackTrace();
			}
		});
	}
	
	/**
	 * Description:进入扫描业务编号FX页面
	 * @Author 高拓  
	 * @Date 2020年9月4日 下午2:32:45
	 */
	public void scan() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("fxml/Scan.fxml"));
            Platform.runLater(() -> {
                try {
                    MainView.topController.showReturnHome();
                    MainView.topController.startCountDown(199);
                    AnchorPane anchorPane = loader.load();
                    MainView.border.setCenter(anchorPane);
                    MediaPlug.barcodeScan();
                } catch (Exception e) {
                    logger.error("扫码页面加载异常");
                    e.printStackTrace();
                }
            });
        } catch (Exception e) {
        	e.printStackTrace();
        }
    }

	/**
	 * 进入管理员登录界面
	 * 
	 */
	public void getAdminPane() {
		logger.info("in getAdminPane...");
		// 还原管理员页面点击次数
		MainView.indexController.result = 0;
		Platform.runLater(()->{
			try {
				MainView.topController.showReturnHome();
				MainView.topController.startCounDown();
				MainView.border.getStyleClass().add("index-bg");
				FXMLLoader inputPasswordLoader = new FXMLLoader();
				inputPasswordLoader.setLocation(Main.class.getResource("fxml/InPutPassword.fxml"));
				BorderPane inputPasswordPane = inputPasswordLoader.load();
                MainView.border.setTop(MainView.topAnchorPane);
				MainView.border.setCenter(inputPasswordPane);
			} catch (IOException e) {
				logger.error("读卡信息界面加载失败...... ", e);
			}
		});
		logger.info("out getAdminPane...");
	}

	/**
	 * message: 提示信息<br>
	 * countDownTime : -1：停止倒计时; 0：不作处理; 大于零：重置倒计时
	 * 
	 * 跳转到提示界面
	 */
	public static void showMessagePane(String message, int countDownTime) {
		logger.info("in showMessagePage...");
		if (countDownTime > 0) {
			if (!CountDownPlug.countDownStart) {
				MainView.topController.resetCountDown();
			} else {
				MainView.topController.startCountDown(countDownTime);
			}
		} else if (countDownTime == -1) {
			MainView.topController.stopCountDown();
		}
		Platform.runLater(()-> {
    		MainView.transitPageController.changeMessage(message);
        	MainView.border.setCenter(MainView.transitPage);
    	});
	}
}
