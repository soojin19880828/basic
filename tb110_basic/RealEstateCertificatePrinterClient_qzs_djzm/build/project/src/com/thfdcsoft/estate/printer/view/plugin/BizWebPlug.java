package com.thfdcsoft.estate.printer.view.plugin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.thfdcsoft.estate.printer.constant.Constant;
import com.thfdcsoft.estate.printer.view.MainView;
import com.thfdcsoft.estate.printer.view.script.ClientScript;

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
//		browser.getStyleClass().add("browser-box");
		WebEngine web = browser.getEngine();
		ClientScript clientScript = new ClientScript();
		web.load(Constant.Business.BUSINESS_URL);
		web.getLoadWorker().stateProperty().addListener(new ChangeListener<State>() {
			@Override
			public void changed(ObservableValue<? extends State> observable, State oldState, State newState) {
				if (newState == Worker.State.SUCCEEDED) {
					JSObject win = (JSObject) web.executeScript("window");
					win.setMember("clientScript", clientScript);
					
					MainView.border.setCenter(MainView.web);
					logger.info(MainView.web.getEngine().getLocation());
					
					String indexURL = Constant.Business.BUSINESS_URL + "index";
					if (indexURL.equals(MainView.web.getEngine().getLocation())) {
						MediaPlug.welcome();
						MainView.usageId = null;
					}
					
					String printURL = Constant.Business.BUSINESS_URL + "client/printSuccess";
					if (printURL.equals(MainView.web.getEngine().getLocation())) {
						MainView.web.getEngine().executeScript(
								"getNum('" + ClientScript.num + "')");
					}
					
					// 登记证明打印请求监听
					String djzmPrintURL = Constant.Business.BUSINESS_URL + "client/print";
					if (djzmPrintURL.equals(MainView.web.getEngine().getLocation())) {
						MainView.web.getEngine().executeScript(
								"getEstateList('" + MainView.usageId + "')");
					}
				}
			}
		});
		return browser;
	}
}
