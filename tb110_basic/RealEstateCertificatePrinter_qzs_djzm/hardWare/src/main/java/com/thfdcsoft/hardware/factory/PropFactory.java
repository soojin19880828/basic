package com.thfdcsoft.hardware.factory;

import java.io.*;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Properties;

import com.thfdcsoft.hardware.constant.Constant;
import com.thfdcsoft.hardware.hardwareApplication;

public class PropFactory {

	public static Properties properties = new Properties();
	
	public static String getPath() {
		URL url = hardwareApplication.class.getProtectionDomain().getCodeSource().getLocation();
		try {
			String path = URLDecoder.decode(url.getPath(), "UTF-8");
			//判断path是否以file:/开始
			while(path.startsWith("file:/")) {
				path = path.substring(6);
			}
			//判断path是否以/开始
			while(path.startsWith("/")) {
				path = path.substring(1);
			}
			//判断path是否以.jar的后缀结束
			if (path.contains(".jar"))  {  
	            path = path.substring(0, path.lastIndexOf(".jar") + 4);
	            path = path.substring(0, path.lastIndexOf("/") + 1);
	        }
			//判断path是否以target/classes/的后缀结束
			if (path.endsWith("target/classes/"))  {  
	            path = path.substring(0, path.lastIndexOf("target"));   
	        }
			return path;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
//		return "G:/FaceDetect/";
	}

	public static void initProperties() {
		try {
			String filePath = getPath();
			filePath += Constant.FileName.PROPERTIES;
			System.out.println("读取配置文件:" + filePath);
			InputStream is = new BufferedInputStream(new FileInputStream(filePath));
			properties.load(new InputStreamReader(is, "UTF-8"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
