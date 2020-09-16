package com.whminwei.util.http;

public interface IHttpClientConfig {

	// 链接超时
	public int getConnectTimeout();

	// 链接请求超时
	public int getConnectionRequestTimeout();

	// 信道超时
	public int getSocketTimeout();

	// 最大链接
	public int getMaxTotal();
	
	// 默认单路由最大链接
	public int getDefaultMaxPerRoute();
}
