package com.pccw.ott.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class OttMainController {
	
	private static Logger logger = LoggerFactory.getLogger(OttMainController.class);
	
	@RequestMapping("/main.html")
	public ModelAndView main(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView("main");
		mv.addObject("hasUserPermission", "Y");
		mv.addObject("hasRolePermission", "Y");
		mv.addObject("hasPermission", "Y");
		mv.addObject("hasAuditTrailPermission", "Y");
		return mv;
	}

}
