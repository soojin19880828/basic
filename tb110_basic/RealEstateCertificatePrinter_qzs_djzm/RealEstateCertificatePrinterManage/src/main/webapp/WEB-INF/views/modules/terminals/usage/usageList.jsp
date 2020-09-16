<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="/WEB-INF/tlds/shiro.tld"%>
<!DOCTYPE html>
<div class="datatables" style="display: block">
	<div class="module-head">
		<h3>查询使用记录</h3>
		<div style="float: right; margin-top: -24px;">
			<shiro:hasPermission name="usage:detail">
				<button class="btn btn-info" id="usageDetail" style="display: none">详情</button>
			</shiro:hasPermission>
			<shiro:hasPermission name="usage:log">
				<button class="btn btn-success" id="usageLog" style="display: none">日志</button>
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
			class="usageTable table table-bordered table-striped table-message display">
		</table>
	</div>
</div>