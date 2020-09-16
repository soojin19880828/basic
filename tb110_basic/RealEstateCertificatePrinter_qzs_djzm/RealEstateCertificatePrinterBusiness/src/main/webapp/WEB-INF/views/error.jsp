<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>不动产等级证明打印系统</title>
    <jsp:include page="/WEB-INF/views/include/taglib.jsp"/>
    <link rel="stylesheet" type="text/css"
          href="${ctxResources}/scripts/bootstrap.min.css"/>
    <style type="text/css">
        * {
            padding: 0;
            margin: 0;
        }

        .nav {
            width: 100%;
            background: url('${ctxResources}/images/djzm_img/bg_02.jpg');
        }

        .nav-center {
            width: 1182px;
            height: 957px;
            margin: 0 auto;
        }

        .cert-list {
            width: 1182px;
            height: 714px;
            overflow: auto;
            background: url('${ctxResources}/images/djzm_img/bg-center.png') no-repeat;
        }

        .cert-list li {
            height: 92px;
            width: 950px;
            background: url("${ctxResources}/images/djzm_img/list-tag.png") no-repeat;
            margin-top: 15px;
            margin-left: 125px;
            list-style: none;
            line-height: 88px;
            padding-left: 15px;
            font-size: 32px;
            font-weight: bold;
        }
        .cert-list li span{
            padding-left: 20px;
        }


        .rightImg {
            float: right;
            margin-top: 33px;
            margin-right: 50px;
        }

        .cert-title {
            position: relative;
            width: 100%;
            height: 104px;

        }
        .cert-title img{
            position: absolute;
            left: 6%;
            top: 63%;
        }

        .cert-title p {
            font-size: 36px;
            font-weight: bold;
            text-align: center;
            line-height: 140px;
            color: #FFFFFF;
        }

        .cert-content {
            height: 570px;
            overflow-y: auto;
        }

        .content{
            padding:10px 50px;
            font-size: 26px;
        }

        .cert-content::-webkit-scrollbar {
            display: none; /* Chrome Safari */
        }

        .cert-printing {
            display: none;
            position: relative;
            text-align: center;
            padding-top: 100px;
            height: 600px;

        }

        .cert-printing h3 {
            font-size: 30px;
            font-weight: bold;
            text-align: center;
            line-height: 90px;
        }

        .cert-list-no {
            font-size: 60px;
            font-weight: bold;
            text-align: center;
            line-height: 500px;
        }

        .cert-operation {
            min-height: 150px;

        }

        .cert-operation ul {
            float: right;
            position: relative;
            padding-top: 40px;
        }

        .cert-operation img {
            width: 180px;
            height: 70px;
        }

        .cert-operation p {
            position: absolute;
            top: 53%;
            left: 21%;
            color: #FFFFFF;
            font-size: 26px;
        }
    </style>
</head>
<body onmousedown="return false">
<div class="nav">
    <div class="nav-center">

        <div class="cert-list">
            <%--标题--%>
            <div class="cert-title">
                <p>查询结果</p>
            </div>
            <div class="cert-list-no">
                <p>${errorMessage}</p>
            </div>
        </div>

    </div>
</div>


</body>
<script src="${ctxResources}/scripts/jquery.min.js"></script>
<script src="${ctxResources}/scripts/bootstrap.min.js"></script>

</html>