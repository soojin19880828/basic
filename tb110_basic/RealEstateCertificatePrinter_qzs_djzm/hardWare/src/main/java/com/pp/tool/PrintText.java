package com.pp.tool;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.io.IOException;
import java.util.List;

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

public class PrintText implements Printable {

	private List<TextStruct> words;

	public void PrintTest(String printername, List<TextStruct> words) {
		this.words = words;
		DocFlavor flavor = DocFlavor.SERVICE_FORMATTED.PRINTABLE;
		PrintRequestAttributeSet aset = new HashPrintRequestAttributeSet();
		aset.add(new Copies(1));// 打印页数
		aset.add(OrientationRequested.LANDSCAPE);// 横向打印还是纵向打印
		aset.add(new JobName("沃航科技自助打印系统", null));

		/* 找到可以处理请求的打印服务 */
		PrintService[] services = PrintServiceLookup.lookupPrintServices(flavor, aset);// 获取打印机列表
		for (PrintService service : services){
			if (service.getName().equals(printername)){// 选择特定的打印机
				CancelablePrintJob pj = (CancelablePrintJob) service.createPrintJob();
				Doc doc = new SimpleDoc(this, flavor, null);
				try {
						pj.addPrintJobListener(new PrintJobListener() {

						@Override
						public void printDataTransferCompleted(PrintJobEvent arg0) {// 回调函数
							// FIXME Auto-generated method stub
							System.out.println("01传输完成。" + arg0);
						}

						@Override
						public void printJobCanceled(PrintJobEvent arg0) {// 回调函数
							// TODO Auto-generated method stub
							System.out.println("02打印取消。" + arg0);
						}

						@Override
						public void printJobCompleted(PrintJobEvent arg0) {// 回调函数
							System.out.println("03打印完成。" + arg0);
						}

						@Override
						public void printJobFailed(PrintJobEvent arg0) {// 回调函数
							// TODO Auto-generated method stub
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
					// TODO Auto-generated catch block
					System.out.println(e.getMessage());
					e.printStackTrace();
				}
				break;
			}
		}
	}

	/**
	 * @author Lq
	 * @功能 使用外部打印机函数
	 * @时间 2017/10/31
	 * @param context
	 * @param PrinterName
	 */
	public void outPrint() {
		try {
			Runtime.getRuntime().exec("java -jar F:\\runprinter.jar ");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {

		if (pageIndex == 0){
			Graphics2D g2d = (Graphics2D) graphics;
			g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());
			g2d.setColor(Color.black);
			
						// 二维码信息打印 登记证明编号，日期 ，不动产证号
						try {
							//Thread.sleep(3000);
							Component c = null;
							// 获取动态生成的二维码图片
							Image src = Toolkit.getDefaultToolkit().getImage("D:\\qrcod.png");
							// 将二维码 设置到打印区域 x ,y 坐标
							g2d.drawImage(src, 5, 250, c);
						}catch(Exception e){
							System.out.println(e.getMessage());
						}
			
			for (TextStruct word : this.words) {
				String str = word.text;
				// System.out.println("打印内容为:" + str);
				float positionY = word.y;
				g2d.setFont(new Font("宋体", Font.PLAIN, word.fontsize));
				while (true) {
					String tmpstr;
					int len = str.length();
					int width = g2d.getFontMetrics().stringWidth(str);
					if (width < word.maxwidth){
						g2d.drawString(str, word.x, positionY);
						break;
					}
					do {
						len--;
						tmpstr = str.substring(0, len);
						width = g2d.getFontMetrics().stringWidth(tmpstr);
					} while (width > word.maxwidth);

					
					g2d.drawString(tmpstr, word.x, positionY);
					str = str.substring(len);
					positionY += word.fontsize;
				}
			}
			
			
			return Printable.PAGE_EXISTS;
		} else {
			return Printable.NO_SUCH_PAGE;
		}
	}

}