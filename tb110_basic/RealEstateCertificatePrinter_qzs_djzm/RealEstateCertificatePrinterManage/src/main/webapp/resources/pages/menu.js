var Menu = function() {
	
	var buildMenu = function(json) {
		var menus = eval('(' + json + ')');
		var html = '';
		if (menus.length > 0) {
			html += '<ul class="widget widget-menu unstyled">'
			for (var mi = 0; mi < menus.length; mi++) {
				var menu = menus[mi];
				html += '<li><a class="collapsed" data-toggle="collapse" href="#'
						+ menu.menuId + '">';
				html += '<i class="menu-icon ' + menu.menuIconCss + '"></i>';
				html += '<i class="icon-chevron-down pull-right"></i><i class="icon-chevron-up pull-right"></i>';
				html += menu.menuName + '</a>';
				var pages = menu.subordinateMenus;
				if (pages.length > 0) {
					html += '<ul id="' + menu.menuId
							+ '" class="collapse unstyled">';
					for (var pi = 0; pi < pages.length; pi++) {
						var page = pages[pi];
						html += '<li><a href="' + ctx + page.menuUrl + '">';
						html += '<i class="icon-inbox"></i>';
						html += page.menuName + '</a></li>';
					}
					html += '</ul>';
				}
				html += '</li>';
			}
			html += '</ul>';
		}
		$("#menubar").append(html);
	}
	
	return {
		init : function(json) {
			return buildMenu(json);
		}
	};
}();