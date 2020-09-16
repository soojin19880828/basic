package com.thfdcsoft.hardware.factory;

import zkteco.id100com.jni.id100sdk;

public class IDCardReadFactory {

	public static boolean isInitiated = false;
	
	public static int initComm() {
		System.out.println("初始化 端口号=" + id100sdk.InitCommExt());
		//自动搜索并连接身份证阅读器 成功返回设备端口（串口:1~16;USB:1001~1016） 
		//失败返回0
		int i = id100sdk.InitCommExt();
		if (i <= 0) {
			System.out.printf("InitCommExt fail\n");
			return -1;
		}
		System.out.printf("InitCommExt succ\n");
		isInitiated = true;
		return i;
	}
}
