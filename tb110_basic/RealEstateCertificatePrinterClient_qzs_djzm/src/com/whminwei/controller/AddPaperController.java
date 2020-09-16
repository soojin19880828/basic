package com.whminwei.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.whminwei.client.Main;
import com.whminwei.constant.Constant;
import com.whminwei.dto.req.ChkRemainingReq;
import com.whminwei.dto.req.SubmitRemainingReq;
import com.whminwei.dto.rspn.HttpReturnDto;
import com.whminwei.util.JacksonFactory;
import com.whminwei.util.http.BaseService;
import com.whminwei.util.http.impl.BaseServiceImpl;
import com.whminwei.view.MainView;
import com.whminwei.view.pages.LoadPanes;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

/**
 * @Author 罗子敬
 * @Description 加纸页面
 * @Date 2020/8/11 11:52
 * @Param
 * @return
 **/
public class AddPaperController implements Initializable {
	
	public static Logger logger = LoggerFactory.getLogger(AddPaperController.class);

    private BaseService baseService = new BaseServiceImpl();


    /**证明数量*/
    public Label paperNum;

    /**
     * 加1
     */
    public void add1() {
        add(1);
    }

    public void add2() {
        add(2);
    }

    public void add5() {
        add(5);
    }

    public void add10() {
        add(10);
    }


    public void add100() {
        add(100);
    }

    public void reduce1() {
        reduce(1);
    }

    public void reduce2() {
        reduce(2);
    }

    public void reduce5() {
        reduce(5);
    }

    public void reduce10() {
        reduce(10);
    }

    public void reduce100() {
        reduce(100);
    }
    
    /**
     * Description:证明数量添加
     * @param num
     * @Author 罗子敬  
     * @Date 2020年9月7日 下午5:18:20
     */
    private void add(int num) {
        String paperNumText = paperNum.getText();
        int papNum = Integer.parseInt(paperNumText);
        paperNum.setText(String.valueOf(papNum + num));
    }

    /**
     * Description:证明数量减少
     * @param num
     * @Author 罗子敬  
     * @Date 2020年9月7日 下午5:19:03
     */
    private void reduce(int num) {
        String paperNumText = paperNum.getText();
        int papNum = Integer.parseInt(paperNumText);
        papNum = papNum - num;
        if (papNum > 0) {
            paperNum.setText(String.valueOf(papNum));
        } else {
        	logger.error("----证明数量无法减成负数---");
        }
    }

    /**
     * Description:保存修改
     * @Author 罗子敬  
     * @Date 2020年9月7日 下午5:21:20
     */
    public void save() {
        setRemaining();
        MainView.paperRemaining = paperNum.getText();
        logger.info("MainView.remaining" + MainView.paperRemaining);
        try {
            FXMLLoader administrator = new FXMLLoader();
            administrator.setLocation(Main.class.getResource("fxml/AdministratorPane.fxml"));
            AnchorPane administratorBorderPane = administrator.load();
            MainView.border.setCenter(administratorBorderPane);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Description:取消修改
     * @Author 罗子敬  
     * @Date 2020年9月7日 下午5:21:00
     */
    public void cancel() {
        try {
            FXMLLoader administrator = new FXMLLoader();
            administrator.setLocation(Main.class.getResource("fxml/AdministratorPane.fxml"));
            AnchorPane administratorBorderPane = administrator.load();
            MainView.border.setCenter(administratorBorderPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        logger.info("初始化加证明页面");
        getRemaining();
    }

    /**
     * Description:查询设备证明数量
     * @Author 罗子敬 
     * @Date 2020年9月7日 下午5:19:38
     */
    public void getRemaining() {
        logger.info("获取证明数量，DEVICE_NUMBER->" + Constant.DeviceInfo.DEVICE_NUMBER);
        ChkRemainingReq req = new ChkRemainingReq();
		req.setDeviceNumber(Constant.DeviceInfo.DEVICE_NUMBER);
		req.setTerminalId(Constant.TerminalId.DJZM_PRINT);
        String reqJson = JacksonFactory.writeJson(req);
        try {
            String rspnJson = baseService.httpTrans(reqJson, Constant.Business.BUSINESS_URL + "chkRemaining");
            HttpReturnDto rspn = JacksonFactory.readJson(rspnJson, HttpReturnDto.class);
            logger.info("获取纸张数量，HttpReturnDto->" + rspn.toString());
            if (rspn.isResult()) {
                Platform.runLater(() -> {
                    paperNum.setText(rspn.getRespJson());
                });
            }
        } catch (Exception e) {
        	LoadPanes.showMessagePane("获取证明数量失败，请联系技术人员。", 5);
            e.printStackTrace();
        }
    }

    /**
     * Description:修改设备证明数量
     * @Author 罗子敬  
     * @Date 2020年9月7日 下午5:20:21
     */
    public void setRemaining() {
        logger.info("---修改设备剩余证明数量---");
        SubmitRemainingReq req = new SubmitRemainingReq();
        req.setDeviceNumber(Constant.DeviceInfo.DEVICE_NUMBER);
        req.setRemaining(paperNum.getText());
		req.setTerminalId(Constant.TerminalId.DJZM_PRINT);
        String reqJson = JacksonFactory.writeJson(req);
        try {
            String rspnJson = baseService.httpTrans(reqJson, Constant.Business.BUSINESS_URL + "submitRemaining");
            HttpReturnDto rspn = JacksonFactory.readJson(rspnJson, HttpReturnDto.class);
            if (rspn.isResult()) {
                Platform.runLater(() -> {
                    MainView.paperRemaining = paperNum.getText();
                    logger.info("MainView.remaining" + MainView.paperRemaining);
                });
            }
        } catch (Exception e) {
        	LoadPanes.showMessagePane("修改证明数量失败，请联系技术人员。", 5);
            e.printStackTrace();
        }

    }


}
