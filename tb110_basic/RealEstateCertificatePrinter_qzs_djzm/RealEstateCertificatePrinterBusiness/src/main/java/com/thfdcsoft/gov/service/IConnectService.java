package com.thfdcsoft.gov.service;

import com.thfdcsoft.gov.dto.qzs.HandleResult;
import com.thfdcsoft.framework.business.entity.PrintRecord;
import com.thfdcsoft.framework.business.entity.UsageRecord;
import com.thfdcsoft.gov.dto.djzm.QueryEstateRspn;

import java.io.File;
import java.util.List;

/**
 * @author 高拓
 * @title: IConnectService
 * @projectName RealEstateCertificatePrinter_qzs_djzm
 * @description: TODO
 * @date 2020/9/10 10:35
 */
public interface IConnectService {

    /**
     * 通知不动产系统，并向manage请求更新回传状态
     *
     * @param printRecord 打印记录
     * @param usage 使用记录
     * @return
     */
    Integer notifyAndUpdate(PrintRecord printRecord, UsageRecord usage);

    /**
     * 获取不动产信息列表
     */
    HandleResult getRealEstateListByIdNumberAndName(UsageRecord usageRecord);

    /**
     * 通知不动产
     * @param printRecord
     * @param usage
     * @return
     */
    boolean notification(PrintRecord printRecord, UsageRecord usage);

    /**
     * 登记证明信息查询
     * @param yjm
     * @param idNumber
     * @param recordFile
     * @return
     */
    public QueryEstateRspn getRealEstateListByIdNumber(String yjm, String idNumber, File recordFile);

    /**
     * 登记证明打印完成
     * @param usage
     * @param completes
     * @param recordFile
     */
    public void printComplete(UsageRecord usage, List<PrintRecord> completes, File recordFile);

}
