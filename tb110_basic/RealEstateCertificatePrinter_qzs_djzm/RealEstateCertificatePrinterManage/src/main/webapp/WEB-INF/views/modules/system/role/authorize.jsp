<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<div class="modulesTree" style="display: none">
	<div class="module-head">
		<h3 class="authTree"></h3>
		<div style="float: right; margin-top: -24px;">
			<button class="btn btn-info" id="submitAuth">提交</button>
			<button class="btn btn-warning" id="backEdit">返回</button>
		</div>
	</div>
	<div class="module-body">
		<div class="alert" style="display: none;" id="authWarning">
			<button type="button" class="close" data-dismiss="alert">×</button>
			<strong>警告!</strong><i></i>
		</div>
		<div class="alert alert-error" style="display: none;" id="authError">
			<button type="button" class="close" data-dismiss="alert">×</button>
			<strong>错误!</strong><i></i>
		</div>
		<div class="alert alert-success" style="display: none;"
			id="authSuccess">
			<button type="button" class="close" data-dismiss="alert">×</button>
			<strong>成功!</strong><i></i>
		</div>
		<ul class="ztree authTree" id="authTree"></ul>
	</div>
</div>