package com.pccw.ott.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.pccw.ott.model.OttSnookerPoint;
import com.pccw.ott.model.OttSnookerRank;

public class JsonUtil {

	private static Logger logger = LoggerFactory.getLogger(JsonUtil.class);

	public static List<OttSnookerRank> parseJson2SnookerRank(String jsonData) {
		List<OttSnookerRank> list = new ArrayList<>();

		ObjectMapper mapper = new ObjectMapper();
		try {
			JsonNode rootNode = mapper.readTree(jsonData);
			JsonNode rankNode = rootNode.path("rank");
			String rankTitle = rankNode.path("ts").asText();
			String rankYear = rankNode.path("sid").asText();
			Iterator<JsonNode> personIt = rankNode.path("person").elements();
			OttSnookerRank snookerRank = null;
			while (personIt.hasNext()) {
				JsonNode personNode = personIt.next();
				snookerRank = new OttSnookerRank();
				snookerRank.setRankTitle(rankTitle);
				snookerRank.setRankYear(rankYear);
				snookerRank.setRank(personNode.path("rank").asLong());
				snookerRank.setPlayerId(personNode.path("pid").asLong());
				String[] nameArr = personNode.path("name").asText().split("\\|");
				snookerRank.setNameCn(nameArr[0]);
				snookerRank.setNameTr(nameArr[1]);
				snookerRank.setNameEn(nameArr[2]);
				snookerRank.setNationality(personNode.path("na").asText());
				snookerRank.setPtcPoint(personNode.path("ptc_point").asLong());
				snookerRank.setTotalPoint(personNode.path("total_point").asLong());
				ArrayNode pointNode = (ArrayNode) personNode.path("point");
				String[] pointArr = pointNode.get(0).asText().split("\\|");
				snookerRank.setPoint1(Long.valueOf(pointArr[0]));
				snookerRank.setPoint2(Long.valueOf(pointArr[1]));
				snookerRank.setPoint3(Long.valueOf(pointArr[2]));
				OttSnookerPoint snookerPoint = null;
				List<OttSnookerPoint> pointList = new ArrayList<>();
				for (int i = 1; i < pointNode.size(); i++) {
					JsonNode snookerPointNode = pointNode.get(i);
					snookerPoint = new OttSnookerPoint();
					snookerPoint.setLeagueId(snookerPointNode.path("lid").asLong());
					snookerPoint.setLeagueNameCn(snookerPointNode.path("lcn").asText());
					snookerPoint.setLeagueNameEn(snookerPointNode.path("len").asText());
					snookerPoint.setLeagueNameTr(snookerPointNode.path("ltr").asText());
					snookerPoint.setSn(snookerPointNode.path("sn").asLong());
					pointList.add(snookerPoint);
				}
				snookerRank.setSnookerPointList(pointList);
				list.add(snookerRank);
			}
		} catch (IOException e) {
			logger.error(e.toString());
			e.printStackTrace();
		}

		return list;
	}
	
	public static void main(String[] args) {
		String response = HttpClientUtil.readFile("e:/desktop/json.txt");
		parseJson2SnookerRank(response);
	}

}
