package com.whminwei.util;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;

import javax.print.CancelablePrintJob;
import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.PrintException;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.Copies;
import javax.print.attribute.standard.JobName;
import javax.print.attribute.standard.OrientationRequested;
import javax.print.event.PrintJobEvent;
import javax.print.event.PrintJobListener;
import javax.swing.ImageIcon;

import com.whminwei.constant.Constant;
import com.whminwei.dto.TextStruct;

public class PrintFactory implements Printable {

	private TextStruct[] words;

	public void printText(String printerName, TextStruct[] words) {
		this.words = words;
		DocFlavor flavor = DocFlavor.SERVICE_FORMATTED.PRINTABLE;
		PrintRequestAttributeSet aset = new HashPrintRequestAttributeSet();
		aset.add(new Copies(1));// 打印页数
		aset.add(OrientationRequested.LANDSCAPE);// 横向打印还是纵向打印
		aset.add(new JobName(Constant.DeviceInfo.DEVICE_NAME, null));

		/* 找到可以处理请求的打印服务 */
		PrintService[] services = PrintServiceLookup.lookupPrintServices(flavor, aset);// 获取打印机列表
		for (PrintService service : services) {
			System.out.println(service.getName());
			if (service.getName().equals(printerName)) {// 选择特定的打印机
				System.out.println("准备打印……");
				CancelablePrintJob pj = (CancelablePrintJob) service.createPrintJob();
				Doc doc = new SimpleDoc(this, flavor, null);
				try {
					pj.addPrintJobListener(new PrintJobListener() {
						@Override
						public void printDataTransferCompleted(PrintJobEvent arg0) {// 回调函数
							System.out.println("01传输完成。" + arg0);
						}

						@Override
						public void printJobCanceled(PrintJobEvent arg0) {// 回调函数
							System.out.println("02打印取消。" + arg0);
						}

						@Override
						public void printJobCompleted(PrintJobEvent arg0) {// 回调函数
							System.out.println("03打印完成。" + arg0);
						}

						@Override
						public void printJobFailed(PrintJobEvent arg0) {// 回调函数
							try {
								pj.cancel();
							} catch (PrintException e) {
								e.printStackTrace();
							}
							System.out.println("04打印失败。/n/" + arg0);
						}

						@Override
						public void printJobNoMoreEvents(PrintJobEvent arg0) {// 回调函数
							System.out.println("05打印没有更多的事件需要执行。" + arg0);
						}

						@Override
						public void printJobRequiresAttention(PrintJobEvent arg0) {// 回调函数
							System.out.println("06缺纸或是缺墨等情况。" + arg0);
						}

					});
					pj.print(doc, aset);
				} catch (PrintException e) {
					System.out.println(e.getMessage());
					e.printStackTrace();
				}
				break;
			}
		}
	}

	@Override
	public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
		if (pageIndex == 0) {
			Graphics2D g2d = (Graphics2D) graphics;
			g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());
			g2d.setColor(Color.black);
			for (TextStruct word : this.words) {
				String str = word.text;
				Float y = word.y;
				g2d.setFont(new Font(word.fontFamily, word.fontWeight, word.fontSize));
				while (true) {
					String tmpstr;
					int len = str.length();
					int width = g2d.getFontMetrics().stringWidth(str);
					if (width < word.maxWidth) {
						g2d.drawString(str, word.x, y);
						break;
					}
					// 自动换行
					do {
						len--;
						tmpstr = str.substring(0, len);
						width = g2d.getFontMetrics().stringWidth(tmpstr);
					} while (width > word.maxWidth);
					// 获取二维码图片
					try {
						Component c = null;
						// 获取动态生成的二维码图片
						Image src = Toolkit.getDefaultToolkit().getImage(Constant.QRCode.QRCODE_PATH);
						// 加载印章图片
						// Image src1 =
						// Toolkit.getDefaultToolkit().getImage(Constant.QRCode.YINZHANG_PATH);
						
						ImageIcon ii = new ImageIcon(Constant.QRCode.YINZHANG_PATH);
						Image i = ii.getImage();
						System.out.println("读取图片宽=："+i.getWidth(c)+"，高="+i.getHeight(c));
						Image resizedImage = i.getScaledInstance(100,100,Image.SCALE_SMOOTH);  
						Image temp = new ImageIcon(resizedImage).getImage();  
						BufferedImage bufferedImage = new BufferedImage(temp.getWidth(null),  
					                temp.getHeight(null), BufferedImage.TYPE_4BYTE_ABGR);  
						// Soften.  
				        System.out.println("处理后的图片宽="+bufferedImage.getWidth()+"，高="+bufferedImage.getHeight());
						
					/*	
						File srcfile = new File(Constant.QRCode.YINZHANG_PATH);
						BufferedImage im = ImageIO.read(srcfile);
						 原始图像的宽度和高度  
						int w = im.getWidth();
						int h = im.getHeight(); // 压缩计算 
						float resizeTimes = 0.3f; 这个参数是要转化成的倍数,如果是1就是转化成1倍
						 调整后的图片的宽度和高度 
						int toWidth = (int) (w * resizeTimes);
						int toHeight = (int) (h * resizeTimes);
						 新生成结果图片 

						BufferedImage  result = new BufferedImage(toWidth, toHeight,BufferedImage.TYPE_4BYTE_ABGR_PRE);
						result.getGraphics().drawImage(
								im.getScaledInstance(toWidth, toHeight,
										java.awt.Image.SCALE_SMOOTH), 0, 0, null);
					 */
						
						
						// File srcFile = new File(Constant.QRCode.YINZHANG_PATH);
						// BufferedImage bufImg = ImageIO.read(srcFile); //读取图片
						// int w = 111;
						// int h = 111;
						// Image Itemp = bufImg.getScaledInstance(w, h,
						// bufImg.SCALE_SMOOTH);//设置缩放目标图片模板
						// double wr=w*1.0/bufImg.getWidth(); //获取缩放比例
						// double hr=h*1.0 / bufImg.getHeight();
						// AffineTransformOp ato = new
						// AffineTransformOp(AffineTransform.getScaleInstance(wr, hr), null);
						// Itemp = ato.filter(bufImg, null);
						// g2d.drawImage(Itemp, 165, 280, c);

						// 将二维码 设置到打印区域 x ,y 坐标
						g2d.drawImage(src, 10, 315, c);
						// 将印章 设置到打印区

						// src1.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
						g2d.drawImage(bufferedImage, 165, 280, c);

					} catch (Exception e) {
						System.out.println(e.getMessage());
					}
					g2d.drawString(tmpstr, word.x, y);
					str = str.substring(len);
					y += word.fontSize;
				}
			}
			return Printable.PAGE_EXISTS;
		}
		return Printable.NO_SUCH_PAGE;
	}

}
