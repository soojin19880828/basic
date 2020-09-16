package com.whminwei.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Arrays;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.AuthSchemes;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.whminwei.dto.rspn.HttpResult;


/**
 * 默认配置HTTP链接工程类
 * 
 * @author 10118 2020年8月28日
 */
public class HttpClientFactory {

	private static final Logger logger = LoggerFactory.getLogger(HttpClientFactory.class);

	private static int CONNECT_TIMEOUT = 10000;

	private static int CONNECTION_REQUEST_TIMEOUT = 60000;

	private static int SOCKET_TIMEOUT = 600000;

	private static int MAX_TOTAL = 5;

	private static int DEFAULT_MAX_PER_ROUTE = 1;

	/**
	 * 公用客户端
	 */
	public static CloseableHttpClient client;

	/**
	 * 重写验证方法
	 */
	private static TrustManager manager = new X509TrustManager() {
		public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
		}

		public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
		}

		public X509Certificate[] getAcceptedIssuers() {
			return null;
		}
	};

	/**
	 * SSL连接工厂
	 */
	private static SSLConnectionSocketFactory socketFactory;

	/**
	 * 设置报文头属性
	 * 
	 * @param request
	 * @return
	 */
	private HttpRequestBase setHeader(HttpRequestBase request) {
		request.setHeader(HTTP.CONTENT_TYPE, "application/json");
		return request;
	}

	/**
	 * 调用SSL
	 * 
	 * @throws NoSuchAlgorithmException
	 * @throws KeyManagementException
	 */
	private void enableSSL() {
		try {
			SSLContext context = SSLContext.getInstance("TLS");
			context.init(null, new TrustManager[] { manager }, null);
			socketFactory = new SSLConnectionSocketFactory(context, NoopHostnameVerifier.INSTANCE);
		} catch (KeyManagementException | NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 设置Https Client
	 * 
	 * @throws KeyManagementException
	 * @throws NoSuchAlgorithmException
	 */
	private void setClient() {
		/** 调用SSL */
		enableSSL();
		/** 设置全局的标准Cookie策略 */
		RequestConfig config = RequestConfig.custom().setCookieSpec(CookieSpecs.IGNORE_COOKIES)
				.setConnectTimeout(CONNECT_TIMEOUT).setConnectionRequestTimeout(CONNECTION_REQUEST_TIMEOUT)
				.setSocketTimeout(SOCKET_TIMEOUT).setExpectContinueEnabled(true)
				.setTargetPreferredAuthSchemes(Arrays.asList(AuthSchemes.NTLM, AuthSchemes.DIGEST))
				.setProxyPreferredAuthSchemes(Arrays.asList(AuthSchemes.BASIC)).build();
		/** 设置连接工厂类 */
		Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
				.register("http", PlainConnectionSocketFactory.INSTANCE).register("https", socketFactory).build();
		/** 设置连接管理 */
		PoolingHttpClientConnectionManager manager = new PoolingHttpClientConnectionManager(registry);
		manager.setMaxTotal(MAX_TOTAL);
		manager.setDefaultMaxPerRoute(DEFAULT_MAX_PER_ROUTE);
		/** 设置可关闭的HttpClient */
		client = HttpClients.custom().setConnectionManager(manager).setDefaultRequestConfig(config).build();
	}

	/**
	 * 关闭HttpClient链接<br>
	 * 将变量client设为空
	 */
	public static void closeClient() {
		try {
			client.close();
			client = null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @description: get请求
	 * @param url
	 * @param sendTimes
	 *            发送次数 建议不超过三次
	 * @return
	 * @author: 10118
	 * @date: 2020年8月28日上午10:32:56
	 */
	public HttpResult doGetThrowException(String url, int sendTimes) {
		HttpResult result = new HttpResult(true);
		/** 设置HttpClient */
		if (client == null) {
			setClient();
		}
		/** 发送Get请求 */
		HttpGet get = new HttpGet(url);
		setHeader(get);
		CloseableHttpResponse response = null;
		// 第一次请求若抛出异常，会再发sendTimes次
		int count = 0;
		boolean sendFlag = true;
		while (true) {
			try {
				response = client.execute(get);
			} catch (ConnectException | ConnectTimeoutException e) {
				// 连接异常
				count++;
				sendFlag = false;
				logger.error("网络连接异常,请联系现场工作人员...", e);
				result.setRespMsg("网络连接异常,请联系现场工作人员！！！");
				result.setErrCode("E001");
				result.setResult(false);
			} catch (SocketTimeoutException e) {
				// 请求相应超时异常,默认时长90 * 1000s ,若此处时间延长,客户端超时时间也对应修改
				count++;
				sendFlag = false;
				logger.error("请求响应超时...", e);
				result.setErrCode("E002");
				result.setRespMsg("请求响应超时,请联系现场工作人员！！！");
				result.setResult(false);
			} catch (Exception e) {
				// 其他异常信息 
				count++;
				sendFlag = false;
				logger.error("查询其他异常,请联系现场工作人员...", e);
				result.setErrCode("E099");
				result.setRespMsg("查询其他异常,请联系现场工作人员！！！");
				result.setResult(false);
			} finally {
				if (sendFlag || count >= sendTimes) {
					break;
				}
			}
		}
		/** 解析并关闭响应 */
		try {
			result.setData(EntityUtils.toString(response.getEntity(), "UTF-8"));
			response.close();
			get.releaseConnection();
		} catch (ParseException | IOException e) {
			logger.error("关闭请求响应异常...");
			result.setRespMsg("关闭请求响应异常,请联系现场工作人员！！！");
			result.setResult(false);
		}
		return result;
	}

	/**
	 * Https Get
	 * 
	 * @return
	 * @throws KeyManagementException
	 * @throws NoSuchAlgorithmException
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public String doGet(String url) {
		/** 设置HttpClient */
		if (client == null) {

			setClient();
		}
		/** 发送Get请求 */
		HttpGet get = new HttpGet(url);
		setHeader(get);
		CloseableHttpResponse response = null;
		try {
			response = client.execute(get);
		} catch (IOException e) {
			e.printStackTrace();
		}
		/** 解析并关闭响应 */
		String respStr = null;
		try {
			respStr = EntityUtils.toString(response.getEntity(), "UTF-8");
			response.close();
			get.releaseConnection();
		} catch (ParseException | IOException e) {
			e.printStackTrace();
		}
		return respStr;
	}

	/**
	 * 通过Https Get获取数据流实现下载
	 * 
	 * @param url
	 * @param fileName
	 * @return
	 */
	public boolean doGetForDownload(String url, String fileName) {
		/** 设置HttpClient */
		if (client == null) {

			setClient();

		}
		/** 发送Get请求 */
		HttpGet get = new HttpGet(url);
		setHeader(get);
		CloseableHttpResponse response = null;
		try {
			response = client.execute(get);
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		/** 解析并关闭响应 */
		InputStream fis = null;
		try {
			fis = response.getEntity().getContent();
			response.close();
			get.releaseConnection();
		} catch (UnsupportedOperationException | IOException e) {
			e.printStackTrace();
			return false;
		}
		/** 创建输出文件并将数据流写入文件中 */
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(new File(fileName));
			byte[] temp = new byte[1024];
			int b;
			while ((b = fis.read(temp)) != -1) {
				fos.write(temp, 0, b);
				fos.flush();
			}
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		/** 关闭文件链接 */
		try {
			if (fis != null) {
				fis.close();
			}
			if (fos != null) {
				fos.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public HttpResult doPostThrowException(String data, String url, int sendTimes) {
		HttpResult result = new HttpResult(true);
		/** 设置HttpClient */
		if (client == null) {

			setClient();

		}
		/** 发送Post请求 */
		HttpPost post = new HttpPost(url);
		this.setHeader(post);
		StringEntity body = new StringEntity(data, "UTF-8");
		post.setEntity(body);
		CloseableHttpResponse response = null;
		// 第一次请求若抛出异常，会再发sendTimes次
		int count = 0;
		boolean sendFlag = true;
		while (true) {
			try {
				response = client.execute(post);
			} catch (ConnectException | ConnectTimeoutException e) {
				// 连接异常
				count++;
				sendFlag = false;
				logger.error("网络连接异常,请联系现场工作人员...", e);
				result.setRespMsg("网络连接异常,请联系现场工作人员！！！");
				result.setResult(false);
			} catch (SocketTimeoutException e) {
				// 请求相应超时异常,默认时长90 * 1000s ,若此处时间延长,客户端超时时间也对应修改
				count++;
				sendFlag = false;
				logger.error("请求响应超时...", e);
				result.setRespMsg("请求响应超时,请联系现场工作人员！！！");
				result.setResult(false);
			} catch (Exception e) {
				/** 其他异常信息 **/
				count++;
				sendFlag = false;
				logger.error("查询其他异常,请联系现场工作人员...", e);
				result.setRespMsg("查询其他异常,请联系现场工作人员！！！");
				result.setResult(false);
			} finally {
				if (sendFlag || count >= sendTimes) {
					break;
				}
			}
		}
		/** 解析并关闭响应 */
		try {
			result.setData(EntityUtils.toString(response.getEntity(), "UTF-8"));
			response.close();
			post.releaseConnection();
		} catch (ParseException | IOException e) {
			logger.error("关闭请求响应异常...");
			result.setRespMsg("关闭请求响应异常,请联系现场工作人员！！！");
			result.setResult(false);
		}
		return result;
	}

	/**
	 * Https Post
	 * 
	 * @param data
	 * @return
	 * @throws KeyManagementException
	 * @throws NoSuchAlgorithmException
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public String doPost(String data, String url) {
		/** 设置HttpClient */
		if (client == null) {
			setClient();

		}
		/** 发送Post请求 */
		HttpPost post = new HttpPost(url);
		this.setHeader(post);
		StringEntity body = new StringEntity(data, "UTF-8");
		post.setEntity(body);
		CloseableHttpResponse response = null;
		try {
			response = client.execute(post);
		} catch (IOException e) {
			e.printStackTrace();
		}
		/** 解析并关闭响应 */
		String respStr = null;
		try {
			respStr = EntityUtils.toString(response.getEntity(), "UTF-8");
			response.close();
			post.releaseConnection();
		} catch (ParseException | IOException e) {
			e.printStackTrace();
		}
		return respStr;
	}

	/**
	 * 通过Https Post获取数据流实现下载
	 * 
	 * @param data
	 * @param url
	 * @param fileName
	 * @return
	 */
	public boolean doPostForDownload(String data, String url, String fileName) {
		/** 设置HttpClient */
		if (client == null) {

			setClient();

		}
		/** 发送Post请求 */
		HttpPost post = new HttpPost(url);
		this.setHeader(post);
		StringEntity body = new StringEntity(data, "UTF-8");
		post.setEntity(body);
		CloseableHttpResponse response = null;
		try {
			response = client.execute(post);
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		/** 解析并关闭响应 */
		InputStream fis = null;
		try {
			fis = response.getEntity().getContent();
			response.close();
			post.releaseConnection();
		} catch (UnsupportedOperationException | IOException e) {
			e.printStackTrace();
			return false;
		}
		/** 创建输出文件并将数据流写入文件中 */
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(new File(fileName));
			byte[] temp = new byte[1024];
			int b;
			while ((b = fis.read(temp)) != -1) {
				fos.write(temp, 0, b);
				fos.flush();
			}
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		/** 关闭文件链接 */
		try {
			if (fis != null) {
				fis.close();
			}
			if (fos != null) {
				fos.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	private HttpClientFactory() {
		setClient();
	}

	private static HttpClientFactory instance;

	public static HttpClientFactory getInstance() {
		if (instance == null) {
			instance = new HttpClientFactory();
		}
		return instance;
	}
}
