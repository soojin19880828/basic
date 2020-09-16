<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>查询情况</title>
<jsp:include page="/WEB-INF/views/include/taglib.jsp" />
<script src="${ctxResources}/scripts/jquery.min.js"></script>
<script src="${ctxResources}/scripts/views/index.js"></script>
<script src="${ctxResources}/scripts/views/preview.js"></script>
<script src="${ctxResources}/scripts/views/alreadyPay.js"></script>
<link rel="stylesheet" type="text/css"
	href="${ctxResources}/css/baseStyle.css" />
<link rel="stylesheet" type="text/css"
	href="${ctxResources}/css/previewPage.css" />
</head>

<!-- <body onmousedown="return false" onselectstart="return false" oncopy="return false;" oncut="return false;"
      oncontextmenu="window.event.returnValue=false"> -->
<body>
	<input id="recordId" value="${recordId}" type="text"
		style="display: none">
	<div class="home">
		<div class="home_center">
			<div class="title">
				<font>查询结果</font>
			</div>
			<!-- 提示信息 -->
			<div id="promptInfo" class="transit-center">
				<font>数据查询中，请稍等...</font>
			</div>
			<!-- 查询数据结果选择按钮 -->
			<div style="position: absolute; float: none; top: -85px; left: 20px">
				<button class="button normalData_btn" onclick="normalData_btn()"
					style="display: none">正常数据</button>
				<button class="button abnormalData_btn" onclick="abnormalData_btn()"
					style="display: none">异常数据</button>
			</div>
			<!-- 查询结果列表 -->
			<div id="dataList" style="display: none">
				<div
					style="overflow-y: auto; position: absolute; top: 16%; left: 125px; width: 932px; height: 450px;">
					<table class="print_table">
						<tbody id="body">
						</tbody>
					</table>
				</div>
			</div>
			<!-- 打印按钮 -->
			<div style="position: absolute; top: 76%; left: 470px">
				<button class="button" id="print_selected_btn"
					onclick="print_selected()" style="display: none">打印选中</button>
				<button class="button" id="print_all_btn" onclick="print_all()"
					style="display: none">打印全部</button>
			</div>
			<!-- 预览 -->
			<div>
				<%--背景白色部分--%>
				<div id="print_preview_background" style="display: none;"></div>
				<%--预览部分--%>
				<div id="print_preview" style="display: none;">
					<%--证书预览--%>
					<div id="preview_first" class="preview_page_first">
						<p id="cert-province"></p>
						<p id="cert-year"></p>
						<p id="cert-city"></p>
						<p id="cert-number"></p>
						<p id="obligee"></p>
						<p id="co_ownershipCircumstance"></p>
						<p id="located"></p>
						<p id="unitNumber"></p>
						<p id="rightTypes"></p>
						<p id="rightNature"></p>
						<p id="application"></p>
						<p id="area"></p>
						<p id="serviceLife"></p>
						<p id="other"></p>
						<p id="excursus"></p>
					</div>

					<div id="preview_second" class="preview_page_second"
						style="display: none">
						<div style="width: 353px;">
							<img id="img_zongditu"
								style="width: 353px; height: auto; margin-left: 63px; margin-top: 80px;">
							<%--分布图--%>
							<img id="img_fenbutu"
								style="width: 353px; height: auto; margin-left: 63px; margin-top: 53px;">
						</div>
						<%--宗地图--%>

					</div>

					<!--左边翻页-->
					<div id="turn_left" style="cursor: pointer;">
						<img id="turn_left_img"
							src="${ctxResources}/images/qzs_img/yl_leftwuxiao.png" onclick="">
					</div>

					<!--右边翻页-->
					<div id="turn_right" style="cursor: pointer;">
						<img id="turn_right_img"
							src="${ctxResources}/images/qzs_img/yl_rightfanye.png"
							onclick="turnRight()">
					</div>

					<!-- 退出图片 -->
					<div id="close" style="cursor: pointer;">
						<button class="close_btn" onclick="preview_cancel()"></button>
					</div>
				</div>
			</div>
			<%--正在打印--%>
			<div id="printing" style="display: none">
				<div style="margin-left: 480px; margin-top: 200px">
					<img alt="" src="${ctxResources}/images/qzs_img/print.png">
				</div>
				<p
					style="margin-left: 360px; font-family: Microsoft YaHei; font-size: 35px; font-style: normal;">
					<span id="print_text">设备打印进行中，请稍等...</span>
				</p>
				<p
					style="margin-left: 330px; font-family: Microsoft YaHei; font-size: 35px; font-style: normal;">
					<span id="print_progress"></span>
				</p>
			</div>

			<%--打印完成--%>
			<div id="printComplete" style="display: none">
				<div style="margin-left: 530px; margin-top: 200px">
					<img alt="" src="${ctxResources}/images/qzs_img/printComplete.png">
				</div>
				<p
					style="margin-left: 530px; margin-top: 80px; font-family: Microsoft YaHei; font-size: 35px; font-style: normal">打印完成</p>
				<div style="height: 28px; align-items: center; margin-left: 250px;">
					<p style="display: inline;">
						<img style="margin: -5px" src="${ctxResources}/images/qzs_img/prompt.png">
					</p>
					<p
						style="font-family: Microsoft YaHei; font-size: 25px; display: inline; color: #FF8C00; font-weight: normal">
						<span>请取走您的证书并核对证书编号，若有疑问请联系工作人员</span>
					</p>
				</div>
			</div>
</body>
</html>