<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ taglib prefix="shiro" uri="/WEB-INF/tlds/shiro.tld"%>
<!DOCTYPE html>
<div class="editform" style="display: none">
	<div class="module-head">
		<h3 class="helpEdit"></h3>
			<div style="float: right; margin-top: -24px;">
			<shiro:hasPermission name="help:submit">
				<button class="btn btn-info" id="submithelp">提交</button>
			</shiro:hasPermission>
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
		<form class="form-horizontal row-fluid helpEdit">
			<div class="control-group" style="display : none">
				<label class="control-label" for="recordId">ID</label>
				<div class="controls">
					<input type="text" id="recordId" name="recordId"
						placeholder="请输入编号..." class="span8">
				</div>
			</div>
			<div class="control-group">
				<label class="control-label" for="number">编号</label>
				<div class="controls">
					<input type="text" id="number" name="number"
						placeholder="请输入编号..." class="span8">
				</div>
			</div>
			<div class="control-group">
				<label class="control-label" for="content">内容</label>
				<div class="controls">
					<textarea id="content" name="content"
						placeholder="请输入内容..." class="span8" style="height: 200px"></textarea>
				</div>
			</div>
		</form>
	</div>
</div>