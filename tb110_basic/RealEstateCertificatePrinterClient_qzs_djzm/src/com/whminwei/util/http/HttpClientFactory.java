package com.whminwei.util.http;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
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

/**
 * 默认配置HTTP链接工程类
 *
 * @author 张嘉琪
 * @date 2018年1月8日下午2:48:35
 */
public class HttpClientFactory {

	private static int CONNECT_TIMEOUT = 10000;

	private static int CONNECTION_REQUEST_TIMEOUT = 60000;

	private static int SOCKET_TIMEOUT = 60000;

	private static int MAX_TOTAL = 5;

	private static int DEFAULT_MAX_PER_ROUTE = 1;

	/**
	 * 公用客户端
	 */
	private static CloseableHttpClient client;

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
	private void enableSSL() throws NoSuchAlgorithmException, KeyManagementException {
		SSLContext context = SSLContext.getInstance("TLS");
		context.init(null, new TrustManager[] { manager }, null);
		socketFactory = new SSLConnectionSocketFactory(context, NoopHostnameVerifier.INSTANCE);
	}

	/**
	 * 设置Https Client
	 *
	 * @throws KeyManagementException
	 * @throws NoSuchAlgorithmException
	 */
	private void setClient() throws KeyManagementException, NoSuchAlgorithmException {
		/** 调用SSL */
		enableSSL();
		/** 设置全局的标准Cookie策略 */
		RequestConfig config = RequestConfig.custom().setCookieSpec(CookieSpecs.IGNORE_COOKIES)
				.setConnectTimeout(CONNECT_TIMEOUT)
				.setConnectionRequestTimeout(CONNECTION_REQUEST_TIMEOUT)
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
	public void closeClient() {
		try {
			client.close();
			client = null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
			try {
				setClient();
			} catch (KeyManagementException | NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
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
			try {
				setClient();
			} catch (KeyManagementException | NoSuchAlgorithmException e) {
				e.printStackTrace();
				return false;
			}
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
			try {
				setClient();
			} catch (KeyManagementException | NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
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
			try {
				setClient();
			} catch (KeyManagementException | NoSuchAlgorithmException e) {
				e.printStackTrace();
				return false;
			}
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

	private HttpClientFactory() throws KeyManagementException, NoSuchAlgorithmException {
		setClient();
	}

	private static HttpClientFactory instance;

	public static HttpClientFactory getInstance() throws KeyManagementException, NoSuchAlgorithmException {
		if (instance == null) {
			instance = new HttpClientFactory();
		}
		return instance;
	}
}
