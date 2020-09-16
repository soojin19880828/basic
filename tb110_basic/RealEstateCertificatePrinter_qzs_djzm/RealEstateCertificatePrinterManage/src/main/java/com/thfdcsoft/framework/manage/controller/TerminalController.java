package com.thfdcsoft.framework.manage.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.thfdcsoft.framework.common.json.jackson.JacksonFactory;
import com.thfdcsoft.framework.manage.dao.QueryTerminalsMapper;
import com.thfdcsoft.framework.manage.entity.QueryTerminals;

@Controller
@RequestMapping("/terminal")
public class TerminalController extends BaseController {
	
	@Autowired
	private QueryTerminalsMapper queryTerminalsMapper;
	
	/**
	 * 跳转至设备管理页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index(HttpServletRequest request, HttpServletResponse response) {
		return "modules/terminals/terminal/terminalUI";
	}
	
	/**
	 * 查询终端机信息
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/query", method = RequestMethod.GET)
	public void queryTerminals(HttpServletRequest request, HttpServletResponse response) {
		List<QueryTerminals> terminals = this.queryTerminalsMapper.selectAll();
		System.out.println( "99999"+JacksonFactory.writeJson(terminals));
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", JacksonFactory.writeJson(terminals));
		writeJSONResult(result, response);
	}

}
