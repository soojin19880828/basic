<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<div class="detailForm" style="display: none">
	<div class="module-head">
		<h3>使用记录详情</h3>
		<div style="float: right; margin-top: -24px;">
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
		<form class="form-horizontal row-fluid">
			<input type="hidden" name="recordId" id="recordId">
			<div class="control-group">
				<label class="control-label" for="userFullname">用户姓名</label>
				<div class="controls">
					<input type="text" id="userFullname" name="userFullname" class="span8" disabled="disabled">
				</div>
			</div>
			<div class="control-group">
				<label class="control-label" for="userIdnumber">身份证号</label>
				<div class="controls">
					<input type="text" id="userIdnumber" name="userIdnumber" class="span8" disabled="disabled">
				</div>
			</div>
			<div class="control-group">
				<label class="control-label" for="usageTime">使用时间</label>
				<div class="controls">
					<input type="text" id="usageTime" name="usageTime" class="span8" disabled="disabled">
				</div>
			</div>
			<div class="control-group">
				<label class="control-label" for="deviceNumber">设备编号</label>
				<div class="controls">
					<input type="text" id="deviceNumber" name="deviceNumber" class="span8" disabled="disabled">
				</div>
			</div>
			<div class="control-group">
				<label class="control-label" for="userIdPic">身份证照片</label>
				<div class="controls">
					<img src="#" id="userIdPic" name="userIdPic">
				</div>
			</div>
			<div class="control-group">
				<label class="control-label" for="userDetPic">现场照片</label>
				<div class="controls">
					<img src="#" id="userDetPic" name="userDetPic">
				</div>
			</div>
		</form>
	</div>
</div>