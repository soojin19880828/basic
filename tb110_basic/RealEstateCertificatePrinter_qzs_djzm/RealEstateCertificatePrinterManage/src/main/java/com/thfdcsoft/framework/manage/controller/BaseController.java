package com.thfdcsoft.framework.manage.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.thfdcsoft.framework.common.json.jackson.JacksonFactory;
import com.thfdcsoft.framework.manage.dto.Page;

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

	public PageBounds getPageBounds(Page page) {
		int start = Integer.parseInt(page.getStart());
		int limit = Integer.parseInt(page.getLength());
		return new PageBounds(start / limit + 1, limit);
	}
}
