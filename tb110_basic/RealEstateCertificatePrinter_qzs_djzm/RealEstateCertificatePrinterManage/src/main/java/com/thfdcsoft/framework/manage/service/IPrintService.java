package com.thfdcsoft.framework.manage.service;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.thfdcsoft.framework.manage.entity.PrintRecord;
import com.thfdcsoft.framework.manage.entity.PrintRecordExample;
import com.thfdcsoft.framework.manage.entity.TerminalInfo;
import com.thfdcsoft.framework.manage.entity.UsageRecord;

import java.util.List;

/**
 * @author ww
 * @date 2019/5/17 15:59
 */
public interface IPrintService {

    /**
     * 重传给不动产系统（经过business）
     *
     * @return
     */
    Boolean resend2EstateSystem(PrintRecord printRecord, UsageRecord usage);

    int insertUsageRecordSelective(UsageRecord usageRecord);

    int insertPrintRecordSelective(PrintRecord printRecord);

    UsageRecord selectUsageRecordByUsageId(String usageId);

    PrintRecord selectPrintRecordByPrintId(String printId);

    List<PrintRecord> selectPrintRecordsByPrintRecordExample(PrintRecordExample example);

    PageList<PrintRecord> selectPrintRecordsByPrintRecordExample(PrintRecordExample example, PageBounds pageBounds);

    Long countByPrintRecordExample(PrintRecordExample example);

    int updatePrintRecordByPrintIdSelective(PrintRecord printRecord);

    TerminalInfo selectByDeployNumber(String deviceNumber);

    List<TerminalInfo>  getTerminalListByDeployNumber(String deviceNumber);

}
