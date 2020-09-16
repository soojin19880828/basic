package com.whminwei.util.http;

/**
 * <h3>THZB01</h3>
 * <p></p>
 *
 * @author : 汪颖
 * @date : 2020/7/3 0003上午 9:47
 **/
public interface BaseService {

    /**
     * @param : [req, reqUrl]
     * @desciption : 请求转发Post
     * @date : 2020/7/3 0003 上午 9:48
     * @author : 汪颖
     */
    String httpTrans(String req, String reqUrl);

    /**
     * @param : [reqUrl]
     * @desciption : 请求转发Get
     * @date : 2020/7/3 0003 上午 9:48
     * @author : 汪颖
     */
    String httpTrans(String reqUrl);
}
