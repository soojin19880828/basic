package com.whminwei.controller.param;

import java.util.List;


public class LEDMDeviceStatusResp {
    private List<LEDMDeviceStatus> statusList;

    public List<LEDMDeviceStatus> getStatusList() {
        return statusList;
    }

    public void setStatusList(List<LEDMDeviceStatus> statusList) {
        this.statusList = statusList;
    }
}
