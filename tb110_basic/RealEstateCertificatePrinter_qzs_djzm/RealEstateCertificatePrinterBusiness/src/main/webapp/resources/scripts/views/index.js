// 开始打证
var ksdz = function () {
    clientScript.forwardFace();
};

// 办事指南
var guidance = function () {
    clientScript.startCount();
    clientScript.help("bszn");
};

// 法律法规
var lawsAndRegulations = function () {
    clientScript.startCount();
//    window.location.href = ctx + "/client/lawsAndRegulations";
    clientScript.help("flfg");
};

// 收费标准
var chargesNotes = function () {
    clientScript.startCount();
    clientScript.help("sfbz");
};

// 返回首页
var return_home = function () {
    clientScript.home();
};

var btn_jiaofei = function (age, a) {

    clientScript.pay(age);
};


//支付宝或微信支付
var fangshi = function () {
    clientScript.phonepay();
};


//二维码支付
var erweima = function () {
    clientScript.erweima();
};


//付款码支付
var fukuanma = function () {
    clientScript.fukuanma();
};

/**
 * 获取页面flag
 */
var getPageFlag = function () {
    return $('#pageFlag').val();
};

/**
 * 设置页面flag
 */
var setPageFlag = function (data) {
	$('#pageFlag').val(data);
}