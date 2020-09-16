package com.whminwei.util.http.def;

import com.whminwei.util.http.HttpClientFactory;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

public class DefHttpClientFactory {

	public static HttpClientFactory getInstance() throws KeyManagementException, NoSuchAlgorithmException {
		return HttpClientFactory.getInstance();
	}

}
