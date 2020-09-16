package com.thfdcsoft.framework.business.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thfdcsoft.gov.dto.qzs.HandleResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.thfdcsoft.framework.business.entity.UsageRecord;
import com.thfdcsoft.framework.business.service.ManageService;
import com.thfdcsoft.framework.business.service.QueryService;
import com.thfdcsoft.framework.common.http.dto.BaseHttpRspn;
import com.thfdcsoft.framework.common.json.jackson.JacksonFactory;

/**
 * 接收business 页面请求
 * 2019年4月29日  11点56分
 *
 * @author weaso
 */
@Controller
@RequestMapping(value = "/business")
public class BusinessController {

    private static final Logger logger = LoggerFactory.getLogger(BusinessController.class);

    @Autowired
    private ManageService manageService;

    @Autowired
    private QueryService queryService;

    /**
     * 权证书列表页面查询
     */
    @RequestMapping(value = "/query")
    @ResponseBody
    public HandleResult query(HttpServletRequest request, HttpServletResponse response) {
        String recordId = request.getParameter("recordId");
        logger.info("in query.... 使用记录id: " + recordId);
        HandleResult result = new HandleResult(true);
        // 通过recordid请求manage查询使用记录
        BaseHttpRspn rspn = manageService.post2ManageAndTrans("business/queryRecord", recordId, BaseHttpRspn.class);
        if (rspn != null && rspn.isResult()) {
            UsageRecord usageRecord = JacksonFactory.readJson(rspn.getRespJson(), UsageRecord.class);
            result = queryService.queryRealEstate(usageRecord);
        } else {
            logger.info("未查询到"+ recordId +"使用记录");
            result.setResult(false);
            result.setExceptionDetail("使用记录获取失败...");
        }
        logger.info("查询到的数据： " + result.toString());
        return result;
    }
    
}
