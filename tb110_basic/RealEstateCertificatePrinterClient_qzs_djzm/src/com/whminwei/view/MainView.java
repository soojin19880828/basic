package com.whminwei.view;

import java.io.File;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.whminwei.client.Main;
import com.whminwei.constant.Constant;
import com.whminwei.controller.IndexController;
import com.whminwei.controller.TopController;
import com.whminwei.controller.TransitPageController;
import com.whminwei.httpReq.HardWareHttp;
import com.whminwei.httpReq.HttpReq;
import com.whminwei.util.OpenCVCamFactory;
import com.whminwei.view.pages.IdReadInformation;
import com.whminwei.view.pages.LoadPanes;
import com.whminwei.view.plugin.BizWebPlug;
import com.whminwei.view.plugin.MediaPlug;
import com.whminwei.view.script.ClientScript;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.web.WebView;

public class MainView {

	private static final Logger logger = LoggerFactory.getLogger(MainView.class);
	
	/** 初始化是否成功 */
	public static boolean initStatus = true; // 
	
	/** 初始化错误信息 */
	public static String errorMessage;

	public static WebView web;
	/** 使用记录id */
	public static String usageId = "";

	/**设备证明数量*/
	public static String paperRemaining = "0";

	/**设备证书数量*/
	public static String certificateRemaining = "0"; 
	
	/** 当前获取身份证信息 */
	public static IdReadInformation idInformation;
	
	/** 程序的框架容器 */
    public static BorderPane border = new BorderPane();

    /** 头部页面控制 */
    public static TopController topController;
    
    /** top盒子 */
    public static AnchorPane topAnchorPane;

    /** 首页控制 */
    public static IndexController indexController;
    
    /**index盒子 */
    public static AnchorPane indexAnchorPane;
    
    /** 过渡页面 */
    public static AnchorPane transitPage;
    
    /** 过渡页面控制 */
    public static TransitPageController transitPageController;
    
    static {
        //初始化人脸
       /* System.load(Constant.FaceDetect.OPENCV_PATH + "/opencv_java401.dll");
        System.load(Constant.FaceDetect.THFACE_PATH + "/opencv_world310.dll");
		System.load(Constant.FaceDetect.THFACE_PATH + "/tensorflow.dll");
		System.load(Constant.FaceDetect.THFACE_PATH + "/thface.dll");
        FaceRecognitionController.face.THFaceInit(Constant.FaceDetect.THFACE_PATH);*/

        //初始化头部
        FXMLLoader topLoader = new FXMLLoader();
        topLoader.setLocation(Main.class.getResource("fxml/top.fxml"));
        try {
            topAnchorPane = topLoader.load();
            topController = topLoader.getController();
            topAnchorPane.getStyleClass().add("title-bg");
        } catch (IOException e) {
            e.printStackTrace();
        }
        //初始化主页
        FXMLLoader mainLoader = new FXMLLoader();
        mainLoader.setLocation(Main.class.getResource("fxml/index2.fxml"));
        try {
            indexAnchorPane = mainLoader.load();
            indexController = mainLoader.getController();
            border.getStyleClass().add("index-bg");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    
    public static Scene buildScene() {
        try {
            FXMLLoader transLoad = new FXMLLoader();
            transLoad.setLocation(Main.class.getResource("fxml/transitPage.fxml"));
            transitPage = transLoad.load();
            transitPageController = transLoad.getController();
            System.out.println(transitPageController);
            MainView.border.setCenter(transitPage);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(buildPane(), 1920, 1080);
        scene.getStylesheets().add(Main.class.getResource("css/common.css").toExternalForm());
        scene.getStylesheets().add(Main.class.getResource("css/top.css").toExternalForm());
        return scene;
    }
    
    public static Pane buildPane() {
		// 系统启动初始化信息
		initDevice();
		return border;
	}

	/**
	 * 设备初始化校验
	 */
	private static void initDevice() {
		Task<Void> task = new Task<Void>() {
			@Override
			protected Void call() throws Exception {
				if (Constant.StartMode.DEBUG.equals(Constant.StartMode.START_MODE)) {
					// 测试直接查接口信息
//					ClientScript.justQuery("20190520144723000002");
				} else {
					// 初始化摄像头
					logger.info("初始化摄像头...");
					if(!OpenCVCamFactory.initWebCam()) {
						initStatus = false;
					}else {
						// 初始化身份证读卡器
						logger.info("初始化身份证读卡器...");
						if (Constant.IDReader.IDREADER_MODE.equals(Constant.IDReader.Mode.ENABLE)) {
							new HardWareHttp().initIdCardreader();
						}
					}
				}
				// 判断硬件初始化结果
				if (initStatus) {
					// 获取设备证书、证明数量
					logger.info("获取证书、证明数量...");
					boolean getRemainingFlag = new HttpReq().getRemainingCount();
					if(getRemainingFlag) {
						Platform.runLater(() -> {
							// 初始化成功，跳转至Web页面
							logger.info("设备初始化成功！");
							web = BizWebPlug.index();
							border.setCenter(indexAnchorPane);
							MediaPlug.welcome();
						});
					}else {
						// 获取证书、证明数量失败
						LoadPanes.showMessagePane(errorMessage,-1);
						MediaPlug.getRemainingFailure();
					}
				} else {
					Platform.runLater(() -> {
						// 设备初始化失败
						LoadPanes.showMessagePane(errorMessage,-1);
						MediaPlug.initError();
					});
				}

				return null;
			}
		};
		Thread thread = new Thread(task);
		thread.setDaemon(true);
		thread.start();
	}

	/**
	 * 返回首页
	 */
	public static void home() {
		border.getChildren().clear();
		topController.timer.cancel();
		// 设置首页证书、证明数量，保持与数据库数量一致
		indexController.setPaperNum(paperRemaining);
		indexController.setCertificateNum(certificateRemaining);
		// 使用记录id还原
		MainView.usageId = "";
		// 还原盖章机 翻页机 状态
		ClientScript.gaiZhangJiComStr = "";// 从盖章机器串口获取的数据
		ClientScript.gaiZhangJiComState = false;// 盖章机是否传值
		ClientScript.pageTurningComStr = "";// 从翻页机器串口获取的数据
		ClientScript.pageCount = 0;// 还原翻页机当前页面
		logger.info("out getHomePane...");
		// 加载首页FX页面
		Platform.runLater(() -> {
            try {
                MediaPlug.welcome();
                MainView.border.setCenter(indexAnchorPane);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
	}

}
