var RoleAuth = function() {

	var setting = {
		view : {
			showIcon : false
		},
		check : {
			enable : true
		},
		data : {
			simpleData : {
				enable : true
			}
		}
	};

	var zTree;
	var buildModuleTree = function() {
		$.ajax({
			type : "POST",
			url : ctx + "/roles/getModuleTree",
			data : {
				roleId : $("#roleId").val()
			},
			dataType : "json",
			success : function(data) {
				if (data.result) {
					var zNodes = eval('(' + data.respJson + ')');
					zTree = $.fn.zTree.init($("ul.authTree"), setting, zNodes);
				} else {
					$('.alert').hide();
					$('.alert > i').empty();
					$('#authWarning > i').append('获取功能列表失败，这将导致您无法为角色授权！');
					$('#authWarning').show();
				}
			}
		});
	}

	$('#submitAuth').click(function() {
		checks = zTree.getCheckedNodes(true);
		var modules = new Array(checks.length)
		for (var i = 0; i < checks.length; i++) {
			modules[i] = checks[i].id;
		}
		$.ajax({
			type : "POST",
			url : ctx + "/roles/authorize",
			data : {
				modules : JSON.stringify(modules),
				roleId : $("#roleId").val()
			},
			dataType : "json",
			success : function(data) {
				if (data.result) {
					$('.alert').hide();
					$('.alert > i').empty();
					$('#authSuccess > i').append(data.respMsg + '！');
					$('#authSuccess').show();
				} else {
					$('.alert').hide();
					$('.alert > i').empty();
					$('#authError > i').append(data.respMsg + '！');
					$('#authError').show();
				}
			}
		});
	});

	return {
		init : function() {
			buildModuleTree();
		}
	};
}();

var RoleForm = function() {

	var handleValidation = function() {
		$('form.roleEdit').validate({
			errorElement : 'span',
			errorClass : 'help-inline',
			focusInvalid : false,
			ignore : ":disabled",
			rules : {
				roleName : {
					required : true,
				},
				roleDescribe : {
					required : true,
				}
			},
			messages : {
				roleName : {
					required : "角色名称不能为空."
				},
				roleDescribe : {
					required : "角色描述不能为空.",
				}
			}
		});
	}

	return {
		init : function() {
			handleValidation();
		}
	};

}();

var editTag;
$('#roleCreate').click(function() {
	editTag = 'create';
	$('h3.roleEdit').text("新建角色");
	$('.datatables').hide();
	$('.editform').show();
	$('#resetRole').show();
	$('#nextRole').hide();
	$('#authRole').hide();
	$('.alert').hide();
	$('.alert > i').empty();
});

$("#submitRole").click(function() {
	if ($("form.roleEdit").valid()) {
		$.ajax({
			type : "POST",
			url : ctx + "/roles/submitRole",
			data : {
				roleId : $("#roleId").val(),
				roleName : $("#roleName").val(),
				roleDescribe : $("#roleDescribe").val(),
			},
			dataType : "json",
			success : function(data) {
				if (data.result) {
					if ('create' == editTag) {
						$('#nextRole').show();
					}
					$("#roleId").val(data.respObj.roleId);
					$('#resetRole').hide();
					$('#authRole').show();
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

$('#resetRole').click(function() {
	resetRoleForm();
});

$('#nextRole').click(function() {
	resetRoleForm();
	$('#resetRole').show();
	$('#nextRole').hide();
	$('#authRole').hide();
});

$('#authRole').click(function() {
	var roleName = $("#roleName").val();
	$('h3.authTree').text("授权【" + roleName + "】");
	RoleAuth.init();
	$('.editform').hide();
	$('.modulesTree').show();
});

$('#backEdit').click(function() {
	$('.editform').show();
	$('.modulesTree').hide();
	$('.alert').hide();
	$('.alert > i').empty();
});

var resetRoleForm = function() {
	$("#roleId").val('');
	$("#roleName").val('');
	$("#roleDescribe").val('');
	$('.controls > span.help-inline').remove();
}

var RolesTable = function() {

	var handleTable = function() {
		$('.rolesTable').DataTable(
				{
					"lengthMenu" : [ 10, 20, 30 ],
					"pageLength" : 10,
					"ajax" : {
						"url" : ctx + "/roles/query",
						"dataSrc" : function(json) {
							return eval('(' + json.data + ')');
						}
					},
					"columnDefs" : [ {
						"targets" : [ 0 ],
					}, {
						"targets" : [ 1 ],
						"width" : "25%"
					}, {
						"targets" : [ 2 ],
						"width" : "75%"
					} ],
					"columns" : [ {
						"title" : "角色ID",
						"data" : "roleId",
						"visible" : false
					}, {
						"title" : "用户姓名",
						"data" : "roleName"
					}, {
						"title" : "用户角色",
						"data" : "roleDescribe"
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
		var table = $('.rolesTable').DataTable();
		$('.rolesTable tbody').on(
				'click',
				'tr',
				function() {
					if ($(this).hasClass('selected')) {
						$(this).removeClass('selected');
						row = null;
						$('#roleUpdate').hide();
						$('#roleCancle').hide();
					} else {
						table.$('tr.selected').removeClass('selected');
						$(this).addClass('selected');
						row = table.row(this).data();
						if ("00000000000000000001" == row.roleId
								|| "00000000000000000002" == row.roleId) {
							// 默认用户禁止编辑/删除
							$('#roleUpdate').hide();
							$('#roleCancle').hide();
						} else {
							$('#roleUpdate').show();
							$('#roleCancle').show();
						}
					}
				});
		$('#roleUpdate').click(function() {
			editTag = 'update';
			$('h3.roleEdit').text("编辑角色");
			$("#roleId").val(row.roleId);
			$("#roleName").val(row.roleName);
			$("#roleDescribe").val(row.roleDescribe);
			$('.datatables').hide();
			$('.editform').show();
			$('#resetRole').hide();
			$('#nextRole').hide();
			$('#authRole').show();
			$('.alert').hide();
			$('.alert > i').empty();
		});
		$('#roleCancle').click(function() {
			$.ajax({
				type : "POST",
				url : ctx + "/roles/cancleRole",
				data : {
					roleId : row.roleId,
				},
				dataType : "json",
				success : function(data) {
					if (data.result) {
						table.ajax.reload().draw();
						$('#roleUpdate').hide();
						$('#roleCancle').hide();
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
			resetRoleForm();
			table.ajax.reload().draw();
			$('#roleUpdate').hide();
			$('#roleCancle').hide();
		});
	}

	return {
		init : function() {
			handleTable();
			selectBtn();
		}
	};
}();