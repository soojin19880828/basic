package com.whminwei.view.script;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.whminwei.constant.Constant;
import com.whminwei.dto.RealEstateInfo;
import com.whminwei.util.DocxPrint;
import com.whminwei.util.FileFactory;
import com.whminwei.util.ImageFactory;
import com.whminwei.view.MainView;

/**
 * @ClassName: PrintingTool
 * @Description: 处理打印数据
 * @author 肖银
 * @date 2020年8月31日 下午1:43:12
 *
 */
public class PrintingTool {

	private static final Logger logger = LoggerFactory.getLogger(PrintingTool.class);

	private static boolean endFlag;

	/**
	 * @description: 拼装打印文档
	 * @param data     需要打印的数据
	 * @param destPath 打印文档
	 * @return
	 * @author: 肖银
	 * @date: 2020年8月20日下午4:19:00
	 */
	public static boolean readyPrint(RealEstateInfo data, String destPath) {
		boolean result = true;
		// 查杀word进程 避免word占用文件导致程序无法操作
		DocxPrint docxP = new DocxPrint();
		DocxPrint.comfirmSingleProcess("WINWORD");

		// 处理宗地图、分布图图片
		if (!buildFenbutuAndZongditu(data)) {
			MainView.errorMessage = "宗地图/分布图生成失败！！！";
			return false;
		}

		// 生成word文档
		boolean makeFlag = docxP.makeFile(data);
		if (!makeFlag) {
			MainView.errorMessage = "生成打印文件失败，请联系工作人员！！！";
			return false;
		}
		// 保存生成的word文件
		boolean saveState = FileFactory.copyFileUsingFileChannels(new File(Constant.QRCode.DOCFILEOUT), new File(destPath));
		if (!saveState) {
			logger.error("打印文档生成状态：" + saveState);
			return false;
		}
		logger.info("打印文档组装成功");
		return result;
	}

	/**
	 * @description: 处理宗地图、分布图图片
	 * @param info
	 * @return
	 * @author: 肖银
	 * @date: 2020年9月8日下午4:25:45
	 */
	private static boolean buildFenbutuAndZongditu(RealEstateInfo info) {
		// 检查是否已经存在宗地图图片 存在则先删除
		boolean result = true;
		// 生成宗地图
		boolean zongDiTuFlag = ImageFactory.generateImage(info.getZongditu(), Constant.FileDir.ZONGDITU);
		// 检查是否已经存在分布图图片 存在则先删除
		// 生成分布图
		boolean fenBuTuFlag = ImageFactory.generateImage(info.getFenbutu(), Constant.FileDir.FENBUTU);
		if (!zongDiTuFlag || !fenBuTuFlag) {
			logger.error("宗地图生成状态：" + zongDiTuFlag + "   分布图生成状态：" + fenBuTuFlag);
			result = false;
		}
		logger.info("宗地图、分布图生成成功");
		return result;
	}

}
