package com.whminwei.view.plugin;

import com.whminwei.client.Main;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;

/**
 * 提示音播放
 * 
 * @author 张嘉琪
 *
 */
public class MediaPlug {
	
	private static MediaPlayer player;

	// 欢迎使用民为技术不动产自助打印终端
	private static Media media_welcome = new Media(Main.class.getResource("media/welcome.mp3").toExternalForm());

	// 请将二代身份证放在指定位置
	private static Media media_readID = new Media(Main.class.getResource("media/readIDCard.mp3").toExternalForm());
	
	// 验证失败，请重试
	private static Media media_validateFail = new Media(Main.class.getResource("media/ValicadeFail1.mp3").toExternalForm());
	
	// 验证失败，请。。。。。。
	private static Media media_validateFailure = new Media(Main.class.getResource("media/validateFail.mp3").toExternalForm());
	
	// 验证成功,请稍等，设备正在处理
	private static Media media_validateSuccess = new Media(Main.class.getResource("media/validateSuccess.mp3").toExternalForm());


	//密码错误，请重新输入！
	private static Media media_checkFailure = new Media(Main.class.getResource("media/checkFailure.mp3").toExternalForm());

	// 请正对摄像头。。。
	private static Media media_faceDetect = new Media(Main.class.getResource("media/faceDetect.mp3").toExternalForm());
	
	// 正在查询。。。
	private static Media media_query = new Media(Main.class.getResource("media/query.mp3").toExternalForm());
	
	// 请勾选需要打印的。。。
	private static Media media_checkPrint = new Media(Main.class.getResource("media/checkPrint.mp3").toExternalForm());
	
	// 设备正在打印。。。
	private static Media media_print = new Media(Main.class.getResource("media/print.mp3").toExternalForm());
	
	// 打印完成。。。
	private static Media media_printSuccess = new Media(Main.class.getResource("media/printSuccess.mp3").toExternalForm());

	// 打印失败。。。
	private static Media media_printFail = new Media(Main.class.getResource("media/printFail.mp3").toExternalForm());

	// 打印过程中出现异常。。。
	private static Media media_printError = new Media(Main.class.getResource("media/printError.mp3").toExternalForm());

	// 未查询到相关数据。。。
	private static Media media_querynull = new Media(Main.class.getResource("media/querynull.mp3").toExternalForm());

	// 未查询到相关数据。。。
	private static Media media_initError = new Media(Main.class.getResource("media/initError.mp3").toExternalForm());
	
	// 未查询到相关数据。。。
	private static Media media_readID_FaceDetec = new Media(Main.class.getResource("media/readID_FaceDetect.mp3").toExternalForm());
	
	// 身份证过期。。。
	private static Media media_IDPastDue = new Media(Main.class.getResource("media/IDPastDue.mp3").toExternalForm());

	//终端设备状态监测
	private static Media media_terminalStateDetection = new Media(Main.class.getResource("media/terminalStateDetection.mp3").toExternalForm());

	//识别错误提示
	private static Media media_readNumError = new Media(Main.class.getResource("media/readNumError.mp3").toExternalForm());

	//证书数量不足,请联系工作人员及时添加后重试
	private static Media media_pageCountError = new Media(Main.class.getResource("media/pageCountError.mp3").toExternalForm());
	
	//证明数量不足,请联系工作人员及时添加后重试
	private static Media media_zmPageCountError = new Media(Main.class.getResource("media/zmPageCountError.mp3").toExternalForm());
	
	//打印过程中可能出现异常
	private static Media media_printMaybeError = new Media(Main.class.getResource("media/maybeError.mp3").toExternalForm());

	//验证成功，请选择打印类型
	private static Media media_validateSuccessAndSelectType = new Media(Main.class.getResource("media/validateSuccessAndSelectType.mp3").toExternalForm());
	
	//请将条码放在扫描模块处进行扫描！
	private static Media media_barcodeScan = new Media(Main.class.getResource("media/barcodeScan.mp3").toExternalForm());
	
	//扫描成功
	private static Media media_scanSuccess = new Media(Main.class.getResource("media/scanSuccess.mp3").toExternalForm());
	
	//扫描码重复，请重新扫描
	public static Media media_scanRepeat = new Media(Main.class.getResource("media/scanRepeat.mp3").toExternalForm());
	
	//打印完成，请拿好您的证明及随身物品！
	private static Media media_complete = new Media(Main.class.getResource("media/printComplete.mp3").toExternalForm());
	
