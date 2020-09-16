package com.thfdcsoft.framework.manage.constant;

/**
 * 常量参数
 * 
 * @author 张嘉琪
 * @date 2017年11月20日下午5:38:48
 */
public abstract class BaseConstant {

	// Session参数
	public interface SessionAttribute {

		// 用户ID
		public static final String USER_ID = "userId";

		// 用户名
		public static final String USER_NAME = "userName";

		// 登录账号
		public static final String LOGIN_USERNAME = "loginUsername";

		// 登录密码
		public static final String LOGIN_PASSWORD = "loginPassword";

		// 错误信息
		public static final String ERROR_MSG = "errorMsg";

		// JSON格式菜单列表
		public static final String MENU_JSON = "menuJson";
	}

	// 功能模块级别
	public interface ModuleLevel {

		// 父模块
		public static final String MODULE = "01";

		// 子页面
		public static final String PAGE = "02";

		// 页面按钮
		public static final String BUTTON = "03";
	}

	// 用户状态
	public interface UserStatus {

		// 正常可用
		public static final String ENABLE = "00";

		// 异常锁定
		public static final String LOCKED = "01";
	}

	// 终端机状态
	public interface TerminalStatus {

		// 正常
		public static final String NORMAL = "00";

		// 警告
		public static final String WARNING = "01";

		// 错误
		public static final String DANGER = "02";
	}

	// 默认角色
	public interface DefaultRoles {

		// 超级管理员
		public static final String ADMIN = "00000000000000000001";

		// 运维工程师
		public static final String OPERA = "00000000000000000002";
	}

	// 默认用户
	public interface DefaultUsers {

		// 超级管理员
		public static final String ADMIN = "00000000000000000001";

		// 运维工程师
		public static final String OPERA = "00000000000000000002";
	}
	
	// ZTree节点默认值
	public interface DefaultZNode {
		
		// 根节点PID
		public static final String ROOT_PID = "0";
	}
	
	// 统计周期
	public interface StatisticsCycle {
		
		// 日
		public static final String DAY = "00";
		
		// 周
		public static final String WEEK = "01";
		
		// 月
		public static final String MONTH = "02";
		
		// 年
		public static final String YEAR = "03";
	}
}
