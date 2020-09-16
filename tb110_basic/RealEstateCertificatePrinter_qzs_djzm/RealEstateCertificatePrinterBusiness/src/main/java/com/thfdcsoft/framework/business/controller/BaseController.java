package com.thfdcsoft.framework.business.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.thfdcsoft.framework.common.json.jackson.JacksonFactory;

public abstract class BaseController {
	
	private static final Logger logger = LoggerFactory.getLogger(BaseController.class);

	public void writeJSONResult(Object result, HttpServletResponse response) {
		try {
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html");
			response.getWriter().write(JacksonFactory.writeJson(result));
		} catch (IOException e) {
			logger.error("response回写失败", e);
		}
	}

	public void writeJSONArrayResult(@SuppressWarnings("rawtypes") List result, HttpServletResponse response) {
		try {
			response.setCharacterEncoding("UTF-8");
			response.setContentType("appliaction/json");
			response.getWriter().write(JacksonFactory.writeJson(result));
		} catch (IOException e) {
			logger.error("response回写失败", e);
		}
	}

	public void writeJSONArrayResult(String result, HttpServletResponse response) {
		try {
			response.setCharacterEncoding("UTF-8");
			response.setContentType("appliaction/json");
			response.getWriter().write(JacksonFactory.writeJson(result));
		} catch (IOException e) {
			logger.error("response回写失败", e);
		}
	}
}
