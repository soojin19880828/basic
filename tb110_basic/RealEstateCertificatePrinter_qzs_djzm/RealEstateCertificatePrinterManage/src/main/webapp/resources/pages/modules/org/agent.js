var AgentForm = function() {

	var handleValidation = function() {
		$('form.agentEdit').validate({
			errorElement : 'span',
			errorClass : 'help-inline',
			focusInvalid : false,
			ignore : ":disabled",
			rules : {
				orgId : {
					required : true,
				},
				agentIdNumber : {
					required : true,
				},
				agentFullName : {
					required : true,
				}
			},
			messages : {
				orgId : {
					required : "请选择机构名称.",
				},
				agentIdNumber : {
					required : "代理人身份证号不能为空."
				},
				agentFullName : {
					required : "代理人姓名不能为空."
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
$('#agentCreate').click(function() {
	resetAgentForm();
	editTag = 'create';
	$('h3.agentEdit').text("新建代理人");
	$('.datatables').hide();
	$('.editform').show();
	$('#resetAgent').show();
	$('#nextAgent').hide();
	$('.alert').hide();
	$('.alert > i').empty();
});

$("#submitAgent").click(function() {
	if ($("form.agentEdit").valid()) {
		$('#submitAgent').button('loading');
		$.ajax({
			type : "POST",
			url : ctx + "/agent/submit",
			data : {
				agentId : $("#agentId").val(),
				orgId : $("#orgId").val(),
				agentIdNumber : $("#agentIdNumber").val(),
				agentFullName : $("#agentFullName").val(),
			},
			dataType : "json",
			success : function(data) {
				$('#submitAgent').button('reset');
				if (data.result) {
					if ('create' == editTag) {
						$('#nextAgent').show();
					}
					$("#agentId").val(data.respObj.agentId);
					$('#resetAgent').hide();
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

$('#resetAgent').click(function() {
	resetAgentForm();
	$('.alert').hide();
	$('.alert > i').empty();
});

$('#nextAgent').click(function() {
	resetAgentForm();
	$('#resetAgent').show();
	$('#nextAgent').hide();
	$('.alert').hide();
	$('.alert > i').empty();
});

var resetAgentForm = function() {
	$("#agentId").val('');
	$("#orgId").val('');
	$("#agentIdNumber").val('');
	$("#agentFullName").val('');
	$('.controls > span.help-inline').remove();
}

var AgentTable = function() {

	var handleTable = function() {
		$('.agentTable').DataTable(
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
						"url" : ctx + "/agent/query",
						"dataSrc" : "data",
						"data" : function(page) {
							var param = {};
							param.draw = page.draw;
							param.start = page.start;
							param.length = page.length;
							param.sOrgId = $("#sOrgId").val();
							param.sAgentIdNumber = $("#sAgentIdNumber").val();
							param.sAgentFullName = $("#sAgentFullName").val();
							return param;
						}
					},
					"columnDefs" : [ {
						"targets" : [ 0 ]
					}, {
						"targets" : [ 1 ]
					}, {
						"targets" : [ 2 ]
					}, {
						"targets" : [ 3 ]
					}, {
						"targets" : [ 4 ]
					} ],
					"columns" : [ {
						"title" : "代理人ID",
						"data" : "agentId",
						"visible" : false,
						"render" : function(data, type, full, meta) {
							if (data == null) {
								return null;
							}
							return data;
						}
					}, {
						"title" : "机构ID",
						"data" : "orgId",
						"visible" : false,
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
					}, {
						"title" : "代理人身份证号",
						"data" : "agentIdNumber",
						"render" : function(data, type, full, meta) {
							if (data == null) {
								return null;
							}
							return data;
						}
					}, {
						"title" : "代理人姓名",
						"data" : "agentFullName",
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
		var table = $('.agentTable').DataTable();
		$('.agentTable tbody').on('click', 'tr', function() {
			if ($(this).hasClass('selected')) {
				$(this).removeClass('selected');
				row = null;
				$('#agentUpdate').hide();
				$('#agentCancle').hide();
			} else {
				table.$('tr.selected').removeClass('selected');
				$(this).addClass('selected');
				row = table.row(this).data();
				$('#agentUpdate').show();
				$('#agentCancle').show();
			}
		});
		$('#agentSearch').click(function() {
			table.draw();
		});
		$('#agentReset').click(function() {
			$("#sOrgId").val("");
			$("#sAgentIdNumber").val("");
			$("#sAgentFullName").val("");
			table.draw();
		});
		$('#agentUpdate').click(function() {
			editTag = 'update';
			$('h3.agentEdit').text("编辑代理人");
			$("#agentId").val(row.agentId);
			$("#orgId").val(row.orgId);
			$("#agentIdNumber").val(row.agentIdNumber);
			$("#agentFullName").val(row.agentFullName);
			$('.datatables').hide();
			$('.editform').show();
			$('#resetAgent').hide();
			$('#nextAgent').hide();
			$('.alert').hide();
			$('.alert > i').empty();
		});
		$('#agentCancle').click(function() {
			$.ajax({
				type : "POST",
				url : ctx + "/agent/cancle",
				data : {
					agentId : row.agentId,
				},
				dataType : "json",
				success : function(data) {
					if (data.result) {
						table.ajax.reload().draw();
						$('#agentUpdate').hide();
						$('#agentCancle').hide();
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
			resetAgentForm();
			table.ajax.reload().draw();
			$('#agentUpdate').hide();
			$('#agentCancle').hide();
		});
	}

	return {
		init : function() {
			handleTable();
			selectBtn();
			buildOrgList();
		}
	};
}();

var buildOrgList = function() {
	$.ajax({
		type : "POST",
		url : ctx + "/org/getOrgList",
		data : {},
		dataType : "json",
		success : function(data) {
			if (data.result) {
				var orgs = data.respObj;
				for (var i = 0; i < orgs.length; i++) {
					var org = orgs[i];
					$('#orgId').append(
							'<option value="' + org.orgId + '">' + org.orgName
									+ '</option>');
					$('#sOrgId').append(
							'<option value="' + org.orgId + '">' + org.orgName
									+ '</option>');
				}
			} else {
				$('.alert').hide();
				$('.alert > i').empty();
				$('#alertError > i').append('获取机构列表失败，这将导致您无法新建代理人！');
				$('#alertError > i').show();
			}
		}
	});
}