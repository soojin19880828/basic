package com.pp.tool;

import com.thfdcsoft.hardware.factory.PropFactory;

/**
 * @author LQ
 * @功能 盖章机操作
 * @时间 2017年10月19日16:25:55
 *
 */
public class SealMachine {
	
	static{
		String NativeFuncdllpath = PropFactory.getPath() + "dll";
		System.out.println(NativeFuncdllpath);
		NativeFunc.SetDllPath(NativeFuncdllpath);
	}

	/**
	 * @author MZW
	 * @功能 盖章操作 全流程
	 * @时间 2017年10月19日16:11:58
	 * @param port
	 *            硬件的端口号
	 * @param x
	 *            打印位置的X坐标
	 * @param y
	 *            打印位置的Y坐标
	 * @return
	 */
	int[] nHandle;

	public String TestStampPrinter(int port, int x, int y) {
		int res;
		int[] nHandle = new int[1];
		// 第一步 初始化串口
		res = NativeFunc.STAMPOpenDevice("COM" + port + ":9600:E:8:1", nHandle, 0);
		if (res != 0) {
			System.out.println("初始化设备失败,res = " + res);
			return "error2001";
		}
		System.out.println("初始化设备成功,nHandle = " + nHandle[0]);
		// 第二步 复位盖章机
		res = NativeFunc.STAMPReset(nHandle[0]);
		if (res != 0) {
			System.out.println("复位设备失败,res = " + res);
			res = NativeFunc.STAMPCloseDevice(nHandle[0]);
			if (res != 0) {
				System.out.println("关闭设备失败,res = " + res);
			}
			return "error2002";
		}
		System.out.println("复位设备成功!!!");
		// 第三步 检测盖章机状态
		res = NativeFunc.STAMPGetStatus(nHandle[0]);
		if (res != 0) {
			System.out.println("盖章机状态异常,可能存在卡纸等现象,res = " + res);
			res = NativeFunc.STAMPCloseDevice(nHandle[0]);
			if (res != 0) {
				System.out.println("关闭设备失败,res = " + res);
			}
			return "error2003";
		}
		System.out.println("盖章机状态正常,可以进行如下操作!!!");
		// 第四步 设置印章位置(A4纸)，这里我们只设置上面的情况即可，不需要设置下方的情况
		res = NativeFunc.STAMPSetStampPosEX(nHandle[0], 1, x, y);
		if (res != 0) {
			System.out.println("设置印章位置(A4纸)失败,res = " + res);
			res = NativeFunc.STAMPCloseDevice(nHandle[0]);
			if (res != 0) {
				System.out.println("关闭设备失败,res = " + res);
			}
			return "error2004";
		}
		System.out.println("设置印章位置(A4纸)成功,x = " + x + ",y = " + y);
		// 第五步 设置走纸张数为一张
		res = NativeFunc.STAMPSetWorkCount(nHandle[0], 1);
		if (res != 0) {
			System.out.println("设置走纸张数为一张失败,res = " + res);
			res = NativeFunc.STAMPCloseDevice(nHandle[0]);
			if (res != 0) {
				System.out.println("关闭设备失败,res = " + res);
			}
			return "error2005";
		}
		System.out.println("设置走纸张数为一张成功!!!");
		// 第六步 设置第一页的盖章模式为位置：左边，A4纸上部特定位置盖章
		res = NativeFunc.STAMPSetWorksheet(nHandle[0], 1, 0, 9);
		if (res != 0) {
			System.out.println("设置第一页的盖章模式为位置：左边，A4纸上部特定位置盖章失败,res = " + res);
			res = NativeFunc.STAMPCloseDevice(nHandle[0]);
			if (res != 0) {
				System.out.println("关闭设备失败,res = " + res);
			}
			return "error2006";
		}
		System.out.println("设置第一页的盖章模式为位置：左边，A4纸上部特定位置盖章成功!!!");
		// 第七步 启动盖章行为
		res = NativeFunc.STAMPStartUp(nHandle[0]);
		if (res != 0) {
			System.out.println("启动盖章行为失败,res = " + res);
			res = NativeFunc.STAMPCloseDevice(nHandle[0]);
			if (res != 0) {
				System.out.println("关闭设备失败,res = " + res);
			}
			return "error2007";
		}
		System.out.println("启动盖章行为成功!!!");
		// 第八步 等待盖章机结束盖章
		int timeout = 100;
		while (timeout != 0) {
			System.out.println("盖章等待超时倒计时:" + timeout + "s");
			res = NativeFunc.STAMPGetStatus(nHandle[0]);
			if (res == 0) {
				break;
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			timeout--;
		}
		if (timeout == 0) {// 等待了50s超时了
			System.out.println("盖章等待超时!!!");
			res = NativeFunc.STAMPCloseDevice(nHandle[0]);
			if (res != 0) {
				System.out.println("关闭设备失败,res = " + res);
			}
			return "error2007";
		}

		// 第九步 关闭设备
		res = NativeFunc.STAMPCloseDevice(nHandle[0]);
		if (res != 0) {
			System.out.println("关闭设备失败,res = " + res);
			return "error2008";
		}
		System.out.println("关闭设备成功!!!");
		return "success";
	}

	/**
	 * @author LQ
	 * @功能 启动盖章机
	 * @时间 2017年10月19日16:28:24
	 * @param port
	 * @param x
	 * @param y
	 * @return
	 */
	public String StartSeal(int port, int x, int y) {
		int res;
		this.nHandle = new int[1];
		// 第一步 初始化串口    gallop_horse
		res = NativeFunc.STAMPOpenDevice("COM"+ port + ":9600:E:8:1", this.nHandle, 0);
		if (res != 0){
			System.out.println("初始化设备失败,res = " + res);
			return "error2001";
		}
		System.out.println("初始化设备成功,this.nHandle = " + this.nHandle[0]);
		// 第二步 复位盖章机
		res = NativeFunc.STAMPReset(this.nHandle[0]);
		if (res != 0) {
			System.out.println("复位设备失败,res = " + res);
			res = NativeFunc.STAMPCloseDevice(this.nHandle[0]);
			if (res != 0) {
				System.out.println("关闭设备失败,res = " + res);
			}
			return "error2002";
		}
		System.out.println("复位设备成功!!!");
		// 第三步 检测盖章机状态
		res = NativeFunc.STAMPGetStatus(this.nHandle[0]);
		if (res != 0) {
			System.out.println("盖章机状态异常,可能存在卡纸等现象,res = " + res);
			res = NativeFunc.STAMPCloseDevice(this.nHandle[0]);
			if (res != 0) {
				System.out.println("关闭设备失败,res = " + res);
			}
			return "error2003";
		}
		System.out.println("盖章机状态正常,可以进行如下操作!!!");
		// 第四步 设置印章位置(A4纸)，这里我们只设置上面的情况即可，不需要设置下方的情况
		res = NativeFunc.STAMPSetStampPosEX(this.nHandle[0], 2, x, y);
		if (res != 0) {
			System.out.println("设置印章位置(A4纸)失败,res = " + res);
			res = NativeFunc.STAMPCloseDevice(this.nHandle[0]);
			if (res != 0) {
				System.out.println("关闭设备失败,res = " + res);
			}
			return "error2004";
		}
		System.out.println("设置印章位置(A4纸)成功,x = " + x + ",y = " + y);
		// 第五步 设置走纸张数为一张
		res = NativeFunc.STAMPSetWorkCount(this.nHandle[0], 1);
		if (res != 0) {
			System.out.println("设置走纸张数为一张失败,res = " + res);
			res = NativeFunc.STAMPCloseDevice(this.nHandle[0]);
			if (res != 0) {
				System.out.println("关闭设备失败,res = " + res);
			}
			return "error2005";
		}
		System.out.println("设置走纸张数为一张成功!!!");
		// 第六步 设置第一页的盖章模式为位置：左边，A4纸上部特定位置盖章
		res = NativeFunc.STAMPSetWorksheet(this.nHandle[0], 1, 0, 10);
		if (res != 0) {
			System.out.println("设置第一页的盖章模式为位置：左边，A4纸上部特定位置盖章失败,res = " + res);
			res = NativeFunc.STAMPCloseDevice(this.nHandle[0]);
			if (res != 0) {
				System.out.println("关闭设备失败,res = " + res);
			}
			return "error2006";
		}
		System.out.println("设置第一页的盖章模式为位置：左边，A4纸上部特定位置盖章成功!!!");
		// this.nHandle = nHandle;
		return "success";
	}

	/**
	 * @author LQ
	 * @功能 开始盖章
	 * @时间 2017年10月19日16:36:46
	 * @return
	 */
	public String Sealing() {
		int res;

		// 第七步 启动盖章行为
		res = NativeFunc.STAMPStartUp(this.nHandle[0]);
		if (res != 0) {
			System.out.println("启动盖章行为失败,res = " + res);
			res = NativeFunc.STAMPCloseDevice(this.nHandle[0]);
			if (res != 0) {
				System.out.println("关闭设备失败,res = " + res);
			}
			return "error2007";
		}
		System.out.println("启动盖章行为成功!!!");
		// 第八步 等待盖章机结束盖章
		int timeout = ConfigInfor.getSealMachineWriteTime();
		while (timeout != 0) {
			System.out.println("盖章等待超时倒计时:" + timeout + "s");
			System.out.println(this.nHandle[0]);
			res = NativeFunc.STAMPGetStatus(this.nHandle[0]);
			if (res == 0) {
				System.out.println(res);
				break;
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			timeout--;
		}
		if (timeout == 0) {// 等待了50s超时了
			System.out.println("盖章等待超时!!!");
			res = NativeFunc.STAMPCloseDevice(this.nHandle[0]);
			if (res != 0) {
				System.out.println("关闭设备失败,res = " + res);
			}
			return "error2007";
		}
		return "success";
	}

	/**
	 * @author LQ
	 * @功能 关闭盖章机
	 * @时间 2017年10月19日16:36:46
	 * @return
	 */
	public void closeSeal() {
		// 第九步 关闭设备
		int res = NativeFunc.STAMPCloseDevice(this.nHandle[0]);
		if (res != 0) {
			System.out.println("关闭设备失败,res = " + res);
			// return "error2008";
			return;
		}
		System.out.println("关闭设备成功!!!");
	}
}
