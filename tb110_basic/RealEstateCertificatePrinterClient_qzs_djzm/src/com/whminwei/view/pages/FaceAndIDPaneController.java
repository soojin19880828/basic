package com.whminwei.view.pages;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.groovy.runtime.wrappers.ShortWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.whminwei.constant.Constant;
import com.whminwei.dto.req.FaceValidateReq;
import com.whminwei.dto.rspn.HttpReturnDto;
import com.whminwei.util.*;
import com.whminwei.view.MainView;
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

    private static boolean faceComparisonState = false;

    private static int count = 0;// 记录验证失败次数

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

    public boolean getFaceComparisonState() {
        return faceComparisonState;
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

    public void reSetCount() {
        count = 0;
    }

    public void resetFaceComparisonState() {
        faceComparisonState = false;
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
    
    public void setID() {
    	fullName.setText("gaotuo");
        sex.setText("nan");
        nation.setText("zhongguo");
        idNumber.setText("421126198808280119");
        idPic.setImage(null);
        chkResTest.setText("");
        detPic.setImage(null);
        idPicPath = "D:/AgentRecord/pics/id/20180202094429_421126198808280119.jpg";
        detPicPath = "D:/AgentRecord/pics/det/20180202094429_421126198808280119.jpg";
    }

    /**
     * 人脸识别读身份证
     *
     * @return
     */
    public void readIDCard() {
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                // 初始化数据
                resetID();
                String url = Constant.HardWare.HARDWARE_URL + "readIDCard";
                HttpReturnDto returndto = null;
				try {
					String returnvalue = HttpClientFactory.getInstance().doGet(url);
					returndto = JacksonFactory.readJson(returnvalue, HttpReturnDto.class);
					logger.info("请求returndto = {}", returndto);
				} catch (Exception e) {
					TransitPage.attentionPage("硬件系统非正常关闭，即将重启硬件系统", 8);
					Thread.sleep(7000);
					logger.info("读卡器连接异常，重启硬件服务");
					Runtime.getRuntime().exec(Constant.Restart.HARDWARE);
				}
                if (returndto != null && returndto.isResult()) {
                    IdReadInformation idInformation = JacksonFactory.readJson(returndto.getRespJson(),
                            IdReadInformation.class);
                    fullName.setText(idInformation.getFullName());
                    sex.setText(idInformation.getSex());
                    nation.setText(idInformation.getNation());
                    idNumber.setText(idInformation.getIdNumber());
                    idPicPath = idInformation.getIdPicPath();
                    String expireDate = idInformation.getExpireDate();
                    // 检查身份证是否过期
                    if("长期".equals(expireDate)) {
                    	logger.info("身份证有效期为长期，校验已通过！");
                    }else {
                    	String sdf = "yyyyMMdd";
                        boolean value = DateFactory.checkDate(expireDate, sdf);
                        if (!value) {
                            TransitPage.attentionPage("此身份证已过期，无法进行业务办理", 15);
                            MediaPlug.IDPastDue();
                        } else {
                            logger.info("身份证校验已通过！");
                        }
                    }
                    FileInputStream detFis = new FileInputStream(idPicPath);
                    Image idImg = new Image(detFis);
                    idPic.setImage(idImg);
                    logger.info("身份证读取成功：" + idNumber.getText());
                    // 设置人脸跟踪可进行合适人脸照片保存
                    OpenCVCamFactory.succeedReadID = true;
                    detFis.close();
                } else {
                    MainView.home();
                }
                return null;
            }
        };
        Thread readIDthread = new Thread(task);
        readIDthread.setDaemon(true);
        readIDthread.start();
    }

    public static Thread faceComparisonThread;

    /**
     * 人证对比
     * @param detPicPathTemp
     */
    /*public void faceComparison(String detPicPathTemp) {
        faceComparisonThread = new Thread(new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                // 可识别现场照片生成状态 满足识别要求
                while (OpenCVCamFactory.facePageState) {
                    if (OpenCVCamFactory.saveDetPicState) {
                        try {
                            FaceValidateReq req = new FaceValidateReq();
                            if (Constant.FaceDetect.SITE_LOCAL.equalsIgnoreCase(Constant.FaceDetect.FACE_DETECT_SITE)) {
                                // 本地模式下传输照片地址
                                req.setIdPicPath(idPicPath);
                                req.setDetPicPath(detPicPathTemp);
                            } else if (Constant.FaceDetect.SITE_NETWORK
                                    .equalsIgnoreCase(Constant.FaceDetect.FACE_DETECT_SITE)) {
                                // 网络模式下传输照片文件
                                req.setIdPicFile(ImageFactory.getImageStr(idPicPath));
                                req.setDetPicFile(ImageFactory.getImageStr(detPicPathTemp));
                            } else {
                                TransitPage.attentionPage("比对模式设置有误，请联系工作人员", -1);
                                faceComparisonState = false;
                            }
                            if (Constant.IDReader.IDREADER_MODE.equals(Constant.IDReader.Mode.DISABLE)) {
                                req.setIdNumber(Constant.IDReader.TEST_ID);
                            } else {
                                req.setIdNumber(ClientScript.faceAndID.getIdNumber());
                            }

                            THface thface = new THface();
                            thface.THFaceInit(Constant.FaceDetect.THFACE_PATH);

                            logger.info("faceComparisonThread idPicPath = {}", idPicPath);
                            logger.info("faceComparisonThread detPicPathTemp = {}", detPicPathTemp);
                            if (StringUtils.isNotEmpty(idPicPath) && StringUtils.isNotEmpty(detPicPathTemp)) {
                                float degree = thface.compareFaces(idPicPath, detPicPathTemp);

                                detPicPath = PropertyFactory.getPath() + "opencv" + "/faces/" + "normalPic" + ".jpg";

                                logger.info("相似度={}", degree);
                                if (degree >= Constant.FaceDetect.SIMILARITY) {
                                    Platform.runLater(() -> {
                                        ClientScript.faceAndID.chkResTest.getStyleClass().clear();
                                        ClientScript.faceAndID.chkResTest.getStyleClass().add("green-text");
                                        ClientScript.faceAndID.chkResTest.setText("验证成功：" + String.format("%.2f",degree));
                                    });
                                    faceComparisonState = true;
                                    break;
                                } else {
                                    Platform.runLater(() -> {
                                        count++;
                                        logger.info("失败次数=" + count);
                                        if (count % 2 == 0) {
                                            MediaPlug.validateFailure();
                                        } else {
                                            MediaPlug.validateFail();
                                        }
                                        ClientScript.faceAndID.chkResTest.getStyleClass().clear();
                                        ClientScript.faceAndID.chkResTest.getStyleClass().add("red-text");
                                        ClientScript.faceAndID.chkResTest.setText("验证失败：" + String.format("%.2f",degree));
                                        logger.info("验证失败，即将重新验证");
                                    });
                                    faceComparisonState = false;
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    Thread.sleep(100);
                }
                return null;
            }
        });
        faceComparisonThread.setDaemon(true);
        faceComparisonThread.start();
    }*/

}
