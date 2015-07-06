package com.bbeerr.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class BlogController {

	// 博客列表
	@RequestMapping(value = "/{usercode}/blog", method = RequestMethod.GET)
	public ModelAndView blogList(@PathVariable("usercode") String usercode) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("redirect:/" + usercode + "/blog/1");
		return mav;
	}

	// 博客列表
	@RequestMapping(value = "/{usercode}/blog/{page}", method = RequestMethod.GET)
	public ModelAndView blogList(@PathVariable("usercode") String usercode, @PathVariable("page") int page) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/web/blog/list");
		return mav;
	}

	// 博客详情
	@RequestMapping(value = "/{usercode}/blog/{blogNo}.html", method = RequestMethod.GET)
	public ModelAndView blogDetail(@PathVariable("usercode") String usercode, @PathVariable("blogNo") String blogNo) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/web/blog/detail");
		return mav;
	}

	// 博客修改
	@RequestMapping(value = "/{usercode}/blog/update/{blogNo}", method = RequestMethod.GET)
	public ModelAndView blogUpdate(@PathVariable("usercode") String usercode, @PathVariable("blogNo") String blogNo) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/web/blog/update");
		return mav;
	}

	// 博客创建
	@RequestMapping(value = "/{usercode}/blog/create", method = RequestMethod.GET)
	public ModelAndView blogCreate(@PathVariable("usercode") String usercode) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/web/blog/create");
		return mav;
	}

}