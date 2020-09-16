package com.thfdcsoft.framework.business.util;


import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * @Author: Tyrell
 * @Date: 2019-08-02 下午 17:57
 */
public class HttpUtil {
    private static final Logger log = LoggerFactory.getLogger(HttpUtil.class);
    private static final CloseableHttpClient httpclient = HttpClients.createDefault();
    private static final String userAgent = "Mozilla/5.0 (Windows NT 6.2; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.87 Safari/537.36";

    /** * 发送HttpGet请求 * * @param url * 请求地址 * @return 返回字符串 */
    public static String sendGet(String url) {
        String result = null;
        CloseableHttpResponse response = null;
        try {
            HttpGet httpGet = new HttpGet(url);
            httpGet.setHeader("User-Agent", userAgent);
            response = httpclient.execute(httpGet);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                result = EntityUtils.toString(entity);
            }
        } catch (Exception e) {
            log.error("get请求处理失败 {}" + e);
            e.printStackTrace();
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    log.error(e.getMessage());
                }
            }
        }
        return result;
    }

    /** * 发送HttpPost请求，参数为map * * @param url * 请求地址 * @param map * 请求参数 * @return 返回字符串 */
    public static String sendPost(String url, Map<String, String> map) {
        // 设置参数
        List<NameValuePair> formparams = new ArrayList<NameValuePair>();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            formparams.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
        }
        // 编码
        UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(formparams, Consts.UTF_8);
        // 取得HttpPost对象
        HttpPost httpPost = new HttpPost(url);
        // 防止被当成攻击添加的
        httpPost.setHeader("User-Agent", userAgent);
        // 参数放入Entity
        httpPost.setEntity(formEntity);
        CloseableHttpResponse response = null;
        String result = null;
        try {
            // 执行post请求
            response = httpclient.execute(httpPost);
            // 得到entity
            HttpEntity entity = response.getEntity();
            // 得到字符串
            result = EntityUtils.toString(entity);
        } catch (IOException e) {
            log.error(e.getMessage());
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    log.error(e.getMessage());
                }
            }
        }
        return result;
    }

    /** * 发送HttpPost请求，参数为文件，适用于微信上传素材 * * @param url * 请求地址 * @param file * 上传的文件 * @return 返回字符串 * @throws IOException * @throws ClientProtocolException */
