var chatroomService = {

	createChatRoom : function(data) {
		remoteServiceCall({
			uri : contextPath + '/admin/data/chatroom/create',
			ref : {
				callback : 'afCreateChatRoom'
			},
			data : data
		});
	},

	updateChatRoom : function(data, afDone) {
		remoteServiceCall({
			uri : contextPath + '/admin/data/chatroom/update/' + data.id,
			ref : {
				callback : afDone
			},
			data : data
		});
	},

	listChatRoom : function(target, pageNo) {
		remoteServiceCall({
			uri : contextPath + '/admin/data/chatroom/list/' + pageNo + window.location.search,
			ref : {
				target : target,
				callback : 'afListChatRoom'
			},
			data : {
				$sort : {
					hot : 1,// 按照热度排序优先
					name : 1
				}
			}
		});
	},

	deleteChatRoom : function(id, afDone) {
		remoteServiceCall({
			uri : contextPath + '/admin/data/chatroom/delete/' + id,
			ref : {
				callback : afDone
			},
			data : {}
		});
	},

	findChatRoom : function(id, afDone) {
		remoteServiceCall({
			uri : contextPath + '/admin/data/chatroom/find/' + id,
			ref : {
				callback : afDone
			},
			data : {}
		});
	},

	changeChatRoom : function(data, afDone) {
		remoteServiceCall({
			uri : contextPath + '/admin/data/chatroom/change',
			ref : {
				callback : afDone
			},
			data : data
		});
	}

};

$(function() {

	$(".dropdown").eq(2).addClass("active_drop");

	$("#btn_cancel").click(function() {
		location.href = contextPath + "/admin/chatroom/list/1";
	});

	$("#btn_save").click(function() {
		var obj = getFormInput("#form1");
		chatroomService.createChatRoom(obj);
	});

	$("#btn_update").click(function() {
		var obj = getFormInput("#form1");
		obj.id = update_id;
		chatroomService.updateChatRoom(obj, "afUpdateChatRoom");
	});

	$("#btn_search").click(function() {
		var queryStr = $("#form_search").serialize();
		location.href = contextPath + "/admin/chatroom/list/1?" + queryStr;
	});

	$("#btn_delete").click(function() {
		chatroomService.deleteChatRoom($("#delete_confirm").data("id"), "reloadPage");
	});

});

var hotJson = {
	1 : "最热",
	2 : "热",
	3 : "中",
	4 : "低"
}

function load(page) {
	chatroomService.listChatRoom("#table_chatroom", page);
}

function afListChatRoom(ref, data, page) {
	$(ref.target + " .datagrid_row").remove();
	for (var i = 0; i < data.length; i++) {
		fillDatagrid(ref.target, data[i]);
	}
	$(ref.target + "PageCtrl").html(pageCtrl(page.count, "/admin/chatroom/list/"));
}

function nameConverter(o) {
	return "<a href='" + contextPath + "/admin/chatroom/update/" + o.id + "'>" + o.name + "</a>";
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
		chatroomService.updateChatRoom(obj, "");
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

function afCreateChatRoom(ref, data) {
	location.href = contextPath + "/admin/chatroom/list/1";
}

function afUpdateChatRoom(ref, data) {
	location.href = contextPath + "/admin/chatroom/list/1";
}

function reloadPage(ref, data) {
	window.location.reload();
}