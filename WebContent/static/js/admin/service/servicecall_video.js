var videoService = {

	createVideo : function(data) {
		remoteServiceCall({
			uri : contextPath + '/admin/data/video/create',
			ref : {
				callback : 'afCreateVideo'
			},
			data : data
		});
	},

	updateVideo : function(data, afDone) {
		remoteServiceCall({
			uri : contextPath + '/admin/data/video/update/' + data.id,
			ref : {
				callback : afDone
			},
			data : data
		});
	},

	listVideo : function(target, pageNo) {
		remoteServiceCall({
			uri : contextPath + '/admin/data/video/list/' + pageNo + window.location.search,
			ref : {
				target : target,
				callback : 'afListVideo'
			},
			data : {
				$sort : {
					categoryId : 1,// 分类
					hot : 1,// 按照热度排序优先
					name : 1
				}
			}
		});
	},

	deleteVideo : function(id, afDone) {
		remoteServiceCall({
			uri : contextPath + '/admin/data/video/delete/' + id,
			ref : {
				callback : afDone
			},
			data : {}
		});
	},

	findVideo : function(id, afDone) {
		remoteServiceCall({
			uri : contextPath + '/admin/data/video/find/' + id,
			ref : {
				callback : afDone
			},
			data : {}
		});
	},

	changeVideo : function(data, afDone) {
		remoteServiceCall({
			uri : contextPath + '/admin/data/video/change',
			ref : {
				callback : afDone
			},
			data : data
		});
	},

	listCategory : function() {
		remoteServiceCall({
			uri : contextPath + '/admin/data/category/list/0',
			ref : {
				callback : 'afListCategory'
			},
			data : {
				$sort : {
					hot : 1,// 按照热度排序优先
					name : 1
				}
			}
		});
	}

};

$(function() {

	$(".dropdown").eq(1).addClass("active_drop");

	$("#btn_cancel").click(function() {
		location.href = contextPath + "/admin/video/list/1";
	});

	$("#btn_save").click(function() {
		var obj = getFormInput("#form1");
		videoService.createVideo(obj);
	});

	$("#btn_update").click(function() {
		var obj = getFormInput("#form1");
		obj.id = update_id;
		videoService.updateVideo(obj, "afUpdateVideo");
	});

	$("#btn_search").click(function() {
		var queryStr = $("#form_search").serialize();
		location.href = contextPath + "/admin/video/list/1?" + queryStr;
	});

	$("#btn_delete").click(function() {
		videoService.deleteVideo($("#delete_confirm").data("id"), "reloadPage");
	});

});

var categoryId = null;

function afListCategory(ref, data) {
	for (var i = 0; i < data.length; i++) {
		var $option = $("<option>", {
			value : data[i].id,
			html : data[i].name
		});
		$("#categoryId").append($option);
	}

	if (categoryId != null) {
		$("#categoryId").val(categoryId);
	}
}

var hotJson = {
	1 : "最热",
	2 : "热",
	3 : "中",
	4 : "低"
}

function load(page) {
	videoService.listVideo("#table_video", page);
}

function afListVideo(ref, data, page) {
	$(ref.target + " .datagrid_row").remove();
	for (var i = 0; i < data.length; i++) {
		fillDatagrid(ref.target, data[i]);
	}
	$(ref.target + "PageCtrl").html(pageCtrl(page.count, "/admin/video/list/"));
}

function nameConverter(o) {
	return "<a href='" + contextPath + "/admin/video/update/" + o.id + "'>" + o.name + "</a>";
}

function hotConverter(o) {
	return hotJson[o.hot];
}

function isShowConverter(o) {
	var $input = $("<input>", {
		type : 'checkbox',
	});
	$input.data("id", o.id);
	if (o.isShow == 1) {
		$input.attr("checked", "checked");
	}
	$input.click(function() {
		var id = $(this).data("id");
		var isShow = 0;
		if ($(this).prop("checked")) {
			isShow = 1;
		}

		var obj = {
			id : id,
			isShow : isShow
		};
		videoService.updateVideo(obj, "");
	});

	return $input;
}

function _actionConverter(o) {
	var $btn_del = $("<button>");
	$btn_del.addClass("btn");
	$btn_del.addClass("btn-danger");
	$btn_del.addClass("btn-sm");
	$btn_del.html("删除");
	$btn_del.data("id", o.id);
	$btn_del.click(function() {
		var id = $(this).data("id");
		$("#delete_confirm").data("id", id);
		$("#delete_confirm").modal("show");
	});

	var $div = $("<div>");
	$div.append($btn_del);
	return $div;
}

function afCreateVideo(ref, data) {
	location.href = contextPath + "/admin/video/list/1";
}

function afUpdateVideo(ref, data) {
	location.href = contextPath + "/admin/video/list/1";
}

function reloadPage(ref, data) {
	window.location.reload();
}