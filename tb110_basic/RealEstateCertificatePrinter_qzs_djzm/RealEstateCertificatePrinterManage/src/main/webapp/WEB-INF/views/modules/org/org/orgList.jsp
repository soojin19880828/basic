<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="/WEB-INF/tlds/shiro.tld"%>
<!DOCTYPE html>
<div class="datatables" style="display: block">
	<div class="module-head">
		<h3>机构信息</h3>
		<div style="float: right; margin-top: -24px;">
			<shiro:hasPermission name="org:create">
				<button class="btn btn-info" id="orgCreate">新建</button>
			</shiro:hasPermission>
			<shiro:hasPermission name="org:update">
				<button class="btn btn-success" id="orgUpdate" style="display: none">编辑</button>
			</shiro:hasPermission>
			<shiro:hasPermission name="org:cancle">
				<button class="btn btn-danger" id="orgCancle" style="display: none">删除</button>
			</shiro:hasPermission>
		</div>
	</div>
	<div class="module-head">
		<div>
			<label style="display: inline;">机构编码：</label> <input type="text"
				id="sOrgCode" name="sOrgCode" class="span2"> <label
				style="display: inline;">机构名称：</label> <input type="text"
				id="sOrgName" name="sOrgName" class="span2">
		</div>
		<shiro:hasPermission name="org:search">
			<button class="btn btn-info" id="orgSearch">查询</button>
			<button class="btn btn-success" id="orgReset">重置</button>
		</shiro:hasPermission>
	</div>
	<div class="module-body table">
		<div class="alert alert-error" style="display: none;" id="operError">
			<button type="button" class="close" data-dismiss="alert">×</button>
			<strong>错误!</strong><i></i>
		</div>
		<div class="alert alert-success" style="display: none;"
			id="operSuccess">
			<button type="button" class="close" data-dismiss="alert">×</button>
			<strong>成功!</strong><i></i>
		</div>
		<table style="width: 100%;"
			class="orgTable table table-bordered table-striped table-message display">
		</table>
	</div>
</div>