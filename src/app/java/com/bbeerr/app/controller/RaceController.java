package com.bbeerr.app.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import com.sun.xml.internal.ws.resources.HttpserverMessages;

@Controller
public class RaceController {

	@RequestMapping(value = "/race", method = RequestMethod.GET)
	public ModelAndView race(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/web/race");
		return mav;
	}

	@RequestMapping(value = "/race/{category}", method = RequestMethod.GET)
	public ModelAndView raceCategory(HttpServletRequest request, HttpServletResponse response, @PathVariable("category") int category) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/web/raceCategory");
		return mav;
	}

	@RequestMapping(value = "/race/{category}/{id}", method = RequestMethod.GET)
	public ModelAndView raceDetail(HttpServletRequest request, HttpServletResponse response, @PathVariable("category") int category, @PathVariable("id") int id) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/web/raceDetail");
		return mav;
	}
	
	@RequestMapping(value = "/detail", method = RequestMethod.GET)
	public ModelAndView raceDetail(HttpServletRequest request, HttpServletResponse response){
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/web/detail");
		return mav;
	}

}