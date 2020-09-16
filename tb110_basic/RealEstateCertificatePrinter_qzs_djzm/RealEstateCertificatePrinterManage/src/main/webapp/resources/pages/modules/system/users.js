var UserForm = function() {

	var handleValidation = function() {
		$('form.userEdit').validate({
			errorElement : 'span',
			errorClass : 'help-inline',
			focusInvalid : false,
			ignore : ":disabled",
			rules : {
				userName : {
					required : true
				},
				loginUsername : {
					required : true,
					username : true,
					remote : {
						type : "POST",
						url : ctx + "/users/checkUsername",
						async : false,
						dataType : "json"
					}
				},
				loginPassword : {
					required : true,
					password : true
				},
				repeatPassword : {
					required : true,
					equalTo : "#loginPassword"
				},
				userMobile : {
					required : true,
					mobile : true
				},
				userIdnumber : {
					required : true,
					idnumber : true
				},
				roleId : {
					required : true
				}
			},
			messages : {
				userName : {
					required : "用户姓名不能为空."
				},
				loginUsername : {
					required : "登录账号不能为空.",
					username : "请输入有效的登录账号（5-16位数字或字母）.",
					remote : "登录账号已被占用，请重新输入."
				},
				loginPassword : {
					required : "登录密码不能为空.",
					password : "请输入有效的登录密码（5-16位数字、字母或特殊字符【!@#$%^&*.】）."
				},
				repeatPassword : {
					required : "重复密码不能为空.",
					equalTo : "与登录密码不一致，请重新输入."
				},
				userMobile : {
					required : "手机号不能为空.",
					mobile : "请输入正确的手机号."
				},
				userIdnumber : {
					required : "身份证号不能为空.",
					idnumber : "请输入正确的身份证号."
				},
				roleId : {
					required : "用户角色不能为空."
				}
			}
		});
	}

	return {
		init : function() {
			handleValidation();
			buildRoleList();
		}
	};

}();

var editTag;
$('#userCreate').click(function() {
	editTag = 'create';
	$('h3.userEdit').text("新建用户");
	$('.datatables').hide();
	$('.editform').show();
	$('#resetUser').show();
	$('#nextUser').hide();
	$('#changeUsername').hide();
	$('#changePassword').hide();
	$("#loginUsername").removeAttr("disabled");
	$('#loginPassword').removeAttr("disabled");
	$('#repeatPassword').removeAttr("disabled");
	$('.alert').hide();
	$('.alert > i').empty();
});

$("#submitUser").click(function() {
	if ($("form.userEdit").valid()) {
		loginPassword = $('#loginPassword').val();
		var sha256Password = $.sha256($('#loginPassword').val());
		$.ajax({
			type : "POST",
			url : ctx + "/users/submitUser",
			data : {
				userId : $("#userId").val(),
				userName : $("#userName").val(),
				loginUsername : $("#loginUsername").val(),
				loginPassword : sha256Password.toUpperCase(),
				userMobile : $("#userMobile").val(),
				userIdnumber : $("#userIdnumber").val(),
				roleId : $("#roleId").val(),
			},
			dataType : "json",
			success : function(data) {
				if (data.result) {
					loginUsername = data.respObj.loginUsername;
					if ('create' == editTag) {
						$('#nextUser').show();
					}
					$("#userId").val(data.respObj.userId);
					$('#resetUser').hide();
					$('#changeUsername').show();
					$('#changePassword').show();
					$("#loginUsername").attr("disabled", "disabled");
					$('#loginPassword').attr("disabled", "disabled");
					$('#repeatPassword').attr("disabled", "disabled");
					$('.alert').hide();
					$('.alert > i').empty();
					$('#alertSuccess > i').append(data.respMsg + '！');
					$('#alertSuccess').show();
				} else {
					$('.alert').hide();
					$('.alert > i').empty();
					$('#alertError > i').append(data.respMsg + '！');
					$('#alertError').show();
				}
			}
		});
	}
});

$('#resetUser').click(function() {
	resetUserForm();
});

$('#nextUser').click(function() {
	resetUserForm();
	$('#resetUser').show();
	$('#nextUser').hide();
	$('#changeUsername').hide();
	$('#changePassword').hide();
	$("#loginUsername").removeAttr("disabled");
	$('#loginPassword').removeAttr("disabled");
	$('#repeatPassword').removeAttr("disabled");
});

