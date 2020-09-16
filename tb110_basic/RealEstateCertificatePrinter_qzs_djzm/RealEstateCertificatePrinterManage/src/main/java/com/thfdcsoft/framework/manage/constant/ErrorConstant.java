package com.thfdcsoft.framework.manage.constant;

/**
 * 异常参数
 * 
 * @author 张嘉琪
 * @date 2017年11月21日下午7:48:31
 */
public abstract class ErrorConstant {

	// 登录异常
	public interface LoginError {

		// 未知账户
		public static final String UNKNOWN_ACCOUNT = "未知账户";

		// 密码错误
		public static final String INCORRECT_CREDENTIALS = "密码错误";

		// 账户已冻结
		public static final String LOCKED_ACCOUNT = "账户已冻结";

		// 登录失败
		public static final String LOGIN_FAILED = "登录失败";
	}
}
