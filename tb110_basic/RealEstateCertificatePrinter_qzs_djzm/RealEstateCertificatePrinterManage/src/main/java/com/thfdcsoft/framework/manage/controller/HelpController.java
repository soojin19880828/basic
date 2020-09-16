package com.thfdcsoft.framework.manage.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.thfdcsoft.framework.common.http.dto.BaseHttpRspn;
import com.thfdcsoft.framework.common.json.jackson.JacksonFactory;
import com.thfdcsoft.framework.common.string.StringUtils;
import com.thfdcsoft.framework.manage.constant.BaseConstant;
import com.thfdcsoft.framework.manage.dto.DataTableView;
import com.thfdcsoft.framework.manage.dto.Page;
import com.thfdcsoft.framework.manage.entity.Help;
import com.thfdcsoft.framework.manage.entity.HelpExample;
import com.thfdcsoft.framework.manage.entity.UserInfo;
import com.thfdcsoft.framework.manage.entity.HelpExample.Criteria;
import com.thfdcsoft.framework.manage.service.HelpService;
import com.thfdcsoft.framework.manage.util.AutoIdFactory;

/**
 * 设备index页面 公告控制器
 * @author weaso
 *
 */
@Controller
@RequestMapping("/help")
public class HelpController extends BaseController {

	
	@Autowired
	private HelpService helpService;
	
	
	@RequestMapping(value = "/cancleHelp", method = RequestMethod.POST)
	public void cancleHelp(HttpServletRequest request, HttpServletResponse response, Help help) {
		BaseHttpRspn result = new BaseHttpRspn(false);
		System.out.println(help.toString());
		int i = helpService.cancleHelp(help);
			if (i > 0) {
				result.setResult(true);
				result.setRespObj(help);
				result.setRespMsg("删除成功");
			} else {
				result.setResult(false);
				result.setRespMsg("删除失败");
			}
		writeJSONResult(result, response);
	}
	/*
	 * 新增
	 */
	@RequestMapping(value = "/increaseHelp", method = RequestMethod.POST)
	public void increaseHelp(HttpServletRequest request, HttpServletResponse response, Help help) {
		BaseHttpRspn result = new BaseHttpRspn(false);
		int i = helpService.increaseHelp(help);
			if (i > 0) {
				result.setResult(true);
				result.setRespObj(help);
				result.setRespMsg("提交成功");
			} else {
				result.setResult(false);
				result.setRespMsg("提交失败");
			}
		writeJSONResult(result, response);
	}
	
	
	/*
	 * 修改公告
	 */
	@RequestMapping(value = "/updateHelp", method = RequestMethod.POST)
	public void submitUser(HttpServletRequest request, HttpServletResponse response, Help help) {
		BaseHttpRspn result = new BaseHttpRspn(false);
		int i = helpService.updateHelp(help);
			if (i > 0) {
				result.setResult(true);
				result.setRespObj(help);
				result.setRespMsg("提交成功");
			} else {
				result.setResult(false);
				result.setRespMsg("提交失败");
			}
		writeJSONResult(result, response);
	}
	
	/*
	 * 页面查询办事指南
	 */
	@RequestMapping(value = "/query", produces = "text/json;charset=UTF-8")
	@ResponseBody
	public String query(Page page) {
		System.out.println(page.toString());
		List<Help> helpList = helpService.selectByHelpPage(page);
		if(helpList != null) {
			DataTableView<Help> view = new DataTableView<Help>();
			view.setData(helpList);
			Long i = helpService.selectByHelpTitle(page.getTitle());
			view.setRecordsTotal(i.intValue());
			view.setRecordsFiltered(i.intValue());
			return JacksonFactory.writeJson(view);
		}
		return null;
	}
	
	/*
	 * 接收business 请求公告内容
	 */
	@RequestMapping(value = "/search", method = RequestMethod.POST)
	@ResponseBody
	public String searchNoticeByTitle(HttpServletRequest request, HttpServletResponse response,@RequestBody String helpTitle) {
		System.out.println("进入公告查询方法！" + helpTitle);
		BaseHttpRspn result = new BaseHttpRspn(false);
		List<Help> helpList = helpService.findDataByHelpTitle(helpTitle);
		if(helpTitle != null) {
			result.setResult(true);
			result.setRespJson(JacksonFactory.writeJson(helpList));
		}
		System.out.println(JacksonFactory.writeJson(result));
		return JacksonFactory.writeJson(result);
	}
	
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index(HttpServletRequest request, HttpServletResponse response) {
		return "modules/terminals/help/helpUI";
	}
}