//    public static String sendPost(String url, File file) {
//        String result = null;
//        HttpPost httpPost = new HttpPost(url);
//        // 防止被当成攻击添加的
//        httpPost.setHeader("User-Agent", userAgent);
//        MultipartEntityBuilder multipartEntity = MultipartEntityBuilder.create();
//        multipartEntity.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
//        multipartEntity.addPart("media", new FileBody(file));
//        httpPost.setEntity(multipartEntity.build());
//        CloseableHttpResponse response = null;
//        try {
//            response = httpclient.execute(httpPost);
//            HttpEntity entity = response.getEntity();
//            result = EntityUtils.toString(entity);
//        } catch (IOException e) {
//            log.error(e.getMessage());
//        } finally {
//            // 关闭CloseableHttpResponse
//            if (response != null) {
//                try {
//                    response.close();
//                } catch (IOException e) {
//                    log.error(e.getMessage());
//                }
//            }
//        }
//        return result;
//
//    }

    /** * 发送HttpPost请求，参数为json字符串 * * @param url * @param jsonStr * @return */
    public static String sendPost(String url, String jsonStr) {
        String result = null;
        // 字符串编码
        StringEntity entity = new StringEntity(jsonStr, Consts.UTF_8);
        // 设置content-type
        entity.setContentType("application/json");
        HttpPost httpPost = new HttpPost(url);
        // 防止被当成攻击添加的
        httpPost.setHeader("User-Agent", userAgent);
        // 接收参数设置
        httpPost.setHeader("Accept", "application/json");
        httpPost.setEntity(entity);
        CloseableHttpResponse response = null;
        try {
            response = httpclient.execute(httpPost);
            HttpEntity httpEntity = response.getEntity();
            result = EntityUtils.toString(httpEntity);
        } catch (IOException e) {
            log.error(e.getMessage());
            return "connection time out";
        } finally {
            // 关闭CloseableHttpResponse
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    log.error(e.getMessage());
                }
            }
        }
        return result;
    }

    /** * 发送不带参数的HttpPost请求 * * @param url * @return */
    public static String sendPost(String url) {
        String result = null;
        // 得到一个HttpPost对象
        HttpPost httpPost = new HttpPost(url);
        // 防止被当成攻击添加的
        httpPost.setHeader("User-Agent", userAgent);
        CloseableHttpResponse response = null;
        try {
            // 执行HttpPost请求，并得到一个CloseableHttpResponse
            response = httpclient.execute(httpPost);
            // 从CloseableHttpResponse中拿到HttpEntity
            HttpEntity entity = response.getEntity();
            // 将HttpEntity转换为字符串
            result = EntityUtils.toString(entity);
        } catch (IOException e) {
            log.error(e.getMessage());
        } finally {
            // 关闭CloseableHttpResponse
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    log.error(e.getMessage());
                }
            }
        }
        return result;
    }


    /*public static void main(String[] args) throws UnsupportedEncodingException {
        //demo:代理访问
//        String url = "http://api.adf.ly/api.php";
//        String url = "http://www.baidu.com";
//        String para = "";
////        String para = "key=youkeyid&youuid=uid&advert_type=int&domain=adf.ly&url=http://somewebsite.com";
//
//        String sr = HttpRequestUtil.sendPost(url, para, true);
//        System.out.println(sr);

        PYWriteBackReq pyWriteBackReq = new PYWriteBackReq();
        pyWriteBackReq.setYWBH("201707190043");
        pyWriteBackReq.setBDCDJZMH("赣(2017)鄱阳县不动产证明第0004416号");
        pyWriteBackReq.setZZJID("140600-01");
        pyWriteBackReq.setPRINTDATE("2019-08-05 17:41:56");
        pyWriteBackReq.setQZYSXLH("36002360504");

        StringBuilder sb =  new StringBuilder();
        sb.append("YWBH").append("=").append(pyWriteBackReq.getYWBH()).append("&");
        sb.append("BDCDJZMH").append("=").append(pyWriteBackReq.getBDCDJZMH()).append("&");
        sb.append("BDCDYH").append("=").append(pyWriteBackReq.getBDCDYH()).append("&");
        sb.append("QLR").append("=").append(pyWriteBackReq.getQLR()).append("&");
//        sb.append("YWR").append("=").append(pyWriteBackReq.getYWR()).append("&");
        sb.append("ZZJID").append("=").append(pyWriteBackReq.getZZJID()).append("&");
        sb.append("PRINTDATE").append("=").append(pyWriteBackReq.getPRINTDATE()).append("&");
        sb.append("QZYSXLH").append("=").append(pyWriteBackReq.getQZYSXLH());
//        String encode = URLEncoder.encode(sb.toString(), "GBK");
//        String pyHCUrl = "http://17.188.24.246:8080/PYZMCXJ/GetHC.ashx"+"?"+encode;
        String pyHCUrl = "http://17.188.24.246:8080/PYZMCXJ/GetHC.ashx"+"?"+sb.toString();
//        String pyHCUrl = "http://17.188.24.246:8080/PYZMCXJ/GetHC.ashx"+"?YWBH="+pyWriteBackReq.getYWBH()+"&BDCDJZMH="+pyWriteBackReq.getBDCDJZMH()+"&BDCDYH="+pyWriteBackReq.getBDCDYH()+"&QLR="+pyWriteBackReq.getQLR()+"&YWR="+pyWriteBackReq.getYWR()+"&ZZJID="+pyWriteBackReq.getZZJID()+"&PRINTDATE="+pyWriteBackReq.getPRINTDATE()+
//					"&QZYSXLH="+pyWriteBackReq.getQZYSXLH();

        pyHCUrl= pyHCUrl.replaceAll(" ", "%20");
        // 鄱阳回传发送请求
        System.out.println("鄱阳回传接口请求地址========"+pyHCUrl);
        String rspnStr = sendGet(pyHCUrl);
        System.out.println(rspnStr);
    }*/

}
