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
import com.pccw.ott.util.CustomizedPropertyConfigurer;
import com.pccw.ott.util.HttpClientUtil;
import com.pccw.ott.util.JsonUtil;

@Controller
@RequestMapping("/snooker")
public class OttSnookerController {

	private static Logger logger = LoggerFactory.getLogger(OttSnookerController.class);

	@Autowired
	private OttSnookerService ottSnookerService;

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
		List<OttSnookerRank> list = ottSnookerService.findSnookerRankList(playerName, first, max, sort, order);
		Long total = ottSnookerService.findSnookerRankListSize(playerName);
		returnMap.put("rows", list);
		returnMap.put("total", total);
		return returnMap;
	}
	
	@RequestMapping("/rank/saveChanges.html")
	@ResponseBody
	public Map<String, Object> saveChanges(HttpServletRequest request) throws JsonParseException, JsonMappingException, IOException {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		String inserted = request.getParameter("inserted");
		String updated = request.getParameter("updated");
		String deleted = request.getParameter("deleted");
		ObjectMapper mapper = new ObjectMapper();
		JavaType javaType = mapper.getTypeFactory().constructParametricType(ArrayList.class, OttSnookerRank.class); 
		if (StringUtils.isNotBlank(inserted)) {
			List<OttSnookerRank> insertedList = mapper.readValue(inserted, javaType);
			if (insertedList.size() > 0)
				ottSnookerService.batchSaveSnookerRankList(insertedList);
		}
		if (StringUtils.isNotBlank(updated)) {
			List<OttSnookerRank> updatedList = mapper.readValue(updated, javaType);
			if (updatedList.size() > 0)
				ottSnookerService.batchUpdateSnookerRankList(updatedList);
		}
		if (StringUtils.isNotBlank(deleted)) {
			List<OttSnookerRank> deletedList = mapper.readValue(deleted, javaType);
			if (deletedList.size() > 0)
				ottSnookerService.batchDeleteSnookerRankList(deletedList);
		}
		returnMap.put("success", true);
		return returnMap;
	}
	
	@RequestMapping("/rank/deleteRank.html")
	@ResponseBody
	public Map<String, Object> deleteRank(HttpServletRequest request) {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		String deleteId = request.getParameter("rankId");
		if (StringUtils.isNotBlank(deleteId)) {
			ottSnookerService.deleteSnookerRankById(Long.valueOf(deleteId));
		}
		return returnMap;
	}

	@RequestMapping("/rank/listPoint.html")
	@ResponseBody
	public Map<String, Object> listPoint(HttpServletRequest request, @RequestParam String playerId) {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		List<OttSnookerPoint> list = ottSnookerService.findSnookerPointList(playerId);
		returnMap.put("rows", list);
		return returnMap;
	}
	
	@RequestMapping("/rank/save.html")
	public void save() {
		String response = HttpClientUtil.getInstance().sendHttpPost(CustomizedPropertyConfigurer.getContextProperty("api.snooker_rank"));
//	 	String response = HttpClientUtil.readFile("e:/desktop/json.txt");
	 	if (StringUtils.isNotBlank(response)) {
		 	List<OttSnookerRank> list = JsonUtil.parseJson2SnookerRank(response);
		 	ottSnookerService.batchSaveSnookerRankList(list);
	 	} else {
	 		logger.error("OttSchedualTask.retrieveSnookerRankData() failed.");
	 	}
	}
	
	@RequestMapping("/rank/cleanUp.html")
	public void cleanUp() {
		ottSnookerService.flushSnookerRankData(null);
	}
	
	@RequestMapping("/fixture/goToListFixturePage.html")
	public ModelAndView goToListFixturePage() {
		ModelAndView mv = new ModelAndView("snooker_fixture");
		return mv;
	}

}