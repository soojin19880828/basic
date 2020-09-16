<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<div class="editform" style="display: none">
	<div class="module-head">
		<h3 class="roleEdit"></h3>
		<div style="float: right; margin-top: -24px;">
			<button class="btn btn-info" id="submitRole">提交</button>
			<button class="btn btn-success" id="resetRole">重置</button>
			<button class="btn btn-success" id="nextRole">新建</button>
			<button class="btn btn-danger" id="authRole">授权</button>
			<button class="btn btn-warning" id="backTable">返回</button>
		</div>
	</div>
	<div class="module-body">
		<div class="alert" style="display: none;" id="alertWarning">
			<button type="button" class="close" data-dismiss="alert">×</button>
			<strong>警告!</strong><i></i>
		</div>
		<div class="alert alert-error" style="display: none;" id="alertError">
			<button type="button" class="close" data-dismiss="alert">×</button>
			<strong>错误!</strong><i></i>
		</div>
		<div class="alert alert-success" style="display: none;"
			id="alertSuccess">
			<button type="button" class="close" data-dismiss="alert">×</button>
			<strong>成功!</strong><i></i>
		</div>
		<form class="form-horizontal row-fluid roleEdit">
			<input type="hidden" name="roleId" id="roleId">
			<div class="control-group">
				<label class="control-label" for="roleName">角色名称</label>
				<div class="controls">
					<input type="text" id="roleName" name="roleName"
						placeholder="请输入角色名称..." class="span8">
				</div>
			</div>
			<div class="control-group">
				<label class="control-label" for="roleDescribe">角色描述</label>
				<div class="controls">
					<textarea class="span8" rows="5" id="roleDescribe"
						name="roleDescribe" placeholder="请输入角色描述..."></textarea>
				</div>
			</div>
		</form>
	</div>
</div>