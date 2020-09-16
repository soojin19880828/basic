var Terminals = function() {

	var handleTable = function() {
		$('.terminalsTable').DataTable(
				{
					"lengthMenu" : [ 10, 20, 30 ],
					"pageLength" : 10,
					"ajax" : {
						"url" : ctx + "/queryTerminals",
						"dataSrc" : function(json) {							
							return eval('(' + json.data + ')');						
						}
					},
					"columnDefs" : [
							{
								"targets" : [ 0 ],
							},
							{
								"targets" : [ 1 ],
							},
							{
								"targets" : [ 2 ],
							},
							{
								"targets" : [ 3 ],
							},
							{
								"targets" : [ 4 ],
							},
							{
								"targets" : [ 5 ],
								"createdCell" : function(td, cellData, rowData,
										row, col) {
									$(td).addClass("cell-status")
								}
							}
							],
					"columns" : [ {
						"title" : "终端机编号",
						"data" : "terminalId"
					}, {
						"title" : "终端机型号",
						"data" : "terminalModel"
					}, {
						"title" : "终端机名称",
						"data" : "terminalName"
					}, {
						"title" : "部署地区",
						"data" : "admiAreaName"
					},{
						"title" : "超级密码",
						"data" : "extendField"
					},
					{
						"title" : "终端机状态",
						"data" : "terminalStatus",
						"render" : function(data, type, full, meta) {
							if ("01" == data) {
								return '<b class="normal">正常</b>';
							}
							if ("02" == data) {
								return '<b class="warning">警告</b>';
							}
							if ("03" == data) {
								return '<b class="danger">错误</b>';
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
					}
				});
	}

	return {
		init : function() {
			handleTable();
		}
	};
}();