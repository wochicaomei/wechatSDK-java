var navbar = {
	menu : [ {
		name : "分类管理",
		href : contextPath + "/admin/category/list/1"
	}, {
		name : "视频管理",
		href : contextPath + "/admin/video/list/1"
	}, {
		name : "聊天室管理",
		href : contextPath + "/admin/chatroom/list/1"
	} ]
};

function createNav($obj) {
	for ( var m in navbar.menu) {
		var menu = navbar.menu[m];
		var $menu = $("<li>");
		$menu.addClass("dropdown");
		if (menu.items && menu.items.length > 0) {
			$menu.append($('<a class="dropdown-toggle" data-toggle="dropdown" href="' + menu.href + '">' + menu.name + '<span class="caret"></span></a>'));
			var $sub = $("<ul class='dropdown-menu' role='menu'>");
			$menu.append($sub);
			for (var i = 0; i < menu.items.length; i++) {
				var $item = $("<li>");
				$sub.append($item);

				if (menu.items[i].name == "#divider") {
					$item.addClass("divider");
				} else {
					var $a = $("<a>", {
						href : menu.items[i].href,
						html : menu.items[i].name
					});
					$item.append($a);
				}
			}
		} else {
			var $a = $("<a>", {
				href : menu.href,
				html : menu.name
			});
			$menu.append($a);
		}
		$obj.append($menu);
	}
}

$(function() {
	$("#navbar").html("");
	createNav($("#navbar"));
});