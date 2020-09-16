package com.whminwei.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.whminwei.httpReq.HardWareHttp;

import javafx.concurrent.Task;

/**
 * 读卡页面
 */
public class ReadIdCardController {

	public static Logger logger = LoggerFactory.getLogger(ReadIdCardController.class);
	
	/**
	 * 发送读卡请求
	 */
	public void initialize() {
		Task<Void> task = new Task<Void>() {
			@Override
			protected Void call() throws Exception {
				logger.info("发送读卡请求...");
				new HardWareHttp().getIdCardInfo();
				return null;
			}
		};
		Thread thread = new Thread(task);
		thread.setDaemon(true);
		thread.start();
	}
}
