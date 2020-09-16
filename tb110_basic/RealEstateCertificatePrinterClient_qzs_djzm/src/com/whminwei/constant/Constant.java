package com.whminwei.constant;

import com.whminwei.util.PropertyFactory;

public interface Constant {

	// 文件名称
	public interface FileName {

		public static final String PROPERTIES = "client.properties";

		public static final String OPERATOR_PROP = "operator.properties";
	}

	// 启动模式
	public interface StartMode {

		public static final String START_MODE = PropertyFactory.properties.getProperty("startMode");

		public static final String DEBUG = "debug";// 开发

		public static final String FORMAL = "formal";// 正式

		public static final Integer COUND_DOWN = Integer.parseInt(PropertyFactory.properties.getProperty("countDown"));
		
		// 手写调用地址路径			
		public static final String HANDINPUT_PATH = PropertyFactory.properties.getProperty("handInput");
		public static final String KEYBOARD_REPAIR = PropertyFactory.properties.getProperty("keyboard");
		
		// 1 直接查询 , 2 扫码查询
		public static final String SEARCH_METHOD = PropertyFactory.properties.getProperty("searchMethod");
	}
	
	// 重启bat文件
	public interface Restart {
		public static final String TOMCAT = PropertyFactory.properties.getProperty("restartTomcat");
		public static final String HARDWARE = PropertyFactory.properties.getProperty("restartHardWare");
		public static final String QUERYIII = PropertyFactory.properties.getProperty("restartQueryIII");
	}

	// 设备信息
	public interface DeviceInfo {

		public static final String DEVICE_NAME = PropertyFactory.properties.getProperty("deviceName");

		public static final String TECHNICAL_SUPPORT = PropertyFactory.properties.getProperty("technicalSupport");

		public static final String PRINTER_NAME = PropertyFactory.properties.getProperty("printerName");

		public static final String DEVICE_NUMBER = PropertyFactory.properties.getProperty("deviceNumber");

		public static final String NOTICE_TEXT = PropertyFactory.properties.getProperty("noticeText");
	}
	
	// JNative
	// 用于调用动态连接库dll
//	public interface JNative {
//
//		public static final String JNATIVE_DLL = PropertyFactory.getPath() + "jnative/JNativeCpp.dll";
//	}

	// 身份证读卡器
	public interface IDReader {

		public static final String IDREADER_MODE = PropertyFactory.properties.getProperty("idreaderMode");

		public interface Mode {
			// 启用
			public static final String ENABLE = "enable";
			// 禁用
			public static final String DISABLE = "disable";
			// 无效[启用身份证读卡器，使用测试ID进行业务查询]
			public static final String INVALID = "invalid";
		}

//		public static final String IDREADER_ROOT = PropertyFactory.getPath() + "idreader";
//
//		public static final String IDREADER_WZ = PropertyFactory.getPath() + "idreader/wz.txt";
//
//		public static final String IDREADER_ZP = PropertyFactory.getPath() + "idreader/zp.bmp";

//		public static final int IDREADER_PORT = Integer
//				.parseInt(PropertyFactory.properties.getProperty("idreaderPort"));

		public static final String TEST_ID = PropertyFactory.properties.getProperty("testID");
	}

	// 摄像头
	public interface WebCam {

		public static final String WEBCAM_ROOT = PropertyFactory.getPath() + "webcam";
		
		public static final String WEBCAM_NAME = PropertyFactory.properties.getProperty("cameraName");
		public static final String INNER_CAM = PropertyFactory.properties.getProperty("innerCam");
//		public static final String NORMAL_WEBCAM = PropertyFactory.properties.getProperty("normalWebCam");
//		public static final String INFRARED_WEBCAM = PropertyFactory.properties.getProperty("infraredWebCam");
	}

	// 人脸识别
	public interface FaceDetect {

		public static final String SITE_LOCAL = "local";

		public static final String SITE_NETWORK = "network";

		public static final String FACE_DETECT_SITE = PropertyFactory.properties.getProperty("faceDetectSite");

