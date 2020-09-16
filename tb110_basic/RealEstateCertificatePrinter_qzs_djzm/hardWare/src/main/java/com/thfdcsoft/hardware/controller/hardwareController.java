package com.thfdcsoft.hardware.controller;

import java.awt.print.PrinterJob;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Map;

import com.thfdcsoft.hardware.factory.*;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.export.JRPrintServiceExporter;
import net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.pp.tool.SealMachine;
import com.thfdcsoft.hardware.constant.Constant;
import com.thfdcsoft.hardware.dto.HttpReturnDto;
import com.thfdcsoft.hardware.dto.IdReadInformation;
import com.thfdcsoft.hardware.properties.StaticConfig;
import com.thfdcsoft.ocr.OCRTool;
import com.yunmai.DllDemo;

import net.sf.jasperreports.engine.util.JRLoader;
import zkteco.id100com.jni.id100sdk;

import javax.print.PrintService;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.servlet.http.HttpServletRequest;

@RestController
public class hardwareController {

	private SealMachine sealmachine = new SealMachine();

	@Value("${hpPrinter}")
	private String hpPrinter;

	@Value("${fstPrinter}")
	private String fstPrinter;
	
	// 权证书打印（指定打印机打印）
	@PostMapping("/printDoc")
	public void printDoc(@RequestBody String filePath) {
		System.out.println("开始打印文件……" + filePath);
		DocxPrint dp = new DocxPrint();
		dp.print(filePath,fstPrinter);
	}

	// 识别OCR
	@PostMapping("/identifyOcr")
	public String identifyOcr(HttpServletRequest request) {
		String recognitionPath =  request.getParameter("recognitionPath");
		System.out.println("开始识别OCR……");
		HttpReturnDto httpReturnDto = new HttpReturnDto(true);
		int t = 0;
		String result = new String();
		int i = 5;
		do {
			try {
				// 0成功，其它值失败。1表示装载图像失败，2表示区域定位失败，3表示识别区域失败，4表示软件试用期到期
				i = new OCRTool().recognize(recognitionPath, result);
				System.out.println("识别结果:" + result);
				result = result.trim();
				t++;
				System.out.println(t);
			} catch (Exception e) {
				e.printStackTrace();
			}

		} while ((result.length() != 11) && (t < 10));
		// 移动文件位置
		if (result.length() != 11) {
			result = "00000000000";
		}
		httpReturnDto.setRespMsg(String.valueOf(i));
		httpReturnDto.setRespJson(result);
		return JacksonFactory.writeJson(httpReturnDto);
	}

	/**
	 * 测试连接，用于确认系统是否处于可用状态
	 * 
	 * @return
	 */
	@GetMapping("/")
	public String home() {
		return "normal";
	}

	/**
	 * 清空读取身份证时保存的身份证图片 2019年4月26日 09点10分 
	 */
	@RequestMapping(value = "/cleanIDCardPic", method = RequestMethod.GET)
	@ResponseBody
	public void cleanIDCardPic() {
		String path = Constant.IDReader.IDCARDPIC;
		FileFactory.deleteDirs(path);
	}

	@RequestMapping(value = "/closeID")
	@ResponseBody
	public String closeID() {
		System.out.println("进入身份证关闭方法");
		HttpReturnDto httpReturnDto = new HttpReturnDto(false);
		int i = id100sdk.CloseComm();
		if (i == 1) {
			httpReturnDto.setResult(true);
			System.out.println("身份证模块关闭成功");
		}
		return JacksonFactory.writeJson(httpReturnDto);
	}

