package com.pccw.ott.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.pccw.ott.service.OttUserService;

@Controller
public class OttMainController {
	
	private static Logger logger = LoggerFactory.getLogger(OttMainController.class);
	
	@Autowired
	private OttUserService OttUserService;

	@RequestMapping("/main.html")
	public ModelAndView main(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView("main");
		mv.addObject("hasUserPermission", "Y");
		mv.addObject("hasRolePermission", "Y");
		mv.addObject("hasPermission", "Y");
		mv.addObject("hasAuditTrailPermission", "Y");
		return mv;
	}
	
	@RequestMapping("/testDB.html")
	@ResponseBody
	public Map<String, Object> testDB() {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		try {
			Date date = OttUserService.getSysdate();
			if (date != null) {
				returnMap.put("msg", "Database connection is success!");
			} else {
				returnMap.put("msg", "Database connection is fail!");
			}
		} catch (Exception e) {
			logger.error(e.toString());
			returnMap.put("msg", "Error occurs while tring to connect database.");
		}
		return returnMap;
	}

}
