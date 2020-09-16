package com.whminwei.controller;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.io.FileInputStream;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.whminwei.view.MainView;
import com.whminwei.view.pages.IdReadInformation;
import com.whminwei.view.pages.LoadPanes;

public class IdCardInfoController{
	
	public static Logger logger = LoggerFactory.getLogger(IdCardInfoController.class);
    
    public Label name;
    public Label sex;
    public Label nation;
    public Label idNumber;
    public ImageView idPic;
    

	public void initialize() {
		logger.info("in init idInformation id: {}",MainView.idInformation.getIdNumber());
		resetId();
		name.setText(MainView.idInformation.getFullName());
		sex.setText(MainView.idInformation.getSex());
		nation.setText(MainView.idInformation.getNation());
		idNumber.setText(MainView.idInformation.getIdNumber());
		String idPicPath = MainView.idInformation.getIdPicPath();
		FileInputStream idFis = null;
		try {
			idFis = new FileInputStream(idPicPath);
			Image idImg = new Image(idFis);
			idPic.setImage(idImg);
			idFis.close();
		} catch (IOException e) {
			logger.error("身份证图片转换失败...");
			e.printStackTrace();
		}
	}

	public void nextStep(MouseEvent mouseEvent) {
		// 跳转到人脸识别界面
		new LoadPanes().getFaceRecognitionPane();
	}

	/**
	 * 重置身份信息
	 */
	public void resetId() {
		logger.info("重置身份信息...");
		name.setText("XXX");
		sex.setText("X");
		nation.setText("X");
		idNumber.setText("XXXXXXXXXXXXXXXXXX");
		idPic.setImage(null);
	}
}
