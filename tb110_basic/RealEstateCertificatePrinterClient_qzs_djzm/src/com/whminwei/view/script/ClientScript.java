package com.whminwei.view.script;

import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.whminwei.constant.Constant;
import com.whminwei.controller.FaceRecognitionController;
import com.whminwei.controller.TopController;
import com.whminwei.dto.EstateInfo;
import com.whminwei.dto.RealEstateInfo;
import com.whminwei.dto.UsageRecord;
import com.whminwei.dto.req.SubmitUsageReq;
import com.whminwei.dto.rspn.HttpResult;
import com.whminwei.dto.rspn.HttpReturnDto;
import com.whminwei.httpReq.HttpReq;
import com.whminwei.util.ComCommunication;
import com.whminwei.util.DateFactory;
import com.whminwei.util.DocxPrint;
import com.whminwei.util.FileFactory;
import com.whminwei.util.HttpClientFactory;
import com.whminwei.util.ImageFactory;
import com.whminwei.util.JacksonFactory;
import com.whminwei.util.OpenCVCamFactory;
import com.whminwei.util.RotateImageUtil;
import com.whminwei.util.SerialTool;
import com.whminwei.util.StringUtils;
import com.whminwei.util.serialException.SendDataToSerialPortFailure;
import com.whminwei.util.serialException.SerialPortOutputStreamCloseFailure;
import com.whminwei.view.MainView;
import com.whminwei.view.pages.LoadPanes;
import com.whminwei.view.pages.TransitPage;
import com.whminwei.view.plugin.CountDownPlug;
import com.whminwei.view.plugin.MediaPlug;

import cn.hutool.core.util.ImageUtil;
import javafx.application.Platform;
import javafx.concurrent.Task;

/**
 * JS 调用方法类
 *
 * @author 张嘉琪
 */
public class ClientScript {

    private static final Logger logger = LoggerFactory.getLogger(ClientScript.class);

    public static boolean managerState = false;

    public static DocxPrint docxP;
    
    /** 查询业务类型 */
	public static String printType;
	
	/**登记证明回传状态*/
    private static boolean returnResult = true;
	
    //-------------------------------本机测试开始----------------------------
 // 翻页机
    private static ComCommunication comPageTurning = null;
    // 登记证明盖章机
    public static ComCommunication djzmComSealMachine = null;
    // 权证书盖章机
    public static ComCommunication qzsComSealMachine = null;
    //-------------------------------本机测试结束（机器设备上把下面代码放开）----------------------------
    /*// 翻页机
    private static ComCommunication comPageTurning = new ComCommunication(Constant.COM.PAGE_RETURNING_COM);
    // 登记证明盖章机
    public static ComCommunication djzmComSealMachine = new ComCommunication(Constant.COM.DJZM_GAI_ZHANG_JI);
    // 权证书盖章机
    public static ComCommunication qzsComSealMachine = new ComCommunication(Constant.COM.QZS_GAI_ZHANG_JI);*/

    private static File source;// 文件复制 源文件
    private static File dest;// 文件复制 保存文件
    private static File ocrFile; // 操作证书编号识别文件作
    private static File check;// 对证书打印前两页进行拍照 检查绝对路径下是否存在文件，并进行删除件

    /** 人脸比对结果 */
	public static boolean faceRecognitionFlag = false;

    public static String gaiZhangJiComStr;// 从盖章机器串口获取的数据
    public static boolean gaiZhangJiComState;// 盖章机是否传值

    public static String pageTurningComStr;// 从翻页机器串口获取的数据
    public static boolean pageTurningComState;// 翻页机是否传值

    public static int pageCount = 0;// 当前页

    /** 判断要不要继续打印 */
    public static int num;
    
    /** 证书、证明打印线程 */
    public static Thread printThread;
    
    /**
	 * 根据业务类型设置打印类型
	 */
	public static void search(String selectPrintType) {
		logger.info("in search serchType= " + selectPrintType);
		printType = selectPrintType;
		TopController.resetCountDown();
		MediaPlug.readID();
		MainView.topController.startCounDown();
		MainView.topController.showReturnHome();
		MainView.border.getStyleClass().add("title-bg");
		MainView.border.setTop(MainView.topAnchorPane);
		// 加载读卡界面
		new LoadPanes().getReadIdCardPane();
		logger.info("out search...");
	}

