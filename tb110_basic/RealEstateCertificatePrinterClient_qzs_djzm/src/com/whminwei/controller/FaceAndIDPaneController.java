package com.whminwei.controller;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.whminwei.constant.Constant;
import com.whminwei.dto.req.FaceValidateReq;
import com.whminwei.dto.rspn.HttpReturnDto;
import com.whminwei.util.*;
import com.whminwei.view.MainView;
import com.whminwei.view.pages.IdReadInformation;
import com.whminwei.view.pages.TransitPage;
import com.whminwei.view.plugin.CountDownPlug;
import com.whminwei.view.plugin.MediaPlug;
import com.whminwei.view.script.ClientScript;

import thFace.THface;

import java.io.FileInputStream;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

public class FaceAndIDPaneController {

	private static final Logger logger = LoggerFactory.getLogger(FaceAndIDPaneController.class);

	@FXML
	private HBox mainH;

	@FXML
	private Text fullName;// 姓名

	@FXML
	private Text sex;// 性别

	@FXML
	private Text nation;// 民族

	@FXML
	private Text idNumber;// 身份证号码

	// 身份证图片
	@FXML
	private ImageView idPic;

	// 验证结果
	@FXML
	private Text chkResTest;

	private static boolean faced; // 是否框到人脸标识符

//	private static boolean faceComparisonState = false;

	private static ImageView detPic = new ImageView();

	private static String idPicPath;

	private static String detPicPath;

	private static String InfraredDetPicPath; // 红外摄像头照片路径

	static {
		detPic.setFitWidth(600);
		detPic.prefWidth(600);
		detPic.setPreserveRatio(true);
		System.load(Constant.FaceDetect.OPENCV_PATH + "/opencv_java401.dll");
		System.load(Constant.FaceDetect.THFACE_PATH + "/opencv_world310.dll");
		System.load(Constant.FaceDetect.THFACE_PATH + "/tensorflow.dll");
		System.load(Constant.FaceDetect.THFACE_PATH + "/thface.dll");
	}

	public String getSex() {
		return sex.getText();
	}

	public String getNation() {
		return nation.getText();
	}

	public String getIdNumber() {
		return idNumber.getText();
	}

	public String getFullName() {
		return fullName.getText();
	}

	public String getIdPicPath() {
		return idPicPath;
	}

	public String getDetPicPath() {
		return detPicPath;
	}

	public String getInfraredDetPicPath() {
		return InfraredDetPicPath;
	}

	public void setFacedStateFalse() {
		faced = false;
	}

	public void setFacedStateTrue() {
		faced = true;
	}

	/**
	 * 加载摄像头
	 */
	public void addWebCam() {
		/* 添加摄像头 */
		mainH.getChildren().remove(1);
		mainH.getChildren().add(1, OpenCVCamFactory.normalCapturedImage);
	}

	/**
	 * 重置身份信息
	 */
	public void resetID() {

		fullName.setText("XXX");
		sex.setText("X");
		nation.setText("X");
		idNumber.setText("XXXXXXXXXXXXXXXXXX");
		idPic.setImage(null);
		chkResTest.setText("");
		detPic.setImage(null);
		idPicPath = null;
		detPicPath = null;

	}

	public static Thread faceComparisonThread;

	/**
	 * 人证对比
	 * 
	 * @param detPicPathTemp
	 */
//	public void faceComparison(String detPicPathTemp) {
//		faceComparisonThread = new Thread(new Task<Void>() {
//			@Override
//			protected Void call() throws Exception {
//				// 可识别现场照片生成状态 满足识别要求
//				while (OpenCVCamFactory.facePageState) {
//					if (OpenCVCamFactory.saveDetPicState) {
//						try {
//							FaceValidateReq req = new FaceValidateReq();
//
//							THface thface = new THface();
//							thface.THFaceInit(Constant.FaceDetect.THFACE_PATH);
//
//							logger.info("faceComparisonThread idPicPath = {}", idPicPath);
//							logger.info("faceComparisonThread detPicPathTemp = {}", detPicPathTemp);
//							if (StringUtils.isNotEmpty(idPicPath) && StringUtils.isNotEmpty(detPicPathTemp)) {
//								float degree = thface.compareFaces(idPicPath, detPicPathTemp);
//
//								detPicPath = PropertyFactory.getPath() + "opencv" + "/faces/" + "normalPic" + ".jpg";
//
//								logger.info("相似度={}", degree);
//								if (degree >= Constant.FaceDetect.SIMILARITY) {
//									Platform.runLater(() -> {
//										MediaPlug.validateSuccess();
//										ClientScript.faceAndID.chkResTest.getStyleClass().clear();
//										ClientScript.faceAndID.chkResTest.getStyleClass().add("green-text");
//										ClientScript.faceAndID.chkResTest
//												.setText("验证成功：" + String.format("%.2f", degree));
//									});
//
//								} else {
//									Platform.runLater(() -> {
//										MediaPlug.validateFail();
//										ClientScript.faceAndID.chkResTest.getStyleClass().clear();
//										ClientScript.faceAndID.chkResTest.getStyleClass().add("red-text");
//										ClientScript.faceAndID.chkResTest
//												.setText("验证失败：" + String.format("%.2f", degree));
//										logger.info("验证失败，即将重新验证");
//									});
//								}
//							}
//						} catch (Exception e) {
//							e.printStackTrace();
//						}
//					}
//					Thread.sleep(500L);
//				}
//				return null;
//			}
//		});
//		faceComparisonThread.setDaemon(true);
//		faceComparisonThread.start();
//	}

}
