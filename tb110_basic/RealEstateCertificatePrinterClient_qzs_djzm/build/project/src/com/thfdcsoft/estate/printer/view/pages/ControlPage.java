package com.thfdcsoft.estate.printer.view.pages;

import java.io.IOException;

import com.thfdcsoft.estate.printer.client.Main;
import com.thfdcsoft.estate.printer.view.MainView;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

/**
 * 控制台页面<br>
 * 1.退回桌面<br>
 * 2.补充证明
 * 
 * @author 张嘉琪
 *
 */
public class ControlPage {

	/**
	 * 封装控制台页面
	 * 
	 * @return
	 */
	public static Pane buildPage() {
		// 按钮面板
		GridPane btnPane = new GridPane();
		btnPane.getStyleClass().add("btn-pane");
		btnPane.setAlignment(Pos.TOP_CENTER);
		btnPane.setHgap(60);
		btnPane.setVgap(30);
		btnPane.setPadding(new Insets(100, 0, 0, 20));

		
		// 返回首页
		Image homeBtn = new Image(Main.class.getResource("images/home-btn.png").toExternalForm());
		ImageView homeBtnView = new ImageView(homeBtn);
		homeBtnView.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				MainView.home();
			}
		});
		homeBtnView.getStyleClass().add("base-button");
		btnPane.add(homeBtnView, 0, 3);

		// 退回桌面
		Image exitBtn = new Image(Main.class.getResource("images/exit-btn.png").toExternalForm());
		ImageView exitBtnView = new ImageView(exitBtn);
		exitBtnView.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				Platform.exit();
			}
		});
		exitBtnView.getStyleClass().add("base-button");
		btnPane.add(exitBtnView, 3, 3);
		
		// 重启程序
		Image cqcxImage = new Image(Main.class.getResource("images/cqcx.png").toExternalForm());
		ImageView cqcxImageView = new ImageView(cqcxImage);
		cqcxImageView.setTranslateX(300);
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
		zxhcxImageView.setTranslateX(350);
		zxhcxImageView.setTranslateY(250);
		zxhcxImageView.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				Main.stage.setIconified(true);
			}
		});
		
		// 补充证明
		Image replBtn = new Image(Main.class.getResource("images/repl-btn.png").toExternalForm());
		ImageView replBtnView = new ImageView(replBtn);
		replBtnView.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				Platform.runLater(() -> {
					MainView.border.setCenter(ReplenishPage.buildPage());
				});
			}
		});
		replBtnView.getStyleClass().add("base-button");
		btnPane.add(replBtnView, 0, 5);

		return btnPane;
	}

}
