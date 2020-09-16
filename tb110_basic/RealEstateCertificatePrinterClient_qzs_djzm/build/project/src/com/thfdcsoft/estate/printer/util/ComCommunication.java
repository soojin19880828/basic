package com.thfdcsoft.estate.printer.util;

import com.thfdcsoft.estate.printer.util.serialException.NoSuchPort;
import com.thfdcsoft.estate.printer.util.serialException.NotASerialPort;
import com.thfdcsoft.estate.printer.util.serialException.PortInUse;
import com.thfdcsoft.estate.printer.util.serialException.SerialPortParameterFailure;
import com.thfdcsoft.estate.printer.util.serialException.TooManyListeners;

import gnu.io.SerialPort;

/**
 * 指定端口打开 并添加监听
 * @author weason
 *
 */
public class ComCommunication {

	public SerialPort comNum;
	
	public String comPort;
	

	public SerialPort getComNum() {
		return comNum;
	}

	public void setComNum(SerialPort comNum) {
		this.comNum = comNum;
	}

	public String getComPort() {
		return comPort;
	}

	public void setComPort(String comPort) {
		this.comPort = comPort;
	}

//	public static void main(String[] args) {
//		
////		ComCommunication t = new ComCommunication("COM4");
//		Dimension[] nonStandardResolutions = new Dimension[] { WebcamResolution.PAL.getSize(),
//				WebcamResolution.VGA.getSize(), new Dimension(1280, 960), };
//		Webcam cam = null;
//		List<Webcam> camList = Webcam.getWebcams();
//		for (Webcam webcam : camList) {
//			System.out.println(webcam.getName());
//			if("USB CAMERA 2".equals(webcam.getName())) {
//				cam = webcam;
//				webcam.setCustomViewSizes(nonStandardResolutions);
//				cam.setViewSize(new Dimension(1280, 960));
////				cam.setViewSize(cam.getViewSizes()[1]);// 使用最高的清晰度[width=640,height=480]
//			}
//		}
//		if(cam.open()) {
//			BufferedImage img = cam.getImage();
//			File f = new File("D:\\1.jpg");
//			try {
//				ImageIO.write(img, "jpg", f);
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//	}

	public ComCommunication(String com) {
		comPort = com;
		try {
			comNum = SerialTool.openPort(comPort, 9600);
		} catch (SerialPortParameterFailure e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (NotASerialPort e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (NoSuchPort e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (PortInUse e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		ComListener listener = new ComListener(comNum,comPort);
		try {
			SerialTool.addListener(comNum, listener);
		} catch (TooManyListeners e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}
	

}
