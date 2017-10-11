package com.pccw.ott.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/npvr")
public class OttNpvrController {

	@RequestMapping("/goToNpvrMappingPage.html")
	public ModelAndView goToNpvrMappingPage() {
		ModelAndView mv = new ModelAndView("npvr_mapping");
		return mv;
	}
}
