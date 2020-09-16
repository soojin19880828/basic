package com.thfdcsoft.estate.printer.util;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Properties;

import com.thfdcsoft.estate.printer.client.Main;
import com.thfdcsoft.estate.printer.constant.Constant;

public class PropertyFactory {
	
	public static Properties properties = new Properties();
	
	public static String getPath() {
		URL url = Main.class.getProtectionDomain().getCodeSource().getLocation();
		try {
			String path = URLDecoder.decode(url.getPath(), "UTF-8");
			//判断path是否以/开始
			if(path.startsWith("/")) {
				path = path.substring(1);
			}
			//判断path是否以.jar的后缀结束
			if (path.endsWith(".jar"))  {  
	            path = path.substring(0, path.lastIndexOf("/") + 1);    
	        }
			//判断path是否以bin/的后缀结束
			if (path.endsWith("bin/"))  {  
	            path = path.substring(0, path.lastIndexOf("bin"));   
	        }
			return path;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
//		return "C:\\Users\\liu\\Desktop\\新建文件夹\\";
		return null;
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
