package com.pccw.ott.controller;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.ArrayUtils;
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
import com.pccw.ott.model.OttSnookerFrame;
import com.pccw.ott.model.OttSnookerLeague;
import com.pccw.ott.model.OttSnookerLevel;
import com.pccw.ott.model.OttSnookerPerson;
import com.pccw.ott.model.OttSnookerPoint;
import com.pccw.ott.model.OttSnookerRank;
import com.pccw.ott.model.OttSnookerScore;
import com.pccw.ott.schedual.OttSchedualTask;
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
	
	// ------------------ Snooker Score ------------------

	@RequestMapping("/score/goToListLivePage.html")
	public ModelAndView goToListLivePage() {
		ModelAndView mv = new ModelAndView("snooker_live");
		return mv;
	}
	
	@RequestMapping("/score/goToListFixturePage.html")
	public ModelAndView goToListFixturePage() {
		ModelAndView mv = new ModelAndView("snooker_fixture");
		return mv;
	}
	
	@RequestMapping("/score/listScore.html")
	@ResponseBody
	public Map<String, Object> listScore(HttpServletRequest request, @RequestParam int page, @RequestParam int rows) {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		String leagueName = request.getParameter("leagueNameForSearch");
		String scoreType = request.getParameter("scoreType");
		String sort = request.getParameter("sort");
		String order = request.getParameter("order");
		int first = (page - 1) * rows;
		int max = rows;
		List<OttSnookerScore> list = ottSnookerService.findSnookerScoreList(leagueName, scoreType, first, max, sort, order);
		Long total = ottSnookerService.findSnookerScoreListSize(leagueName, scoreType);
		returnMap.put("rows", list);
		returnMap.put("total", total);
		return returnMap;
	}
	
	@RequestMapping("/score/saveScoreChanges.html")
	@ResponseBody
	public Map<String, Object> saveScoreChanges(HttpServletRequest request) {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		String inserted = request.getParameter("inserted");
		String updated = request.getParameter("updated");
		String deleted = request.getParameter("deleted");
		ObjectMapper mapper = new ObjectMapper();
		JavaType javaType = mapper.getTypeFactory().constructParametricType(ArrayList.class, OttSnookerScore.class);
		try {
			if (StringUtils.isNotBlank(inserted)) {
				List<OttSnookerScore> insertedList = mapper.readValue(inserted, javaType);
				if (insertedList.size() > 0)
					ottSnookerService.batchSaveSnookerScoreList(insertedList);
			}
			if (StringUtils.isNotBlank(updated)) {
				List<OttSnookerScore> updatedList = mapper.readValue(updated, javaType);
				if (updatedList.size() > 0)
					ottSnookerService.batchUpdateSnookerScoreList(updatedList);
			}
			if (StringUtils.isNotBlank(deleted)) {
				List<OttSnookerScore> deletedList = mapper.readValue(deleted, javaType);
				if (deletedList.size() > 0)
					ottSnookerService.batchDeleteSnookerScoreList(deletedList);
			}
			returnMap.put("success", true);
		} catch (DataIntegrityViolationException e) {
			logger.error(e.toString());
			returnMap.put("success", false);
			returnMap.put("msg", "Failed to save changes. Duplicate match id is not allowed.");
		} catch (JsonParseException | JsonMappingException e) {
			logger.error(e.toString());
			returnMap.put("success", false);
		} catch (IOException e) {
			logger.error(e.toString());
			returnMap.put("success", false);
		}
		return returnMap;
	}
	
	@RequestMapping("/score/saveFrameChanges.html")
	@ResponseBody
	public Map<String, Object> saveFrameChanges(HttpServletRequest request) {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		String inserted = request.getParameter("inserted");
		String updated = request.getParameter("updated");
		String deleted = request.getParameter("deleted");
		String scoreString = request.getParameter("score");
		ObjectMapper mapper = new ObjectMapper();
		JavaType javaType = mapper.getTypeFactory().constructParametricType(ArrayList.class, OttSnookerFrame.class);
		try {
			OttSnookerScore score = mapper.readValue(scoreString, OttSnookerScore.class);
			if (StringUtils.isNotBlank(inserted)) {
				List<OttSnookerFrame> insertedList = mapper.readValue(inserted, javaType);
				if (insertedList.size() > 0)
					ottSnookerService.batchSaveSnookerFrameList(score, insertedList);
			}
			if (StringUtils.isNotBlank(updated)) {
				List<OttSnookerFrame> updatedList = mapper.readValue(updated, javaType);
				if (updatedList.size() > 0)
					ottSnookerService.batchUpdateSnookerFrameList(updatedList);
			}
			if (StringUtils.isNotBlank(deleted)) {
				List<OttSnookerFrame> deletedList = mapper.readValue(deleted, javaType);
				if (deletedList.size() > 0)
					ottSnookerService.batchDeleteSnookerFrameList(deletedList);
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
	
	// ------------------ Snooker Rank ------------------
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

/*	@RequestMapping("/rank/listPoint.html")
	@ResponseBody
	public Map<String, Object> listPoint(HttpServletRequest request, @RequestParam String playerId) {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		List<OttSnookerPoint> list = ottSnookerService.findSnookerPointList(playerId);
		returnMap.put("rows", list);
		return returnMap;
	}*/
	
	@RequestMapping("/rank/savePointChanges.html")
	@ResponseBody
	public Map<String, Object> savePointChanges(HttpServletRequest request) {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		String inserted = request.getParameter("inserted");
		String updated = request.getParameter("updated");
		String deleted = request.getParameter("deleted");
		String rankString = request.getParameter("rank");
		ObjectMapper mapper = new ObjectMapper();
		JavaType javaType = mapper.getTypeFactory().constructParametricType(ArrayList.class, OttSnookerPoint.class);
		try {
			OttSnookerRank rank = mapper.readValue(rankString, OttSnookerRank.class);
			if (StringUtils.isNotBlank(inserted)) {
				List<OttSnookerPoint> insertedList = mapper.readValue(inserted, javaType);
				if (insertedList.size() > 0)
					ottSnookerService.batchSaveSnookerPointList(rank, insertedList);
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
	
	//------------------ Snooker League ------------------
	@RequestMapping("/league/goToListLeaguePage.html")
	public ModelAndView goToListLeaguePage() {
		ModelAndView mv = new ModelAndView("snooker_league");
		return mv;
	}
	
	@RequestMapping("/league/listLeague.html")
	@ResponseBody
	public Map<String, Object> listLeague(HttpServletRequest request, @RequestParam int page, @RequestParam int rows) {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		String leagueName = request.getParameter("leagueNameForSearch");
		String sort = request.getParameter("sort");
		String order = request.getParameter("order");
		int first = (page - 1) * rows;
		int max = rows;
		List<OttSnookerLeague> list = ottSnookerService.findSnookerLeagueList(leagueName, first, max, sort, order);
		Long total = ottSnookerService.findSnookerLeagueListSize(leagueName);
		returnMap.put("rows", list);
		returnMap.put("total", total);
		return returnMap;
	}
	
	@RequestMapping("/league/saveLeagueChanges.html")
	@ResponseBody
	public Map<String, Object> saveLeagueChanges(HttpServletRequest request) {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		String inserted = request.getParameter("inserted");
		String updated = request.getParameter("updated");
		String deleted = request.getParameter("deleted");
		ObjectMapper mapper = new ObjectMapper();
		JavaType javaType = mapper.getTypeFactory().constructParametricType(ArrayList.class, OttSnookerLeague.class);
		try {
			if (StringUtils.isNotBlank(inserted)) {
				List<OttSnookerLeague> insertedList = mapper.readValue(inserted, javaType);
				if (insertedList.size() > 0)
					ottSnookerService.batchSaveSnookerLeagueList(insertedList);
			}
			if (StringUtils.isNotBlank(updated)) {
				List<OttSnookerLeague> updatedList = mapper.readValue(updated, javaType);
				if (updatedList.size() > 0)
					ottSnookerService.batchUpdateSnookerLeagueList(updatedList);
			}
			if (StringUtils.isNotBlank(deleted)) {
				List<OttSnookerLeague> deletedList = mapper.readValue(deleted, javaType);
				if (deletedList.size() > 0)
					ottSnookerService.batchDeleteSnookerLeagueList(deletedList);
			}
			returnMap.put("success", true);
		} catch (DataIntegrityViolationException e) {
			logger.error(e.toString());
			returnMap.put("success", false);
			returnMap.put("msg", "Failed to save changes. Duplicate league id is not allowed.");
		} catch (JsonParseException | JsonMappingException e) {
			logger.error(e.toString());
			returnMap.put("success", false);
		} catch (IOException e) {
			logger.error(e.toString());
			returnMap.put("success", false);
		}
		return returnMap;
	}
	
	@RequestMapping("/league/saveLevelChanges.html")
	@ResponseBody
	public Map<String, Object> saveLevelChanges(HttpServletRequest request) {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		String inserted = request.getParameter("inserted");
		String updated = request.getParameter("updated");
		String deleted = request.getParameter("deleted");
		String leagueString = request.getParameter("league");
		ObjectMapper mapper = new ObjectMapper();
		JavaType javaType = mapper.getTypeFactory().constructParametricType(ArrayList.class, OttSnookerLevel.class);
		try {
			OttSnookerLeague league = mapper.readValue(leagueString, OttSnookerLeague.class);
			if (StringUtils.isNotBlank(inserted)) {
				List<OttSnookerLevel> insertedList = mapper.readValue(inserted, javaType);
				if (insertedList.size() > 0)
					ottSnookerService.batchSaveSnookerLevelList(league, insertedList);
			}
			if (StringUtils.isNotBlank(updated)) {
				List<OttSnookerLevel> updatedList = mapper.readValue(updated, javaType);
				if (updatedList.size() > 0)
					ottSnookerService.batchUpdateSnookerLevelList(updatedList);
			}
			if (StringUtils.isNotBlank(deleted)) {
				List<OttSnookerLevel> deletedList = mapper.readValue(deleted, javaType);
				if (deletedList.size() > 0)
					ottSnookerService.batchDeleteSnookerLevelList(deletedList);
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
	
	// ------------------ Snooker Player ------------------
	@RequestMapping("/player/goToListPlayerPage.html")
	public ModelAndView goToListPlayerPage() {
		ModelAndView mv = new ModelAndView("snooker_player");
		return mv;
	}
	
	@RequestMapping("/player/listPlayer.html")
	@ResponseBody
	public Map<String, Object> listPlayer(HttpServletRequest request, @RequestParam int page, @RequestParam int rows) {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		String playerName = request.getParameter("playerNameForSearch");
		String sort = request.getParameter("sort");
		String order = request.getParameter("order");
		int first = (page - 1) * rows;
		int max = rows;
		List<OttSnookerPerson> list = ottSnookerService.findSnookerPersonList(playerName, first, max, sort, order);
		Long total = ottSnookerService.findSnookerPersonListSize(playerName);
		returnMap.put("rows", list);
		returnMap.put("total", total);
		return returnMap;
	}
	
	@RequestMapping("/player/savePersonChanges.html")
	@ResponseBody
	public Map<String, Object> savePersonChanges(HttpServletRequest request) {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		String inserted = request.getParameter("inserted");
		String updated = request.getParameter("updated");
		String deleted = request.getParameter("deleted");
		ObjectMapper mapper = new ObjectMapper();
		JavaType javaType = mapper.getTypeFactory().constructParametricType(ArrayList.class, OttSnookerPerson.class);
		try {
			if (StringUtils.isNotBlank(inserted)) {
				List<OttSnookerPerson> insertedList = mapper.readValue(inserted, javaType);
				if (insertedList.size() > 0)
					ottSnookerService.batchSaveSnookerPersonList(insertedList);
			}
			if (StringUtils.isNotBlank(updated)) {
				List<OttSnookerPerson> updatedList = mapper.readValue(updated, javaType);
				if (updatedList.size() > 0)
					ottSnookerService.batchUpdateSnookerPersonList(updatedList);
			}
			if (StringUtils.isNotBlank(deleted)) {
				List<OttSnookerPerson> deletedList = mapper.readValue(deleted, javaType);
				if (deletedList.size() > 0)
					ottSnookerService.batchDeleteSnookerPersonList(deletedList);
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
		} catch (ParseException e) {
			e.printStackTrace();
			logger.error(e.toString());
			returnMap.put("success", false);
		}
		return returnMap;
	}
	
	@RequestMapping("/test.html")
	public void test() {
		List<Map<String, Integer>> params = ottSnookerService.getLeagueParams();
		logger.info("############ OttSchedualTask.retrieveSnookerLeagueData() ############");
		Integer sid = null;
		Integer lid = null;
		String api = null;
		String response = null;
		OttSnookerLeague ottSnookerLeague = null;
		String leagueApi = CustomizedPropertyConfigurer.getContextProperty("api.snooker_league");
		for (Map<String, Integer> map : params) {
			sid = map.get("sid");
			lid = map.get("lid");
			api = leagueApi + "&lId=" + lid + "&sId=" + sid;
			response = HttpClientUtil.getInstance().sendHttpGetWithProxy(api, "10.12.251.1", 8080, "http");
			if (StringUtils.isNotBlank(response)) {
				ottSnookerLeague = JsonUtil.parseJson2SnookerLeague(response);
				ottSnookerService.renewSnookerLeagueData(ottSnookerLeague);
			} else {
				logger.error("OttSchedualTask.retrieveSnookerLeagueData() failed on sid = " + sid + ", lid = " + lid);
			}
		}
	
	}
	
}