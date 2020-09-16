package com.thfdcsoft.gov.service.impl;

import com.thfdcsoft.framework.business.contant.RecordConstant;
import com.thfdcsoft.framework.business.contant.StaticConstant;
import com.thfdcsoft.framework.business.util.*;
import com.thfdcsoft.gov.dto.qzs.HandleResult;
import com.thfdcsoft.framework.business.dto.PrintRecordBycerNumReq;
import com.thfdcsoft.framework.business.dto.PrintRecordRsp;
import com.thfdcsoft.framework.business.entity.PrintRecord;
import com.thfdcsoft.framework.business.entity.UsageRecord;
import com.thfdcsoft.framework.business.service.ManageService;
import com.thfdcsoft.framework.common.http.HttpClientFactory;
import com.thfdcsoft.framework.common.image.ImageFactory;
import com.thfdcsoft.gov.connector.BizConnector;
import com.thfdcsoft.gov.dto.YwrInfo;
import com.thfdcsoft.gov.dto.djzm.*;
import com.thfdcsoft.gov.dto.qzs.QzsEstateInfo;
import com.thfdcsoft.gov.service.IConnectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Transactional
public class ConnectServiceImpl implements IConnectService {

    private static final Logger logger = LoggerFactory.getLogger(ConnectServiceImpl.class);

    @Autowired
    private ManageService manageService;

    @Override
    public HandleResult getRealEstateListByIdNumberAndName(UsageRecord usageRecord) {
        // 1、请求查询接口，获取权证书信息
        HandleResult result = BizConnector.getCertInfos(usageRecord);
        if (result.getResult()) {
            // 存放未打印的不动产数据
            List<QzsEstateInfo> queryArray = new ArrayList<>();
            //2. 校验数据，剔除已打印的数据
            List<QzsEstateInfo> realEstateInfoList_query = result.getRealEstateInfo();
            List<String> certNumberList = realEstateInfoList_query.stream().map(QzsEstateInfo::getCertNumber).collect(Collectors.toList());
            PrintRecordBycerNumReq certNumReq = new PrintRecordBycerNumReq(certNumberList);
            //2.1 从manage获取已打印的权证书记录
            String printRecords_str = manageService.post2Manage("business/getQzsRecordsByCertNums", JacksonFactory.writeJson(certNumReq));
            PrintRecordRsp printRecordRsp = JacksonFactory.readJson(printRecords_str, PrintRecordRsp.class);
            List<PrintRecord> printRecords = new ArrayList<>();
            if(printRecordRsp != null && printRecordRsp.getResult()) {
                printRecords = printRecordRsp.getPrintList();
            }
            //2.2 循环比较，剔除已打印数据，将未打印的放入queryArray中
            for (QzsEstateInfo query : realEstateInfoList_query) {
                AtomicBoolean flag = new AtomicBoolean(true);
                for (PrintRecord record : printRecords) {
                    if (query.getCertNumber().equalsIgnoreCase(record.getCertNumber())) {
                        flag.set(false);
                        break;
                    }
                }
                if (flag.get()) {
                    queryArray.add(query);
                }
            }

            // 3.将查询数据进行非空判断
            /**
             * 校验获取的list对象数据是否有空值
             */
            List<QzsEstateInfo> normalRealEstateInfo = new ArrayList<>();
            List<QzsEstateInfo> abnormalRealEstateInfo = new ArrayList<>();
            for (QzsEstateInfo info : queryArray) {
                if (new CheckIsNullFactory().CheckObjectProperyIsNull(info)) {
                    // 有空数据
                    abnormalRealEstateInfo.add(info);
                } else {
                    // 没有空数据
                    normalRealEstateInfo.add(info);
                }
            }
            result.setNormalRealEstateInfo(normalRealEstateInfo);
            result.setAbnormalRealEstateInfo(abnormalRealEstateInfo);
        }
        return result;
    }

    /**
     * 通知不动产系统，并根据系统商的返回更改回传状态
     *
     * @param printRecord
     * @param usage
     * @author ww
     * @date 2019年5月14日 15:05:23
     */
    @Override
    public Integer notifyAndUpdate(PrintRecord printRecord, UsageRecord usage) {

        //通知不动产系统
        logger.info("通知不动产系统");
        boolean res;
        if ("DEBUG".equals(StaticConstant.RUNMODE)) {
            res = true;
        } else {
            res = notification(printRecord, usage);
        }

        //更新回传状态
        logger.info("更新回传状态");
        if (res) {
            printRecord.setTranStatusBiz(RecordConstant.TranStatus.SUCCESS);
        } else {
            printRecord.setTranStatusBiz(RecordConstant.TranStatus.FAILURE);
        }
        String str_printRecord = com.thfdcsoft.framework.business.util.JacksonFactory.writeJson(printRecord);
        return Integer.valueOf(manageService.post2Manage("business/updateTranStatus", str_printRecord));
    }

