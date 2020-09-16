package com.thfdcsoft.estate.printer.view.pages;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.thfdcsoft.estate.printer.client.Main;
import com.thfdcsoft.estate.printer.constant.Constant;
import com.thfdcsoft.estate.printer.util.ComCommunication;
import com.thfdcsoft.estate.printer.util.FileFactory;
import com.thfdcsoft.estate.printer.util.SerialTool;
import com.thfdcsoft.estate.printer.util.serialException.SendDataToSerialPortFailure;
import com.thfdcsoft.estate.printer.util.serialException.SerialPortOutputStreamCloseFailure;
import com.thfdcsoft.estate.printer.view.MainView;
import com.thfdcsoft.estate.printer.view.plugin.CountDownPlug;
import com.thfdcsoft.estate.printer.view.script.ClientScript;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class HandlePage {
	
	private static final Logger logger = LoggerFactory.getLogger(HandlePage.class);
	
	public static Pane buildPage() {
		System.out.println("进入管理员操作页面");
		VBox vb = new VBox(0);
		vb.setPadding(new Insets(0, 0, 0, 0));
		vb.getStyleClass().add("main-box");

		// 添加证书
		Image tjzsImage = new Image(Main.class.getResource("images/tjzs.png").toExternalForm());
		ImageView tjzsImageView = new ImageView(tjzsImage);
		tjzsImageView.setTranslateX(10);
		tjzsImageView.setTranslateY(250);
		tjzsImageView.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				Platform.runLater(() -> {
					CountDownPlug.resetCountDown();
					MainView.border.setCenter(ReplenishPage.buildPage());
				});
			}
		});
		
		// 添加证明
		Image tjzmImage = new Image(Main.class.getResource("images/tjzm.png").toExternalForm());
		ImageView tjzmImageView = new ImageView(tjzmImage);
		tjzmImageView.setTranslateX(40);
		tjzmImageView.setTranslateY(250);
		tjzmImageView.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				Platform.runLater(() -> {
					CountDownPlug.resetCountDown();
					MainView.border.setCenter(ZmReplenishPage.buildPage());
				});
			}
		});
		
		

		// 重启程序
		Image cqcxImage = new Image(Main.class.getResource("images/cqcx.png").toExternalForm());
		ImageView cqcxImageView = new ImageView(cqcxImage);
		cqcxImageView.setTranslateX(70);
		cqcxImageView.setTranslateY(250);
		cqcxImageView.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				Runtime runtime = Runtime.getRuntime();
			     try {
			    	//5秒电脑重启
					runtime.exec("shutdown -r -f -t 5");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

		// 最小化程序
		Image zxhcxImage = new Image(Main.class.getResource("images/zxhcx.png").toExternalForm());
		ImageView zxhcxImageView = new ImageView(zxhcxImage);
		zxhcxImageView.setTranslateX(100);
		zxhcxImageView.setTranslateY(250);
		zxhcxImageView.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				Main.stage.setIconified(true);
			}
		});

		// 返回首页
//		System.out.println("返回首页");
		Image homeImage = new Image(Main.class.getResource("images/fhsy.png").toExternalForm());
		ImageView homeImageView = new ImageView(homeImage);
		homeImageView.setTranslateX(130);
		homeImageView.setTranslateY(250);
		homeImageView.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				MainView.home();
			}
		});
		//异常出证
		Image errorchuzheng = new Image(Main.class.getResource("images/yccz.png").toExternalForm());
		ImageView errorImageView = new ImageView(errorchuzheng);
		errorImageView.setTranslateX(160);
		errorImageView.setTranslateY(250);
		errorImageView.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				HandlePage page = new HandlePage();
				page.errorchuzheng();
			}
		});		
		
		HBox homeAndConfirmHB = new HBox(50);
		homeAndConfirmHB.getChildren().addAll(tjzsImageView,tjzmImageView,cqcxImageView,zxhcxImageView,homeImageView,errorImageView);
		vb.getChildren().add(homeAndConfirmHB);
		return vb;
	}

	private void errorchuzheng() {
//		if(ClientScript.comCommunication != null) {
//			SerialTool.closePort(ClientScript.comCommunication.getComNum());
//		}
		try {
			Thread.sleep(50);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		ComCommunication comCommunication = new ComCommunication(Constant.COM.QZS_GAI_ZHANG_JI);
		// 向盖章机传递走纸指令
		byte[] success = { (byte) 254, 1, 5, (byte) 151, (byte) 178, 34,
				3 };
		try {
			SerialTool.sendToPort(comCommunication.getComNum(), success);
		} catch (SendDataToSerialPortFailure e) {
			e.printStackTrace();
		} catch (SerialPortOutputStreamCloseFailure e) {
			e.printStackTrace();
		}
		int sendCount = 0;
		boolean sendState = true;
		while (sendState) {
			sendCount ++;
			if (ClientScript.gaiZhangJiComState) {
				logger.info("指令下发状态= " + ClientScript.gaiZhangJiComStr);
				if ("fe970501b00023".equals(ClientScript.gaiZhangJiComStr)) {
					logger.info("指令下发成功");
					if(MainView.logFile != null) {
						String text2 = "盖章机异常出证成功！";
						FileFactory.writeTXTFile(MainView.logFile, text2);
					}
					MainView.home();
					break;// 跳出送正指令循环环
				}else {
//					SerialTool.closePort(comCommunication.getComNum());
					Platform.runLater(() -> {
						TransitPage.attentionPage("送证指令发送异常1，请联系运维人员。", -1);
					});
				}
			}
			//十秒钟
			if(sendCount > 200) {
//				SerialTool.closePort(comCommunication.getComNum());
				sendState = false;
				Platform.runLater(() -> {
					TransitPage.attentionPage("送证指令发送异常2，请联系运维人员。", -1);
				});
			}
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
//		SerialTool.closePort(comCommunication.getComNum());
	}
}
