<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fns" uri="/WEB-INF/tlds/fns.tld"%>
<title>不动产权证书打印系统</title>
<c:set var="ctx" value="${pageContext.request.contextPath}"
	scope="request" />
<c:set var="ctxResources"
	value="${pageContext.request.contextPath}/resources" scope="request" />
<script>
	var ctx = '${ctx}';
	var ctxResources = '${ctxResources}';
</script>