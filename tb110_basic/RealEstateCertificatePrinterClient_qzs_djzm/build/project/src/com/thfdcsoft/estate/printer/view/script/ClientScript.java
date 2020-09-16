package com.thfdcsoft.estate.printer.view.script;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamResolution;
import com.thfdcsoft.estate.printer.client.Main;
import com.thfdcsoft.estate.printer.constant.Constant;
import com.thfdcsoft.estate.printer.dto.EstateInfo;
import com.thfdcsoft.estate.printer.dto.HttpReturnDto;
import com.thfdcsoft.estate.printer.dto.RealEstateInfo;
import com.thfdcsoft.estate.printer.dto.TerminalInfo;
import com.thfdcsoft.estate.printer.dto.UsageRecord;
import com.thfdcsoft.estate.printer.dto.req.QueryPaperReq;
import com.thfdcsoft.estate.printer.dto.req.SubmitPrintReq;
import com.thfdcsoft.estate.printer.dto.req.SubmitUsageReq;
import com.thfdcsoft.estate.printer.dto.rspn.Certnumber;
import com.thfdcsoft.estate.printer.httpReq.HttpReq;
import com.thfdcsoft.estate.printer.util.*;
import com.thfdcsoft.estate.printer.util.serialException.SendDataToSerialPortFailure;
import com.thfdcsoft.estate.printer.util.serialException.SerialPortOutputStreamCloseFailure;
import com.thfdcsoft.estate.printer.view.MainView;
import com.thfdcsoft.estate.printer.view.pages.FaceAndIDPaneController;
import com.thfdcsoft.estate.printer.view.pages.HandlePage;
import com.thfdcsoft.estate.printer.view.pages.SmNumberPage;
import com.thfdcsoft.estate.printer.view.pages.TransitPage;
import com.thfdcsoft.estate.printer.view.plugin.BizWebPlug;
import com.thfdcsoft.estate.printer.view.plugin.CountDownPlug;
import com.thfdcsoft.estate.printer.view.plugin.MediaPlug;

import cn.hutool.core.util.ImageUtil;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;
import sun.management.snmp.util.SnmpNamedListTableCache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/**
 * JS 调用方法类
 *
 * @author 张嘉琪
 */
public class ClientScript {

    private static final Logger logger = LoggerFactory.getLogger(ClientScript.class);

    public static boolean managerState = false;

    public static DocxPrint docxP;

    // public static ComCommunication comCommunication;

    // 翻页机
    private static ComCommunication comPageTurning = new ComCommunication(Constant.COM.PAGE_RETURNING_COM);
    // 登记证明盖章机
    public static ComCommunication djzmComSealMachine = new ComCommunication(Constant.COM.DJZM_GAI_ZHANG_JI);
    // 权证书盖章机
    public static ComCommunication qzsComSealMachine = new ComCommunication(Constant.COM.QZS_GAI_ZHANG_JI);

    private static File source;// 文件复制 源文件
    private static File dest;// 文件复制 保存文件
    private static File check;// 对证书打印前两页进行拍照 检查绝对路径下是否存在文件，并进行删除件
    private static File ocrFile; // 操作证书编号识别文件作

    public static FaceAndIDPaneController faceAndID;

    public static String gaiZhangJiComStr;// 从盖章机器串口获取的数据
    public static boolean gaiZhangJiComState;// 盖章机是否传值

    public static String pageTurningComStr;// 从翻页机器串口获取的数据
    public static boolean pageTurningComState;// 翻页机是否传值

    // public static Webcam cam = null;// 调用内部摄像头

    public static int pageCount = 0;// 当前页

    // 判断要不要继续打印
    public static int num;
    
    // 登记证明扫码数据转换的json数据
    public static String scanJson;

    /**
     * Zenith - help(bszl 办事指南, flfg 法律法规, sfbz 收费标准)
     */
    public void help(String helpType) {
        System.out.println("helpType: " + helpType);
        String url = Constant.Business.BUSINESS_URL + "help/detail?helpType=" + helpType;
        System.out.println(url);
        Platform.runLater(() -> {
            try {
                MainView.web = BizWebPlug.index();
                MainView.web.getEngine().load(url);
            } catch (Exception e) {
                TransitPage.attentionPage("数据查询出现异常，请联系工作人员。", -1);
                e.printStackTrace();
            }
        });
    }

    /*
     * 进入人脸识别页面opencv
     */
    public void forwardFace() {

        logger.info("in forwardFace...");
        // --------------------------------------
        // 启动倒计时
       /* CountDownPlug.startCountDown(90);
        ClientScript.saveUsageRecord();*/
        // -----------------------------------------
        // 启动倒计时
//        CountDownPlug.startCountDown(90);
////        MainView.adminFlag = false;
//		MediaPlug.readID_FaceDetec();
//        FXMLLoader face = new FXMLLoader();
//        System.out.println("setLocation" + Main.class.getResource("fxml/FaceAndIDPane.fxml"));
//        face.setLocation(Main.class.getResource("fxml/FaceAndIDPane.fxml"));
//        try {
//            MainView.border.getChildren().remove(2);
//            logger.info("获取人脸识别界面...");
//            BorderPane faceand = (BorderPane) face.load();
//            MainView.border.setCenter(faceand);
//            faceAndID = face.getController();
//            faceAndID.addWebCam();
//            faceAndID.resetID();
//            faceAndID.validate();
//        } catch (Exception e) {
//            e.printStackTrace();
//            logger.error("拼装人脸识别界面失败...");
//        }
        
        // 暂时注释掉
		MainView.normalIndex = false;

		// 判断摄像头是否关闭，若已经关闭则重新打开
		if (!OpenCVCamFactory.cameraActive) {
			OpenCVCamFactory of = new OpenCVCamFactory();
			of.startCamera();
		}
		// 开启 摄像头与页面绑定
		OpenCVCamFactory.facePageState = true;
		// 重置身份证读取状态
		OpenCVCamFactory.succeedReadID = false;
        FXMLLoader face = new FXMLLoader();
        face.setLocation(Main.class.getResource("fxml/FaceAndIDPane.fxml"));
		if (OpenCVCamFactory.normalCapture.isOpened() && OpenCVCamFactory.infraredCapture.isOpened()) {
            try {
                MainView.border.getChildren().remove(2);
                BorderPane faceand = (BorderPane) face.load();
                MainView.border.setCenter(faceand);
                faceAndID = face.getController();
                // 重置验证次数
                faceAndID.reSetCount();
                // 初始化身份证信息
                faceAndID.addWebCam();
                faceAndID.resetID();
            } catch (Exception e) {
                e.printStackTrace();
            }
//			MainView.border.setCenter(FaceDetectPage.getPage());
			// 语音播报
			MediaPlug.readID_FaceDetec();
			// 启动倒计时
			CountDownPlug.countDownStart = true;
			CountDownPlug.startCountDown(50);
			// 进行身份证读取
            faceAndID.readIDCard();

			String path = PropertyFactory.getPath() + "opencv";
			String detPicPathTemp = path + "/faces/" + "infraredPic" + ".jpg";
			// 人证比对
            faceAndID.faceComparison(detPicPathTemp);
		} else {
			TransitPage.attentionPage("设备摄像头存在故障，请联系工作人员。", -1);
		}
    }

    /**
     * 保存使用记录
     */
    public static void saveUsageRecord() {
        // 生成日志文件
        String userLogPath = Constant.FileDir.LOG_DIR + DateFactory.getCurrentDateString("yyyyMMddHHmmss") + "_"
                + Constant.SuffixName.LOG;
        File log = FileFactory.buildFile(userLogPath);
        logger.info("日志文件地址=" + userLogPath);
        if (!log.exists()) {
            TransitPage.attentionPage("日志文件生成失败，请稍后重试。", 10);
        }
        SubmitUsageReq req = new SubmitUsageReq();
        req.setFullName(faceAndID.getFullName());
        req.setIdNumber(faceAndID.getIdNumber());
        req.setDeviceNumber(Constant.DeviceInfo.DEVICE_NUMBER);
        req.setIdPic(ImageFactory.getImageStr(faceAndID.getIdPicPath()));
        req.setDetPic(ImageFactory.getImageStr(faceAndID.getDetPicPath()));
        req.setLogPath(userLogPath);
        
      //----------------------------------------
        // 本机测试用，项目中需要注释
        /*FXMLLoader face = new FXMLLoader();
        face.setLocation(Main.class.getResource("fxml/FaceAndIDPane.fxml"));
        try {
			BorderPane faceand = (BorderPane) face.load();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        faceAndID = face.getController();
        faceAndID.setID();
        req.setFullName("gaotuo");
        req.setIdNumber("421126198808280119");
        req.setDeviceNumber(Constant.DeviceInfo.DEVICE_NUMBER);
        req.setIdPic(ImageFactory.getImageStr("D:/AgentRecord/pics/id/20180202094429_421126198808280119.jpg"));
        req.setDetPic(ImageFactory.getImageStr("D:/AgentRecord/pics/det/20180202094429_421126198808280119.jpg"));
        req.setLogPath("D:/AgentRecord/logs/20180331171150_.txt");*/
        //------------------------------------------------------------------

        logger.info("使用记录信息：" + req.toString());
        String url = Constant.Business.BUSINESS_URL + "client/saveUsageRecord";
        String usageJson = JacksonFactory.writeJson(req);
        try {
            String resp = HttpClientFactory.getInstance().doPost(usageJson, url);
            HttpReturnDto returnDto = JacksonFactory.readJson(resp, HttpReturnDto.class);
            logger.info("保存记录结果：" + returnDto.isResult());
            if (returnDto.isResult()) {
                // 使用数据保存成功 进行数据查询
                System.out.println("客户端保存完使用记录：" + JacksonFactory.writeJson(returnDto));
                UsageRecord record = JacksonFactory.readJson(returnDto.getRespJson(), UsageRecord.class);
                System.out.println("11.使用记录保存成功；" + record.getRecordId());
                // 当前使用记录的usageId
                MainView.usageId = record.getRecordId();
                // 日志保存
                MainView.logFile = log;
                String text = DateFactory.getCurrentDateString("yyyyMMddHHmmss") + "__"
                        + faceAndID.getIdNumber() + ":使用记录保存成功！";
                FileFactory.writeTXTFile(log, text);
                // 使用数据保存成功后，跳转至选择打印类型页面
                selectPrintType();
            } else {
                String text = DateFactory.getCurrentDateString("yyyyMMddHHmmss") + "__"
                        + faceAndID.getIdNumber() + ":使用记录保存失败！";
                FileFactory.writeTXTFile(log, text);
                TransitPage.attentionPage("使用记录保存失败，请重新进行操作。", 8);
            }
        } catch (Exception e) {
            String text = DateFactory.getCurrentDateString("yyyyMMddHHmmss") + "__" + faceAndID.getIdNumber()
                    + ":使用记录保存、更新设备证书数量  过程失败！";
            FileFactory.writeTXTFile(log, text);
            TransitPage.attentionPage("使用记录保存过程出现异常，请联系工作人员。", -1);
            e.printStackTrace();
        }
    }
    
