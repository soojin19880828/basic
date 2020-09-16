package com.thfdcsoft.estate.printer.dto;

public class TextStruct {

	public String text;

	public Float x;

	public Float y;

	public String fontFamily;

	public Integer fontSize;

	public Float maxWidth;

	public Integer fontWeight;

	public TextStruct(String text, Float x, Float y, String fontFamily, Integer fontSize, Float maxWidth,
			Integer fontWeight) {
		this.text = text;
		this.x = x;
		this.y = y;
		this.fontFamily = fontFamily;
		this.fontSize = fontSize;
		this.maxWidth = maxWidth;
		this.fontWeight = fontWeight;
	}

	@Override
	public String toString() {
		return "TextStruct [text=" + text + ", x=" + x + ", y=" + y + ", fontFamily=" + fontFamily + ", fontSize="
				+ fontSize + ", maxWidth=" + maxWidth + ", fontWeight=" + fontWeight + "]";
	}
	
}
