package com.thfdcsoft.estate.printer.util;

import com.thfdcsoft.estate.printer.view.script.ClientScript;
import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.objdetect.Objdetect;

import java.awt.image.BufferedImage;

public class FaceFactory {

	public static BufferedImage mat2BI(Mat mat) {
		int dataSize = mat.cols() * mat.rows() * (int) mat.elemSize();
		byte[] data = new byte[dataSize];
		mat.get(0, 0, data);
		int type = mat.channels() == 1 ? BufferedImage.TYPE_BYTE_GRAY : BufferedImage.TYPE_3BYTE_BGR;

		if (type == BufferedImage.TYPE_3BYTE_BGR) {
			for (int i = 0; i < dataSize; i += 3) {
				byte blue = data[i + 0];
				data[i + 0] = data[i + 2];
				data[i + 2] = blue;
			}
		}
		BufferedImage image = new BufferedImage(mat.cols(), mat.rows(), type);
		image.getRaster().setDataElements(0, 0, mat.cols(), mat.rows(), data);
		return image;
	}

	public static Mat detectFace(Mat img, CascadeClassifier faceDetector, Mat gray, MatOfRect faces) throws Exception {
		System.out.println("in DetectFace ... ");
		// 调整图片，准备识别
		Imgproc.cvtColor(img, gray, Imgproc.COLOR_BGR2GRAY);
		Imgproc.equalizeHist(gray, gray);
		System.out.println("before detectMultiScale ... ");
		/*
		 * 1.image表示的是要检测的输入图像
		 * 2.objects表示检测到的人脸目标序列
		 * 3.scaleFactor表示每次图像尺寸减小的比例
		 * 4. minNeighbors表示每一个目标至少要被检测到3次才算是真的目标(因为周围的像素和不同的窗口大小都可以检测到人脸)
		 * 5.minSize为目标的最小尺寸
		 * 6.minSize为目标的最大尺寸
		 * */
		faceDetector.detectMultiScale(gray, faces, 1.1, 3, 0 | Objdetect.CASCADE_SCALE_IMAGE,
				new Size(358 / 2, 441 / 2), img.size());
		System.out.println("after detectMultiScale ... ");
		Rect[] rects = faces.toArray();
		Rect detFace = new Rect();
		for (Rect face : rects) {
			// 获取最大的人脸作为识别结果
			if (face.width > detFace.width) {
				detFace = face;
			}
		}
		if (detFace.width > 0) {
			if ((detFace.y > 0) && ((detFace.y + detFace.height) < 480)) {
				// 款选识别区域
				Imgproc.rectangle(img, detFace.tl(), detFace.br(), new Scalar(0, 255, 0), 3);
				// 成功框到人脸
				ClientScript.faceAndID.setFacedStateTrue();
			} else {
				ClientScript.faceAndID.setFacedStateFalse();
			}
		} else {
			ClientScript.faceAndID.setFacedStateFalse();
		}
		gray.release();
		faces.release();
		return img;
	}
}