    /**
     * 保存使用记录
     */
    public static void saveUsageRecord() {
    	logger.debug("人脸核验成功，当前成功次数为第："+FaceRecognitionController.faceValidateSuccess+"次");
        SubmitUsageReq req = new SubmitUsageReq();
        req.setFullName(MainView.idInformation.getFullName());
		req.setIdNumber(MainView.idInformation.getIdNumber());
		req.setDeviceNumber(Constant.DeviceInfo.DEVICE_NUMBER);
		req.setIdPic(ImageFactory.getImageStr(FaceRecognitionController.idPicPath));
//		req.setDetPic(ImageFactory.getImageStr(FaceRecognitionController.detPicPath));
		// ---------测试用放开----------
		req.setDetPic(ImageFactory.getImageStr("D:\\AgentRecord\\pics\\det\\20200901091647000002.jpg"));
		//---------测试用放开----------
        logger.info("使用记录信息：" + req.toString());
        String url = Constant.Business.BUSINESS_URL + "client/saveUsageRecord";
        String usageJson = JacksonFactory.writeJson(req);
        try {
            String resp = HttpClientFactory.getInstance().doPost(usageJson, url);
            HttpReturnDto returnDto = JacksonFactory.readJson(resp, HttpReturnDto.class);
            logger.info("保存记录结果：" + returnDto.isResult());
            if (returnDto.isResult()) {
                // 使用数据保存成功 进行数据查询
                UsageRecord record = JacksonFactory.readJson(returnDto.getRespJson(), UsageRecord.class);
                // 当前使用记录的usageId
                MainView.usageId = record.getRecordId();
                // 使用数据保存成功后，根据业务类型进入相应数据列表页面
                if(Constant.SearchType.QZS_TYPE.equals(printType)) {
                	qzsSearchType();
                }else if(Constant.SearchType.DJZM_TYPE.equals(printType)) {
                	djzmSearchType();
                }
            } else {
                LoadPanes.showMessagePane("使用记录保存失败，请重新进行操作。", 5);
            }
        } catch (Exception e) {
            LoadPanes.showMessagePane("使用记录保存过程出现异常，请联系工作人员。", -1);
            e.printStackTrace();
        }
    }
    
    /**
     * Description:查询登记证明数据
     * @Author 高拓  
     * @Date 2020年9月13日 上午9:59:43
     */
    public static void djzmSearchType() {
    	logger.info("进入登记证明操作");
		if (Constant.searchMode.DIRECT_INQUIRY.equals(Constant.StartMode.SEARCH_METHOD)) {
			// 1.刷身份证直接查询
			djzmPrintPage();
		} else {
			// 2.扫描查询
			ClientScript.forwardSm();
		}
    }
    
    /**
     * Description:查询权证书数据
     * @Author 高拓  
     * @Date 2020年9月13日 上午10:15:34
     */
    public static void qzsSearchType() {
    	logger.info("进入权证书操作");
    	if (Constant.searchMode.DIRECT_INQUIRY.equals(Constant.StartMode.SEARCH_METHOD)) {
    		// 直接查询
    		qzsPrintPage();
    	} else {
    		// 2.扫描查询
			ClientScript.forwardSm();
    	}	
    }
    
   
	/**
	 * Description:进入登记证明列表页面
	 * @Author 高拓  
	 * @Date 2020年9月14日 下午6:14:13
	 */
	public static void djzmPrintPage() {
		// 判断内部登记证明高拍仪是否开启 关闭则开启
        /*if (OpenCVCamFactory.innerWebCam == null || !OpenCVCamFactory.innerWebCam.isOpen()) {
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
                    if (!OpenCVCamFactory.innerWebCam.open()) {
                        LoadPanes.showMessagePane("登记证明高拍仪摄像头存在异常，请联系工作人员。", -1);
                    } else {
                    	logger.info("登记证明高拍仪摄像头重启成功！");
                    }
                }
            }
        } else {
            logger.info("登记证明高拍仪摄像头已正常开启！");
        }*/
		
		Platform.runLater(() -> {
			// 跳转至不动产登记数据列表
			String url = Constant.Business.BUSINESS_URL + "client/print/"+MainView.usageId;
			try {
				logger.debug("请求登记证明数据url="+url);
				MainView.web.getEngine().load(url);
			} catch (Exception e) {
                LoadPanes.showMessagePane("查询登记证明数据出现异常，请联系工作人员。", -1);
                e.printStackTrace();
			}
		});
	}
	
	/**
	 * Description:跳转至扫码页面
	 * @Author 高拓  
	 * @Date 2020年9月13日 上午10:19:08
	 */
	public static void forwardSm() {
		System.out.println("in forwardSm....");
		MediaPlug.barcodeScan();
		new LoadPanes().scan();
	}
    
