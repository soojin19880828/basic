package com.whminwei.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.whminwei.controller.param.LEDMDeviceInfo;
import com.whminwei.controller.param.LEDMDeviceResp;
import com.whminwei.util.JacksonFactory;
import com.whminwei.util.http.BaseService;
import com.whminwei.util.http.impl.BaseServiceImpl;
import com.whminwei.view.MainView;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.whminwei.view.MainView.topController;

/**
 * 打印机状态管理
 */
public class PrinterManageController implements Initializable {
	private static final Logger logger = LoggerFactory.getLogger(InPutPassWordController.class);

    /**打印机服务地址*/
    public static final String URL="https://192.168.3.59:8081/";

    private BaseService baseService = new BaseServiceImpl();

    /**打印机状态*/
    public Label status;
    /**剩余墨百分比*/
    public Label remaining;
    /**打印总数*/
    public Label letter;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        List<LEDMDeviceInfo> ledmDeviceInfos = listDevice();
        if (ledmDeviceInfos.size()>0){
            LEDMDeviceInfo ledmDeviceInfo = ledmDeviceInfos.get(0);
            if (ledmDeviceInfo.isOnline()){
                status.setText("就绪");
                String port = ledmDeviceInfo.getPortName();
                //获取已打印总数
                String usageStatistics = getUsageStatistics(port);
                letter.setText(usageStatistics);
                String consumableStatus = getConsumableStatus(port);
                remaining.setText(consumableStatus+"%");
            }else{
                status.setText("打印机未开机");
            }
        }
    }


    /*
     * @Author 罗子敬
     * @Description 获取所有设备信息
     * @Date 2020/8/17 14:33
     * @Param []
     * @return java.util.List<DeviceInfo>
     **/
    public List<LEDMDeviceInfo>  listDevice() {
    	logger.info("获取所有打印机信息");
        StringBuilder url = new StringBuilder(URL);
        url.append("/devices");
        String rspnStr = baseService.httpTrans(url.toString());
        logger.info("获取结果"+rspnStr);
        /**
         * {"printers":[{"printerName":"HP LaserJet Pro M402-M403 PCL 6","driverName":"HP LaserJet Pro M402-M403 PCL 6","portName":"USB001","online":false}]}
         */
        LEDMDeviceResp deviceResp = JacksonFactory.readJson(rspnStr, LEDMDeviceResp.class);
        List<LEDMDeviceInfo> printers = deviceResp.getPrinters();
        return printers;
    }

    private static final String RESULT_KEY = "Status";

    /*
     * @Author 罗子敬
     * @Description 获取打印机状态
     * @Date 2020/8/17 14:38
     * @Param [port] Printer's port
     * @return status
     **/
    public String getDeviceStatus(String port){
        StringBuilder url = new StringBuilder(URL);
        url.append("status/device/"+port);
        String rspnStr = baseService.httpTrans(url.toString());
        logger.info("获取结果"+rspnStr);
        if (rspnStr.equals("Printer Offline")){
            return null;
        }
        JSONArray jsonArray = JSONObject.parseArray(rspnStr);
        for (int i = 0; i < jsonArray.size(); i++) {
            Object obj = jsonArray.get(i);
            Map map = JSONObject.parseObject(JSON.toJSONString(obj), Map.class);
            if (map.containsKey(RESULT_KEY)) {
                Object result = map.get(RESULT_KEY);
                return result.toString();
            }
        }
        return null;
    }

    private static final String STATIS_KEY = "TotalImpressions";

    /*
     * @Author 罗子敬
     * @Description 获取打印总数
     * @Date 2020/8/17 17:28
     * @Param [port]
     * @return java.lang.String
     **/
    public String getUsageStatistics(String port){
        StringBuilder url = new StringBuilder(URL);
        url.append("status/usage/"+port);
        String rspnStr = baseService.httpTrans(url.toString());
        logger.info("获取结果"+rspnStr);

        Map map = JSONObject.parseObject(rspnStr, Map.class);
        if (map.containsKey(STATIS_KEY)) {
            Object result = map.get(STATIS_KEY);
            return result.toString();
        }
        return null;
    }

    private static final String REMAINING_KEY = "ConsumablePercentageLevelRemaining";
    /*
     * @Author 罗子敬
     * @Description 获取墨粉百分比
     * @Date 2020/8/17 17:28
     * @Param [port]
     * @return java.lang.String
     **/
    public String getConsumableStatus (String port){
        StringBuilder url = new StringBuilder(URL);
        url.append("status/consumable/"+port);
        String rspnStr = baseService.httpTrans(url.toString());
        logger.info("获取结果"+rspnStr);
        JSONArray jsonArray = JSONObject.parseArray(rspnStr);
        for (int i = 0; i < jsonArray.size(); i++) {
            Object obj = jsonArray.get(i);
            Map map = JSONObject.parseObject(JSON.toJSONString(obj), Map.class);
            if (map.containsKey(REMAINING_KEY)) {
                Object result = map.get(REMAINING_KEY);
                return result.toString();
            }
        }
        return null;
    }


    public static void main(String[] args) {
       /* StringBuilder url = new StringBuilder(URL);
        url.append("status/usage/"+"USB001");
        BaseServiceImpl baseService = new BaseServiceImpl();
        String rspnStr = baseService.httpTrans(url.toString());
        log.info("获取结果"+rspnStr);
        Map map = JSONObject.parseObject(rspnStr, Map.class);
        if (map.containsKey("TotalImpressions")) {
            Object result = map.get("TotalImpressions");
            System.out.println(result.toString());
        }*/
        StringBuilder url = new StringBuilder(URL);
        url.append("status/device/"+"USB001");
        BaseServiceImpl baseService = new BaseServiceImpl();
        String rspnStr = baseService.httpTrans(url.toString());
        logger.info("获取结果"+rspnStr);
        System.out.println(rspnStr.equalsIgnoreCase("Printer Offline"));
        if (rspnStr.equalsIgnoreCase("Printer Offline")){
            return;
        }
        JSONArray jsonArray = JSONObject.parseArray(rspnStr);
        for (int i = 0; i < jsonArray.size(); i++) {
            Object obj = jsonArray.get(i);
            Map map = JSONObject.parseObject(JSON.toJSONString(obj), Map.class);
            if (map.containsKey(RESULT_KEY)) {
                Object result = map.get(RESULT_KEY);
                System.out.println(result);
            }
        }
        /*StringBuilder url = new StringBuilder(URL);
        url.append("status/consumable/"+"USB001");
        BaseServiceImpl baseService = new BaseServiceImpl();
        String rspnStr = baseService.httpTrans(url.toString());
        log.info("获取结果"+rspnStr);
        JSONArray jsonArray = JSONObject.parseArray(rspnStr);
        for (int i = 0; i < jsonArray.size(); i++) {
            Object obj = jsonArray.get(i);
            Map map = JSONObject.parseObject(JSON.toJSONString(obj), Map.class);
            if (map.containsKey(REMAINING_KEY)) {
                Object result = map.get(REMAINING_KEY);
                System.out.println(result.toString());
            }
        }*/
    }


   /* public static String replaceBlank(String str) {
        String dest = "";
        if (str!=null) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        return dest;
    }*/
}
