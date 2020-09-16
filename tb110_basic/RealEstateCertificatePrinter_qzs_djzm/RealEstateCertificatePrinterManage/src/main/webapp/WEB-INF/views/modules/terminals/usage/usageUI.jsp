<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<jsp:include page="/WEB-INF/views/include/taglib.jsp" />
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
<link type="text/css" href="${ctxResources}/css/zTree/zTreeStyle.css"
	rel="stylesheet">
<link type="text/css"
	href='http://fonts.googleapis.com/css?family=Open+Sans:400italic,600italic,400,600'
	rel='stylesheet'>
</head>
<body>
	<jsp:include page="/WEB-INF/views/index/header.jsp"></jsp:include>

	<div class="wrapper">
		<div class="container">
			<div class="row">
				<div class="span3">
					<div class="sidebar" id="menubar"></div>
				</div>
				<div class="span9">
					<div class="content">
						<div class="module message">
							<jsp:include
								page="/WEB-INF/views/modules/terminals/usage/usageList.jsp"></jsp:include>
							<jsp:include
								page="/WEB-INF/views/modules/terminals/usage/usageDetail.jsp"></jsp:include>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<jsp:include page="/WEB-INF/views/index/footer.jsp"></jsp:include>

	<script src="${ctxResources}/scripts/jquery-1.9.1.min.js"></script>
	<script src="${ctxResources}/scripts/jquery-ui-1.10.1.custom.min.js"></script>
	<script src="${ctxResources}/scripts/jquery.validate.min.js"
		type="text/javascript"></script>
	<script src="${ctxResources}/pages/jquery.validate.methods.js"
		type="text/javascript"></script>
	<script src="${ctxResources}/bootstrap/js/bootstrap.min.js"></script>
	<script src="${ctxResources}/scripts/datatables/jquery.dataTables.js"></script>
	<script src="${ctxResources}/scripts/zTree/jquery.ztree.core.js"></script>
	<script src="${ctxResources}/scripts/zTree/jquery.ztree.excheck.js"></script>

	<script src="${ctxResources}/pages/menu.js" type="text/javascript"></script>
	<script src="${ctxResources}/pages/modules/terminals/usage.js"
		type="text/javascript"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			var json = '${menuJson }';
			Menu.init(json);
			UsageTable.init();
		});
	</script>
</body>
</html>