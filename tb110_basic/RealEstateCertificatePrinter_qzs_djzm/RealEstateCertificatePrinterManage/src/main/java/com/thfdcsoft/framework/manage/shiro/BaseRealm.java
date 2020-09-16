package com.thfdcsoft.framework.manage.shiro;

import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

import com.thfdcsoft.framework.manage.constant.BaseConstant;
import com.thfdcsoft.framework.manage.dao.UserAuthorizeMapper;
import com.thfdcsoft.framework.manage.entity.UserAuthorize;
import com.thfdcsoft.framework.manage.entity.UserInfo;
import com.thfdcsoft.framework.manage.service.LoginService;

/**
 * Shiro登录验证及角色菜单获取
 * 
 * @author 张嘉琪
 * @date 2017年11月20日下午5:28:04
 */
public class BaseRealm extends AuthorizingRealm {

	@Autowired
	private UserAuthorizeMapper userAuthorizeMapper;

	@Autowired
	private LoginService loginService;

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principal) {
		Subject subject = SecurityUtils.getSubject();
		SimpleAuthorizationInfo authorization = new SimpleAuthorizationInfo();
		String userId = subject.getSession().getAttribute(BaseConstant.SessionAttribute.USER_ID).toString();
		List<UserAuthorize> authorizeList = this.userAuthorizeMapper.selectByUserId(userId);
		if (!authorizeList.isEmpty()) {
			for (UserAuthorize authorize : authorizeList) {
				if (BaseConstant.ModuleLevel.BUTTON.equals(authorize.getModuleLevel())) {
					authorization.addStringPermission(authorize.getModuleUrl());
				}
			}
		}
		return authorization;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		UsernamePasswordToken userToken = (UsernamePasswordToken) token;
		String username = userToken.getUsername();
		String password = String.valueOf(userToken.getPassword());
		UserInfo user = this.loginService.selectByLoginUsername(username);
		if (user == null) {
			throw new UnknownAccountException();
		}
		if (!password.equals(user.getLoginPassword())) {
			throw new IncorrectCredentialsException();
		}
		if (BaseConstant.UserStatus.LOCKED.equals(user.getUserStatus())) {
			throw new LockedAccountException();
		}
		Subject subjuct = SecurityUtils.getSubject();
		subjuct.getSession().setAttribute(BaseConstant.SessionAttribute.USER_ID, user.getUserId());
		subjuct.getSession().setAttribute(BaseConstant.SessionAttribute.USER_NAME, user.getUserName());
		return new SimpleAuthenticationInfo(username, password, user.getUserName());
	}

}
