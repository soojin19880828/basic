package com.thfdcsoft.framework.business.dto;

import java.io.File;

/**
 * 登记证明打印信息
 * 
 * @author 张嘉琪
 *
 */
public class SubmitPrintReq {

	// 不动产单元号
		private String busiNumber;

		// 不动产权证书号     
		private String certNumber;
		
		// 识别工本号
		private String sequNumber;

		// 登记证明扫描件
		private String certScan;
		
		// 使用记录ID
		private String UsageId;
		
		// word文档保存路径
		private String destPath;
		
		//设备证书编号照片路径
		private String ocrPicPath;
		
		//日志文件
		private File logFile;

		// 打印模块ID
		private String terminalId;
		
		

		public File getLogFile() {
			return logFile;
		}

		public void setLogFile(File logFile) {
			this.logFile = logFile;
		}

		public String getDestPath() {
			return destPath;
		}

		public void setDestPath(String destPath) {
			this.destPath = destPath;
		}

		public String getOcrPicPath() {
			return ocrPicPath;
		}

		public void setOcrPicPath(String ocrPicPath) {
			this.ocrPicPath = ocrPicPath;
		}

		public String getBusiNumber() {
			return busiNumber;
		}

		public void setBusiNumber(String busiNumber) {
			this.busiNumber = busiNumber;
		}

		public String getCertNumber() {
			return certNumber;
		}

		public void setCertNumber(String certNumber) {
			this.certNumber = certNumber;
		}

		public String getSequNumber() {
			return sequNumber;
		}

		public void setSequNumber(String sequNumber) {
			this.sequNumber = sequNumber;
		}

		public String getCertScan() {
			return certScan;
		}

		public void setCertScan(String certScan) {
			this.certScan = certScan;
		}

		public String getUsageId() {
			return UsageId;
		}

		public void setUsageId(String usageId) {
			UsageId = usageId;
		}

		public String getTerminalId() {
			return terminalId;
		}

		public void setTerminalId(String terminalId) {
			this.terminalId = terminalId;
		}

		@Override
		public String toString() {
			return "SubmitPrintReq{" +
					"busiNumber='" + busiNumber + '\'' +
					", certNumber='" + certNumber + '\'' +
					", sequNumber='" + sequNumber + '\'' +
					", certScan='" + certScan + '\'' +
					", UsageId='" + UsageId + '\'' +
					", destPath='" + destPath + '\'' +
					", ocrPicPath='" + ocrPicPath + '\'' +
					", logFile=" + logFile +
					", terminalId='" + terminalId + '\'' +
					'}';
		}
}
