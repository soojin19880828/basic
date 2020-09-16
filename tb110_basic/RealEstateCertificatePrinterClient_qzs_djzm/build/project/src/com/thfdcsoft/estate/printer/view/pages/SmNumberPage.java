package com.thfdcsoft.estate.printer.view.pages;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import com.thfdcsoft.estate.printer.constant.Constant;
import com.thfdcsoft.estate.printer.dto.Number;
import com.thfdcsoft.estate.printer.httpReq.HttpReq;
import com.thfdcsoft.estate.printer.util.FileFactory;
import com.thfdcsoft.estate.printer.util.HttpClientFactory;
import com.thfdcsoft.estate.printer.util.JacksonFactory;
import com.thfdcsoft.estate.printer.view.MainView;
import com.thfdcsoft.estate.printer.view.plugin.CountDownPlug;
import com.thfdcsoft.estate.printer.view.plugin.MediaPlug;
import com.thfdcsoft.estate.printer.view.script.ClientScript;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class SmNumberPage {

	public static TextField qrCodeTF = new TextField();

	private static TableView<Number> table = null;

	public static ObservableList<Number> data = FXCollections.observableArrayList();

	public static List<String> smNumber = new ArrayList<String>();
	
	public static TextField textValue = new TextField(null);
	
	public static boolean returnHome = false;

	@SuppressWarnings("unchecked")
	public static Pane child() {

		table = new TableView<Number>();
		table.setEditable(false);
		table.setMaxWidth(1100);
		table.setPrefHeight(550);
		table.setTranslateX(415);
		table.setTranslateY(-25);

		VBox vb = new VBox(0);
		vb.setPadding(new Insets(0, 0, 0, 0));
		vb.getStyleClass().add("scan-btn-pane");

		HBox passWordhb = new HBox();
		passWordhb.getStyleClass().add("hb");
		Label lb = new Label("请扫描条形码：");
		passWordhb.setTranslateX(410);
		passWordhb.setTranslateY(45);
		lb.getStyleClass().add("lb");
		qrCodeTF = new TextField("");
		qrCodeTF.setTranslateX(0);
		qrCodeTF.setTranslateY(-10);
		qrCodeTF.getStyleClass().add("text");
//		qrCodeTF.setEditable(false);
		passWordhb.getChildren().addAll(lb, qrCodeTF);
		
		TableColumn<Number, String> number = new TableColumn<Number, String>("序号");
		TableColumn<Number, String> ywh = new TableColumn<Number, String>("扫描编号");
		number.setCellValueFactory(new PropertyValueFactory<Number, String>("id"));
		ywh.setCellValueFactory(new PropertyValueFactory<Number, String>("number"));
		ywh.getStyleClass().add("ywh");
		number.getStyleClass().add("number");
		number.setMinWidth(300);
		ywh.setMinWidth(800);
		//设置不可排序
		number.setSortable(false);
		ywh.setSortable(false);
		
		table.getColumns().addAll(number, ywh);
		table.setItems(data);
		
		//防止拖拽
		table.getColumns().addListener(new ListChangeListener<Object>() {  
			@Override  
	        public void onChanged(Change<?> change) {  
	          change.next();  
	          if(change.wasReplaced()) {  
	        	  table.getColumns().clear();  
	        	  table.getColumns().addAll(number, ywh);  
	          }  
	        }  
	    });
		
		HBox homeAndConfirmHB = new HBox(100);
		Button ConfirmImageView = new Button("查询");
		ConfirmImageView.getStyleClass().add("button");
		ConfirmImageView.setTranslateX(900);
		ConfirmImageView.setTranslateY(677);
		ConfirmImageView.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				//停止扫描进行扫码查询
				System.out.println("smNumber======"+smNumber.size());
				String text1 = "登记证明扫码查询请求数据：" + smNumber + ",数据长度为：" + smNumber.size();
				FileFactory.writeTXTFile(MainView.logFile, text1);
				if (smNumber.size() > 0) {
					// 关闭身份证读卡器
					try {
						String result = HttpClientFactory.getInstance().doGet(Constant.HardWare.HARDWARE_URL + "closeID");
						System.out.println("result======"+result);
						String text2 = "登记证明扫码查询请求关闭读卡器："+result;
                        FileFactory.writeTXTFile(MainView.logFile, text2);
					} catch (KeyManagementException | NoSuchAlgorithmException e) {
						e.printStackTrace();
						TransitPage.attentionPage("扫码后关闭读卡器失败，请联系工作人员。", -1);
					}
					HttpReq.forwardSmcx();
				}else {
					Platform.runLater(() -> {
						MainView.border.setCenter(SmNumberPage.child());
					});
				}
			}
		});

		Button homeImageView = new Button("返回首页");
		homeImageView.getStyleClass().add("button");
		homeImageView.setTranslateX(1555);
		homeImageView.setTranslateY(677);
		homeImageView.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				MainView.home();
			}
		});
		homeAndConfirmHB.getChildren().addAll(homeImageView, ConfirmImageView);
		vb.getChildren().addAll(passWordhb, homeAndConfirmHB, table);
		
		qrCodeTF.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				// 待数据读取完再判断
				// TODO Auto-generated method stub
				Task<Void> listebTask = new Task<Void>() {
					@Override
					protected Void call() throws Exception {
						Thread.sleep(1000);
						if(qrCodeTF.getText().length() > 0){
							System.out.println("扫码输入框中的内容："+qrCodeTF.getText());
							System.out.println("长度是否一致："+(qrCodeTF.getText().length() == Constant.ScanQrCode.QRCODE_LENGTH));
							if (qrCodeTF.getText().length() ==  Constant.ScanQrCode.QRCODE_LENGTH) {
								textValue.setText(qrCodeTF.getText());
								qrCodeTF.setText(null);
								if (smNumber.contains(textValue.getText())) {
									// 扫描重复
									textValue.setText(null);
									MediaPlug.scanRepeat();
								} else {
									CountDownPlug.resetCountDown();
									System.out.println("获取扫码值："+textValue.getText());
									smNumber.add(textValue.getText());
									data.add(new Number(String.valueOf(smNumber.size()), textValue.getText()));
									textValue.setText(null);
									MediaPlug.scanSuccess();
									Platform.runLater(() -> {
										ConfirmImageView.setVisible(true);
								});
								}
							} else {
								//扫描格式不正确
								if(!returnHome){
									qrCodeTF.setText(null);
//									MediaPlug.scanError();
								}else{
									returnHome = false;
								}
							}
							Platform.runLater(() -> {
								CountDownPlug.resetCountDown();
						});
						}
						return null;
					}
				};
				Thread listenThread = new Thread(listebTask);
				listenThread.setDaemon(true);
				listenThread.start();
			}

		});
		
		return vb;
	}

}
