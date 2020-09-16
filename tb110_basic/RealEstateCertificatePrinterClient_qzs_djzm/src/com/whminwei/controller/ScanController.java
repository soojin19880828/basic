package com.whminwei.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ScanController implements Initializable {
	
	public static Logger logger = LoggerFactory.getLogger(ScanController.class);
	
    /**
     * 回执单列表
     */
    public AnchorPane certList;

    public ObservableList<String> strList;

    /**
     * 查询
     * @param mouseEvent
     */
    public void search(MouseEvent mouseEvent) {
        //模拟数据
        String str = "";
        for(int i=0;i<20;i++){
            str+=String.valueOf((int)(Math.random()*9));
        }
        strList.add(str);
        logger.info("---查询---");
    }


    /**
     * 初始化扫码头
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //模拟给list塞数据
        strList = FXCollections.observableArrayList();
        ListView<String> listView = new ListView<>(strList);
        listView.setItems(strList);
        listView.setStyle("-fx-font-size: 24px");
        listView.setPrefSize(500, 250);
        certList.getChildren().add(listView);
    }
}
