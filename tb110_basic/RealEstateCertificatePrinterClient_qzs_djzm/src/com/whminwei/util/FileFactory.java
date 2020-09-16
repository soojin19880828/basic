package com.whminwei.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.channels.FileChannel;


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
	
	/**
	 * 文件复制
	 * @param source 源
	 * @param dest
	 * @return
	 * 2019年4月26日 14点04分
	 * 田伟
	 */
	@SuppressWarnings({ "resource", "finally" })
	public static boolean copyFileUsingFileChannels(File source, File dest) {   
		System.out.println("开始保存图片");
        FileChannel inputChannel = null;    
        FileChannel outputChannel = null;
        long bytesNum = 0;
        try {
        	 try {
        	        inputChannel = new FileInputStream(source).getChannel();
        	        outputChannel = new FileOutputStream(dest).getChannel();
        	        bytesNum = outputChannel.transferFrom(inputChannel, 0, inputChannel.size());
        	    } finally {
        	    	if(inputChannel != null) {
        	    		 inputChannel.close();
        	    	}
        	    	if(outputChannel != null) {
        	    		 outputChannel.close();
        	    	}
        	    	if(bytesNum > 0) {
        	    		return true;
        	    	}
        	    }
		} catch (Exception e) {
			e.printStackTrace();
		}
        return false;
   
}
	/**
	 * 向txt文件中写入数据
	 * 
	 * @param file
	 * @param content
	 */
	public static void writeTXTFile(File file, String content) {
		try {
			BufferedWriter out = new BufferedWriter(new FileWriter(file, true));
			out.write(DateFactory.getCurrentDateString("yyyy-MM-dd-HH:mm:ss")
					+ "\r\n" + content + "\r\n");
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 *@description: 如果扫描件有未清空的,移动到备份文件夹
	 *@param sourcePath 源文件路径
	 *@param targetPath 目标文件路径
	 *@author: 10118
	 *@date: 2020年8月20日下午4:38:29
	 */
	public static void removeDirs(String sourcePath, String targetPath) {
		File file = new File(sourcePath);
		if (file.exists()) {
			String[] content = file.list();
			for (String name : content) {
				File temp = new File(sourcePath, name);
				if (temp.isDirectory()) {
					//如果是文件夹则删除文件夹
					deleteDirs(temp.getAbsolutePath());
					temp.delete();
				}else{
					//若是文件则移动到目标文件
					if(!new File(targetPath).exists()){
						buildDirs(targetPath);
					}
					temp.renameTo(new File(targetPath + "\\" + temp.getName()));
				}
			}
		}
	}
	
}
