package zkteco.id100com.jni;

import javax.swing.JOptionPane;

import zkteco.id100com.jni.*;

public class testDemo {

	public static void main(String[] args) {
		JOptionPane.showMessageDialog(null, "等待调试!", "系统信息", JOptionPane.INFORMATION_MESSAGE);
		// ----读身份证测试
		String path = id100sdk.getPath();
		System.out.println("初始化 端口号=" + id100sdk.InitCommExt());
		if (id100sdk.InitCommExt() <= 0) {
			System.out.printf("InitCommExt fail\n");
			return;
		}
		System.out.printf("InitCommExt succ\n");
//			try {
//				Thread.sleep(1000);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
			int i = id100sdk.Authenticate();
			if (i != 1) {
				System.out.printf("Authenticate fail\n");
				return;
			}
			System.out.printf("Authenticate succ\n");
			// 循环读取
			long tickStart = System.currentTimeMillis();
			int ret = 0;
			int r = id100sdk.ReadContent(1);
//			while (r != 1 && System.currentTimeMillis() < 2000) {
//				try {
//					Thread.sleep(50);
//				} catch (InterruptedException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}
			if (r != 1) {
				System.out.printf("ReadContent fail\n");
				return;
			}

			System.out.printf("ReadContent succ\n");
			System.out.printf("输出文件路径�?" + path + "\n");
			System.out.printf("GetBmpPhotoExt ret=" + id100sdk.GetBmpPhotoExt());
			System.out.printf("\n");
			System.out.printf("姓名=%s\n", id100sdk.getName());
			System.out.printf("民族=%s\n", id100sdk.getNation());
			System.out.printf("性别=%s\n", id100sdk.getSex());
			System.out.printf("身份证号=%s\n", id100sdk.getIDNum());
			System.out.printf("出生日期=%s\n", id100sdk.getBirthdate());
			System.out.printf("常住地址=%s\n", id100sdk.getAddress());
			System.out.printf("签发机关=%s\n", id100sdk.getIssue());
			System.out.printf("有效�?=%s-%s\n", id100sdk.getEffectedDate(), id100sdk.getExpireDate());
			// System.out.printf("bmp base64头像=%s\n", id100sdk.getBMPPhotoBase64());
			 System.out.printf("jpg base64头像=%s\n", id100sdk.getJPGPhotoBase64());


		// 读身份证测试结束
		/*
		 * //IC卡测�? int nPort = 8; //串口�? System.out.printf("发卡器版�?:"+
		 * id100sdk.ICGetDevVersion(nPort));
		 * 
		 * int snr[] = new int[1]; if (1 == id100sdk.ICGetICSnr(nPort, snr)) {
		 * System.out.printf("读取IC卡卡号成�?,卡号�?"+ snr[0]); } else {
		 * System.out.printf("读取IC卡卡号失�?"); } System.out.printf("身份证物理卡�?"+
		 * id100sdk.ICGetIDSnr(nPort)); System.out.printf("读IC卡数据："+
		 * id100sdk.ICReadData(nPort, 0, 2, 0, "FFFFFFFFFFFF", snr)); if(1 ==
		 * id100sdk.ICWriteData(nPort, 0, 2, 0, "FFFFFFFFFFFF",
		 * "EEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE", snr)) { System.out.printf("写卡成功"); } else
		 * { System.out.printf("写卡失败"); }
		 * 
		 * //HID 语音测试 id100sdk.HIDVoice(0);
		 */
	}
}