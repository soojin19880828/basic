package com.thfdcsoft.hardware.constant;

import com.thfdcsoft.hardware.factory.PropFactory;

public interface Constant {
	// JNative
	// 用于调用动态连接库dll
	public interface JNative {
		
		public static final String JNATIVE_DLL = PropFactory.getPath() + "jnative/JNativeCpp.dll";
	}

	// 文件名称
	public interface FileName {

		public static final String PROPERTIES = "hardWare.properties";

	}

	// 身份证读卡器
	public interface IDReader {
		
		public static final String IDREADER_ROOT = PropFactory.getPath() + "idreader";
		
		public static final String IDREADER_DLL = PropFactory.getPath() + "idreader/Termb.dll";
		
		public static final String IDREADER_WZ = PropFactory.getPath() + "idreader/wz.txt";
		
		public static final String IDREADER_ZP = PropFactory.getPath() + "idreader/zp.bmp";
		
		public static final String IDCARDPIC =  Constant.IDReader.IDREADER_ROOT + "/faces";
		
	}
	
	// OCR识别印刷序列号
	public interface YunMai{
		
		public static final String YUNMAI_ROOT = PropFactory.getPath() + "yunmai";
	}

}
