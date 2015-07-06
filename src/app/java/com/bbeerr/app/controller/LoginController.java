package com.bbeerr.app.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import org.springframework.web.servlet.ModelAndView;

import com.bbeerr.app.service.IUserService;
import com.bbeerr.core.db.entity.AbstractPo;
import com.bbeerr.core.db.entity.User;

@Controller
public class LoginController {

	@Autowired
	IUserService userService;

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView login(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/web/login");
		return mav;
	}

	@Autowired
	@Qualifier("org.springframework.security.authenticationManager")
	protected AuthenticationManager authenticationManager;

	// 注册Post(若成功，则自动登录)
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ModelAndView doRegister(User user, HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("user", user);

		try {
			user = userService.register(user);
			request.setAttribute("newRegister", "yes");
			mav.setViewName("redirect:/index");

			// 成功，自动登录
			UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getRepassword());
			try {
				token.setDetails(new WebAuthenticationDetails(request));
				Authentication authenticatedUser = authenticationManager.authenticate(token);
				SecurityContextHolder.getContext().setAuthentication(authenticatedUser);
				request.getSession().setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, SecurityContextHolder.getContext());

				// 登录成功后用户名写入cookie
				Cookie cookie = new Cookie("my_account", AbstractPo.getPassport().getUsername());
				cookie.setMaxAge((int) Math.pow(2, 31));// 永久？
				cookie.setPath("/");
				response.addCookie(cookie);
			} catch (AuthenticationException e) {
				System.out.println("Authentication Failed: " + e.getMessage());
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return mav;
	}

}