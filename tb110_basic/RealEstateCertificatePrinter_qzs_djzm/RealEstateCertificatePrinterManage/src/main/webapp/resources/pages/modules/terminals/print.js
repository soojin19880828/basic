var resetDetailForm = function () {
    $("#recordId").val('');
    $("#bizNumber").val('');
    $("#certNumber").val('');
    $("#seqNumber").val('');
    $("#printTime").val('');
    $("#certScan").attr("src", '#');
}

var resetAddDetailForm = function () {
    $("#userFullname").val('');
    $("#userIdNumber").val('');
    $("#certNumber0").val('');
    $("#certNumber1").val('');
    $("#certNumber4").val('');
    $("#certNumber3").val('');
    $("#bizNumber1").val('');
    $("#ocr").val('');
}

/** ************************************后面加的开始************************************************************* */

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
        message: string,
        // dialog的标题
        title: "提示：",
        // 是否显示body的遮罩，默认true
        backdrop: false,
        // 是否显示关闭按钮，默认true
        closeButton: true,
        // dialog的类名
        className: "my-modal",
        // dialog底端按钮配置
        buttons: {
            // 其中一个按钮配置
            success: {
                // 按钮显示的名称
                label: "确认",
                // 按钮的类名
                className: "btn-success",
            },
        }
    });
}

function SetInsertCss(string) {

    bootbox.dialog({
        // dialog的内容
        message: string,
        // dialog的标题
        title: "提示：",
        // 是否显示body的遮罩，默认true
        backdrop: false,
        // 是否显示关闭按钮，默认true
        closeButton: true,
        // dialog的类名
        className: "my-modal",
        // dialog底端按钮配置
        buttons: {
            // 其中一个按钮配置
            success: {
                // 按钮显示的名称
                label: "确认",
                // 按钮的类名
                className: "btn-success",
                callback: function () {
                    location.href += "?reload=true";
                }
            },
        }
    });
}

// 导出数据
var exportState = true;
var exportData = function () {
    // 获取记录数
    var tab = $('.printTable').DataTable();
    var dataRows = tab.page.info().recordsTotal;
    $('#printResend').attr('style', 'display:none');
    $('#printDetail').attr('style', 'display:none');
    if (dataRows > 0) {
        /*$.ajax({
            type : "POST",
            url : ctx + "/print/export",
            success : function(data) {
                if (data.result = true) {
                    string = "操作成功,请在（D:\\PrintRecord）此目录下查看!!!";
                } else {
                    string = "操作异常,请联系管理员!!!"
                }
                SetCss(string);
            }
        });*/
        if (exportState) {
            window.location.href = ctx + "/print/export";
            exportState = false;
        } else {
            string = "正在下载数据，请不要重复点击!!!";
            SetCss(string);
        }
    } else {
        string = "无数据!!!";
        SetCss(string);
    }
}

// 判断字符串时间是否为空
function isNull(timeString) {
    if (timeString == null || timeString == "") {
        return true;
    }
    return false;
}

/*
 * 检测时间是否为空
 */
function checkNull(id) {
    // 开始时间
    if (id == "startTime") {
        startTime = $("#startTime").val();
        if (isNull(startTime)) {
            string = "开始日期不能为空!!!";
            SetCss(string);
            return false;
        }
        return true;
    }

    // 结束时间
    if (id == "endTime") {
        endTime = $("#endTime").val();

        if (isNull(endTime)) {
            string = "结束日期不能为空!!!";
            SetCss(string);
            return false;
        }
        return true;
    }
}

/*
 * 检测开始时间是否小于结束时间（字符串也可以之间比较难控制相差的时间长度，使用毫秒计算）
 */
function checkDate() {

    var startTimeMills = new Date($("#startTime").val());

    var endTIimeMills = new Date($("#endTime").val());

    if (startTimeMills < endTIimeMills) {
        return true;
    } else {
        string = "开始日期必须小于结束日期";
        SetCss(string);
        return false;
    }
}

