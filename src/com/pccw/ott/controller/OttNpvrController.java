package com.pccw.ott.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pccw.ott.dto.OttNpvrMappingDto;
import com.pccw.ott.dto.OttNpvrSearchDto;
import com.pccw.ott.model.OttNpvrMapping;
import com.pccw.ott.service.OttNpvrMappingService;
import com.pccw.ott.util.CustomizedPropertyConfigurer;
import com.pccw.ott.util.HttpClientUtil;
import com.pccw.ott.util.JsonUtil;

@Controller
@RequestMapping("/npvr")
public class OttNpvrController {

	private static Logger logger = LoggerFactory.getLogger(OttNpvrController.class);
	
	@Autowired
	private OttNpvrMappingService ottNpvrMappingService;

	@RequestMapping("/goToNpvrMappingPage.html")
	public ModelAndView goToNpvrMappingPage() {
		ModelAndView mv = new ModelAndView("npvr_mapping");
		return mv;
	}

	@RequestMapping("doSearch.html")
	@ResponseBody
	public Map<String, Object> doSearch(HttpServletRequest request, OttNpvrSearchDto npvrSearchDto) {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		String api = "";
		try {
			Date fromDate = sdf.parse(npvrSearchDto.getFromDate());
			Date toDate = sdf.parse(npvrSearchDto.getToDate());
			if (toDate.after(fromDate)) {
				long days = (toDate.getTime() - fromDate.getTime()) / 86400000;
				String start_date = new SimpleDateFormat("yyyyMMdd").format(fromDate);
//				List<Map<String, String>> leagueList = this.retrieveLeagueList(npvrSearchDto.getSportType());
				if (npvrSearchDto.getSportType().equals("Soccer")) {
					api = CustomizedPropertyConfigurer.getContextProperty("api.soccer_fixture") + "&start_date=" + start_date + "&days=" + days;
//					String response = HttpClientUtil.getInstance().sendHttpGet(api);
					String response = HttpClientUtil.getInstance().sendHttpGetWithProxy(api, "10.12.251.1", 8080, "http");
					if (StringUtils.isNotBlank(response)) {
						List<OttNpvrMappingDto> allList = JsonUtil.parseJson2NpvrMapping(response);
						List<OttNpvrMappingDto> filterList = ottNpvrMappingService.filterByNpvrSearchDto(allList, npvrSearchDto);
						returnMap.put("success", true);
						returnMap.put("list", filterList);
					}
				}
			} else {
				returnMap.put("success", false);
				returnMap.put("msg", "From Date must earlier than to To Date.");
			}
		} catch (ParseException e) {
			e.printStackTrace();
			logger.error(e.toString());
			returnMap.put("success", false);
			returnMap.put("msg", "Failed to search. Date format error.");
		}
		return returnMap;
	}
	
	@RequestMapping("/verifyNpvrIds.html")
	@ResponseBody
	public Map<String, Object> verifyNpvrIds(@RequestParam String[] npvrIdArr) {
		Map<String, Object> returnMap = new HashMap<>();
		String q = "&q=";
		if (npvrIdArr.length > 0) {
			if (npvrIdArr.length == 1) {
				q += "p_vimProgId%3A" + npvrIdArr[0] + "%20AND%20p_recordable%3Atrue";
			} else {
				q += "(p_vimProgId%3A" + npvrIdArr[0];
				for (int i = 1; i < npvrIdArr.length; i++) {
					if (i == npvrIdArr.length - 1) {
						q += "%20OR%20p_vimProgId%3A" + npvrIdArr[i] + ")%20AND%20p_recordable%3Atrue";
					} else {
						q += "%20OR%20p_vimProgId%3A" + npvrIdArr[i];
					}
				}
			}
			String api = CustomizedPropertyConfigurer.getContextProperty("api.npvr_verification") + q;
			String response = HttpClientUtil.getInstance().sendHttpGet(api);
			List<String> invalidIds = JsonUtil.getInvalidNpvrIds(response, npvrIdArr);
			if (invalidIds.size() > 0) {
				returnMap.put("success", false);
				String msg = "Invalid NPVR IDs: ";
				for (int i = 0; i < invalidIds.size(); i++) {
					msg += "\n" + invalidIds.get(i);	
				}
				msg += "\nPlease check again.";
				returnMap.put("msg", msg);
			} else {
				returnMap.put("success", true);	
			}
		} else {
			returnMap.put("success", false);
			returnMap.put("msg", "No valid NPVR IDs.");
		}
		
		return returnMap;
	}
	
	@RequestMapping("/saveNpvrIds.html")
	@ResponseBody
	public Map<String, Object> saveNpvrIds(HttpServletRequest request) {
		Map<String, Object> returnMap = new HashMap<>();
		String channelNo = request.getParameter("channelNo");
		String sportType = request.getParameter("sportType").toUpperCase();
		String fixtureId = request.getParameter("fixtureId");
		String[] npvrIdArr = request.getParameter("npvrIds").split(","); 
		List<OttNpvrMapping> list = new ArrayList<>();
		OttNpvrMapping ottNpvrMapping = null;
		for (int i = 0; i < npvrIdArr.length; i++) {
			ottNpvrMapping = new OttNpvrMapping();
			ottNpvrMapping.setChannelNo(Integer.valueOf(channelNo));
			ottNpvrMapping.setSportType(sportType);
			ottNpvrMapping.setFixtureId(fixtureId);
			ottNpvrMapping.setNpvrId(npvrIdArr[i]);
			list.add(ottNpvrMapping);
		}
		ottNpvrMappingService.batchSave(list);
		return returnMap;
	}
	
	/*private List<Map<String, String>> retrieveLeagueList(String sportType) {
		List<Map<String, String>> leagueList = new ArrayList<>();
		Map<String, String> leagueMap = new HashMap<>();
		String api = "";
		if (sportType.equals("Soccer")) {
			api = CustomizedPropertyConfigurer.getContextProperty("api.soccer_league");
		}
		String response = HttpClientUtil.getInstance().sendHttpGetWithProxy(api, "10.12.251.1", 8080, "http");
		if (sportType.equals("Soccer")) {
			leagueList = JsonUtil.parseJson2SoccerLeagueList(response);
		}
		return leagueList;
	}*/

}
