package com.thfdcsoft.hardware.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

//配置文件数据加载
@Component 
@ConfigurationProperties(prefix = "com.hardware")
public class StaticConfig
{
	public static int port;
	public static int x;
	public static int y;
	public static int idreaderPort;
	
	public static int getPort() {
		return port;
	}
	public static void setPort(int port) {
		StaticConfig.port = port;
	}
	public static int getX() {
		return x;
	}
	public static void setX(int x) {
		StaticConfig.x = x;
	}
	public static int getY() {
		return y;
	}
	public static void setY(int y) {
		StaticConfig.y = y;
	}
	public static int getIdreaderPort() {
		return idreaderPort;
	}
	public static void setIdreaderPort(int idreaderPort) {
		StaticConfig.idreaderPort = idreaderPort;
	}
}
