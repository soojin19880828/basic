package com.whminwei.util;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class ClockPaneFactory extends Pane {
	private int year;
	private int month;
	private int day;
	private int hour;
	private int minute;
	private String week;

	// 构造函数
	public ClockPaneFactory() {
		setCurrentTime();
	}

	// 设置带参数的构造函数
	
	
	public int getYear() {
		return year;
	}

	public ClockPaneFactory(int year, int month, int day, int hour, int minute, String week) {
		super();
		this.year = year;
		this.month = month;
		this.day = day;
		this.hour = hour;
		this.minute = minute;
		this.week = week;
		paintClock();
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

	public String getWeek() {
		return week;
	}

	public void setWeek(String week) {
		this.week = week;
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
		this.week = DateFactory.getWeekOfDate(new Date());

		// 重画
		paintClock();
	}

	/*public void paintClock() {
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
	}*/
	
	public void paintClock() {
		String timeHour = "";
		String timeMinute = "";
		if (hour < 10) {
			timeHour = "0" + hour;
		} else {
			timeHour = "" + hour;
		}
		if (minute < 10) {
			timeMinute = "0" + minute;
		} else {
			timeMinute = "" + minute;
		}
		// 时分
		String hourAndMinute = timeHour + ":" + timeMinute;
		HBox hourAndMinuteHbox = new HBox();
		Text hourAndMinuteText = new Text(hourAndMinute);
		hourAndMinuteText.getStyleClass().add("hourAndMinuteText");
		hourAndMinuteHbox.getChildren().add(hourAndMinuteText);

		// 日期
		HBox dayHbox = new HBox();
		Text dayText = new Text(month + "月" + day + "日");
		dayText.getStyleClass().add("day_text");
		dayHbox.getChildren().add(dayText);

		// 周
		HBox weekHbox = new HBox();
		Text weekText = new Text(week);
		weekText.getStyleClass().add("week_text");
		weekHbox.getChildren().add(weekText);

		VBox timeVbox = new VBox();
		timeVbox.getStyleClass().add("time_vbox");
		timeVbox.getChildren().addAll(weekHbox,dayHbox);

		getChildren().clear();
		getChildren().addAll(hourAndMinuteHbox,timeVbox);

	}

}
