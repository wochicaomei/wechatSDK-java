package com.bbeerr.app.servicecall;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bbeerr.app.service.IUserService;
import com.bbeerr.core.db.entity.AbstractPo;
import com.bbeerr.core.service.ServiceCall;

@Controller
public class UserServiceCall {

	@Autowired
	IUserService service;

	@RequestMapping(value = "/data/user/check", method = RequestMethod.POST)
	public @ResponseBody ServiceCall checkUser(@RequestParam(value = "ref", required = false, defaultValue = "{}") String ref,
			@RequestParam(value = "data", required = false, defaultValue = "{}") String data) {

		ServiceCall serviceCall = new ServiceCall(ref, data);
		JSONObject jsonData = JSONObject.fromObject(data);

		Object retVal = null;
		if (jsonData.containsKey("username")) {
			retVal = service.findUserByUsername(jsonData.getString("username"));
		}
		serviceCall.setData(retVal);

		serviceCall.setMessage("success");
		return serviceCall;
	}

	@Autowired
	@Qualifier("org.springframework.security.authenticationManager")
	protected AuthenticationManager authenticationManager;

	// AJAX方式登录
	@RequestMapping(value = "/data/user/login", method = RequestMethod.POST)
	public @ResponseBody ServiceCall loginUser(@RequestParam(value = "ref", required = false, defaultValue = "{}") String ref,
			@RequestParam(value = "data", required = false, defaultValue = "{}") String data, HttpServletRequest request, HttpServletResponse response) {
		ServiceCall serviceCall = new ServiceCall(ref, data);

		JSONObject json = JSONObject.fromObject(data);
		String username = json.getString("username");
		String password = json.getString("password");

		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);
		try {
			token.setDetails(new WebAuthenticationDetails(request));
			Authentication authenticatedUser = authenticationManager.authenticate(token);
			SecurityContextHolder.getContext().setAuthentication(authenticatedUser);
			request.getSession().setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, SecurityContextHolder.getContext());

			Cookie cookie = new Cookie("my_account", AbstractPo.getPassport().getUsername());
			cookie.setMaxAge((int) Math.pow(2, 31));// 永久？
			cookie.setPath("/");
			response.addCookie(cookie);

			json.put("login", "success");
		} catch (AuthenticationException e) {
			json.put("login", "error");
		}

		serviceCall.setData(json);
		serviceCall.setMessage("success");
		return serviceCall;
	}

}