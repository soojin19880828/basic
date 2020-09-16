package com.whminwei.util.http.def;


import com.whminwei.util.http.IHttpClientConfig;

public class DefHttpClientConfig implements IHttpClientConfig {

	private static int CONNECT_TIMEOUT = 10000;

	private static int CONNECTION_REQUEST_TIMEOUT = 300000;

	private static int SOCKET_TIMEOUT = 300000;

	private static int MAX_TOTAL = 5;

	private static int DEFAULT_MAX_PER_ROUTE = 1;

	@Override
	public int getConnectTimeout() {
		return CONNECT_TIMEOUT;
	}

	@Override
	public int getConnectionRequestTimeout() {
		return CONNECTION_REQUEST_TIMEOUT;
	}

	@Override
	public int getSocketTimeout() {
		return SOCKET_TIMEOUT;
	}

	@Override
	public int getMaxTotal() {
		return MAX_TOTAL;
	}

	@Override
	public int getDefaultMaxPerRoute() {
		return DEFAULT_MAX_PER_ROUTE;
	}

}
