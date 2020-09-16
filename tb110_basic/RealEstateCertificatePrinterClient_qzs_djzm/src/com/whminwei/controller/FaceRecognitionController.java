package com.whminwei.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.whminwei.constant.Constant;
import com.whminwei.util.OpenCVCamFactory;
import com.whminwei.util.PropertyFactory;
import com.whminwei.view.plugin.MediaPlug;
import com.whminwei.view.script.ClientScript;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import thFace.THface;

/*
 * 人脸识别界面
 **/
public class FaceRecognitionController {

	public HBox camera;
	
	public Label faceResult;

	// 页面提示文字
	public Label pointMessage;
	
	public static String idPicPath;

	public static String detPicPath;
	
	/** 可识别现场照片生成状态 控制是否启用人证比对 */
	public static boolean DetPicReccognitionFlag = false;
	
	public static int faceValidateSuccess = 0; // 记录人脸验证成功次数
	
	/**人脸比对控制类*/
	public static THface face = new THface();

	public static Logger logger = LoggerFactory.getLogger(FaceRecognitionController.class);
	
	public void initialize() {
		// 加载摄像头
		logger.info("初始化摄像头...");
		Task<Void> task = new Task<Void>() {
			@Override
			protected Void call() throws Exception {
				// 开启人脸识别标识符
				ClientScript.faceRecognitionFlag = false;
				// -----------测试注释（正式放开）-----------
				// 开启摄像头，进行人脸识别
				OpenCVCamFactory of = new OpenCVCamFactory();
				of.startCamera();
				// -----------测试注释（正式放开）-----------
				// 加载摄像头
				Platform.runLater(() -> {
					camera.getChildren().add(OpenCVCamFactory.normalCapturedImage);
				});
				// 语音播报
				MediaPlug.facedetect();
				String path = PropertyFactory.getPath() + "opencv";
				String detPicPathTemp = path + "/faces/" + "infraredPic" + ".jpg";
				faceComparison(detPicPathTemp);
				// -----------测试注释（正式放开）-----------
//				ClientScript.saveUsageRecord();
				// -----------测试注释（正式放开）-----------
				return null;
			}
		};
		Thread thread = new Thread(task);
		thread.setDaemon(true);
		thread.start();
	}

	/**人证比对线程*/
	public static Thread faceComparisonThread;
	/**
	 * Description:人证对比
	 * @param detPicPathTemp 实时的现场照片路径
	 * @return
	 * @Author 高拓  
	 * @Date 2020年9月7日 下午5:28:40
	 */
	public boolean faceComparison(String detPicPathTemp) {
		if(faceComparisonThread != null) {
			faceComparisonThread.interrupt();
		}
		faceComparisonThread = new Thread(new Task<Void>() {
			@Override
			protected Void call() throws Exception {
				// 可识别现场照片生成状态 满足识别要求
				while (!ClientScript.faceRecognitionFlag) {
					logger.info("in faceComparison...");
					if (OpenCVCamFactory.DetPicReccognitionFlag) {
						try {
							logger.info("faceComparisonThread idPicPath = {}", idPicPath);
							logger.info("faceComparisonThread detPicPathTemp = {}", detPicPathTemp);
							float degree = face.compareFaces(idPicPath, detPicPathTemp);
							detPicPath = PropertyFactory.getPath() + "opencv" + "/faces/" + "normalPic" + ".jpg";
							logger.info("相似度={}", degree);
							if (degree >= Constant.FaceDetect.SIMILARITY) {
								Platform.runLater(() -> {
									// 验证成功，保存使用记录
									ClientScript.faceRecognitionFlag = true;
									MediaPlug.validateSuccess();
									// 保存打印记录
									ClientScript.saveUsageRecord();
									faceValidateSuccess++;
								});
								break;
							} else {
								Platform.runLater(() -> {
									MediaPlug.validateFail();
									logger.info("验证失败，即将重新验证");
								});
							}
						} catch (Throwable e) {
							logger.info("异常信息: ", e);
							break;
						}
					}
					Thread.sleep(500);
				}
				return null;
			}
		});
		faceComparisonThread.setDaemon(true);
		faceComparisonThread.start();
		return false;
	}
}
