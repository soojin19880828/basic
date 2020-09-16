package com.pp.tool;


/**
 * 
 * @author LQ
 * @功能 获取配置信息
 *
 */
public class ConfigInfor {

	private static String ServerUrl; // 服务器端地址
	private static String PrinterAddress; // 打印机地址
	private static String HardwareAddress; // 硬件dll调用地址
	private static int CardPort; // 身份证读取器端口号
	private static int CardReadTimes; // 身份证循环读取次数
	private static int StampingMachinePort; // 盖章机调用端口号
	private static int LEDLempPort; // 身份证放置提示灯端口号
	private static int SealMachineWriteTime=30; // 盖章机等待时间

	// 佛山市禅城区-ZJQ
	private static String User; // 身份验证用户名
	private static String Password; // 身份验证密码

	private static String IsDebug; // 是否为测试模式
	private static String DebugIdCard; // 测试身份证号

	public static int getLEDLempPort() {
		return LEDLempPort;
	}

	public static int getSealMachineWriteTime() {
		return SealMachineWriteTime;
	}

	public static String getServerUrl() {
		return ServerUrl;
	}

	public static String getPrinterAddress() {
		return PrinterAddress;
	}

	public static String getHardwareAddress() {
		return HardwareAddress;
	}

	public static int getCardPort() {
		return CardPort;
	}

	public static int getCardReadTimes() {
		return CardReadTimes;
	}

	public static int getStampingMachinePort() {
		return StampingMachinePort;
	}

	public static String getUser() {
		return User;
	}

	public static void setUser(String user) {
		User = user;
	}

	public static String getPassword() {
		return Password;
	}

	public static void setPassword(String password) {
		Password = password;
	}

	public static String getIsDebug() {
		return IsDebug;
	}

	public static void setIsDebug(String isDebug) {
		IsDebug = isDebug;
	}

	public static String getDebugIdCard() {
		return DebugIdCard;
	}

	public static void setDebugIdCard(String debugIdCard) {
		DebugIdCard = debugIdCard;
	}

//	@SuppressWarnings("static-access")
//	public String SetConfig(String FileUrl) {
//		ReadFile readFile = new ReadFile();
//		HashMap<String, String> resMap = readFile.ReadConfig(FileUrl);
//		if (resMap == null) {
//			return "请检查配置文件是否存在";
//		}
//		try {
//			this.ServerUrl = resMap.get("ServerUrl");
//			System.out.println("ServerUrl : "+ ServerUrl);
//			this.PrinterAddress = resMap.get("PrinterAddress");
//			System.out.println("PrinterAddress : "+ PrinterAddress);
//			this.HardwareAddress = resMap.get("HardwareAddress");
//			System.out.println("HardwareAddress : "+ HardwareAddress);
//			this.CardPort = Integer.parseInt(resMap.get("CardPort"));
//			System.out.println("CardPort : "+ CardPort);
//			this.CardReadTimes = Integer.parseInt(resMap.get("CardReadTimes"));
//			System.out.println("CardReadTimes : "+ CardReadTimes);
//			this.StampingMachinePort = Integer.parseInt(resMap.get("StampingMachinePort"));
//			System.out.println("StampingMachinePort : "+ StampingMachinePort);
//			this.LEDLempPort = Integer.parseInt(resMap.get("LEDLempPort"));
//			System.out.println("LEDLempPort : "+ LEDLempPort);
//			this.SealMachineWriteTime = Integer.parseInt(resMap.get("SealMachineWriteTime"));
//			System.out.println("SealMachineWriteTime : "+ SealMachineWriteTime);
//			this.User = resMap.get("User");
//			System.out.println("User : "+ User);
//			this.Password = resMap.get("Password");
//			System.out.println("Password : "+ Password);
//			this.setIsDebug(resMap.get("IsDebug"));
//			System.out.println("IsDebug : "+ IsDebug);
//			this.setDebugIdCard(resMap.get("DebugIdCard"));
//			System.out.println("DebugIdCard : "+ DebugIdCard);
//
//		} catch (Exception e) {
//
//			return "请检查配置内容格式是否正确";
//		}
//		return "success";
//	}

}
