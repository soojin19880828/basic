package com.pp.tool;

import com.thfdcsoft.hardware.factory.PropFactory;

public class NativeFunc {

	static {
//		String NativeFuncdllpath = "D:\\jnitext\\WEB-INF\\dll\\NativeFunc.dll";
//		String HardwareAddress ="D:\\dll"; //44ConfigInfor.getHardwareAddress();
//		String NativeFuncdllpath = HardwareAddress+"\\NativeFunc.dll";
		String NativeFuncdllpath=PropFactory.getPath()+"dll/NativeFunc.dll";
		System.load(NativeFuncdllpath);
		System.out.println("java.library.path============"+System.getProperty("java.library.path"));
//		System.loadLibrary(NativeFuncdllpath);
		
	}

	public static native int SetDllPath(String dllpath);//初始化并且设置dll文件路径

	/* LED灯控制逻辑 */
    public static native int LEDOpenDevice(String pstrCom, int[] nHandle, int nFage);//打开设备
    public static native int LEDCloseDevice(int nHandle);//关闭设备
    public static native int LEDGetStatus(int nHandle);//取得设备状态
    public static native int LEDSetLightParam(int nHandle,int ledNo, int ledMode, int timeSpan);//设置开关参数

    /* 盖章机控制逻辑 */
    public static native int STAMPOpenDevice(String pstrCom, int[] nHandle, int nFage);//打开设备
    public static native int STAMPCloseDevice(int nHandle);//关闭设备
    public static native int STAMPReset(int nHandle);//设备复位
    public static native int STAMPGetStatus (int nHandle);//获取设备状态
    public static native int STAMPGetPaperCount(int nHandle);//查询走纸张数
    public static native int STAMPSetStampPos(int nHandle, int nStampNum, int nLeftPosition, int nRightPosition);//设置印章位置
    public static native int STAMPSetStampPosEX(int nHandle, int nStampNum, int nLeftPosition, int nRightPosition);//设置印章位置(A4)
    public static native int STAMPSetWorkCount(int nHandle, int nsheets);//设置印章总页数
    public static native int STAMPSetWorksheet(int nHandle, int nNum, int nLR, int nMode);//设置每页盖章模式
    public static native int STAMPStartUp(int nHandle);//盖章启动
    public static native int STAMPStartStop(int nHandle);//盖章停止

    /* 身份证读取设备，读完的信息放在了这个路径下C:\Users\Administrator\AppData\Local\Temp\chinaidcard*/
    public static native int Cvrinitcomm(int port);//初始化串口
    public static native int Cvrclosecomm();//关闭串口
    public static native int Cvrauthenticate();//识别卡
    public static native int Cvrreadcontent(int active);//读卡信息

    /* LED灯操作  */
    public static native int OpenDevice(int nPort, long nComBaud);//打开串口
    public static native int CloseDevice();//关闭串口
    public static native int CtrlLed(int nLed, int nStatus);//控制灯
    public static native int AllLed(int nStatus);//控制所有灯
    public static native int GetStatus();//读设备信息
    public static native int GetVer(char[] szVersion);//读设备信息

    /* 测试用本地方法 */
    public static native int CheckDll();
    
}
