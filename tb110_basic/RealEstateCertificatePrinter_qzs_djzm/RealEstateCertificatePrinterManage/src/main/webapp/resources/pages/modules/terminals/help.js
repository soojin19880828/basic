
// 进行多次加载时要清空和还原dataTable
function clearDataTable() {
	if ($('.printTable').hasClass('dataTable')) {
		var table = $('.printTable').dataTable();
		table.fnClearTable(false);
		table.fnDestroy();
	}
}

var string;

function SetCss(string) {

	bootbox.dialog({
		// dialog的内容
		message : string,
		// dialog的标题
		title : "提示：",
		// 是否显示body的遮罩，默认true
		backdrop : false,
		// 是否显示关闭按钮，默认true
		closeButton : true,
		// dialog的类名
		className : "my-modal",
		// dialog底端按钮配置
		buttons : {
			// 其中一个按钮配置
			success : {
				// 按钮显示的名称
				label : "确认",
				// 按钮的类名
				className : "btn-success",
			},
		}
	});
}
//提交状态
var submit = true;
//删除时使用
var recordIdNum;
 //记录 公告类别（办事指南 法律法规 收费标准）
var helpTitlevalue;
// 提交类型 （新增 修改）
var submitType;
var PrintTable = function() {
	var handleTable = function() {
		/*
		 * 办事指南 table
		 */
		$('.bsznTable').DataTable(
				{
					"serverSide" : true,
					"processing" : true,
					"ordering" : true,
					"stateSave" : true,
					"destroy": true,
					"searching" : false,
//					"select" : true,
					"lengthMenu" : [ 10, 20, 30 ],
					"pageLength" : 10,
					"ajax" : {
						"url" : ctx + "/help/query",
						"data" : function(page) {
							var param = {};
							param.draw = page.draw;
							param.start = page.start;
							param.length = page.length;
							param.title = "bszn"
							return param;
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
						}],
					"columns" : [ {
						"title" : "编号",
						"data" : "helpId",
						"width" : "10%",
						"visible" : true
					}, {
						"title" : "内容",
						"data" : "helpContent",
						"width" : "90%",
					}, {
						"title" : "ID",
						"data" : "recordId",
						"visible" : false
					}],
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
		
		
		/*
		 * 法律法规 table
		 */
		$('.flfgTable').DataTable(
				{
					"serverSide" : true,
					"processing" : true,
					"ordering" : true,
//					"stateSave" : true,
					"destroy": true,
					"searching" : false,
					"select" : true,
					"lengthMenu" : [ 10, 20, 30 ],
					"pageLength" : 10,
					"ajax" : {
						"url" : ctx + "/help/query",
						"data" : function(page) {
							var param = {};
							param.draw = page.draw;
							param.start = page.start;
							param.length = page.length;
							param.title = "flfg"
							return param;
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
						}],
					"columns" : [ {
						"title" : "编号",
						"data" : "helpId",
						"visible" : true,
						"width" : "10%"
					}, {
						"title" : "内容",
						"data" : "helpContent",
						"width" : "90%"
					}, {
						"title" : "ID",
						"data" : "recordId",
						"visible" : false
					}],
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
		
		/*
		 * 收费标准
		 */
		$('.sfbzTable').DataTable(
				{
					"serverSide" : true,
					"processing" : true,
					"ordering" : true,
//					"stateSave" : true,
					"destroy": true,
					"searching" : false,
					"select" : true,
					"lengthMenu" : [ 10, 20, 30 ],
					"pageLength" : 10,
					"ajax" : {
						"url" : ctx + "/help/query",
						"data" : function(page) {
							var param = {};
							param.draw = page.draw;
							param.start = page.start;
							param.length = page.length;
							param.title = "sfbz"
							return param;
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
						}],
					"columns" : [ {
						"title" : "编号",
						"data" : "helpId",
						"visible" : true,
						"width" : "10%"
					}, {
						"title" : "内容",
						"data" : "helpContent",
						"width" : "90%"
					}, {
						"title" : "ID",
						"data" : "recordId",
						"visible" : false
					}],
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

	/*
	 * 选中办事指南
	 */
		var selectBtnbszn = function() {
			helpTitlevalue = "bszn";
			var row;
			var table = $('.bsznTable').DataTable();
			$('.bsznTable tbody').off('click', 'tr').on('click', 'tr', function() {
				if ($(this).hasClass('selected')) {
					$(this).removeClass('selected');
					row = null;
					$('#deletebszn').hide();
					$('#changebszn').hide();
				} else {
					table.$('tr.selected').removeClass('selected');
					$(this).addClass('selected');
					row = table.row(this).data();
					$('#deletebszn').show();
					$('#changebszn').show();
				}
			});
			
			/*
			 * 办事指南 点击修改
			 */
			$('#changebszn').click(function() {
				submitType = "change";
				helpTitlevalue = "bszn";
				$('h3.helpEdit').text("办事指南--修改");
				$("#number").val(row.helpId);
				$("#content").val(row.helpContent);
				$("#recordId").val(row.recordId);
				$('.datatables').hide();
				$('.editform').show();
				$('#submitUser').show();
				$('#backTable').show();
				$('.alert').hide();
				$('.alert > i').empty();
			});
			/*
			 * 办事指南 新增
			 */
			$('#increasebszn').click(function(){
				submitType = "increase";
				helpTitlevalue = "bszn";
				$('h3.helpEdit').text("办事指南--新增");
				$("#number").val("");
				$("#content").val("");
				$("#recordId").val("");
				$('.datatables').hide();
				$('.editform').show();
				$('#submitUser').show();
				$('#backTable').show();
				$('.alert').hide();
				$('.alert > i').empty();
			})
			/*
			 * 办事指南删除
			 */
			$('#deletebszn').click(function() {
				recordIdNum = row.recordId;
				deleteHelp();
			});
		}
			/*
			 * 选中法律法规
			 */
			var selectBtnflfg = function() {
				helpTitlevalue = "flfg";
				var row;
				var table = $('.flfgTable').DataTable();
				$('.flfgTable tbody').off('click', 'tr').on('click', 'tr', function() {
					if ($(this).hasClass('selected')) {
						$(this).removeClass('selected');
						row = null;
						$('#deleteflfg').hide();
						$('#changeflfg').hide();
					} else {
						table.$('tr.selected').removeClass('selected');
						$(this).addClass('selected');
						row = table.row(this).data();
						
						$('#deleteflfg').show();
						$('#changeflfg').show();
					}
				});
				/*
				 * 法律法规 点击修改
				 */
				$('#changeflfg').click(function() {
					submitType = "change";
					helpTitlevalue = "flfg";
					$('h3.helpEdit').text("法律法规--修改");
					$("#number").val(row.helpId);
					$("#content").val(row.helpContent);
					$("#recordId").val(row.recordId);
					$('.datatables').hide();
					$('.editform').show();
					$('#submitUser').show();
					$('#backTable').show();
					$('.alert').hide();
					$('.alert > i').empty();
				});
				
				/*
				 * 法律法规 新增
				 */
				$('#increaseflfg').click(function(){
					submitType = "increase";
					helpTitlevalue = "flfg";
					$('h3.helpEdit').text("法律法规--新增");
					$("#number").val("");
					$("#content").val("");
					$("#recordId").val("");
					$('.datatables').hide();
					$('.editform').show();
					$('#submitUser').show();
					$('#backTable').show();
					$('.alert').hide();
					$('.alert > i').empty();
				})
				/*
				 * 法律法规 删除
				 */
				$('#deleteflfg').click(function() {
					recordIdNum = row.recordId;
					deleteHelp();
				});
				
			}
				
			/*
			 * 选中收费标准
			 */
				var selectBtnsfbz = function() {
					helpTitlevalue = "sfbz";
					var row;
					var table = $('.sfbzTable').DataTable();
					$('.sfbzTable tbody').off('click', 'tr').on('click', 'tr', function() {
						if ($(this).hasClass('selected')) {
							$(this).removeClass('selected');
							row = null;
							$('#deletesfbz').hide();
							$('#changesfbz').hide();
						} else {
							table.$('tr.selected').removeClass('selected');
							$(this).addClass('selected');
							row = table.row(this).data();
							
							$('#deletesfbz').show();
							$('#changesfbz').show();
						}
					});
					
					/*
					 * 收费标准 点击修改
					 */
					$('#changesfbz').click(function() {
						submitType = "change";
						helpTitlevalue = "sfbz";
						$('h3.helpEdit').text("收费标准--修改");
						$("#number").val(row.helpId);
						$("#content").val(row.userName);
						$("#content").val(row.helpContent);
						$("#recordId").val(row.recordId);
						$('.datatables').hide();
						$('.editform').show();
						$('#submitUser').show();
						$('#backTable').show();
						$('.alert').hide();
						$('.alert > i').empty();
					});
					/*
					 * 收费标准 新增
					 */
					$('#increasesfbz').click(function(){
						submitType = "increase";
						helpTitlevalue = "sfbz";
						$('h3.helpEdit').text("收费标准--新增");
						$("#number").val("");
						$("#content").val("");
						$("#recordId").val("");
						$('.datatables').hide();
						$('.editform').show();
						$('#submitUser').show();
						$('#backTable').show();
						$('.alert').hide();
						$('.alert > i').empty();
					})
					
					/*
					 * 收费便准删除
					 */
					$('#deletesfbz').click(function() {
						recordIdNum = row.recordId;
						deleteHelp();
					});
				}
				
				/*
				 * 提交
				 */
				$("#submithelp").click(function() {
					
					//修改提交
					if(submit && submitType == "change"){
						if ($("form.helpEdit").valid()) {
							var helpId = $('#number').val();
							var content = $('#content').val();
							if(helpId == "" || content == ""){
								alert("提交内容不能为空！");
								return false;
							}
							var reg = /^[0-9]*$/;
							if (!reg.test(helpId)) {
							alert("编号只能为数字！")
							return false;
							}
							
							submit = false;
							
							$.ajax({
								type : "POST",
								url : ctx + "/help/updateHelp",
								data : {
									helpId : $('#number').val(),
									helpTitle : helpTitlevalue,
									recordId : $("#recordId").val(),
									helpContent : $('#content').val()
								},
								dataType : "json",
								success : function(data) {
									if (data.result) {
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
						//新增提交
					}else if(submit && submitType == "increase"){
						if ($("form.helpEdit").valid()) {
							var helpId = $('#number').val();
							var content = $('#content').val();
							if(helpId == "" || content == ""){
								alert("提交内容不能为空！");
								return false;
							}
							var reg = /^[0-9]*$/;
							if (!reg.test(helpId)) {
							alert("编号只能为数字！")
							return false;
							}
							
							submit = false;
							
							
							$.ajax({
								type : "POST",
								url : ctx + "/help/increaseHelp",
								data : {
									helpId : $('#number').val(),
									helpTitle : helpTitlevalue,
									helpContent : $('#content').val()
								},
								dataType : "json",
								success : function(data) {
									if (data.result) {
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
					}else{
						alert("已完成提交，请不要重复提交！");
					}
					
				});
				
				/*
				 * 删除
				 */
				var deleteHelp = function() {
					$.ajax({
						type : "POST",
						url : ctx + "/help/cancleHelp",
						data : {
							recordId : recordIdNum,
							helpId : $('#number').val(),
							helpTitle : helpTitlevalue
						},
						dataType : "json",
						success : function(data) {
							if (data.result) {
								$('.alert').hide();
								$('.alert > i').empty();
								$('#operSuccess > i').append(data.respMsg + '！');
								$('#operSuccess').show();
								location.href += "?reload=true";
							} else {
								$('.alert').hide();
								$('.alert > i').empty();
								$('#operError > i').append(data.respMsg + '！');
								$('#operError').show();
							}
						}
					});
				}
				
				
				
				
				
				
				
				
				
				
				
				
			$('#printDetail').click(
					function() {
						$("#recordId").val(row.recordId);
						$("#bizNumber").val(row.bizNumber);
						$("#certNumber").val(row.certNumber);
						$("#seqNumber").val(row.seqNumber);
						$("#printTime").val(row.printTime);
						$("#certScan").attr(
								"src",
								ctx + "/print/getPic?picPath="
										+ row.certScanPath);
						$('.datatables').hide();
						$('.detailForm').show();
						$('.alert').hide();
						$('.alert > i').empty();
					});
			$('#printResend').click(function() {
				$.ajax({
					type : "POST",
					url : ctx + "/print/resend",
					data : {
						recordId : row.recordId,
					},
					dataType : "json",
					success : function(data) {
						if (data.result) {
							table.ajax.reload().draw();
							$('#printDetail').hide();
							$('#printResend').hide();
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
			//返回
			$('#backTable').click(function() {
				submit = true;
				$('.datatables').show();
				// 刷新页面
				location.href += "?reload=true";
			});

	return {
		init : function() {
			handleTable();
			selectBtnbszn();
			selectBtnflfg();
			selectBtnsfbz();
		}
	};

}();

