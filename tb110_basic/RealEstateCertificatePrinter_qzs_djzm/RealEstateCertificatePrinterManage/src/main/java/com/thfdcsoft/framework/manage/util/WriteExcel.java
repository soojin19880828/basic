package com.thfdcsoft.framework.manage.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.thfdcsoft.framework.manage.entity.PrintRecord;

public class WriteExcel {

	public <T> void ExportExcel(List<T> records,File file) {
		// TODO Auto-generated constructor stub
		HSSFWorkbook excel = new HSSFWorkbook();
		  HSSFSheet sheet = excel.createSheet("records");
		  HSSFRow firstRow = sheet.createRow(0);
		  HSSFCell cells[] = new HSSFCell[5];
		  String[] titles = new String[] { "业务流水号", "不动产权证号", "印刷序列号", "打印时间","回传不动产" };
		  for (int i = 0; i < 5; i++) {

		   cells[0] = firstRow.createCell(i);

		   cells[0].setCellValue(titles[i]);

		  }

		  for (int i = 0; i < records.size(); i++) {

		   HSSFRow row = sheet.createRow(i + 1);

		   PrintRecord record = (PrintRecord) records.get(i);

		   HSSFCell cell = row.createCell(0);

		   cell.setCellValue(record.getBizNumber());

		   cell = row.createCell(1);

		   cell.setCellValue(record.getCertNumber());

		   cell = row.createCell(2);

		   cell.setCellValue(record.getSeqNumber());

		   cell = row.createCell(3);

		   cell.setCellValue(record.getPrintTime());

		   cell = row.createCell(4);

		   cell.setCellValue(record.getTranStatusBiz());

		  }
		        OutputStream out = null;

		        try {

		            out = new FileOutputStream(file);

		            excel.write(out);

		            out.close();

		        } catch (FileNotFoundException e) {

		            e.printStackTrace();

		        } catch (IOException e) {

		            e.printStackTrace();

		        }
	}
	
	public void downloadExcel(HttpServletResponse response,String path) throws IOException{
		
		//1、设置响应的头文件，会自动识别文件内容
		response.setContentType("multipart/form-data");
		
		//2、设置Content-Disposition
		response.setHeader("Content-Disposition", "attachment; filename=test.xls");
		
		OutputStream out = null;
		InputStream in = null;
		try {
			//3、输出流
			out = response.getOutputStream();
			
			//4、获取服务端生成的excel文件，这里的path等于4.8中的path
			in = new FileInputStream(new File(path));
			
			//5、输出文件
			int b;
			while((b=in.read())!=-1){
			    out.write(b);
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		finally{
			in.close();
			out.close();
			
		}
	}

}
