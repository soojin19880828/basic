package com.thfdcsoft.framework.manage.util;

import java.util.Random;

public class GetRandomString {
	/*
	 * 获取随机字符串 
	 * str 源字符串 
	 * length 生成字符串随机位数
	 */
	public String GetString(String str, int length) {
		Random random = new Random();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < length; i++) {
			int number = random.nextInt(str.length());
			sb.append(str.charAt(number));
		}
		return sb.toString();
	}

	public static void main(String[] args) throws Exception {
		/*GetRandomString getString = new GetRandomString();
		String key = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC14GfgsTkNHW3BrdMg3JDFzXDZ3qp5oeLDI7nKou1IuZN7Bu3higboeRxxNVqW+Dluo9hE6fT4U2Z4is5svpzBdKbIrdTBMbhOcq9RvseOz+zbZKMQLCf0ziLrm+4VcMzWiCxVajPWi1Atn/Xf8aWjqh3JQp4EkDUCnB7fOcQ9IQIDAQAB";
//		String key = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCIIQXhaM3xb/Xy0lL9DYbwKnxnF31nedrvCsu6RlhcuTFpXbm/mk+/GXQ+eiJDworCrx+SyHPmBGm7T2UdgETniIutegAK5tuJFGAum1Dt3V947i34VvCacLjliQIaL+YFyQfPuOG3ob3rDEclkEtg5RdIsv9yJYNnejjilquDhQIDAQAB";
		String str = getString.GetString(key, 8);
		StringBuilder builder = new StringBuilder();
		//测试账号+密码
		String yzm = builder.append("000348")
				.append("556b147394488fd7892afb7a06fab9fa").append(str).toString();
//		String yzm = builder.append("000059")
//				.append("740053b797eadcf564d457e234f20836").append(str).toString();
		System.out.println(yzm);
		GetRsaString getRsa = new GetRsaString();
		//验证码加密
		String keyWord = getRsa.GetRsaStr(yzm);
		System.out.println(keyWord);*/

		// 范县
		/*GetRandomString getString = new GetRandomString();
		String key = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC14GfgsTkNHW3BrdMg3JDFzXDZ3qp5oeLDI7nKou1IuZN7Bu3higboeRxxNVqW+Dluo9hE6fT4U2Z4is5svpzBdKbIrdTBMbhOcq9RvseOz+zbZKMQLCf0ziLrm+4VcMzWiCxVajPWi1Atn/Xf8aWjqh3JQp4EkDUCnB7fOcQ9IQIDAQAB";
		String str = getString.GetString(key, 8);
		StringBuilder builder = new StringBuilder();
		String yzm = builder.append("000050")
				.append("e207faedc98ec166e5ea51d1999907e9").append(str).toString();
		System.out.println(yzm);
		GetRsaString getRsa = new GetRsaString();
		//验证码加密
		String keyWord = getRsa.GetRsaStr(yzm);
		System.out.println("yzm===="+keyWord);*/
		/*EstateQueryReq req = new EstateQueryReq();
		req.setYzm(keyWord);
		req.setYjm("140600-01");
		req.setSfzh("410926199509220023");
		String reqJson = JacksonFactory.writeJson(req);
		// 范县  账号/密码 000025 /9c3b6825e418b1c8caef90462daae15b    查询接口地址 http://172.16.200.5/gdbdc/THXX/THXXDYZM/GetCertInfo
		String URL1 = "http://172.16.200.5/gdbdc/THXX/THXXDYZM/GetCertInfo";
		String rspnStr = HttpClientFactory.getInstance().doPost(reqJson, URL1);
		PYQueryRsp rsp = JacksonFactory.readJson(rspnStr, PYQueryRsp.class);
		System.out.println(rsp);*/

		// 汤阴县
		GetRandomString getString = new GetRandomString();
		String key = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC14GfgsTkNHW3BrdMg3JDFzXDZ3qp5oeLDI7nKou1IuZN7Bu3higboeRxxNVqW+Dluo9hE6fT4U2Z4is5svpzBdKbIrdTBMbhOcq9RvseOz+zbZKMQLCf0ziLrm+4VcMzWiCxVajPWi1Atn/Xf8aWjqh3JQp4EkDUCnB7fOcQ9IQIDAQAB";
		String str = getString.GetString(key, 8);
		StringBuilder builder = new StringBuilder();
		String yzm = builder.append("000033")
				.append("aef44202fbdd96168aafb6c2ccd3745f").append(str).toString();
		System.out.println(yzm);
		GetRsaString getRsa = new GetRsaString();
		//验证码加密
		String keyWord = getRsa.GetRsaStr(yzm);
		System.out.println("yzm===="+keyWord);
		/*EstateQueryReq req = new EstateQueryReq();
		req.setYzm(keyWord);
		req.setYjm("140600-01");
		req.setSfzh("410926196511100019");
		String reqJson = JacksonFactory.writeJson(req);
		// 汤阴县  账号/密码 000033 /aef44202fbdd96168aafb6c2ccd3745f    查询接口地址 http://192.168.18.197/gdbdc/THXX/THXXDYZM/GetCertInfo
		String URL1 = "http://192.168.18.197/gdbdc/THXX/THXXDYZM/GetCertInfo";
		String rspnStr = HttpClientFactory.getInstance().doPost(reqJson, URL1);
		PYQueryRsp rsp = JacksonFactory.readJson(rspnStr, PYQueryRsp.class);
		System.out.println(rsp);*/

	}
}