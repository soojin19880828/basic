<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="/WEB-INF/tlds/shiro.tld"%>
<!DOCTYPE html>
<div class="datatables" style="display: block">
	<div class="module-head">
		<h3>角色信息</h3>
		<div style="float: right; margin-top: -24px;">
			<shiro:hasPermission name="role:create">
				<button class="btn btn-info" id="roleCreate">新建</button>
			</shiro:hasPermission>
			<shiro:hasPermission name="role:update">
				<button class="btn btn-success" id="roleUpdate"
					style="display: none">编辑</button>
			</shiro:hasPermission>
			<shiro:hasPermission name="role:cancle">
				<button class="btn btn-danger" id="roleCancle" style="display: none">删除</button>
			</shiro:hasPermission>
		</div>
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
			class="rolesTable table table-bordered table-striped table-message display">
		</table>
	</div>
</div>