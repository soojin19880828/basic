// 页面加载完成后执行
$(document).ready(function() {
	query_table_info();
});
// 正常数据
var normalData;
// 异常数据
var abnormalData;
// 打印剩余数
var selectedLength;
// 需要打印的总数量
var sum;
// 选中的数据
var selected = [];

/**
 * 查询证书信息
 */
var query_table_info = function() {
	$.ajax({
		type : 'post',
		url : ctx + '/business/query',
		dataType : 'json',
		data : {
			recordId : $('#recordId').val()
		},
		success : function(data) {
			if (data.result) {
				// clientScript.startCount(300);
				$('#promptInfo').hide();
				$('#dataList').show();
				normalData = data.normalRealEstateInfo;
				abnormalData = data.abnormalRealEstateInfo;
				sum = normalData.length;
				if (sum > 0) {
					$('#print_all_btn').show()
					$('.normalData_btn').show();
					put_table_html(normalData, true);
					if (abnormalData.length > 0) {
						$('.abnormalData_btn').show();
					}
				} else {
					$('.abnormalData_btn').show();
					put_table_html(abnormalData, false);
				}
			} else {
				// clientScript.startCount(10);
				$('#promptInfo font').empty();
				$('#promptInfo font').append(data.exceptionDetail);
			}
		}
	});
};

/**
 * 向table中写入数据
 * 
 * @param list
 * @param flag(true:正常数据,false:异常数据)
 * 
 */
var put_table_html = function(list, flag) {
	// 将body清空
	$("#body").empty();
	var item;
	$
			.each(
					list,
					function(i, RealEstateInfo) {
						var flg;
						// 异常数据默认不显示勾选按钮
						flg = flag ? "" : "style='display:none'";
						item = "<tr class='table_tr' style='position:relative'>"
								+ "<td style='width: 100px'><input name='print_checkbox' type='checkbox' class='checkbox' value='"
								+ i
								+ " ' onclick='check_data()' "
								+ flg
								+ "></td>"
								+ "<td style='width: 732px'>"
								+ RealEstateInfo.located + "</td>";
						item += "<td style='width: 100px'><button " + flg + "class='preview preview_text' onclick='preview($(this),"
								+ flag + ")' value='" + i + "'></button></td>";
						item += "</tr>";
						$("#body").append(item);
					});
};

/**
 * 选中数据将其放入selected中
 */
var check_data = function() {
	selected = [];
	$('input[name="print_checkbox"]:checked').each(function() {
		var i = parseInt($(this).val());
		selected.push(normalData[i]);
	});
	if (selected.length) {
		// 显示"打印选中"按钮
		$('#print_selected_btn').show();
		$('#print_all_btn').hide();
	} else {
		// 隐藏"打印选中"按钮
		$('#print_selected_btn').hide();
		$('#print_all_btn').show();
	}
};

/**
 * 正常数据
 */
var normalData_btn = function() {
	$(".preview").show();
	$('#print_selected_btn').hide();
	$('#print_all_btn').show();
	put_table_html(normalData, true);
}

/**
 * 异常数据
 */
var abnormalData_btn = function() {
	$(".preview").hide();
	$('#print_selected_btn').hide();
	$('#print_all_btn').hide()
	put_table_html(abnormalData, false);
}

/**
 * 预览
 */
var preview = function(btn, flag) {
	$('#print_preview_background').attr('style', "");
	$('#print_preview').attr('style',
			"display: inline;position:absolute;z-index:100");
	fillEstate(btn.val(), flag);
};

/**
 * 打印选中证件
 */
var print_selected = function() {
	selectedLength = selected.length;
	clientScript.stopCount();
	$('#dataList').empty();
	$('#dataList').hide();
	$('.normalData_btn').hide();
	$('.abnormalData_btn').hide();
	$('#print_selected_btn').attr('style', "display: none;");
	$('#print_all_btn').attr('style', "display: none;");
	showPrinting();
	print_cert(selected);
};

/**
 * 打印全部
 */
var print_all = function() {
	selectedLength = sum;
	clientScript.stopCount();
	$('#dataList').empty();
	$('#dataList').hide();
	$('.normalData_btn').hide();
	$('.abnormalData_btn').hide();
	$('#print_selected_btn').attr('style', "display: none;");
	$('#print_all_btn').attr('style', "display: none;");
	showPrinting();
	print_cert(normalData);
};

/**
 * 打印的数据
 */
var print_cert = function(data) {
	var json_data = JSON.stringify(data);
	clientScript.qzsPrint(json_data);
};

/**
 * 显示正在打印
 */
var showPrinting = function() {
	$('#printing').attr('style',
			"display: inline;position:absolute;z-index:100");
};

/** 开始打印，变更提示 */
var doPrint = function(f) {
	$('#print_text').empty();
	$('#print_text').append('正在打印第' + f + '份不动产权证书...');
}

// 打印异常
var printError = function (msg) {
	$('#promptInfo').empty();
	$('#promptInfo').append(msg);
	clientScript.stopCountDown();
}

/** 打印进度变更提示 */
var showPrintInfo = function(processInfo) {
	$('#print_progress').empty();
	$('#print_progress').append(processInfo);
}

/** 打印完成 */
var printComplete = function() {
	$.ajax({
		type : 'post',
		url : ctx + "/business/printComplete",
		dataType : 'json',
		data : {
			recordId : $('#recordId').val()
		},
		success : function(data) {
			if (data.result) {
				selectedLength--;
				if (selectedLength <= 0) {
					clientScript.startCount(10);
					$('#printing').hide();
					$('#printComplete').show();
				}
			}
		}
	});
}