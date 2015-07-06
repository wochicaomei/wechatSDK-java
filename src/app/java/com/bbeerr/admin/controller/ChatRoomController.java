package com.bbeerr.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.bbeerr.app.form.query.ChatRoomQueryForm;
import com.bbeerr.app.service.IChatRoomService;

@Controller
@RequestMapping(value = "/admin")
public class ChatRoomController {

	@Autowired
	IChatRoomService service;

	@RequestMapping(value = "/chatroom/create", method = RequestMethod.GET)
	public ModelAndView create(ModelMap model) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/admin/chatRoom/create");
		return mav;
	}

	@RequestMapping(value = "/chatroom/update/{id}", method = RequestMethod.GET)
	public ModelAndView update(ModelMap model, @PathVariable("id") int id) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("chatRoomId", id);
		mav.setViewName("/admin/chatRoom/update");
		return mav;
	}

	@RequestMapping(value = "/chatroom/list/{currentPage}", method = RequestMethod.GET)
	public ModelAndView list(ModelMap model, @PathVariable("currentPage") int currentPage, @ModelAttribute("queryForm") ChatRoomQueryForm queryForm) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("currentPage", currentPage);
		mav.addObject("queryForm", queryForm);
		mav.setViewName("/admin/chatRoom/list");
		return mav;
	}

}