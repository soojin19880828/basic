package com.thfdcsoft.estate.printer.view.pages;

import com.thfdcsoft.estate.printer.view.MainView;
import com.thfdcsoft.estate.printer.view.plugin.CountDownPlug;

import javafx.application.Platform;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 * 提示信息过渡页<br>
 * 跳转时显示提示信息
 * 
 * @author 张嘉琪
 *
 */
public class TransitPage {
	private static Text initText = new Text();

	// 过渡页面中的文字
	private static Text transitText = new Text();
	
	private static Text transitText2 = new Text();
		
	// 用于页面跳转或提示信息的过渡页面
	private static VBox transit = new VBox();
	
	/**首页初始化过度界面*/ 
	private static VBox initPane = new VBox();
	
	/**用于页面跳转或提示信息的过渡页面*/ 
	private static VBox transitPane = new VBox();

	static {
		
		initPane.getStyleClass().add("init_pane");
		initText.setText("设备初始化中，请稍等......");
		initText.getStyleClass().add("initText");
		initPane.getChildren().add(initText);

		transitPane.getStyleClass().add("transit_pane");
		transitText.setText("界面加载中，请稍等......");
		transitText.getStyleClass().add("transitText");
		transitPane.getChildren().add(transitText);

	}
	static {
		transit.getStyleClass().add("transit-box");
		transitText.setText("设备初始化……");
		transitText.getStyleClass().add("transit-info");
		transit.getChildren().add(transitText);
	}
	
	
	private static VBox transit2 = new VBox();
	static {
		transit2.getStyleClass().add("transit-box");
		transitText2.setText("正在对设备进行检查，请稍等!");
		transitText2.getStyleClass().add("transit-info");
		transit2.getChildren().add(transitText2);
	}
	
	// 获取过渡页面
	public static Pane getTransit() {
		return transit;
	}
	public static Pane getTransit2() {
		return transit2;
	}
	
	public static Pane getInitPane() {
		return initPane;
	}
	
	// 设置提示文字
	public static void setTransitText(String text) {
			transitText.setText(text);
	}

	public static void setTransitText2(String text) {
		transitText2.setText(text);

	}
	/**
	 * 
	 * @param 需要提示的信息息
	 * @param s倒计时时间  若不需要倒计时 则传递负数
	 */
	public static void attentionPage(String msg , int s) {
		Platform.runLater(() -> {
			transit = new VBox();
			transitText = new Text();
			transit.getStyleClass().add("transit-box");
			transitText.setText(msg);
			transitText.getStyleClass().add("transit-info");
			transit.getChildren().add(transitText);
			MainView.border.setCenter(transit);
			if(s>0) {
				CountDownPlug.countDownStart = true;
				CountDownPlug.startCountDown(s);
			}else {
				CountDownPlug.stopCountDown();
			}
		});
	}


}