	/**
	 * 不动产全证书自助打印终端 调用身份证读卡器进行读卡 2019年4月25日 17点53分 田伟
	 * 
	 * @return
	 */
	@RequestMapping(value = "/readIDCard", method = RequestMethod.GET)
	@ResponseBody
	public String readIDCard() {
		IdReadInformation ClientRsp = new IdReadInformation();
		HttpReturnDto httpReturnDto = new HttpReturnDto(false);
		System.out.println("开始读取身份证……");
		boolean readFlg = true;
		int count = 0;
		while (readFlg && count < 40) {
			count ++;
			if (id100sdk.InitCommExt() > 0) {
				int i = id100sdk.Authenticate();
				if (i == 1) {
					int r = id100sdk.ReadContent(1);
					if (r == 1) {
						String path = id100sdk.getPath();
						System.out.printf("GetBmpPhotoExt ret=" + id100sdk.GetBmpPhotoExt());
						System.out.printf("姓名=%s\n", id100sdk.getName());
						ClientRsp.setFullName(id100sdk.getName());
						System.out.printf("民族=%s\n", id100sdk.getNation());
						ClientRsp.setNation(id100sdk.getNation());
						System.out.printf("性别=%s\n", id100sdk.getSex());
						ClientRsp.setSex(id100sdk.getSex());
						System.out.printf("身份证号=%s\n", id100sdk.getIDNum());
						ClientRsp.setIdNumber(id100sdk.getIDNum());
						System.out.printf("出生日期=%s\n", id100sdk.getBirthdate());
						System.out.printf("常住地址=%s\n", id100sdk.getAddress());
						System.out.printf("签发机关=%s\n", id100sdk.getIssue());
						System.out.printf("有效�?=%s-%s\n", id100sdk.getEffectedDate(), id100sdk.getExpireDate());
						ClientRsp.setExpireDate(id100sdk.getExpireDate());
						String idpicbase64 = id100sdk.getJPGPhotoBase64();

						String idPicPath = Constant.IDReader.IDREADER_ROOT + "/faces/"
								+ DateFactory.getCurrentDateString("yyyyMMddHHmm") + "_" + id100sdk.getIDNum() + ".jpg";
						boolean flag = ImageFactory.generateImage(idpicbase64, idPicPath);
						if (flag) {
							ClientRsp.setIdPicPath(idPicPath);
							httpReturnDto.setResult(true);
							httpReturnDto.setRespJson(JacksonFactory.writeJson(ClientRsp));
						}
						System.out.println("httpReturnDto====" + httpReturnDto.isResult());
						int o = id100sdk.CloseComm();
						if (o == 1) {
							System.out.println("身份证读卡关闭成功");
						} else {
							System.out.println("身份证读卡关闭失败");
						}
						return JacksonFactory.writeJson(httpReturnDto);
					}
				}
			}
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return JacksonFactory.writeJson(httpReturnDto);
	}

	/**
	 * 初始化 不动产全证书自助打印终端身份证读卡器 2019年4月25日 17点53分 田伟
	 * 
	 * @return
	 */
	@RequestMapping(value = "/initIDCardreader", method = RequestMethod.GET)
	@ResponseBody
	public String initIDCardRead() {
		System.out.println("初始化身份证读卡器……");
		HttpReturnDto httpReturnDto = new HttpReturnDto(false);
		IDCardReadFactory.initComm();
		if (IDCardReadFactory.isInitiated) {
			System.out.println("读卡器初始化成功");
			httpReturnDto.setResult(true);
		}
		return JacksonFactory.writeJson(httpReturnDto);
	}

	// 初始化读卡器跟盖章机
	@RequestMapping(value = "/initIdreader", method = RequestMethod.GET)
	public String initIdreader() {
		System.out.println("初始化身份证读卡器……");
		HttpReturnDto httpReturnDto = new HttpReturnDto(true);
		IDReaderFactory.initComm();
		if (!IDReaderFactory.isInitiated) {
			System.out.println("读卡器初始化异常!!!");
			httpReturnDto.setRespMsg("身份证读卡器初始化失败，请联系现场工作人员！！！");
			httpReturnDto.setResult(false);
		}
		return JacksonFactory.writeJson(httpReturnDto);
	}

	// 启动盖章机
	@RequestMapping(value = "/initStampingMachine", method = RequestMethod.GET)
	public String initStampingMachine() {
		System.out.println("启动盖章机盖章……");
		HttpReturnDto httpReturnDto = new HttpReturnDto(true);
		String resStr = "";
		// 初始化盖章机函数 //初始化盖章机函数 端口号 x坐标 y坐标
		resStr = sealmachine.StartSeal(StaticConfig.port, StaticConfig.x, StaticConfig.y);
		// 启动盖章机 并判断盖章机状态
		if (!resStr.equals("success")) {
			// 盖章机启动异常
			System.out.println("盖章机启动异常!!!");
			httpReturnDto.setRespMsg("盖章机启动失败，请联系现场工作人员！！！");
			httpReturnDto.setResult(false);
		}
		return JacksonFactory.writeJson(httpReturnDto);
	}

	// 启动盖章并关闭盖章机
	@RequestMapping(value = "/startStampingMachine", method = RequestMethod.GET)
	public String startStampingMachine() {
		System.out.println("开始盖章……");
		HttpReturnDto httpReturnDto = new HttpReturnDto(true);
		// 启动盖章
		String resStr = sealmachine.Sealing();
		sealmachine.closeSeal();
		if (!resStr.equals("success")) {
			httpReturnDto.setResult(false);
			httpReturnDto.setRespMsg("盖章机调用失败，请联系现场工作人员！！！");
		}
		return JacksonFactory.writeJson(httpReturnDto);
	}

	/**
	 * 登记证明打印（指定打印机打印）
	 */
	@PostMapping("/jasperPrint")
	public String jasperPrint(@Validated @RequestBody HttpReturnDto req) throws JRException {
		System.out.println("in jasperPrint.....");
		System.out.println("打印请求req="+req);
		HttpReturnDto httpReturnDto = new HttpReturnDto(true);
		try {
			JasperReport jasperReport = (JasperReport) JRLoader.loadObject(new File(req.getRespJson()));
			@SuppressWarnings("unchecked")
			// JasperFillManager.fillReport在64位jdk上加载不了
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, (Map<String, Object>) req.getRespObj(),
			new JREmptyDataSource());
			// 获取打印机服务
			PrintService ps = getPrintService(hpPrinter);
			JRAbstractExporter je = new JRPrintServiceExporter();
			je.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
			//设置指定打印机
			je.setParameter(JRPrintServiceExporterParameter.PRINT_SERVICE, ps);
			je.setParameter(JRPrintServiceExporterParameter.DISPLAY_PAGE_DIALOG, false);
			je.setParameter(JRPrintServiceExporterParameter.DISPLAY_PRINT_DIALOG, false);
			//打印
			je.exportReport();
		} catch (Exception e) {
			httpReturnDto.setResult(false);
			httpReturnDto.setRespMsg("打印数据出错，请联系技术人员");
			e.printStackTrace();
		}
		return JacksonFactory.writeJson(httpReturnDto);
	}

	/**
	 * 获取指定名称的打印机
	 * @param printName 指定的打印机名称
	 */
	private static PrintService getPrintService(String printName) {
		PrintRequestAttributeSet pras = new HashPrintRequestAttributeSet();
		//可用的打印机列表(字符串数组)
		PrintService printService[] = PrinterJob.lookupPrintServices();
		if (printService != null) {
			for (PrintService p : printService) {
                System.out.println("printName="+p.getName());
				if (p.getName().equals(printName)) {
					return p;
				}
			}
		}
		return null;
	}

}
