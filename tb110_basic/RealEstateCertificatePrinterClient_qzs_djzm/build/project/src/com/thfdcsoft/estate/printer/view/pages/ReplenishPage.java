package com.thfdcsoft.estate.printer.view.pages;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import com.thfdcsoft.estate.printer.constant.Constant;
import com.thfdcsoft.estate.printer.dto.HttpReturnDto;
import com.thfdcsoft.estate.printer.dto.req.ChkRemainingReq;
import com.thfdcsoft.estate.printer.dto.req.SubmitRemainingReq;
import com.thfdcsoft.estate.printer.util.HttpClientFactory;
import com.thfdcsoft.estate.printer.util.JacksonFactory;
import com.thfdcsoft.estate.printer.view.MainView;
import com.thfdcsoft.estate.printer.view.plugin.CountDownPlug;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

/**
 * 补充证书页面
 * 
 * @author 张嘉琪
 *
 */
public class ReplenishPage {

	private static Text remaining = new Text(MainView.remaining);   //0
 
	
	public static void setRemaining(String n) {
		SubmitRemainingReq req = new SubmitRemainingReq();
		req.setDeviceNumber(Constant.DeviceInfo.DEVICE_NUMBER);
		req.setRemaining(remaining.getText());
		req.setTerminalId(Constant.TerminalInfo.QZS_TYPE);
		
		String reqJson = JacksonFactory.writeJson(req);
		try {
			String rspnJson = HttpClientFactory.getInstance().doPost(reqJson,
					Constant.Business.BUSINESS_URL + "submitRemaining");
			HttpReturnDto rspn = JacksonFactory.readJson(rspnJson, HttpReturnDto.class);
			if (rspn.isResult()) {
				Platform.runLater(() -> {
					MainView.remaining = remaining.getText();
				});
			}
		} catch (KeyManagementException | NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

	}

	public static void getRemaining() {                           //数据库证书数量
		ChkRemainingReq req = new ChkRemainingReq();
		req.setDeviceNumber(Constant.DeviceInfo.DEVICE_NUMBER);
		req.setTerminalId(Constant.TerminalInfo.QZS_TYPE);
		String reqJson = JacksonFactory.writeJson(req);
		try {
			String rspnJson = HttpClientFactory.getInstance().doPost(reqJson,
					Constant.Business.BUSINESS_URL + "chkRemaining");
			HttpReturnDto rspn = JacksonFactory.readJson(rspnJson, HttpReturnDto.class);
			if (rspn.isResult()) {
				Platform.runLater(() -> {
					ReplenishPage.remaining.setText(rspn.getRespJson());
		//			MainView.remaining=String.valueOf(rspn.getRespJson());       //数据库证书数量
					System.out.println("remaining  M:"+remaining);				
				});
			}
		} catch (KeyManagementException | NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

	}

	public static Pane buildPage() { 
		// 按钮面板
		GridPane replPane = new GridPane();
		replPane.getStyleClass().add("replenish-pane");
		replPane.setAlignment(Pos.TOP_CENTER);
		replPane.setHgap(60);
		replPane.setVgap(30);
		replPane.setPadding(new Insets(120, 0, 0, 20));
		
		Text label  = new Text("证书总量不能超过80：");
		label.getStyleClass().add("ts_text");
		replPane.add(label, 0, 1, 3, 1);// 第一行，占用第一第二第三列
		remaining.getStyleClass().add("normal-text");
	//	replPane.add(remaining, 3, 0, 2, 1);// 第一行，占用第四第五列
		
		
		// 剩余空白证明
		Label remainingLabel = new Label("剩余空白证书：");
		remainingLabel.getStyleClass().add("normal-text");
		replPane.add(remainingLabel, 0, 0, 3, 1);// 第一行，占用第一第二第三列
		remaining.getStyleClass().add("normal-text");
		replPane.add(remaining, 3, 0, 2, 1);// 第一行，占用第四第五列
		
			
		// 获取空白证明数量(权证书)
		getRemaining();
		
		
		Integer sum = Integer.parseInt(remaining.getText());          //获取总的证书数量
		System.out.println("获取总的证书数量:"+sum);
		
		
		// 增加
		Text add1 = new Text("+1");
		add1.getStyleClass().add("add-text");
		add1.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
//				if(sum<=79) {
					Integer r = Integer.parseInt(remaining.getText());					
					r += 1;
					remaining.setText(String.valueOf(r));
					System.out.println("remaining sum :"+remaining);
//				}else {
//					remaining.setText(String.valueOf(sum));
//				}
			}
		});
		replPane.add(add1, 0, 2);
		
