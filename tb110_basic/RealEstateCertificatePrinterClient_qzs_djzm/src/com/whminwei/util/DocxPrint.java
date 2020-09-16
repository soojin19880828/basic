package com.whminwei.util;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.data.PictureRenderData;
import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.ComThread;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;
import com.whminwei.constant.Constant;
import com.whminwei.dto.DocxFile;
import com.whminwei.dto.RealEstateInfo;
import com.whminwei.view.pages.TransitPage;
import com.whminwei.view.plugin.CountDownPlug;

public class DocxPrint {

	public void print() {
		final Long value = 2000L;
		try {
			Thread.sleep(value);
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		}
		comfirmSingleProcess("WINWORD");
		String path = Constant.QRCode.DOCFILEOUT;
		System.out.println("开始打印=" + path);
		System.out.println("00000");
		ComThread.InitSTA();
		System.out.println("11111");
		ActiveXComponent word = new ActiveXComponent("Word.Application");
		System.out.println("22222");
		Dispatch doc = null;
		Dispatch.put(word, "Visible", new Variant(false));
		System.out.println("33333");
		Dispatch docs = word.getProperty("Documents").toDispatch();
		System.out.println("44444");
		doc = Dispatch.call(docs, "Open", path).toDispatch();
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

	public boolean makeFile(RealEstateInfo printData) {
		DocxFile docxFile = new DocxFile();
		docxFile.setZongditu(new PictureRenderData(400, 200, Constant.FileDir.ZONGDITU));
		docxFile.setFenbutu(new PictureRenderData(400, 200, Constant.FileDir.FENBUTU));
		docxFile.setPro(printData.getCertNumber().substring(0, 1));
		docxFile.setYear(printData.getCertNumber().substring(2, 6));
		docxFile.setCity(printData.getCertNumber().substring(7, 10));
		docxFile.setNum(printData.getCertNumber().substring(16, printData.getCertNumber().length() - 1));
		docxFile.setObligee(printData.getObligee());
		docxFile.setCo_ownershipCircumstance(printData.getCo_ownershipCircumstance());
		docxFile.setLocated(printData.getLocated());
		docxFile.setUnitNumber(printData.getUnitNumber());
		docxFile.setRightNature(printData.getRightNature());
		docxFile.setRightTypes(printData.getRightTypes());
		docxFile.setApplication(printData.getApplication());
		docxFile.setArea(printData.getArea());
		docxFile.setServiceLife(printData.getServiceLife());
		docxFile.setOther(printData.getOther());
		docxFile.setExcursus(printData.getExcursus());
		Date date = DateFactory.getDate(printData.getBookTime());
		if (date == null) {
			TransitPage.setTransitText("解析响应数据中的登簿时间时出现异常，请联系工作人员。");
			CountDownPlug.startCountDown(10, 1);
			 return false;
		}
		docxFile.setBookYear(DateFactory.getYearOfDate(date));
		docxFile.setBookMonth(DateFactory.getMonthOfDate(date));
		docxFile.setBookDay(DateFactory.getDayOfDate(date));

		final XWPFTemplate render = XWPFTemplate.compile(Constant.QRCode.MOBAN_PATH).render((Object) docxFile);
		// 检查生成文件 文件夹是否存在文件 存在就先删除
		File check = new File(Constant.QRCode.DOCFILEOUT);
		if (check.exists()) {
			check.delete();
		}

		try {
			FileOutputStream out = new FileOutputStream(check);
			render.write(out);
			out.flush();
			out.close();
			render.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		 return false;
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
