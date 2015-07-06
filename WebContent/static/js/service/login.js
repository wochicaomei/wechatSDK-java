var mobile_reg = /^1[1-9][0-9]\d{8}$/;

var loginService = {
	login : function(data) {
		remoteServiceCall({
			uri : contextPath + '/data/user/login',
			ref : {
				callback : 'afLogin'
			},
			data : data
		});
	}
};

$(function() {

	$("#login_username").blur(function() {
		if ($(this).val() != '') {
			if (!mobile_reg.test($(this).val())) {
				$(this).next().html("请输入正确的手机号码!");
				return;
			}
		}
	});

	$("#login_username").keyup(function() {
		$(this).next().html("&nbsp;");
	});

	$("#btn_login_confirm").click(function() {
		if ($("#login_username").val() != '') {
			if (!mobile_reg.test($("#login_username").val())) {
				$("#login_username").next().html("请输入正确的手机号码!");
				return;
			}
		} else {
			$("#login_username").next().html("请输入手机号码");
			return;
		}

		if ($("#login_password").val() == '') {
			$("#login_password").next().html("请输入密码");
			return;
		}

		var json = {
			username : $("#login_username").val(),
			password : $("#login_password").val()
		};
		loginService.login(json);
	});

});

function afLogin(ref, data) {
	if (data.login == 'success') {
		window.location.reload();
	} else {
		$("#login_error").html("用户名或密码错误");
	}
}