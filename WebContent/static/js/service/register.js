var mobile_reg = /^1[1-9][0-9]\d{8}$/;

var registerService = {
	checkUser : function(data, afDone) {
		remoteServiceCall({
			uri : contextPath + '/data/user/check',
			ref : {
				callback : afDone
			},
			data : data
		});
	}
};

$(function() {

	$("#username").blur(function() {
		if ($(this).val() != '') {
			if (!mobile_reg.test($(this).val())) {
				$(this).next().html("请输入正确的手机号码!");
				return;
			}

			var obj = {
				username : $(this).val()
			};
			registerService.checkUser(obj, "afCheckUser");
		}
	});

	$("#username").keyup(function() {
		$(this).next().html("&nbsp;");
		$("#btn_register_confirm").attr("disabled", "disabled");
	});

	$(".password").blur(function() {
		if ($("#password1").val() != '' && $("#password2").val() != '') {
			if ($("#password1").val() != $("#password2").val()) {
				$("#password2").next().html("两次输入的密码不一致!");
				return;
			}
		}
	});

	$("#password1").keyup(function() {
		$("#password2").next().html("&nbsp;");
	});

	$("#password2").keyup(function() {
		$("#password2").next().html("&nbsp;");
	});

	$("#btn_register_confirm").click(function() {
		if ($("#username").val() != '') {
			if (!mobile_reg.test($("#username").val())) {
				$("#username").next().html("请输入正确的手机号码!");
				return;
			}
		} else {
			$("#username").next().html("请输入手机号码");
			return;
		}

		if ($("#password1").val() == '' || $("#password2").val() == '') {
			$("#password2").next().html("请输入密码");
			return;
		} else {
			if ($("#password1").val() != $("#password2").val()) {
				$("#password2").next().html("两次输入的密码不一致!");
				return;
			}
		}

		$("#form_register").submit();
	});

});

function afCheckUser(ref, data) {
	if (data != null) {
		$("#username").next().html("该手机号码已存在!");
		$("#btn_register_confirm").attr("disabled", "disabled");
	} else {
		$("#username").next().html("&nbsp;");
		$("#btn_register_confirm").removeAttr("disabled");
	}
}