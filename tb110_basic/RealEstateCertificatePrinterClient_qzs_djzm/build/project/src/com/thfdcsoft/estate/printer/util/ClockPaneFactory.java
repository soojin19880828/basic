package com.thfdcsoft.estate.printer.util;

import java.util.Calendar;
import java.util.GregorianCalendar;

import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class ClockPaneFactory extends Pane {
	private int year;
	private int month;
	private int day;
	private int hour;
	private int minute;

	// 构造函数
	public ClockPaneFactory() {
		setCurrentTime();
	}

	// 设置带参数的构造函数
	public ClockPaneFactory(int year,int month, int day, int hour, int minute) {
		super();
		this.year = year;
		this.month = month;
		this.day = day;
		this.hour = hour;
		this.minute = minute;
		paintClock();

	}
	
	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
		paintClock();
	}

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
		paintClock();
	}

	public int getHour() {
		return hour;
	}

	public void setHour(int hour) {
		this.hour = hour;
		paintClock();
	}

	public int getMinute() {
		return minute;
	}

	public void setMinute(int minute) {
		this.minute = minute;
		paintClock();
	}

	

	public void setCurrentTime() {
		// 获取当前时间
		Calendar calendar = new GregorianCalendar();

		// 设置当前时间
		this.year = calendar.get(Calendar.YEAR);
		this.month = calendar.get(Calendar.MONTH) + 1;
		this.day = calendar.get(Calendar.DATE);
		this.hour = calendar.get(Calendar.HOUR_OF_DAY);
		this.minute = calendar.get(Calendar.MINUTE);

		// 重画
		paintClock();
	}

	public void paintClock() {
		String endMinute = "";
		String endHour = "";
		String timeString = "";
		if (hour < 10) {
			endHour = "0" + hour;
		}else{
			endHour = "" + hour;
		}
		if (minute < 10) {
			endMinute = "0" + minute;
		}else{
			endMinute = "" + minute;
		}
		timeString = endHour + ":" + endMinute;
		Text time = new Text(timeString);
		time.getStyleClass().add("time");
		time.setTranslateX(210);

		// 显示当前时间,并设置时间显示的位置
		timeString = year + "年" +  month + "月" + day + "日";
		Text currentTime = new Text(timeString);
		currentTime.getStyleClass().add("currentTime");
		currentTime.setTranslateX(-50);

		getChildren().clear();
		getChildren().addAll(time,currentTime);
	}

}
