package com.pccw.ott.controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pccw.ott.dto.OttNpvrMappingDto;
import com.pccw.ott.dto.OttNpvrSearchDto;
import com.pccw.ott.model.OttNpvrMapping;
import com.pccw.ott.model.OttSnookerScore;
import com.pccw.ott.service.OttNpvrMappingService;
import com.pccw.ott.service.OttSnookerService;
import com.pccw.ott.util.CustomizedPropertyConfigurer;
import com.pccw.ott.util.HttpClientUtil;
import com.pccw.ott.util.JsonUtil;

@Controller
@RequestMapping("/npvr")
public class OttNpvrController {

	private static Logger logger = LoggerFactory.getLogger(OttNpvrController.class);
	
	@Autowired
	private OttNpvrMappingService ottNpvrMappingService;
	
	@Autowired
	private OttSnookerService ottSnookerService;
	
	@RequestMapping("/goToNpvrMappingPage.html")
	public ModelAndView goToNpvrMappingPage() {
		ModelAndView mv = new ModelAndView("npvr_mapping");
		return mv;
	}
	
	@RequestMapping("retrieveLeagueList.html")
	@ResponseBody
	public Map<String, Object> retrieveLeagueList(HttpServletRequest request, @RequestParam String sportType) {
		Map<String, Object> returnMap = new HashMap<>();
		switch (sportType) {
		case "SOCCER":
			returnMap.put("success", true);
			returnMap.put("leagueList", new ArrayList<>());
			break;
		case "SNOOKER":
			List<Map<String, Object>> leagueList = ottSnookerService.retrieveLeagueListForNpvr();
			returnMap.put("success", true);
			returnMap.put("leagueList", leagueList);
			break;
		}
		return returnMap;
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
				List<OttNpvrMappingDto> allList = new ArrayList<>();
				List<OttNpvrMappingDto> filterList = new ArrayList<>();
				long days = (toDate.getTime() - fromDate.getTime()) / 86400000;
				String start_date = new SimpleDateFormat("yyyyMMdd").format(fromDate);
				switch (npvrSearchDto.getSportType()) {
				case "SOCCER":
					api = CustomizedPropertyConfigurer.getContextProperty("api.soccer_fixture") + "&start_date=" + start_date + "&days=" + days;
//					String response = HttpClientUtil.getInstance().sendHttpGet(api);
					String response = HttpClientUtil.getInstance().sendHttpGetWithProxy(api, "10.12.251.1", 8080, "http");
					if (StringUtils.isNotBlank(response)) {
						allList = JsonUtil.parseJson2NpvrMapping(response);
					}
					break;
				case "SNOOKER":
					allList = ottNpvrMappingService.findForSnookerFixture();
					break;
				}
				filterList = ottNpvrMappingService.filterByNpvrSearchDto(allList, npvrSearchDto);

				returnMap.put("success", true);
				returnMap.put("list", filterList);
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
		Set<String> tempSet = new HashSet<>();
		for (int i = 0; i < npvrIdArr.length; i++) {
			if (StringUtils.isNotEmpty(npvrIdArr[i])) {
				tempSet.add(npvrIdArr[i]);
			}
		}
		npvrIdArr = this.convert2StringArray(tempSet.toArray());
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
			if (StringUtils.isNotBlank(response)) {
				Map<String, Object> reponseMap = JsonUtil.verifyNpvrIds(response, npvrIdArr);
				List<String> invalidIds = (List<String>) reponseMap.get("invalidNpvr");
				if (invalidIds.size() > 0) {
					returnMap.put("success", false);
					String msg = "Invalid NPVR IDs: ";
					for (int i = 0; i < invalidIds.size(); i++) {
						msg += "<br>" + invalidIds.get(i);	
					}
					msg += "<br>Please check again.";
					returnMap.put("msg", msg);
				} else {
					returnMap.put("success", true);	
					returnMap.put("validNpvrs", reponseMap.get("validNpvrs"));
				}
			} else {
				returnMap.put("success", false);
			}
			
		} else {
			returnMap.put("success", false);
			returnMap.put("msg", "No valid NPVR IDs.");
		}
		
		return returnMap;
	}
	
	@RequestMapping("/clearNpvrIds.html")
	@ResponseBody
	public Map<String, Object> clearNpvrIds(HttpServletRequest request) {
		Map<String, Object> returnMap = new HashMap<>();
		String sportType = request.getParameter("sportType").toUpperCase();
		String fixtureId = request.getParameter("fixtureId");
		ottNpvrMappingService.clearNpvrIds(sportType.toUpperCase(), fixtureId);
		returnMap.put("success", true);
		return returnMap;
	}
	
	@RequestMapping("/saveNpvrIds.html")
	@ResponseBody
	public Map<String, Object> saveNpvrIds(HttpServletRequest request) {
		Map<String, Object> returnMap = new HashMap<>();
		String npvrMappingStr = request.getParameter("npvrMappingList");
		String sportType = request.getParameter("sportType");
		String fixtureId = request.getParameter("fixtureId");
		ObjectMapper mapper = new ObjectMapper();
		JavaType javaType = mapper.getTypeFactory().constructParametricType(ArrayList.class, OttNpvrMapping.class);
		List<OttNpvrMapping> mappingList = new ArrayList<>();
		if (StringUtils.isNotBlank(npvrMappingStr)) {
			try {
				mappingList = mapper.readValue(npvrMappingStr, javaType);
				String[] npvrIdArr = new String[mappingList.size()];
				String[] channelNoArr = new String[mappingList.size()];
				for (int i = 0; i < mappingList.size(); i++) {
					npvrIdArr[i] = mappingList.get(i).getNpvrId();
					channelNoArr[i] = mappingList.get(i).getChannelNo().toString();
				}
				ottNpvrMappingService.doSaveNpvrIds(sportType.toUpperCase(), fixtureId, mappingList);
				returnMap.put("success", true);
				returnMap.put("npvrIds", String.join(",", npvrIdArr));
				returnMap.put("channelNos", String.join(",", channelNoArr));
			} catch (IOException e) {
				e.printStackTrace();
				logger.error(e.getMessage());
				returnMap.put("success", false);
				returnMap.put("msg", "Failed to save NPVR IDs. Please try again.");
			}
		}

		return returnMap;
	}
	
	private String[] convert2StringArray(Object[] objArr) {
		String[] strArr = new String[objArr.length];
		for (int i = 0; i < objArr.length; i++) {
			strArr[i] = (String) objArr[i];
		}
		return strArr;
	}
	
}