    @Override
    public boolean notification(PrintRecord printRecord, UsageRecord usage) {
        logger.debug("权证书信息开始回传......");
        boolean flag = false;
        try {
            flag = BizConnector.notification(printRecord, usage);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * ---------------------登记证明代码迁移--------------------------
     */
    /**
    　　* @description: 查询用户登记证明数据
    　　* @params
    　　* @author 高拓
    　　* @date 2020/9/8 13:45
    　　*/
    @Override
    public QueryEstateRspn getRealEstateListByIdNumber(String yjm, String idNumber, File recordFile) {
        EstateInfoRspn estateInfoRspn = BizConnector.getCerts(yjm, idNumber, recordFile);
        QueryEstateRspn queryResult = new QueryEstateRspn();
        if (!StringUtils.isEmpty(estateInfoRspn.getResultcode()) && RecordConstant.Code.SUCCESS.equals(estateInfoRspn.getResultcode())) {
            List<DjzmEstateResult> estateList = new ArrayList<DjzmEstateResult>();
            if (estateInfoRspn.getQueryResultDtoList() != null) {
                // 校验数据，剔除已打印的数据（通过查询到的产权证号和后台已经保存打印记录的产权证号进行比较排除）
                String rspnJson = null;
                // 将查询到的每一个房产信息的产权证号封装到集合（参考二期代码zsbh属性就是产权证号 —》如下：if (query.getZsbh().equalsIgnoreCase(record.getCertNumber()))）
                List<String> certNumberList = estateInfoRspn.getQueryResultDtoList().stream().map(DjzmQueryResultDto::getZsbh).collect(Collectors.toList());
                System.out.println("系统商接口返回的产权证号集合遍历：");
                certNumberList.stream().forEach(System.out::println);
                // 通过查询到的权证号集合去后台查询已经打印过的记录
                PrintRecordBycerNumReq req = new PrintRecordBycerNumReq(certNumberList);
                rspnJson = HttpClientFactory.getInstance().doPost(JacksonFactory.writeJson(req),
                        StaticConstant.MANAGE_URL + "business/getDjzmRecordsByCertNums");
                PrintRecordRsp rspn = JacksonFactory.readJson(rspnJson, PrintRecordRsp.class);
                // 将已经打印过的权证记录从系统商返回的数据里面删除
                List<DjzmQueryResultDto> queryArray = new ArrayList<DjzmQueryResultDto>();
                if (rspn.getResult()) {
                    List<PrintRecord> abnormalList = rspn.getPrintList();
                    for (DjzmQueryResultDto query : estateInfoRspn.getQueryResultDtoList()) {
                        boolean f = true;
                        for (PrintRecord record : abnormalList) {
                            // 证书编号 粤(2018)佛禅不动产证明第0001666号
                            if (query.getZsbh().equalsIgnoreCase(record.getCertNumber())) {
                                f = false;
                                break;
                            }
                        }
                        if (f) {
                            queryArray.add(query);
                        }
                    }
                } else {
                    queryArray = estateInfoRspn.getQueryResultDtoList();
                }
                // 获取不动产登记业务列表
                // 打印类型：1、预告登记  2、抵押权预告登记  3、不动产权抵押  4、在建建筑物抵押
                for (DjzmQueryResultDto query : queryArray) {
                    //因为濮阳权证书接口和登记证明共用一个接口，因此还会出现权证书的5、6、7、8业务类型，因此封装数据时需要做限制
                    if (query.getYwlx() == 1 || query.getYwlx() == 2 || query.getYwlx() == 3 || query.getYwlx() == 4) {
                        DjzmEstateResult estateResult = new DjzmEstateResult();
                        DjzmEstateInfo estate = new DjzmEstateInfo();
                        // 不动产单元号
                        StringBuilder bdcdyh = new StringBuilder();
                        if (!CollectionUtils.isEmpty(query.getBdcdyxx())) {
                            if (query.getBdcdyxx().size() > 1) {
                                bdcdyh.append(query.getBdcdyxx().get(0).getBdcdyh()).append(" 等").append(query.getBdcdyxx().size())
                                        .append("套");
                            } else {
                                bdcdyh.append(query.getBdcdyxx().get(0).getBdcdyh());
                            }
                        }
                        estate.setUnitNumber(bdcdyh.toString());
                        // 不动产权证书号
                        estate.setCertNumber(query.getZsbh());
                        // 权利人(集合,必输)
                        StringBuilder qlr = new StringBuilder();
                        if (!CollectionUtils.isEmpty(query.getQlr())) {
                            for (DjzmQlrInfo qlrInfo : query.getQlr()) {
                                qlr.append(",").append(qlrInfo.getMc());
                            }
                            estate.setObligee(qlr.toString().substring(1));
                        } else {
                            estate.setObligee(qlr.toString());
                        }
                        System.out.println("权利人======" + estate.getObligee());
                        // 义务人(集合)
                        StringBuilder ywr = new StringBuilder();
                        if (!CollectionUtils.isEmpty(query.getYwr())) {
                            for (YwrInfo ywrInfo : query.getYwr()) {
                                ywr.append(",").append(ywrInfo.getMc());
                            }
                            estate.setObligor(ywr.toString().substring(1));
                        } else {
                            estate.setObligor(ywr.toString());
                        }
                        System.out.println("义务人======" + estate.getObligor());
                        //业务编号没有单独传，需从受理编号截取
                        //"SLBH":"41050020180814000431"
                        StringBuilder ywbh = new StringBuilder();
                        if (!StringUtils.isEmpty(query.getSlbh())) {
                            ywbh.append(query.getSlbh().substring(8, 14)).append(query.getSlbh().substring(15));
                        }

                        // 其他
                        StringBuilder qt = new StringBuilder();
                        // 附计
                        StringBuilder fj = new StringBuilder();
                        System.out.println("业务类型=====" + query.getYwlx());
                        // 业务类型；1、预告登记 2、抵押权预告登记 3、不动产权抵押 4、在建建筑物抵押
                        if (query.getYwlx() == 1) {
                            //预告登记
                            qt.append("登记原因: ").append(query.getDjyy()).append("\r\n");
                            qt.append("共有情况: ").append(query.getGyqk()).append("\r\n");
                            if (query.getQlr().size() > 1) {
                                fj.append("   ").append("共有人").append("            ").append("份额   ").append("           ").append("关系")
                                        .append("                                 ");
                                fj.append("___________________________________").append("\r\n");
                                for (int i = 1; i < query.getQlr().size(); i++) {
                                    String gybl = "";
                                    String gygx = "";
                                    if (i == 3) {
                                        break;
                                    }
                                    if (query.getQlr().get(i).getGybl() == null) {
                                        gybl = "    ";
                                    } else {
                                        gybl = query.getQlr().get(i).getGybl();
                                    }
                                    if (query.getQlr().get(i).getGygx() == null) {
                                        gygx = "    ";
                                    } else {
                                        gygx = query.getQlr().get(i).getGygx();
                                    }
                                    fj.append("   ").append(query.getQlr().get(i).getMc()).append("                 ").append(gybl)
                                            .append("               ").append(gygx).append("\r\n");
                                }
                            }
                            fj.append("幢号").append("   房号").append("  总层").append(" 所在层").append(" 建筑面积").append(" 规划用途")
                                    .append("  建筑结构").append("\r\n");
                            fj.append("_______________________________________________").append("\r\n");
                            String szc = "";
                            if (!CollectionUtils.isEmpty(query.getBdcdyxx())) {
                                for (DjzmBdcdyxxInfo Info : query.getBdcdyxx()) {
                                    if (Info.getSzc() != "" && Info.getSzc() != null) {
                                        if (Info.getSzc().contains("第")) {
                                            szc = Info.getSzc().substring(1, Info.getSzc().length() - 1);
                                        } else {
                                            szc = Info.getSzc();
                                        }
                                    }
                                    if (Info.getJzjg() != "" && "钢、钢筋混凝土结构".equals(Info.getJzjg())) {
                                        fj.append(Info.getLd()).append("  ").append(Info.getFh()).append("   ").append(Info.getZcs()).append("     ")
                                                .append(szc).append("    ").append(Info.getMj()).append("   ").append(Info.getYt())
                                                .append("  ").append(Info.getJzjg()).append("\r\n");
                                    } else if (Info.getJzjg() != "" && Info.getJzjg().equals("钢筋混凝土结构")) {
                                        fj.append(Info.getLd()).append("  ").append(Info.getFh()).append("   ").append(Info.getZcs()).append("     ")
                                                .append(szc).append("      ").append(Info.getMj()).append("    ").append(Info.getYt())
                                                .append("    ").append(Info.getJzjg()).append("\r\n");
                                    } else {
                                        fj.append(Info.getLd()).append("  ").append(Info.getFh()).append("   ").append(Info.getZcs()).append("     ")
                                                .append(szc).append("      ").append(Info.getMj()).append("      ").append(Info.getYt())
                                                .append("       ").append(Info.getJzjg()).append("\r\n");
                                    }
                                }
                            }
                        } else if (query.getYwlx() == 2) {
                            //抵押权预告登记（预告抵押）
                            qt.append("登记原因:").append(query.getDjyy()).append("\r\n");
                            Double zqse = 0.00; // 债权数额
                            Double dymj = 0.00; // 抵押面积
                            String qsrq = ""; // 起始日期
                            String zzrq = ""; // 终止日期
                            if (query.getDyxx() != null) {
                                zqse = query.getDyxx().getZqse();
                                dymj = query.getDyxx().getDymj();
                                if (!StringUtils.isEmpty(query.getDyxx().getQsrq())) {
                                    qsrq = query.getDyxx().getQsrq().substring(0, 10).replace("-", "/");
                                }
                                if (!StringUtils.isEmpty(query.getDyxx().getZzrq())) {
                                    zzrq = query.getDyxx().getZzrq().substring(0, 10).replace("-", "/");
                                }
                            }
                            qt.append("债权数额:").append(Tool.change(zqse * 10000)).append("\r\n");
                            qt.append("抵押面积:").append(dymj).append("平方米").append("\r\n");
                            qt.append("约定履行期限:").append(qsrq)
                                    .append(" 至 ").append(zzrq)
                                    .append("\r\n");
                            //过滤掉抵押权人，从第二个list遍历
                            //接口没有传不动产权人,取义务人信息,不显示比例关系
                            if (query.getYwr().size() > 1) {
                                fj.append("            ").append("共有人").append("            ").append("份额(%)").append("           ").append("关系")
                                        .append("                                 ");
                                fj.append("_______________________________________________").append("\r\n");
                                for (int i = 1; i < query.getYwr().size(); i++) {
                                    if (i == 3) {
                                        break;
                                    } else {
                                        fj.append("            ").append(query.getYwr().get(i).getMc()).append("                                    ")
                                                .append("\r\n");
                                    }
                                }
                            }
                            fj.append("幢号").append("   房号").append("  总层").append(" 所在层").append(" 建筑面积").append(" 规划用途")
                                    .append("  建筑结构").append("\r\n");

                            fj.append("_______________________________________________").append("\r\n");
                            String szc = "";
                            if (!CollectionUtils.isEmpty(query.getBdcdyxx())) {
                                for (DjzmBdcdyxxInfo Info : query.getBdcdyxx()) {
                                    // 系统商的数据szc这个数据有的是"SZC": "16"，有的是"SZC": "第12层"
                                    if (Info.getSzc() != "" && Info.getSzc() != null) {
                                        if (Info.getSzc().contains("第")) {
                                            szc = Info.getSzc().substring(1, Info.getSzc().length() - 1);
                                        } else {
                                            szc = Info.getSzc();
                                        }
                                    }
						/*fj.append(Info.getLd()).append("  ").append(Info.getFh()).append("   ").append(Info.getZcs()).append("     ")
								.append(szc).append("      ").append(Info.getMj()).append("    ").append(Info.getYt())
								.append("  ").append(Info.getJzjg()).append("\r\n");*/
                                    if (Info.getJzjg() != "" && Info.getJzjg().equals("钢、钢筋混凝土结构")) {
                                        fj.append(Info.getLd()).append("  ").append(Info.getFh()).append("   ").append(Info.getZcs()).append("     ")
                                                .append(szc).append("    ").append(Info.getMj()).append("   ").append(Info.getYt())
                                                .append("  ").append(Info.getJzjg()).append("\r\n");
                                    } else if (Info.getJzjg() != "" && Info.getJzjg().equals("钢筋混凝土结构")) {
                                        fj.append(Info.getLd()).append("  ").append(Info.getFh()).append("   ").append(Info.getZcs()).append("     ")
                                                .append(szc).append("      ").append(Info.getMj()).append("    ").append(Info.getYt())
                                                .append("    ").append(Info.getJzjg()).append("\r\n");
                                    } else {
                                        fj.append(Info.getLd()).append("  ").append(Info.getFh()).append("   ").append(Info.getZcs()).append("     ")
                                                .append(szc).append("      ").append(Info.getMj()).append("      ").append(Info.getYt())
                                                .append("       ").append(Info.getJzjg()).append("\r\n");
                                    }
                                }
                            }
                            // 不动产权抵押（抵押权）
                        } else if (query.getYwlx() == 3) {
                            Double zqse = 0.00; // 债权数额
                            Double dymj = 0.00; // 抵押面积
                            String qsrq = ""; // 起始日期
                            String zzrq = ""; // 终止日期
                            String zwr = ""; // 债务人
                            if (query.getDyxx() != null) {
                                zqse = query.getDyxx().getZqse();
                                dymj = query.getDyxx().getDymj();
                                if (!StringUtils.isEmpty(query.getDyxx().getQsrq())) {
                                    if (query.getDyxx().getQsrq().length() == 10) {
                                        qsrq = query.getDyxx().getQsrq().substring(0, 10).replace("-", "/");
                                    }
                                }
                                if (!StringUtils.isEmpty(query.getDyxx().getZzrq())) {
                                    if (query.getDyxx().getQsrq().length() == 10) {
                                        zzrq = query.getDyxx().getZzrq().substring(0, 10).replace("-", "/");
                                    }
                                }
                                zwr = query.getDyxx().getZwr();
                            }
                            if (!CollectionUtils.isEmpty(query.getBdcdyxx())) {
                                //房屋 BDCLX=2
                                if (query.getBdcdyxx().get(0).getBdclx() == 2) {
                                    Double mj = 0.0; // 建筑面积
                                    qt.append("登记原因:").append(query.getDjyy()).append("\r\n");
                                    qt.append("债权数额:").append(Tool.change(zqse * 10000)).append("\r\n");
                                    if (!StringUtils.isEmpty(zwr)) {
                                        qt.append("债务人:").append(zwr).append("\r\n");
                                    } else {
                                        qt.append("债务人:").append("\r\n");
                                    }
                                    BigDecimal start = new BigDecimal(Double.toString(mj));
                                    for (DjzmBdcdyxxInfo Info : query.getBdcdyxx()) {
                                        BigDecimal add = new BigDecimal(Info.getMj().toString());
                                        start = start.add(add);
                                    }
                                    mj = start.doubleValue();
                                    qt.append("建筑面积:").append(mj).append("㎡").append("\r\n");
                                    qt.append("抵押面积:").append(dymj).append("㎡").append("\r\n");
                                    qt.append("约定履行期限:").append(qsrq)
                                            .append(" 至 ").append(zzrq)
                                            .append("\r\n");
                                    if (!CollectionUtils.isEmpty(query.getDybdcqr())) {
//							qt.append("不动产权证号:").append(query.getDybdcqr().get(0).getBdcqzh());
                                        fj.append("不动产权证号").append("               ").append("权利人").append("               ")
                                                .append("共有人").append("\r\n");
                                        fj.append("___________").append("             ").append("_______").append("            ")
                                                .append("_______").append("\r\n");
                                        String bdcqzh = query.getDybdcqr().get(0).getBdcqzh();
                                        String subStringYear = "";
                                        String subStringNum = "";
                                        String typeinBdcqzh = "";
                                        if (!StringUtils.isEmpty(bdcqzh)) {
                                            if (bdcqzh.contains("豫")) {
                                                subStringYear = bdcqzh.substring(bdcqzh.indexOf("（") + 1, bdcqzh.indexOf("）"));
                                                subStringNum = bdcqzh.substring(bdcqzh.indexOf("第") + 1, bdcqzh.indexOf("号"));
                                                typeinBdcqzh = subStringYear + subStringNum;
                                            } else {
                                                typeinBdcqzh = bdcqzh;
                                            }
                                        }

							/*String zwr = query.getDyxx().getZwr();
							List<String> zwrList = Arrays.asList(zwr.split(","));*/
                                        // TODO 暂时看到系统商模板多个ZWR情况，只取第一个人，下面的代码暂时注释
							/*if(zwrList.size()==1) {
								fj.append(typeinBdcqzh).append("               ")
										.append(query.getDyxx().getZwr()).append("\r\n");
							}else{
								fj.append(typeinBdcqzh).append("               ")
										.append(zwrList.get(0)).append("               ")
										.append("等"+zwrList.size()+"人").append("\r\n");
							}*/
                                        if (!CollectionUtils.isEmpty(query.getYwr())) {
                                            String mc = query.getYwr().get(0).getMc();
                                            if (query.getYwr().size() > 1) {
                                                fj.append(typeinBdcqzh).append("               ")
                                                        .append(mc).append("               ")
                                                        .append("等" + query.getYwr().size() + "人").append("\r\n");
                                            } else {
                                                fj.append(typeinBdcqzh).append("               ")
                                                        .append(mc).append("\r\n");
                                            }
                                        }
                                    }
						/*fj.append("业务编号:").append(ywbh.toString());
						if(StringUtils.isNotNullAndEmpty(query.getBz())){
							fj.append(query.getBz().replace("\r", "\r\n"));
						}*/
                                } else {
                                    //土地 BDCLX=1
                                    String djsj = ""; // 登记时间
                                    if (!StringUtils.isEmpty(query.getDjsj())) {
                                        djsj = query.getDjsj().substring(0, 10);
                                    }
                                    qt.append("抵押方式:").append(query.getDjyy()).append("\r\n");
                                    qt.append("债权数额:").append(Tool.change(zqse * 10000)).append("\r\n");
                                    qt.append("设定日期:").append(djsj).append("\r\n");
                                    qt.append("不动产权证书号:");
                                    StringBuilder zsh = new StringBuilder();
                                    for (DjzmBdcdyxxInfo info : query.getBdcdyxx()) {
                                        String bdcqzh = info.getBdcqz();
                                        String subStringYear = "";
                                        String subStringNum = "";
                                        if (bdcqzh.contains("豫")) {
                                            subStringYear = bdcqzh.substring(bdcqzh.indexOf("（") + 1, bdcqzh.indexOf("）"));
                                            subStringNum = bdcqzh.substring(bdcqzh.indexOf("第") + 1, bdcqzh.indexOf("号"));
                                        }
                                        String typeinBdcqzh = subStringYear + subStringNum;
                                        zsh.append(",").append(typeinBdcqzh);
                                    }
                                    qt.append(zsh.toString().substring(1)).append("\r\n");
                                    qt.append("债务履行期限:").append(qsrq)
                                            .append(" 至 ").append(zzrq)
                                            .append("\r\n");
                                    fj.append("根据土地他项权利登记有关规定，登记机构仅对土地使用权属来源合法性进行审查，权利人(申请人)应对该宗地的抵押或借款金额与风险性进行科学评估，并自行负责。若宗地闲置，该抵押权不影响国土部门依然对闲置土地的处置.");
                                }
                            }
                        } else if (query.getYwlx() == 4) {
                            //4 在建建筑物抵押
                            // 面积单位
                            String mjdw = "";
                            if (!CollectionUtils.isEmpty(query.getBdcdyxx())) {
                                if (query.getBdcdyxx().get(0).getMjdw() == 1) {
                                    mjdw = "平方米";
                                } else if (query.getBdcdyxx().get(0).getMjdw() == 2) {
                                    mjdw = "亩";
                                } else if (query.getBdcdyxx().get(0).getMjdw() == 3) {
                                    mjdw = "公顷";
                                }
                            }
                            Double zqse = 0.00; // 债权数额
                            Double dymj = 0.00; // 抵押面积
                            String qsrq = ""; // 起始日期
                            String zzrq = ""; // 终止日期
                            String zwr = ""; // 债务人
                            if (query.getDyxx() != null) {
                                zqse = query.getDyxx().getZqse();
                                dymj = query.getDyxx().getDymj();
                                if (!StringUtils.isEmpty(query.getDyxx().getQsrq())) {
                                    qsrq = query.getDyxx().getQsrq().substring(0, 10);
                                }
                                if (!StringUtils.isEmpty(query.getDyxx().getZzrq())) {
                                    zzrq = query.getDyxx().getZzrq().substring(0, 10);
                                }
                                zwr = query.getDyxx().getZwr();
                            }

                            qt.append("登记原因:").append(query.getDjyy()).append("\r\n");
//					Double pgjz = query.getDyxx().getPgje();
                            qt.append("债权数额:").append(Tool.change(zqse * 10000)).append("\r\n");
//					qt.append("评估价值:").append(Tool.change(pgjz*10000)).append("\r\n");
                            qt.append("债务人:").append(zwr).append("\r\n");
                            Double jzmj = 0.0;
                            BigDecimal start = new BigDecimal(Double.toString(jzmj));
                            for (DjzmBdcdyxxInfo Info : query.getBdcdyxx()) {
                                BigDecimal add = new BigDecimal(Info.getMj().toString());
                                start = start.add(add);
                            }
                            jzmj = start.doubleValue();
                            qt.append("在建建筑面积:").append(jzmj).append(mjdw).append("\r\n");
                            qt.append("抵押建筑面积:").append(dymj).append(mjdw).append("\r\n");
                            // TODO 业务类型4里面的宗地面积取哪个值，需要系统商确认
                            String zdmj = query.getBdcdyxx().get(0).getZdmj();
                            if (zdmj != "" && zdmj != null) {
                                qt.append("宗地面积:").append(zdmj + mjdw).append("\r\n");
                            } else {
                                qt.append("宗地面积:").append("0" + mjdw).append("\r\n");
                            }
                            qt.append("抵押房屋占用范围内的土地使用权一并抵押").append("\r\n");
                            qt.append("约定履行期限:").append(qsrq).append(" 至 ").append(zzrq).append("\r\n");
                            fj.append("  ");
                        }
                        estate.setOtherCases(qt.toString());
                        estate.setNotes(fj.toString());
                        // 登记坐落
                        estate.setLocated(query.getDjzl());
                        // 登簿时间
                        String djsj = "";
                        if (!StringUtils.isEmpty(query.getDjsj())) {
                            djsj = query.getDjsj().substring(0, 10);
                        }
                        estate.setRegisterTime(djsj);
                        // 证书二维码(产权证号 + 权利人)
                        StringBuilder ewm = new StringBuilder();
//				ewm.append("产权证号:").append(query.getZsbh()).append(" ").append("权利人:").append(qlr.toString().substring(1));
                        ewm.append(query.getEwm());
                        System.out.println(ewm.toString());
                        estate.setEwm(ewm.toString());
                        // 受理编号
                        estate.setBusiNumber(query.getSlbh());
                        // 业务类型
                        if(query.getYwlx()==1) {
                            estate.setBusiType("预告登记");
                        }else if(query.getYwlx()==2) {
                            estate.setBusiType("预告登记");
                        }else if (query.getYwlx()==3) {
                            estate.setBusiType("抵押权");
                        }else if(query.getYwlx()==4) {
                            estate.setBusiType("抵押权");
                        }
                        estateResult.setLocation(query.getDjzl());
                        estateResult.setEstateInfo(estate);
                        estateList.add(estateResult);
                    }
                }
            }
            queryResult.setEstateResults(estateList);
        }
        queryResult.setResultcode(estateInfoRspn.getResultcode());
        queryResult.setResultmsg(estateInfoRspn.getResultmsg());
        return queryResult;
    }

    @Override
    public void printComplete(UsageRecord usage, List<PrintRecord> completes, File recordFile) {
        // 二次登簿,回传不动产
        WriteBackReq registers = new WriteBackReq();
        // 授权码
        GetRandomString getString = new GetRandomString();
        String str = getString.GetString(RecordConstant.ParametetsConstant.RSA, 8);
        StringBuilder builder = new StringBuilder();
        String yzm = builder.append(RecordConstant.ParametetsConstant.ACCOUNT)
                .append(RecordConstant.ParametetsConstant.PASSWORD).append(str).toString();
        GetRsaString getRsa = new GetRsaString();
        String sqm = "";
        // 授权码加密操作
        try {
            sqm = getRsa.GetRsaStr(yzm);
        } catch (Exception e) {
            e.printStackTrace();
        }
        registers.setYzm(sqm);
        // 硬件码
        registers.setYjm(usage.getDeviceNumber());
        List<WriteBackDataInfo> register = new ArrayList<>();
        System.out.println("printrecord size===========" + completes.size());
        for (PrintRecord complete : completes) {
            WriteBackDataInfo data = new WriteBackDataInfo();
            // data数据
            // 受理编号
            data.setSlbh(complete.getBizNumber());
            // data.setSlbh("41080020180803000008");
            // 证书编号
            data.setZsbh(complete.getCertNumber());
            // data.setZsbh("京（2018）解放区证明号第8889088号");
            // 证书印刷编号
            data.setYsbh(complete.getSeqNumber());
            // 发证时间(打印时间)
            data.setFzsj(complete.getPrintTime());
            // 发证人(设备编号)
            data.setFzr(usage.getDeviceNumber());
            // 取件人
            data.setQjr(usage.getUserIdnumber());
            // 取件人照片(转Base64字符传输)
            data.setQjrzp(ImageFactory.getImageStr(usage.getUserDetPicPath()));
            // 取件时间
            data.setQjsj(complete.getPrintTime());
            // 证书照片(转Base64字符传输)
            List<String> zszp = new ArrayList<String>();
            zszp.add(ImageFactory.getImageStr(complete.getCertScanPath()));
            data.setZszp(zszp);
            register.add(data);
        }
        registers.setData(register);
        boolean res = BizConnector.printFinish(JacksonFactory.writeJson(registers), recordFile);
        List<PrintRecord> completeSubmit = new ArrayList<>();
        for (PrintRecord complete : completes) {
            if (res) {
                complete.setTranStatusBiz(RecordConstant.TranStatus.SUCCESS);
            } else {
                complete.setTranStatusBiz(RecordConstant.TranStatus.FAILURE);
            }
            completeSubmit.add(complete);
        }
        // 更新打印状态
        String rspnJson = null;
        PrintRecordRsp req = new PrintRecordRsp();
        req.setPrintList(completeSubmit);
        rspnJson = HttpClientFactory.getInstance().doPost(JacksonFactory.writeJson(req),
            StaticConstant.MANAGE_URL + "business/completeSubmit");
    }


    private static String testValue = "[{\r\n" +
            "		\"bookTime\": \"2019-1-1\",\r\n" +
            "		\"certNumber\": \"鄂(2019)黄冈市不动产权第0003784号\",\r\n" +
            "		\"obligee\": \"王大林\",\r\n" +
            "		\"co_ownershipCircumstance\": \"单独所有\",\r\n" +
            "		\"located\": \"黄冈市黄州区路口大道王家湾小区4栋3单元101\",\r\n" +
            "		\"unitNumber\": \"0123456789876543210\",\r\n" +
            "		\"rightTypes\": \"住户\",\r\n" +
            "		\"rightNature\": \"出让\",\r\n" +
            "		\"application\": \"办公\",\r\n" +
            "		\"area\": \"450.5平米\",\r\n" +
            "		\"serviceLife\": \"2017年3月1号到2087年3月1号\",\r\n" +
            "		\"other\": \"无\",\r\n" +
            "		\"excursus\": \"无\",\r\n" +
            "		\"fenbutu\": \"iVBORw0KGgoAAAANSUhEUgAAAvUAAAGvCAYAAADbvyqBAAAAAXNSR0IArs4c6QAAAARnQU1BAACxjwv8YQUAAAAJcEhZcwAADsMAAA7DAcdvqGQAABcKSURBVHhe7dzBql5VFoXRvMEFu3kU8/7vEAQbQgJBsaHYTnldVAqr5gbLudxwyPhgdMJtFf/aZ9qpN58lSZIkPTqjXpIkSXp4Rr0kSZL08Ix6SZIk6eEZ9ZIkSdLDM+olSZKkh2fUS5IkSQ/PqJckSZIenlEvSZIkPTyjXpIkSXp4Rr0kSZL08Ix6SZIk6eEZ9ZIkSdLDM+olSZKkh2fUS5IkSQ/PqJckSZIenlEvSZIkPTyjXpIkSXp4Rr0kSZL08Ix6SZIk6eEZ9ZIkSdLDM+olSZKkh2fUS5IkSQ/PqJckSZIenlEvSZIkPTyjXpIkSXp4Rr0kSZL08Ix6SZIk6eEZ9UVv3779/ObNG+C/vLy8xH8H/j53BdnrHpNRX/X6Q/r555+B/+I2YJ+7guz1NmTUV3lgIXMbsM9dQWbUT/5XKPLAQvZ6G58+ffoi/Q3w//HNgez1Nn777bcvvtaM+iIPLGSvt/Hdd999kf4G+P/45kD2ehvv37//4mvNqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MO+d+/e/XFbwJ+9vLwY9b9n1Be9/pDSwwtfu9fbMOrhn/Phw4c/3Rh8zV6/OUa9UV9l1ENm1MM/y6iH/zDqJ6O+yKiHzKiHf5ZRD/9h1E9GfZFRD5lRD/+sH3/88fOnT5+A371+cz5+/PjF15pRX2TUQ2bUA3DL6zfn9T90/+1rzagvMuohM+oBuMWon4z6IqMeMqMegFuM+smoLzLqITPqAbjFqJ+M+iKjHjKjHoBbjPrJqC8y6iEz6gG4xaifjPoiox4yox6AW4z6yagvMuohM+oBuMWon4z6IqMeMqMegFuM+smoLzLqITPqAbjFqJ+M+iKjHjKjHoBbjPrJqC8y6iEz6gG4xaifjPoiox4yox6AW4z6yagvMuohM+oBuMWon4z6IqMeMqMegFuM+smoLzLqITPqAbjFqJ+M+iKjHjKjHoBbjPrJqC8y6iEz6gG4xaifjPoiox4yox6AW4z6yagvMuohM+oBuMWon4z6IqMeMqMegFuM+smoLzLqITPqAbjFqJ+M+iKjHjKjHoBbjPrJqC8y6iEz6gG4xaifjPoiox4yox6AW4z6yagvMuohM+oBuMWon4z6IqMeMqMegFuM+smoLzLqITPqAbjFqJ+M+iKjHjKjHoBbjPrJqC8y6iEz6gG4xaifjPoiox4yox6AW4z6yagvMuohM+oBuMWon4z6IqMeMqMegFuM+smoLzLqITPqAbjFqJ+M+iKjHjKjHoBbjPrJqC8y6iEz6gG4xaifjPoiox4yox6AW4z6yagvMuohM+oBuMWon4z6IqMeMqMegFuM+smoLzLqITPqAbjFqJ+M+iKjHjKjHoBbjPrJqC8y6iEz6gG4xaifjPoiox4yox6AW4z6yagvMuohM+oBuMWon4z6IqMeMqMegFuM+smoLzLqITPqAbjFqJ+M+iKjHjKjHoBbjPrJqC8y6iEz6gG4xaifjPoiox4yox6AW4z6yagvMuohM+oBuMWon4z6IqMeMqMegFuM+smoLzLqITPqAbjFqJ+M+iKjHjKjHoBbjPrJqC8y6iEz6gG4xaifjPoiox4yox6AW4z6yagvMuohM+oBuMWon4z6IqMeMqMegFuM+smoLzLqITPqAbjFqJ+M+iKjHjKjHoBbjPrJqC8y6iEz6gG4xaifjPoiox4yox6AW4z6yagvMuohM+oBuMWon4z6IqMeMqMegFuM+smoLzLqITPqAbjFqJ+M+iKjHjKjHoBbjPrJqC8y6iEz6gG4xaifjPoiox4yox6AW4z6yagvMuohM+oBuMWon4z6IqMeMqMegFuM+smoLzLqITPqAbjFqJ+M+iKjHjKjHoBbjPrJqC8y6iEz6gG4xaifjPoiox4yox6AW4z6yagvMuohM+oBuMWon4z6IqMeMqMegFuM+smoLzLqITPqAbjFqJ+M+iKjHjKjHoBbjPrJqC8y6iEz6gG4xaifjPoiox4yox6AW4z6yagvMuohM+oBuMWon4z6IqMeMqMegFuM+smoLzLqITPqAbjFqJ+M+iKjHjKjHoBbjPrJqC8y6iEz6gG4xaifjPoiox4yox6AW4z6yagvMuohM+oBuMWon4z6IqMeMqMegFuM+smoLzLqITPqAbjFqJ+M+iKjHjKjHoBbjPrJqC8y6iEz6gG4xaifjPoiox4yox6AW4z6yagvMuohM+oBuMWon4z6IqMeMqMegFuM+smoLzLqITPqAbjFqJ+M+iKjHjKjHoBbjPrJqC8y6iEz6gG4xaifjPoiox4yox6AW4z6yagvMuohM+oBuMWon4z6IqMeMqMegFuM+smoLzLqITPqAbjFqJ+M+iKj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\",\r\n" +
            "		\"zongditu\": \"iVBORw0KGgoAAAANSUhEUgAAApgAAAGRCAYAAADB1sOcAAAAAXNSR0IArs4c6QAAAARnQU1BAACxjwv8YQUAAAAJcEhZcwAADsMAAA7DAcdvqGQAAAvfSURBVHhe7dbLSSgIFERB83Jh/rEIggsXgp9BeUwEl6HPmzpQEfSmH74lSZKkwxxMSZIkneZgSpIk6TQHU5IkSac5mJIkSTrNwZQkSdJpDqYkSZJOczAlSZJ0moMpSZKk0xxMSZIkneZgSpIk6TQHU5IkSac5mJIkSTrNwZQkSdJpDqYkSZJOczAlSZJ0moMpSZKk0xxMSZIkneZgSpIk6TQHU5IkSac5mJIkSTrNwZQkSdJpDqYkSZJOczAlSZJ0moMpSZKk0xxMSZIknfZXH8ynp6fvh4cHAICMn/9S768+mD8jvby8AABk/PyXeg4mAMAQB3M8BxMAqPn5L29vb78+Pj7+vJpWDiYAwJCf//L8/Pzr/f39z6tp5WACAAxxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzvKenp9+RAAAqHh8fHcxKr6+v/44FAFDgYI7nYAIANQ7meA4mAFDjYI739fX1/fn5CQCQ8fNfiv1vDqYkSZL+mxxMSZIkneZgSpIk6TQHU5IkSac5mJIkSTrNwZQkSdJpDqYkSZJOczAlSZJ0moMpSZKk0xxMSZIkneZgSpIk6TQHU5IkSac5mJIkSTrNwZQkSdJpDqYkSZJOczAlSZJ0moMpSZKk0xxMSZIkneZgSpIk6TQHU5IkSac5mJIkSTrNwZQkSdJpDqYkSZJOczAlSZJ0moMpSZKk0xxMSZIkneZgSpIk6TQHU5IkSac5mJIkSTrNwZQkSdJpDqYkSZJOczAlSZJ0moMpSZKk0xxMSZIkneZgSpIk6TQHU5IkSac5mJIkSTrs+/sfaSF82dkHu7gAAAAASUVORK5CYII=\",\r\n" +
            "		\"paymentStatus\": true,\r\n" +
            "		\"cost\": \"1000\"\r\n" +
            "	}, {\r\n" +
            "		\"bookTime\": \"2019-1-2\",\r\n" +
            "		\"certNumber\": \"鄂(2019)黄冈市不动产权第0003785号\",\r\n" +
            "		\"obligee\": \"李爱民\",\r\n" +
            "		\"co_ownershipCircumstance\": \"单独所有\",\r\n" +
            "		\"located\": \"黄冈市黄州区路口大道王家湾小区11栋3单元51\",\r\n" +
            "		\"unitNumber\": \"0123456789876543211\",\r\n" +
            "		\"rightTypes\": \"住户\",\r\n" +
            "		\"rightNature\": \"出让\",\r\n" +
            "		\"application\": \"办公\",\r\n" +
            "		\"area\": \"250.5平米\",\r\n" +
            "		\"serviceLife\": \"2016年3月12号到2077年12月1号\",\r\n" +
            "		\"other\": \"无\",\r\n" +
            "		\"excursus\": \"无\",\r\n" +
            "		\"fenbutu\": \"iVBORw0KGgoAAAANSUhEUgAAAvUAAAGvCAYAAADbvyqBAAAAAXNSR0IArs4c6QAAAARnQU1BAACxjwv8YQUAAAAJcEhZcwAADsMAAA7DAcdvqGQAABcKSURBVHhe7dzBql5VFoXRvMEFu3kU8/7vEAQbQgJBsaHYTnldVAqr5gbLudxwyPhgdMJtFf/aZ9qpN58lSZIkPTqjXpIkSXp4Rr0kSZL08Ix6SZIk6eEZ9ZIkSdLDM+olSZKkh2fUS5IkSQ/PqJckSZIenlEvSZIkPTyjXpIkSXp4Rr0kSZL08Ix6SZIk6eEZ9ZIkSdLDM+olSZKkh2fUS5IkSQ/PqJckSZIenlEvSZIkPTyjXpIkSXp4Rr0kSZL08Ix6SZIk6eEZ9ZIkSdLDM+olSZKkh2fUS5IkSQ/PqJckSZIenlEvSZIkPTyjXpIkSXp4Rr0kSZL08Ix6SZIk6eEZ9UVv3779/ObNG+C/vLy8xH8H/j53BdnrHpNRX/X6Q/r555+B/+I2YJ+7guz1NmTUV3lgIXMbsM9dQWbUT/5XKPLAQvZ6G58+ffoi/Q3w//HNgez1Nn777bcvvtaM+iIPLGSvt/Hdd999kf4G+P/45kD2ehvv37//4mvNqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MO+d+/e/XFbwJ+9vLwY9b9n1Be9/pDSwwtfu9fbMOrhn/Phw4c/3Rh8zV6/OUa9UV9l1ENm1MM/y6iH/zDqJ6O+yKiHzKiHf5ZRD/9h1E9GfZFRD5lRD/+sH3/88fOnT5+A371+cz5+/PjF15pRX2TUQ2bUA3DL6zfn9T90/+1rzagvMuohM+oBuMWon4z6IqMeMqMegFuM+smoLzLqITPqAbjFqJ+M+iKjHjKjHoBbjPrJqC8y6iEz6gG4xaifjPoiox4yox6AW4z6yagvMuohM+oBuMWon4z6IqMeMqMegFuM+smoLzLqITPqAbjFqJ+M+iKjHjKjHoBbjPrJqC8y6iEz6gG4xaifjPoiox4yox6AW4z6yagvMuohM+oBuMWon4z6IqMeMqMegFuM+smoLzLqITPqAbjFqJ+M+iKjHjKjHoBbjPrJqC8y6iEz6gG4xaifjPoiox4yox6AW4z6yagvMuohM+oBuMWon4z6IqMeMqMegFuM+smoLzLqITPqAbjFqJ+M+iKjHjKjHoBbjPrJqC8y6iEz6gG4xaifjPoiox4yox6AW4z6yagvMuohM+oBuMWon4z6IqMeMqMegFuM+smoLzLqITPqAbjFqJ+M+iKjHjKjHoBbjPrJqC8y6iEz6gG4xaifjPoiox4yox6AW4z6yagvMuohM+oBuMWon4z6IqMeMqMegFuM+smoLzLqITPqAbjFqJ+M+iKjHjKjHoBbjPrJqC8y6iEz6gG4xaifjPoiox4yox6AW4z6yagvMuohM+oBuMWon4z6IqMeMqMegFuM+smoLzLqITPqAbjFqJ+M+iKjHjKjHoBbjPrJqC8y6iEz6gG4xaifjPoiox4yox6AW4z6yagvMuohM+oBuMWon4z6IqMeMqMegFuM+smoLzLqITPqAbjFqJ+M+iKjHjKjHoBbjPrJqC8y6iEz6gG4xaifjPoiox4yox6AW4z6yagvMuohM+oBuMWon4z6IqMeMqMegFuM+smoLzLqITPqAbjFqJ+M+iKjHjKjHoBbjPrJqC8y6iEz6gG4xaifjPoiox4yox6AW4z6yagvMuohM+oBuMWon4z6IqMeMqMegFuM+smoLzLqITPqAbjFqJ+M+iKjHjKjHoBbjPrJqC8y6iEz6gG4xaifjPoiox4yox6AW4z6yagvMuohM+oBuMWon4z6IqMeMqMegFuM+smoLzLqITPqAbjFqJ+M+iKjHjKjHoBbjPrJqC8y6iEz6gG4xaifjPoiox4yox6AW4z6yagvMuohM+oBuMWon4z6IqMeMqMegFuM+smoLzLqITPqAbjFqJ+M+iKjHjKjHoBbjPrJqC8y6iEz6gG4xaifjPoiox4yox6AW4z6yagvMuohM+oBuMWon4z6IqMeMqMegFuM+smoLzLqITPqAbjFqJ+M+iKjHjKjHoBbjPrJqC8y6iEz6gG4xaifjPoiox4yox6AW4z6yagvMuohM+oBuMWon4z6IqMeMqMegFuM+smoLzLqITPqAbjFqJ+M+iKjHjKjHoBbjPrJqC8y6iEz6gG4xaifjPoiox4yox6AW4z6yagvMuohM+oBuMWon4z6IqMeMqMegFuM+smoLzLqITPqAbjFqJ+M+iKjHjKjHoBbjPrJqC8y6iEz6gG4xaifjPoiox4yox6AW4z6yagvMuohM+oBuMWon4z6IqMeMqMegFuM+smoLzLqITPqAbjFqJ+M+iKjHjKjHoBbjPrJqC8y6iEz6gG4xaifjPoiox4yox6AW4z6yagvMuohM+oBuMWon4z6IqMeMqMegFuM+smoLzLqITPqAbjFqJ+M+iKj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\",\r\n" +
            "		\"zongditu\": \"iVBORw0KGgoAAAANSUhEUgAAApgAAAGRCAYAAADB1sOcAAAAAXNSR0IArs4c6QAAAARnQU1BAACxjwv8YQUAAAAJcEhZcwAADsMAAA7DAcdvqGQAAAvfSURBVHhe7dbLSSgIFERB83Jh/rEIggsXgp9BeUwEl6HPmzpQEfSmH74lSZKkwxxMSZIkneZgSpIk6TQHU5IkSac5mJIkSTrNwZQkSdJpDqYkSZJOczAlSZJ0moMpSZKk0xxMSZIkneZgSpIk6TQHU5IkSac5mJIkSTrNwZQkSdJpDqYkSZJOczAlSZJ0moMpSZKk0xxMSZIkneZgSpIk6TQHU5IkSac5mJIkSTrNwZQkSdJpDqYkSZJOczAlSZJ0moMpSZKk0xxMSZIknfZXH8ynp6fvh4cHAICMn/9S768+mD8jvby8AABk/PyXeg4mAMAQB3M8BxMAqPn5L29vb78+Pj7+vJpWDiYAwJCf//L8/Pzr/f39z6tp5WACAAxxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzvKenp9+RAAAqHh8fHcxKr6+v/44FAFDgYI7nYAIANQ7meA4mAFDjYI739fX1/fn5CQCQ8fNfiv1vDqYkSZL+mxxMSZIkneZgSpIk6TQHU5IkSac5mJIkSTrNwZQkSdJpDqYkSZJOczAlSZJ0moMpSZKk0xxMSZIkneZgSpIk6TQHU5IkSac5mJIkSTrNwZQkSdJpDqYkSZJOczAlSZJ0moMpSZKk0xxMSZIkneZgSpIk6TQHU5IkSac5mJIkSTrNwZQkSdJpDqYkSZJOczAlSZJ0moMpSZKk0xxMSZIkneZgSpIk6TQHU5IkSac5mJIkSTrNwZQkSdJpDqYkSZJOczAlSZJ0moMpSZKk0xxMSZIkneZgSpIk6TQHU5IkSac5mJIkSTrs+/sfaSF82dkHu7gAAAAASUVORK5CYII=\",\r\n" +
            "		\"paymentStatus\": true,\r\n" +
            "		\"cost\": \"1000\"\r\n" +
            "	}, {\r\n" +
            "		\"bookTime\": \"2019-1-2\",\r\n" +
            "		\"certNumber\": \"鄂(2019)黄冈市不动产权第0003786号\",\r\n" +
            "		\"obligee\": \"张建军\",\r\n" +
            "		\"co_ownershipCircumstance\": \"单独所有\",\r\n" +
            "		\"located\": \"黄冈市黄州区路口大道王家湾小区9栋2单元601\",\r\n" +
            "		\"unitNumber\": \"0123456789876543211\",\r\n" +
            "		\"rightTypes\": \"住户\",\r\n" +
            "		\"rightNature\": \"出让\",\r\n" +
            "		\"application\": \"办公\",\r\n" +
            "		\"area\": \"250.5平米\",\r\n" +
            "		\"serviceLife\": \"2016年3月12号到2077年12月1号\",\r\n" +
            "		\"other\": \"无\",\r\n" +
            "		\"excursus\": \"无\",\r\n" +
            "		\"fenbutu\": \"iVBORw0KGgoAAAANSUhEUgAAAvUAAAGvCAYAAADbvyqBAAAAAXNSR0IArs4c6QAAAARnQU1BAACxjwv8YQUAAAAJcEhZcwAADsMAAA7DAcdvqGQAABcKSURBVHhe7dzBql5VFoXRvMEFu3kU8/7vEAQbQgJBsaHYTnldVAqr5gbLudxwyPhgdMJtFf/aZ9qpN58lSZIkPTqjXpIkSXp4Rr0kSZL08Ix6SZIk6eEZ9ZIkSdLDM+olSZKkh2fUS5IkSQ/PqJckSZIenlEvSZIkPTyjXpIkSXp4Rr0kSZL08Ix6SZIk6eEZ9ZIkSdLDM+olSZKkh2fUS5IkSQ/PqJckSZIenlEvSZIkPTyjXpIkSXp4Rr0kSZL08Ix6SZIk6eEZ9ZIkSdLDM+olSZKkh2fUS5IkSQ/PqJckSZIenlEvSZIkPTyjXpIkSXp4Rr0kSZL08Ix6SZIk6eEZ9UVv3779/ObNG+C/vLy8xH8H/j53BdnrHpNRX/X6Q/r555+B/+I2YJ+7guz1NmTUV3lgIXMbsM9dQWbUT/5XKPLAQvZ6G58+ffoi/Q3w//HNgez1Nn777bcvvtaM+iIPLGSvt/Hdd999kf4G+P/45kD2ehvv37//4mvNqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MO+d+/e/XFbwJ+9vLwY9b9n1Be9/pDSwwtfu9fbMOrhn/Phw4c/3Rh8zV6/OUa9UV9l1ENm1MM/y6iH/zDqJ6O+yKiHzKiHf5ZRD/9h1E9GfZFRD5lRD/+sH3/88fOnT5+A371+cz5+/PjF15pRX2TUQ2bUA3DL6zfn9T90/+1rzagvMuohM+oBuMWon4z6IqMeMqMegFuM+smoLzLqITPqAbjFqJ+M+iKjHjKjHoBbjPrJqC8y6iEz6gG4xaifjPoiox4yox6AW4z6yagvMuohM+oBuMWon4z6IqMeMqMegFuM+smoLzLqITPqAbjFqJ+M+iKjHjKjHoBbjPrJqC8y6iEz6gG4xaifjPoiox4yox6AW4z6yagvMuohM+oBuMWon4z6IqMeMqMegFuM+smoLzLqITPqAbjFqJ+M+iKjHjKjHoBbjPrJqC8y6iEz6gG4xaifjPoiox4yox6AW4z6yagvMuohM+oBuMWon4z6IqMeMqMegFuM+smoLzLqITPqAbjFqJ+M+iKjHjKjHoBbjPrJqC8y6iEz6gG4xaifjPoiox4yox6AW4z6yagvMuohM+oBuMWon4z6IqMeMqMegFuM+smoLzLqITPqAbjFqJ+M+iKjHjKjHoBbjPrJqC8y6iEz6gG4xaifjPoiox4yox6AW4z6yagvMuohM+oBuMWon4z6IqMeMqMegFuM+smoLzLqITPqAbjFqJ+M+iKjHjKjHoBbjPrJqC8y6iEz6gG4xaifjPoiox4yox6AW4z6yagvMuohM+oBuMWon4z6IqMeMqMegFuM+smoLzLqITPqAbjFqJ+M+iKjHjKjHoBbjPrJqC8y6iEz6gG4xaifjPoiox4yox6AW4z6yagvMuohM+oBuMWon4z6IqMeMqMegFuM+smoLzLqITPqAbjFqJ+M+iKjHjKjHoBbjPrJqC8y6iEz6gG4xaifjPoiox4yox6AW4z6yagvMuohM+oBuMWon4z6IqMeMqMegFuM+smoLzLqITPqAbjFqJ+M+iKjHjKjHoBbjPrJqC8y6iEz6gG4xaifjPoiox4yox6AW4z6yagvMuohM+oBuMWon4z6IqMeMqMegFuM+smoLzLqITPqAbjFqJ+M+iKjHjKjHoBbjPrJqC8y6iEz6gG4xaifjPoiox4yox6AW4z6yagvMuohM+oBuMWon4z6IqMeMqMegFuM+smoLzLqITPqAbjFqJ+M+iKjHjKjHoBbjPrJqC8y6iEz6gG4xaifjPoiox4yox6AW4z6yagvMuohM+oBuMWon4z6IqMeMqMegFuM+smoLzLqITPqAbjFqJ+M+iKjHjKjHoBbjPrJqC8y6iEz6gG4xaifjPoiox4yox6AW4z6yagvMuohM+oBuMWon4z6IqMeMqMegFuM+smoLzLqITPqAbjFqJ+M+iKjHjKjHoBbjPrJqC8y6iEz6gG4xaifjPoiox4yox6AW4z6yagvMuohM+oBuMWon4z6IqMeMqMegFuM+smoLzLqITPqAbjFqJ+M+iKjHjKjHoBbjPrJqC8y6iEz6gG4xaifjPoiox4yox6AW4z6yagvMuohM+oBuMWon4z6IqMeMqMegFuM+smoLzLqITPqAbjFqJ+M+iKjHjKjHoBbjPrJqC8y6iEz6gG4xaifjPoiox4yox6AW4z6yagvMuohM+oBuMWon4z6IqMeMqMegFuM+smoLzLqITPqAbjFqJ+M+iKjHjKjHoBbjPrJqC8y6iEz6gG4xaifjPoiox4yox6AW4z6yagvMuohM+oBuMWon4z6IqMeMqMegFuM+smoLzLqITPqAbjFqJ+M+iKjHjKjHoBbjPrJqC8y6iEz6gG4xaifjPoiox4yox6AW4z6yagvMuohM+oBuMWon4z6IqMeMqMegFuM+smoLzLqITPqAbjFqJ+M+iKjHjKjHoBbjPrJqC8y6iEz6gG4xaifjPoiox4yox6AW4z6yagvMuohM+oBuMWon4z6IqMeMqMegFuM+smoLzLqITPqAbjFqJ+M+iKjHjKjHoBbjPrJqC8y6iEz6gG4xaifjPoiox4yox6AW4z6yagvMuohe3l5+eM+gP/17t27eDfA3/N6V0a9UV/1+iNKPy742rkNOHMfsMuon4z6Ig8zZG4DztwH7DLqJ6O+yMMMmduAM/cBu4z6yagv8jBD5jbg7PU+vv/++z98/Pgx/g3w1xn1k1FfZLhA5jbg7PU+/v3/DPXhw4f4N8BfZ9RPRn2R4QKZ24Azox52GfWTUV9kuEDmNuDMqIddRv1k1BcZLpC5DTgz6mGXUT8Z9UWGC2RuA86Methl1E9GfZHhApnbgDOjHnYZ9ZNRX2S4QOY24Myoh11G/WTUFxkukLkNODPqYZdRPxn1RYYLZG4Dzox62GXUT0Z9keECmduAM6Medhn1k1FfZLhA5jbgzKiHXUb9ZNQXGS6QuQ04M+phl1E/GfVFhgtkbgPOjHrYZdRPRn2R4QKZ24Azox52GfWTUV9kuEDmNuDMqIddRv1k1BcZLpC5DTgz6mGXUT8Z9UWGC2RuA86Methl1E9GfZHhApnbgDOjHnYZ9ZNRX2S4QOY24Myoh11G/WTUFxkukLkNODPqYZdRPxn1RYYLZG4Dzox62GXUT0Z9keECmduAM6Medhn1k1FfZLhA5jbgzKiHXUb9ZNQXGS6QuQ04M+phl1E/GfVFhgtkbgPOjHrYZdRPRn2R4QKZ24Azox52GfWTUV9kuEDmNuDMqIddRv1k1BcZLpC5DTgz6mGXUT8Z9UWGC2RuA86Methl1E9GfZHhApnbgDOjHnYZ9ZNRX2S4QOY24Myoh11G/WTUFxkukLkNODPqYZdRPxn1RYYLZG4Dzox62GXUT0Z9keECmduAM6Medhn1k1FfZLhA5jbgzKiHXUb9ZNQXGS6QuQ04M+phl1E/GfVFhgtkbgPOjHrYZdRPRn2R4QKZ24Azox52GfWTUV9kuEDmNuDMqIddRv1k1BcZLpC5DTgz6mGXUT8Z9UWGC2RuA86Methl1E9GfZHhApnbgDOjHnYZ9ZNRX2S4QOY24Myoh11G/WTUFxkukLkNODPqYZdRPxn1RYYLZG4Dzox62GXUT0Z9keECmduAM6Medhn1k1FfZLhA5jbgzKiHXUb9ZNQXGS6QuQ04M+phl1E/GfVFhgtkbgPOjHrYZdRPRn2R4QKZ24Azox52GfWTUV9kuEDmNuDMqIddRv1k1BcZLpC5DTgz6mGXUT8Z9UWGC2RuA86Methl1E9GfZHhApnbgDOjHnYZ9ZNRX2S4QOY24Myoh11G/WTUFxkukLkNODPqYZdRPxn1RYYLZG4Dzox62GXUT0Z9keECmduAM6Medhn1k1FfZLhA5jbgzKiHXUb9ZNQXGS6QuQ04M+phl1E/GfVFhgtkbgPOjHrYZdRPRn2R4QKZ24Azox52GfWTUV9kuEDmNuDMqIddRv1k1BcZLpC5DTgz6mGXUT8Z9UWGC2RuA86Methl1E9GfZHhApnbgDOjHnYZ9ZNRX2S4QOY24Myoh11G/WTUFxkukLkNODPqYZdRPxn1RYYLZG4Dzox62GXUT0Z9keECmduAM6Medhn1k1FfZLhA5jbgzKiHXUb9ZNQXGS6QuQ04M+phl1E/GfVFhgtkbgPOjHrYZdRPRn2R4QKZ24Azox52GfWTUV9kuEDmNuDMqIddRv1k1BcZLpC5DTgz6mGXUT8Z9UWGC2RuA86Methl1E9GfZHhApnbgDOjHnYZ9ZNRX2S4QOY24Myoh11G/WTUFxkukLkNODPqYZdRPxn1RYYLZG4Dzox62GXUT0Z9keECmduAM6Medhn1k1FfZLhA5jbgzKiHXUb9ZNQXGS6QuQ04M+phl1E/GfVFhgtkbgPOjHrYZdRPRn2R4QKZ24Azox52GfWTUV9kuEDmNuDMqIddRv1k1BcZLpC5DTgz6mGXUT8Z9UWGC2RuA86Methl1E9GfZHhApnbgDOjHnYZ9ZNRX2S4QOY24Myoh11G/WTUFxkukLkNODPqYZdRPxn1RYYLZG4Dzox62GXUT0Z9keECmduAM6Medhn1k1FfZLhA5jbgzKiHXUb9ZNQXGS6QuQ04M+phl1E/GfVFhgtkbgPOjHrYZdRPRn2R4QKZ24Azox52GfWTUV9kuEDmNuDMqIddRv1k1BcZLpC5DTgz6mGXUT8Z9UWGC2RuA86Methl1E9GfZHhApnbgDOjHnYZ9ZNRX2S4QOY24Myoh11G/WTUFxkukLkNODPqYZdRPxn1RYYLZG4Dzox62GXUT0Z9keECmduAM6Medhn1k1FfZLhA5jbgzKiHXUb9ZNQXGS6QuQ04M+phl1E/GfVFhgtkbgPOjHrYZdRPRn2R4QKZ24Azox52GfWTUV9kuEDmNuDMqIddRv1k1BcZLpC5DTgz6mGXUT8Z9UWGC2RuA86Methl1E9GfZHhApnbgDOjHnYZ9ZNRX2S4QOY24Myoh11G/WTUFxkukLkNODPqYZdRPxn1RYYLZG4Dzox62GXUT0Z9keECmduAM6Medhn1k1Ff9Pbt2z9+SMCfffPNN/HfgTefX15e4r8Df8+3335r1P+eUb/U64/o/fv3APCX/fDDD38aI0Dva82oX+r1R5QebAA4Meph39eaUb/Ur7/++vnjx48A8Jf99NNPn3/55Rdg0deaUS9JkiQ9PKNekiRJenhGvSRJkvTwjHpJkiTp4Rn1kiRJ0sMz6iVJkqSHZ9RLkiRJD8+olyRJkh6eUS9JkiQ9PKNekiRJenhGvSRJkvTwjHpJkiTp4Rn1kiRJ0sMz6iVJkqSHZ9RLkiRJD8+olyRJkh6eUS9JkiQ9PKNekiRJenhGvSRJkvTwjHpJkiTp4Rn1kiRJ0sMz6iVJkqSHZ9RLkiRJD8+olyRJkh6eUS9JkiQ9PKNekiRJenhGvSRJkvTwjHpJkiTp4Rn1kiRJ0sMz6iVJkqSHZ9RLkiRJD8+olyRJkh6eUS9JkiQ9PKNekiRJenSfP/8LRbozq4j1feUAAAAASUVORK5CYII=\",\r\n" +
            "		\"zongditu\": \"iVBORw0KGgoAAAANSUhEUgAAApgAAAGRCAYAAADB1sOcAAAAAXNSR0IArs4c6QAAAARnQU1BAACxjwv8YQUAAAAJcEhZcwAADsMAAA7DAcdvqGQAAAvfSURBVHhe7dbLSSgIFERB83Jh/rEIggsXgp9BeUwEl6HPmzpQEfSmH74lSZKkwxxMSZIkneZgSpIk6TQHU5IkSac5mJIkSTrNwZQkSdJpDqYkSZJOczAlSZJ0moMpSZKk0xxMSZIkneZgSpIk6TQHU5IkSac5mJIkSTrNwZQkSdJpDqYkSZJOczAlSZJ0moMpSZKk0xxMSZIkneZgSpIk6TQHU5IkSac5mJIkSTrNwZQkSdJpDqYkSZJOczAlSZJ0moMpSZKk0xxMSZIknfZXH8ynp6fvh4cHAICMn/9S768+mD8jvby8AABk/PyXeg4mAMAQB3M8BxMAqPn5L29vb78+Pj7+vJpWDiYAwJCf//L8/Pzr/f39z6tp5WACAAxxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzvKenp9+RAAAqHh8fHcxKr6+v/44FAFDgYI7nYAIANQ7meA4mAFDjYI739fX1/fn5CQCQ8fNfiv1vDqYkSZL+mxxMSZIkneZgSpIk6TQHU5IkSac5mJIkSTrNwZQkSdJpDqYkSZJOczAlSZJ0moMpSZKk0xxMSZIkneZgSpIk6TQHU5IkSac5mJIkSTrNwZQkSdJpDqYkSZJOczAlSZJ0moMpSZKk0xxMSZIkneZgSpIk6TQHU5IkSac5mJIkSTrNwZQkSdJpDqYkSZJOczAlSZJ0moMpSZKk0xxMSZIkneZgSpIk6TQHU5IkSac5mJIkSTrNwZQkSdJpDqYkSZJOczAlSZJ0moMpSZKk0xxMSZIkneZgSpIk6TQHU5IkSac5mJIkSTrs+/sfaSF82dkHu7gAAAAASUVORK5CYII=\",\r\n" +
            "		\"paymentStatus\": true,\r\n" +
            "		\"cost\": \"1000\"\r\n" +
            "	}, {\r\n" +
            "		\"bookTime\": \"2019-1-2\",\r\n" +
            "		\"certNumber\": \"鄂(2019)黄冈市不动产权第0003787号\",\r\n" +
            "		\"obligee\": \"王小军\",\r\n" +
            "		\"co_ownershipCircumstance\": \"单独所有\",\r\n" +
            "		\"located\": \"黄冈市黄州区路口大道王家湾小区9栋1单元303\",\r\n" +
            "		\"unitNumber\": \"0123456789876543211\",\r\n" +
            "		\"rightTypes\": \"住户\",\r\n" +
            "		\"rightNature\": \"出让\",\r\n" +
            "		\"application\": \"办公\",\r\n" +
            "		\"area\": \"250.5平米\",\r\n" +
            "		\"serviceLife\": \"2016年3月12号到2077年12月1号\",\r\n" +
            "		\"other\": \"无\",\r\n" +
            "		\"excursus\": \"无\",\r\n" +
            "		\"fenbutu\": \"iVBORw0KGgoAAAANSUhEUgAAAvUAAAGvCAYAAADbvyqBAAAAAXNSR0IArs4c6QAAAARnQU1BAACxjwv8YQUAAAAJcEhZcwAADsMAAA7DAcdvqGQAABcKSURBVHhe7dzBql5VFoXRvMEFu3kU8/7vEAQbQgJBsaHYTnldVAqr5gbLudxwyPhgdMJtFf/aZ9qpN58lSZIkPTqjXpIkSXp4Rr0kSZL08Ix6SZIk6eEZ9ZIkSdLDM+olSZKkh2fUS5IkSQ/PqJckSZIenlEvSZIkPTyjXpIkSXp4Rr0kSZL08Ix6SZIk6eEZ9ZIkSdLDM+olSZKkh2fUS5IkSQ/PqJckSZIenlEvSZIkPTyjXpIkSXp4Rr0kSZL08Ix6SZIk6eEZ9ZIkSdLDM+olSZKkh2fUS5IkSQ/PqJckSZIenlEvSZIkPTyjXpIkSXp4Rr0kSZL08Ix6SZIk6eEZ9UVv3779/ObNG+C/vLy8xH8H/j53BdnrHpNRX/X6Q/r555+B/+I2YJ+7guz1NmTUV3lgIXMbsM9dQWbUT/5XKPLAQvZ6G58+ffoi/Q3w//HNgez1Nn777bcvvtaM+iIPLGSvt/Hdd999kf4G+P/45kD2ehvv37//4mvNqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MO+d+/e/XFbwJ+9vLwY9b9n1Be9/pDSwwtfu9fbMOrhn/Phw4c/3Rh8zV6/OUa9UV9l1ENm1MM/y6iH/zDqJ6O+yKiHzKiHf5ZRD/9h1E9GfZFRD5lRD/+sH3/88fOnT5+A371+cz5+/PjF15pRX2TUQ2bUA3DL6zfn9T90/+1rzagvMuohM+oBuMWon4z6IqMeMqMegFuM+smoLzLqITPqAbjFqJ+M+iKjHjKjHoBbjPrJqC8y6iEz6gG4xaifjPoiox4yox6AW4z6yagvMuohM+oBuMWon4z6IqMeMqMegFuM+smoLzLqITPqAbjFqJ+M+iKjHjKjHoBbjPrJqC8y6iEz6gG4xaifjPoiox4yox6AW4z6yagvMuohM+oBuMWon4z6IqMeMqMegFuM+smoLzLqITPqAbjFqJ+M+iKjHjKjHoBbjPrJqC8y6iEz6gG4xaifjPoiox4yox6AW4z6yagvMuohM+oBuMWon4z6IqMeMqMegFuM+smoLzLqITPqAbjFqJ+M+iKjHjKjHoBbjPrJqC8y6iEz6gG4xaifjPoiox4yox6AW4z6yagvMuohM+oBuMWon4z6IqMeMqMegFuM+smoLzLqITPqAbjFqJ+M+iKjHjKjHoBbjPrJqC8y6iEz6gG4xaifjPoiox4yox6AW4z6yagvMuohM+oBuMWon4z6IqMeMqMegFuM+smoLzLqITPqAbjFqJ+M+iKjHjKjHoBbjPrJqC8y6iEz6gG4xaifjPoiox4yox6AW4z6yagvMuohM+oBuMWon4z6IqMeMqMegFuM+smoLzLqITPqAbjFqJ+M+iKjHjKjHoBbjPrJqC8y6iEz6gG4xaifjPoiox4yox6AW4z6yagvMuohM+oBuMWon4z6IqMeMqMegFuM+smoLzLqITPqAbjFqJ+M+iKjHjKjHoBbjPrJqC8y6iEz6gG4xaifjPoiox4yox6AW4z6yagvMuohM+oBuMWon4z6IqMeMqMegFuM+smoLzLqITPqAbjFqJ+M+iKjHjKjHoBbjPrJqC8y6iEz6gG4xaifjPoiox4yox6AW4z6yagvMuohM+oBuMWon4z6IqMeMqMegFuM+smoLzLqITPqAbjFqJ+M+iKjHjKjHoBbjPrJqC8y6iEz6gG4xaifjPoiox4yox6AW4z6yagvMuohM+oBuMWon4z6IqMeMqMegFuM+smoLzLqITPqAbjFqJ+M+iKjHjKjHoBbjPrJqC8y6iEz6gG4xaifjPoiox4yox6AW4z6yagvMuohM+oBuMWon4z6IqMeMqMegFuM+smoLzLqITPqAbjFqJ+M+iKjHjKjHoBbjPrJqC8y6iEz6gG4xaifjPoiox4yox6AW4z6yagvMuohM+oBuMWon4z6IqMeMqMegFuM+smoLzLqITPqAbjFqJ+M+iKjHjKjHoBbjPrJqC8y6iEz6gG4xaifjPoiox4yox6AW4z6yagvMuohM+oBuMWon4z6IqMeMqMegFuM+smoLzLqITPqAbjFqJ+M+iKjHjKjHoBbjPrJqC8y6iEz6gG4xaifjPoiox4yox6AW4z6yagvMuohM+oBuMWon4z6IqMeMqMegFuM+smoLzLqITPqAbjFqJ+M+iKjHjKjHoBbjPrJqC8y6iEz6gG4xaifjPoiox4yox6AW4z6yagvMuohM+oBuMWon4z6IqMeMqMegFuM+smoLzLqITPqAbjFqJ+M+iKjHjKjHoBbjPrJqC8y6iEz6gG4xaifjPoiox4yox6AW4z6yagvMuohM+oBuMWon4z6IqMeMqMegFuM+smoLzLqITPqAbjFqJ+M+iKj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\",\r\n" +
            "		\"zongditu\": \"iVBORw0KGgoAAAANSUhEUgAAApgAAAGRCAYAAADB1sOcAAAAAXNSR0IArs4c6QAAAARnQU1BAACxjwv8YQUAAAAJcEhZcwAADsMAAA7DAcdvqGQAAAvfSURBVHhe7dbLSSgIFERB83Jh/rEIggsXgp9BeUwEl6HPmzpQEfSmH74lSZKkwxxMSZIkneZgSpIk6TQHU5IkSac5mJIkSTrNwZQkSdJpDqYkSZJOczAlSZJ0moMpSZKk0xxMSZIkneZgSpIk6TQHU5IkSac5mJIkSTrNwZQkSdJpDqYkSZJOczAlSZJ0moMpSZKk0xxMSZIkneZgSpIk6TQHU5IkSac5mJIkSTrNwZQkSdJpDqYkSZJOczAlSZJ0moMpSZKk0xxMSZIknfZXH8ynp6fvh4cHAICMn/9S768+mD8jvby8AABk/PyXeg4mAMAQB3M8BxMAqPn5L29vb78+Pj7+vJpWDiYAwJCf//L8/Pzr/f39z6tp5WACAAxxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzvKenp9+RAAAqHh8fHcxKr6+v/44FAFDgYI7nYAIANQ7meA4mAFDjYI739fX1/fn5CQCQ8fNfiv1vDqYkSZL+mxxMSZIkneZgSpIk6TQHU5IkSac5mJIkSTrNwZQkSdJpDqYkSZJOczAlSZJ0moMpSZKk0xxMSZIkneZgSpIk6TQHU5IkSac5mJIkSTrNwZQkSdJpDqYkSZJOczAlSZJ0moMpSZKk0xxMSZIkneZgSpIk6TQHU5IkSac5mJIkSTrNwZQkSdJpDqYkSZJOczAlSZJ0moMpSZKk0xxMSZIkneZgSpIk6TQHU5IkSac5mJIkSTrNwZQkSdJpDqYkSZJOczAlSZJ0moMpSZKk0xxMSZIkneZgSpIk6TQHU5IkSac5mJIkSTrs+/sfaSF82dkHu7gAAAAASUVORK5CYII=\",\r\n" +
            "		\"paymentStatus\": true,\r\n" +
            "		\"cost\": \"1000\"\r\n" +
            "	}, {\r\n" +
            "		\"bookTime\": \"2019-1-2\",\r\n" +
            "		\"certNumber\": \"鄂(2019)黄冈市不动产权第0003788号\",\r\n" +
            "		\"obligee\": \"陆军\",\r\n" +
            "		\"co_ownershipCircumstance\": \"单独所有\",\r\n" +
            "		\"located\": \"黄冈市黄州区路口大道王家湾小区2栋2单元101\",\r\n" +
            "		\"unitNumber\": \"0123456789876543211\",\r\n" +
            "		\"rightTypes\": \"住户\",\r\n" +
            "		\"rightNature\": \"出让\",\r\n" +
            "		\"application\": \"办公\",\r\n" +
            "		\"area\": \"250.5平米\",\r\n" +
            "		\"serviceLife\": \"2016年3月12号到2077年12月1号\",\r\n" +
            "		\"other\": \"无\",\r\n" +
            "		\"excursus\": \"无\",\r\n" +
            "		\"fenbutu\": \"iVBORw0KGgoAAAANSUhEUgAAAvUAAAGvCAYAAADbvyqBAAAAAXNSR0IArs4c6QAAAARnQU1BAACxjwv8YQUAAAAJcEhZcwAADsMAAA7DAcdvqGQAABcKSURBVHhe7dzBql5VFoXRvMEFu3kU8/7vEAQbQgJBsaHYTnldVAqr5gbLudxwyPhgdMJtFf/aZ9qpN58lSZIkPTqjXpIkSXp4Rr0kSZL08Ix6SZIk6eEZ9ZIkSdLDM+olSZKkh2fUS5IkSQ/PqJckSZIenlEvSZIkPTyjXpIkSXp4Rr0kSZL08Ix6SZIk6eEZ9ZIkSdLDM+olSZKkh2fUS5IkSQ/PqJckSZIenlEvSZIkPTyjXpIkSXp4Rr0kSZL08Ix6SZIk6eEZ9ZIkSdLDM+olSZKkh2fUS5IkSQ/PqJckSZIenlEvSZIkPTyjXpIkSXp4Rr0kSZL08Ix6SZIk6eEZ9UVv3779/ObNG+C/vLy8xH8H/j53BdnrHpNRX/X6Q/r555+B/+I2YJ+7guz1NmTUV3lgIXMbsM9dQWbUT/5XKPLAQvZ6G58+ffoi/Q3w//HNgez1Nn777bcvvtaM+iIPLGSvt/Hdd999kf4G+P/45kD2ehvv37//4mvNqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MM+3xzIjPrJqC/ywEJm1MO+d+/e/XFbwJ+9vLwY9b9n1Be9/pDSwwtfu9fbMOrhn/Phw4c/3Rh8zV6/OUa9UV9l1ENm1MM/y6iH/zDqJ6O+yKiHzKiHf5ZRD/9h1E9GfZFRD5lRD/+sH3/88fOnT5+A371+cz5+/PjF15pRX2TUQ2bUA3DL6zfn9T90/+1rzagvMuohM+oBuMWon4z6IqMeMqMegFuM+smoLzLqITPqAbjFqJ+M+iKjHjKjHoBbjPrJqC8y6iEz6gG4xaifjPoiox4yox6AW4z6yagvMuohM+oBuMWon4z6IqMeMqMegFuM+smoLzLqITPqAbjFqJ+M+iKjHjKjHoBbjPrJqC8y6iEz6gG4xaifjPoiox4yox6AW4z6yagvMuohM+oBuMWon4z6IqMeMqMegFuM+smoLzLqITPqAbjFqJ+M+iKjHjKjHoBbjPrJqC8y6iEz6gG4xaifjPoiox4yox6AW4z6yagvMuohM+oBuMWon4z6IqMeMqMegFuM+smoLzLqITPqAbjFqJ+M+iKjHjKjHoBbjPrJqC8y6iEz6gG4xaifjPoiox4yox6AW4z6yagvMuohM+oBuMWon4z6IqMeMqMegFuM+smoLzLqITPqAbjFqJ+M+iKjHjKjHoBbjPrJqC8y6iEz6gG4xaifjPoiox4yox6AW4z6yagvMuohM+oBuMWon4z6IqMeMqMegFuM+smoLzLqITPqAbjFqJ+M+iKjHjKjHoBbjPrJqC8y6iEz6gG4xaifjPoiox4yox6AW4z6yagvMuohM+oBuMWon4z6IqMeMqMegFuM+smoLzLqITPqAbjFqJ+M+iKjHjKjHoBbjPrJqC8y6iEz6gG4xaifjPoiox4yox6AW4z6yagvMuohM+oBuMWon4z6IqMeMqMegFuM+smoLzLqITPqAbjFqJ+M+iKjHjKjHoBbjPrJqC8y6iEz6gG4xaifjPoiox4yox6AW4z6yagvMuohM+oBuMWon4z6IqMeMqMegFuM+smoLzLqITPqAbjFqJ+M+iKjHjKjHoBbjPrJqC8y6iEz6gG4xaifjPoiox4yox6AW4z6yagvMuohM+oBuMWon4z6IqMeMqMegFuM+smoLzLqITPqAbjFqJ+M+iKjHjKjHoBbjPrJqC8y6iEz6gG4xaifjPoiox4yox6AW4z6yagvMuohM+oBuMWon4z6IqMeMqMegFuM+smoLzLqITPqAbjFqJ+M+iKjHjKjHoBbjPrJqC8y6iEz6gG4xaifjPoiox4yox6AW4z6yagvMuohM+oBuMWon4z6IqMeMqMegFuM+smoLzLqITPqAbjFqJ+M+iKjHjKjHoBbjPrJqC8y6iEz6gG4xaifjPoiox4yox6AW4z6yagvMuohM+oBuMWon4z6IqMeMqMegFuM+smoLzLqITPqAbjFqJ+M+iKjHjKjHoBbjPrJqC8y6iEz6gG4xaifjPoiox4yox6AW4z6yagvMuohM+oBuMWon4z6IqMeMqMegFuM+smoLzLqITPqAbjFqJ+M+iKjHjKjHoBbjPrJqC8y6iEz6gG4xaifjPoiox4yox6AW4z6yagvMuohM+oBuMWon4z6IqMeMqMegFuM+smoLzLqITPqAbjFqJ+M+iKjHjKjHoBbjPrJqC8y6iEz6gG4xaifjPoiox4yox6AW4z6yagvMuohM+oBuMWon4z6IqMeMqMegFuM+smoLzLqITPqAbjFqJ+M+iKjHjKjHoBbjPrJqC8y6iEz6gG4xaifjPoiox4yox6AW4z6yagvMuohM+oBuMWon4z6IqMeMqMegFuM+smoLzLqITPqAbjFqJ+M+iKj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\",\r\n" +
            "		\"zongditu\": \"iVBORw0KGgoAAAANSUhEUgAAApgAAAGRCAYAAADB1sOcAAAAAXNSR0IArs4c6QAAAARnQU1BAACxjwv8YQUAAAAJcEhZcwAADsMAAA7DAcdvqGQAAAvfSURBVHhe7dbLSSgIFERB83Jh/rEIggsXgp9BeUwEl6HPmzpQEfSmH74lSZKkwxxMSZIkneZgSpIk6TQHU5IkSac5mJIkSTrNwZQkSdJpDqYkSZJOczAlSZJ0moMpSZKk0xxMSZIkneZgSpIk6TQHU5IkSac5mJIkSTrNwZQkSdJpDqYkSZJOczAlSZJ0moMpSZKk0xxMSZIkneZgSpIk6TQHU5IkSac5mJIkSTrNwZQkSdJpDqYkSZJOczAlSZJ0moMpSZKk0xxMSZIknfZXH8ynp6fvh4cHAICMn/9S768+mD8jvby8AABk/PyXeg4mAMAQB3M8BxMAqPn5L29vb78+Pj7+vJpWDiYAwJCf//L8/Pzr/f39z6tp5WACAAxxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzPAcTAKhxMMdzMAGAGgdzvKenp9+RAAAqHh8fHcxKr6+v/44FAFDgYI7nYAIANQ7meA4mAFDjYI739fX1/fn5CQCQ8fNfiv1vDqYkSZL+mxxMSZIkneZgSpIk6TQHU5IkSac5mJIkSTrNwZQkSdJpDqYkSZJOczAlSZJ0moMpSZKk0xxMSZIkneZgSpIk6TQHU5IkSac5mJIkSTrNwZQkSdJpDqYkSZJOczAlSZJ0moMpSZKk0xxMSZIkneZgSpIk6TQHU5IkSac5mJIkSTrNwZQkSdJpDqYkSZJOczAlSZJ0moMpSZKk0xxMSZIkneZgSpIk6TQHU5IkSac5mJIkSTrNwZQkSdJpDqYkSZJOczAlSZJ0moMpSZKk0xxMSZIkneZgSpIk6TQHU5IkSac5mJIkSTrs+/sfaSF82dkHu7gAAAAASUVORK5CYII=\",\r\n" +
            "		\"paymentStatus\": true,\r\n" +
            "		\"cost\": \"1000\"\r\n" +
            "	}]";
}