	//证书、证明数量获取失败，请联系工作人员处理
	private static Media media_getRemainingFailure = new Media(Main.class.getResource("media/getRemainingFailure.mp3").toExternalForm());

	/**
	 * 语音播报
	 *
	 * @param media 需要播报的语音
	 */
	static void voice(Media media) {
		if (player != null) {
			if (player.getStatus().equals(Status.PLAYING)) {
				player.stop();
			}
			player.dispose();
		}
		player = new MediaPlayer(media);
		player.play();
	}

	/**
	 * 打印过程中可能出现异常
	 */
	public static void printMaybeError() {
		voice(media_printMaybeError);
	}
	
	
	/**
	 * 证书数量不足
	 */
	public static void pageCountError() {
		voice(media_pageCountError);
	}
	
	/**
	 * 证明数量不足
	 */
	public static void zmPageCountError() {
		voice(media_zmPageCountError);
	}
	
	/**
	 * 证书、证明数量获取失败
	 */
	public static void getRemainingFailure() {
		voice(media_getRemainingFailure);
	}
	
	/**
	 * 证书编号识别错误
	 */
	public static void readNumError() {
		voice(media_readNumError);
	}
	
	/**
	 * 身份证过期
	 */
	public static void IDPastDue() {
		voice(media_IDPastDue);
	}
	
	
	/**
	 * 身份证读取+识别人脸
	 */
	public static void readID_FaceDetec() {
		voice(media_readID_FaceDetec);
	}
	/**
	 * 初始化失败
	 */
	public static void initError() {
		voice(media_initError);
	}
	
	/**
	 * 未查询到相关数据
	 */
	public static void queryNull() {
		voice(media_querynull);
	}
	
	/**
	 * 打印过程中出现异常
	 */
	public static void printError() {
		voice(media_printError);
	}
	
	/**
	 * 打印失败
	 */
	public static void printFail() {
		voice(media_printFail);
	}
	
	/**
	 * 打印完成
	 */
	public static void printSuccess() {
		voice(media_printSuccess);
	}
	
	/**
	 * 设备正在打印
	 */
	public static void print() {
		voice(media_print);
	}
	
	/**
	 * 请勾选需要打印的证书
	 */
	public static void checkPrint() {
		voice(media_checkPrint);
	}
	
	/**
	 * 正在查询
	 */
	public static void query() {
		voice(media_query);
	}
	
	/**
	 * 请正对摄像头
	 */
	public static void facedetect() {
		voice(media_faceDetect);
	}
	
	/**
	 * 验证失败请重试
	 */
	public static void validateFail() {
		voice(media_validateFail);
	}
	/**
	 * 欢迎使用
	 */

	public static void welcome() {
		voice(media_welcome);
	}
	
	/**
	 * 请将二代身份证放在指定位置
	 */
	public static void readID() {
		voice(media_readID);
	}

	/**
	 * 验证失败 您与设备间的距离较远...
	 */
	public static void validateFailure() {
		voice(media_validateFailure);
	}

	/**
	 * 验证成功
	 */
	public static void validateSuccess() {
		voice(media_validateSuccess);
	}

	/**
	 * 终端状态监测
	 */
	public static void terminalStateDetection() {
		voice(media_terminalStateDetection);
	}
	
	/**
	 * 请选择打印类型
	 */
	public static void selectPrintType() {
		voice(media_validateSuccessAndSelectType);
	}
	
	public static void barcodeScan() {
		if (player != null) {
			if (player.getStatus().equals(Status.PLAYING)) {
				player.stop();
			}
			player.dispose();
		}
		player = new MediaPlayer(media_barcodeScan);
		player.play();
	}
	
	public static void scanSuccess() {
		if (player != null) {
			if (player.getStatus().equals(Status.PLAYING)) {
				player.stop();
			}
			player.dispose();
		}
		player = new MediaPlayer(media_scanSuccess);
		player.play();
	}
	
	public static void scanRepeat() {
		if (player != null) {
			if (player.getStatus().equals(Status.PLAYING)) {
				player.stop();
			}
			player.dispose();
		}
		player = new MediaPlayer(media_scanRepeat);
		player.play();
	}
	
	public static void complete() {
		if (player != null) {
			if (player.getStatus().equals(Status.PLAYING)) {
				player.stop();
			}
			player.dispose();
		}
		player = new MediaPlayer(media_complete);
		player.play();
	}
	
}
