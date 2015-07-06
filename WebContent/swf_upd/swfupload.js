var swfupload = {

	prepare : function(refid, upid, filename) {
		$("#" + refid).data("upid", upid);
		$("#" + refid).data("filename", filename);
	},

	progress : function(refid, l, t) {

	},

	success : function(refid) {

	},

	error : function(refid, msg) {
		alert(msg);
	}

};