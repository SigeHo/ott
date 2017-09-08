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
import org.springframework.dao.DataIntegrityViolationException;
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

	@RequestMapping("/rank/saveRankChanges.html")
	@ResponseBody
	public Map<String, Object> saveRankChanges(HttpServletRequest request) {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		String inserted = request.getParameter("inserted");
		String updated = request.getParameter("updated");
		String deleted = request.getParameter("deleted");
		ObjectMapper mapper = new ObjectMapper();
		JavaType javaType = mapper.getTypeFactory().constructParametricType(ArrayList.class, OttSnookerRank.class);
		try {
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
		} catch (DataIntegrityViolationException e) {
			logger.error(e.toString());
			returnMap.put("success", false);
			returnMap.put("msg", "Failed to save changes. Duplicate player id is not allowed.");
		} catch (JsonParseException | JsonMappingException e) {
			logger.error(e.toString());
			returnMap.put("success", false);
		} catch (IOException e) {
			logger.error(e.toString());
			returnMap.put("success", false);
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
	
	@RequestMapping("/rank/savePointChanges.html")
	@ResponseBody
	public Map<String, Object> savePointChanges(HttpServletRequest request) {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		String inserted = request.getParameter("inserted");
		String updated = request.getParameter("updated");
		String deleted = request.getParameter("deleted");
		ObjectMapper mapper = new ObjectMapper();
		JavaType javaType = mapper.getTypeFactory().constructParametricType(ArrayList.class, OttSnookerPoint.class);
		try {
			if (StringUtils.isNotBlank(inserted)) {
				List<OttSnookerPoint> insertedList = mapper.readValue(inserted, javaType);
				if (insertedList.size() > 0)
					ottSnookerService.batchSaveSnookerPointList(insertedList);
			}
			if (StringUtils.isNotBlank(updated)) {
				List<OttSnookerPoint> updatedList = mapper.readValue(updated, javaType);
				if (updatedList.size() > 0)
					ottSnookerService.batchUpdateSnookerPointList(updatedList);
			}
			if (StringUtils.isNotBlank(deleted)) {
				List<OttSnookerPoint> deletedList = mapper.readValue(deleted, javaType);
				if (deletedList.size() > 0)
					ottSnookerService.batchDeleteSnookerPointList(deletedList);
			}
			returnMap.put("success", true);
		} catch (JsonParseException | JsonMappingException e) {
			logger.error(e.toString());
			returnMap.put("success", false);
		} catch (IOException e) {
			logger.error(e.toString());
			returnMap.put("success", false);
		}
		return returnMap;
	}
	
	// ------------------ Snooker Fixture ------------------

	@RequestMapping("/fixture/goToListFixturePage.html")
	public ModelAndView goToListFixturePage() {
		ModelAndView mv = new ModelAndView("snooker_fixture");
		return mv;
	}

}