    /**
     * Description:进入权证书列表页面
     */
    public static void qzsPrintPage() {
        // 判断内部摄像头是否开启 开启则关闭
        /*if (OpenCVCamFactory.webCam == null || !OpenCVCamFactory.webCam.isOpen()) {
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
                        LoadPanes.showMessagePane("内部摄像头存在异常，请联系工作人员。", -1);
                    } else {
                        String text = "queryMsg 1.内部摄像头状态正常！";
                        FileFactory.writeTXTFile(MainView.logFile, text);
                    }
                }
            }
        } else {
            logger.info("内部摄像头已开启，且正常！");
        }*/
        String url = Constant.Business.BUSINESS_URL + "client/queryHouse/" + MainView.usageId;
        Platform.runLater(() -> {
            try {
            	logger.debug("请求权证书数据url ="+url);
                MainView.web.getEngine().load(url);
            } catch (Exception e) {
                LoadPanes.showMessagePane("查询权证书数据出现异常，请联系工作人员。", 5);
                e.printStackTrace();
            }
        });
    }
    
    /**
     * Description:权证书打印
     * @param value 打印数据json
     * @param sum   查询数据总条数 用于处理 继续打印 按钮的显示隐藏控制
     */
    public void qzsPrint(String value, String sum) {
        // 停止倒计时
        stopCountDown();
        // 隐藏返回按钮
  		MainView.topController.hiddenReturnHome();
        // 语音播报
        MediaPlug.print();
        HttpClientFactory.closeClient();
        logger.info("权证书打印---->选中的打印数据【{}】", value);
        
        if (printThread != null && !printThread.isInterrupted()) {
        	printThread.interrupt();
        }
        List<RealEstateInfo> infoList = JacksonFactory.readJsonList(value, RealEstateInfo.class);
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                // 需打印证书数量
                int count = infoList.size();
                // 设备中剩余证书数量
                int remaining = Integer.parseInt(MainView.certificateRemaining);
                if (count > remaining) {
                    int c = count;
                    int r = remaining;
                    Platform.runLater(() -> {
                        MediaPlug.pageCountError();
                        // 纸张数量不足，显示返回首页按钮
 						MainView.topController.showReturnHome();
 						MainView.web.getEngine().executeScript("printError('计划打印" + c + "本,剩余空白证书不足[" + r + "]!')");
                    });
                    return null;
                }

                boolean gaiZhangJiState = true;
                boolean pageTurningState = true;
                for (int i = 0; i < infoList.size(); i++) {
                    // System.gc();
                    docxP = new DocxPrint();
                    // 打印提示
                    int f = i + 1;
                    Platform.runLater(() -> {
                        MainView.web.getEngine().executeScript("doPrint('" + f + "')");
                    });
                    
                    // 获取打印文档路径
					String wordPath = Constant.FileDir.DOCX_DIR + DateFactory.getCurrentDateString("yyyyMMddHHmmss")+ "_" + ".docx";
					
					// 准备打印文档
					if (!PrintingTool.readyPrint(infoList.get(i), wordPath)) {
						// 显示返回首页按钮
 						MainView.topController.showReturnHome();
						LoadPanes.showMessagePane(MainView.errorMessage, -1);
						return null;
					}

                    // 查询翻页机状态
                    if(!sendCheckCommand()) {
                    	// 显示返回首页按钮
 						MainView.topController.showReturnHome();
                    	return null;
                    }
                 
                    // 送证
             	   	if(!sendCertificateCommand()) {
             	   		// 显示返回首页按钮
 						MainView.topController.showReturnHome();
             	   		return null;
             	   	}
             	   
         		   // 再次查询翻页机状态
             	   	// 送证机串口返回正确数据，需要把送证的这个操作完成，整个送证操作完成大概需要7秒左右
             	   	Thread.sleep(7000);
         		   if(sendCheckCommand()) {
         			   // 开始打证
         			   File docFileout = new File(Constant.QRCode.DOCFILEOUT);
         			   if (docFileout.exists()) {
                           String url = Constant.HardWare.HARDWARE_URL + "printDoc";
                           HttpClientFactory.getInstance().doPost(Constant.QRCode.DOCFILEOUT, url);
                       } else {
                           MediaPlug.printError();
                           // 显示返回首页按钮
                           MainView.topController.showReturnHome();
                           LoadPanes.showMessagePane("系统异常，请联系工作人员。", -1);
                           return null;
                       }
         		   }else {
         			   return null;
         		   }

                    // 接收翻页机com5发送数据 共发送3次
                    int countPageTurn = 0;
                    while (pageTurningState) {
                        // 翻页机翻页打印完成 可进行拍照
                        countPageTurn++;
                        // 90s后翻页机还未检测到数据 此时设备可能出现异常
                        if (countPageTurn >= 180) {
                            MediaPlug.printMaybeError();
                            MainView.topController.showReturnHome();
                            LoadPanes.showMessagePane("证书打印过程中出现异常1，请联系工作人员检查。", -1);
                            return null;
                        }
                        logger.info("当前串口是否有数据输入【{}】", pageTurningComState);
                        if (pageTurningComState) {
                            logger.info("读取翻页机串口数据【{}】", pageTurningComStr);
                            // 判断读取到的数据是否正确
                        	if (Constant.ComValue.PUT_IN_SUCCESS.equals(pageTurningComStr) || 
                        			Constant.ComValue.SEND_SUCCESS.equals(pageTurningComStr) || 
                        			Constant.ComValue.CHECK_SUCCESS.equals(pageTurningComStr) || 
                        			Constant.ComValue.DISGORGE.equals(pageTurningComStr)) {

                                countPageTurn = 0;
                                pageCount += 1;
                                logger.info("当前页=" + pageCount);
                                // 还原翻页机状态
                                pageTurningComState = false;
                                pageTurningComStr = "";
                                if (pageCount < 3) {
                                    // 获得正确参数 可进行拍照 保存 一秒钟后再进行拍照 保证照片清晰
                                    Thread.sleep(1000);
                                    String filePath = Constant.FileDir.CERTIFICATE_IMG
                                            + DateFactory.getCurrentDateString("yyyyMMddHHmmss") + "_"
                                            + infoList.get(i).getCertNumber() + ".jpg";
                                    BufferedImage img = OpenCVCamFactory.qzsWebCam.getImage();
                                    check = new File(filePath);
                                    try {
                                        ImageIO.write(img, "jpg", check);
                                    } catch (IOException e) {
                                        MediaPlug.printError();
                                        LoadPanes.showMessagePane("证书打印过程中出现异常2，请联系工作人员。", -1);
                                        e.printStackTrace();
                                        return null;
                                    }
                                    logger.info("保存证书" + infoList.get(i).getCertNumber() + "第" + pageCount + "张照片成功！");
                                }
                                if (pageCount == 2) {
                                    logger.info("盖章机等待中......");
                                    int countGaiZhangJi = 0;
                                    String path = DateFactory.getCurrentDateString("yyyyMMddHHmmss") + ".jpg";
                                    // ocr识别,证书编号页面照片保存路径
                                    String scanerPath = Constant.FileDir.CERT_SCAN_DIR + infoList.get(i).getCertNumber() + "_" + path;
                                    while (gaiZhangJiState) {
                                        countGaiZhangJi++;
                                        // 90s后 盖章机还未检测到数据 可能出现异常
                                        if (countGaiZhangJi >= 180) {
                                            MediaPlug.printMaybeError();
                                            LoadPanes.showMessagePane("证书打印过程中出现异常3，请联系工作人员检查。", -1);
                                            break;
                                        }
                                        logger.info("读取盖章机串口数据【{}】" + gaiZhangJiComStr);
                                        if (gaiZhangJiComState && Constant.COM.GAI_ZHANG_JI_VALUE.equals(gaiZhangJiComStr)) {
                                            countGaiZhangJi = 0;
                                            // 证书数量更新
                                            remaining--;
                                            MainView.certificateRemaining = String.valueOf(remaining);
                                            // 更新盖章机状态
                                            gaiZhangJiComState = false;
                                            gaiZhangJiComStr = "";
                                            // 更新页码
                                            pageCount = 0;
                                            
                                            // OCR识别并保存权证书打印记录
                                            if (ocrRecognition(infoList.get(i), scanerPath, wordPath)) {
                                                break; // 跳出循环
                                            } else {
                                                logger.info("证书打印--->ocr识别两次均失败");
                                                pageTurningState = false;
                                                gaiZhangJiState = false;
                                                boolean res;
												res = new HttpReq().saveQzsPrintRecord(infoList.get(i).getCertNumber(),
												        infoList.get(i).getUnitNumber(), Constant.OcrCode.FAIL, wordPath,
												        scanerPath);
												// 清空图片缓存
												Toolkit.getDefaultToolkit().getImage(Constant.FileDir.ZONGDITU).flush();
												Toolkit.getDefaultToolkit().getImage(Constant.FileDir.FENBUTU).flush();
                                                if (!res) {
                                                    MediaPlug.printError();
                                                    LoadPanes.showMessagePane("权证书打印记录保存失败2，请联系工作人员", -1);
                                                    break;
                                                } else {
                                                    logger.info("证书打印--->" + infoList.get(i).getCertNumber() + ",保存打印记录成功！");
                                                    // 证书打印记录保存成功后，将证书吐出
                                                    SerialTool.serialPort = qzsComSealMachine.getComNum();
                                                    ocrDiscernSuccess(qzsComSealMachine);
                                                    break;
                                                }
                                            }
                                        }
                                        Thread.sleep(500);
                                    }
                                }
                            } else {
                                MediaPlug.printError();
                                logger.error("打印方法--->打印证书" + infoList.get(i).getCertNumber() + "过程中 翻页机异常");
                                LoadPanes.showMessagePane("证书打印过程中出现异常4,请联系工作人员。", -1);
                                return null;
                            }
                        }
                        Thread.sleep(500);
                    }
                }
                // 加载打印完成页面
                logger.info("打印完成");
                Platform.runLater(() -> {
                    CountDownPlug.startCountDown();
                    MediaPlug.printSuccess();
                    String url = Constant.Business.BUSINESS_URL + "client/printSuccess";
                    MainView.web.getEngine().load(url);
                });
                return null;
            }

        };
        printThread = new Thread(task);
        printThread.setDaemon(true);
        printThread.start();
    }
    
    /**
     * ocr识别
     */
    public boolean ocrRecognition(RealEstateInfo realEstateInfo, String scanerPath, String wordFilePath) {
    	// 识别次数 初始默认2次 
        int defaultCount = 2;
        // 打印结果 true：识别和保存均成功
        boolean printState = false;
        File fileImg = null;
        for (int a = 0; a < defaultCount; a++) {
            // 先清空ocr识别文件夹
            ClientScript.clearOCR();
            // 进行证书编号页面拍照
            try {
				Thread.sleep(3000);
			} catch (Exception e) {
				e.printStackTrace();
			}// 保证能获取清晰图像
            BufferedImage img = OpenCVCamFactory.qzsWebCam.getImage();
            // 进行照片保存
            ocrFile = new File(scanerPath);
            try {
                ImageIO.write(img, "jpg", ocrFile);
            } catch (IOException e) {
                MediaPlug.printError();
                LoadPanes.showMessagePane("证书照片保存失败,请联系现场工作人员。", -1);
                e.printStackTrace();
                return false;
            }

            // 判断scaner文件夹下是否存在文件 存在则进行ocr识别
            ocrFile = new File(Constant.Scaner.SCANER_DIR);
            if (ocrFile.listFiles().length > 0) {
                logger.info("证书打印--->获取证书" + realEstateInfo.getCertNumber() + " 编号盖章页面照片成功");
                fileImg = ocrFile.listFiles()[0];
                String targetPath = Constant.Scaner.SCANER_DIR + "disposeState.jpg";
				try {
					disposeImage(fileImg, targetPath);
				} catch (IOException e) {
					e.printStackTrace();
					logger.error("图片处理异常。");
                    LoadPanes.showMessagePane("证书照片处理异常,请联系现场工作人员。", -1);
                    return false;
				}
                // 开始识别
                HttpReturnDto returnDto = ocrRecognition(targetPath);
                if (returnDto.isResult()) {
                    String ocr = returnDto.getRespJson();
                    logger.info("ocr识别结果：" + ocr);
					boolean res;
					try {
						// 保存权证书打印记录
						res = new HttpReq().saveQzsPrintRecord(realEstateInfo.getCertNumber(),realEstateInfo.getUnitNumber(), ocr, wordFilePath,scanerPath);
						// 清空图片缓存
						Toolkit.getDefaultToolkit().getImage(Constant.FileDir.ZONGDITU).flush();
						Toolkit.getDefaultToolkit().getImage(Constant.FileDir.FENBUTU).flush();
					} catch (Exception e) {
						e.printStackTrace();
						LoadPanes.showMessagePane("权证书打印记录保存出错，请联系工作人员", -1);
                        return false;
					}
                    if (!res) {
                        MediaPlug.printError();
                        LoadPanes.showMessagePane("权证书打印记录保存失败1，请联系工作人员", -1);
                        return false;
                    } else {
                        logger.info("权证书打印--->打印证书" + realEstateInfo.getCertNumber() + ",保存打印记录 成功！");
                        // ocr识别成功， 证书打印记录保存成功，将证书吐出
                        SerialTool.serialPort = qzsComSealMachine.getComNum();
                        try {
							ocrDiscernSuccess(qzsComSealMachine);
						} catch (Exception e) {
							e.printStackTrace();
							logger.error("吐证指令发送异常，请联系工作人员。");
							LoadPanes.showMessagePane("吐证出现异常,请联系工作人员。", -1);
			                return false;
						}
                        printState = true;
                    }
                }else {
                	LoadPanes.showMessagePane(returnDto.getRespMsg(), -1);
                }
            } else {
                MediaPlug.printError();
                logger.error("证书打印--->内部错误 ： 证书编号照片scaner文件夹为空！");
                LoadPanes.showMessagePane("未检测到证书照片文件,请联系工作人员。", -1);
                return false;
            }
        }
		return printState;
    }

    /**
     * Description:发送查询指令
     */
    private boolean sendCheckCommand() throws SendDataToSerialPortFailure, SerialPortOutputStreamCloseFailure, InterruptedException {
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
    			switch(ClientScript.pageTurningComStr) {
	        	case Constant.ComValue.CHECK_SUCCESS:
	        		logger.info("发送查询，翻页机串口返回结果：正常");
					checkCommandStatues = true;
		            break;
	        	case Constant.ComValue.CHECK_LACK:
	                LoadPanes.showMessagePane("翻页机状态：缺证，请联系工作人员。", -1);
	                break;
	        	case Constant.ComValue.CHECK_STUCK:
	        		LoadPanes.showMessagePane("翻页机状态：卡证，请联系工作人员。", -1);
	                break;
	        	case Constant.ComValue.CHECK_MALFUNCTION:
	        		LoadPanes.showMessagePane("翻页机状态：故障，请联系工作人员。", -1);
	                break;
	        	case Constant.ComValue.CHECK_LACK_STUCK:
	        		LoadPanes.showMessagePane("翻页机状态：缺证 卡证，请联系工作人员。", -1);
	                break;
	        	case Constant.ComValue.CHECK_LACK_MALFUNCTION:
	        		LoadPanes.showMessagePane("翻页机状态：缺证 故障，请联系工作人员。", -1);
	                break;
	        	case Constant.ComValue.CHECK_STUCK_MALFUNCTION:
	        		LoadPanes.showMessagePane("翻页机状态：卡证 故障，请联系工作人员。", -1);
	                break;
	        	case Constant.ComValue.CHECK_LACK_STUCK_MALFUNCTION:
	        		LoadPanes.showMessagePane("翻页机状态：缺证 卡证 故障，请联系工作人员。", -1);
	                break;
	        	default:
	    			break;    
    			}
    		}else {
    			if (valueFailureCount == 5) {
                    // 第一次发送串口请求没有接收到数据后，重新发送5次，如果5次都没有返回信息，停止发送，将结果返回
                    LoadPanes.showMessagePane("查询-翻页机串口请求失败，请联系工作人员。", -1);
                    break;
                }
                valueFailureCount++;
        	}
        	Thread.sleep(500);
        }
    	return checkCommandStatues;
    }        
    
    /**
     * Description:发送送证指令
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
    			if (Constant.ComValue.SEND_SUCCESS.equals(ClientScript.pageTurningComStr)) {
    				logger.info("发送送证，翻页机串口返回结果：正常");
    				sendCertificateStatues = true;
                    break;
    			}
    		}else {
    			if (valueFailureCount == 5) {
                    // 第一次发送串口请求没有接收到数据后，重新发送5次，如果5次都没有返回信息，停止发送，将结果返回
                    LoadPanes.showMessagePane("送证-翻页机串口请求失败，请联系工作人员。", -1);
                    break;
                }
                valueFailureCount++;
        	}
    		Thread.sleep(500);
    	}
    	return sendCertificateStatues;
    }

    /**
     * Description:处理图片，将图片旋转、裁剪，提高ocr识别率
     * @param fileImg 源文件路径
     * @param targetPath 目标路径
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
            LoadPanes.showMessagePane("图片处理出现异常1，请联系工作人员。", -1);
            return false;
        }
        if (!cropState) {
            MediaPlug.printError();
            LoadPanes.showMessagePane("图片处理出现异常2，请联系工作人员。", -1);
            return false;
        }
        return true;
    }

    /**
     * Description: ocr识别
     * @param targetPath 图片路径
     */
   
    private static HttpReturnDto ocrRecognition(String targetPath) {
    	logger.info("开始ocr识别");
        HttpReturnDto dto = new HttpReturnDto();
        dto.setRespJson(targetPath);
        String url = Constant.HardWare.HARDWARE_URL + "identifyOcr" + "?recognitionPath=" + targetPath;
        HttpResult rspn = HttpClientFactory.getInstance().doGetThrowException(url, 1);
		if (rspn.getResult()) {
			dto = JacksonFactory.readJson(rspn.getData(), HttpReturnDto.class);
		} else {
			// 请求发送异常
			dto.setResult(false);
			dto.setRespMsg(MainView.errorMessage);
		}
		return dto;
    }

    /**
     * Description: ocr识别成功后 发送走纸指令
     * @param comCommunication 串口对象
     */
    private static void ocrDiscernSuccess(ComCommunication comCommunication)
            throws SendDataToSerialPortFailure, SerialPortOutputStreamCloseFailure, InterruptedException {
        // 向盖章机传递走纸指令
        byte[] success = {(byte) 254, 1, 5, (byte) 151, (byte) 178, 34, 3};
        SerialTool.sendToPort(comCommunication.getComNum(), success);
        int sendCount = 0;
        boolean sendState = true;
        while (sendState) {
            sendCount++;
            if (gaiZhangJiComState) {
                if (Constant.ComValue.SEND_SUCCESS.equals(gaiZhangJiComStr)) {
                    logger.info("指令下发成功");
                    break;
                }
            }
            // 十秒钟
            if (sendCount > 20) {
                MediaPlug.printMaybeError();
                sendState = false;
                LoadPanes.showMessagePane("送证指令无响应，请联系工作人员。", -1);
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
     * Description:继续打印
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
     * Description:删除url指向的文件
     */
    private static void cleanPic(String url) {
        check = new File(url);
        if (check.exists()) {
            check.delete();
        }
    }

    /**
     * Description:保存文件 绝对路径
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
     * Description:清空ocr文件夹
     */
    private static void clearOCR() {
        ocrFile = new File(Constant.Scaner.SCANER_DIR);
        if (ocrFile.isDirectory()) {
            FileFactory.deleteDirs(Constant.Scaner.SCANER_DIR);
        }
    }

    /**
     * Description:返回首页
     */
    public void home() {
    	MainView.home();
    }
    
    /**
     * Description:登记证明打印完成语音提示
     */
    public void complete() {
		MediaPlug.complete();
	}
    
    /**
     * Description:登记证明打印
     */
 	public void djzmPrint(String json) {
 		returnResult = true;
 		logger.info("进入登记证明打印方法");
        // 把即将打印的数据写入日志
 		stopCountDown();
 		// 隐藏返回按钮
 		MainView.topController.hiddenReturnHome();
 		
 		if (printThread != null && !printThread.isInterrupted()) {
        	printThread.interrupt();
        }
 		Task<Void> task = new Task<Void>() {
 			@Override
 			protected Void call() throws Exception {
 				List<EstateInfo> selected = JacksonFactory.readJsonList(json, EstateInfo.class);
 				// 计算打印总量
 				int count = selected.size();
 				int remaining = Integer.parseInt(MainView.paperRemaining);
 				if (count > remaining) {
 					int c = count;
 					int r = remaining;
 					Platform.runLater(() -> {
 						// 纸张数量不足，显示返回首页按钮
 						MainView.topController.showReturnHome();
 						MediaPlug.zmPageCountError();
 						MainView.web.getEngine().executeScript("printError('计划打印" + c + "张,剩余空白证明不足[" + r + "]!')");
 					});
 					return null;
 				}
 				EstateInfo printData;
 				for (int n = 0; n < selected.size(); n++) {
 					int currentNum = n++;
					printData = selected.get(n);
 					// 打印提示
 					Platform.runLater(() -> {
 						MainView.web.getEngine().executeScript("doPrint('" + currentNum + "','" + count + "')");
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
 					parameters.put("ewm", printData.getEwm());
 					logger.info("开始打印");
 					HttpReturnDto jasperPrintReq = new HttpReturnDto();
 					jasperPrintReq.setRespJson(Constant.JasperPath.JASPER_PATH);
 					jasperPrintReq.setRespObj(parameters);
 					String jasperPrintRspn = HttpClientFactory.getInstance().doPost(
 							JacksonFactory.writeJson(jasperPrintReq),
 							Constant.HardWare.HARDWARE_URL + "jasperPrint");
 					HttpReturnDto printRspn = JacksonFactory.readJson(jasperPrintRspn, HttpReturnDto.class);
 					logger.info("打印请求响应【{}】",printRspn);
 					if(printRspn != null) {
 						if (!printRspn.isResult()) {
 	 						Platform.runLater(() -> {
 	 							MainView.web.getEngine().executeScript("printError('" + printRspn.getRespMsg() + "')");
 	 						});
 	 						return null;
 	 					}
 					}else {
 						Platform.runLater(() -> {
 							MainView.web.getEngine().executeScript("printError('打印请求超时，请联系工作人员。')");
 						});
 						return null;
 					}
 					
 					//高拍仪拍照获取照片并进行ocr识别
					boolean getImageFlag = true;
					long startTime = System.currentTimeMillis();
					while (getImageFlag) {
						long endTime = System.currentTimeMillis();
						if (endTime - startTime <= 50 * 1000) {
							logger.info("登记证明盖章机串口返回"+gaiZhangJiComStr);
 							if (StringUtils.isNotNullAndEmpty(gaiZhangJiComStr)) {
// 								System.out.println("gaiZhangJiComStr3="+gaiZhangJiComStr);
								// 盖章完成,下一步进行拍照
								if (Constant.COM.GAI_ZHANG_JI_VALUE.equals(gaiZhangJiComStr)) {
									gaiZhangJiComStr = "";
									Thread.sleep(500);
									logger.info("开始拍照");
									String scanFilePath = Constant.Scaner.SCANER_DIR + "/scanCapture.jpg";
									BufferedImage img = OpenCVCamFactory.djzmWebCam.getImage();
									File ocrFile = new File(scanFilePath);
									ImageIO.write(img, "jpg", ocrFile);
									img.flush();
									Thread.sleep(2000);
									// 向盖章机发送走纸指令
									ocrDiscernSuccess(djzmComSealMachine);
									break;
								}
							}
						} else {
							logger.error("盖章机超时");
							MediaPlug.printError();
					        showAttention("登记证明盖章超时，请联系工作人员。", -1);
							break;
						}
					}
 					// 成功打印后修改数据库证明数，并获取实时OCR印刷编号
 					String ocr = "";
 					boolean result = HttpReq.updateRemainPager();
 					if (result) {
 						// 剩余纸张数量-1
 						remaining--;
 						MainView.paperRemaining = String.valueOf(remaining);
 					} else {
 						Platform.runLater(() -> {
 							MainView.web.getEngine().executeScript("printError('证明数修改失败，请联系工作人员!')");
 						});
 						return null;
 					}
 					boolean flag = true;
 					// 裁剪图片保存路径
 					String tailorFilePath = Constant.Scaner.SCANER_DIR + "/tailor.jpg";
 					do {
 						File dir = new File(Constant.Scaner.SCANER_DIR);
 						if (dir.isDirectory()) {
 							if (dir.listFiles().length > 0) {
 								// 获取扫描件
 								File file = dir.listFiles()[0];
 								//图片旋转
								ImageUtil.rotate(file, Constant.Tailor.DEGREE, file);
 								String filePath = file.getAbsolutePath();
 								logger.info("开始进行第一次ocr识别");
 								String rspn = HttpClientFactory.getInstance().doGet(Constant.HardWare.HARDWARE_URL + "identifyOcr" + "?recognitionPath="+tailorFilePath);
 								HttpReturnDto httpReturnDto = JacksonFactory.readJson(rspn, HttpReturnDto.class);
 								ocr = httpReturnDto.getRespJson();
 								if(Constant.OcrCode.FAIL.equals(ocr)) {
 									logger.info("ocr识别失败,开始进行第二次ocr识别");
 	 								BufferedImage bufferedImage = ImageIO.read(new File(filePath));
 	 								// 设置截图尺寸
 									BufferedImage newBufferedImage = bufferedImage.getSubimage(Constant.Tailor.X_AXIS,
 											Constant.Tailor.Y_AXIS, Constant.Tailor.WIDTH, Constant.Tailor.HEIGHT);
 	 								ImageIO.write(newBufferedImage, "jpg", new File(tailorFilePath));
 	 								rspn = HttpClientFactory.getInstance().doGet(Constant.HardWare.HARDWARE_URL + "identifyOcr" + "?recognitionPath="+tailorFilePath);
 	 								httpReturnDto = JacksonFactory.readJson(rspn, HttpReturnDto.class);
 	 								ocr = httpReturnDto.getRespJson();
 								}
 								logger.info("OCR识别出的登记证明序列号", ocr);
 								// 回传扫描件
 								boolean res = new HttpReq().saveDjzmPrintRecord(filePath, printData.getCertNumber(),
 										printData.getBusiNumber(), httpReturnDto.getRespJson());
 								if (!res) {
 									Platform.runLater(() -> {
 										MainView.web.getEngine().executeScript("printError('扫描件保存失败，请联系工作人员!')");
 										logger.error("扫描件保存失败");
 									});
 									return null;
 								}
 								flag = false;
 							}
 						}
 						Thread.sleep(1000);
 					} while (flag);
 					// 打印完成并二次登簿
 					Platform.runLater(() -> {
 						 MainView.web.getEngine().executeScript("estateWriteBack('"+ MainView.usageId+"')");
                    });
 					//等待回传结果
                    while (returnResult) {
                        Thread.sleep(1000);
                    }
                    returnResult = true;
 				}
 				Platform.runLater(() -> {
 					MainView.web.getEngine().executeScript("complete()");
                });
 				startCountDown(10);
 				logger.info("打印结束..............");
 				return null;
 			}
 		};
 		printThread = new Thread(task);
 		printThread.setDaemon(true);
 		printThread.start();
 	}
 	
 	/**
     * @Description: 修改回传状态标志符
     * @Author: 罗子敬
     * @Date: 2020/9/4 17:02
     **/
    public void changeResult() {
        System.out.println("---修改回传状态标志符---");
        returnResult = false;
    }

    /**
     * Description:停止倒计时
     * @Author 高拓  
     * @Date 2020年9月14日 下午1:49:22
     */
    public static void stopCountDown() {
        MainView.topController.stopCountDown();
    }

    /**
     * Description:开始倒计时（指定时长）
     * @param num 
     * @Author 高拓  
     * @Date 2020年9月14日 下午1:49:37
     */
    public void startCountDown(int num) {
        logger.info("开始倒计时" + num);
        MainView.topController.startCountDown(num);
    }
 	
}