var loginUsername;
$('#changeUsername').click(function() {
	if ($("#loginUsername").prop("disabled") == true) {
		$("#loginUsername").removeAttr("disabled");
	} else {
		$("#loginUsername").val(loginUsername);
		$("#loginUsername").attr("disabled", "disabled");
		$("#loginUsername").next().remove();
	}
});

var loginPassword;
$('#changePassword').click(function() {
	if ($("#loginPassword").prop("disabled") == true) {
		$('#loginPassword').removeAttr("disabled");
		$('#repeatPassword').removeAttr("disabled");
	} else {
		$('#loginPassword').val(loginPassword);
		$('#repeatPassword').val(loginPassword);
		$('#loginPassword').attr("disabled", "disabled");
		$('#repeatPassword').attr("disabled", "disabled");
		$('#loginPassword').next().remove();
		$('#repeatPassword').next().remove();
	}
});

var resetUserForm = function() {
	$("#userId").val('');
	$("#userName").val('');
	$("#loginUsername").val('');
	$('#loginPassword').val('');
	$('#repeatPassword').val('');
	$("#userMobile").val('');
	$("#userIdnumber").val('');
	$("#roleId").val('');
	$('.controls > span.help-inline').remove();
}

var UsersTable = function() {

	var handleTable = function() {
		$('.usersTable').DataTable(
				{
					"select" : true,
					"lengthMenu" : [ 10, 20, 30 ],
					"pageLength" : 10,
					"ajax" : {
						"url" : ctx + "/users/query",
						"dataSrc" : function(json) {
							return eval('(' + json.data + ')');
						}
					},
					"columnDefs" : [
							{
								"targets" : [ 0 ]
							},
							{
								"targets" : [ 1 ]
							},
							{
								"targets" : [ 2 ]
							},
							{
								"targets" : [ 3 ]
							},
							{
								"targets" : [ 4 ]
							},
							{
								"targets" : [ 5 ]
							},
							{
								"targets" : [ 6 ]
							},
							{
								"targets" : [ 7 ],
								"createdCell" : function(td, cellData, rowData,
										row, col) {
									$(td).addClass("cell-status")
								}
							} ],
					"columns" : [ {
						"title" : "用户ID",
						"data" : "userId",
						"visible" : false
					}, {
						"title" : "角色ID",
						"data" : "roleId",
						"visible" : false
					}, {
						"title" : "用户姓名",
						"data" : "userName"
					}, {
						"title" : "用户角色",
						"data" : "roleName"
					}, {
						"title" : "用户账号",
						"data" : "loginUsername"
					}, {
						"title" : "手机号",
						"data" : "userMobile"
					}, {
						"title" : "身份证号",
						"data" : "userIdnumber"
					}, {
						"title" : "用户状态",
						"data" : "userStatus",
						"render" : function(data, type, full, meta) {
							if ("00" == data) {
								return '<b class="normal">正常</b>';
							}
							if ("01" == data) {
								return '<b class="danger">锁定</b>';
							}
						}
					} ],
					"fnInitComplete" : function() {
						$('.dataTables_paginate').addClass(
								"btn-group datatable-pagination");
						$('.dataTables_paginate > a').wrapInner('<span />');
						$('.dataTables_paginate > a:first-child').append(
								'<i class="icon-chevron-left shaded"></i>');
						$('.dataTables_paginate > a:last-child').append(
								'<i class="icon-chevron-right shaded"></i>');
					},
					"drawCallback" : function() {
						$('.dataTables_paginate').addClass(
								"btn-group datatable-pagination");
						$('.dataTables_paginate > a').wrapInner('<span />');
						$('.dataTables_paginate > a:first-child').append(
								'<i class="icon-chevron-left shaded"></i>');
						$('.dataTables_paginate > a:last-child').append(
								'<i class="icon-chevron-right shaded"></i>');
					}
				});
	}

	var selectBtn = function() {
		var row;
		var table = $('.usersTable').DataTable();
		$('.usersTable tbody').on(
				'click',
				'tr',
				function() {
					if ($(this).hasClass('selected')) {
						$(this).removeClass('selected');
						row = null;
						$('#userUpdate').hide();
						$('#userCancle').hide();
						$('#userLock').hide();
						$('#userUnlock').hide();
					} else {
						table.$('tr.selected').removeClass('selected');
						$(this).addClass('selected');
						row = table.row(this).data();
						if ("00000000000000000001" == row.userId
								|| "00000000000000000002" == row.userId) {
							// 默认用户禁止编辑/删除
							$('#userUpdate').hide();
							$('#userCancle').hide();
							$('#userLock').hide();
							$('#userUnlock').hide();
						} else {
							$('#userUpdate').show();
							$('#userCancle').show();
							if ("00" == row.userStatus) {
								$('#userLock').show();
								$('#userUnlock').hide();
							}
							if ("01" == row.userStatus) {
								$('#userLock').hide();
								$('#userUnlock').show();
							}
						}
					}
				});
		$('#userUpdate').click(function() {
			editTag = 'update';
			loginUsername = row.loginUsername;
			loginPassword = '';
			$('h3.userEdit').text("编辑用户");
			$("#userId").val(row.userId);
			$("#userName").val(row.userName);
			$("#loginUsername").val(row.loginUsername);
			$("#loginUsername").attr("disabled", "disabled");
			$('#loginPassword').attr("disabled", "disabled");
			$('#repeatPassword').attr("disabled", "disabled");
			$("#userMobile").val(row.userMobile);
			$("#userIdnumber").val(row.userIdnumber);
			$("#roleId").val(row.roleId);
			$('.datatables').hide();
			$('.editform').show();
			$('#resetUser').hide();
			$('#nextUser').hide();
			$('#changeUsername').show();
			$('#changePassword').show();
			$('.alert').hide();
			$('.alert > i').empty();
		});
		$('#userCancle').click(function() {
			$.ajax({
				type : "POST",
				url : ctx + "/users/cancleUser",
				data : {
					userId : row.userId,
				},
				dataType : "json",
				success : function(data) {
					if (data.result) {
						table.ajax.reload().draw();
						$('#userUpdate').hide();
						$('#userCancle').hide();
						$('#userLock').hide();
						$('#userUnlock').hide();
						$('.alert').hide();
						$('.alert > i').empty();
						$('#operSuccess > i').append(data.respMsg + '！');
						$('#operSuccess').show();
					} else {
						$('.alert').hide();
						$('.alert > i').empty();
						$('#operError > i').append(data.respMsg + '！');
						$('#operError').show();
					}
				}
			});
		});
		$('#userLock').click(function() {
			$.ajax({
				type : "POST",
				url : ctx + "/users/lockUser",
				data : {
					userId : row.userId,
				},
				dataType : "json",
				success : function(data) {
					if (data.result) {
						table.ajax.reload().draw();
						$('#userUpdate').hide();
						$('#userCancle').hide();
						$('#userLock').hide();
						$('#userUnlock').hide();
						$('.alert').hide();
						$('.alert > i').empty();
						$('#operSuccess > i').append(data.respMsg + '！');
						$('#operSuccess').show();
					} else {
						$('.alert').hide();
						$('.alert > i').empty();
						$('#operError > i').append(data.respMsg + '！');
						$('#operError').show();
					}
				}
			});
		});
		$('#userUnlock').click(function() {
			$.ajax({
				type : "POST",
				url : ctx + "/users/unlockUser",
				data : {
					userId : row.userId,
				},
				dataType : "json",
				success : function(data) {
					if (data.result) {
						table.ajax.reload().draw();
						$('#userUpdate').hide();
						$('#userCancle').hide();
						$('#userLock').hide();
						$('#userUnlock').hide();
						$('.alert').hide();
						$('.alert > i').empty();
						$('#operSuccess > i').append(data.respMsg + '！');
						$('#operSuccess').show();
					} else {
						$('.alert').hide();
						$('.alert > i').empty();
						$('#operError > i').append(data.respMsg + '！');
						$('#operError').show();
					}
				}
			});
		});
		$('#backTable').click(function() {
			$('.editform').hide();
			$('.datatables').show();
			resetUserForm();
			table.ajax.reload().draw();
			$('#userUpdate').hide();
			$('#userCancle').hide();
			$('#userLock').hide();
			$('#userUnlock').hide();
		});
	}

	return {
		init : function() {
			handleTable();
			selectBtn();
		}
	};
}();

var buildRoleList = function() {
	$.ajax({
		type : "POST",
		url : ctx + "/users/getRoleList",
		data : {},
		dataType : "json",
		success : function(data) {
			if (data.result) {
				var roles = data.respObj;
				for (var i = 0; i < roles.length; i++) {
					var role = roles[i];
					$('#roleId').append(
							'<option value="' + role.roleId + '">'
									+ role.roleName + '</option>');
				}
			} else {
				$('.alert').hide();
				$('.alert > i').empty();
				$('#alertError > i').append('获取角色列表失败，这将导致您无法新建用户！');
				$('#alertError > i').show();
			}
		}
	});
}