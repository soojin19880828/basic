package com.whminwei.controller;


import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.DataFormat;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.web.WebEngine;
import sun.applet.Main;

import javax.swing.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.whminwei.util.DateFactory;
import com.whminwei.view.MainView;
import com.whminwei.view.script.ClientScript;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.Timer;


public class TopController implements Initializable {

	public static Logger logger = LoggerFactory.getLogger(ReadIdCardController.class);
	
    public ImageView returnBack;
    public ImageView returnHome;
    //时间
    public Label time;
    //星期
    public Label weekday;
    //月
    public Label mouth;
    //倒计时
    public Label counDown;

    public static Integer countDownNum = 99;

    public static boolean countDownStart = false;

    public static int num = 0;

    //返回首页
    public void home(MouseEvent mouseEvent) {
    	MainView.home();
    }

    //返回上一步
    public void back(MouseEvent mouseEvent) {
        MainView.home();
    }

    //时间
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    	logger.info("开始时间");
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() {
                while (true) {
                    try {
                        Platform.runLater(() -> {
                            time.setText(DateFactory.getCurrentDateString("HH:mm"));
                            weekday.setText(DateFactory.getCurrentDateString("EEEE"));
                            mouth.setText(DateFactory.getCurrentDateString("MM月dd日"));
                        });
                        Thread.sleep(60000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
    }

    public Timer timer = new Timer(false);

    public void startCounDown() {
    	logger.info("倒计时" + countDownStart);
        MainView.topController.counDown.setVisible(true);
        logger.info(timer.toString());
        timer.cancel();
        timer = new Timer();
        countDownStart = true;
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
//                log.info(String.valueOf(countDownNum--));
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        try {
//                            log.info("countDownNum" + countDownNum);
                            if (countDownNum >= 0 && countDownStart) {
                                counDown.setText(String.valueOf(countDownNum));
                                countDownNum--;
                            }
                            // 倒计时正常结束返回首页
                            if (countDownStart && countDownNum == 0) {
//                                countDownStart = false;
//                                timer.cancel();
                                MainView.home();

                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            logger.error(e.getMessage());
                        }
                    }
                });
            }
        }, 100, 1000);

    }

    public static void resetCountDown() {
        countDownNum = 99;
    }

    public static void resetCountDown(int num) {
        countDownNum = num;
    }

    public static void stopCountDown() {
    	logger.info("停止倒计时");
        //隐藏属性
        MainView.topController.counDown.setVisible(false);
        countDownStart = false;
    }

    public static void startCountDown() {
        MainView.topController.counDown.setVisible(true);
        countDownNum = 99;
        countDownStart = true;
    }

    public static void startCountDown(int num) {
        MainView.topController.counDown.setVisible(true);
        countDownNum = num;
        countDownStart = true;
    }

    /**
     * 显示返回首页按钮
     */
    public void showReturnHome() {
        returnHome.setVisible(true);
    }

    /**
     * 显示返回首页按钮
     */
    public void hiddenReturnHome() {
        returnHome.setVisible(false);
    }


    /**
     * 进入管理员页面
     */
    public void admin() {
        Platform.runLater(() -> {
            try {
                showReturnHome();
                startCounDown();
                MainView.border.getStyleClass().add("index-bg");
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(Main.class.getResource("fxml/InPutPassword.fxml"));
                BorderPane borderPane = loader.load();
                MainView.border.setTop(MainView.topAnchorPane);
                MainView.border.setCenter(borderPane);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });


    }
}

