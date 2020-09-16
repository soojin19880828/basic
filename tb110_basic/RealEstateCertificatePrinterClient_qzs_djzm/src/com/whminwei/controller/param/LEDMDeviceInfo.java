package com.whminwei.controller.param;

/**
 * 打印机信息
 */
public class LEDMDeviceInfo {
    private String printerName;
    private String driverName;
    private String portName;
    private boolean online;

    public LEDMDeviceInfo() {
    }

    public LEDMDeviceInfo(String printerName, String driverName, String portName, boolean online) {
        this.printerName = printerName;
        this.driverName = driverName;
        this.portName = portName;
        this.online = online;
    }

    public String getPrinterName() {
        return printerName;
    }

    public void setPrinterName(String printerName) {
        this.printerName = printerName;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getPortName() {
        return portName;
    }

    public void setPortName(String portName) {
        this.portName = portName;
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }
}
