package com.yunmai;

import java.io.UnsupportedEncodingException;

import com.sun.jna.Native;
import com.sun.jna.win32.StdCallLibrary;
import com.thfdcsoft.hardware.constant.Constant;


public class DllDemo {
	public interface YMOCR extends StdCallLibrary {

		String HardwareAddress = Constant.YunMai.YUNMAI_ROOT;
//		String HardwareAddress = "D:\\dll";
		YMOCR verifyEngine = (YMOCR) Native.loadLibrary(HardwareAddress+"\\HCBCR" , YMOCR.class);// 初始化引擎

		/**
		 * char *inFileName：传入要识别的图片路径, 
		 * char *outresultOK：表示存放识别结果。至少1024字节, 
		 * int size（数组大小）:识别的长度，这里默认为1024就行。
		 * char *pData：传入ocr_data文件夹的路径, 
		 * char *pConfig：传入ScanPen_PC.cfg文件的路径
		 * int TmpDllMain(char *inFileName, char *outresultOK, int size,char *pData, char *pConfig);
		 * 返回值：1：成功，0：失败，100：过期，300：识别次数超过
		 */
		public int TmpDllMain(String inFileName, byte[] outresultOK, int size, String pData,String pConfig);
		
	}
	
	public static void main(String[] args) throws Exception {
		
		//System.out.println(System.getProperty("java.library.path"));//引擎存放位置
		String HardwareAddress ="G:\\Workspace\\Default\\RealEstatePrinterClient\\yunmai";

		//原图片（完整路径包含后缀）
		String inFileName = "D:\\3.jpg";
		//识别结果
		byte[] outresultOK = new byte[1024];
		//识别的长度
	    int size = 1024;
		//ocr_data文件夹的路径
//		String pData = "C:\\Users\\Administrator\\Desktop\\whth\\ocr_data";
	    String pData = HardwareAddress+"\\ocr_data";
		//cfg文件的路径
//		String pConfig = "C:\\Users\\Administrator\\Desktop\\whth\\ScanPen_PC.cfg";
	    String pConfig = HardwareAddress+"\\ScanPen_PC.cfg";
		int result = 0;
		try {
			result = YMOCR.verifyEngine.TmpDllMain(inFileName, outresultOK, size, pData,pConfig);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("result:"+result);
		System.out.println("**************************");
		System.out.println(new String(outresultOK,"gb2312"));
//		System.out.println(outresultOK.toString();
	}
	
	public String GetOCR(String inFileName) throws UnsupportedEncodingException
	{
		String HardwareAddress = Constant.YunMai.YUNMAI_ROOT;
		//识别结果
		byte[] outresultOK = new byte[1024];
		//识别的长度
	    int size = 1024;
		//ocr_data文件夹的路径
	    String pData = HardwareAddress+"\\ocr_data";
		//cfg文件的路径
	    String pConfig = HardwareAddress+"\\ScanPen_PC.cfg";
		int result = 0;
		try {
			result = YMOCR.verifyEngine.TmpDllMain(inFileName, outresultOK, size, pData,pConfig);
		} catch (Exception e) {
			
			e.printStackTrace();
			return "false";
		}
		System.out.println("result:"+result);
		System.out.println("**************************");
		System.out.println(new String(outresultOK,"gb2312").split("\r\n")[0]);	
		System.out.println("0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0\n\n");
		if(result != 1)
		{
			return "false";
		}else
		{
			return new String(outresultOK,"gb2312").split("\r\n")[0];
		}
	}
}
