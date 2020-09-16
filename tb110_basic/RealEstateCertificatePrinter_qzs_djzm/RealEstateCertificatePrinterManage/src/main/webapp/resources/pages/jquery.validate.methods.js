(function(factory) {
	if (typeof define === "function" && define.amd) {
		define([ "jquery", "./jquery.validate" ], factory);
	} else {
		factory(jQuery);
	}
}(function($) {
	
	$.validator.addMethod("username", function(value, element) {
		return this.optional(element) || /^[a-zA-Z0-9]{5,16}$/.test(value);
	}, "请输入有效的登录账号（5-16位数字或字母）");

	$.validator.addMethod("password", function(value, element) {
		return this.optional(element) || /^[a-zA-Z0-9|!|@|#|$|%|^|&|*|.|_]{5,16}$/.test(value);
	}, "请输入有效的登录密码（5-16位数字、字母或特殊字符【!@#$%^&*.】）");
	
	$.validator.addMethod("mobile", function(value, element) {
		return this.optional(element) || /^1[3|4|5|7|8|][0-9]{9}$/.test(value);
	}, "请输入正确的手机号");
	
	$.validator.addMethod("idnumber", function(value, element) {
		return this.optional(element) || /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/.test(value);
	}, "请输入正确的身份证号");
}));