    /**
     * 查询证书数量（根据选择打印类型查询设备证书/证明数量）
     */
    public boolean getRemainingPaperCount(String selectType) {
    	// 查询剩余证书数量
        String queryPaperUrl = Constant.Business.BUSINESS_URL + "client/querypaper";
        QueryPaperReq queryPaperReq = new QueryPaperReq();
        queryPaperReq.setDeviceNumber(Constant.DeviceInfo.DEVICE_NUMBER);
        try {
	    	if(Constant.SelectType.DJZM.equals(selectType)) {
	    		queryPaperReq.setTerminalId(Constant.TerminalInfo.DJZM_TYPE);
	    	}else if(Constant.SelectType.QZS.equals(selectType)) {
	    		queryPaperReq.setTerminalId(Constant.TerminalInfo.QZS_TYPE);
	    	}
	    	 String queryPaperRspn = HttpClientFactory.getInstance().doPost(JacksonFactory.writeJson(queryPaperReq), queryPaperUrl);
			HttpReturnDto queryPaperReturnDto = JacksonFactory.readJson(queryPaperRspn, HttpReturnDto.class);
			if (queryPaperReturnDto.isResult()) {
	            TerminalInfo terminalInfo = JacksonFactory.readJson(queryPaperReturnDto.getRespJson(),
	                    TerminalInfo.class);
	            System.out.println("当前设备剩余"+selectType+"纸张数量==" + terminalInfo.getRemainingPaper());
	            // 当前设备剩余纸张数量
	            MainView.remaining = terminalInfo.getRemainingPaper();
	            String text = ":更新设备"+selectType+"数量成功！";
	            FileFactory.writeTXTFile(MainView.logFile, text);
	        } else {
	            String text = ":更新设备"+selectType+"数量失败！";
	            FileFactory.writeTXTFile(MainView.logFile, text);
	            TransitPage.attentionPage("设备证书数量获取失败，请联系工作人员。", -1);
	            MainView.web.getEngine().executeScript("setPageFlag('pageError')");
	            return false;
	        }
		} catch (Exception e) {
			String text = "获取证书/证明数量失败！";
			FileFactory.writeTXTFile(MainView.logFile, text);
			TransitPage.attentionPage("查询证书/证明数量异常，请联系工作人员。", -1);
			MainView.web.getEngine().executeScript("setPageFlag('pageError')");
			e.printStackTrace();
			return false;
		}
		return true;
    }
    
    /**
     * 跳转至选择打印类型页面
     */
    public static void selectPrintType() {
    	 System.out.println("人脸核验成功，当前成功次数为第："+FaceAndIDPaneController.faceValidateSuccess+"次");
    	CountDownPlug.startCountDown(30);
    	String url = Constant.Business.BUSINESS_URL + "client/selectPrintType";
        System.out.println(url);
        // 语音播报(请选择打印类型)
        MediaPlug.selectPrintType();
        Platform.runLater(() -> {
            try {
                MainView.web.getEngine().load(url);
            } catch (Exception e) {
                TransitPage.attentionPage("页面异常，请联系工作人员。", -1);
                e.printStackTrace();
            }
        });
    }
    
    // 登记证明类型打印
    public void djzmPrintType() {
    	logger.info("进入登记证明操作");
    	if(getRemainingPaperCount(Constant.TerminalInfo.DJZM_TYPE)) {
    		if ("1".equals(Constant.StartMode.SEARCH_METHOD)) {
    			// 1.刷身份证直接查询
    			forwardPrint();
    		} else {
    			// 2.扫描直接查询
    			ClientScript.forwardSm();
    		}
    	}
    }
    
    // 权证书类型打印
    public void qzsPrintType() {
    	logger.info("进入权证书操作");
    	if(getRemainingPaperCount(Constant.TerminalInfo.QZS_TYPE)) {
    		queryMsg(MainView.usageId, MainView.logFile);
    	}
    }
    
   
	// 跳转可打印列表页面（登记证明）
	public static void forwardPrint() {
		// 判断内部登记证明高拍仪是否开启 关闭则开启
        if (OpenCVCamFactory.innerWebCam == null || !OpenCVCamFactory.innerWebCam.isOpen()) {
            // 内部摄像头常开 随时准备进行拍照
            List<Webcam> camList = Webcam.getWebcams();
            for (Webcam webcam : camList) {
                logger.info(webcam.getName());
                if (Constant.WebCam.INNER_CAM.equals(webcam.getName())) {
                    OpenCVCamFactory.innerWebCam = webcam;
                    Dimension[] nonStandardResolutions = new Dimension[] { WebcamResolution.PAL.getSize(),
    						WebcamResolution.VGA.getSize(), new Dimension(1400, 1000), };
                    OpenCVCamFactory.innerWebCam.setCustomViewSizes(nonStandardResolutions);
                    OpenCVCamFactory.innerWebCam.setViewSize(new Dimension(1400, 1000));
                    // 打开内部摄像头 打开失败 进行提示
                    if (!OpenCVCamFactory.webCam.open()) {
                        String text = "登记证明forwardPrint  1.内部摄像头存在异常！";
                        FileFactory.writeTXTFile(MainView.logFile, text);
                        Platform.runLater(() -> {
                            TransitPage.attentionPage("内部摄像头存在异常，请联系工作人员。", -1);
                        });
                    } else {
                        String text = "登记证明forwardPrint  1.内部摄像头状态正常！";
                        FileFactory.writeTXTFile(MainView.logFile, text);
                    }
                }
            }
        } else {
            logger.info("内部摄像头已开启，且正常！");
        }
		
		CountDownPlug.resetCountDown();
		Platform.runLater(() -> {
			// 跳转至不动产登记数据列表
			String url = Constant.Business.BUSINESS_URL + "client/print";
			try {
				MainView.web.getEngine().load(url);
			} catch (Exception e) {
				String text = DateFactory.getCurrentDateString("yyyyMMddHHmmss") + "__"
                        + faceAndID.getIdNumber() + ":查询数据出现异常！";
                FileFactory.writeTXTFile(MainView.logFile, text);
                TransitPage.attentionPage("数据查询出现异常，请联系工作人员。", -1);
                e.printStackTrace();
			}
			System.out.println("跳转数据列表页面成功");
		});
	}
	
	// 跳转扫描查询界面（登记证明）
	public static void forwardSm() {
		System.out.println("in forwardSm....");
		CountDownPlug.resetCountDown();
		MediaPlug.barcodeScan();
		// 初始化扫描接口（权证书、登记证明一体机的扫描头是USB接口，不需像COM口一样监听数据，也不需初始化，只要有文本框，就可以接收到数据，因此暂时注释）
		/*Task<Void> task = new Task<Void>() {
			@Override
			protected Void call() throws Exception {
				HttpReq.scaner();
				return null;
			}
		};
		Thread thread = new Thread(task);
		thread.setDaemon(true);
		thread.start();*/
		Platform.runLater(() -> {
			MainView.border.setCenter(SmNumberPage.child());
		});
	}
    
    

    /**
     * Zenith - 测试请求业务层查询数据
     */
    public static void justQuery(String recordId) {
        String url = Constant.Business.BUSINESS_URL + "client/queryhouse?recordId=" + recordId;
        System.out.println(url);
        Platform.runLater(() -> {
            try {
            	MainView.web = BizWebPlug.index();
                MainView.web.getEngine().load(url);
            } catch (Exception e) {
                TransitPage.attentionPage("数据查询出现异常，请联系工作人员。", -1);
                e.printStackTrace();
            }
        });
    }

