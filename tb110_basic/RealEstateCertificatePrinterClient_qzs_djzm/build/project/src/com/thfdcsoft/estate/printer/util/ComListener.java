package com.thfdcsoft.estate.printer.util;

import com.thfdcsoft.estate.printer.constant.Constant;
import com.thfdcsoft.estate.printer.util.serialException.ReadDataFromSerialPortFailure;
import com.thfdcsoft.estate.printer.util.serialException.SerialPortInputStreamCloseFailure;
import com.thfdcsoft.estate.printer.view.script.ClientScript;

import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

/**
 * 监听端口 获取数据
 * @author weason
 *
 */
public class ComListener implements SerialPortEventListener {

	private static final Logger logger = LoggerFactory.getLogger(ComListener.class);

	private String[] commandReturn = { 
			"fe970701b0000000", // 正常
			"fe970701b000000021", // 正常
			"fe970701b0110000", // 缺证
			"fe970701b0001100", // 卡证
			"fe970701b0000011", // 故障
			"fe970701b0111100", // 缺证  卡证
			"fe970701b0110011", // 缺证  故障
			"fe970701b0001111", // 卡证  故障
			"fe970701b0111111", // 缺证 卡证 故障
			"fe970501b00023", // 送证指令下达成功
			"fe970501b0ffdc", // 送证指令下达失败
			"fe970501b088ab", // 上证成功反馈数据
			"fe970501b06645", // 证本出反馈数据
			"fe970501b01132" // 请求拍照（登记证明）
			
	};
	private SerialPort comNum;

	private String name;

	public ComListener(SerialPort com,String comName) {
		super();
		comNum = com;
		name = comName;
	}

	@Override
	public void serialEvent(SerialPortEvent arg0) {

		switch (arg0.getEventType()) {

		case SerialPortEvent.BI: // 10 通讯中断
			break;

		case SerialPortEvent.OE: // 7 溢位（溢出）错误

		case SerialPortEvent.FE: // 9 帧错误

		case SerialPortEvent.PE: // 8 奇偶校验错误

		case SerialPortEvent.CD: // 6 载波检测

		case SerialPortEvent.CTS: // 3 清除待发送数据

		case SerialPortEvent.DSR: // 4 待发送数据准备好了

		case SerialPortEvent.RI: // 5 振铃指示

		case SerialPortEvent.OUTPUT_BUFFER_EMPTY: // 2 输出缓冲区已清空
			break;

		case SerialPortEvent.DATA_AVAILABLE: // 1 串口存在可用数据

			byte[] data = null;
			try {
				if (comNum == null) {
					logger.info("【serialEvent】串口对象为空！监听失败！");
				} else {
					data = SerialTool.readFromPort(comNum); // 读取数据，存入字节数组
					if (data == null || data.length < 1) { // 检查数据是否读取正确
						logger.info("读取数据过程中未获取到有效数据！请检查设备或程序！");
					} else {
						// 登记证明盖章机
						if(Constant.COM.DJZM_GAI_ZHANG_JI.equals(name)) {
							ClientScript.gaiZhangJiComStr = SerialTool.bytesToHexString(data);
							ClientScript.gaiZhangJiComState = true;
						// 权证书盖章机
						}else if(Constant.COM.QZS_GAI_ZHANG_JI.equals(name)) {
							ClientScript.gaiZhangJiComStr = SerialTool.bytesToHexString(data);
							ClientScript.gaiZhangJiComState = true;
						}else {
							// 权证书翻页机
							if(Arrays.asList(commandReturn).contains(SerialTool.bytesToHexString(data))) {
								ClientScript.pageTurningComStr = SerialTool.bytesToHexString(data);
							}
							ClientScript.pageTurningComState = true;
						}
						for (byte b : data) {
							logger.info("{}", b);
						}
						logger.info("串口{}——获取到数据{}",comNum, SerialTool.bytesToHexString(data));
					}

				}
			} catch (ReadDataFromSerialPortFailure | SerialPortInputStreamCloseFailure e) {
				e.printStackTrace();
			}

			break;

		}

	}
}
