package com.whminwei.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.whminwei.client.Main;
import com.whminwei.view.MainView;

public class InPutPassWordController {
	private static final Logger logger = LoggerFactory.getLogger(InPutPassWordController.class);
    @FXML
    private TextField ScanTextField;

    @FXML
    private Text hintScan;

    /**
     * 返回首页
     */
    public void backHome() {
    	logger.info("人脸识别页面返回首页");
//        ClientScript.home();
    }

    public void sure() {
        if (ScanTextField.getText().trim() == null || "".equals(ScanTextField.getText().trim())) {
            hintScan.setText("请输入密码！！！");
        } else {
            if (ScanTextField.getText().trim().equals("123456")) {
                try {
                    FXMLLoader administrator = new FXMLLoader();
                    administrator.setLocation(Main.class.getResource("fxml/AdministratorPane.fxml"));
                    AnchorPane administratorBorderPane = administrator.load();
                    MainView.border.setCenter(administratorBorderPane);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                hintScan.setText("密码错误！！！");
            }

        }

    }

    public TextField getScanTextField() {
        return ScanTextField;
    }

    public void setScanTextField(TextField scanTextField) {
        ScanTextField = scanTextField;
    }

}