    /*
     * 请求业务层查询数据
     */
    public static void queryMsg(String recordId, File log) {
        CountDownPlug.resetCountDown();
        // 判斷内部摄像头是否开启 开启则关闭
        if (OpenCVCamFactory.webCam == null || !OpenCVCamFactory.webCam.isOpen()) {
            // 内部摄像头常开 随时准备进行拍照
            List<Webcam> camList = Webcam.getWebcams();
            for (Webcam webcam : camList) {
                logger.info(webcam.getName());
                if (Constant.WebCam.WEBCAM_NAME.equals(webcam.getName())) {
                    OpenCVCamFactory.webCam = webcam;
                    Dimension[] nonStandardResolutions = new Dimension[]{WebcamResolution.PAL.getSize(),
                            WebcamResolution.VGA.getSize(), new Dimension(1280, 960),};
                    OpenCVCamFactory.webCam.setCustomViewSizes(nonStandardResolutions);
                    OpenCVCamFactory.webCam.setViewSize(new Dimension(1280, 960));
                    // 打开内部摄像头 打开失败 进行提示
                    if (!OpenCVCamFactory.webCam.open()) {
                        String text = "queryMsg 1.内部摄像头存在异常！";
                        FileFactory.writeTXTFile(MainView.logFile, text);
                        Platform.runLater(() -> {
                            TransitPage.attentionPage("内部摄像头存在异常，请联系工作人员。", -1);
                        });
                    } else {
                        String text = "queryMsg 1.内部摄像头状态正常！";
                        FileFactory.writeTXTFile(MainView.logFile, text);
                    }
                }
            }
        } else {
            logger.info("内部摄像头已开启，且正常！");
        }

        logger.info("recordId= " + recordId);
        String url = Constant.Business.BUSINESS_URL + "client/queryhouse" + "?recordId=" + recordId;
        Platform.runLater(() -> {
            try {
                MainView.web.getEngine().load(url);
            } catch (Exception e) {
                String text = DateFactory.getCurrentDateString("yyyyMMddHHmmss") + "__"
                        + faceAndID.getIdNumber() + ":查询数据出现异常！";
                FileFactory.writeTXTFile(log, text);
                TransitPage.attentionPage("数据查询出现异常，请联系工作人员。", -1);
                e.printStackTrace();
            }
        });
    }

