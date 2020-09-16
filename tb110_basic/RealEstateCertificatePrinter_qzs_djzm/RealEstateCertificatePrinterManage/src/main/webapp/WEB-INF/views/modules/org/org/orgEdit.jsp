<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<div class="editform" style="display: none">
	<div class="module-head">
		<h3 class="orgEdit"></h3>
		<div style="float: right; margin-top: -24px;">
			<button class="btn btn-info" id="submitOrg"
				data-loading-text="提交中...">提交</button>
			<button class="btn btn-success" id="resetOrg">重置</button>
			<button class="btn btn-success" id="nextOrg">新建</button>
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
		<form class="form-horizontal row-fluid orgEdit">
			<input type="hidden" name="orgId" id="orgId">
			<div class="control-group">
				<label class="control-label" for="orgCode">机构编码</label>
				<div class="controls">
					<input type="text" id="orgCode" name="orgCode"
						placeholder="请输入机构编码..." class="span8">
				</div>
			</div>
			<div class="control-group">
				<label class="control-label" for="orgName">机构名称</label>
				<div class="controls">
					<input type="text" id="orgName" name="orgName"
						placeholder="请输入机构名称..." class="span8">
				</div>
			</div>
		</form>
	</div>
</div>