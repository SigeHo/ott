package com.pccw.ott.schedual;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

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
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date startDate=new Date();
		Date endDate=new Date();
        Calendar calendar = Calendar.getInstance();  
        calendar.setTime(startDate);  
        calendar.add(Calendar.DAY_OF_MONTH, -2);  
        startDate = calendar.getTime(); 
        calendar.setTime(endDate);  
        calendar.add(Calendar.DAY_OF_MONTH, +7);  
        endDate = calendar.getTime();
		String api = CustomizedPropertyConfigurer.getContextProperty("api.snooker_live");
		String response = HttpClientUtil.getInstance().sendHttpPost(api);
//		String response = HttpClientUtil.getInstance().readFile("e:/desktop/live.json");
		if (StringUtils.isNotBlank(response)) {
			List<OttSnookerScore> list = JsonUtil.parseJson2SnookerScore(response, "LIVE");
			ottSnookerService.flushSnookerScoreData(list);
		} else {
			logger.error("OttSchedualTask.retrieveSnookerLiveData() failed.");
		}
	}
	
	public void retrieveSnookerFixtureData() {
		logger.info("############ OttSchedualTask.retrieveSnookerFixtureData() ############");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date startDate=new Date();
		Date endDate=new Date();
        Calendar calendar = Calendar.getInstance();  
        calendar.setTime(startDate);  
        calendar.add(Calendar.DAY_OF_MONTH, -2);  
        startDate = calendar.getTime(); 
        calendar.setTime(endDate);  
        calendar.add(Calendar.DAY_OF_MONTH, +7);  
        endDate = calendar.getTime();
		String api = CustomizedPropertyConfigurer.getContextProperty("api.snooker_fixture");
		api += "&startDate=" + sdf.format(startDate) + "&endDate=" + sdf.format(endDate);
		String response = HttpClientUtil.getInstance().sendHttpPost(api);
//		String response = HttpClientUtil.getInstance().readFile("e:/desktop/live.json");
		if (StringUtils.isNotBlank(response)) {
			List<OttSnookerScore> list = JsonUtil.parseJson2SnookerScore(response, "FIXTURE");
			ottSnookerService.flushSnookerScoreData(list);
		} else {
			logger.error("OttSchedualTask.retrieveSnookerFixtureData() failed.");
		}
	}

	public void retrieveSnookerRankData() {
		logger.info("############ OttSchedualTask.retrieveSnookerRankData() ############");
		String api = CustomizedPropertyConfigurer.getContextProperty("api.snooker_rank");
		// String response = HttpClientUtil.getInstance().sendHttpPost(api);
		String response = HttpClientUtil.getInstance().readFile("e:/desktop/json.txt");
		if (StringUtils.isNotBlank(response)) {
			List<OttSnookerRank> list = JsonUtil.parseJson2SnookerRank(response);
			ottSnookerService.flushSnookerRankData(list);
		} else {
			logger.error("OttSchedualTask.retrieveSnookerRankData() failed.");
		}
	}
	
	public void retrieveSnookerLeagueData() {
		logger.info("############ OttSchedualTask.retrieveSnookerLeagueData() ############");
		List<Map<String, Integer>> params = ottSnookerService.getLeagueParams();
		Integer sid = null;
		Integer lid = null;
		String api = null;
		String leagueApi = CustomizedPropertyConfigurer.getContextProperty("api.snooker_league");
		for (Map<String, Integer> map : params) {
			sid = map.get("sid");
			lid = map.get("lid");
			api += leagueApi + "&sid=" + sid + "&lid=" + lid;
			String response = HttpClientUtil.getInstance().sendHttpPost(api);
		}
	}
	
	public static void main(String[] args) {
		OttSchedualTask task = new OttSchedualTask();
		task.retrieveSnookerFixtureData();
	}

}
