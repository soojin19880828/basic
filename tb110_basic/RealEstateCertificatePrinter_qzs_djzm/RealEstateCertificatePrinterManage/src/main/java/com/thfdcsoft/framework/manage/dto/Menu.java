package com.thfdcsoft.framework.manage.dto;

import java.util.List;

/**
 * 菜单对象
 * 
 * @author 张嘉琪
 * @date 2017年11月21日下午5:16:46
 */
public class Menu implements Comparable<Menu>{

	// 菜单ID
	private String menuId;

	// 菜单名称
	private String menuName;

	// 菜单图标样式
	private String menuIconCss;

	// 跳转地址
	private String menuUrl;

	// 父菜单ID
	private String superiorMenuId;

	// 显示顺序
	private String displayOrder;

	// 下级菜单列表
	private List<Menu> subordinateMenus;

	public Menu(String menuId, String menuName, String menuIconCss, String menuUrl, String superiorMenuId,
			String displayOrder) {
		super();
		this.setMenuId(menuId);
		this.setMenuName(menuName);
		this.setMenuIconCss(menuIconCss);
		this.setMenuUrl(menuUrl);
		this.setSuperiorMenuId(superiorMenuId);
		this.setDisplayOrder(displayOrder);
	}

	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getMenuIconCss() {
		return menuIconCss;
	}

	public void setMenuIconCss(String menuIconCss) {
		this.menuIconCss = menuIconCss;
	}

	public String getMenuUrl() {
		return menuUrl;
	}

	public void setMenuUrl(String menuUrl) {
		this.menuUrl = menuUrl;
	}

	public String getSuperiorMenuId() {
		return superiorMenuId;
	}

	public void setSuperiorMenuId(String superiorMenuId) {
		this.superiorMenuId = superiorMenuId;
	}

	public String getDisplayOrder() {
		return displayOrder;
	}

	public void setDisplayOrder(String displayOrder) {
		this.displayOrder = displayOrder;
	}

	public List<Menu> getSubordinateMenus() {
		return subordinateMenus;
	}

	public void setSubordinateMenus(List<Menu> subordinateMenus) {
		this.subordinateMenus = subordinateMenus;
	}

	@Override
	public int compareTo(Menu menu) {
		return this.getDisplayOrder().compareTo(menu.getDisplayOrder());
	}
}
