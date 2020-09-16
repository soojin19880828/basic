package com.whminwei.controller;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.scene.web.WebEngine;

import static com.whminwei.view.MainView.*;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.whminwei.client.Main;
import com.whminwei.constant.Constant;
import com.whminwei.dto.TerminalInfo;
import com.whminwei.dto.req.ChkRemainingReq;
import com.whminwei.dto.rspn.HttpReturnDto;
import com.whminwei.util.DateFactory;
import com.whminwei.util.JacksonFactory;
import com.whminwei.util.StringUtils;
import com.whminwei.util.http.BaseService;
import com.whminwei.util.http.impl.BaseServiceImpl;
import com.whminwei.view.MainView;
import com.whminwei.view.pages.LoadPanes;
import com.whminwei.view.plugin.CountDownPlug;
import com.whminwei.view.plugin.MediaPlug;
import com.whminwei.view.script.ClientScript;

/**
 * 首页
 */
public class IndexController implements Initializable {
	private static final Logger logger = LoggerFactory.getLogger(IndexController.class);
	
    /**
     * @Description  点击次数
     * @Date 2020/8/31 8:38
     * @Author 罗子敬
     **/
    public static Integer result = 0;
    /**
     * @Description  进入管理员页面需要点击的次数
     * @Date 2020/8/31 8:46
     * @Author 罗子敬
     **/
    private Integer SUCCESS_RESULT = 10;
    /**
     * 时间
     * @Author 罗子敬
     * @Date 2020/9/1 16:05
     **/
    public Text time;
    /**
     * 年月日
     * @Author 罗子敬
     * @Date 2020/9/1 16:05
     **/
    public Text year;
    /**
     * 星期
     * @Author 罗子敬
     * @Date 2020/9/1 16:06
     **/
    public Text sunday;
  
    /**证明数量*/
    public Label paperNum;
    
    /**证书数量*/
    public Label certificateNum;
    
	public void setPaperNum(String num) {
		paperNum.setText(num);
	}

	public void setCertificateNum(String num) {
		certificateNum.setText(num);
	}

	/**
     * Description: 首页FX初始化加载显示时间日期
     * @Author 罗子敬  
     * @Date 2020年9月2日 上午10:47:58
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    	logger.info("初始化首页时间");
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() {
                while (true) {
                    try {
                        Platform.runLater(() -> {
                            time.setText(DateFactory.getCurrentDateString("HH:mm"));
                            sunday.setText(DateFactory.getCurrentDateString("EEEE"));
                            year.setText(DateFactory.getCurrentDateString("yyyy年MM月dd日"));
                        });
                        Thread.sleep(10000);
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
    
    /**
     * Description:权证书类型打印跳转
     * @Author 高拓  
     * @Date 2020年9月2日 下午1:57:46
     */
    public void qzsType() {
    	ClientScript.search("qzs");
    }
    
    /**
     * Description:登记证明类型打印跳转
     * @Author 高拓  
     * @Date 2020年9月2日 下午1:58:32
     */
    public void djzmType() {
    	ClientScript.search("djzm");
    }
    
    /**
     * @Description  管理员页面
     * @Date 2020/8/31 8:38
     * @Param [mouseEvent]
     * @return void
     * @Author 罗子敬
     **/
    public void admin(MouseEvent mouseEvent) {
        result++;
        System.out.println("result--------------"+result);
        if (result>=SUCCESS_RESULT) {
            result = 0;
            Platform.runLater(() -> {
				// 启动倒计时
				CountDownPlug.startCountDown();
				new LoadPanes().getAdminPane();
			});
        }
    }
    
    

}
