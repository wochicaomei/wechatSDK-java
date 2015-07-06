$(function() {
	$("._calendar").datepicker({
		format : 'yyyy-mm-dd'
	}).on("changeDate", function(e) {
		$(this).datepicker('hide');
	});

	$("._datetime").datetimepicker({
		format : 'yyyy-mm-dd hh:ii'
	}).on("changeDate", function(e) {
		$(this).datetimepicker('hide');
	});
});

function remoteServiceCall(service) {
	$("#loading_div").show();
	var headers = {};
	$.ajax({
		type : "POST",
		url : service.uri,
		data : {
			ref : str(service.ref),
			data : str(service.data)
		},
		headers : headers,
		dataType : "json",
		success : function(data, textStatus, jqXHR) {
			$("#loading_div").hide();
			callback(data);
		},
		error : function(jqXHR, textStatus, errorThrown) {
			$("#loading_div").hide();
		},
		beforeSend : function(jqXHR, settings) {

		},
		complete : function(jqXHR, textStatus) {
			$("#loading_div").hide();
		}
	});
}