    /**
     * 打印方法 此方法处理代码冗余问题 初次测试正常 未进行大量测试
     *
     * @param value 打印数据json
     * @param sum   查询数据总条数 用于处理 继续打印 按钮的显示隐藏控制
     */
    public void print(String value, String sum) {
        // 停止倒计时
        CountDownPlug.stopCountDown();
        // 语音播报
        MediaPlug.print();

        HttpClientFactory.closeClient();

        logger.info("打印数据长度：" + value.length());
        // 将即将打印的数据写入日志
        FileFactory.writeTXTFile(MainView.logFile, "打印方法 1.用户选中的打印数据：" + value);
        if (thread != null && !thread.isInterrupted()) {
            thread.interrupt();
        }
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                // 判断要不要继续打印
                int parseInt = Integer.parseInt(sum); // 打印列表一共有sum条数据

                List<RealEstateInfo> infoList = JacksonFactory.readJsonList(value, RealEstateInfo.class);
                // 所需证书数量
                int count = infoList.size();
             // 打印任务中剩余还未打印证书的数量
                num = parseInt - count; 
                // 设备中剩余证书数量
                int remaining = Integer.parseInt(MainView.remaining);
                if (count > remaining) {
                    int c = count;
                    int r = remaining;
                    String text = "打印方法 2.计划打印" + c + "本证书,剩余空白证书数量仅有" + r + "本，证书不足！";
                    FileFactory.writeTXTFile(MainView.logFile, text);

                    Platform.runLater(() -> {
                        MediaPlug.pageCountError();
                        TransitPage.attentionPage("计划打印" + c + "本证书,剩余空白证书数量仅有" + r + "本，请联系工作人员及时补充！", 10);
                    });
                    return null;
                }

                boolean gaiZhangJiState = true;
                boolean pageTurningState = true;
                for (int i = 0; i < infoList.size(); i++) {
                    // System.gc();
                    docxP = new DocxPrint();
                    // 检查数据 存在空数据 跳过
                    if (!checkData(infoList.get(i))) {
                        String text = "打印方法 3.此条记录不满足打印条件=" + infoList.get(i).toString();
                        FileFactory.writeTXTFile(MainView.logFile, text);
                        continue;
                    }

                    // 打印提示
                    int f = i + 1;
                    Platform.runLater(() -> {
                        MainView.web.getEngine().executeScript("doPrint('" + f + "')");
                    });

                    // 查杀word进程 避免word占用文件导致程序无法操作
                    DocxPrint.comfirmSingleProcess("WINWORD");
//
//                    // 处理宗地图、分布图图片
//                    String zongDiTuSaveUrl = Constant.FileDir.ZONGDITU;
//                    // 检查是否已经存在宗地图图片 存在则先删除
//                    ClientScript.cleanPic(zongDiTuSaveUrl);
//                    // 生成宗地图
//                    boolean zongDiTuFlag = ImageFactory.generateImage(infoList.get(i).getZongditu(), zongDiTuSaveUrl);
//                    String fenBuTuSaveUrl = Constant.FileDir.FENBUTU;
//                    // 检查是否已经存在分布图图片 存在则先删除
//                    ClientScript.cleanPic(fenBuTuSaveUrl);
//                    // 生成分布图
//                    boolean fenBuTuFlag = ImageFactory.generateImage(infoList.get(i).getFenbutu(), fenBuTuSaveUrl);
//                    if (!zongDiTuFlag || !fenBuTuFlag) {
//                        MediaPlug.printError();
//                        String text = "打印方法 4.失败！  地图生成状态：" + zongDiTuFlag + "   分布图生成状态：" + fenBuTuFlag;
//                        FileFactory.writeTXTFile(MainView.logFile, text);
//                        showAttention("生成宗地图、分布图出现异常，请联系工作人员。", -1);
//                        return null;
//                    } else {
//                        String text = "打印方法 5.成功！  地图生成状态：" + zongDiTuFlag + "   分布图生成状态：" + fenBuTuFlag;
//                        FileFactory.writeTXTFile(MainView.logFile, text);
//                    }

                    // 处理宗地图、分布图图片
                    if (!buildFenbutuAndZongditu(infoList.get(i))) {
                        return null;
                    }

                    // 生成word文档
                    boolean makeFlag = docxP.makeFile(infoList.get(i));
                    if (!makeFlag) {
                        MediaPlug.printError();
                        String text = "打印方法 6.生成打印文件状态：" + makeFlag;
                        FileFactory.writeTXTFile(MainView.logFile, text);
                        showAttention("生成打印文件失败，请联系工作人员。", -1);
                        return null;
                    } else {
                        String text = "打印方法 7.生成打印文件状态：" + makeFlag;
                        FileFactory.writeTXTFile(MainView.logFile, text);
                    }
                    // 保存生成的word文件
                    String sourcePath = Constant.QRCode.DOCFILEOUT;
                    String destPath = Constant.FileDir.DOCX_DIR + DateFactory.getCurrentDateString("yyyyMMddHHmmss")
                            + "_" + ".docx";
                    boolean saveState = ClientScript.saveFile(sourcePath, destPath);
                    if (!saveState) {
                        MediaPlug.printError();
                        String text = "打印方法 8.保存打印文件状态：" + saveState;
                        FileFactory.writeTXTFile(MainView.logFile, text);
                        showAttention("保存打印文件失败，请联系工作人员。", -1);
                        return null;
                    } else {			
                        String text = "打印方法 9.保存打印文件状态：" + saveState;
                        FileFactory.writeTXTFile(MainView.logFile, text);
                    }

                    //	查询翻页机状态
                    if(!sendCheckCommand()) {
                    	return null;
                    }
                 
                    // 送证
             	   	if(!sendCertificateCommand()) {
             	   		return null;
             	   	}
             	   
         		   // 再次查询翻页机状态
             	   	// 送证机串口返回正确数据，需要把送证的这个操作完成，整个送证操作完成大概需要7秒左右
             	   	Thread.sleep(7000);
         		   if(sendCheckCommand()) {
         			   // 开始打证
         			   check = new File(Constant.QRCode.DOCFILEOUT);
         			   if (check.exists()) {
                           String url = Constant.HardWare.HARDWARE_URL + "printDoc";
                           HttpClientFactory.getInstance().doPost(Constant.QRCode.DOCFILEOUT, url);
                       } else {
                           MediaPlug.printError();
                           showAttention("系统异常，请联系工作人员。", -1);
                           return null;
                       }
         		   }else {
         			   return null;
         		   }

                    // 接收翻页机com5发送数据 共发送3次
                    logger.info("当前pageTurningState = " + pageTurningState);
                    int countPageTurn = 0;
                    while (pageTurningState) {
                        // 翻页机翻页打印完成 可进行拍照照
                        countPageTurn++;
                        // 90s后翻页机还未检测到数据 此时设备可能出现异常
                        if (countPageTurn >= 180) {
                            MediaPlug.printMaybeError();
//                            SerialTool.closePort(comCommunication.getComNum());
                            showAttention("打印过程中可能出现异常，请联系工作人员检查1。", -1);
                            return null;
                        }
                        logger.info("pageTurningComState2-------{}", pageTurningComState);
                        if (pageTurningComState) {
                            logger.info("pageTurningComState3-------{}", pageTurningComState);
                            logger.info("pageTurningComStr+++++++++++{}", pageTurningComStr);
//                            if (Constant.COM.PAGE_RETURNING_COM_VALUE.equals(pageTurningComStr)) {
                            if ("fe970501b088ab".equals(pageTurningComStr) || "fe970501b00023".equals(pageTurningComStr) || "fe970701b0000000".equals(pageTurningComStr) || "fe970501b06645".equals(pageTurningComStr)) {

                                countPageTurn = 0;
                                logger.info("翻页机=" + pageTurningComStr);
                                pageCount += 1;
                                logger.info("当前页=" + pageCount);
                                // 还原翻页机状态数
                                pageTurningComState = false;
                                pageTurningComStr = "";
                                if (pageCount < 3) {
                                    logger.info("pageCount现在=" + pageCount);
                                    // 获得正确参数 可进行拍照 保存 一秒钟后再进行拍照 保证照片清晰
                                    Thread.sleep(1000);
                                    String filePath = Constant.FileDir.CERTIFICATE_IMG
                                            + DateFactory.getCurrentDateString("yyyyMMddHHmmss") + "_"
                                            + infoList.get(i).getCertNumber() + ".jpg";
                                    BufferedImage img = OpenCVCamFactory.webCam.getImage();
                                    check = new File(filePath);
                                    try {
                                        ImageIO.write(img, "jpg", check);
                                    } catch (IOException e) {
                                        MediaPlug.printError();
                                        String text = "证书打印 10.保存证书" + infoList.get(i).getCertNumber() + " 第"
                                                + pageCount + "张照片出现异常";
                                        FileFactory.writeTXTFile(MainView.logFile, text);
                                        showAttention("证书打印过程中出现异常1，请联系工作人员。", -1);
                                        e.printStackTrace();
                                        return null;
                                    }
                                    String text = "保存证书" + infoList.get(i).getCertNumber() + "第" + pageCount + "张照片成功！";
                                    FileFactory.writeTXTFile(MainView.logFile, text);
                                }
                                if (pageCount == 2) {
                                    logger.info("盖章机等待中，line747");
                                    logger.info("当前gaiZhangJiState = {}", gaiZhangJiState);
                                    int countGaiZhangJi = 0;
                                    while (gaiZhangJiState) {
                                        countGaiZhangJi++;
                                        // 90s后 盖章机还未检测到数据 可能出现异常
                                        if (countGaiZhangJi >= 180) {
                                            MediaPlug.printMaybeError();
                                            showAttention("打印过程中可能出现异常，请联系工作人员检查。 code：2", -1);
                                            return null;
                                        }
                                        logger.info("从盖章机串口获取的数据=" + gaiZhangJiComStr);
                                        if (gaiZhangJiComState
                                                && Constant.COM.GAI_ZHANG_JI_VALUE.equals(gaiZhangJiComStr)) {
                                            countGaiZhangJi = 0;
                                            // 证书数量更新
                                            remaining--;
                                            MainView.remaining = String.valueOf(remaining);
                                            // 更新盖章机状态
                                            gaiZhangJiComState = false;
                                            gaiZhangJiComStr = "";
                                            // 更新页码
                                            pageCount = 0;

                                            // 识别次数 初始默认2次
                                            int defaultCount = 2;
                                            // 打印结果 true：识别和保存均成功
                                            boolean printState = false;
                                            File fileImg = null;
                                            String path = DateFactory.getCurrentDateString("yyyyMMddHHmmss")
                                                    + ".jpg";
                                            String scanerPath = Constant.Scaner.SCANER_DIR + path;// ocr识别文件夹
                                            for (int a = 0; a < defaultCount; a++) {
                                                // 清空ocr识别文件夹
                                                ClientScript.clearOCR();
                                                // 进行证书编号页面拍照
                                                
                                                Thread.sleep(3000);// 保证能获取清晰图像
                                                BufferedImage img = OpenCVCamFactory.webCam.getImage();
                                                // 进行照片 保存
                                                ocrFile = new File(scanerPath);
                                                try {
                                                    ImageIO.write(img, "jpg", ocrFile);
                                                } catch (IOException e) {
                                                    MediaPlug.printError();
                                                    String text = "证书打印 11.获取证书" + infoList.get(i).getCertNumber()
                                                            + " 编号页面出现异常";
                                                    FileFactory.writeTXTFile(MainView.logFile, text);
                                                    showAttention("证书打印过程中出现异常2，请联系工作人员。", -1);
                                                    e.printStackTrace();
                                                    return null;
                                                }

                                                // 判断scaner文件夹下是否存在文件 存在则进行ocr识别
                                                ocrFile = new File(Constant.Scaner.SCANER_DIR);
                                                if (ocrFile.listFiles().length > 0) {
                                                    // 第一次操作图片准备识别
                                                    String text = "证书打印 12.获取证书" + infoList.get(i).getCertNumber()
                                                            + " 编号页面照片成功";
                                                    FileFactory.writeTXTFile(MainView.logFile, text);
                                                    logger.info("获取证书编号照片获取成功");
                                                    fileImg = ocrFile.listFiles()[0];
                                                    String targetPath = Constant.Scaner.SCANER_DIR + "crop.jpg";
                                                    boolean disposeState = disposeImage(fileImg, targetPath);
                                                    if (!disposeState) {
                                                        logger.info("图片处理异常。");
                                                        return null;
                                                    }
                                                    logger.info("即将进行ocr识别。");
                                                    // 开始识别
                                                    HttpReturnDto returnDto = ocrRecognition(targetPath);
                                                    System.out.println("ocr识别请求返回：" + returnDto);
                                                    if (returnDto != null
                                                            && 0 == Integer.parseInt(returnDto.getRespMsg())
                                                            && !"00000000000".equals(returnDto.getRespJson())) {
                                                        String ocr = returnDto.getRespJson();
                                                        logger.info("ocr = {}", ocr);
                                                        String ocrtext = "证书" + infoList.get(i).getCertNumber()
                                                                + "编号识别号码=" + ocr;
                                                        FileFactory.writeTXTFile(MainView.logFile, ocrtext);
                                                        String filePath = Constant.FileDir.CERT_SCAN_DIR
                                                                + infoList.get(i).getCertNumber() + "_" + path;// 证书编号页面照片保存文件夹
                                                       // 本地保存全证书首页照片，已经将保存操作改到后台执行
                                                        /*boolean saveOCRFile = ClientScript
                                                                .saveFile(fileImg.getAbsolutePath(), filePath);
                                                        if (!saveOCRFile) {
                                                            MediaPlug.printError();
                                                            String text1 = "打印方法 13.保存证书编号照片状态：" + saveOCRFile;
                                                            FileFactory.writeTXTFile(MainView.logFile, text1);
                                                            showAttention("保存证书编号文件失败1，请联系工作人员。", -1);
                                                            return null;
                                                        } else {
                                                            String text1 = "打印方法 14.保存证书编号照片状态：" + saveOCRFile;
                                                            FileFactory.writeTXTFile(MainView.logFile, text1);
                                                        }*/
                                                        // 保存打印记录 证书权证号 权力类型 不动产单元号 ocr识别号码 word文档路径 ocr照片路径 日志文件
														boolean res;
														try {
															res = saveScanCopy(infoList.get(i).getCertNumber(),
															        infoList.get(i).getUnitNumber(), ocr, destPath,
															        scanerPath, filePath,  MainView.logFile);
															// 清空图片缓存
															Toolkit.getDefaultToolkit().getImage(Constant.FileDir.ZONGDITU).flush();
															Toolkit.getDefaultToolkit().getImage(Constant.FileDir.FENBUTU).flush();
														} catch (Exception e) {
															e.printStackTrace();
															showAttention("证书打印请求出错，请联系工作人员", -1);
                                                            return null;
														}
                                                        if (!res) {
                                                            MediaPlug.printError();
                                                            String text1 = "证书打印 15.打印证书"
                                                                    + infoList.get(i).getCertNumber()
                                                                    + "更新设备证书数量、保存打印记录 失败！";
                                                            FileFactory.writeTXTFile(MainView.logFile, text1);
                                                            showAttention("证书打印记录保存失败3，请联系工作人员", -1);
                                                            return null;
                                                        } else {
                                                            String text1 = "证书打印 16.打印证书"
                                                                    + infoList.get(i).getCertNumber()
                                                                    + "更新设备证书数量、保存打印记录 成功！";
                                                            FileFactory.writeTXTFile(MainView.logFile, text1);

                                                            // ocr识别成功
                                                            SerialTool.serialPort = qzsComSealMachine.getComNum();
                                                            ocrDiscernSuccess(qzsComSealMachine, infoList.get(i));

                                                            printState = true;
                                                            break;// 跳出接收盖章机数据循环
                                                        }
                                                    }
                                                } else {
                                                    MediaPlug.printError();
                                                    String text = "证书打印 17.内部错误 ： 证书编号照片scaner文件夹为空！";
                                                    FileFactory.writeTXTFile(MainView.logFile, text);
                                                    showAttention("证书打印出现异常7,请联系工作人员。", -1);
                                                    return null;
                                                }
                                            }
                                            // 打印保存 成功
                                            if (printState) {
                                                break; // 跳出循环
                                            } else {
                                                String text6 = "证书打印 24.ocr识别两次均失败！";
                                                FileFactory.writeTXTFile(MainView.logFile, text6);
                                                pageTurningState = false;
                                                gaiZhangJiState = false;
                                                // 两次识别均失败时 保存证书编号页面 照片
                                                String filePath = Constant.FileDir.CERT_SCAN_DIR
                                                        + infoList.get(i).getCertNumber() + "_" + DateFactory.getCurrentDateString("yyyyMMddHHmmss")
                                                        + ".jpg";// 证书编号页面照片保存文件夹
                                                boolean res;
												try {
													res = saveScanCopy(infoList.get(i).getCertNumber(),
													        infoList.get(i).getUnitNumber(), "00000000000", destPath,
													        scanerPath, filePath, MainView.logFile);
													// 清空图片缓存
													Toolkit.getDefaultToolkit().getImage(Constant.FileDir.ZONGDITU).flush();
													Toolkit.getDefaultToolkit().getImage(Constant.FileDir.FENBUTU).flush();
												} catch (Exception e) {
													e.printStackTrace();
													showAttention("证书打印请求出错，请联系工作人员", -1);
                                                    return null;
												}
                                                if (!res) {
                                                    MediaPlug.printError();
                                                    String text1 = "证书打印 15.打印证书"
                                                            + infoList.get(i).getCertNumber()
                                                            + "更新设备证书数量、保存打印记录 失败！";
                                                    FileFactory.writeTXTFile(MainView.logFile, text1);
                                                    showAttention("证书打印记录保存失败3，请联系工作人员", -1);
                                                    return null;
                                                } else {
                                                    String text1 = "证书打印 16.打印证书"
                                                            + infoList.get(i).getCertNumber()
                                                            + "更新设备证书数量、保存打印记录 成功！";
                                                    FileFactory.writeTXTFile(MainView.logFile, text1);

                                                    // ocr识别成功
                                                    SerialTool.serialPort = qzsComSealMachine.getComNum();
                                                    ocrDiscernSuccess(qzsComSealMachine, infoList.get(i));

                                                    printState = true;
                                                    break;// 跳出接收盖章机数据循环
                                                }
                                            }
                                        }
                                        Thread.sleep(500);
                                    }
                                    break;
                                }
                            } else {
                                MediaPlug.printError();
                                String text = "打印方法 26.打印证书" + infoList.get(i).getCertNumber() + "过程中 翻页机异常";
                                FileFactory.writeTXTFile(MainView.logFile, text);
                                showAttention("证书打印过程中出现异常8,请联系工作人员。", -1);
                                return null;
                            }
                        }
                        Thread.sleep(500);
                    }
                }
                // 加载打印完成页面
                logger.info("打印完成");
                String text2 = "打印成功！";
                FileFactory.writeTXTFile(MainView.logFile, text2);
                Platform.runLater(() -> {
                    CountDownPlug.startCountDown();
                    MediaPlug.printSuccess();
                    String url = Constant.Business.BUSINESS_URL + "client/printSuccess";
                    MainView.web.getEngine().load(url);
                });
                return null;
            }

