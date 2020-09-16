package com.thfdcsoft.estate.printer.view.pages;

import java.io.FileInputStream;
import java.util.Properties;

import com.thfdcsoft.estate.printer.view.MainView;
import com.thfdcsoft.estate.printer.view.plugin.MediaPlug;
import com.thfdcsoft.estate.printer.view.script.ClientScript;

import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class AdminPage {
	
	private static String str;       //配置文件的超级密码
	
	public static Pane buildPage() {
		System.out.println(" 超级密码页面---------");
		
		VBox vb = new VBox(0);
		vb.setPadding(new Insets(0, 0, 0, 0));
		vb.getStyleClass().add("btn-pane");
	
		HBox text = new HBox();
		Text lb = new Text("请输入超级密码：");
		text.setTranslateX(550);
		text.setTranslateY(130);
		lb.getStyleClass().add("lb");

		PasswordField passWord = new PasswordField();
		passWord.setTranslateY(-10);
		passWord.getStyleClass().add("pwd");
		Text message = new Text("");
		message.getStyleClass().add("message");
		text.getChildren().addAll(lb, passWord, message);
		//聚焦事件
		passWord.hoverProperty().addListener(new InvalidationListener() {
		@Override
		public void invalidated(Observable observable) {
		if(passWord.isHover()){
			ClientScript client = new ClientScript();
			client.handinput();         //开启键盘
		}
		       }
		});		
			
		
		
		//写入配置文件					 
		 Properties p = new Properties();
		 String profilepath="supper.properties";			 
		 try {					     
			// 读取配置文件supper.properties
		     p.load(new FileInputStream(profilepath));
		     // 获取配置文件中的相关内容
		      str = p.getProperty("supperpwd");
		     System.out.println("获取配置文件的超级密码为:"+str);			   
		 }catch (Exception e) {
		  }
	
		 
		HBox homeAndConfirmHB = new HBox(100);		
		Button ConfirmImageView = new Button("登陆");
		ConfirmImageView.getStyleClass().add("homecss");
		ConfirmImageView.setTranslateX(400);//900
		ConfirmImageView.setTranslateY(390);//730
		ConfirmImageView.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				//获取输入的超级密码
				String pwd=passWord.getText();
				System.out.println("输入的超级密码为:"+pwd);
				
				if(pwd.equals(str)) {							//登陆成功
					// 跳转到管理员操作界面
					Platform.runLater(() -> {
//						ClientScript.handinputclose();
						MediaPlug.validateSuccess();
						MainView.border.setCenter(HandlePage.buildPage());  //跳转到管理员操作页面
					});
				}
				 else {
					Task<Void> task = new Task<Void>() {
						@Override
						protected Void call() throws Exception {
							Platform.runLater(() -> {
//								MediaPlug.checkFailure();			//语音提示
								message.setText(" 密码错误，请重新输入！！！");
							});
							Thread.sleep(4000);
							Platform.runLater(() -> {
								message.setText("");
							});
							return null;
						}
					};
					Thread thread = new Thread(task);
					thread.setDaemon(true);
					thread.start();
				}
			}
		});

		Button homeImageView = new Button("返回首页");
		homeImageView.getStyleClass().add("homecss");
		homeImageView.setTranslateX(865);  //1555
		homeImageView.setTranslateY(390);	//730
		homeImageView.setOnMouseClicked(new EventHandler<MouseEvent>() {
		@Override
		public void handle(MouseEvent event) {
//			ClientScript.handinputclose();
			MainView.home();
		}
	});
		
		homeAndConfirmHB.getChildren().addAll(homeImageView, ConfirmImageView);
		vb.getChildren().addAll(homeAndConfirmHB, text);
		return vb;
	}
}
