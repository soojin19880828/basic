var resetDetailForm = function() {
	$("#recordId").val('');
	$("#userFullname").val('');
	$("#userIdnumber").val('');
	$("#usageTime").val('');
	$("#deviceNumber").val('');
	$("#userIdPic").attr("src", '#');
	$('#userDetPic').attr("src", '#');
}

var UsageTable = function() {

	var handleTable = function() {
		$('.usageTable').DataTable(
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
						"url" : ctx + "/usage/query",
						"dataSrc" : "data",
						"data" : function(page) {
							var param = {};
							param.draw = page.draw;
							param.start = page.start;
							param.length = page.length;
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
					}, {
						"targets" : [ 5 ]
					}, {
						"targets" : [ 6 ]
					}, {
						"targets" : [ 7 ]
					} ],
					"columns" : [ {
						"title" : "使用ID",
						"data" : "recordId",
						"visible" : false
					}, {
						"title" : "用户姓名",
						"data" : "userFullname"
					}, {
						"title" : "身份证号",
						"data" : "userIdnumber"
					}, {
						"title" : "使用时间",
						"data" : "usageTime"
					}, {
						"title" : "设备编号",
						"data" : "deviceNumber"
					}, {
						"title" : "身份证照片",
						"data" : "userIdPicPath",
						"visible" : false
					}, {
						"title" : "现场照片",
						"data" : "userDetPicPath",
						"visible" : false
					}, {
						"title" : "使用日志",
						"data" : "userLogPath",
						"visible" : false
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
		var table = $('.usageTable').DataTable();
		$('.usageTable tbody').on('click', 'tr', function() {
			if ($(this).hasClass('selected')) {
				$(this).removeClass('selected');
				row = null;
				$('#usageDetail').hide();
				$('#usageLog').hide();
			} else {
				table.$('tr.selected').removeClass('selected');
				$(this).addClass('selected');
				row = table.row(this).data();
				$('#usageDetail').show();
				$('#usageLog').show();
			}
		});
		$('#usageDetail').click(
				function() {
					$("#recordId").val(row.recordId);
					$("#userFullname").val(row.userFullname);
					$("#userIdnumber").val(row.userIdnumber);
					$("#usageTime").val(row.usageTime);
					$("#deviceNumber").val(row.deviceNumber);
					$("#userIdPic").attr(
							"src",
							ctx + "/usage/getPic?picPath="
									+ row.userIdPicPath);
					$('#userDetPic').attr(
							"src",
							ctx + "/usage/getPic?picPath="
									+ row.userDetPicPath);
					$('.datatables').hide();
					$('.detailForm').show();
					$('.alert').hide();
					$('.alert > i').empty();
				});
		$('#usageLog').click(function(){
			window.open(ctx + "/usage/getLog?logPath=" + row.userLogPath);
		});
		$('#backTable').click(function() {
			$('.detailForm').hide();
			$('.datatables').show();
			resetDetailForm();
		});
	}

	return {
		init : function() {
			handleTable();
			selectBtn();
		}
	};
}();