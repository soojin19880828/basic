package com.thfdcsoft.estate.printer.view;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Properties;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.sarxos.webcam.Webcam;
import com.thfdcsoft.estate.printer.client.Main;
import com.thfdcsoft.estate.printer.constant.Constant;
import com.thfdcsoft.estate.printer.dto.HttpReturnDto;
import com.thfdcsoft.estate.printer.dto.req.FaceValidateReq;
import com.thfdcsoft.estate.printer.dto.rspn.FaceValidateRspn;
import com.thfdcsoft.estate.printer.util.ClockPaneFactory;
import com.thfdcsoft.estate.printer.util.HttpClientFactory;
import com.thfdcsoft.estate.printer.util.JacksonFactory;
import com.thfdcsoft.estate.printer.util.OpenCVCamFactory;
import com.thfdcsoft.estate.printer.view.pages.AdminPage;
import com.thfdcsoft.estate.printer.view.pages.SmNumberPage;
import com.thfdcsoft.estate.printer.view.pages.TransitPage;
import com.thfdcsoft.estate.printer.view.plugin.BizWebPlug;
import com.thfdcsoft.estate.printer.view.plugin.CountDownPlug;
import com.thfdcsoft.estate.printer.view.plugin.MediaPlug;
import com.thfdcsoft.estate.printer.view.script.ClientScript;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.web.WebView;
import javafx.util.Duration;

public class MainView {

	private static final Logger logger = LoggerFactory.getLogger(MainView.class);

	private static boolean cam = false; // 相机状态

	private static boolean fact = false; // 人脸比对状态

	private static boolean decidemanage = false; // 后台管理状态

	private static boolean isInitiated = true; // 初始化是否成功

	public static BorderPane border = new BorderPane();

	public static WebView web;

	public static String usageId = ""; // 使用记录id

	public static String remaining = "0"; // 设备证书数量
	
	public static String lastPageCode = "0"; // 登记证明数量

	public static File logFile; // 日志地址

	public static boolean managePageState = true;
	
	//正常进入首页，身份证请求不用提示异常，否者身份证请求需要提示异常
	public static boolean normalIndex = false;

