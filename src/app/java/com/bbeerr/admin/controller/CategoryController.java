package com.bbeerr.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.bbeerr.app.form.query.CategoryQueryForm;
import com.bbeerr.app.service.ICategoryService;

@Controller
@RequestMapping(value = "/admin")
public class CategoryController {

	@Autowired
	ICategoryService service;

	@RequestMapping(value = "/category/create", method = RequestMethod.GET)
	public ModelAndView create(ModelMap model) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/admin/category/create");
		return mav;
	}

	@RequestMapping(value = "/category/update/{id}", method = RequestMethod.GET)
	public ModelAndView update(ModelMap model, @PathVariable("id") int id) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("categoryId", id);
		mav.setViewName("/admin/category/update");
		return mav;
	}

	@RequestMapping(value = "/category/list/{currentPage}", method = RequestMethod.GET)
	public ModelAndView list(ModelMap model, @PathVariable("currentPage") int currentPage, @ModelAttribute("queryForm") CategoryQueryForm queryForm) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("currentPage", currentPage);
		mav.addObject("queryForm", queryForm);
		mav.setViewName("/admin/category/list");
		return mav;
	}

}