/*
var search = function() {
	// 日期校验
	// if (!checkNull('startTime')) {
	// return;
	// }
	// if (!checkNull('endTime')) {
	// return;
	// }
	// 如果日期都不为空再校验
	if (!isNull($("#startTime").val()) && !isNull($("#endTime").val())) {
		if (!checkDate()) {
			return;
		}
	}
	// 进行多次加载时要清空和还原dataTable
	clearDataTable();
	$('#printDetail').hide();
	$('.printTable').DataTable(
			{
				"serverSide" : true,
				"processing" : true,
				"ordering" : false,
				"stateSave" : false,
				// "destroy":true,
				"select" : true,
				"searching" : false,
				"lengthMenu" : [ 10, 20, 30 ],
				"pageLength" : 10,
				"autoWidth" : false,
				"ajax" : {
					"url" : ctx + "/print/search",
					"dataSrc" : "data",
					"data" : function(page) {
						var param = {};
						param.draw = page.draw;
						param.start = page.start;
						param.length = page.length;
						param.startTime = $("#startTime").val();
						param.endTime = $("#endTime").val();
						param.bizNumber = $("#bizNumber2").val();
						param.certNumber = $("#certNumber2").val();
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
				}, 
					 * { "targets" : [ 5 ], "createdCell" : function(td,
					 * cellData, rowData, row, col) {
					 * $(td).addClass("cell-status") } },
					 {
					"targets" : [ 5 ],
					"createdCell" : function(td, cellData, rowData, row, col) {
						$(td).addClass("cell-status")
					}
				}, {
					"targets" : [ 6 ]
				} ],
				"columns" : [ {
					"title" : "打印ID",
					"data" : "recordId",
					"visible" : false
				}, {
					"title" : "业务流水号",
					"data" : "bizNumber",
					"width" : "15%",
				}, {
					"title" : "不动产权证号",
					"data" : "certNumber",
					"width" : "40%",
				}, {
					"title" : "印刷序列号",
					"data" : "seqNumber",
					"width" : "12%",
				}, {
					"title" : "打印时间",
					"data" : "printTime",
					"width" : "20%",
				}, 
					 * { "title" : "回传一门式", "data" : "tranStatusWin", "render" :
					 * function(data, type, full, meta) { if ("00" == data) {
					 * return '<b class="warning">等待</b>'; } if ("01" == data) {
					 * return '<b class="normal">成功</b>'; } if ("02" == data) {
					 * return '<b class="danger">失败</b>'; } } },
					 {
					"title" : "回传不动产",
					"width" : "13%",
					"data" : "tranStatusBiz",
					"render" : function(data, type, full, meta) {
						if ("00" == data) {
							return '<b class="warning">等待</b>';
						}
						if ("01" == data) {
							return '<b class="normal">成功</b>';
						}
						if ("02" == data) {
							return '<b class="danger">失败</b>';
						}
					}
				}, {
					"title" : "证明扫描件",
					"data" : "certScanPath",
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
	selectBtn1();

}
var selectBtn1 = function() {
	alert("selectBtn1---->0");
	var table = $('.printTable').DataTable();
	$('.printTable tbody').on('click', 'tr', function() {
		alert("selectBtn1---->1");
		if ($(this).hasClass('selected')) {
			$(this).removeClass('selected');
			row = null;
			$('#printDetail').hide();
			$('#printResend').hide();

		} else {
			alert("selectBtn1---->2");
			table.$('tr.selected').removeClass('selected');
			$(this).addClass('selected');
			row = table.row(this).data();
			$('#printDetail').show();
			// if("01" != row.tranStatusBiz || "01" != row.tranStatusWin){
			if ("01" != row.tranStatusBiz) {
				$('#printResend').show();
			}
		}
	});
	$('#printDetail').click(
			function() {
				alert("printDetail点击事件");
				$("#recordId").val(row.recordId);
				$("#bizNumber").val(row.bizNumber);
				$("#certNumber").val(row.certNumber);
				$("#seqNumber").val(row.seqNumber);
				$("#printTime").val(row.printTime);
				$("#certScan").attr("src",
						ctx + "/print/getPic?picPath=" + row.certScanPath);
				$('.datatables').hide();
				$('.detailForm').show();
				$('.alert').hide();
				$('.alert > i').empty();
			});
	$('#printResend').click(function() {
		alert("printResend点击事件");
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
	$('#backTable').click(function() {
		alert("backTable点击事件");
		$('.detailForm').hide();
		$('.datatables').show();
		resetDetailForm();
		if (location.href.indexOf("?reload=true") < 0) {
			location.href += "?reload=true";
		}
		// 刷新页面
		location.href += "?reload=true";
	});
}
*/
/** ************************************后面加的结束************************************************************* */

