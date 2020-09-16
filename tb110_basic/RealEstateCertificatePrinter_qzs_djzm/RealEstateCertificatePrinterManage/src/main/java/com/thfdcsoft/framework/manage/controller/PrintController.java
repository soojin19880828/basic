package com.thfdcsoft.framework.manage.controller;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.thfdcsoft.framework.common.date.DateFormatConstant;
import com.thfdcsoft.framework.common.date.DateFormatFactory;
import com.thfdcsoft.framework.common.file.FileFactory;
import com.thfdcsoft.framework.common.http.dto.BaseHttpRspn;
import com.thfdcsoft.framework.common.http.dto.HttpReturnDto;
import com.thfdcsoft.framework.common.image.ImageFactory;
import com.thfdcsoft.framework.common.json.jackson.JacksonFactory;
import com.thfdcsoft.framework.common.string.StringUtils;
import com.thfdcsoft.framework.manage.constant.RecordConstant;
import com.thfdcsoft.framework.manage.constant.StaticConstant;
import com.thfdcsoft.framework.manage.dto.DataTableView;
import com.thfdcsoft.framework.manage.dto.Page;
import com.thfdcsoft.framework.manage.dto.lankao.WriteBackDataInfo;
import com.thfdcsoft.framework.manage.dto.lankao.WriteBackReq;
import com.thfdcsoft.framework.manage.dto.lankao.WriteBackResponse;
import com.thfdcsoft.framework.manage.dto.xian.BaseZB05File;
import com.thfdcsoft.framework.manage.dto.xian.Cert;
import com.thfdcsoft.framework.manage.entity.PrintRecord;
import com.thfdcsoft.framework.manage.entity.PrintRecordExample;
import com.thfdcsoft.framework.manage.entity.PrintRecordExample.Criteria;
import com.thfdcsoft.framework.manage.entity.TerminalInfo;
import com.thfdcsoft.framework.manage.entity.UsageRecord;
import com.thfdcsoft.framework.manage.service.IPrintService;
import com.thfdcsoft.framework.manage.util.*;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

