package com.thfdcsoft.estate.printer.view.plugin;

import java.util.Timer;
import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.thfdcsoft.estate.printer.constant.Constant;
import com.thfdcsoft.estate.printer.view.MainView;
import com.thfdcsoft.estate.printer.view.script.ClientScript;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.text.Text;

/**
 * 倒计时组件
 * 
 * @author 张嘉琪
 *
 */
public class CountDownPlug {
	
	private static final Logger logger = LoggerFactory.getLogger(CountDownPlug.class);

	public static boolean countDownStart = false;

	public static Integer countDownNum = Constant.StartMode.COUND_DOWN;

	public static Text countDown = new Text();

	private static int countType = 0;
	
	/**
	 * 获取倒计时组件
	 * 
	 * @return
	 */
	public static Text getCountDownText() {
		return countDown;
	}

	
	public static Thread thread;
	/**
	 * 启动倒计时
	 */
	/*public static void startCountDown() {
		if(thread != null && !thread.isInterrupted()){
			thread.interrupt();
		}
		logger.info("倒计时countDownStart="+countDownStart);
		if (countDownStart) {
			resetCountDown();
		} else {
			countDownStart = true;
			Task<Void> task = new Task<Void>() {
				@Override
				protected Void call() throws Exception {
					while (countDownNum >= 0 && countDownStart) {
						countDown.setText(String.valueOf(countDownNum));
						countDownNum--;
						Thread.sleep(1000);
					}
					// 倒计时正常结束返回首页
					if (countDownStart) {
						countDownStart = false;
						if (countType == 0) {
							Platform.runLater(() -> {
								MainView.home();
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
	}*/

	/**
	 * 停止倒计时
	 */
	public static void stopCountDown() {
		countDownStart = false;
		countDownNum = Constant.StartMode.COUND_DOWN;
		countDown.setText("");
	}

	/**
	 * 重置倒计时
	 */
	public static void resetCountDown() {
		countDownNum = Constant.StartMode.COUND_DOWN;
	}

	/**
	 * 开始倒计时
	 * 
	 * @param num
	 */
	public static void startCountDown(int num) {
		countDownNum = num;
		countType = 0;
		startCountDown();
	}

	/**
	 * 开始倒计时
	 * 
	 * @param num
	 * @param type
	 *            0:倒计时结束后返回首页|1:倒计时结束后退出程序
	 */
	public static void startCountDown(int num, int type) {
		countDownNum = num;
		countType = type;
		startCountDown();
	}
	
	public static Timer timer = new Timer(false);

    public static void startCountDown() {
        logger.info("倒计时" + countDownStart);
        if (!countDownStart) {
            resetCountDown();
        }
        timer.cancel();
        timer = new Timer();
        countDownStart = true;
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        try {
//                            log.info("countDownNum" + countDownNum);
                            if (countDownNum >= 0 && countDownStart) {
                            	countDown.setText(String.valueOf(countDownNum));
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
                        }
                    }
                });
            }
        }, 100, 1000);

    }
	
}