		public static final String FACE_DETECT_URL = PropertyFactory.properties.getProperty("faceDetectUrl");
		
		public static final String FACESIZE = PropertyFactory.properties.getProperty("facesize");
		
		public static final double SIMILARITY = Double.parseDouble(PropertyFactory.properties.getProperty("similarity"));

		public static final String OPENCV_PATH = PropertyFactory.getPath() + "opencv";

		public static final String THFACE_PATH = PropertyFactory.getPath() + "thface";

	}

	// 业务系统
	public interface Business {
		public static final String BUSINESS_URL = PropertyFactory.properties.getProperty("businessUrl");
	}
	
	
	
	//硬件系统
	public interface HardWare{
		public static final String HARDWARE_URL = PropertyFactory.properties.getProperty("hardWareUrl");
	}
		
	// 后台管理系统地址
	public interface Manage{
		public static final String MANAGE_URL = PropertyFactory.properties.getProperty("manage");
	}

	// 扫描仪
	public interface Scaner {

		public static final String SCANER_DIR = PropertyFactory.getPath() + "scaner/";
		public static final String RotateImage = PropertyFactory.getPath() + "rotate";
	}
	
	public interface ScanQrCode {
		// 二维码长度设置
		public static final int QRCODE_LENGTH = Integer.valueOf(PropertyFactory.properties.getProperty("qrCodeLength"));
	}
	

	// 盖章机
//	public interface NativeFunc {
//
//		public static final String DLLPATH = PropertyFactory.getPath() + "dll";
//
//		public static final String NATIVE_FUNC_DLLPATH = PropertyFactory.getPath() + "dll/NativeFunc.dll";
//	}

	// OCR识别印刷序列号
	public interface OcrCode {
		public static final String FAIL = "00000000000";
	}

	// 二维码图片存放地址
	// 印章图片存放地址
	public interface QRCode {

		public static final String QRCODE_PATH = PropertyFactory.getPath() + "qrcode/qrcode.png";
		public static final String YINZHANG_PATH = PropertyFactory.getPath() + "yinzhang/yinzhang.png";
		public static final String MOBAN_PATH = PropertyFactory.getPath() +"docFile/moban.docx";
		public static final String DOCFILEOUT = PropertyFactory.getPath() +"docFileout/docFileout.docx";
		
		
	}
	
	
	// 日志相关文件目录
		public interface FileDir {

			// 身份证照片
			public static final String ID_PIC_DIR = PropertyFactory.properties.getProperty("fileDir") + "pics/id/";

			// 现场照片
			public static final String DET_PIC_DIR = PropertyFactory.properties.getProperty("fileDir") + "pics/det/";

			// 证书编号识别备份文件
			public static final String CERT_SCAN_DIR = PropertyFactory.properties.getProperty("fileDir") + "pics/scan/";
			
			// 日志文件
			public static final String LOG_DIR = PropertyFactory.properties.getProperty("fileDir") + "logs/";
			
			// word文档备份文件
			public static final String DOCX_DIR = PropertyFactory.properties.getProperty("fileDir") + "docx/";
			
			// 证书打印照片文件
			public static final String CERTIFICATE_IMG = PropertyFactory.properties.getProperty("fileDir") + "certificate/";
			
			//宗地图路径
			public static final String ZONGDITU = PropertyFactory.getPath() + "realEstatePic/zongditu.png";
			
			//分布图路径
			public static final String FENBUTU = PropertyFactory.getPath() + "realEstatePic/fenbutu.png";
		}
		// 日志相关文件后缀名
		public interface SuffixName {

			// 照片
			public static final String JPG = ".jpg";
			
			// 日志
			public static final String LOG = ".txt";
		}
		
		public interface COM {
			
