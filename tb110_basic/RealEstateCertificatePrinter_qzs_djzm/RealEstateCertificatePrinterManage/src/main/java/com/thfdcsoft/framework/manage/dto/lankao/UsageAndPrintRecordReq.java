package com.thfdcsoft.framework.manage.dto.lankao;

import com.thfdcsoft.framework.manage.entity.PrintRecord;
import com.thfdcsoft.framework.manage.entity.UsageRecord;

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