		Text add5 = new Text("+5");
		add5.getStyleClass().add("add-text");
		add5.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
//				if(sum<=75) {
					Integer r = Integer.parseInt(remaining.getText());
					r += 5;		
						remaining.setText(String.valueOf(r));
//				}else {
//					remaining.setText(String.valueOf(sum));
//				}								
			}
		});
		replPane.add(add5, 1, 2);
		
		Text add10 = new Text("+10");
		add10.getStyleClass().add("add-text");
		add10.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
//				if(sum<=70) {
					Integer r = Integer.parseInt(remaining.getText());
					r += 10;
					remaining.setText(String.valueOf(r));
//				}else{
//					remaining.setText(String.valueOf(sum));
//				}						
			}
		});
		replPane.add(add10, 2, 2);
		
		Text add50 = new Text("+50");
		add50.getStyleClass().add("add-text");
		add50.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
//				if(sum<=30) {
					Integer r = Integer.parseInt(remaining.getText());
					r += 50;
					remaining.setText(String.valueOf(r));
//				}else {
//					remaining.setText(String.valueOf(sum));
//				}				
			}
		});
		replPane.add(add50, 3, 2);
		
	
		// 减少
		Text sub1 = new Text("-1");
		sub1.getStyleClass().add("sub-text");
		sub1.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
//				if(sum<=80) {
					Integer r = Integer.parseInt(remaining.getText());
					if (r >= 1) {
						r -= 1;
						remaining.setText(String.valueOf(r));
					}
//				}
			}
		});
		replPane.add(sub1, 0, 3);
		
		Text sub5 = new Text("-5");
		sub5.getStyleClass().add("sub-text");
		sub5.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
//				if(sum>=5) {
					Integer r = Integer.parseInt(remaining.getText());
					if (r >= 5) {
						r -= 5;
						remaining.setText(String.valueOf(r));
					}
//				}
			}
		});
		replPane.add(sub5, 1, 3);
		
		Text sub10 = new Text("-10");
		sub10.getStyleClass().add("sub-text");
		sub10.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
//				if(sum>=10) {
					Integer r = Integer.parseInt(remaining.getText());
					if (r >= 10) {
						r -= 10;
						remaining.setText(String.valueOf(r));
					}
//				}
			}
		});
		replPane.add(sub10, 2, 3);
		
		Text sub50 = new Text("-50");
		sub50.getStyleClass().add("sub-text");
		sub50.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
//				if(sum>=50) {
					Integer r = Integer.parseInt(remaining.getText());
					if (r >= 50) {
						r -= 50;
						remaining.setText(String.valueOf(r));
					}
//				}
			}
		});
		replPane.add(sub50, 3, 3);
				

		// 返回
		Button homeImageView = new Button("返回");
		homeImageView.getStyleClass().add("homecss");
//		Image returnBtn = new Image(Main.class.getResource("images/button.png").toExternalForm());
//		ImageView returnBtnView = new ImageView(returnBtn);
				
		homeImageView.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				CountDownPlug.resetCountDown();
//				
				if(Integer.parseInt(remaining.getText())>80) {
					remaining.setText("80");
				}
				setRemaining(remaining.getText());            //更新数据库
				Platform.runLater(() -> {
					MainView.border.setCenter(HandlePage.buildPage());
				});
			}
		});
//		homeImageView.getStyleClass().add("base-button");
		replPane.add(homeImageView, 2, 5, 3, 1);
		return replPane;
	}
}
