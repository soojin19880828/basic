<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<div class="addDetailForm" style="display: none">
	<div class="module-head">
		<h3>补录记录详情</h3>
		<div style="float: right; margin-top: -24px;">
			<button class="btn btn-warning" id="back">返回</button>
			<button class="btn btn-success" id="resetPrint">重置</button>
			<button class="btn btn-warning" id="submit">提交</button>
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

		<form class="form-horizontal row-fluid printEdit">
			<div class="control-group">
				<label class="control-label" for="userFullname">领证人姓名</label>
				<div class="controls">
					<input type="text" id="userFullname" name="userFullname" class="span6">
				</div>
			</div>
			<div class="control-group">
				<label class="control-label" for="userIdNumber">领证人身份证号</label>
				<div class="controls">
					<input type="text" id="userIdNumber" name="userIdNumber" class="span6">
				</div>
			</div>
			<div class="control-group">
				<label class="control-label" for="certNumber">不动产权证号</label>
				<div class="controls">
					<input type="text" id="certNumber0" name="certNumber0" class="span1">
					<span>(</span>
					<input type="text" id="certNumber1" name="certNumber1" class="span2" list="yearList">
		            <datalist id="yearList">
		            </datalist>
					<span>)</span>
					<input type="text" id="certNumber4" name="certNumber4" class="span2">
					<span>不动产权第</span>
					<input type="text" id="certNumber3" name="certNumber3" class="span2">
					<span>号</span>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label" for="bizNumber">不动产单元号</label>
				<div class="controls">
					<input type="text" id="bizNumber1" name="bizNumber1" class="span6">
				</div>
			</div>
			<div class="control-group">
				<label class="control-label" for="ocr">证书编号</label>
				<div class="controls">
					<input type="text" id="ocr" name="ocr" class="span6">
				</div>
			</div>
			<div id="localImag">
				<img id="preview" width=-1 height=-1 style="diplay: none" />
			</div>
		</form>

	</div>
</div>