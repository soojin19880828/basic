<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="/WEB-INF/tlds/shiro.tld"%>
<!DOCTYPE html>
<div class="datatables" style="display: block">
	<div class="module-head">
		<h3>设备管理</h3>
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
			class="terminalTable table table-bordered table-striped table-message display">
		</table>
	</div>
</div>