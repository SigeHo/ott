package com.pccw.ott.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pccw.ott.model.OttSnookerPoint;
import com.pccw.ott.model.OttSnookerRank;
import com.pccw.ott.service.OttSnookerService;

@Controller
@RequestMapping("/snooker")
public class OttSnookerController {

	private static Logger logger = LoggerFactory.getLogger(OttSnookerController.class);

	@Autowired
	private OttSnookerService snookerService;

	@RequestMapping("/rank/goToListRankPage.html")
	public ModelAndView goToListRankPage(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("snooker_rank");
		return mv;
	}

	@RequestMapping("/rank/listRank.html")
	@ResponseBody
	public Map<String, Object> listRank(HttpServletRequest request, @RequestParam int page, @RequestParam int rows) {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		String playerName = request.getParameter("playerNameForSearch");
		String sort = request.getParameter("sort");
		String order = request.getParameter("order");
		int first = (page - 1) * rows;
		int max = rows;
		List<OttSnookerRank> list = snookerService.findSnookerRankList(playerName, first, max, sort, order);
		List<OttSnookerPoint> pointList = list.get(0).getSnookerPointList();
		OttSnookerPoint point = pointList.get(0);
		Long total = snookerService.findSnookerRankListSize(playerName);
		returnMap.put("rows", list);
		returnMap.put("total", total);
		return returnMap;
	}
	
	@RequestMapping("/rank/batchUpdateRank.html")
	@ResponseBody
	public Map<String, Object> batchUpdateRank(HttpServletRequest request) throws JsonParseException, JsonMappingException, IOException {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		String updated = request.getParameter("updated");
		if (StringUtils.isNotBlank(updated)) {
			ObjectMapper mapper = new ObjectMapper();
			JavaType javaType = mapper.getTypeFactory().constructParametricType(ArrayList.class, OttSnookerRank.class); 
			List<OttSnookerRank> list = mapper.readValue(updated, javaType);
			
		}
		return returnMap;
	}
	
	@RequestMapping("/rank/deleteRank.html")
	@ResponseBody
	public Map<String, Object> deleteRank(HttpServletRequest request) {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		String deleteId = request.getParameter("rankId");
		if (StringUtils.isNotBlank(deleteId)) {
			snookerService.deleteSnookerRankById(Long.valueOf(deleteId));
		}
		return returnMap;
	}

	@RequestMapping("/rank/listPoint.html")
	@ResponseBody
	public Map<String, Object> listPoint(HttpServletRequest request, @RequestParam String playerId) {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		List<OttSnookerPoint> list = snookerService.findSnookerPointList(playerId);
		returnMap.put("rows", list);
		return returnMap;
	}
	
	@RequestMapping("/fixture/goToListFixturePage.html")
	public ModelAndView goToListFixturePage() {
		ModelAndView mv = new ModelAndView("snooker_fixture");
		return mv;
	}

}