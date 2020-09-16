package com.thfdcsoft.estate.printer.util;

import java.awt.Dimension;
import java.util.List;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.objdetect.Objdetect;
import org.opencv.videoio.VideoCapture;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamResolution;
import com.thfdcsoft.estate.printer.constant.Constant;
import com.thfdcsoft.estate.printer.view.pages.TransitPage;
import com.thfdcsoft.estate.printer.view.script.ClientScript;

import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.concurrent.Task;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * 使用opencv調用攝像頭
 *
 * @author weaso
 *
 */
public class OpenCVCamFactory {

	//初始化标志
	public static boolean isInitiated = false;
	private static int absoluteFaceSize;

	private static ObjectProperty<Image> normalImageProperty;
	private static ObjectProperty<Image> infraredImageProperty;

	public static ImageView normalCapturedImage = new ImageView();// 正常摄像头
	public static ImageView infraredCapturedImage = new ImageView();// 红外摄像头

	static {

		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

		normalCapturedImage.setFitWidth(600);
		normalCapturedImage.prefWidth(600);
		normalCapturedImage.setPreserveRatio(true);// 锁定图像宽高比，避免由于图像变形造成的人脸识别失败

		infraredCapturedImage.setFitWidth(600);
		infraredCapturedImage.prefWidth(600);
		infraredCapturedImage.setPreserveRatio(true);// 锁定图像宽高比，避免由于图像变形造成的人脸识别失败
	}

	// 普通摄像头
	public static VideoCapture normalCapture;
	// 红外摄像头
	public static VideoCapture infraredCapture;
	// 权证书高拍仪摄像头
	public static Webcam webCam = null;
	// 登记证明高拍仪摄像头
	public static Webcam innerWebCam = null;

	private static CascadeClassifier faceCascade;

	// 摄像头状态
	public static boolean cameraActive = false;

	// 是否获取到合适图片，获取到之后将不在进行人脸跟踪
	private static boolean rightPic = false;

	// 是否完成身份证读取
	public static boolean succeedReadID = false;
	// 普通摄像头线程
	public static Thread normalThreadcam;
	// 红外摄像头线程;
	public static Thread infraredThreadcam;
	// 标志是否进入人脸页面
	public static boolean facePageState = false;
	// 可识别现场照片生成状态 控制是否启用人证比对
	public static boolean saveDetPicState = false;

	public static boolean manageInitCam() {
		if (normalCapture.isOpened() && infraredCapture.isOpened()) {
			return true;
		} else {
			OpenCVCamFactory of = new OpenCVCamFactory();
			of.startCamera();
			if (normalCapture.isOpened() && infraredCapture.isOpened()) {
				return true;
			}else {
				return false;
			}
		}
	}

	public static void initWebCam() {
		String path = PropertyFactory.getPath() + "opencv";
		// 加载人脸识别xml文件
		// 1:普通摄像头 0：红外摄像头
		String classifierPath = path + "/haarcascade_frontalface_alt2.xml";

		normalCapture = new VideoCapture();
		infraredCapture = new VideoCapture();

		faceCascade = new CascadeClassifier();
		faceCascade.load(classifierPath);

		normalImageProperty = new SimpleObjectProperty<Image>();
		infraredImageProperty = new SimpleObjectProperty<Image>();

		absoluteFaceSize = 0;
		// 检查摄像头是否能正常启用
		// 0:普通摄像头 3：红外摄像头
		if (normalCapture.open(3) && infraredCapture.open(2)) {
			isInitiated = true;
			cameraActive = true;// 标志摄像头已开启
			OpenCVCamFactory oo = new OpenCVCamFactory();
			oo.startCamera();
		} else {
			isInitiated = false;
		}
		// 初始化权证书高拍仪摄像头
		if (webCam != null) {
			webCam.close();
		}
		// 初始化登记证明高拍仪摄像头
		if (innerWebCam != null) {
			innerWebCam.close();
		}
		List<Webcam> camList = Webcam.getWebcams();
		for (Webcam webcam : camList) {
			if (Constant.WebCam.WEBCAM_NAME.equals(webcam.getName())) {
				webCam = webcam;
				Dimension[] nonStandardResolutions = new Dimension[] { WebcamResolution.PAL.getSize(),
						WebcamResolution.VGA.getSize(), new Dimension(1280, 960), };
				webCam.setCustomViewSizes(nonStandardResolutions);
				webCam.setViewSize(new Dimension(1280, 960));
				System.out.println("权证书高拍仪摄像头初始化成功...");
				if (!webCam.open()) {
					isInitiated = false;
				} else {
					isInitiated = true;
				}
			}else if (Constant.WebCam.INNER_CAM.equals(webcam.getName())) {
				innerWebCam = webcam;
				Dimension[] nonStandardResolutions = new Dimension[] { WebcamResolution.PAL.getSize(),
//						WebcamResolution.VGA.getSize(), new Dimension(1400, 1000), };
						WebcamResolution.VGA.getSize(), new Dimension(1280, 960), };
				innerWebCam.setCustomViewSizes(nonStandardResolutions);
				innerWebCam.setViewSize(new Dimension(1280, 960));
				System.out.println("登记证明高拍仪摄像头初始化成功...");
				if (!innerWebCam.open()) {
					isInitiated = false;
				} else {
					isInitiated = true;
				}
			}
			
		}
	}

