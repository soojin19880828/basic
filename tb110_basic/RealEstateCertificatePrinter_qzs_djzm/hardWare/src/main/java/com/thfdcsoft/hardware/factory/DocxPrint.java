package com.thfdcsoft.hardware.factory;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.ComThread;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;

public class DocxPrint {

	public void print(String docPath,String printerName) {
		final Long value = 2000L;
		try {
			Thread.sleep(value);
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		}
		comfirmSingleProcess("WINWORD");
		String path = docPath;
		System.out.println("开始打印=" + path);
		System.out.println("00000");
		ComThread.InitSTA();
		System.out.println("11111");
		ActiveXComponent word = new ActiveXComponent("Word.Application");
		System.out.println("22222");
		Dispatch doc = null;
		// 使用默认打印机打印
//		Dispatch.put(word, "Visible", new Variant(false));
		// 测试选择针式打印机进行打印
		Dispatch.put(word, "Visible", new Variant(true));
		System.out.println("33333");
		Dispatch docs = word.getProperty("Documents").toDispatch();
		System.out.println("44444");
		doc = Dispatch.call(docs, "Open", path).toDispatch();
		// 指定打印机名称
		word.setProperty("ActivePrinter", new Variant(printerName));
		System.out.println("55555");
		try {
			Dispatch.call(doc, "PrintOut");// 打印
			System.out.println("66666");
		} catch (Exception e) {
			System.out.println("77777");
			System.out.println("打印失败");
			e.printStackTrace();
		} finally {
			try {
				System.out.println("88888");
				if (doc != null) {
					Dispatch.call(doc, "Close", new Variant(0));
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
			// 释放资源
			ComThread.Release();
		}
	}

	/**
	 * 
	 * @param str
	 * @return
	 */
	public static String replaceBlank(String str) {
		String dest = "";
		if (str != null) {
			Pattern p = Pattern.compile("\\r");
			Matcher m = p.matcher(str);
			dest = m.replaceAll("");
		}
		return dest;
	}

	/**
	 * 查杀指定前缀的进程名
	 * @param s 名称
	 */
	public static void comfirmSingleProcess(final String s) {
		if (s == null) {
			throw new NullPointerException("The prefix is null,please check it!!");
		}
		BufferedReader bufferedReader = null;
		BufferedReader bufferedReader2 = null;
		try {
			final Process start = new ProcessBuilder(new String[] { "tasklist" }).start();
			bufferedReader = new BufferedReader(
					new InputStreamReader(new BufferedInputStream(start.getInputStream()), Charset.forName("GB2312")));
			bufferedReader2 = new BufferedReader(
					new InputStreamReader(new BufferedInputStream(start.getErrorStream())));
			final ArrayList<String> list = new ArrayList<String>();
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				list.add(line);
			}
			for (int i = 3; i < list.size(); ++i) {
				final String trim = list.get(i).substring(0, 25).trim();
				final String trim2 = list.get(i).substring(25, 35).trim();
				if (trim.startsWith(s)) {
					Runtime.getRuntime().exec("taskkill /F /PID " + trim2);
				}
			}
		} catch (IOException ex) {
			ex.printStackTrace();
			try {
				if (bufferedReader != null) {
					bufferedReader.close();
				}
			} catch (IOException ex2) {
				ex2.printStackTrace();
			}
			try {
				if (bufferedReader2 != null) {
					bufferedReader2.close();
				}
			} catch (IOException ex3) {
				ex3.printStackTrace();
			}
		} finally {
			try {
				if (bufferedReader != null) {
					bufferedReader.close();
				}
			} catch (IOException ex4) {
				ex4.printStackTrace();
			}
			try {
				if (bufferedReader2 != null) {
					bufferedReader2.close();
				}
			} catch (IOException ex5) {
				ex5.printStackTrace();
			}
		}
	}
}
