package com.thfdcsoft.framework.manage.dto.xian;

public class File {

	// 文件名
	private String fileName;

	// 文件流[Base64]
	private String file;

	public File() {

	}

	public File(BaseZB05File file) {
		if (file.getFileType().equals("02")) {
			this.setFileName("人物图像");
		} else if (file.getFileType().equals("03")) {
			this.setFileName("证明扫描图像");
		} else {
			this.setFileName(file.getFileName());
		}
		this.setFile(file.getFileData());
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}

}