var PrintTable = function () {

    var handleTable = function () {
        $('.printTable').DataTable(
            {
                "serverSide": true,
                "processing": true,
                "ordering": false,
//					"stateSave" : true,
                "destroy": true,
                "searching": false,
                "select": true,
                "lengthMenu": [10, 20, 30],
                "pageLength": 10,
                "ajax": {
                    "url": ctx + "/print/query",
                    "dataSrc": "data",
                    "data": function (page) {
                        var param = {};
                        param.draw = page.draw;
                        param.start = page.start;
                        param.length = page.length;
                        param.startTime = $("#startTime").val();
                        param.endTime = $("#endTime").val();
                        param.bizNumber = $("#bizNumber2").val();
                        param.certNumber = $("#certNumber2").val();
                        param.seqNumber = $("#seqNumber2").val();
                        param.printType = $("#printType2").val();
                        return param;
                    }
                },
                "columnDefs": [
                    {
                        "targets": [0]
                    },
                    {
                        "targets": [1]
                    },
                    {
                        "targets": [2]
                    },
                    {
                        "targets": [3]
                    },
                    {
                        "targets": [4]
                    },
                    {
                        "targets": [5]
                    },
                    {
                        "targets": [6],
                        "createdCell": function (td, cellData, rowData,
                                                 row, col) {
                            $(td).addClass("cell-status")
                        }
                    },
                    /*
                     * { "targets" : [ 6 ], "createdCell" : function(td,
                     * cellData, rowData, row, col) {
                     * $(td).addClass("cell-status") } },
                     */
                    {
                        "targets": [7]
                    }],
                "columns": [{
                    "title": "打印ID",
                    "data": "recordId",
                    "visible": false
                }, {
                    "title": "业务流水号",
                    "data": "bizNumber",
                    "width": "12%",
                }, {
                    "title": "不动产权证号",
                    "data": "certNumber",
                    "width": "40%",
                }, {
                    "title": "印刷序列号",
                    "data": "seqNumber",
                    "width": "12%",
                }, {
                    "title": "打印时间",
                    "data": "printTime",
                    "width": "18%",
                },
                    /*
                     * { "title" : "回传一门式", "data" : "tranStatusWin", "render" :
                     * function(data, type, full, meta) { if ("00" == data) {
                     * return '<b class="warning">等待</b>'; } if ("01" == data) {
                     * return '<b class="normal">成功</b>'; } if ("02" == data) {
                     * return '<b class="danger">失败</b>'; } } },
                     */
                    {
                        "title": "打印类型",
                        "data": "printType",
                        "width": "10%",
                    },{
                        "title": "回传不动产",
                        "width": "8%",
                        "data": "tranStatusBiz",
                        "render": function (data, type, full, meta) {
                            if ("00" == data) {
                                return '<b class="warning">等待</b>';
                            }
                            if ("01" == data) {
                                return '<b class="normal">成功</b>';
                            }
                            if ("02" == data) {
                                return '<b class="danger">失败</b>';
                            }
                        }
                    }, {
                        "title": "证明扫描件",
                        "data": "certScanPath",
                        "visible": false
                    }],
                "fnInitComplete": function () {
                    $('.dataTables_paginate').addClass(
                        "btn-group datatable-pagination");
                    $('.dataTables_paginate > a').wrapInner('<span />');
                    $('.dataTables_paginate > a:first-child').append(
                        '<i class="icon-chevron-left shaded"></i>');
                    $('.dataTables_paginate > a:last-child').append(
                        '<i class="icon-chevron-right shaded"></i>');
                },
                "drawCallback": function () {
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

    var selectBtn = function () {
        var row;
        var table = $('.printTable').DataTable();
        $('.printTable tbody').off('click', 'tr').on('click', 'tr', function () {
            if ($(this).hasClass('selected')) {
                $(this).removeClass('selected');
                row = null;
                $('#printDetail').hide();
                $('#printResend').hide();
            } else {
                table.$('tr.selected').removeClass('selected');
                $(this).addClass('selected');
                row = table.row(this).data();
                $('#printDetail').show();
                //2019.7.3 判断打印编号是否包含remedy 若包含则此条记录为补录，不能显示详情
                if (row.recordId.indexOf("remedy") == -1) {
                    $('#printDetail').show();
                } else {
                    $('#printDetail').hide();
                }
                // if("01" != row.tranStatusBiz || "01" !=
                // row.tranStatusWin){
                if ("01" != row.tranStatusBiz) {
                    $('#printResend').show();
                } else {
                    $('#printResend').hide();
                }
            }
        });
        $('#search').on('click', function () {
            var startTime = $("#startTime").val();
            var endTime = $("#endTime").val();
            if (startTime !== "") {
                if (endTime == "") {
                    string = "请输入结束日期!";
                    SetCss(string);
                    return false;
                }
            }
            if (endTime !== "") {
                if (startTime == "") {
                    string = "请输入开始日期!";
                    SetCss(string);
                    return false;

                }
            }
            table.ajax.reload().draw();
            $('#printResend').attr('style', 'display:none');
            $('#printDetail').attr('style', 'display:none');

            exportState = true;
        });
        $('#printDetail').click(
            function () {
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
        $('#printResend').click(function () {
            $.ajax({
                type: "POST",
                url: ctx + "/print/resend",
                data: {
                    recordId: row.recordId,
                },
                dataType: "json",
                success: function (data) {
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
        $('#backTable').click(function () {
            $('.detailForm').hide();
            $('.datatables').show();
            $('#printResend').attr('style', 'display:none');
            $('#printDetail').attr('style', 'display:none');
            resetDetailForm();
            /*
             * if(location.href.indexOf("?reload=true")<0){
             * location.href+="?reload=true"; }
             */
            // 刷新页面
            location.href += "?reload=true";
        });
    }

    return {
        init: function () {
            handleTable();
            selectBtn();
            // search();
            // selectBtn1();
        }
    };

}();

// 更新工本号按钮
$('#updateSeq').click(function () {

    // var reg = /\d{11}$/;
    var reg =/^\d{11}$/
    if (reg.test($("#seqNumber").val())) {
        $('#updateSeq').attr('disabled', 'disabled');
        $.ajax({
            type: "POST",
            url: ctx + "/print/updateSeqNumber",
            data: {
                seqNumber: $("#seqNumber").val(),
                recordId: $("#recordId").val(),
            },
            dataType: "json",
            success: function (data) {
                if (data.result) {
                    // table.ajax.reload().draw();
//					alert(data.result)
                    string = data.respMsg;
                    SetCss(string);
                } else {
//					alert(data.result)
                    string = data.respMsg;
                    SetCss(string);
                }
            }
        });
    } else {
        $('#updateSeq').attr('disabled', false);
//		alert("输入的工本号格式不对，请核对工本号");
        string = "输入的工本号格式不对，请核对工本号!";
        SetCss(string);

    }

});

//补录数据
var addPrintRecord = function () {

    $('.datatables').hide();
    $('.addDetailForm').show();

    //返回
    $('#back').click(function () {
        $('.addDetailForm').hide();
        $('.datatables').show();
        $('#printResend').attr('style', 'display:none');
        $('#printDetail').attr('style', 'display:none');
        resetAddDetailForm();
        $('.printTable').DataTable().ajax.reload().draw();
        $('.controls > span.help-inline').remove();
    });

    //重置
    $('#resetPrint').click(function () {
        resetAddDetailForm();
        $('.alert').hide();
        $('.alert > i').empty();
        $('.controls > span.help-inline').remove();
    });

    /*var resetPrint = function() {
        $("#bizNumberC").val(''),
        $("#certNumberC").val(''),
        $("#seqNumberC").val(''),
        $("#printTimeC").val(''),
        $("#userIdnumberC").val(''),
        $("#certScanPathC").val(''),
        $('.controls > span.help-inline').remove();
    }*/

    // 提交
    $("#submit").click(function () {
		$('#submit').attr("disabled",true);
        if ($("form.printEdit").valid()) {

            // 校验姓名
            var userFullname = $("#userFullname").val();
            if (userFullname.length < 2) {
//	            alert("请输入正确的领证人姓名!");
                string = "请输入正确的领证人姓名!!";
                SetCss(string);
				$('#submit').attr("disabled",false);
                return;
            }
            // 校验身份证号
            var userIdNumber = $("#userIdNumber").val();
            var CheckIdNum = /^[1-9]\d{7}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}$|^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}([0-9]|X)$/;
            if (CheckIdNum.test(userIdNumber) === false) {
//	            alert("请输入正确的领证人身份证号!");
                string = "请输入正确的领证人身份证号!";
                SetCss(string);
				$('#submit').attr("disabled",false);
                return;
            }
            // 校验不动产权证号
            var certNumber0 = $("#certNumber0").val();	// 鄂
            if (certNumber0.length != 1) {
//	            alert("请在不动产权证号的第一个文本框中输入正确的值!");
                string = "请在不动产权证号的第一个文本框中输入正确的值!";
                SetCss(string);
				$('#submit').attr("disabled",false);
                return;
            }
            var certNumber1 = $("#certNumber1").val();	// 2019
            if (certNumber1.length < 1) {
//	            alert("请在不动产权证号的第二个文本框中选择正确的年份!");
                string = "请在不动产权证号的第二个文本框中选择正确的年份!";
                SetCss(string);
				$('#submit').attr("disabled",false);
                return;
            }
            var certNumber4 = $("#certNumber4").val();	// 武汉市
            if (certNumber4.length < 1) {
//	            alert("请在不动产权证号的第三个文本框中输入正确的值!");
                string = "请在不动产权证号的第三个文本框中输入正确的值!";
                SetCss(string);
				$('#submit').attr("disabled",false);
                return;
            }
            var certNumber3 = $("#certNumber3").val();	// 7位数字
            var testCertNumber3 = /^\d{7}$/;
            if (testCertNumber3.test(certNumber3) === false) {
//	            alert("请在不动产权证号的第四个文本框中输入正确的值!");
                string = "请在不动产权证号的第四个文本框中输入正确的值!";
                SetCss(string);
				$('#submit').attr("disabled",false);
                return;
            }
            // 校验不动产单元号
            var bizNumber = $("#bizNumber1").val();

            // 校验证书编号
            var ocr = $("#ocr").val();	// 11位数字
            var testOcr = /^\d{11}$/;
            if (testOcr.test(ocr) === false) {
//	            alert("请输入正确的证书编号!");
                string = "请输入正确的证书编号!";
                SetCss(string);
				$('#submit').attr("disabled",false);
                return;
            }

			$.ajax({
				type : "POST",
				//这里访问的是Business中的方法
				url : "http://localhost:8080/Business/manage/resend",
				jsonp:"callback",
				dataType:"jsonp",
				data : {
					userFullname : $("#userFullname").val(),
					userIdNumber : $("#userIdNumber").val(),
					certNumber : $("#certNumber0").val()+"("+$("#certNumber1").val()+")"+$("#certNumber4").val()
						+"不动产权第"+$("#certNumber3").val()+"号",
					bizNumber : $("#bizNumber1").val(),
					ocr : $("#ocr").val(),
				},
				success : function(data) {
//					alert(data);
					if (data.result) {
						$('.alert').hide();
						$('.alert > i').empty();
						SetInsertCss(data.respMsg);

					} else {
						$('.alert').hide();
						$('.alert > i').empty();
						SetCss(data.respMsg);
					}
				}
			});
        }
    });

}