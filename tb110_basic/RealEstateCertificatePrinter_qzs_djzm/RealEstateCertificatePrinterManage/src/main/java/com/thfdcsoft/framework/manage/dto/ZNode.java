package com.thfdcsoft.framework.manage.dto;

/**
 * ZTree 节点对象类
 * 
 * @author 张嘉琪
 * @date 2017年11月30日上午11:31:58
 */
public class ZNode implements Comparable<ZNode> {

	// 节点ID
	private String id;

	// 父节点ID
	private String pId;

	// 节点名称
	private String name;

	// 是否展开
	private Boolean open;

	// 是否勾选
	private Boolean checked;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getpId() {
		return pId;
	}

	public void setpId(String pId) {
		this.pId = pId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getOpen() {
		return open;
	}

	public void setOpen(Boolean open) {
		this.open = open;
	}

	public Boolean getChecked() {
		return checked;
	}

	public void setChecked(Boolean checked) {
		this.checked = checked;
	}

	@Override
	public int compareTo(ZNode zNode) {
		return this.getId().compareTo(zNode.getId());
	}
}