	/**
	 * 开启摄像头
	 */
	public void startCamera() {
		if (!cameraActive) {
			normalCapture.open(3);
			infraredCapture.open(2);
			if (normalCapture.isOpened() && infraredCapture.isOpened()) {
				cameraActive = true;
				normalCam();
				infraredCam();
			} else {
				Platform.runLater(() -> {
					TransitPage.attentionPage("摄像头存在故障，请联系工作人员。", -1);
				});
			}
		} else {
			normalCam();
			infraredCam();
		}
	}

	/**
	 * 关闭摄像头头
	 */
	public static void stopCamera() {
		if (normalThreadcam != null && !normalThreadcam.isInterrupted()) {
			normalThreadcam.interrupt();
		}
		if (infraredThreadcam != null && !infraredThreadcam.isInterrupted()) {
			infraredThreadcam.interrupt();
		}
		cameraActive = false;
		normalCapture.release();
		infraredCapture.release();
	}

	/**
	 * 普通摄像头头 运行 仅作显示
	 */
	public void normalCam() {
		if (normalThreadcam != null && !normalThreadcam.isInterrupted()) {
			normalThreadcam.interrupt();
		}
		normalThreadcam = new Thread(new Task<Void>() {
			@Override
			protected Void call() throws Exception {
				int count = 0;
				while (normalCapture.isOpened()) {
					count ++;
					if (facePageState) {
						count = 0;
						// 有效地抓取和处理单个帧
						Mat frame = normalGrabFrame(normalCapture);
						// 转换并显示帧
						Image imageToShow = OpenCVCamUtil.mat2Image(frame);
						//保存彩色图片
						String idPicPathTemp = PropertyFactory.getPath() + "opencv" + "/faces/" + "normalPic" + ".jpg";
						Imgcodecs.imwrite(idPicPathTemp, frame);
						normalImageProperty.set(imageToShow);
					}
					//三分钟后不使用 则关闭摄像头
					if(count > 6000 && !facePageState) {
						System.out.println("摄像头已3分钟未使用，即将关闭。");
						cameraActive = false;
						stopCamera();
						System.gc();
						break;
					}
					Thread.sleep(30);
				}
				return null;
			}
		});
		normalCapturedImage.imageProperty().bind(normalImageProperty);
		normalThreadcam.setDaemon(true);
		normalThreadcam.start();
	}

	/**
	 * 红外摄像头运行 作活体检测操作
	 */
	public void infraredCam() {
		if (infraredThreadcam != null && !infraredThreadcam.isInterrupted()) {
			infraredThreadcam.interrupt();
		}
		infraredThreadcam = new Thread(new Task<Void>() {
			@Override
			protected Void call() throws Exception {
				while (infraredCapture.isOpened()) {
					if (facePageState) {
						// 当前未获取到合适图像 限制进行人脸识别
						rightPic = false;
						// 有效地抓取和处理单个帧
						Mat frame = infraredGrabFrame(infraredCapture);
						// 转换并显示帧
						Image imageToShow = OpenCVCamUtil.mat2Image(frame);
						infraredImageProperty.set(imageToShow);
						// 获得合适的人脸图片 身份证读取成功 后进行图片存 人脸识别
						if (rightPic && succeedReadID) {
							Mat f = new Mat();
							infraredCapture.read(f);
							if (!f.empty()) {
								String path = PropertyFactory.getPath() + "opencv";
								String detPicPathTemp = path + "/faces/" + "infraredPic" + ".jpg";
								saveDetPicState = Imgcodecs.imwrite(detPicPathTemp, frame);

								if (ClientScript.faceAndID.getFaceComparisonState()) {
									if (ClientScript.managerState) {
										// 管理员验证
										String idNumber = ClientScript.faceAndID.getIdNumber();
										System.out.println("管理员人脸识别的身份证号:" + idNumber);
										ClientScript clientScript = new ClientScript();
										clientScript.manageYanzheng(idNumber);
										// 标识即将离开人脸页面
										OpenCVCamFactory.facePageState = false;
										// 还原人证比对结果
										ClientScript.faceAndID.resetFaceComparisonState();
										// 改变可识别现场照片生成状态 停止人证比对方法
										OpenCVCamFactory.saveDetPicState = false;
										// break;
									} else {
										ClientScript.saveUsageRecord();
										// 标识即将离开人脸页面
										OpenCVCamFactory.facePageState = false;
										// 还原人证比对结果
										ClientScript.faceAndID.resetFaceComparisonState();
										// 改变可识别现场照片生成状态 停止人证比对方法
										OpenCVCamFactory.saveDetPicState = false;
										// break;
									}
								} else {
									Thread.sleep(50);
								}
							}
						}
					}
					Thread.sleep(30);
				}
				return null;
			}
		});
		infraredCapturedImage.imageProperty().bind(infraredImageProperty);
		infraredThreadcam.setDaemon(true);
		infraredThreadcam.start();
	}

