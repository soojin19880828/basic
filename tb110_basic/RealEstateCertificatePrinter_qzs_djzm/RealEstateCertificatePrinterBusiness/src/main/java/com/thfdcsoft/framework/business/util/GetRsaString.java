package com.thfdcsoft.framework.business.util;

import com.thfdcsoft.framework.business.contant.RecordConstant;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import java.security.PublicKey;

/**
 * 授权码加密 
 */
public class GetRsaString {
	public String GetRsaStr(String str) throws Exception {
		GetPublicKey Key = new GetPublicKey();
		String keyType = "RSA";
		PublicKey publicKey = Key.getKey(RecordConstant.ParametetsConstant.RSA);
		// 创建密钥类 
		Cipher cipher = Cipher.getInstance(keyType);
		// 指定密钥类是 加密 注意 CIPHER类ENCRYPT_MODE常量是 加密 publicKey公钥
		cipher.init(Cipher.ENCRYPT_MODE, publicKey);
		// 进行公钥加密处理
		byte[] bytes = cipher.doFinal(str.getBytes());
		// 返回base64 编码加密之后的字符串
		return Base64.encodeBase64String(bytes);
	}

}
