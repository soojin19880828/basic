<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>不动产权证书打印系统</title>
    <jsp:include page="/WEB-INF/views/include/taglib.jsp"/>
    <link rel="stylesheet" type="text/css"
          href="${ctxResources}/css/baseStyle.css"/>
</head>
<body  onmousedown="return false" onselectstart="return false" oncopy="return false;" oncut="return false;"
      oncontextmenu="window.event.returnValue=false">
<input id="pageFlag" value="home" style="display: none">
<div id="home">
    <div id="home-attention" class="attention">
        <div class="helpTitle">
            <p>
                <span style="font-size: 45px;">${title}</span>
            </p>
        </div>
        <div class="helpContent">
        
        <c:forEach items="${helpList}" var="help"> 
        	<p>
                <span style="font-size: 20px;">${help.helpId}</span>
                <span>、</span>
                <span style="font-size: 20px;">${help.helpContent}</span>
            </p>
        </c:forEach>
        </div>
    </div>
</div>

<div id="div_cx3">
    <button class="btn_fhsy" onclick="return_home()">返回首页</button>
</div>

<script src="${ctxResources}/scripts/jquery.min.js"></script>
<script src="${ctxResources}/scripts/views/index.js"></script>
<script src="${ctxResources}/scripts/views/forbidBackSpace.js"></script>
</body>
</html>
