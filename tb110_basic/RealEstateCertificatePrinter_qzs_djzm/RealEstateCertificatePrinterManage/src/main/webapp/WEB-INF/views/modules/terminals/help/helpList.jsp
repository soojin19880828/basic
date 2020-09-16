<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="/WEB-INF/tlds/shiro.tld"%>
<!DOCTYPE html>
<div class="datatables" style="display: block">
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
		
		<div class="module-head">
		<div>
		<span style="font-size: 30px;color: gray;">办事指南</span>
		</div>
			<div style="margin-top: -25px; float: right; margin-right: 0px">
			
				<shiro:hasPermission name="help:delete">
					<button class="btn btn-danger" id="deletebszn"
						style="display: none">删除</button>
				</shiro:hasPermission>
				<shiro:hasPermission name="help:change">
					<button class="btn btn-warning" id="changebszn"
						style="display: none">修改</button>
				</shiro:hasPermission>
				<shiro:hasPermission name="help:increase">
					<button id="increasebszn" class="btn btn-info" style="" >新增</button>
				</shiro:hasPermission>
			</div>
		</div>
		<table style="width: 100%;"
			class="bsznTable table table-bordered table-striped table-message display">
		</table>
		
		<!-- <p></p> -->
		<span>&nbsp&nbsp&nbsp&nbsp</span>
		<span>&nbsp&nbsp&nbsp&nbsp</span>
		<div class="module-head">
		<div>
		<span style="font-size: 30px;color: gray;">法律法规</span>
		</div>
			<div style="margin-top: -25px; float: right; margin-right: 0px">
			
				<shiro:hasPermission name="help:delete">
					<button class="btn btn-danger" id="deleteflfg"
						style="display: none">删除</button>
				</shiro:hasPermission>
				<shiro:hasPermission name="help:change">
					<button class="btn btn-warning" id="changeflfg"
						style="display: none">修改</button>
				</shiro:hasPermission>
				<shiro:hasPermission name="help:increase">
					<button id="increaseflfg" class="btn btn-info" style="" >新增</button>
				</shiro:hasPermission>
			</div>
		</div>
		<table style="width: 100%;"
			class="flfgTable table table-bordered table-striped table-message display">
		</table>
		
		<!-- <p></p> -->
		<span>&nbsp&nbsp&nbsp&nbsp</span>
		<span>&nbsp&nbsp&nbsp&nbsp</span>
		<div class="module-head">
		<div>
		<span style="font-size: 30px;color: gray;">收费标准</span>
		</div>
			<div style="margin-top: -25px; float: right; margin-right: 0px">
			
				<shiro:hasPermission name="help:delete">
					<button class="btn btn-danger" id=deletesfbz
						style="display: none">删除</button>
				</shiro:hasPermission>
				<shiro:hasPermission name="help:change">
					<button class="btn btn-warning" id="changesfbz"
						style="display: none">修改</button>
				</shiro:hasPermission>
				<shiro:hasPermission name="help:increase">
					<button id="increasesfbz" class="btn btn-info" style="" >新增</button>
				</shiro:hasPermission>
			</div>
		</div>
		<table style="width: 100%;"
			class="sfbzTable table table-bordered table-striped table-message display">
		</table>
	</div>
</div>