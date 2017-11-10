package com.pccw.ott.schedual;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.pccw.ott.model.OttSnookerLeague;
import com.pccw.ott.model.OttSnookerPerson;
import com.pccw.ott.model.OttSnookerRank;
import com.pccw.ott.model.OttSnookerScore;
import com.pccw.ott.service.OttSnookerService;
import com.pccw.ott.util.CustomizedPropertyConfigurer;
import com.pccw.ott.util.HttpClientUtil;
import com.pccw.ott.util.JsonUtil;

public class OttSchedualTask {

	private static Logger logger = LoggerFactory.getLogger(OttSchedualTask.class);

	@Autowired
	private OttSnookerService ottSnookerService;

	public void retrieveSnookerLiveData() {
		logger.info("############ OttSchedualTask.retrieveSnookerLiveData() ############");
		String api = CustomizedPropertyConfigurer.getContextProperty("api.snooker_live");
//		String response = HttpClientUtil.getInstance().sendHttpPost(api);
		String response = HttpClientUtil.getInstance().sendHttpGetWithProxy(api, "10.12.251.1", 8080, "http");
		if (StringUtils.isNotBlank(response)) {
			List<OttSnookerScore> list = JsonUtil.parseJson2SnookerScore(response, "LIVE");
			if (list.size() > 0)
				ottSnookerService.renewSnookerScoreData(list, "LIVE");
		} else {
			logger.error("OttSchedualTask.retrieveSnookerLiveData() failed.");
		}
	}

	public void retrieveSnookerFixtureData() {
		logger.info("############ OttSchedualTask.retrieveSnookerFixtureData() ############");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date startDate;
		Date endDate;
		try {
			startDate = sdf.parse("2017-08-03");
			endDate = sdf.parse("2017-08-03");
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(startDate);
			calendar.add(Calendar.DAY_OF_MONTH, -2);
			startDate = calendar.getTime();
			calendar.setTime(endDate);
			calendar.add(Calendar.DAY_OF_MONTH, +5);
			endDate = calendar.getTime();
			String api = CustomizedPropertyConfigurer.getContextProperty("api.snooker_fixture");
			api += "&startDate=" + sdf.format(startDate) + "&endDate=" + sdf.format(endDate);
//			 String response = HttpClientUtil.getInstance().sendHttpPost(api);
			String response = HttpClientUtil.getInstance().sendHttpGetWithProxy(api, "10.12.251.1", 8080, "http");
			if (StringUtils.isNotBlank(response)) {
				List<OttSnookerScore> list = JsonUtil.parseJson2SnookerScore(response, "FIXTURE");
				if (list.size() > 0) {
					ottSnookerService.renewSnookerScoreData(list, "FIXTURE");
					List<Map<String, Integer>> params = ottSnookerService.getLeagueParams();
					if (params.size() > 0) {
						this.retrieveSnookerLeagueData(params);
						this.retrieveSnookerPersonData(params);
					}
				}
			} else {
				logger.error("OttSchedualTask.retrieveSnookerFixtureData() failed.");
			}

		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	public void retrieveSnookerRankData() {
		logger.info("############ OttSchedualTask.retrieveSnookerRankData() ############");
		String api = CustomizedPropertyConfigurer.getContextProperty("api.snooker_rank");
//		 String response = HttpClientUtil.getInstance().sendHttpPost(api);
		String response = HttpClientUtil.getInstance().sendHttpGetWithProxy(api, "10.12.251.1", 8080, "http");
		if (StringUtils.isNotBlank(response)) {
			List<OttSnookerRank> list = JsonUtil.parseJson2SnookerRank(response);
			if (list.size() > 0) 
				ottSnookerService.renewSnookerRankData(list);	
		} else {
			logger.error("OttSchedualTask.retrieveSnookerRankData() failed.");
		}
	}

	public void retrieveSnookerLeagueData(List<Map<String, Integer>> params) {
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
//			response = HttpClientUtil.getInstance().sendHttpPost(api);
			response = HttpClientUtil.getInstance().sendHttpGetWithProxy(api, "10.12.251.1", 8080, "http");
			if (StringUtils.isNotBlank(response)) {
				ottSnookerLeague = JsonUtil.parseJson2SnookerLeague(response);
				ottSnookerService.renewSnookerLeagueData(ottSnookerLeague);
			} else {
				logger.error("OttSchedualTask.retrieveSnookerLeagueData() failed on sid = " + sid + ", lid = " + lid);
			}
		}
	}

	public void retrieveSnookerPersonData(List<Map<String, Integer>> params) {
		logger.info("############ OttSchedualTask.retrieveSnookerPersonData() ############");
		Integer sid = null;
		Integer lid = null;
		String api = null;
		String response = null;
		OttSnookerPerson ottSnookerPerson = null;
		List<OttSnookerPerson> personList = null;
		List<OttSnookerPerson> personDetailList = null;
		String personApi = CustomizedPropertyConfigurer.getContextProperty("api.snooker_person");
		for (Map<String, Integer> map : params) {
			sid = map.get("sid");
			lid = map.get("lid");
			api = personApi + "&sId=" + sid + "&lId=" + lid;
//			response = HttpClientUtil.getInstance().sendHttpPost(api);
			response = HttpClientUtil.getInstance().sendHttpGetWithProxy(api, "10.12.251.1", 8080, "http");
			if (StringUtils.isNotBlank(response)) {
				personList = JsonUtil.parseJson2SnookerPersonList(response);
				if (personList.size() > 0) {
					personDetailList = new ArrayList<>();
					for (OttSnookerPerson person : personList) {
						api = personApi + "&sId=" + sid + "&lId=" + lid + "&pId=" + person.getPlayerId();
//						response = HttpClientUtil.getInstance().sendHttpPost(api);
						response = HttpClientUtil.getInstance().sendHttpGetWithProxy(api, "10.12.251.1", 8080, "http");
						if (StringUtils.isNotBlank(response)) {
							ottSnookerPerson = JsonUtil.parseJson2SnookerPerson(response);
							personDetailList.add(ottSnookerPerson);
						} else {
							logger.error("OttSchedualTask.retrieveSnookerPersonData() person detail failed on sid = " + sid + ", lid = " + lid + ", pid = " + person.getPlayerId());
						}
					}
					ottSnookerService.batchRenewSnookerPersonData(personDetailList);
				}
			} else {
				logger.error("OttSchedualTask.retrieveSnookerPersonData() person list failed on sid = " + sid + ", lid = " + lid);
			}
		}
	}

}
