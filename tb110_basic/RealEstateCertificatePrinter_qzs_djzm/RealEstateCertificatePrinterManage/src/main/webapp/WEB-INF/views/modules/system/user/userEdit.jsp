<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<div class="editform" style="display: none">
	<div class="module-head">
		<h3 class="userEdit"></h3>
		<div style="float: right; margin-top: -24px;">
			<button class="btn btn-info" id="submitUser">提交</button>
			<button class="btn btn-success" id="resetUser">重置</button>
			<button class="btn btn-success" id="nextUser">新建</button>
			<button class="btn btn-warning" id="backTable">返回</button>
			<button class="btn btn-primary" id="changeUsername">账号</button>
			<button class="btn btn-danger" id="changePassword">密码</button>
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
		<form class="form-horizontal row-fluid userEdit">
			<input type="hidden" name="userId" id="userId">
			<div class="control-group">
				<label class="control-label" for="userName">用户姓名</label>
				<div class="controls">
					<input type="text" id="userName" name="userName"
						placeholder="请输入用户姓名..." class="span8">
				</div>
			</div>
			<div class="control-group">
				<label class="control-label" for="loginUsername">登录账号</label>
				<div class="controls">
					<input type="text" id="loginUsername" name="loginUsername"
						placeholder="请输入登录账号..." class="span8">
				</div>
			</div>
			<div class="control-group">
				<label class="control-label" for="loginPassword">登录密码</label>
				<div class="controls">
					<input type="password" id="loginPassword" name="loginPassword"
						placeholder="请输入登录密码..." class="span8">
				</div>
			</div>
			<div class="control-group">
				<label class="control-label" for="repeatPassword">重复密码</label>
				<div class="controls">
					<input type="password" id="repeatPassword" name="repeatPassword"
						placeholder="请输入登录密码..." class="span8">
				</div>
			</div>
			<div class="control-group">
				<label class="control-label" for="userMobile">手机号</label>
				<div class="controls">
					<input type="text" id="userMobile" name="userMobile"
						placeholder="请输入手机号..." class="span8">
				</div>
			</div>
			<div class="control-group">
				<label class="control-label" for="userIdnumber">身份证号</label>
				<div class="controls">
					<input type="text" id="userIdnumber" name="userIdnumber"
						placeholder="请输入身份证号..." class="span8">
				</div>
			</div>
			<div class="control-group">
				<label class="control-label" for="roleId">用户角色</label>
				<div class="controls">
					<select id="roleId" name="roleId" tabindex="1" class="span8">
						<option value=""></option>
					</select>
				</div>
			</div>
		</form>
	</div>
</div>