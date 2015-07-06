package com.bbeerr.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.bbeerr.app.form.query.VideoQueryForm;
import com.bbeerr.app.service.IVideoService;

@Controller
@RequestMapping(value = "/admin")
public class VideoController {

	@Autowired
	IVideoService service;

	@RequestMapping(value = "/video/create", method = RequestMethod.GET)
	public ModelAndView create(ModelMap model) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/admin/video/create");
		return mav;
	}

	@RequestMapping(value = "/video/update/{id}", method = RequestMethod.GET)
	public ModelAndView update(ModelMap model, @PathVariable("id") int id) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("videoId", id);
		mav.setViewName("/admin/video/update");
		return mav;
	}

	@RequestMapping(value = "/video/list/{currentPage}", method = RequestMethod.GET)
	public ModelAndView list(ModelMap model, @PathVariable("currentPage") int currentPage, @ModelAttribute("queryForm") VideoQueryForm queryForm) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("currentPage", currentPage);
		mav.addObject("queryForm", queryForm);
		mav.setViewName("/admin/video/list");
		return mav;
	}

}