            private boolean buildFenbutuAndZongditu(RealEstateInfo info) {
                // 处理宗地图、分布图图片
                // 检查是否已经存在宗地图图片 存在则先删除
                ClientScript.cleanPic(Constant.FileDir.ZONGDITU);
                // 生成宗地图
                boolean zongDiTuFlag = ImageFactory.generateImage(info.getZongditu(), Constant.FileDir.ZONGDITU);
                // 检查是否已经存在分布图图片 存在则先删除
                ClientScript.cleanPic(Constant.FileDir.FENBUTU);
                // 生成分布图
                boolean fenBuTuFlag = ImageFactory.generateImage(info.getFenbutu(), Constant.FileDir.FENBUTU);
                if (!zongDiTuFlag || !fenBuTuFlag) {
                    MediaPlug.printError();
                    String text = "打印方法 4.失败！  地图生成状态：" + zongDiTuFlag + "   分布图生成状态：" + fenBuTuFlag;
                    FileFactory.writeTXTFile(MainView.logFile, text);
                    showAttention("生成宗地图、分布图出现异常，请联系工作人员。", -1);
                    return false;
                } else {
                    String text = "打印方法 5.成功！  地图生成状态：" + zongDiTuFlag + "   分布图生成状态：" + fenBuTuFlag;
                    FileFactory.writeTXTFile(MainView.logFile, text);
                    return true;
                }
            }
        };
        thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
    }

    // 接收business数据进行打印
    public static Thread thread;
    
    /**
     * 发送查询指令
     * 
     * @throws SerialPortOutputStreamCloseFailure 
     * @throws SendDataToSerialPortFailure 
     * @throws InterruptedException 
     */
    private boolean sendCheckCommand() throws
    			SendDataToSerialPortFailure, SerialPortOutputStreamCloseFailure, InterruptedException {
    	boolean checkCommandStatues = false;
    	//"FE 01 05 97 BC CC E3" 翻页机查询指令转换成的byte[]
        byte[] checkCommand = {(byte) 254, 1, 5, (byte) 151, (byte) 188, (byte) 204, (byte) 227};
        // 循环请求查询标记
        boolean reqStatues = true;
        // 返回非正常信息次数
        int valueFailureCount = 0;
        while(reqStatues) {
        	logger.info("---------发送查询指令----------{}", comPageTurning.getComNum());
        	SerialTool.sendToPort(comPageTurning.getComNum(), checkCommand);
    		if(StringUtils.isNotNullAndEmpty(ClientScript.pageTurningComStr)) {
    			// 查询状态：正常1
    			if ("fe970701b0000000".equals(ClientScript.pageTurningComStr) || "fe970701b000000021".equals(ClientScript.pageTurningComStr)) {
    				logger.info("发送查询，翻页机串口返回结果：正常");
    				checkCommandStatues = true;
                    break;
                // 缺证2
    			}else if ("fe970701b0110000".equals(ClientScript.pageTurningComStr)) {
                    logger.info("请求翻页机串口返回结果：缺证");
                    showAttention("翻页机状态：缺证，请联系工作人员。", -1);
                    break;
                    // 卡证3
                } else if ("fe970701b0001100".equals(ClientScript.pageTurningComStr)) {
                    logger.info("请求翻页机串口返回结果：卡证");
                    showAttention("翻页机状态：卡证，请联系工作人员。", -1);
                    break;
                    // 故障4
                } else if ("fe970701b0000011".equals(ClientScript.pageTurningComStr)) {
                    logger.info("请求翻页机串口返回结果：故障");
                    showAttention("翻页机状态：故障，请联系工作人员。", -1);
                    break;
                    // 缺证 卡证5
                } else if ("fe970701b0111100".equals(ClientScript.pageTurningComStr)) {
                    logger.info("请求翻页机串口返回结果：缺证 卡证");
                    showAttention("翻页机状态：缺证 卡证，请联系工作人员。", -1);
                    break;
                    // 缺证 故障6
                } else if ("fe970701b0110011".equals(ClientScript.pageTurningComStr)) {
                    logger.info("请求翻页机串口返回结果：缺证 故障");
                    showAttention("翻页机状态：缺证 故障，请联系工作人员。", -1);
                    break;
                    // 卡证 故障7
                } else if ("fe970701b0001111".equals(ClientScript.pageTurningComStr)) {
                    logger.info("请求翻页机串口返回结果：卡证 故障");
                    showAttention("翻页机状态：卡证 故障，请联系工作人员。", -1);
                    break;
                    // 缺证 卡证 故障8
                } else if ("fe970701b0111111".equals(ClientScript.pageTurningComStr)) {
                    logger.info("请求翻页机串口返回结果：缺证 卡证 故障");
                    showAttention("翻页机状态：缺证 卡证 故障，请联系工作人员。", -1);
                    break;
                }
    		}else {
    			if (valueFailureCount == 5) {
                    // 第一次发送串口请求没有接收到数据后，重新发送5次，如果5次都没有返回信息，停止发送，将结果返回
                    logger.info("翻页机串口故障，连续发送5次串口请求，未接收到串口返回数据");
                    showAttention("查询-翻页机串口请求失败，请联系工作人员。", -1);
                    break;
                }
                valueFailureCount++;
        	}
        	Thread.sleep(500);
        }
    	return checkCommandStatues;
    } 
    
    /**
     * 发送送证指令
     * 
     * @throws SerialPortOutputStreamCloseFailure 
     * @throws SendDataToSerialPortFailure 
     * @throws InterruptedException 
     */
    private boolean sendCertificateCommand() throws SendDataToSerialPortFailure, SerialPortOutputStreamCloseFailure, InterruptedException {
    	boolean sendCertificateStatues = false;
    	//"FE 01 05 97 B2 11 30" 送证指令转换成的byte[]
        byte[] sendCommand = {(byte) 254, 1, 5, (byte) 151, (byte) 178, 17, 48};
        // 循环请求送证标记
    	boolean reqCertificateStatues = true;
    	// 返回非正常信息次数
    	int valueFailureCount = 0;
    	while(reqCertificateStatues) {
    		logger.info("---------发送送证指令----------");
        	SerialTool.sendToPort(comPageTurning.getComNum(), sendCommand);
			if(StringUtils.isNotNullAndEmpty(ClientScript.pageTurningComStr)) {
    			// 送证指令下发成功
    			if ("fe970501b00023".equals(ClientScript.pageTurningComStr)) {
    				logger.info("发送送证，翻页机串口返回结果：正常");
    				sendCertificateStatues = true;
                    break;
    			}
    		}else {
    			if (valueFailureCount == 5) {
                    // 第一次发送串口请求没有接收到数据后，重新发送5次，如果5次都没有返回信息，停止发送，将结果返回
                    logger.info("翻页机串口故障，发送送证指令后，未接收到串口返回正常结果");
                    showAttention("送证-翻页机串口请求失败，请联系工作人员。", -1);
                    break;
                }
                valueFailureCount++;
        	}
    		Thread.sleep(500);
    	}
    	return sendCertificateStatues;
    }

    /**
     * 处理图片
     * 将图片旋转、裁剪，提高ocr识别率
     *
     * @param fileImg
     * @param targetPath
     * @return
     * @throws IOException
     */
    private static boolean disposeImage(File fileImg, String targetPath)
            throws IOException {
        // 旋转图片 方便识别 90度
        boolean rotatestate = RotateImageUtil.Rotate90(fileImg.getAbsolutePath());
        // 裁剪图片
        boolean cropState = false;
        if (rotatestate) {
            cropState = ImageFactory.cropImage(fileImg.getAbsolutePath(), targetPath);
        } else {
            MediaPlug.printError();
            showAttention("图片处理出现异常1，请联系工作人员。", -1);
            return false;
        }
        if (!cropState) {
            MediaPlug.printError();
            showAttention("图片处理出现异常2，请联系工作人员。", -1);
            return false;
        }
        return true;
    }

    /**
     * ocr识别
     *
     * @param targetPath 图片路径
     * @return
     * @throws KeyManagementException
     * @throws NoSuchAlgorithmException
     */
    private static HttpReturnDto ocrRecognition(String targetPath)
            throws KeyManagementException, NoSuchAlgorithmException {
        HttpReturnDto dto = new HttpReturnDto();
        dto.setRespJson(targetPath);
        String url = Constant.HardWare.HARDWARE_URL + "identifyOcr1";
        String returnJson = HttpClientFactory.getInstance().doPost(JacksonFactory.writeJson(dto), url);
        HttpReturnDto returnDto = JacksonFactory.readJson(returnJson, HttpReturnDto.class);
        return returnDto;
    }

    /**
     * ocr识别成功后 发送走纸指令
     *
     * @param comCommunication 串口对象
     * @param info             证书实体
     * @throws SendDataToSerialPortFailure
     * @throws SerialPortOutputStreamCloseFailure
     * @throws InterruptedException
     */
    private static void ocrDiscernSuccess(ComCommunication comCommunication, RealEstateInfo info)
            throws SendDataToSerialPortFailure, SerialPortOutputStreamCloseFailure, InterruptedException {
        // 向盖章机传递走纸指令
        byte[] success = {(byte) 254, 1, 5, (byte) 151, (byte) 178, 34, 3};
        SerialTool.sendToPort(comCommunication.getComNum(), success);
        int sendCount = 0;
        boolean sendState = true;
        while (sendState) {
            sendCount++;
            if (gaiZhangJiComState) {
                logger.info("指令下发状态= " + gaiZhangJiComStr);
                if ("fe970501b00023".equals(gaiZhangJiComStr)) {
                    logger.info("指令下发成功");
                    String text5 = "打印证书" + info.getCertNumber() + "盖章机出证成功！";
                    FileFactory.writeTXTFile(MainView.logFile, text5);
                    break;// 跳出送正指令循环环
                } else {
                    MediaPlug.printError();
//                    SerialTool.closePort(comCommunication.getComNum());
                    showAttention("送证指令发送异常3，请联系工作人员。", -1);
                    break;
                }
            }
            // 十秒钟
            if (sendCount > 20) {
                MediaPlug.printMaybeError();
//                SerialTool.closePort(comCommunication.getComNum());
                sendState = false;
                showAttention("送证指令发送异常4，请联系工作人员。", -1);
                break;
            }
            Thread.sleep(500);
        }
    }
    
    /**
     * 登记证明发送走纸指令
     *
     * @param comCommunication 串口对象
     * @param info             证书实体
     * @throws SendDataToSerialPortFailure
     * @throws SerialPortOutputStreamCloseFailure
     * @throws InterruptedException
     */
    private static void djzmOcrDiscernSuccess(ComCommunication comCommunication, EstateInfo info)
            throws SendDataToSerialPortFailure, SerialPortOutputStreamCloseFailure, InterruptedException {
        // 向盖章机传递走纸指令
        byte[] success = {(byte) 254, 1, 5, (byte) 151, (byte) 178, 34, 3};
        SerialTool.sendToPort(comCommunication.getComNum(), success);
        int sendCount = 0;
        boolean sendState = true;
        while (sendState) {
            sendCount++;
            if (gaiZhangJiComState) {
                logger.info("指令下发状态= " + gaiZhangJiComStr);
                if(StringUtils.isNotNullAndEmpty(gaiZhangJiComStr)) {
                	if ("fe970501b00023".equals(gaiZhangJiComStr)) {
                        logger.info("指令下发成功,打印证明" + info.getCertNumber() + "盖章机出证成功");
                        String text5 = "登记证明打印方法 5.指令下发成功,打印证明" + info.getCertNumber()+ "盖章机出证成功";
						FileFactory.writeTXTFile(MainView.logFile, text5);
                        break;// 跳出送正指令循环环
                    } else {
                        MediaPlug.printError();
                        showAttention("登记证明送证指令发送异常3，请联系工作人员。", -1);
                        break;
                    }
                }
            }
            // 十秒钟
            if (sendCount > 20) {
                MediaPlug.printMaybeError();
                sendState = false;
                showAttention("登记证明送证指令发送异常4，请联系工作人员。", -1);
                break;
            }
            Thread.sleep(500);
        }
    }
    


    /**
     * FX显示提示语句
     *
     * @param msg   提示信息
     * @param state 倒计时 若不需要倒计时则传递负数
     */
    private static void showAttention(String msg, int state) {
        Platform.runLater(() -> {
            TransitPage.attentionPage(msg, state);
        });
    }

    /**
     * 继续打印
     */
    public void continuePrint() {
        logger.info("继续打印recordId====" + MainView.usageId);
        MediaPlug.query();

        // 还原盖章机 翻页机 状态
        ClientScript.gaiZhangJiComStr = "";// 从盖章机器串口获取的数据
        ClientScript.gaiZhangJiComState = false;// 盖章机是否传值
        ClientScript.pageTurningComStr = "";// 从翻页机器串口获取的数据
        ClientScript.pageTurningComState = false;// 翻页机是否传值
        ClientScript.pageCount = 0;// 还原翻页机当前页面

        String url = Constant.Business.BUSINESS_URL + "client/queryhouse" + "?recordId=" + MainView.usageId;
        Platform.runLater(() -> {
            try {
                MainView.web.getEngine().load(url);
            } catch (Exception e) {
                TransitPage.attentionPage("数据查询出现异常，请联系工作人员。", -1);
                e.printStackTrace();
            }
        });
    }

    /**
     * 删除url指向的文件
     *
     * @param url
     */
    private static void cleanPic(String url) {
        check = new File(url);
        if (check.exists()) {
            check.delete();
        }
    }

    /**
     * 保存文件 绝对路径
     *
     * @param sourcePath 源文件路径
     * @param destPath   目标文件路径
     * @return
     */
    private static boolean saveFile(String sourcePath, String destPath) {
        source = new File(sourcePath);
        dest = new File(destPath);
        boolean saveDocxFile = FileFactory.copyFileUsingFileChannels(source, dest);
        return saveDocxFile;
    }

    /**
     * 清空ocr文件夹
     */
    private static void clearOCR() {
        ocrFile = new File(Constant.Scaner.SCANER_DIR);
        if (ocrFile.isDirectory()) {
            FileFactory.deleteDirs(Constant.Scaner.SCANER_DIR);
        }
    }

    /**
     * 检查数据，存在空数据 将不会打印
     */
    private boolean checkData(RealEstateInfo estate) {
        // 登簿时间
        if (estate.getBookTime() == null || estate.getBookTime().isEmpty()) {
            return false;
        }
        // 权证号
        if (estate.getCertNumber() == null || estate.getCertNumber().isEmpty()) {
            return false;
        }
        // 权利人
        if (estate.getObligee() == null || estate.getObligee().isEmpty()) {
            return false;
        }
        // 共有情况
        // if (estate.getCo_ownershipCircumstance() == null ||
        // estate.getCo_ownershipCircumstance().isEmpty()) {
        // return false;
        // }
        // 坐落
        if (estate.getLocated() == null || estate.getLocated().isEmpty()) {
            return false;
        }
        // 不动产单元号
        if (estate.getUnitNumber() == null || estate.getUnitNumber().isEmpty()) {
            return false;
        }
        // 权力类型
        if (estate.getRightTypes() == null || estate.getRightTypes().isEmpty()) {
            return false;
        }
        // 权力性质
        if (estate.getRightNature() == null || estate.getRightNature().isEmpty()) {
            return false;
        }
        // 用途
        if (estate.getApplication() == null || estate.getApplication().isEmpty()) {
            return false;
        }
        // 面积
        if (estate.getArea() == null || estate.getArea().isEmpty()) {
            return false;
        }
        // 使用期限
        if (estate.getServiceLife() == null || estate.getServiceLife().isEmpty()) {
            return false;
        }
        // 权利其他情况
        if (estate.getOther() == null || estate.getOther().isEmpty()) {
            return false;
        }
        // 分布图
        if (estate.getFenbutu() == null || estate.getFenbutu().isEmpty()) {
            return false;
        }
        // 宗地图
        if (estate.getZongditu() == null || estate.getZongditu().isEmpty()) {
            return false;
        }
        return true;
    }

    /**
     * 证书权证号 不动产单元号 ocr识别号码 word文档路径 ocr照片路径
     *
     * @param certNumber
     * @param unitNumber
     * @param ocr
     * @param destPath
     * @param ocrPicPath
     * @param logFile
     * @return
     */
    private boolean saveScanCopy(String certNumber, String unitNumber, String ocr, String destPath, String ocrPicPath,String filePath,
                                 File logFile) {
        SubmitPrintReq cert = new SubmitPrintReq();
        cert.setCertNumber(certNumber);
        cert.setBusiNumber(unitNumber);// 单元号
        cert.setSequNumber(ocr);
        cert.setUsageId(MainView.usageId);
        cert.setDestPath(destPath);// word文档路径
        cert.setCertScan(ImageFactory.getImageStr(ocrPicPath));
        cert.setOcrPicPath(filePath);// ocr照片路径
        cert.setLogFile(logFile);
        cert.setTerminalId(Constant.TerminalInfo.QZS_TYPE);
        String reqJson = JacksonFactory.writeJson(cert);
        System.out.println("发送打印保存req="+reqJson);
        try {
            String rspnJson = HttpClientFactory.getInstance().doPost(reqJson,
                    Constant.Business.BUSINESS_URL + "client/submitPrintRecord");
            logger.info(rspnJson);
            HttpReturnDto result = JacksonFactory.readJson(rspnJson, HttpReturnDto.class);
            // 获取保存打印记录 并传递到business进行回传不动产
            return result.isResult();
        } catch (KeyManagementException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 重置倒计时
     */
    public void resetCount() {
        CountDownPlug.resetCountDown();
    }

    /**
     * 开始倒计时
     */
    public void startCount() {
        CountDownPlug.startCountDown();
    }
    
    /**
     * 开始倒计时（指定倒计时长）
     */
 	public void startCount(int num) {
 		CountDownPlug.startCountDown(num);
 	}
    

    /**
     * 返回首页
     *
     */
    public void home() {
        MainView.home();
    }
    
    /**
     * 管理员身份验证
     *
     * @param idName
     */
    public void manageYanzheng(String idName) {
        // 改变可识别现场照片生成状态 停止人证比对方法
        OpenCVCamFactory.saveDetPicState = false;

        logger.info("进入管理员身份验证---------");
        String url = Constant.Business.BUSINESS_URL + "client/manageYanzheng";
        try {
            String returnValue = HttpClientFactory.getInstance().doPost(idName, url);
            HttpReturnDto dto = JacksonFactory.readJson(returnValue, HttpReturnDto.class);
            if (dto.isResult()) {
                // 人脸识别成功
                // 启动倒计时
                CountDownPlug.resetCountDown();
                supperManage();
            } else { // 识别失败返回首页
                TransitPage.attentionPage("当前不是管理员身份!", 10);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 开启键盘
     */
    public void handinput() {
        logger.info("打开键盘");
        try {
            File handinput = new File(Constant.StartMode.HANDINPUT_PATH);
            logger.info("调用键盘=====" + handinput.exists());
            Desktop.getDesktop().open(handinput);
        } catch (IOException e2) {
            e2.printStackTrace();
        }
    }

    /**
     * 账号密码登陆页面 键盘无法打开时候 进行修复
     */
    public void keyboardRepair() {
        System.out.println("进入键盘修复");
        handinputclose();
        handinput();
    }

    /**
     * 关闭键盘
     */
    public void handinputclose() {
        try {
            Runtime runtime = Runtime.getRuntime();
            Process process = runtime.exec("taskkill /f /im " + Constant.StartMode.KEYBOARD_REPAIR);
            BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream(), "GBK"));
            String line = null;
            StringBuilder build = new StringBuilder();
            while ((line = br.readLine()) != null) {
                build.append(line);
            }
            logger.info("关闭键盘==" + build);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 账号密码登陆成功后进入管理员操作页面
     */
    public void supperManage() {
        Platform.runLater(() -> {
            CountDownPlug.resetCountDown();
            MainView.border.setCenter(HandlePage.buildPage());
        });
    }

    /**
     * 获取页面标识 通过此此标识判断当前页面
     *
     * @return
     */
    public static String getPageFlag() {
        String value = (String) MainView.web.getEngine().executeScript("getPageFlag()");
        return value;
    }

    /**
     * 登记证明打印完成语音提示
     */
    public void complete() {
		MediaPlug.complete();
		CountDownPlug.startCountDown(10);
	}
    
    /**
     * 登记证明打印
     */
 	public void djzmPrint(String json) {
 		logger.info("调用打印机开始打印【{}】",json);
        // 把即将打印的数据写入日志
        FileFactory.writeTXTFile(MainView.logFile, "登记证明打印方法 1.用户选中的打印数据：" + json);
 		CountDownPlug.stopCountDown();
 		Task<Void> task = new Task<Void>() {
 			@Override
 			protected Void call() throws Exception {
 				List<EstateInfo> selected = JacksonFactory.readJsonList(json, EstateInfo.class);
 				// 计算打印总量
 				int count = selected.size();
 				int remaining = Integer.parseInt(MainView.remaining);
 				if (count > remaining) {
 					String text2 = "登记证明打印方法 2.计划打印" + count + "本证明,剩余空白证明数量仅有" + remaining + "本，证明数量不足！";
                    FileFactory.writeTXTFile(MainView.logFile, text2);
 					Platform.runLater(() -> {
 						MainView.web.getEngine().executeScript("printBreak('打印证明纸张不足,请联系工作人员!','-1')");
 					});
 					return null;
 				}
 				EstateInfo printData;
 				List<Certnumber> printed;
 				boolean flg;
 				int k = 0;
 				for (int n = 0; n < selected.size(); n++) {
 					// 打印之前去重
 					printed = HttpReq.hasPrinted();
 					logger.info("去重后的数据是【{}】",printed);
 					flg = false;
 					if (printed != null) {
 						logger.info("循环次数【{}】",printed);
 						for (int m = 0; m < printed.size(); m++) {
 							logger.info("已打印的CertNumber【{}】",printed.get(m).getCertNumber());
 							if (printed.get(m).getCertNumber().equals(selected.get(n).getCertNumber())) {
 								break;
 							} else {
 								if (m == (printed.size() - 1)) {
 									flg = true;
 								}
 							}
 						}
 						if (flg == true) {
 							printData = selected.get(n);
 						} else {
 							// 若打印重复则提示重新刷身份查询确认
 							Platform.runLater(() -> {
 								MainView.web.getEngine().executeScript("printBreak('该证明已经打印，请重新查询确认!','1')");
 							});
 							return null;
 						}
 					} else {
 						printData = selected.get(n);
 						logger.info("打印的printData里面的数据是【{}】",printData);
 						String text3 = "登记证明打印方法 3.此条需打印的记录：" + printData;
 						FileFactory.writeTXTFile(MainView.logFile, text3);
 					}
 					int s = ++k;
 					// 打印提示
 					Platform.runLater(() -> {
 						MainView.web.getEngine().executeScript("doPrint('" + s + "','" + count + "')");
 						logger.info("打印提示doPrint({}，{})",s,count);
 					});
 					// 清空扫描件，防止未删除
 					FileFactory.deleteDirs(Constant.Scaner.SCANER_DIR);
 					// 调用打印机并盖章
 					Map<String, Object> parameters = new HashMap<String, Object>();
 					parameters.put("unitNumber", printData.getUnitNumber());
 					parameters.put("certNumber", printData.getCertNumber());
 					parameters.put("notes", printData.getNotes());
 					parameters.put("otherCases", printData.getOtherCases());
 					parameters.put("located", printData.getLocated());
 					parameters.put("busiType", printData.getBusiType());
 					parameters.put("obligee", printData.getObligee());
 					parameters.put("obligor", printData.getObligor());
 					parameters.put("registerTime", printData.getRegisterTime());
 					// 汤阴县取证明权力或事项、权利人、坐落属性打印
 					String subStringYear = printData.getCertNumber().substring(printData.getCertNumber().indexOf("（")+1, printData.getCertNumber().indexOf("）"));
 					String subStringNum = printData.getCertNumber().substring(printData.getCertNumber().indexOf("第")+1,printData.getCertNumber().indexOf("号"));
 					StringBuilder ewm = new StringBuilder();
 					if("预告登记".equals(printData.getBusiType())) {
 						ewm.append("预告证明号: " +subStringYear+subStringNum);
 					}else if("抵押权".equals(printData.getBusiType())) {
 						ewm.append("抵押权证号: " +subStringYear+subStringNum);
 					}
 					ewm.append("\r\n").append("权利人: " + printData.getObligee());
 					ewm.append("\r\n").append("坐落: " + printData.getLocated());
 					parameters.put("ewm", ewm.toString());
 					
 					// 濮阳、范县直接取值系统商返回的EWM属性打印
// 					parameters.put("ewm", printData.getEwm());

 					HttpReturnDto jasperPrintReq = new HttpReturnDto();
 					jasperPrintReq.setRespJson(Constant.JasperPath.JASPER_PATH);
 					jasperPrintReq.setRespObj(parameters);
 					logger.info("开始发送打印请求");
 					String jasperPrintRspn = HttpClientFactory.getInstance().doPost(
 							JacksonFactory.writeJson(jasperPrintReq),
 							Constant.HardWare.HARDWARE_URL + "jasperPrint");
 					HttpReturnDto PrintRspn = JacksonFactory.readJson(jasperPrintRspn, HttpReturnDto.class);
 					logger.info("后台打印请求的响应【{}】",PrintRspn);
 					String text4 = "登记证明打印方法 4.后台打印请求的响应：" + PrintRspn;
					FileFactory.writeTXTFile(MainView.logFile, text4);
 					if (!PrintRspn.isResult()) {
 						Platform.runLater(() -> {
 							MainView.web.getEngine().executeScript("printBreak('" + PrintRspn.getRespMsg() + "','-1')");
 						});
 						return null;
 					}
 					//高拍仪拍照获取照片并进行ocr识别
					boolean getImageFlag = true;
					long startTime = System.currentTimeMillis();
					while (getImageFlag) {
						long endTime = System.currentTimeMillis();
						if (endTime - startTime <= 50 * 1000) {
							System.out.println("gaiZhangJiComStr2="+gaiZhangJiComStr);
 							if (StringUtils.isNotNullAndEmpty(gaiZhangJiComStr)) {
 								System.out.println("gaiZhangJiComStr3="+gaiZhangJiComStr);
								// 盖章完成
								if (Constant.COM.GAI_ZHANG_JI_VALUE.equals(gaiZhangJiComStr)) {
									// 收到可以拍照了
									// 开始拍照
									gaiZhangJiComStr = "";
									Thread.sleep(500);
									logger.info("开始拍照");
									String scanFilePath = Constant.Scaner.SCANER_DIR + "/scanCapture.jpg";
									BufferedImage img = OpenCVCamFactory.innerWebCam.getImage();
									File ocrFile = new File(scanFilePath);
									ImageIO.write(img, "jpg", ocrFile);
									img.flush();
									Thread.sleep(2000);
									// 向盖章机发送走纸指令
									djzmOcrDiscernSuccess(djzmComSealMachine, printData);
								} else if (gaiZhangJiComStr.contains("fe970501b00023")) {
									
									logger.info("吐证成功");
									getImageFlag = false;
									break;
								}
							}
						} else {
							logger.error("盖章机超时");
							MediaPlug.printError();
					        showAttention("登记证明盖章超时，请联系工作人员。", -1);
							getImageFlag = false;
							break;
						}
					}
 					// 成功打印后修改数据库证明数，并获取实时OCR印刷编号
 					String OCR = "";
 					boolean result = HttpReq.updateRemainPager();
 					if (result) {
 						// 剩余纸张数量-1
 						remaining--;
 						MainView.remaining = String.valueOf(remaining);
 						String text6 = "登记证明打印方法 6.修改数据库证明数成功";
 						FileFactory.writeTXTFile(MainView.logFile, text6);
 					} else {
 						String text6 = "登记证明打印方法 6.修改数据库证明数失败";
 						FileFactory.writeTXTFile(MainView.logFile, text6);
 						Platform.runLater(() -> {
 							MainView.web.getEngine().executeScript("printBreak('证明数修改失败，请联系工作人员!','-1')");
 						});
 						return null;
 					}
 					boolean flag = true;
 					String copyFilePath = Constant.Scaner.SCANER_DIR + "/copy.jpg";
 					do {
 						File dir = new File(Constant.Scaner.SCANER_DIR);
 						if (dir.isDirectory()) {
 							if (dir.listFiles().length > 0) {
 								// 获取扫描件
 								File file = dir.listFiles()[0];
 								//图片旋转
 								File copyFile = new File(copyFilePath);
								ImageUtil.rotate(file, Constant.Tailor.DEGREE, file);
// 								System.out.println("获取扫描件：" + file.getAbsolutePath());
 								String filePath = file.getAbsolutePath();
 								logger.info("获取扫描件【{}】",filePath);
 								HttpReturnDto req = new HttpReturnDto();
 								logger.info("开始进行第一次ocr识别");
 								req.setRespJson(filePath);
 								String rspn = HttpClientFactory.getInstance().doPost(JacksonFactory.writeJson(req),
 										Constant.HardWare.HARDWARE_URL + "identifyOcr1");
 								HttpReturnDto httpReturnDto = JacksonFactory.readJson(rspn, HttpReturnDto.class);
 								OCR = httpReturnDto.getRespJson();
 								if("00000000000".equals(OCR)) {
 									logger.info("第一次ocr识别失败,准备进行第二次ocr识别");
 								// 新的OCR识别存在BUG，有时候会识别到证明右上角也能识别出
 	 								// 统一扫描件的像素再进行截取识别(扫描仪扫描的证明像素有差距，不统一像素图片截取会报错)
// 	 								ImageFactory.resizeImage(filePath, filePath, 1280, 820);
 	 								BufferedImage bufferedImage = ImageIO.read(new File(filePath));
 	 								//原本50, 600, 550, 200 
 									BufferedImage newBufferedImage = bufferedImage.getSubimage(Constant.Tailor.X_AXIS,
 											Constant.Tailor.Y_AXIS, Constant.Tailor.WIDTH, Constant.Tailor.HEIGHT);
 	 								ImageIO.write(newBufferedImage, "jpg", new File(copyFilePath));
 	 								
 	 								rspn = HttpClientFactory.getInstance().doPost(JacksonFactory.writeJson(req),
 	 										Constant.HardWare.HARDWARE_URL + "identifyOcr1");
 	 								httpReturnDto = JacksonFactory.readJson(rspn, HttpReturnDto.class);
 	 								OCR = httpReturnDto.getRespJson();
 								}
 								// 回传扫描件
 								logger.info("识别出的OCR【{}】", httpReturnDto.getRespJson());
 								String text7 = "登记证明识别出的OCR："+httpReturnDto.getRespJson();
 		 						FileFactory.writeTXTFile(MainView.logFile, text7);
 								// 判断印刷编号是否一致
 								// if(!httpReturnDto.getRespJson().equals(OCR)){
 								// Platform.runLater(() -> {
 								// MainView.web.getEngine().executeScript("printError('印刷序列号识别失败，请联系工作人员!','-1')");
 								// });
 								// return null;
 								// }
 								boolean res = HttpReq.saveScanCopy(file.getAbsolutePath(), printData.getCertNumber(),
 										printData.getBusiNumber(), httpReturnDto.getRespJson());
 								if (!res) {
 									String text8 = "登记证明扫描件busiNumber=" + printData.getBusiNumber() + "保存失败";
 	 		 						FileFactory.writeTXTFile(MainView.logFile, text8);
 									Platform.runLater(() -> {
 										MainView.web.getEngine().executeScript("printBreak('扫描件保存失败，请联系工作人员!','-1')");
 										logger.error("扫描件保存失败");
 									});
 									return null;
 								}
// 								file.delete();
 								// 保存打印完成记录
 								flag = false;
 							}
 						}
 						Thread.sleep(1000);
 					} while (flag);
 					// 打印完成并二次登簿,打一张回传一张
 					final FutureTask<Boolean> query = new FutureTask<Boolean>(new Callable<Boolean>() {
 						@Override
 						public Boolean call() throws Exception {
 							Boolean resultFlag = (Boolean) MainView.web.getEngine().executeScript("complete()");
 							return resultFlag;
 						}
 					});
 					Platform.runLater(query);
 					// true 表示打印回传成功
 					if (!query.get()) {
 						logger.error("打印回传异常-----------");
 						String text9 = printData.getBusiNumber() + "打印回传异常";
 						FileFactory.writeTXTFile(MainView.logFile, text9);
 						Platform.runLater(() -> {
 							MainView.web.getEngine().executeScript("printBreak('打印回传异常，请联系工作人员!','-1')");
 						});
 						return null;
 					}
 					String text9 = printData.getBusiNumber() + "打印回传成功";
					FileFactory.writeTXTFile(MainView.logFile, text9);
 					logger.info("打印回传成功--------------");
 				}
 				String text10 = "打印结束";
				FileFactory.writeTXTFile(MainView.logFile, text10);
 				logger.info("打印结束..............");
 				return null;
 			}
 		};
 		Thread thread = new Thread(task);
 		thread.setDaemon(true);
 		thread.start();
 	}
}
