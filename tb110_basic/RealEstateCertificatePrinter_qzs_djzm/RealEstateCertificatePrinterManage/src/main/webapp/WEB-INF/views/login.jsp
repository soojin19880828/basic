<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="/WEB-INF/views/include/taglib.jsp" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>不动产档案查询系统-终端管理平台</title>
<link type="text/css"
	href="${ctxResources}/bootstrap/css/bootstrap.min.css" rel="stylesheet">
<link type="text/css"
	href="${ctxResources}/bootstrap/css/bootstrap-responsive.min.css"
	rel="stylesheet">
<link type="text/css" href="${ctxResources}/css/theme.css"
	rel="stylesheet">
<link type="text/css"
	href="${ctxResources}/images/icons/css/font-awesome.css"
	rel="stylesheet">
<link type="text/css"
	href='http://fonts.googleapis.com/css?family=Open+Sans:400italic,600italic,400,600'
	rel='stylesheet'>
</head>
<body>
	<div class="navbar navbar-fixed-top">
		<div class="navbar-inner">
			<div class="container">
				<a class="btn btn-navbar" data-toggle="collapse"
					data-target=".navbar-inverse-collapse"> <i
					class="icon-reorder shaded"></i>
				</a> <a class="brand" href="index.html">不动产档案查询系统-终端管理平台 </a>
			</div>
		</div>
	</div>
	<div class="wrapper">
		<div class="container">
			<div class="row">
				<div class="module module-login span4 offset4">
					<form class="login-form" action="${ctx}/login"method="post" >
						<div class="module-head">
							<h3>请先登录……</h3>
						</div>
						<c:choose>
							<c:when test="${errorMsg != null}">
								<div class="alert alert-error">
									<button type="button" class="close" data-dismiss="alert">×</button>
									<strong>错误!</strong>${errorMsg }
								</div>
							</c:when>
						</c:choose>
						<div class="module-body">
							<div class="control-group">
								<div class="controls row-fluid">
									<input class="span12" type="text" name="loginUsername"
										id="loginUsername" placeholder="请输入用户登录账号……">
								</div>
							</div>
							<div class="control-group">
								<div class="controls row-fluid">
									<input class="span12" type="password" name="loginPassword"
										id="loginPassword" placeholder="请输入用户登录密码……">
								</div>
							</div>
						</div>
						<div class="module-foot">
							<div class="control-group">
								<div class="controls clearfix">
									<button type="submit" class="btn btn-primary pull-right"
										id="loginBtn">登录</button>
								</div>
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
	<div class="footer">
		<div class="container">
			<b class="copyright">&copy; 2017 天恒智能 - thfdcsoft.com </b> All rights
			reserved.
		</div>
	</div>
	<script src="${ctxResources}/scripts/jquery-1.9.1.min.js"
		type="text/javascript"></script>
	<script src="${ctxResources}/scripts/jquery-ui-1.10.1.custom.min.js"
		type="text/javascript"></script>
	<script src="${ctxResources}/scripts/jquery.validate.min.js"
		type="text/javascript"></script>
	<script src="${ctxResources}/bootstrap/js/bootstrap.min.js"
		type="text/javascript"></script>
	<script src="${ctxResources}/scripts/sha256.jquery.js"
		type="text/javascript"></script>
	<script src="${ctxResources}/pages/login.js" type="text/javascript"></script>

	<script type="text/javascript">
		$(document).ready(function() {
			Login.init();
		});
	</script>
</body>
</html>
