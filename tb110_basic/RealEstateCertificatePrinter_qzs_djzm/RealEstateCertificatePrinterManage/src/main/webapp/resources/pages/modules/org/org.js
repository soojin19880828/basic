var OrgForm = function() {

	var handleValidation = function() {
		$('form.orgEdit').validate({
			errorElement : 'span',
			errorClass : 'help-inline',
			focusInvalid : false,
			ignore : ":disabled",
			rules : {
				orgCode : {
					required : true,
				},
				orgName : {
					required : true,
				}
			},
			messages : {
				orgCode : {
					required : "机构编码不能为空",
				},
				orgName : {
					required : "机构名称不能为空.",
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
$('#orgCreate').click(function() {
	resetOrgForm();
	editTag = 'create';
	$('h3.orgEdit').text("新建机构");
	$('.datatables').hide();
	$('.editform').show();
	$('#resetOrg').show();
	$('#nextOrg').hide();
	$('.alert').hide();
	$('.alert > i').empty();
});

$("#submitOrg").click(function() {
	if ($("form.orgEdit").valid()) {
		$('#submitOrg').button('loading');
		$.ajax({
			type : "POST",
			url : ctx + "/org/submit",
			data : {
				orgId : $("#orgId").val(),
				orgCode : $("#orgCode").val(),
				orgName : $("#orgName").val(),
			},
			dataType : "json",
			success : function(data) {
				$('#submitOrg').button('reset');
				if (data.result) {
					if ('create' == editTag) {
						$('#nextOrg').show();
					}
					$("#orgId").val(data.respObj.orgId);
					$('#resetOrg').hide();
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

$('#resetOrg').click(function() {
	resetOrgForm();
	$('.alert').hide();
	$('.alert > i').empty();
});

$('#nextOrg').click(function() {
	resetOrgForm();
	$('#resetOrg').show();
	$('#nextOrg').hide();
	$('.alert').hide();
	$('.alert > i').empty();
});

var resetOrgForm = function() {
	$("#orgId").val('');
	$("#orgCode").val('');
	$("#orgName").val('');
	$('.controls > span.help-inline').remove();
}

var OrgTable = function() {

	var handleTable = function() {
		$('.orgTable').DataTable(
				{
					"serverSide" : true,
					"processing" : true,
					"ordering" : false,
					"stateSave" : true,
					"searching" : false,
					"select" : true,
					"lengthMenu" : [ 10, 20, 30 ],
					"pageLength" : 10,
					"ajax" : {
						"url" : ctx + "/org/query",
						"dataSrc" : "data",
						"data" : function(page) {
							var param = {};
							param.draw = page.draw;
							param.start = page.start;
							param.length = page.length;
							param.sOrgCode = $("#sOrgCode").val();
							param.sOrgName = $("#sOrgName").val();
							return param;
						}
					},
					"columnDefs" : [ {
						"targets" : [ 0 ]
					},{
						"targets" : [ 1 ]
					}, {
						"targets" : [ 2 ]
					} ],
					"columns" : [ {
						"title" : "机构Id",
						"data" : "orgId",
						"visible" : false,
						"render" : function(data, type, full, meta) {
							if (data == null) {
								return null;
							}
							return data;
						}
					},{
						"title" : "机构编码",
						"data" : "orgCode",
						"render" : function(data, type, full, meta) {
							if (data == null) {
								return null;
							}
							return data;
						}
					}, {
						"title" : "机构名称",
						"data" : "orgName",
						"render" : function(data, type, full, meta) {
							if (data == null) {
								return null;
							}
							return data;
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
		var table = $('.orgTable').DataTable();
		$('.orgTable tbody').on('click', 'tr', function() {
			if ($(this).hasClass('selected')) {
				$(this).removeClass('selected');
				row = null;
				$('#orgUpdate').hide();
				$('#orgCancle').hide();
			} else {
				table.$('tr.selected').removeClass('selected');
				$(this).addClass('selected');
				row = table.row(this).data();
				$('#orgUpdate').show();
				$('#orgCancle').show();
			}
		});
		$('#orgSearch').click(function() {
			table.draw();
		});
		$('#orgReset').click(function() {
			$("#sOrgId").val("");
			$("#sOrgCode").val("");
			$("#sOrgName").val("");
			table.draw();
		});
		$('#orgUpdate').click(function() {
			editTag = 'update';
			$('h3.orgEdit').text("编辑机构");
			$("#orgId").val(row.orgId);
			$("#orgCode").val(row.orgCode);
			$("#orgName").val(row.orgName);
			$('.datatables').hide();
			$('.editform').show();
			$('#resetOrg').hide();
			$('#nextOrg').hide();
			$('.alert').hide();
			$('.alert > i').empty();
		});
		$('#orgCancle').click(function() {
			$.ajax({
				type : "POST",
				url : ctx + "/org/cancle",
				data : {
					orgId : row.orgId,
				},
				dataType : "json",
				success : function(data) {
					if (data.result) {
						table.ajax.reload().draw();
						$('#orgUpdate').hide();
						$('#orgCancle').hide();
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
			resetOrgForm();
			table.ajax.reload().draw();
			$('#orgUpdate').hide();
			$('#orgCancle').hide();
		});
	}

	return {
		init : function() {
			handleTable();
			selectBtn();
		}
	};
}();