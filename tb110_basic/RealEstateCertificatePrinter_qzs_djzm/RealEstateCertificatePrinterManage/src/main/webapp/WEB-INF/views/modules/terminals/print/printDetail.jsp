<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ taglib prefix="shiro" uri="/WEB-INF/tlds/shiro.tld"%>
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
				<label class="control-label" for="bizNumber">业务流水号</label>
				<div class="controls">
					<input type="text" id="bizNumber" name="bizNumber" class="span8" disabled="disabled">
				</div>
			</div>
			<div class="control-group">
				<label class="control-label" for="certNumber">不动产权证号</label>
				<div class="controls">
					<input type="text" id="certNumber" name="certNumber" class="span8" disabled="disabled">
				</div>
			</div>
			<div class="control-group">
				<label class="control-label" for="seqNumber">印刷序列号</label>
				<div class="controls">
					<input type="text" id="seqNumber" name="seqNumber" class="span8">
					<button id ="updateSeq" class="btn-info" type=button>修改印刷序列号</button>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label" for="printTime">打印时间</label>
				<div class="controls">
					<input type="text" id="printTime" name="printTime" class="span8" disabled="disabled">
				</div>
			</div>
			<div class="control-group">
				<label class="control-label" for="certScan">证明扫描件</label>
				<div class="controls">
					<img src="#" id="certScan" name="certScan">
				</div>
			</div>
		</form>
	</div>
</div>