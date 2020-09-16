package com.whminwei.controller.param;

import java.util.List;

public class LEDMDeviceResp {
    private List<LEDMDeviceInfo> printers;

    public LEDMDeviceResp() {
    }

    public LEDMDeviceResp(List<LEDMDeviceInfo> printers) {
        this.printers = printers;
    }

    public List<LEDMDeviceInfo> getPrinters() {
        return printers;
    }

    public void setPrinters(List<LEDMDeviceInfo> printers) {
        this.printers = printers;
    }
}
