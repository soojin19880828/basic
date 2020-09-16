package com.whminwei.controller.param;


import java.util.List;


public class LEDMDeviceStatus {
    private String Status;
    private List<String> Alerts;
    
	public String getStatus() {
		return Status;
	}
	public void setStatus(String status) {
		Status = status;
	}
	public List<String> getAlerts() {
		return Alerts;
	}
	public void setAlerts(List<String> alerts) {
		Alerts = alerts;
	}
    
    
}
