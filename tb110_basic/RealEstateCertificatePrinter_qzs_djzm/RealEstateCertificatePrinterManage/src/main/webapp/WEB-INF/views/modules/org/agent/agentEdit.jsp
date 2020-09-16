<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<div class="editform" style="display: none">
	<div class="module-head">
		<h3 class="agentEdit"></h3>
		<div style="float: right; margin-top: -24px;">
			<button class="btn btn-info" id="submitAgent"
				data-loading-text="提交中...">提交</button>
			<button class="btn btn-success" id="resetAgent">重置</button>
			<button class="btn btn-success" id="nextAgent">新建</button>
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
		<form class="form-horizontal row-fluid agentEdit">
			<input type="hidden" name="agentId" id="agentId">
			<div class="control-group">
				<label class="control-label" for="orgId">机构名称</label>
				<div class="controls">
					<select id="orgId" name="orgId" tabindex="1"
						class="span8">
						<option value=""></option>
					</select>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label" for="agentIdNumber">代理人身份证号</label>
				<div class="controls">
					<input type="text" id="agentIdNumber" name="agentIdNumber"
						placeholder="请输入代理人身份证号..." class="span8">
				</div>
			</div>
			<div class="control-group">
				<label class="control-label" for="agentFulName">代理人姓名</label>
				<div class="controls">
					<input type="text" id="agentFullName" name="agentFullName"
						placeholder="请输入代理人姓名..." class="span8">
				</div>
			</div>
		</form>
	</div>
</div>