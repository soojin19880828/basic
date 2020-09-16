package com.whminwei.view.plugin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.whminwei.constant.Constant;
import com.whminwei.view.MainView;
import com.whminwei.view.pages.LoadPanes;
import com.whminwei.view.script.ClientScript;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.concurrent.Worker.State;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import netscape.javascript.JSObject;;

/**
 * 登记证明打印功能页面
 * @author 张嘉琪
 *
 */
public class BizWebPlug {

	private static final Logger logger = LoggerFactory.getLogger(BizWebPlug.class);
	
	public static WebView index() {
		WebView browser = new WebView();
		WebEngine web = browser.getEngine();
		ClientScript clientScript = new ClientScript();
		// 对页面地址跳转进行监听
		web.getLoadWorker().stateProperty().addListener(new ChangeListener<State>() {
			@Override
			public void changed(ObservableValue<? extends State> observable, State oldState, State newState) {
				System.out.println(newState);
				if (newState == State.READY){
					System.out.println(":过渡页面");
					MainView.transitPageController.changeMessage("数据正在加载中......");
					MainView.border.setCenter(MainView.transitPage);
				}
				if (newState == State.SUCCEEDED) {
					JSObject win = (JSObject) web.executeScript("window");
					System.out.println("加载clientScript");
					win.setMember("clientScript", clientScript);
					System.out.println("加载加载clientScript完成");
					String indexURL = Constant.Business.BUSINESS_URL + "index";
					if (indexURL.equals(MainView.web.getEngine().getLocation())) {
						MainView.usageId = null;
					}
					
					String printURL = Constant.Business.BUSINESS_URL + "client/print";
					if (MainView.web.getEngine().getLocation().startsWith(printURL)) {
						MainView.topController.startCountDown();
						MainView.topController.showReturnHome();
						MainView.border.setCenter(MainView.web);
					}
					
					String certificatePrintURL = Constant.Business.BUSINESS_URL + "client/queryhouse";
					if (MainView.web.getEngine().getLocation().startsWith(certificatePrintURL)) {
						MainView.topController.startCountDown();
						MainView.topController.showReturnHome();
						MainView.border.setCenter(MainView.web);
					}
				}
				if (newState == State.FAILED) {
					MainView.border.setTop(MainView.topAnchorPane);
					MainView.transitPageController.changeMessage("程序服务未启动，请先启动服务......");
					MainView.border.setCenter(MainView.transitPage);
				}
			}
		});
		return browser;
	}
}
