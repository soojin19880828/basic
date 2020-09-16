package com.thfdcsoft.framework.business.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

/**
 * Json解析封装工厂类<br>
 * 使用Jackson2.9.1
 * 
 * @author Administrator
 * @date 2017年9月18日 下午1:33:42
 */
public final class JacksonFactory {

	// Jackson映射对象
	private static ObjectMapper mapper = new ObjectMapper();
	static {
		// 忽略对象不存在的字段
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		// 忽略属性或值为空的对象
		mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, true);
		// 缩放排列输出-正式使用时需设为false
		mapper.configure(SerializationFeature.INDENT_OUTPUT, false);
		// 忽略空字段
		mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
	}

	/**
	 * Json转对象
	 * 
	 * @param json
	 * @param tClass
	 * @return
	 */
	public static <T> T readJson(String json, Class<T> tClass) {
		if (json == null || "".equals(json.trim())) {
			return null;
		}
		T t = null;
		try {
			t = mapper.readValue(json, tClass);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return t;
	}

	/**
	 * Json转List对象
	 * 
	 * @param json
	 * @param tClass
	 * @return
	 */
	public static <T> List<T> readJsonList(String json, Class<T> tClass) {
		if (json == null || "".equals(json.trim())) {
			return null;
		}
		JavaType type = mapper.getTypeFactory().constructParametricType(ArrayList.class, tClass);
		List<T> t = new ArrayList<T>();
		try {
			t = mapper.readValue(json, type);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return t;
	}

	/**
	 * Json转Map对象
	 * 
	 * @param json
	 * @param tClass
	 * @return
	 */
	public static <T> Map<String, T> readJsonMap(String json, Class<T> tClass) {
		if (json == null || "".equals(json.trim())) {
			return null;
		}
		JavaType type = mapper.getTypeFactory().constructParametricType(HashMap.class, String.class, tClass);
		Map<String, T> t = new HashMap<String, T>();
		try {
			t = mapper.readValue(json, type);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return t;
	}

	/**
	 * 转换Json字符串,null和空串忽略
	 * 
	 * @param obj
	 * @return
	 */
	public static <T> String writeJson(Object obj) {
		String json = null;
		try {
			json = mapper.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return json;
	}
}
