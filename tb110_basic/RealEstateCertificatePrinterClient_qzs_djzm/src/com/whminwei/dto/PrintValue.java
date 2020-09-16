package com.whminwei.dto;

public class PrintValue {
	
	private String value;
	
	private PrintProp prop;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public PrintProp getProp() {
		return prop;
	}

	public void setProp(PrintProp prop) {
		this.prop = prop;
	}

	@Override
	public String toString() {
		return "PrintValue [value=" + value + ", prop=" + prop + "]";
	}

	
}
