package com.thfdcsoft.hardware.factory;

import java.io.File;
import java.io.IOException;

public class FileFactory {

	/**
	 * 创建文件夹
	 * 
	 * @param path
	 */
	public static File buildDirs(String path) {
		File file = new File(path);
		if (!file.exists()) {
			if (!file.getParentFile().exists()) {
				buildDirs(file.getParent());
			}
			file.mkdir();
		}
		return file;
	}

	/**
	 * 创建文件
	 * 
	 * @param path
	 * @return
	 */
	public static File buildFile(String path) {
		File file = new File(path);
		if (!file.exists()) {
			if (!file.getParentFile().exists()) {
				buildDirs(file.getParent());
			}
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return file;
	}

	/**
	 * 清空文件夹
	 * 
	 * @param path
	 */
	public static void deleteDirs(String path) {
		File file = new File(path);
		if (file.exists()) {
			String[] content = file.list();
			for (String name : content) {
				File temp = new File(path, name);
				if (temp.isDirectory()) {
					deleteDirs(temp.getAbsolutePath());
				}
				temp.delete();
			}
		}
	}
}
