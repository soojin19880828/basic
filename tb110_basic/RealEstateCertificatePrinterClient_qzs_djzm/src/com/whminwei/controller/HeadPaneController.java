package com.whminwei.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.whminwei.util.ClockPaneFactory;
import com.whminwei.view.MainView;
import com.whminwei.view.plugin.CountDownPlug;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.util.Duration;

/**
 * @author 10118 2020年8月14日
 */
public class HeadPaneController {

	public static Logger logger = LoggerFactory.getLogger(HeadPaneController.class);

	/**
	 * 倒计时
	 */
	@FXML
	private Text countDownTime = CountDownPlug.getCountDownText();

	/**
	 * 日期时间
	 */
	@FXML
	private HBox dateTime;

	@FXML
	private HBox headHbox;

	/** 配件 */
	@FXML
	private void initialize() {
		logger.info("in HeadPaneController initialize...");
		// 加载日期
		ClockPaneFactory clock = new ClockPaneFactory();
		// 创建一个handler
		EventHandler<ActionEvent> eventHandler = e -> {
			clock.setCurrentTime();
		};
		Timeline animation = new Timeline(new KeyFrame(Duration.millis(60000), eventHandler));
		animation.setCycleCount(Timeline.INDEFINITE);
		animation.play();
		dateTime.getChildren().add(clock);

		// 加载倒计时
		CountDownPlug.getCountDownText().getStyleClass().add("count_down");
		headHbox.getChildren().add(CountDownPlug.getCountDownText());
		logger.info("out HeadPaneController initialize...");
	}

	// 返回首页
	public void returnHome() {
		MainView.home();
	}

}
