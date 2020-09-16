package com.thfdcsoft.framework.manage.test;

import com.thfdcsoft.framework.common.http.dto.HttpReturnDto;
import com.thfdcsoft.framework.common.json.jackson.JacksonFactory;
import com.thfdcsoft.framework.manage.dto.PrintRecordBycerNumReq;
import com.thfdcsoft.framework.manage.dto.SubmitPrintReq;
import com.thfdcsoft.framework.manage.dto.UpdatePaperCountReq;
import com.thfdcsoft.framework.manage.entity.ChkRemainingReq;
import com.thfdcsoft.framework.manage.util.HttpUtil;

import java.util.ArrayList;
import java.util.List;

public class TestController {
    public static void main(String[] args) {
        /*UpdatePaperCountReq updatePaperCountReq = new UpdatePaperCountReq();
        updatePaperCountReq.setUsageId("20200803181115000019");
        updatePaperCountReq.setTerminalId("TH-ZB-102-ZZDY1HJ");
        // 登记证明数量扣减
        String rspnJson = HttpUtil.sendPost("http://localhost:8080/Manage/business/updateRemainPager", JacksonFactory.writeJson(updatePaperCountReq));
        //权证书数量扣减
//        String rspnJson = HttpUtil.sendPost("http://localhost:8080/Manage/business/updatePaper","20200803181115000019");
        System.out.println(rspnJson);*/

        /*ChkRemainingReq req = new ChkRemainingReq();
        req.setDeviceNumber("zzdy1hj");
        req.setTerminalId("TH-ZB-102-ZZDY1HJ");
        String rspnJson = HttpUtil.sendPost("http://localhost:8080/Manage/business/selectByDeployNumber", JacksonFactory.writeJson(req));
        System.out.println(rspnJson);*/

       /* SubmitPrintReq req = new SubmitPrintReq();
        req.setUsageId("20200803181115000019");
        req.setTerminalId("TH-ZB-108-ZZDY1HJ");
        String rspnJson = HttpUtil.sendPost("http://localhost:8080/Manage/business/updatePaper", JacksonFactory.writeJson(req));
        System.out.println(rspnJson);*/

       /* SubmitPrintReq req = new SubmitPrintReq();
        req.setUsageId("20200803181115000019");
        req.setTerminalId("TH-ZB-108-ZZDY1HJ");
        String rspnJson = HttpUtil.sendPost("http://localhost:8080/Manage/business/getTerminalInfo", JacksonFactory.writeJson(req));
        System.out.println(rspnJson);*/

        PrintRecordBycerNumReq req = new PrintRecordBycerNumReq();
        List<String> list = new ArrayList<>();
        list.add("豫（2020）濮阳市不动产权第0009367号");
        list.add("豫（2020）濮阳市不动产权第0009370号");
        req.setCerNumsList(list);
        String reqJson = JacksonFactory.writeJson(req);
        System.out.println(req);
        String rspnJson = HttpUtil.sendPost("http://localhost:8080/Manage/business/getDjzmRecordsByCertNums",reqJson );
        System.out.println(rspnJson);
    }
}
