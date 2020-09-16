package com.whminwei.util.http.impl;



import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import com.whminwei.util.http.BaseService;
import com.whminwei.util.http.def.DefHttpClientFactory;

/**
 * <h3>THZB01</h3>
 * <p></p>
 *
 * @author : 汪颖
 * @date : 2020/7/3 0003上午 9:47
 **/
public class BaseServiceImpl implements BaseService {
    /**
     * @param : [req, reqUrl]
     * @desciption : 客户端 发送Post 请求
     * @date : 2020/7/3 0003 上午 9:51
     * @author : 汪颖
     */
    @Override
    public String httpTrans(String req, String reqUrl) {
        String rspnJson = "";
        try {
            rspnJson = DefHttpClientFactory.getInstance().doPost(req, reqUrl);
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return rspnJson;
    }

    /**
     * @param : [reqUrl]
     * @desciption : 客户端 发送Get 请求
     * @date : 2020/7/3 0003 上午 9:51
     * @author : 汪颖
     */
    @Override
    public String httpTrans(String reqUrl) {
        String rspnJson = "";
        try {
            rspnJson = DefHttpClientFactory.getInstance().doGet(reqUrl);
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return rspnJson;
    }
}
