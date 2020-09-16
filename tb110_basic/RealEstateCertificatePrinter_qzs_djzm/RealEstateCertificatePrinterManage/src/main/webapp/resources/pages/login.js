var Login = function() {

	var handleValidation = function() {
		$('.login-form').validate({
			errorElement : 'span',
			errorClass : 'help-inline',
			focusInvalid : false,
			rules : {
				loginUsername : {
					required : true
				},
				loginPassword : {
					required : true
				}
			},
			messages : {
				loginUsername : {
					required : "登录账号不能为空."
				},
				loginPassword : {
					required : "登陆密码不能为空."
				}
			},
			submitHandler:function(form){
				var sha256Password = $.sha256($('#loginPassword').val());
				$('#loginPassword').val(sha256Password.toUpperCase()); 
	            form.submit();
	        } 
		});
	}

	return {
		init : function() {
			return handleValidation();
		}
	};
}();