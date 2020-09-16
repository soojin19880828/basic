package com.thfdcsoft.framework.manage.service.impl;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.thfdcsoft.framework.common.http.def.DefHttpClientFactory;
import com.thfdcsoft.framework.common.json.jackson.JacksonFactory;
import com.thfdcsoft.framework.manage.constant.StaticConstant;
import com.thfdcsoft.framework.manage.dao.PrintRecordMapper;
import com.thfdcsoft.framework.manage.dao.TerminalInfoMapper;
import com.thfdcsoft.framework.manage.dao.UsageRecordMapper;
import com.thfdcsoft.framework.manage.entity.*;
import com.thfdcsoft.framework.manage.service.IPrintService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

/**
 * @author ww
 * @date 2019/5/17 15:59
 */
@Service
@Transactional
public class PrintServiceImpl implements IPrintService {

    private static final Logger logger = LoggerFactory.getLogger(PrintServiceImpl.class);

    @Autowired
    private TerminalInfoMapper terminalInfoMapper;

    @Autowired
    private UsageRecordMapper usageRecordMapper;

    @Autowired
    private PrintRecordMapper printRecordMapper;

    /**
     * 重新回传
     * @param printRecord
     * @param usage
     * @return
     */
    @Override
    public Boolean resend2EstateSystem(PrintRecord printRecord, UsageRecord usage) {
        logger.debug("【重新回传】");

        String url = StaticConstant.BUSINESS_URL + "/manage/resend?";

        try {
            String param = "userFullname=" + URLEncoder.encode(usage.getUserFullname(), "utf-8");
            param += "&userIdNumber=" + URLEncoder.encode(usage.getUserIdnumber(), "utf-8");
            param += "&certNumber=" + URLEncoder.encode(printRecord.getCertNumber(), "utf-8");
            param += "&bizNumber=" + URLEncoder.encode(printRecord.getBizNumber(), "utf-8");
            param += "&ocr=" + URLEncoder.encode(printRecord.getSeqNumber(), "utf-8");
            url += param;
            String state = DefHttpClientFactory.getInstance().doGet(url);
            logger.debug("【重新回传】，返回的数据：{}", state);
            return Boolean.parseBoolean(state);
        } catch (KeyManagementException | NoSuchAlgorithmException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public int insertUsageRecordSelective(UsageRecord usageRecord) {
        return usageRecordMapper.insertSelective(usageRecord);
    }

    @Override
    public int insertPrintRecordSelective(PrintRecord printRecord) {
        return printRecordMapper.insertSelective(printRecord);
    }

    @Override
    public UsageRecord selectUsageRecordByUsageId(String UsageId) {
        return usageRecordMapper.selectByPrimaryKey(UsageId);
    }

    @Override
    public PrintRecord selectPrintRecordByPrintId(String printId) {
        return printRecordMapper.selectByPrimaryKey(printId);
    }

    @Override
    public List<PrintRecord> selectPrintRecordsByPrintRecordExample(PrintRecordExample example) {
        return printRecordMapper.selectByExample(example);
    }

    @Override
    public PageList<PrintRecord> selectPrintRecordsByPrintRecordExample(PrintRecordExample example, PageBounds pageBounds) {
        return printRecordMapper.selectByExample(example, pageBounds);
    }

    @Override
    public Long countByPrintRecordExample(PrintRecordExample example) {
        return printRecordMapper.countByExample(example);
    }

    @Override
    public int updatePrintRecordByPrintIdSelective(PrintRecord printRecord) {
        return printRecordMapper.updateByPrimaryKeySelective(printRecord);
    }

    @Override
    public TerminalInfo selectByDeployNumber(String deviceNumber) {
        return terminalInfoMapper.selectByDeployNumber(deviceNumber);
    }

    @Override
    public List<TerminalInfo> getTerminalListByDeployNumber(String deviceNumber) {
        TerminalInfoExample terminalInfoExample = new TerminalInfoExample();
        TerminalInfoExample.Criteria criteria = terminalInfoExample.createCriteria();
        criteria.andDeployNumberEqualTo(deviceNumber);
        List<TerminalInfo> terminalInfoList = terminalInfoMapper.selectByExample(terminalInfoExample);
        return terminalInfoList;
    }

    /**
     * 请求Business
     *
     * @author ww
     * @date 2019年5月17日 16:00:12
     * @param url 请求地址(方法内已加StaticConstant.BUSINESS_URL)
     * @param requestJson 请求的json字符串
     * @return manage返回的数据(若为null则代表发生异常)
     */
    private String post2Business(String url, String requestJson) {
        try {
            return DefHttpClientFactory.getInstance().doPost(requestJson, StaticConstant.BUSINESS_URL + url);
        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 请求Business并转换为指定类型
     *
     * @author ww
     * @date 2019年5月17日 16:00:17
     * @param url 请求地址(方法内已加StaticConstant.BUSINESS_URL)
     * @param requestJson 请求的json字符串
     * @param tClass 返回的类型
     * @return manage返回的数据(若为null则代表发生异常)
     *
     */
    public <T> T post2ManageAndTrans(String url, String requestJson, Class<T> tClass) {
        String str = post2Business(url, requestJson);
        return JacksonFactory.readJson(str, tClass);
    }


}
