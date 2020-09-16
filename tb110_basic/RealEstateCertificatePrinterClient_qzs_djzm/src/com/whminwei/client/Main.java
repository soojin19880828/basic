package com.whminwei.client;

import com.whminwei.constant.Constant;
import com.whminwei.util.PropertyFactory;
import com.whminwei.view.MainView;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
	public static Stage stage ;
	
	@Override
	public void start(Stage primaryStage) {
		// 读取配置文件
		PropertyFactory.initProperties();
		System.out.println(Constant.DeviceInfo.DEVICE_NUMBER);
		try {
			primaryStage.setScene(MainView.buildScene());
			if (Constant.StartMode.FORMAL.equalsIgnoreCase(Constant.StartMode.START_MODE)) {
				primaryStage.setFullScreen(true);//设置全屏
				primaryStage.setAlwaysOnTop(true);
			}
			stage = primaryStage;
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	public static void main(String[] args) {
		launch(args);
	}
}
