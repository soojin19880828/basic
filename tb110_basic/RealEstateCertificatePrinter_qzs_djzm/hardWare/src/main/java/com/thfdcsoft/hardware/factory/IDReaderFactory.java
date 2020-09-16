package com.thfdcsoft.hardware.factory;

import org.xvolks.jnative.JNative;
import org.xvolks.jnative.Type;
import org.xvolks.jnative.exceptions.NativeException;

import com.thfdcsoft.hardware.constant.Constant;
import com.thfdcsoft.hardware.properties.StaticConfig;

public class IDReaderFactory {
	
	public static boolean isInitiated = false;

	static {
		System.setProperty("jnative.debug", "true");
		System.setProperty("jnative.loadNative", Constant.JNative.JNATIVE_DLL);
	}
	
	// 初始化连接
	public static int initComm() {
		try {
			JNative initComm = new JNative(Constant.IDReader.IDREADER_DLL, "CVR_InitComm");
			initComm.setRetVal(Type.INT);
			initComm.setParameter(0, StaticConfig.idreaderPort);
			initComm.invoke();
			if("1".equals(initComm.getRetVal())) {
				isInitiated = true;
			}
			return Integer.parseInt(initComm.getRetVal());
		} catch (NativeException | IllegalAccessException e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	// 识卡
	public static int authenticate() {
		try {
			JNative authenticate = new JNative(Constant.IDReader.IDREADER_DLL, "CVR_Authenticate");
			authenticate.setRetVal(Type.INT);
			authenticate.invoke();
			return Integer.parseInt(authenticate.getRetVal());
		} catch (NativeException | IllegalAccessException e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	// 读卡
	public static int readContent() {
		try {
			JNative readContent = new JNative(Constant.IDReader.IDREADER_DLL, "CVR_Read_Content");
			readContent.setRetVal(Type.INT);
			readContent.setParameter(0, 4);
			readContent.invoke();
			return Integer.parseInt(readContent.getRetVal());
		} catch (NativeException | IllegalAccessException e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	// 关闭连接
	public static int closeComm() {
		try {
			JNative closeComm = new JNative(Constant.IDReader.IDREADER_DLL, "CVR_CloseComm");
			closeComm.setRetVal(Type.INT);
			closeComm.invoke();
			return Integer.parseInt(closeComm.getRetVal());
		} catch (NativeException | IllegalAccessException e) {
			e.printStackTrace();
		}
		return -1;
	}

	// 获取连接状态
	public static int getState() {
		try {
			JNative getState = new JNative(Constant.IDReader.IDREADER_DLL, "CVR_GetState");
			getState.setRetVal(Type.INT);
			getState.invoke();
			return Integer.parseInt(getState.getRetVal());
		} catch (NativeException | IllegalAccessException e) {
			e.printStackTrace();
		}
		return -1;
	}
}
