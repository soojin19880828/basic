package com.thfdcsoft.estate.printer.util;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoCapture;

import com.github.sarxos.webcam.Webcam;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * 使用webcam调用摄像头
 * 
 * @author 张嘉琪
 *
 */
public class WebCamFactory {

	public static boolean isInitiated = false;

	public static Webcam webCam = null;// 正常摄像头
	public static Webcam colorWebCam = null;// 红外摄像头
	

	private static ObjectProperty<Image> imageProperty = new SimpleObjectProperty<Image>();
	private static ObjectProperty<Image> imageProperty1 = new SimpleObjectProperty<Image>();

	public static ImageView webCamCapturedImage = new ImageView();// 正常摄像头
	public static ImageView ColorWebCamCaptureImage = new ImageView();// 红外摄像头
	static {

		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

		webCamCapturedImage.setFitWidth(600);
		webCamCapturedImage.prefWidth(600);
		webCamCapturedImage.setPreserveRatio(true);// 锁定图像宽高比，避免由于图像变形造成的人脸识别失败

		ColorWebCamCaptureImage.setFitWidth(600);
		ColorWebCamCaptureImage.prefWidth(600);
		ColorWebCamCaptureImage.setPreserveRatio(true);// 锁定图像宽高比，避免由于图像变形造成的人脸识别失败
	}

	private static VideoCapture capture = new VideoCapture(1);
	private static VideoCapture capture1 = new VideoCapture(0);
	private static ScheduledExecutorService timer;
	private static ScheduledExecutorService timer1;
	public void initWebCam() {
		if (capture.open(1) && capture1.open(0)) {
			isInitiated = true;
		}
		Runnable frameGrabber = new Runnable() {
			@Override
			public void run() {
				// effectively grab and process a single frame
				Mat frame = grabFrame();
				// convert and show the frame
				Image imageToShow = WebCamFactory.mat2Image(frame);
				imageProperty.set(imageToShow);
			}
		};
		Runnable frameGrabber1 = new Runnable() {
			@Override
			public void run() {
				// effectively grab and process a single frame
				Mat frame = grabFrame1();
				// convert and show the frame
				Image imageToShow = WebCamFactory.mat2Image(frame);
				imageProperty1.set(imageToShow);
			}
		};
		timer = Executors.newSingleThreadScheduledExecutor();
		timer.scheduleAtFixedRate(frameGrabber, 0, 33, TimeUnit.MILLISECONDS);
		webCamCapturedImage.imageProperty().bind(imageProperty);
		
		timer1 = Executors.newSingleThreadScheduledExecutor();
		timer1.scheduleAtFixedRate(frameGrabber1, 0, 33, TimeUnit.MILLISECONDS);
		ColorWebCamCaptureImage.imageProperty().bind(imageProperty1);

		//
		// if (webCam != null) {
		// webCam.close();
		// }
		// if (webCam1 != null) {
		// webCam1.close();
		// }
		// for(Webcam cam : Webcam.getWebcams()) {
		// System.out.println("摄像头名字："+cam.getName());
		// //红外
		// if ("Logitech HD Webcam C525 0".equalsIgnoreCase(cam.getName())) {
		// webCam1 = cam;
		// webCam1.setViewSize(webCam1.getViewSizes()[2]);//
		// 使用最高的清晰度[width=640,height=480]
		// }else if("Integrated Camera 1".equalsIgnoreCase(cam.getName())) {
		// webCam = cam;
		// webCam.setViewSize(webCam.getViewSizes()[2]);//
		// 使用最高的清晰度[width=640,height=480]
		// }
		// }
		// if(webCam.open() && webCam1.open()) {
		// isInitiated = true;
		// }
		// startWebCam();
		// startWebCam1();
	}

	public static Image mat2Image(Mat frame) {
		try {
			return SwingFXUtils.toFXImage(matToBufferedImage(frame), null);
		} catch (Exception e) {
			System.err.println("Cannot convert the Mat obejct: " + e);
			return null;
		}
	}

