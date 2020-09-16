package com.thfdcsoft.framework.manage.controller;

import java.util.ArrayList;
import java.util.Collections;
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
import com.thfdcsoft.framework.manage.constant.BaseConstant;
import com.thfdcsoft.framework.manage.dao.QueryTerminalsMapper;
import com.thfdcsoft.framework.manage.dao.UserAuthorizeMapper;
import com.thfdcsoft.framework.manage.dto.Menu;
import com.thfdcsoft.framework.manage.entity.QueryTerminals;

import com.thfdcsoft.framework.manage.entity.UserAuthorize;

/**
 * 首页控制器
 * 
 * @author 张嘉琪
 * @date 2017年11月23日下午4:59:45
 */
@Controller
public class IndexController extends BaseController {

	@Autowired
	private UserAuthorizeMapper userAuthorizeMapper;

	@Autowired
	private QueryTerminalsMapper queryTerminalsMapper;

	/**
	 * 跳转至首页<br>
	 * 返回首页菜单<br>
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index(HttpServletRequest request, HttpServletResponse response) {
		String userId = (String) request.getSession().getAttribute(BaseConstant.SessionAttribute.USER_ID);
		String menusJson = JacksonFactory.writeJson(this.buildMenuTree(userId));
		request.getSession().setAttribute(BaseConstant.SessionAttribute.MENU_JSON, menusJson);
		return "index/index";
	}

	/**
	 * 查询终端机信息
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/queryTerminals", method = RequestMethod.GET)
	public void queryTerminals(HttpServletRequest request, HttpServletResponse response) {
		List<QueryTerminals> terminals = this.queryTerminalsMapper.selectAll();	
		
		System.out.println("0000000000"+JacksonFactory.writeJson(terminals));
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", JacksonFactory.writeJson(terminals));
		writeJSONResult(result, response);
	}

	/**
	 * 根据用户ID组装用户角色菜单列表<br>
	 * 对用户权限查询结果进行两次遍历<br>
	 * 第一次获取功能模块列表<br>
	 * 第二次获取功能页面列表
	 * 
	 * @param userId
	 * @return
	 */
	public List<Menu> buildMenuTree(String userId) {
		List<UserAuthorize> authorizeList = this.userAuthorizeMapper.selectByUserId(userId);
		List<Menu> modules = new ArrayList<Menu>();
		if (!authorizeList.isEmpty()) {
			for (UserAuthorize module : authorizeList) {
				if (BaseConstant.ModuleLevel.MODULE.equals(module.getModuleLevel())) {
					Menu moduleMenu = new Menu(module.getModuleId(), module.getModuleName(), module.getIconCss(),
							module.getModuleUrl(), module.getSupeModuleId(), module.getDisplayOrder());
					List<Menu> pages = new ArrayList<Menu>();
					for (UserAuthorize page : authorizeList) {
						if (BaseConstant.ModuleLevel.PAGE.equals(page.getModuleLevel())
								&& module.getModuleId().equals(page.getSupeModuleId())) {
							Menu pageMenu = new Menu(page.getModuleId(), page.getModuleName(), page.getIconCss(),
									page.getModuleUrl(), page.getSupeModuleId(), page.getDisplayOrder());
							pages.add(pageMenu);
						}
					}
					Collections.sort(pages);
					moduleMenu.setSubordinateMenus(pages);
					modules.add(moduleMenu);
				}
			}
		}
		Collections.sort(modules);
		return modules;
	}
}