	/**
	 * 设备初始化校验
	 */
	private static void initDevice() {
		Task<Void> task = new Task<Void>() {
			@Override
			protected Void call() throws Exception {

				if (Constant.StartMode.DEBUG.equals(Constant.StartMode.START_MODE)) {
					// 测试直接查接口信息
					ClientScript.justQuery("20190520144723000002");
				} else {
					// 初始化摄像头
					logger.info("初始化摄像头……");
					for (Webcam cam : Webcam.getWebcams()) {
						logger.info("相机名称: " + cam.getName());
					}
					OpenCVCamFactory.initWebCam();
					if (!OpenCVCamFactory.isInitiated) {
						isInitiated = false;
					}
					// 初始化身份证读卡器
					logger.info("初始化身份证读卡器……");
					// 读卡器连接是否异常
					boolean readConn = true;
					if (!Constant.IDReader.IDREADER_MODE.equals(Constant.IDReader.Mode.DISABLE)) {
						String url = Constant.HardWare.HARDWARE_URL + "initIDCardreader";
						HttpReturnDto dto = null;
						try {
							String value = HttpClientFactory.getInstance().doGet(url);
							dto = JacksonFactory.readJson(value, HttpReturnDto.class);
							if(dto != null) {
								logger.info("身份证读卡器初始化结果: " + dto.isResult());
							}else {
								logger.info("身份证读卡器初始化失败");
							}
							// 清空硬件平台中保存的身份证图片
							String cleanUrl = Constant.HardWare.HARDWARE_URL + "cleanIDCardPic";
							HttpClientFactory.getInstance().doGet(cleanUrl);
						} catch (Exception e) {
							readConn = false;
							logger.info("硬件平台无法访问，请检查重试。");
							e.printStackTrace();
						} finally {
							if (dto == null) {
								isInitiated = false;
							}else if(dto != null) {
								if(!dto.isResult()) {
									isInitiated = false;	
								}
							}
						}
					}
					// 硬件平台故障
					if(!readConn) {
						if (OpenCVCamFactory.webCam != null) {
							OpenCVCamFactory.webCam.close();
						}
						if (OpenCVCamFactory.innerWebCam != null) {
							OpenCVCamFactory.innerWebCam.close();
						}
						if(OpenCVCamFactory.normalCapture.isOpened()) {
							OpenCVCamFactory.normalCapture.release();
						}
						if(OpenCVCamFactory.infraredCapture.isOpened()) {
							OpenCVCamFactory.infraredCapture.release();
						}
						logger.error("读卡器连接异常");
						try {
							System.out.println("重启硬件服务");
							// String restartHardWare = Constant.Restart.HARDWARE;
							Runtime.getRuntime().exec(Constant.Restart.HARDWARE);

							Thread.sleep(5000);
							System.out.println("重启客户端");
							// String restartQueryIII = Constant.Restart.QUERYIII;
							Runtime.getRuntime().exec(Constant.Restart.QUERYIII);

//							Thread.sleep(1000);
							System.exit(0);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}

					// 创建随机数
					Random random = new Random();
					String num = "";
					for (int i = 0; i < 6; i++) {
						num += random.nextInt(10);
					}
					logger.info("随机数为: " + num);
					// 初始化超级密码
					// String url = Constant.Manage.MANAGE_URL + "users/supperlogin";
					String url = Constant.Business.BUSINESS_URL + "client/supperlogin";
					String returnValue = HttpClientFactory.getInstance().doPost(num, url);
					HttpReturnDto dto = JacksonFactory.readJson(returnValue, HttpReturnDto.class);
					if (!dto.isResult()) {
						isInitiated = false;
					} else {
						String respJson = dto.getRespJson();
						logger.info("数据库添加成功: " + respJson);
						// 写入配置文件
						Properties p = new Properties();
						String profilepath = "supper.properties";
						try {
							// 读取配置文件supper.properties
							p.load(new FileInputStream(profilepath));
							// 获取配置文件中的相关内容
							String str = p.getProperty("supperpwd");
							logger.info("str:" + str);

							OutputStream fos = new FileOutputStream(profilepath);
							p.setProperty("supperpwd", respJson); // 超级密码添加在配置文件
							p.store(fos, "Update '" + "supperpwd" + respJson);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}

					// 判断设备初始化结果
					if (isInitiated) {
						// 还原管理员验证过程中各环节状态
						cam = false; // 相机状态
						fact = false; // 人脸状态
						decidemanage = false; // 判断manage是否正常连接
						managePageState = true;// 还原管理员页面状态
						// 初始化状态还原
						isInitiated = true;
						// 还原日志文件
						logFile = null;
						// 使用记录id还原
						usageId = "";
						// 设置人脸识别后管理员状态为false
						ClientScript.managerState = false;
						// 停止人脸跟踪 取消cam与人脸识别页面绑定
						OpenCVCamFactory.facePageState = false;
						// 还原盖章机 翻页机 状态
						ClientScript.gaiZhangJiComStr = "";// 从盖章机器串口获取的数据
						ClientScript.gaiZhangJiComState = false;// 盖章机是否传值
						ClientScript.pageTurningComStr = "";// 从翻页机器串口获取的数据
						ClientScript.pageTurningComState = false;// 翻页机是否传值
						ClientScript.pageCount = 0;// 还原翻页机当前页面
//						FaceDetectPage.count = 0;// 还原人脸验证失败次数

						Platform.runLater(() -> {
							// 初始化成功，跳转至Web页面
							logger.info("设备初始化成功！");
							web = BizWebPlug.index();
						});
					} else {
						Platform.runLater(() -> {
							// 设备初始化失败，即将退出
							TransitPage.setTransitText("设备初始化失败，程序即将退出！");
							CountDownPlug.startCountDown(5, 1);
							MediaPlug.initError();
						});
					}
				}

				return null;
			}
		};
		Thread thread = new Thread(task);
		thread.setDaemon(true);
		thread.start();
	}

	public static Scene buildScene() {
		Scene scene = new Scene(buildPane(), 1920, 1080);
		scene.getStylesheets().add(Main.class.getResource("css/main.css").toExternalForm());
		return scene;
	}

	
	/**
	 * 返回首页
	 */
	public static void home() {
		// 正常进入index首页  避免读取身份证方法异常倒计时
		normalIndex = true;
		Platform.runLater(() -> {
			String url = Constant.Business.BUSINESS_URL + "index";
			logger.info(url);
			web.getEngine().load(url);
		});
		// 改变可识别现场照片生成状态 停止人证比对方法
		OpenCVCamFactory.saveDetPicState = false;
		// 还原人证比对结果
		if(ClientScript.faceAndID!=null) {
			ClientScript.faceAndID.resetFaceComparisonState();
		}
		CountDownPlug.stopCountDown();
		// 还原管理员验证过程中各环节状态
		cam = false; // 相机状态
		fact = false; // 人脸状态
		decidemanage = false; // 判断manage是否正常连接
		managePageState = true;// 还原管理员页面状态
		// 初始化状态还原
		isInitiated = true;
		// 还原日志文件
		logFile = null;
		// 使用记录id还原
		usageId = "";
		// 设置人脸识别后管理员状态为false
		ClientScript.managerState = false;
		// 停止人脸跟踪 取消cam与人脸识别页面绑定
		OpenCVCamFactory.facePageState = false;
		// 还原盖章机 翻页机 状态
		ClientScript.gaiZhangJiComStr = "";// 从盖章机器串口获取的数据
		ClientScript.gaiZhangJiComState = false;// 盖章机是否传值
		ClientScript.pageTurningComStr = "";// 从翻页机器串口获取的数据
		ClientScript.pageTurningComState = false;// 翻页机是否传值
		ClientScript.pageCount = 0;// 还原翻页机当前页面
		ClientScript.faceAndID.resetID();// 还原身份证读取
		if (ClientScript.thread != null) {
			ClientScript.thread.interrupt();
		}
		// 登记证明扫描保存的数据清空
		SmNumberPage.data.clear();
		SmNumberPage.qrCodeTF.setText(" ");
		SmNumberPage.smNumber.clear();
	}

	/**
	 * 跳转到密码登陆页面
	 */
	public static void pwdlogin() {
		Platform.runLater(() -> {
			CountDownPlug.countDownStart = false;
			CountDownPlug.startCountDown(Constant.StartMode.COUND_DOWN);
			String url = Constant.Business.BUSINESS_URL + "client/pwdlogin";
			web.getEngine().load(url);
		});
	}

	public static Pane buildPane() {
		/*// Head
		HBox head = new HBox();
		head.getStyleClass().add("head-box");

		// 标题，设备名称
		HBox title = new HBox();
		title.getStyleClass().add("title-box");
		Text deviceName = new Text(Constant.DeviceInfo.DEVICE_NAME);
		deviceName.getStyleClass().add("device-name");
		title.getChildren().add(deviceName);

		// 倒计时
		VBox exit = new VBox();
		exit.getStyleClass().add("exit-box");
		CountDownPlug.getCountDownText().getStyleClass().add("count-down");
		CountDownPlug.getCountDownText().setTranslateX(-520);
		CountDownPlug.getCountDownText().setTranslateY(45);
		exit.getChildren().add(CountDownPlug.getCountDownText());

		// 时钟
		ClockPaneFactory clock = new ClockPaneFactory();
		clock.setTranslateX(-250);
		clock.setTranslateY(50);
		// 创建一个handler
		EventHandler<ActionEvent> eventHandler = e -> {
			clock.setCurrentTime();
		};
		Timeline animation = new Timeline(new KeyFrame(Duration.millis(500), eventHandler));
		animation.setCycleCount(Timeline.INDEFINITE);
		animation.play();

		// 不动产标志
		Image logo = new Image(Main.class.getResource("images/tubiao.png").toExternalForm());
		ImageView logoView = new ImageView(logo);
		logoView.setTranslateX(10);
		logoView.setTranslateY(0);

		// Control Label
		// 连续点击不动产图标跳转至控制台
		logoView.setOnMouseClicked(new EventHandler<MouseEvent>() {
			private int clicked = 0; // 点击次数

			@Override
			public void handle(MouseEvent event) {
				clicked++;
				if (clicked >= 3 && managePageState) {
					clicked = 0;
					managePageState = false;
					String value = ClientScript.getPageFlag();
					// home:当前页位首页 readError:当前页为编号识别错误页面
					if ("home".equals(value) || "readError".equals(value) || "printError".equals(value) || "pageError".equals(value)) {
						// 选择人脸登陆或密码登陆
						logger.info(this.getClass().getName() + "--"
								+ Thread.currentThread().getStackTrace()[1].getMethodName() + "--" + "进入人脸登陆或密码登陆页面");
						buildPane2();
						// alreadyPay:当前页为打印页面
					} else if ("alreadyPay".equals(value) || "print".equals(value)|| "selectType".equals(value)) {
						home();
					}
				}
			}
		});

		head.getChildren().addAll(logoView, title, exit, clock);
		border.setTop(head);

		// 通知标志
		Image notice = new Image(Main.class.getResource("images/tz.png").toExternalForm());
		ImageView noticeView = new ImageView(notice);
		noticeView.setTranslateX(10);
		noticeView.setTranslateY(20);

		// 通知
		Text noticeText = new Text(Constant.DeviceInfo.NOTICE_TEXT);
		noticeText.getStyleClass().add("notice-text");
		noticeText.setTranslateX(100);
		noticeText.setTranslateY(67);

		// Foot
		BorderPane foot = new BorderPane();
		foot.getStyleClass().add("foot-box");
		Text technicalSupport = new Text(Constant.DeviceInfo.TECHNICAL_SUPPORT);
		technicalSupport.getStyleClass().add("foot-text");
		foot.setCenter(technicalSupport);
		foot.getChildren().addAll(noticeView, noticeText);
		border.setBottom(foot);*/

		// Init
		// 系统启动初始化信息
		border.setCenter(TransitPage.getTransit());
		initDevice();
		return border;
	}

	public static Thread checkDeviceThread;

	/**
	 * 人脸或密码登陆
	 */
	public static void buildPane2() {

		HBox body = new HBox();
		body.getStyleClass().add("transit-box");

		Text deviceName = new Text("正在对设备进行检查,请稍等!");
		deviceName.getStyleClass().add("device-name");

		// 系统启动初始化信息
		body.getChildren().addAll(deviceName);
		border.setCenter(body);

		Task<Void> task = new Task<Void>() {
			@Override
			protected Void call() throws Exception {

				// 判断摄像头状态
				boolean flag = OpenCVCamFactory.manageInitCam();
				cam = flag;
				logger.info("cam状态:" + cam);
				// 人脸比对服务
				try {
					/*FaceValidateReq req = new FaceValidateReq();
					req.setDetPicFile("131");
					req.setIdNumber("420621199807114820");
					req.setIdPicPath("");
					req.setIdPicFile("");
					req.setDetPicPath("");
					String rspnJson = HttpClientFactory.getInstance().doPost(JacksonFactory.writeJson(req),
							Constant.FaceDetect.FACE_DETECT_URL + "faceValidate");
					FaceValidateRspn rspn = JacksonFactory.readJson(rspnJson, FaceValidateRspn.class);
					logger.info("rspn:" + rspn.getResult());

					if (rspn != null && !"".equals(rspn)) {
						fact = true;
					} else {
						fact = false;
					}*/
					logger.info("fact状态:" + fact);
				} catch (Exception e) {
					fact = false;
					e.printStackTrace();
				} finally {
					// 判断manage是否正常连接
					String url = Constant.Manage.MANAGE_URL + "users/decideManage";
					HttpReturnDto readJson = null;
					try {
						String doGet = HttpClientFactory.getInstance().doGet(url);
						readJson = JacksonFactory.readJson(doGet, HttpReturnDto.class);
						logger.info("readJson.isResult()==" + readJson.isResult());
					} catch (Exception e) {
						e.printStackTrace();
					} finally {
						if (readJson == null || !readJson.isResult()) {
							decidemanage = false;
						} else {
							decidemanage = true;
						}
						logger.info("decidemanage状态:" + decidemanage);
						/*if (cam && fact && decidemanage) { // 人脸识别可用
							ClientScript clientScript = new ClientScript();
							// 设置manageState状态为true； 标记当前人脸识别为管理员模式式
							ClientScript.managerState = true;
							Platform.runLater(() -> {
								// 启动倒计时
								clientScript.forwardFace();
							});
						} else */if ((!cam && decidemanage) || (!fact && decidemanage)) { // 人脸识别不可用选择输入密码
							logger.info("进入账号密码");
							MainView.pwdlogin();
						} else if (!decidemanage) { // 超级密码登陆
							logger.info("超级密码登陆");
							Platform.runLater(() -> {
								// 启动倒计时
								CountDownPlug.startCountDown();
								MainView.border.setCenter(AdminPage.buildPage());
							});
						} else {
							Platform.runLater(() -> {
								TransitPage.attentionPage("设备多个模块存在故障，请联系运维人员！", -1);
							});
						}
					}
				}

				return null;
			}
		};
		checkDeviceThread = new Thread(task);
		checkDeviceThread.setDaemon(true);
		checkDeviceThread.start();

	}

}
