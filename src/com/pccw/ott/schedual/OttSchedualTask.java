package com.pccw.ott.schedual;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.pccw.ott.model.OttSnookerRank;
import com.pccw.ott.model.OttSnookerScore;
import com.pccw.ott.service.OttSnookerService;
import com.pccw.ott.util.HttpClientUtil;
import com.pccw.ott.util.JsonUtil;

public class OttSchedualTask {

	private static Logger logger = LoggerFactory.getLogger(OttSchedualTask.class);

	@Autowired
	private OttSnookerService ottSnookerService;
	
	public void retrieveSnookerFixtureData() {
		logger.info("############ OttSchedualTask.retrieveSnookerFixtureData() ############");
		// String response =
		// HttpClientUtil.getInstance().sendHttpPost(CustomizedPropertyConfigurer.getContextProperty("api.snooker_rank"));
		String response = HttpClientUtil.getInstance().readFile("e:/desktop/live.json");
		if (StringUtils.isNotBlank(response)) {
			List<OttSnookerScore> list = JsonUtil.parseJson2SnookerScore(response);
			ottSnookerService.flushSnookerScoreData(list);
		} else {
			logger.error("OttSchedualTask.retrieveSnookerFixtureData() failed.");
		}
	}

	public void retrieveSnookerRankData() {
		logger.info("############ OttSchedualTask.retrieveSnookerRankData() ############");
		// String response =
		// HttpClientUtil.getInstance().sendHttpPost(CustomizedPropertyConfigurer.getContextProperty("api.snooker_rank"));
		String response = HttpClientUtil.getInstance().readFile("e:/desktop/json.txt");
		if (StringUtils.isNotBlank(response)) {
			List<OttSnookerRank> list = JsonUtil.parseJson2SnookerRank(response);
			ottSnookerService.flushSnookerRankData(list);
		} else {
			logger.error("OttSchedualTask.retrieveSnookerRankData() failed.");
		}
	}

}
