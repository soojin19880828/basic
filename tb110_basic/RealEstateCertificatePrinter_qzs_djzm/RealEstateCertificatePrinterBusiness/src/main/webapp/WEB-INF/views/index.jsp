<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>不动产等级证明打印系统</title>
<jsp:include page="/WEB-INF/views/include/taglib.jsp" />
<link rel="stylesheet" type="text/css"
	href="${ctxResources}/css/baseStyle2.css" />
<style type="text/css">
.nav {
	width: 1500px;
	margin: 0 auto;
}

.nav-title {
	height: 245px;
}

.nav-title span {
	color: white;
	font-family: 'Microsoft YaHei';
	font-weight: bold;
}

.nav-title img {
	padding: 125px 0 0 13px;
}

.nav-body {
	width: 100%;
	height: 100%;
	overflow: auto;
}

.nav-body li {
	list-style-type: none;
	float: left;
	position: relative;
	margin-top: 30px;
}

.nav-body img {
	display: block;
	width: 260px;
	height: 260px;
	margin: auto 15px;
}

.nav-body p {
	font-size: 30px;
	color: #FFFFFF;
	position: absolute;
	top: 50%;
	left: 30%;
}
</style>
</head>
<body onmousedown="return false">
	<div>
		<div id="goAdmin">
			<button onclick="admin()" style="position: fixed;opacity: 0.01;width: 80px; height: 80px;"></button>
		</div>
		<div class="nav">
			<%--头部--%>
			<div class="nav-title">
				<div style="padding: 100px 15px">
					<span style="font-size: 50px; letter-spacing: 6px">不动产自助服务终端</span><br />
					<span style="font-size: 19px; letter-spacing: 0px">REAL
						ESTATE REGISTRATION SYSTEM SELF SERVICE</span>
				</div>
			</div>
			<div class="nav-body">
				<li style="/*height:260px;width:680px;*/overflow:hidden;border-radius: 13px;margin-left: 240px;"><img src="${ctxResources}/images/rq.png"
					style="height: 550px">
					<p id="time" style="top: -10%; left: 15%; font-size: 55px"></p>
					<p id="year" style="top: 10%; left: 15%; font-size: 25px"></p>
					<p id="sunday"
						style="top: 16%; left: 15%; font-size: 34px; letter-spacing: 5px"></p>
				</li>
				<li onclick="search('qzs')" style="height:260px;width:550px;overflow:hidden;border-radius: 13px;margin-left: 15px;background: mediumpurple;"><img
						style="filter:progid:DXImageTransform.Microsoft.Alpha(opacity=100);opacity:1;display:block; position:relative; left:50px; top:90px;width: 90px;height: 90px"
					src="${ctxResources}/images/cximg.png" alt="">
					<p style="font-size: 45px;margin-top: -25px;margin-left: 23px">权证书打印</p></li>
				<li onclick="search('djzm')" style="height:260px;width:550px;overflow:hidden;border-radius: 13px;margin-left: 15px;background: chartreuse"><img
						style="filter:progid:DXImageTransform.Microsoft.Alpha(opacity=100);opacity:1;display:block; position:relative; left:50px; top:90px;width: 90px;height: 90px"
					src="${ctxResources}/images/cximg.png" alt="">
						<p style="font-size: 45px;margin-top: -25px;margin-left: 23px">登记证明打印</p></li>

				<%--<li onclick="search('jdcx')"><img
					src="${ctxResources}/images/jdcx.png" alt="">
&lt;%&ndash;					<p>进度查询</p></li>&ndash;%&gt;
					<p></p></li>
				<li onclick="help('bszn')"><img
					src="${ctxResources}/images/bszn.png" alt="">
&lt;%&ndash;					<p>办事指南</p></li>&ndash;%&gt;
					<p></p></li>
				<li onclick="help('flfg')"><img
					src="${ctxResources}/images/flfg.png" alt="">
&lt;%&ndash;					<p>法律法规</p></li>&ndash;%&gt;
					<p></p></li>
				<li onclick="help('sfbz')"><img
					src="${ctxResources}/images/sfbz.png" alt="">
&lt;%&ndash;					<p>收费标准</p></li>&ndash;%&gt;
					<p></p></li>
				<li onclick="search('qtcx')"><img
						src="${ctxResources}/images/qtcx.png" style="width: 550px" alt="">
&lt;%&ndash;						<p style="left: 37%">其他查询</p>&ndash;%&gt;
						<p style="left: 37%"></p>
				</li>--%>
			</div>
		</div>
	</div>
	<script src="${ctxResources}/scripts/jquery.min.js"></script>
	<script>
		window.onload = function() {
			//定时器每秒调用一次fnDate()
			setInterval(function() {
				fnDate();
			}, 1000);
		}

		//js 获取当前时间
		function fnDate() {
			var oDiv = document.getElementById("time");//时间
			var sunday = document.getElementById("sunday");//星期
			var years = document.getElementById("year");//年月日

			var date = new Date();
			var year = date.getFullYear();//当前年份
			var month = date.getMonth();//当前月份
			var data = date.getDate();//天
			var hours = date.getHours();//小时
			var minute = date.getMinutes();//分
			var time = year + "年" + fnW((month + 1)) + "月" + fnW(data) + "日";
			console.log(time);
			years.innerHTML = time;
			time = fnW(hours) + ":" + fnW(minute);
			console.log(time);
			oDiv.innerHTML = time;
			var a = new Array("日", "一", "二", "三", "四", "五", "六");
			var week = new Date().getDay();
			var str = "星期" + a[week];
			console.log(str)
			sunday.innerHTML = str;

		}
		//补位 当某个字段不是两位数时补0
		function fnW(str) {
			var num;
			str >= 10 ? num = str : num = "0" + str;
			return num;
		}

		var adminNum = 0;
		/**
		 * 进管理员页面 
		 * 按钮位置，左上角50*50
		 */
		function admin() {
			if (adminNum > 2) {
				adminNum = 0;
				clientScript.admin();
			} else {
				adminNum++;
			}
		}
		/**
		 * 跳转查询功能
		 */
		function search(searchType) {
			clientScript.search(searchType);
		}
		
		/**
		 * 跳转附加功能
		 */
		function help(searchType) {
			clientScript.help(searchType);
		}
	</script>
</body>
</html>