	/**
	 * 处理单个帧
	 */
	private static Mat normalGrabFrame(VideoCapture capture) {
		Mat frame = new Mat();
		if (capture.isOpened()) {
			try {
				capture.read(frame);
				if (!frame.empty()) {
					// 人脸跟踪并显示
					normalDetectAndDisplay(frame);
				}
			} catch (Exception e) {
				System.err.println("Exception during the image elaboration: " + e);
			}
		}
		return frame;
	}

	/*
	 * 处理单个帧
	 */
	private static Mat infraredGrabFrame(VideoCapture capture) {
		Mat frame = new Mat();
		if (capture.isOpened()) {
			try {
				capture.read(frame);
				if (!frame.empty()) {
					// 人脸跟踪并显示
					infraredDetectAndDisplay(frame);
				}
			} catch (Exception e) {
				System.err.println("Exception during the image elaboration: " + e);
			}
		}
		return frame;
	}

	/*
	 * 人脸跟踪并显示
	 */
	private static void normalDetectAndDisplay(Mat frame) {
		MatOfRect faces = new MatOfRect();
		Mat grayFrame = new Mat();

		// 转换帧 灰度
		Imgproc.cvtColor(frame, grayFrame, Imgproc.COLOR_BGR2GRAY);
		Imgproc.equalizeHist(grayFrame, grayFrame);

		// 计算最小面尺寸(在我们的例子中，是框架高度的20%)
		if (absoluteFaceSize == 0) {
			int height = grayFrame.rows();
			if (Math.round(height * 0.2f) > 0) {
				absoluteFaceSize = Math.round(height * 0.2f);
			}
		}

		// 检测人脸
		faceCascade.detectMultiScale(grayFrame, faces, 1.1, 3, 0 | Objdetect.CASCADE_SCALE_IMAGE,
				new Size(358d / 3d, 441d / 3d), grayFrame.size());

		// 画人脸
		Rect[] facesArray = faces.toArray();
		int width = 0;
		Rect face = null;
		for (int i = 0; i < facesArray.length; i++) {
			if (facesArray[i].width >= width) {
				width = facesArray[i].width;
				face = facesArray[i];
			}
		}
		if (face != null) {
			Imgproc.rectangle(frame, face.tl(), face.br(), new Scalar(0d, 255d, 0d), 3);
		}
	}

	/*
	 * 人脸跟踪并显示
	 */
	private static void infraredDetectAndDisplay(Mat frame) {
		MatOfRect faces = new MatOfRect();
		Mat grayFrame = new Mat();

		// 转换帧 灰度
		Imgproc.cvtColor(frame, grayFrame, Imgproc.COLOR_BGR2GRAY);
		Imgproc.equalizeHist(grayFrame, grayFrame);

		// 计算最小面尺寸(在我们的例子中，是框架高度的20%)
		if (absoluteFaceSize == 0) {
			int height = grayFrame.rows();
			if (Math.round(height * 0.2f) > 0) {
				absoluteFaceSize = Math.round(height * 0.2f);
			}
		}

		// 检测人脸
		faceCascade.detectMultiScale(grayFrame, faces, 1.1, 3, 0 | Objdetect.CASCADE_SCALE_IMAGE,
				new Size(358d / 3d, 441d / 3d), grayFrame.size());

		// 画人脸
		Rect[] facesArray = faces.toArray();
//		int width = 0;
//		Rect face = null;
		for (int i = 0; i < facesArray.length; i++) {
			if (facesArray[i].width >= Integer.parseInt(Constant.FaceDetect.FACESIZE)) {
//				System.out.println("获得合适图像");
				rightPic = true;
//				if (facesArray[i].width >= width) {
//					width = facesArray[i].width;
//					face = facesArray[i];
//				}
			}
			Imgproc.rectangle(frame, facesArray[i].tl(), facesArray[i].br(), new Scalar(0d, 255d, 0d), 3);
		}
//		if (face != null) {
//			Imgproc.rectangle(frame, face.tl(), face.br(), new Scalar(0d, 255d, 0d), 3);

//		}
	}
}