@Controller
@RequestMapping("/print")
public class PrintController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(PrintController.class);

    @Autowired
    private IPrintService printService;

    private List<PrintRecord> lastRecord;

    /**
     * 跳转至打印记录页面
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index(HttpServletRequest request, HttpServletResponse response) {
        return "modules/terminals/print/printUI";
    }

    @RequestMapping(value = "/zmIndex", method = RequestMethod.GET)
    public String zmIndex(HttpServletRequest request, HttpServletResponse response) {
        return "modules/terminals/print/zmPrintUI";
    }

    /**
     * 查询权证书打印记录信息
     *
     * @param page
     */
    @RequestMapping(value = "/query", method = RequestMethod.GET, produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String query(Page page) {
        PrintRecordExample example = new PrintRecordExample();
        PrintRecordExample.Criteria criteria = example.createCriteria();
        criteria.andPrintTypeEqualTo(RecordConstant.PrintType.PRINT_QZS);
        if (!page.getStartTime().isEmpty() && page.getStartTime() != null) {
            criteria.andPrintTimeGreaterThanOrEqualTo(page.getStartTime());
        }
        if (!page.getEndTime().isEmpty() && page.getEndTime() != null) {
            criteria.andPrintTimeLessThanOrEqualTo(page.getEndTime());
        }
        if (!page.getBizNumber().isEmpty() && page.getBizNumber() != null) {
            criteria.andSeqNumberLike("%" + page.getBizNumber() + "%");
        }
        if (!page.getCertNumber().isEmpty() && page.getCertNumber() != null) {
            criteria.andCertNumberLike("%" + page.getCertNumber() + "%");
        }
        if (page.getPrintType() != "" && page.getPrintType() != null) {
            criteria.andPrintTypeLike("%" + page.getPrintType() + "%");
        }
        example.setOrderByClause("PRINT_TIME desc");
        PageList<PrintRecord> records = printService.selectPrintRecordsByPrintRecordExample(example, this.getPageBounds(page));
        lastRecord = records;
        Long count = printService.countByPrintRecordExample(example);
        DataTableView<PrintRecord> view = new DataTableView<PrintRecord>();
        view.setData(records);
        view.setRecordsTotal(count.intValue());
        view.setRecordsFiltered(count.intValue());
        return JacksonFactory.writeJson(view);
    }

    /**
     * 查询登记证明打印记录信息
     *
     * @param page
     */
    @RequestMapping(value = "/zmQuery", method = RequestMethod.GET, produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String zmQuery(Page page) {
        PrintRecordExample example = new PrintRecordExample();
        PrintRecordExample.Criteria criteria = example.createCriteria();
        criteria.andPrintTypeEqualTo(RecordConstant.PrintType.PRINT_DJZM);
        if (!page.getStartTime().isEmpty() && page.getStartTime() != null) {
            criteria.andPrintTimeGreaterThanOrEqualTo(page.getStartTime());
        }
        if (!page.getEndTime().isEmpty() && page.getEndTime() != null) {
            criteria.andPrintTimeLessThanOrEqualTo(page.getEndTime());
        }
        if (!page.getBizNumber().isEmpty() && page.getBizNumber() != null) {
            criteria.andSeqNumberLike("%" + page.getBizNumber() + "%");
        }
        if (!page.getCertNumber().isEmpty() && page.getCertNumber() != null) {
            criteria.andCertNumberLike("%" + page.getCertNumber() + "%");
        }
        if (page.getPrintType() != "" && page.getPrintType() != null) {
            criteria.andPrintTypeLike("%" + page.getPrintType() + "%");
        }
        example.setOrderByClause("PRINT_TIME desc");
        PageList<PrintRecord> records = printService.selectPrintRecordsByPrintRecordExample(example, this.getPageBounds(page));
        lastRecord = records;
        Long count = printService.countByPrintRecordExample(example);
        DataTableView<PrintRecord> view = new DataTableView<PrintRecord>();
        view.setData(records);
        view.setRecordsTotal(count.intValue());
        view.setRecordsFiltered(count.intValue());
        return JacksonFactory.writeJson(view);
    }

    /**
     * 通过不动产单元号和权证号来查询打印记录
     *
     * @param param
     * @return
     */
    @RequestMapping(value = "/selectByBizAndCert", method = RequestMethod.POST)
    @ResponseBody
    public String queryRecord(@RequestBody String param) {
        BaseHttpRspn result = new BaseHttpRspn();
        result.setResult(false);
        String[] split = param.split("-");
        String bizNumber = split[0];
        String certNumber = split[1];
        PrintRecordExample example = new PrintRecordExample();
        Criteria criteria = example.createCriteria();
        criteria.andBizNumberEqualTo(bizNumber);
        criteria.andCertNumberEqualTo(certNumber);
        List<PrintRecord> printRecords = printService.selectPrintRecordsByPrintRecordExample(example);
        if (!printRecords.isEmpty()) {
            result.setResult(true);
            result.setRespJson(JacksonFactory.writeJson(printRecords));
        }
        return JacksonFactory.writeJson(result);
    }

    @RequestMapping(value = "/getPic", method = RequestMethod.GET)
    @ResponseBody
    public void getPic(HttpServletRequest request, HttpServletResponse response, @Param("picPath") String picPath) {
        try {
            BufferedImage img = new BufferedImage(100, 120, BufferedImage.TYPE_INT_RGB);
            img = ImageIO.read(new FileInputStream(picPath));
            if (img != null) {
                ImageIO.write(img, "JPEG", response.getOutputStream());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 重发
     *
     * @param response
     * @param recordId 打印记录ID
     */
    @RequestMapping(value = "/resend", method = RequestMethod.POST)
    public void resend(HttpServletResponse response, String recordId) {
        BaseHttpRspn result = new BaseHttpRspn(false);
        // 获取打印记录信息
        PrintRecord print = printService.selectPrintRecordByPrintId(recordId);
        if (print == null) {
            result.setRespMsg("未获取到打印记录信息！");
            writeJSONResult(result, response);
            return;
        }
        // 获取使用记录信息
        UsageRecord usage = printService.selectUsageRecordByUsageId(print.getUsageId());
        if (usage == null) {
            result.setRespMsg("未获取到使用记录信息！");
            writeJSONResult(result, response);
            return;
        }
        // 回传不动产
        if (!RecordConstant.TranStatus.SUCCESS.equals(print.getTranStatusBiz())) {
            Boolean resendState = printService.resend2EstateSystem(print, usage);
            //回传成功就更新回传状态
            if (resendState) {
                print.setTranStatusBiz(RecordConstant.TranStatus.SUCCESS);
                printService.updatePrintRecordByPrintIdSelective(print);
                result.setResult(true);
                result.setRespMsg("重发回传请求成功！");
            } else {
                result.setResult(false);
                result.setRespMsg("重发回传请求失败！");
            }
        }
        writeJSONResult(result, response);
    }

    @RequestMapping(value = "/updateSeqNumber", method = RequestMethod.POST)
    public void updateSeqNumber(HttpServletRequest request, HttpServletResponse response, String seqNumber, String recordId) {

        logger.info("【updateSeqNumber】，seqNumber为：{}", seqNumber);
        logger.info("【updateSeqNumber】，recordId为：{}", recordId);
        BaseHttpRspn result = new BaseHttpRspn(false);
        if (seqNumber.length() != 11) {
            result.setRespMsg("录入工本号格式不正确，请仔细核对！");
            writeJSONResult(result, response);
            return;
        }
        //获取打印记录信息
        PrintRecord print = printService.selectPrintRecordByPrintId(recordId);
        if (print == null) {
            result.setRespMsg("未获取到打印记录信息！");
            writeJSONResult(result, response);
            return;
        }
        // 获取使用记录信息
        UsageRecord usage = printService.selectUsageRecordByUsageId(print.getUsageId());
        if (usage == null) {
            result.setRespMsg("未获取到使用记录信息！");
            writeJSONResult(result, response);
            return;
        }
        // 获取设备信息
//        TerminalInfo terminal = printService.selectByDeployNumber(usage.getDeviceNumber());
        List<TerminalInfo> terminalList = printService.getTerminalListByDeployNumber(usage.getDeviceNumber());
        if (terminalList.size()<1) {
            result.setRespMsg("未获取到设备信息！");
            writeJSONResult(result, response);
            return;
        }
        // 获取日志文件
//		File recordFile = new File(usage.getUserLogPath());
        //修改工本号
        print.setSeqNumber(seqNumber);
        //手动回传接口
        Boolean resendState = printService.resend2EstateSystem(print, usage);
        //如果resendState是true就是成功,回传完成之后更改状态TRAN_STATUS_BIZ改成01
        //回传成功就更新回传状态
        if (resendState) {
            int i = printService.updatePrintRecordByPrintIdSelective(print);
            if (i == 1) {
                print.setTranStatusBiz(RecordConstant.TranStatus.SUCCESS);
                printService.updatePrintRecordByPrintIdSelective(print);
                logger.error("回传系统商接口成功，更新成功");
                result.setResult(true);
                result.setRespMsg("回传系统商接口成功，更新成功");
            }else {
                logger.error("回传系统商接口成功，数据库更新失败");
                result.setRespMsg("回传系统商接口成功，数据库更新失败");
                writeJSONResult(result, response);
            }
        }else {
            logger.error("回传系统商接口失败，更新失败");
            result.setRespMsg("回传系统商接口失败，更新失败");
            writeJSONResult(result, response);
        }
    }


    /*************************后面加的开始*************************************/
    // 按时间查询
    @RequestMapping(value = "/search", method = RequestMethod.GET, produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String search(HttpServletRequest request, HttpServletResponse response, Page page) {
        logger.info("in search......");
        String result = "";
        PrintRecordExample example = new PrintRecordExample();
        example.setOrderByClause("PRINT_TIME asc");
        Criteria criteria = example.createCriteria();
        if (!page.getStartTime().isEmpty() && page.getStartTime() != null) {
            criteria.andPrintTimeGreaterThanOrEqualTo(page.getStartTime());
        }
        if (!page.getEndTime().isEmpty() && page.getEndTime() != null) {
            criteria.andPrintTimeLessThanOrEqualTo(page.getEndTime());
        }
        if (!page.getBizNumber().isEmpty() && page.getBizNumber() != null) {
            criteria.andSeqNumberLike("%" + page.getBizNumber() + "%");
        }
//			if (!page.getBizNumber().isEmpty() && page.getBizNumber() != null) {
//				criteria.andBizNumberLike("%" + page.getBizNumber() + "%");
//			}
        if (!page.getCertNumber().isEmpty() && page.getCertNumber() != null) {
            criteria.andCertNumberLike("%" + page.getCertNumber() + "%");
        }
        PageList<PrintRecord> records = printService.selectPrintRecordsByPrintRecordExample(example, this.getPageBounds(page));
        lastRecord = printService.selectPrintRecordsByPrintRecordExample(example);
        logger.info("records===========" + records.size());
        Long count = printService.countByPrintRecordExample(example);
        if (records.size() == 0) {
            result = "{\"data\":[],\"recordsTotal\":0,\"recordsFiltered\":0,\"draw\":0}";
        } else {
            DataTableView<PrintRecord> view = new DataTableView<PrintRecord>();
            view.setData(records);
            view.setRecordsTotal(count.intValue());
            view.setRecordsFiltered(count.intValue());
            result = JacksonFactory.writeJson(view);
        }
        return result;
    }

    // 导出数据
	/*@RequestMapping(value = "/export", method = RequestMethod.GET)
	@ResponseBody
	public String export(HttpServletRequest request, HttpServletResponse response) {
		BaseHttpRspn result = new BaseHttpRspn(false);
//			HttpReturnDto result = new HttpReturnDto(false);
		FileFactory fileFactory = new FileFactory();
		fileFactory.buildFile("D:\\PrintRecord\\printRecord.xls");
		WriteExcel writeExcel = new WriteExcel();
		for (PrintRecord record : lastRecord) {
			if (("00").equals(record.getTranStatusBiz())) {
				record.setTranStatusBiz("等待");
			} else if (("01").equals(record.getTranStatusBiz())) {
				record.setTranStatusBiz("成功");
			} else {
				record.setTranStatusBiz("失败");
			}
		}
		writeExcel.ExportExcel(lastRecord, new java.io.File(("D:\\PrintRecord\\printRecord.xls")));
		result.setResult(true);
		return JacksonFactory.writeJson(result);
	}*/

    // 导出数据
    @RequestMapping(value = "/export", method = RequestMethod.GET)
    @ResponseBody
    public String export(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("in export......");
        String path = "D:\\PrintRecord\\printRecord.xls";
        HttpReturnDto result = new HttpReturnDto(false);
        FileFactory.buildFile(path);
        WriteExcel writeExcel = new WriteExcel();
        for (PrintRecord record : lastRecord) {
            if (("00").equals(record.getTranStatusBiz())) {
                record.setTranStatusBiz("等待");
            } else if (("01").equals(record.getTranStatusBiz())) {
                record.setTranStatusBiz("成功");
            } else {
                record.setTranStatusBiz("失败");
            }
        }
        writeExcel.ExportExcel(lastRecord, new File(path));
        //下载excel
        try {
            writeExcel.downloadExcel(response, path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        result.setResult(true);
        System.out.println("out export......");
        return JacksonFactory.writeJson(result);
    }

    /**
     * 补录	这里的数据需根据不动产要求进行取舍
     *
     * @param param
     * @return
     */
    @RequestMapping(value = "/reSave")
    @ResponseBody
    public void reSave(HttpServletResponse response, @RequestParam HashMap<String, String> param) {
        BaseHttpRspn result = new BaseHttpRspn(false);

        String userFullName = param.get("userFullname");
        String userIdNumber = param.get("userIdNumber");
        String bizNumber = param.get("bizNumber");
        String certNumber = param.get("certNumber");
        String bookTime = param.get("bookTime");
        String obligee = param.get("obligee");
        String located = param.get("located");
        String ownership = param.get("ownership");
        String unitNumber = param.get("unitNumber");
        String busiType = param.get("busiType");
        String natureOfRight = param.get("natureOfRight");
        String usage = param.get("usage");
        String coveredArea = param.get("coveredArea");
        String serviceLife = param.get("serviceLife");
        String otherCases = param.get("otherCases");
        String notes = param.get("notes");
        String ocr = param.get("ocr");

        UsageRecord usageRecord = new UsageRecord();

        String autoId = AutoIdFactory.getAutoId();
        String currentDateString = DateFormatFactory.getCurrentDateString(DateFormatConstant.SymbolFormat.YYYY_MM_DD_HH_MM_SS);
        usageRecord.setRecordId(autoId);
        usageRecord.setUserFullname(userFullName);
        usageRecord.setUserIdnumber(userIdNumber);
        usageRecord.setDeviceNumber("补录");
        usageRecord.setUsageTime(currentDateString);
        usageRecord.setUserDetPicPath("/");
        usageRecord.setUserIdPicPath("/");
        usageRecord.setUserLogPath("/");
        int saveUsageRecordFlag = printService.insertUsageRecordSelective(usageRecord);
        if (1 == saveUsageRecordFlag) {
            PrintRecord printRecord = new PrintRecord();
            printRecord.setBizNumber(bizNumber);
            printRecord.setBusiType(busiType);
            printRecord.setCertNumber(certNumber);
            printRecord.setCoveredArea(coveredArea);
            printRecord.setLocated(located);
            printRecord.setNatureOfRight(natureOfRight);
            printRecord.setNotes(notes);
            printRecord.setObligee(obligee);
            printRecord.setOtherCases(otherCases);
            printRecord.setOwnership(ownership);
            printRecord.setPrintTime(currentDateString);
            printRecord.setRecordId(AutoIdFactory.getAutoId());
            printRecord.setUseage(usage);
            printRecord.setUnitNumber(unitNumber);
            printRecord.setTranStatusBiz("00");
            printRecord.setTimeBook(bookTime);
            printRecord.setServiceLife(serviceLife);
            printRecord.setSeqNumber(ocr);
            printRecord.setReferenceColumn("补录");
            printRecord.setCertScanPath("/");
            printRecord.setUsageId(autoId);

            int savePrintRecordFlag = printService.insertPrintRecordSelective(printRecord);
            result.setResult(true);
            if (1 != savePrintRecordFlag) {
                result.setRespMsg("保存打印记录失败，请检查录入信息");
            }
        } else {
            result.setResult(true);
            result.setRespMsg("保存查询记录失败，请检查录入信息");
        }
        writeJSONResult(result, response);
    }

    /**
     * 登记证明手动回传
     * @param request
     * @param response
     * @param recordId
     */
    @RequestMapping(value = "/zmResend", method = RequestMethod.POST)
    public void resend(HttpServletRequest request, HttpServletResponse response, String recordId) {
        HttpReturnDto result = new HttpReturnDto(false);
        // 获取打印记录信息
        System.out.println("recordId=====" + recordId);
        PrintRecord print = printService.selectPrintRecordByPrintId(recordId);
        if (print == null) {
            result.setRespMsg("未获取到打印记录信息！");
            writeJSONResult(result, response);
            return;
        }
        // 获取使用记录信息
        UsageRecord usage = printService.selectUsageRecordByUsageId(print.getUsageId());
        System.out.println("print.getUsageId()=====" + print.getUsageId());
        if (usage == null) {
            result.setRespMsg("未获取到使用记录信息！");
            writeJSONResult(result, response);
            return;
        }
        // OCR识别失败则提示先修改OCR然后再重新发送
        if ("00000000000".equals(print.getSeqNumber())) {
            result.setRespMsg("OCR识别失败，请先在记录详情中修改OCR再重新回传！");
            writeJSONResult(result, response);
            return;
        }
        // 获取日志文件
        File recordFile = new File(usage.getUserLogPath());
        // 回传不动产
        if (!RecordConstant.TranStatus.SUCCESS.equals(print.getTranStatusBiz())) {
            WriteBackReq registers = new WriteBackReq();
            List<WriteBackDataInfo> register = new ArrayList();
            WriteBackDataInfo data = new WriteBackDataInfo();
            //授权码
            GetRandomString getString = new GetRandomString();
            String str =getString.GetString(RecordConstant.ParametetsConstant.RSA, 8);
            StringBuilder builder = new StringBuilder();
            String yzm = builder.append(RecordConstant.ParametetsConstant.ACCOUNT)
                    .append(RecordConstant.ParametetsConstant.PASSWORD).append(str).toString();
            GetRsaString getRsa = new GetRsaString();
            String sqm="";
            //授权码加密操作
            try {
                sqm = getRsa.GetRsaStr(yzm);
            } catch (Exception e) {
                e.printStackTrace();
            }
            registers.setYzm(sqm);
            //硬件码
            registers.setYjm(usage.getDeviceNumber());

            // 受理编号
            data.setSlbh(print.getBizNumber());
            // data.setSlbh("41080020180803000008");
            // 证书编号
            data.setZsbh(print.getCertNumber());
            // data.setZsbh("京（2018）解放区证明号第8889088号");
            // 证书印刷编号
            data.setYsbh(print.getSeqNumber());
            // 发证时间(打印时间)
            data.setFzsj(print.getPrintTime());
            // 发证人(设备编号)
            data.setFzr(usage.getDeviceNumber());
            // 取件人
            data.setQjr(usage.getUserIdnumber());
            // 取件人照片(转Base64字符传输)
            data.setQjrzp(ImageFactory.getImageStr(usage.getUserDetPicPath()));
            // 取件时间
            data.setQjsj(print.getPrintTime());
            // 证书照片(转Base64字符传输)
            List<String> zszp = new ArrayList<String>();
            System.out.println(print.getCertScanPath().toString());
            if (print.getCertScanPath() != null) {
                zszp.add(ImageFactory.getImageStr(print.getCertScanPath()));
            } else {
                zszp.add("");
            }
            data.setZszp(zszp);
            register.add(data);
            registers.setData(register);
            String reqData = JacksonFactory.writeJson(registers);
            FileFactory.writeTXTFile(recordFile, "手动回传请求数据："+reqData);
            String rspnStr = null;
            try {
                // 发送请求
                rspnStr = HttpUtil.sendPost(StaticConstant.WRITE_BACK_URL, reqData);
            } catch (Exception e) {
                System.out.println("请求回传接口出错");
                e.printStackTrace();
            }
            FileFactory.writeTXTFile(recordFile, "手动回传结果返回："+rspnStr);
            if(StringUtils.isNotNullAndEmpty(rspnStr)) {
                WriteBackResponse rspn = JacksonFactory.readJson(rspnStr, WriteBackResponse.class);
                if(rspn.getResult() == 1){
                    print.setTranStatusBiz(RecordConstant.TranStatus.SUCCESS);
                    printService.updatePrintRecordByPrintIdSelective(print);
                }else{
                    logger.debug("错误信息========="+rspn.getMsg());
                    result.setRespMsg("回传不动产系统失败！");
                    writeJSONResult(result, response);
                    return;
                }
            }else {
                result.setRespMsg("不动产系统回传无响应！");
                writeJSONResult(result, response);
                return;
            }
        }
        result.setResult(true);
        result.setRespMsg("重发回传请求成功！");
        writeJSONResult(result, response);
    }

}
