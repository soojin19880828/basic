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
            <%--background: url('${ctxResources}/images/djzm_img/bg_02.jpg');--%>
            background: url('${ctxResources}/images/djzm_img/djzm_home.jpg');
            /*
                        width: 1920px;
            */
            /*height: 959px;*/
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

        .leftImg {

        }

        .rightImg {
            float: right;
            margin-top: 33px;
            margin-right: 50px;
        }

        .cert-title {

            width: 100%;
            height: 104px;

        }

        .cert-title p {
            font-size: 36px;
            font-weight: bold;
            text-align: center;
            line-height: 140px;
            color: #FFFFFF;
        }

        .cert-content {
            display: none;
            height: 470px;
            overflow-y: auto;
        }
        .cert-content::-webkit-scrollbar{
            display: none; /* Chrome Safari */
        }
        .cert-printing{
            display: none;
            position: relative;
            text-align: center;
            padding-top: 100px;
            height: 600px;

        }

        .cert-printing h3{
            font-size: 30px;
            font-weight: bold;
            text-align: center;
            line-height: 90px;
        }

        .cert-list-no{
            font-size: 60px;
            font-weight: bold;
            text-align: center;
            line-height: 500px;
        }

        .cert-operation {
            position: relative;
        }

        .cert-operation img {
            display: block;
            width: 180px;
            height: 70px;
            margin: 30px auto;
        }

        .cert-operation p {
            position: absolute;
            top: 18px;
            left: 540px;
            color: #FFFFFF;
            font-size: 26px;
        }
        .cert-printing-message{
            display: none;
        }
        /*过度动画*/
        .spinner {
            /*position: absolute;
            top: 68%;
            left: 66%;
            margin: 100px auto 0;
            width: 70px;*/
            text-align: center;
        }

        .spinner > div {
            width: 18px;
            height: 18px;
            background-color: #333;

            border-radius: 100%;
            display: inline-block;
            -webkit-animation: sk-bouncedelay 1.4s infinite ease-in-out both;
            animation: sk-bouncedelay 1.4s infinite ease-in-out both;
        }

        .spinner .bounce1 {
            -webkit-animation-delay: -0.32s;
            animation-delay: -0.32s;
        }

        .spinner .bounce2 {
            -webkit-animation-delay: -0.16s;
            animation-delay: -0.16s;
        }

        @-webkit-keyframes sk-bouncedelay {
            0%, 80%, 100% { -webkit-transform: scale(0) }
            40% { -webkit-transform: scale(1.0) }
        }

        @keyframes sk-bouncedelay {
            0%, 80%, 100% {
                -webkit-transform: scale(0);
                transform: scale(0);
            } 40% {
                  -webkit-transform: scale(1.0);
                  transform: scale(1.0);
              }
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
            <%--内容--%>
            <div class="cert-content">
                <c:forEach items="${certLists}" var="cert" varStatus="id">
                    <li>
                        <label  for="${id.index}">
                            <img class="leftImg" src="${ctxResources}/images/djzm_img/list-select-kuang.png">
                        </label>
                        <input onclick="changeKuangStyle(this)" name="printChk" id="${id.index}" value="${id.index}" style="display: none"
                               type="checkbox">
<%--                        &nbsp;&nbsp;&nbsp;&nbsp;${cert.location}&nbsp;--%>
                        <c:if test="${fn:length(cert.location)>'26'}">
                            &nbsp;&nbsp;&nbsp;&nbsp;${fn:substring(cert.location,0,8)}xxxx${fn:substring(cert.location,fn:length(cert.location)-16,fn:length(cert.location))}&nbsp;
                        </c:if>
                        <c:if test="${fn:length(cert.location)<='26'}">
                            &nbsp;&nbsp;&nbsp;&nbsp;${cert.location}&nbsp;
                        </c:if>


                        <img class="rightImg" onclick="showPerview(${id.index})"
                             src="${ctxResources}/images/djzm_img/list-open.png"/>
                    </li>
                </c:forEach>
            </div>
                <%-- 按钮--%>
                <div class="cert-operation">
                    <a id="printAll" onclick="printSelected()">
                        <img src="${ctxResources}/images/djzm_img/button2.png"/>
                        <p>打印全部</p>
                    </a>

                    <a id="printSelect" onclick="printSelected()" style="display: none">
                        <img src="${ctxResources}/images/djzm_img/button2.png"/>
                        <p>打印选中</p>
                    </a>
                </div>

            <%--正在打印中--%>
            <div class="cert-printing">
                <img class="cert-printing-img" src="${ctxResources}/images/djzm_img/printing.png">
                <h3>正在准备打印</h3>
                <div class="spinner">
                    <div class="bounce1"></div>
                    <div class="bounce2"></div>
                    <div class="bounce3"></div>
                </div>
                <div class="cert-printing-message">
                    <img src="${ctxResources}/images/djzm_img/remind.png" >
                    <img src="${ctxResources}/images/djzm_img/remind-message.png" alt="">
                </div>
            </div>

            <div class="cert-list-no">
                <p>未查询到数据......</p>
            </div>
        </div>

    </div>
</div>


<c:forEach items="${certLists}" var="cert" varStatus="busId">
    <div id="a${busId.index}" class="modal fade bs-example-modal-lg" tabindex="-1"
         role="dialog"<%-- aria-labelledby="myLargeModalLabel"--%> >
        <div class="modal-dialog modal-lg" role="document">
            <div class="modal-content" style="width: 1050px;margin-left: -9%;margin-top: 5%">
                <div style="height: 710px;overflow:auto">
                    <div style="position: relative;background: url(${ctxResources}/images/djzm_img/certificate.png) no-repeat;
                            width: 837px;height: 608px;margin: 50px auto">
                        <span style="position:absolute;top: 76px;left: 448px ">${fn:substring(cert.estateInfo.certNumber,0,1)}</span>
                        <span style="position:absolute;top: 76px;left: 482px ">${fn:substring(cert.estateInfo.certNumber,2,6)}</span>
                        <span style="position:absolute;top: 76px;left: 545px ">${fn:substring(cert.estateInfo.certNumber,7,9)}</span>
                        <span style="position:absolute;top: 76px;left: 686px ">${fn:substring(cert.estateInfo.certNumber,15,22)}</span>
                        <span style="position:absolute;top: 118px;left: 560px;width: 200px;overflow: hidden;text-overflow:ellipsis;white-space: nowrap">${cert.estateInfo.busiType}</span>
                        <span style="position:absolute;top: 154px;left: 560px;width: 200px;overflow: hidden;text-overflow:ellipsis;white-space: nowrap">${cert.estateInfo.obligee}</span>
                        <span style="position:absolute;top: 188px;left: 560px;width: 200px;overflow: hidden;text-overflow:ellipsis;white-space: nowrap">${cert.estateInfo.obligor}</span>
                        <span style="position:absolute;top: 223px;left: 560px;width: 200px;overflow: hidden;text-overflow:ellipsis;white-space: nowrap">${cert.estateInfo.located}</span>
                        <span style="position:absolute;top: 258px;left: 560px;width: 200px;overflow: hidden;text-overflow:ellipsis;white-space: nowrap">${cert.estateInfo.unitNumber}</span>
                        <span style="position:absolute;top: 288px;left: 560px;width: 200px;height: 120px;overflow: hidden">${cert.estateInfo.otherCases}</span>
                        <span style="position:absolute;top: 410px;left: 560px;width: 200px;height: 100px;overflow: hidden">${cert.estateInfo.notes}</span>
                        <span style="position:absolute;top: 404px;left: 235px;">${fn:substring(cert.estateInfo.registerTime,0,4)}</span>
                        <span style="position:absolute;top: 404px;left: 285px;">${fn:substring(cert.estateInfo.registerTime,5,7)}</span>
                        <span style="position:absolute;top: 404px;left: 320px;">${fn:substring(cert.estateInfo.registerTime,8,10)}</span>
                    </div>
                </div>
            </div>
        </div>
    </div>
</c:forEach>

</body>
<script src="${ctxResources}/scripts/jquery.min.js"></script>
<script src="${ctxResources}/scripts/bootstrap.min.js"></script>


<script>
    //全部数据
    var bizs = ${bizLists};
    var selected;//选中的
    bizs = eval(bizs);
    var usageId;

    $(function () {
        if (bizs.length){
            $(".cert-list-no").hide();
            $(".cert-content").show();
            $("#printAll").show();
        }else{
            $(".cert-list-no").show();
            $(".cert-content").hide();
        }
        // $(".cert-list").show()
    })
    /**
     * 显示预览模态框
     * @param obj
     */
    function showPerview(obj) {
        $("#a" + obj).modal('show');
    }

    //数据展示
    function showDetail(bizs){
        for (var i = 0; (i < bizs.length) && (i < 10); i++) {
            var biz = bizs[i];
            var chkId = 'printChk_' + i;
            var labVal = biz.located;
            if (labVal.length > 26) {
                labVal = labVal.substring(0, 8) + "XXXX"
                    + labVal.substring(labVal.length - 16, labVal.length);
            }
            // checked 默认打勾
            var flg;
            if (biz.checkFlag) {
                flg = "checked = checked";
            } else {
                flg = "";
            }
            $('.cert-content').append(
                '<div class="print-check"><input name="printChk" id="' + chkId
                + '" value="' + i + '" type="checkbox" ' + flg
                + ' onclick="checks()"/><label for="' + chkId + '">'
                + labVal + '</label></div>');
        }
    }

    /**
     * 修改复选框样式
     * @param obj
     */
    function changeKuangStyle(obj) {
        //修改复选框样式
        let attr = $(obj).prop('checked');
        if (attr) {
            $(obj).prev('label').find('img').attr('src', '${ctxResources}/images/djzm_img/list-select-gou.png');
        } else {
            $(obj).siblings('label').find('img').attr('src', '${ctxResources}/images/djzm_img/list-select-kuang.png')
        }

        //只有存在选中，才会显示 打印选中按钮
        var haveSelect = [];
        $('input[name="printChk"]:checked').each(function () {
            haveSelect.push(bizs[$(this).val()]);
        });
        console.log('haveSelect.length->'+haveSelect.length)
        if (haveSelect.length) {
            $('#printSelect').show();
            $('#printAll').hide();
        } else {
            $('#printSelect').hide();
            $('#printAll').show();

        }
    }

    /**
     * 打印选中
     */
    function printSelected() {
        console.log("打印选中")
        selected = [];
        $('input[name="printChk"]:checked').each(function () {
            console.log(bizs[$(this).val()]);
            selected.push(bizs[$(this).val()]);
        });
        var json = JSON.stringify(selected);
        $(".cert-content").hide();
        $('.cert-operation').hide();
        $('.cert-printing').show();
        // clientScript.print(json);
        clientScript.djzmPrint(json);

    }

    /**
     * 打印全部
     */
    function printAll() {
        $(".cert-content").hide();
        $('.cert-operation').hide();
        $('.cert-printing').show();
        selected = [];
        for (var i = 0; i < bizs.length; i++) {
            selected.push(bizs[i]);
        }
        var json = JSON.stringify(selected);
        // clientScript.print(json);
        clientScript.djzmPrint(json);

      /*  complete();*/

    }

    // 开始打印，变更提示
    var doPrint = function (f, z) {
        $('div.cert-printing h3').empty();
        $('div.cert-printing h3').append('正在打印第' + f + '份第' + z + '张');
    }

    // 打印异常
    var printError = function (msg) {
        $('div.cert-printing h3').empty();
        $('div.cert-printing h3').append(msg);
        $('.operation').show();
        $('.print-continue-btn').show();
        clientScript.stopCountDown();
    }

    //回传信息
    var estateWriteBack =function (obj) {
        $('div.cert-printing h3').empty();
        $('div.cert-printing h3').append('正在回传打证信息到不动产，请耐心等待...');
        $.ajax({
            type: 'post',
            url: ctx + '/printComplete',
            data: {
                'usageId': obj
            },
            success: function (data) {
                if (data.result){
                    //修改等待状态，继续下一步
                    clientScript.changeResult();
                }else{
                    $('.operation').hide();
                    $('div.cert-printing h3').empty();
                    $('div.cert-printing h3').append('通知不动产失败，请联系工作人员检查...');
                }
            }

        });
    }

    // 打印完成
    var complete = function () {
        $('div.cert-printing h3').empty();
        $('div.cert-printing h3').append('打印完成');
        $('div.spinner').hide();
        //修改图片
        $('.cert-printing-img').attr('src','${ctxResources}/images/djzm_img/printSuccess.png')
        $('.cert-printing-message').show();
    }
    /**
     * 返回首页
     */
    function home() {
        // returnInfo('20200907172153000028');
        clientScript.home();
    }
</script>
</html>