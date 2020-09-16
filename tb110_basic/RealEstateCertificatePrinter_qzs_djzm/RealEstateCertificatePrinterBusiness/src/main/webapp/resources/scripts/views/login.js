//登陆
var login =function(){
	var name = $("#name").val();
    var pwd = $("#password").val();
	 
		$.ajax({
				type : 'post',
				url : 'AccountPwdLogin',
				data: { 'name': name, 'password': pwd },
				success : function(data) {					
					if (data) {
						 // 进入管理员操作页面
						 clientScript.supperManage();			
					} else {						
						$("#text").attr("type","text");
						$("#name").val("");
						$("#password").val("");
					}
				
				},
				error : function(data){
					alert("err");
				}
		});	 	 
}


// 返回首页
var fanhui=function(){
	clientScript.home();
}
// 键盘修复
var keyboard = function(){
/*	clientScript.keyboardRepair();
*/}
// 焦点事件
$(document).ready(function() {
	$("#name").focus(function() {
		clientScript.handinput();
	});
	$("#password").focus(function() {
		clientScript.handinput();
	});
});

