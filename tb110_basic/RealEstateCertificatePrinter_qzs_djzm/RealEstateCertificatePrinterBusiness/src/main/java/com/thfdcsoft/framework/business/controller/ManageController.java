package com.thfdcsoft.framework.business.controller;

import com.thfdcsoft.framework.business.contant.RecordConstant;
import com.thfdcsoft.framework.business.dto.UsageAndPrintRecordReq;
import com.thfdcsoft.framework.business.entity.PrintRecord;
import com.thfdcsoft.framework.business.entity.UsageRecord;
import com.thfdcsoft.framework.business.service.ManageService;
import com.thfdcsoft.framework.business.dto.HttpReturnDto;
import com.thfdcsoft.framework.common.http.dto.BaseHttpRspn;
import com.thfdcsoft.framework.common.json.jackson.JacksonFactory;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thfdcsoft.gov.dto.qzs.HandleResult;
import com.thfdcsoft.gov.dto.qzs.QzsEstateInfo;
import com.thfdcsoft.gov.service.IConnectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 管理平台controller
 * 处理管理平台请求
 *
 * @author Zenith
 * @date 2019/7/3 10:26
 */
@Controller
@RequestMapping(value = "/manage")
public class ManageController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(ManageController.class);
	
	@Autowired
    private IConnectService connector;
	
	@Autowired
    private ManageService manageService;

    /**
     * 接收manage重传请求
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "/resend")
    @ResponseBody
    public Boolean resend(HttpServletRequest request, HttpServletResponse response) {
        logger.debug("【接收manage重传请求】");
        HttpReturnDto result = new HttpReturnDto(false);
        String userFullName = request.getParameter("userFullname");
        String userIdNumber = request.getParameter("userIdNumber");
        String certNumber = request.getParameter("certNumber");
        String bizNumber = request.getParameter("bizNumber");
        String ocr = request.getParameter("ocr");

        // 1. 通过业务流水号查询使用记录和打印记录
        BaseHttpRspn rspn = manageService.post2ManageAndTrans("usage/usageRecordAndPrintRecordByBizNumber", bizNumber, BaseHttpRspn.class);
        logger.debug("【通过业务流水号查询到的使用记录和打印记录】respJson = {}", rspn.getRespJson());
        if (rspn.isResult()) {
            // 1.1 查到使用记录和打印记录
            UsageAndPrintRecordReq upReq = JacksonFactory.readJson(rspn.getRespJson(),UsageAndPrintRecordReq.class);
            PrintRecord printRecord = upReq.getPrintRecord();
            UsageRecord usageRecord = upReq.getUsageRecord();
            // 2 获取接口数据
            List<QzsEstateInfo> list = null;
            HandleResult queryResult = new HandleResult(false);
            try {
                List<String> bizNumbers = new ArrayList<>();
                bizNumbers.add(bizNumber);
                // 如果是通过扫码模式，需要带上bizNumbers，如果是通过身份证查询，直接传用户名和身份证号即可（两种方式使用日志都需要传入）
                // 此处请求了系统商查询接口
                queryResult = connector.getRealEstateListByIdNumberAndName(usageRecord);
            } catch (Exception e) {
                logger.error("【接收manage重传请求】，获取不动产信息异常");
                result.setResult(false);
                result.setRespMsg("请求查询接口异常!");
//                writeJSONResult(result, response);
                return false;
            }
            if(queryResult.getResult()) {
                // 系统商返回的结果有值
                list = queryResult.getRealEstateInfo();
            }
            logger.debug("获取接口数据的长度：{}", list.size());
            if (!list.isEmpty() || list != null) {
                // 2.1 请求接口查到数据
                // 3 结果匹配
                QzsEstateInfo realEstateInfo = null;
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).getCertNumber().equals(certNumber) && list.get(i).getBusinessNumber().equals(bizNumber)) {
                        realEstateInfo = list.get(i);
                    }
                }
                if (realEstateInfo != null) {
                    boolean flag = false;
                    try {
                        // 判断当前操作记录是登记证明或是权证书记录，根据不同类型做出相应的请求
                        // 此处请求了系统商回传接口
                        flag = connector.notification(printRecord, usageRecord);
                    } catch (Exception e) {
                        logger.error("【接收manage重传请求】，请求回传接口异常!!", e);
                        result.setResult(false);
                        result.setRespMsg("请求回传接口异常!");
                        writeJSONResult(result, response);
                        return false;
                    }
                    if (flag) {
                        // 5.1 回传成功
                        // 6 保存记录
                        printRecord.setTranStatusBiz(RecordConstant.TranStatus.SUCCESS);
                        String savePrintReqJson = JacksonFactory.writeJson(printRecord);
                        String savePrintRspnJson = manageService.post2Manage("business/updatePrintRecord", savePrintReqJson);
                        BaseHttpRspn savePrintRspn = JacksonFactory.readJson(savePrintRspnJson, BaseHttpRspn.class);
                        if (savePrintRspn.isResult()) {
                            // 6.1 保存记录成功
                            result.setResult(true);
                            result.setRespMsg("回传并保存成功!");
//                                writeJSONResult(result, response);
                            return true;
                        } else {
                            // 6.2 保存记录失败
                            logger.error("回传成功, 保存本地失败!");
                            result.setResult(false);
                            result.setRespMsg("回传成功, 保存本地失败!");
//                                writeJSONResult(result, response);
                            return false;
                        }
                    } else {
                        // 5.2 回传失败
                        logger.error("数据回传系统失败!");
                        result.setResult(false);
                        result.setRespMsg("数据回传系统失败!");
//                            writeJSONResult(result, response);
                        return false;
                    }
//                    } else {
//                        // 4.2 有打印记录
//                        logger.error("该证明已打印回传过!");
//                        result.setResult(false);
//                        result.setRespMsg("该证明已打印回传过!");
////                        writeJSONResult(result, response);
//                        return false;
//                    }
                } else {
                    // 3.2 未匹配到结果
                    logger.error("未查到匹配的登记信息!");
                    result.setResult(false);
                    result.setRespMsg("未查到匹配的登记信息!");
//                    writeJSONResult(result, response);
                    return false;
                }
            } else {
                // 2.2 请求查询接口未查到任何数据
                logger.error("未查到任何登记信息!");
                result.setResult(false);
                result.setRespMsg("未查到任何登记信息!");
//                writeJSONResult(result, response);
                return false;
            }
        } else {
            // 1.2 未查到使用记录
            logger.error("未查到领证人在该系统上有使用记录!");
            result.setResult(false);
            result.setRespMsg("未查到领证人在该系统上有使用记录!");
//            writeJSONResult(result, response);
            return false;
        }
    }
    
}
