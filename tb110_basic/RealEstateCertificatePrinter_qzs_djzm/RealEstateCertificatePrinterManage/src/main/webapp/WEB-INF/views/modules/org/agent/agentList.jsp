<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="/WEB-INF/tlds/shiro.tld"%>
<!DOCTYPE html>
<div class="datatables" style="display: block">
	<div class="module-head">
		<h3>代理人信息</h3>
		<div style="float: right; margin-top: -24px;">
			<shiro:hasPermission name="agent:create">
				<button class="btn btn-info" id="agentCreate">新建</button>
			</shiro:hasPermission>
			<shiro:hasPermission name="agent:update">
				<button class="btn btn-success" id="agentUpdate"
					style="display: none">编辑</button>
			</shiro:hasPermission>
			<shiro:hasPermission name="agent:cancle">
				<button class="btn btn-danger" id="agentCancle"
					style="display: none">删除</button>
			</shiro:hasPermission>
		</div>
	</div>
	<div class="module-head">
		<div>
			<label style="display: inline;">机构名称：</label> <select id="sOrgId"
				name="sOrgId" tabindex="1" class="span2">
				<option value=""></option>
			</select> <label style="display: inline;">代理人身份证号：</label> <input type="text"
				id="sAgentIdNumber" name="sAgentIdNumber" class="span2"> <label
				style="display: inline;">代理人姓名：</label> <input type="text"
				id="sAgentFullName" name="sAgentFullName" class="span2">
		</div>
		<shiro:hasPermission name="agent:search">
			<button class="btn btn-info" id="agentSearch">查询</button>
			<button class="btn btn-success" id="agentReset">重置</button>
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
			class="agentTable table table-bordered table-striped table-message display">
		</table>
	</div>
</div>