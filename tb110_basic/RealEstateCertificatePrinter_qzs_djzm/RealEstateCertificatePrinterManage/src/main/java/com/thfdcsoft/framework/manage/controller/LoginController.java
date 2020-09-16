package com.thfdcsoft.framework.manage.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.thfdcsoft.framework.manage.constant.BaseConstant;
import com.thfdcsoft.framework.manage.constant.ErrorConstant;

/**
 * 登录控制器
 * 
 * @author 张嘉琪
 * @date 2017年11月21日下午4:28:52
 */
@Controller
public class LoginController extends BaseController {

	/**
	 * 跳转至登录页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "${ctxPath}", method = RequestMethod.GET)
	public String index(HttpServletRequest request, HttpServletResponse response) {
		return "login";
	}

	/**
	 * 登录<br>
	 * 校验登录信息<br>
	 * 返回用户角色菜单列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(HttpServletRequest request, HttpServletResponse response) {
		UsernamePasswordToken token = new UsernamePasswordToken(
				request.getParameter(BaseConstant.SessionAttribute.LOGIN_USERNAME),
				request.getParameter(BaseConstant.SessionAttribute.LOGIN_PASSWORD));
		Subject subject = SecurityUtils.getSubject();
		try {
			subject.login(token);
		} catch (UnknownAccountException e) {
			request.setAttribute(BaseConstant.SessionAttribute.ERROR_MSG, ErrorConstant.LoginError.UNKNOWN_ACCOUNT);
			return "login";
		} catch (IncorrectCredentialsException e) {
			request.setAttribute(BaseConstant.SessionAttribute.ERROR_MSG,
					ErrorConstant.LoginError.INCORRECT_CREDENTIALS);
			return "login";
		} catch (LockedAccountException e) {
			request.setAttribute(BaseConstant.SessionAttribute.ERROR_MSG, ErrorConstant.LoginError.LOCKED_ACCOUNT);
			return "login";
		}
		if (subject.isAuthenticated()) {
			return "redirect:index";
		} else {
			request.setAttribute(BaseConstant.SessionAttribute.ERROR_MSG, ErrorConstant.LoginError.LOGIN_FAILED);
			return "login";
		}
	}

	/**
	 * 登出
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(HttpServletRequest request, HttpServletResponse response) {
		Subject subject = SecurityUtils.getSubject();
		request.getSession().removeAttribute(BaseConstant.SessionAttribute.USER_ID);
		request.getSession().removeAttribute(BaseConstant.SessionAttribute.USER_NAME);
		subject.logout();
		return "login";
	}
}
