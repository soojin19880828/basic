package com.whminwei.controller;

import com.whminwei.client.Main;
import com.whminwei.view.MainView;
import com.whminwei.view.script.ClientScript;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.whminwei.view.MainView.*;

public class AdministratorController {
	
	public static Logger logger = LoggerFactory.getLogger(AdministratorController.class);
	/**
	 * 
	 * Description:查看打印机管理
	 * @Author 罗子敬  
	 * @Date 2020年9月7日 下午5:24:06
	 */
	public void printerManage() {
		logger.info("【进入打印机管理页面】");
		Platform.runLater(() -> {
			try {
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(Main.class.getResource("fxml/PrinterManage.fxml"));
				AnchorPane borderPane = loader.load();
				MainView.border.setCenter(borderPane);
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
	}

	/**
	 * Description:重启程序
	 * @Author 罗子敬  
	 * @Date 2020年9月7日 下午5:24:24
	 */
	public void resetButton() {
		Runtime runtime = Runtime.getRuntime();
		try {
			// 5秒电脑重启
			runtime.exec("shutdown -r -f -t 5");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * Description:退出程序
	 * @Author 罗子敬  
	 * @Date 2020年9月7日 下午5:24:43
	 */
	public void exitButton() {
		logger.info("退出程序");
		System.exit(0);
	}

	/**
	 * 
	 * Description:加证明
	 * @param mouseEvent
	 * @Author 罗子敬  
	 * @Date 2020年9月7日 下午5:24:59
	 */
	public void addPaperButton(MouseEvent mouseEvent) {
		logger.info("【进入加证明页面】");
		Platform.runLater(() -> {
			try {
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(Main.class.getResource("fxml/AddPaper.fxml"));
				AnchorPane borderPane = loader.load();
				MainView.border.setCenter(borderPane);
			} catch (IOException e) {
				e.printStackTrace();
			}
		});

	}
	
	/**
	 * Description:加证书
	 * @param mouseEvent
	 * @Author 高拓  
	 * @Date 2020年9月7日 下午5:25:21
	 */
	public void addCertificateButton(MouseEvent mouseEvent) {
		logger.info("【进入加证书页面】");
		Platform.runLater(() -> {
			try {
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(Main.class.getResource("fxml/AddCertificate.fxml"));
				AnchorPane borderPane = loader.load();
				MainView.border.setCenter(borderPane);
			} catch (IOException e) {
				e.printStackTrace();
			}
		});

	}
	
}
