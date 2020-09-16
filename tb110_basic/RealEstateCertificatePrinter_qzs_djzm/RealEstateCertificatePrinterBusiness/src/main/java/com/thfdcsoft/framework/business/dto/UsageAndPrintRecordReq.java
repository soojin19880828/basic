package com.thfdcsoft.framework.business.dto;


import com.thfdcsoft.framework.business.entity.PrintRecord;
import com.thfdcsoft.framework.business.entity.UsageRecord;

public class UsageAndPrintRecordReq {
    private UsageRecord usageRecord;
    private PrintRecord printRecord;

    public UsageRecord getUsageRecord() {
        return usageRecord;
    }

    public void setUsageRecord(UsageRecord usageRecord) {
        this.usageRecord = usageRecord;
    }

    public PrintRecord getPrintRecord() {
        return printRecord;
    }

    public void setPrintRecord(PrintRecord printRecord) {
        this.printRecord = printRecord;
    }

}