	private static BufferedImage matToBufferedImage(Mat original) {
		// init
		BufferedImage image = null;
		int width = original.width(), height = original.height(), channels = original.channels();
		byte[] sourcePixels = new byte[width * height * channels];
		original.get(0, 0, sourcePixels);

		if (original.channels() > 1) {
			image = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
		} else {
			image = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
		}
		final byte[] targetPixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
		System.arraycopy(sourcePixels, 0, targetPixels, 0, sourcePixels.length);

		return image;
	}

	private Mat grabFrame() {
		// init everything
		Mat frame = new Mat();

		// check if the capture is open
		if (capture.isOpened()) {
			try {
				// read the current frame
				capture.read(frame);

				// if the frame is not empty, process it
				if (!frame.empty()) {
					Mat rgb = new Mat();
					Imgproc.cvtColor(frame, rgb, Imgproc.COLOR_BGR2RGB);
					Mat gray = new Mat();
					Imgproc.cvtColor(rgb, gray, Imgproc.COLOR_RGB2GRAY);
					// Imgproc.cvtColor(frame, frame, Imgproc.COLOR_BGR2RGB);
					// Imgproc.cvtColor(frame, frame, Imgproc.COLOR_RGB2GRAY);
				}

			} catch (Exception e) {
				// log the error
				System.err.println("Exception during the image elaboration: " + e);
			}
		}

		return frame;
	}
	
	private Mat grabFrame1() {
		// init everything
		Mat frame = new Mat();

		// check if the capture is open
		if (capture1.isOpened()) {
			try {
				// read the current frame
				capture1.read(frame);

				// if the frame is not empty, process it
				if (!frame.empty()) {
					Mat rgb = new Mat();
					Imgproc.cvtColor(frame, rgb, Imgproc.COLOR_BGR2RGB);
					Mat gray = new Mat();
					Imgproc.cvtColor(rgb, gray, Imgproc.COLOR_RGB2GRAY);
					// Imgproc.cvtColor(frame, frame, Imgproc.COLOR_BGR2RGB);
					// Imgproc.cvtColor(frame, frame, Imgproc.COLOR_RGB2GRAY);
				}

			} catch (Exception e) {
				// log the error
				System.err.println("Exception during the image elaboration: " + e);
			}
		}

		return frame;
	}

	public static void stopAcquisition() {
		if (timer != null && !timer.isShutdown()) {
			try {
				// stop the timer
				timer.shutdown();
				timer.awaitTermination(33, TimeUnit.MILLISECONDS);
			} catch (InterruptedException e) {
				// log any exception
				System.err.println("Exception in stopping the frame capture, trying to release the camera now... " + e);
			}
		}

		if (capture.isOpened()) {
			// release the camera
			capture.release();
		}
	}

	// private static Thread thread;
	// public static void startWebCam() {
	// if(thread != null && !thread.isInterrupted()){
	// thread.interrupt();
	// }
	// thread = new Thread(new Task<Void>() {
	// @Override
	// protected Void call() throws Exception {
	// AtomicReference<WritableImage> ref = new AtomicReference<>();
	// BufferedImage img = null;
	// while ((img = webCam.getImage()) != null) {
	// ref.set(SwingFXUtils.toFXImage(img, ref.get()));
	// img.flush();
	// imageProperty.set(ref.get());
	// }
	// return null;
	// }
	// });
	//
	// thread.setDaemon(true);
	// thread.start();
	//
	// webCamCapturedImage.imageProperty().bind(imageProperty);
	// }
	//
	// private static Thread thread1;
	// public static void startWebCam1() {
	// if(thread1 != null && !thread1.isInterrupted()){
	// thread1.interrupt();
	// }
	// thread1 = new Thread(new Task<Void>() {
	// @Override
	// protected Void call() throws Exception {
	// AtomicReference<WritableImage> ref = new AtomicReference<>();
	// BufferedImage img = null;
	// while ((img = webCam1.getImage()) != null) {
	// ref.set(SwingFXUtils.toFXImage(img, ref.get()));
	// img.flush();
	// imageProperty1.set(ref.get());
	// }
	// return null;
	// }
	// });
	//
	// thread1.setDaemon(true);
	// thread1.start();
	//
	// webCamCapturedImage1.imageProperty().bind(imageProperty1);
	// }

}
