var categoryService = {
	listCategory : function(target, pageNo, data, afDone) {
		if (afDone == null || afDone == '') {
			afDone = 'afListCategory';
		}
		remoteServiceCall({
			uri : contextPath + '/data/category/list/' + pageNo,
			ref : {
				target : target,
				callback : afDone
			},
			data : data
		});
	}
};

var categoryData = {
	$criteria : {

	},
	$sort : {
		hot : 1,
		name : 1
	}
}

function afListCategory(ref, data) {
	for (var i = 0; i < data.length; i++) {
		var $div = $("<div>");
		$div.addClass("category_div")

		var $img = $("<img>", {
			src : contextPath + '/static/css/image/main/test_hot.png',
			width : 323,
			height : 198
		});
		$div.append($img);

		var $img_play = $("<img>", {
			src : contextPath + '/static/css/image/main/icon_play64x64.png',
			width : 64,
			height : 64
		});
		var $a_play = $("<a>", {
			href : contextPath + '/category/' + data[i].id,
			target : '_blank'
		});
		$a_play.append($img_play)
		$a_play.addClass("img_play");
		$div.append($a_play);

		var $img_comment = $("<img>", {
			src : contextPath + '/static/css/image/main/icon_comment.png',
			width : 14,
			height : 12
		})
		var $img_like = $("<img>", {
			src : contextPath + '/static/css/image/main/icon_like.png',
			width : 14,
			height : 11
		})
		var $div_botton = $("<div>");
		$div_botton.addClass("black_div");
		$div_botton.append($img_comment).append("&nbsp;").append(1234);
		$div_botton.append($img_like).append("&nbsp;").append(1234);
		$div.append($div_botton);

		var $li = $("<li>");
		$li.append($div);
		$(ref.target).append($li);
	}
}