package com.whminwei.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.whminwei.constant.Constant;

import javafx.application.Platform;
import javafx.concurrent.Task;

/**
 * @author ww
 * @date 2019/5/23 17:35
 */
public class CountDownUtils {
	
	private static final Logger logger = LoggerFactory.getLogger(CountDownUtils.class);

    private static boolean countDownStart = false;

    private static Integer countDownNum = 18;

    private static int countType = 0;

    public static Thread thread;
    /**
     * 启动倒计时
     */
    public static void startCountDownCloseConnect() {
//        if(thread != null && !thread.isInterrupted()){
//            thread.interrupt();
//        }
        if (countDownStart) {
            resetCountDown();
        } else {
            countDownStart = true;
            Task<Void> task = new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    while (countDownNum >= 0 && countDownStart) {
                        countDownNum--;
                        Thread.sleep(1000);
                    }
                    // 倒计时正常结束关闭连接
                    if (countDownStart) {
                        countDownStart = false;
                        if (countType == 0) {
                            Platform.runLater(() -> {
                                HttpClientFactory.closeClient();
                                logger.info("18s闭连接");
                            });
                        } else {
                            Platform.exit();
                        }
                    }
                    return null;
                }
            };
            thread = new Thread(task);
            thread.setDaemon(true);
            thread.start();
        }
    }

    /**
     * 停止倒计时
     */
    public static void stopCountDown() {
        countDownStart = false;
        countDownNum = Constant.StartMode.COUND_DOWN;
    }

    /**
     * 重置倒计时
     */
    private static void resetCountDown() {
        countDownNum = 18;
    }

    /**
     * 开始倒计时
     *
     * @param num
     */
    public static void startCountDownCloseConnect(int num) {
        countDownNum = num;
        countType = 0;
        startCountDownCloseConnect();
    }

    /**
     * 开始倒计时
     *
     * @param num
     * @param type
     *            0:倒计时结束后返回首页|1:倒计时结束后退出程序
     */
    public static void startCountDownCloseConnect(int num, int type) {
        countDownNum = num;
        countType = type;
        startCountDownCloseConnect();
    }
}