			// 翻页部件com端口
			public static final String PAGE_RETURNING_COM = PropertyFactory.properties.getProperty("pageTurningCOM");
			public static final String DJZM_GAI_ZHANG_JI = PropertyFactory.properties.getProperty("djzmGaiZhangJi");
			public static final String QZS_GAI_ZHANG_JI = PropertyFactory.properties.getProperty("qzsGaiZhangJi");
			//设备端口正常值
			public static final String GAI_ZHANG_JI_VALUE = PropertyFactory.properties.getProperty("gaiZhangJiValue");
			public static final String PAGE_RETURNING_COM_VALUE = PropertyFactory.properties.getProperty("pageTurningCOMValue");
		}
		
		//登记证明打印模板路径
		public interface JasperPath {

			public static final String JASPER_PATH = PropertyFactory.getPath() + "djzm.jasper";
		}
		
		// OCR识别截图参数
		public interface Tailor {

			// x轴坐标
			public static final Integer X_AXIS = Integer.parseInt(PropertyFactory.properties.getProperty("tailorX"));

			// y轴坐标
			public static final Integer Y_AXIS = Integer.parseInt(PropertyFactory.properties.getProperty("tailorY"));

			// 宽度width
			public static final Integer WIDTH = Integer.parseInt(PropertyFactory.properties.getProperty("tailorW"));

			// 高度Height
			public static final Integer HEIGHT = Integer.parseInt(PropertyFactory.properties.getProperty("tailorH"));
			
			//图像旋转度数
			public static final Integer DEGREE = Integer.parseInt(PropertyFactory.properties.getProperty("degree"));

		}
		
		// 打印类型
		public interface SelectType {
			
			// 登记证明打印类型
			public static final String DJZM = "TH-ZB-102-ZZDY1HJ";
			
			// 权证书打印类型
			public static final String QZS = "TH-ZB-108-ZZDY1HJ";
			
		}
		
		/**登记证明查询标志*/
		public interface searchMode {
			/**直接查询*/
			public static final String DIRECT_INQUIRY = "1";
		}
		
		/**业务类型*/
		public interface SearchType {
			/**权证书业务类型*/
			public static final String QZS_TYPE = "qzs";
			/**登记证明业务类型*/
			public static final String DJZM_TYPE = "djzm";
		}
		
		// 证书/证明数量查询条件
		public interface TerminalId {
			public static final String DJZM_PRINT = PropertyFactory.properties.getProperty("djzmTerminal");
			public static final String QZS_PRINT = PropertyFactory.properties.getProperty("qzsTerminal");
		}
		
		/**
		 * @Description: 翻页机与盖章机串口返回信息枚举类
		 * @Author 高拓
		 * @Date 2020年9月14日 下午3:07:03
		 */
		public interface ComValue {
			// 查询返回
			/** 正常 */
			public static final String CHECK_SUCCESS = "fe970701b0000000";
			/** 缺证 */
			public static final String CHECK_LACK = "fe970701b0110000";
			/** 卡证 */
			public static final String CHECK_STUCK = "fe970701b0001100";
			/** 故障 */
			public static final String CHECK_MALFUNCTION = "fe970701b0000011";
			/** 缺证 卡证 */
			public static final String CHECK_LACK_STUCK = "fe970701b0111100";
			/** 缺证 故障 */
			public static final String CHECK_LACK_MALFUNCTION = "fe970701b0110011";
			/** 卡证 故障 */
			public static final String CHECK_STUCK_MALFUNCTION = "fe970701b0001111";
			/** 缺证 卡证 故障 */
			public static final String CHECK_LACK_STUCK_MALFUNCTION = "fe970701b0111111";
			
			// 送证返回
			/** 送证指令下达成功 */
			public static final String SEND_SUCCESS = "fe970501b00023";
			/** 送证指令下达失败 */
			public static final String SEND_FAIL = "fe970501b0ffdc";
			
			// 上证返回
			/** 上证成功 */
			public static final String PUT_IN_SUCCESS = "fe970501b088ab";
			
			// 打印过程返回
			/** 证本出 */
			public static final String DISGORGE = "fe970501b06645";
			
			// 拍摄提示
			/** 请求拍照 */
			public static final String PHOTO_TIP = "fe970501b01132";
		}
		
}
