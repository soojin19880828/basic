package com.thfdcsoft.hardware.ocr;

import java.io.IOException;

public class OCRTool {

	public native int recognize(String fileName, String result);
	
	static{
		System.loadLibrary("OCRTool");
	}

	public static int doOcr(String fileName, String result){
		OCRTool ocr = new OCRTool();
		int i = ocr.recognize(fileName, result);
		return i;
	}
	
	public static void main(String[] args) throws IOException {
		System.out.println(OCRTool.class.getProtectionDomain().getCodeSource().getLocation());
		System.loadLibrary("OCRTool");
		OCRTool ocr = new OCRTool();
		String result = "";
		int i = ocr.recognize("D:/123.jpg", result);
		System.out.println(i);
		System.out.println(result);
		System.out.println(OCRTool.class.getProtectionDomain().getCodeSource().getLocation());
	}
}
