/**
 * 填预览数据
 */
function fillEstate(i,flag) {
	var estate;
	if (flag) {
		estate = normalData[i];
	} else {
		estate = abnormalData[i];
	}
	$('#cert-province').empty();
	$('#cert-province').append(estate.certNumber.substring(0, 1));
	$('#cert-year').empty();
	$('#cert-year').append(estate.certNumber.substring(2, 6));
	$('#cert-city').empty();
	$('#cert-city').append(estate.certNumber.substring(7, 10));
	$('#cert-number').empty();
	$('#cert-number').append(estate.certNumber.substring(15, 22));
	$('#obligee').empty();
	$('#obligee').append(estate.obligee);
	$('#co_ownershipCircumstance').empty();
	$('#co_ownershipCircumstance').append(estate.co_ownershipCircumstance);
	$('#located').empty();
	$('#located').append(estate.located);
	$('#unitNumber').empty();
	$('#unitNumber').append(estate.unitNumber);
	$('#rightTypes').empty();
	$('#rightTypes').append(estate.rightTypes);
	$('#rightNature').empty();
	$('#rightNature').append(estate.rightNature);
	$('#application').empty();
	$('#application').append(estate.application);
	$('#area').empty();
	$('#area').append(estate.area);
	$('#serviceLife').empty();
	$('#serviceLife').append(estate.serviceLife);
	$('#other').empty();
	$('#other').append(estate.other);
	$('#excursus').empty();
	$('#excursus').append(estate.excursus);
	$('#img_zongditu').empty();
	$('#img_zongditu').attr('src', 'data:image/png;base64,' + estate.zongditu);
	$('#img_fenbutu').empty();
	$('#img_fenbutu').attr('src', 'data:image/png;base64,' + estate.fenbutu);
}

/**
 * 往右翻页
 */
var turnRight = function() {
	$('#turn_left_img').attr('src', ctxResources + '/images/qzs_img/yl_leftfanye.png');
	$('#turn_left_img').attr('onclick', 'turnLeft()');
	$('#turn_right_img').attr('src',
			ctxResources + '/images/qzs_img/yl_rightwuxiao.png');
	$('#turn_right_img').attr('onclick', '');
	$('#preview_first').attr('style', 'display:none');
	$('#preview_second').attr('style', '');
};

/**
 * 往左翻页
 */
var turnLeft = function() {
	$('#turn_left_img').attr('src', ctxResources + '/images/qzs_img/yl_leftwuxiao.png');
	$('#turn_left_img').attr('onclick', '');
	$('#turn_right_img')
			.attr('src', ctxResources + '/images/qzs_img/yl_rightfanye.png');
	$('#turn_right_img').attr('onclick', 'turnRight()');
	$('#preview_first').attr('style', '');
	$('#preview_second').attr('style', 'display:none');
};

/**
 * 取消预览（点击右上角X）
 */
var preview_cancel = function() {
	$('#print_preview').attr('style', "display: none;");
	$('#print_preview_background').attr('style', "display: none;");
};