package com.bbeerr.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserController {

	// 用户设置
	@RequestMapping(value = "/{usercode}/setting", method = RequestMethod.GET)
	public ModelAndView Setting(@PathVariable("usercode") String usercode) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/web/user/setting");
		return mav;
	}

	// 用户粉丝
	@RequestMapping(value = "/{usercode}/fans", method = RequestMethod.GET)
	public ModelAndView Fans(@PathVariable("usercode") String usercode) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("redirect:/" + usercode + "/fans/1");
		return mav;
	}

	// 用户粉丝
	@RequestMapping(value = "/{usercode}/fans/{page}", method = RequestMethod.GET)
	public ModelAndView Fans(@PathVariable("usercode") String usercode, @PathVariable("page") int page) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/web/user/fans");
		return mav;
	}

	// 用户关注
	@RequestMapping(value = "/{usercode}/follow", method = RequestMethod.GET)
	public ModelAndView Follow(@PathVariable("usercode") String usercode) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("redirect:/" + usercode + "/follow/1");
		return mav;
	}

	// 用户关注
	@RequestMapping(value = "/{usercode}/follow/{page}", method = RequestMethod.GET)
	public ModelAndView Follow(@PathVariable("usercode") String usercode, @PathVariable("page") int page) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/web/user/follow");
		return mav;
	}

	// 用户关注
	@RequestMapping(value = "/{usercode}/comment", method = RequestMethod.GET)
	public ModelAndView Comment(@PathVariable("usercode") String usercode) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("redirect:/" + usercode + "/comment/1");
		return mav;
	}

	// 用户评论
	@RequestMapping(value = "/{usercode}/comment/{page}", method = RequestMethod.GET)
	public ModelAndView Comment(@PathVariable("usercode") String usercode, @PathVariable("page") int page) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/web/user/comment");
		return mav;
	}

}