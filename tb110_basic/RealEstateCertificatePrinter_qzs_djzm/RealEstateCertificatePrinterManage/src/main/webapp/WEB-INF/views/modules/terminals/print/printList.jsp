<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="/WEB-INF/tlds/shiro.tld"%>
<!DOCTYPE html>
<div class="datatables" style="display: block">
	<div class="module-head" style="height: 100px">
		<h3>查询打印记录</h3>
		<span>开&nbsp始&nbsp日&nbsp期&nbsp&nbsp&nbsp&nbsp&nbsp</span>
		<!--         <input type="text" id="startTime"  onchange="checkNull('startTime')"/>  -->
		<input type="text" id="startTime" />
		<!-- <span>&nbsp&nbsp&nbsp&nbsp</span> -->

		<span>结&nbsp&nbsp束&nbsp&nbsp日&nbsp&nbsp期&nbsp&nbsp&nbsp&nbsp&nbsp</span>
		<input type="text" id="endTime" /> <span>&nbsp&nbsp&nbsp&nbsp</span><br />

		<span>印刷序列号&nbsp&nbsp&nbsp&nbsp</span> <input type="text"
			id="bizNumber2" />
		<!-- <span>&nbsp&nbsp&nbsp&nbsp</span> -->

		<span>不动产权证号&nbsp&nbsp&nbsp&nbsp</span> <input type="text"
			id="certNumber2" />
		<span>&nbsp&nbsp&nbsp&nbsp</span><br />

		<span>打印类型&nbsp&nbsp&nbsp&nbsp</span>
		<input type="text" id="printType2" />

		<div style="margin-top: -20px; float: right; margin-right: 83px">
			<button id="search" class="btn btn-info">查询</button>
			<button id="exportData" class="btn btn-info" onclick="exportData()">导出数据</button>
		</div>
	</div>
	<div class="module-head">
		<div style="margin-top: -20px; float: right; margin-right: 0px">
			<shiro:hasPermission name="print:resend">
				<button class="btn btn-danger" id="printResend"
					style="display: none">重发</button>
			</shiro:hasPermission>
			<shiro:hasPermission name="print:detail">
				<button class="btn btn-info" id="printDetail" style="display: none">详情</button>
			</shiro:hasPermission>
			<shiro:hasPermission name="print:remedy">
				<button id="addPrintRecord" class="btn btn-info" style=""
					onclick="addPrintRecord()">补录记录</button>
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
			class="printTable table table-bordered table-striped table-message display">
		</table>
	</div>
</div>