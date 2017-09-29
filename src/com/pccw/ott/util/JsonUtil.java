package com.pccw.ott.util;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.pccw.ott.model.OttSnookerFrame;
import com.pccw.ott.model.OttSnookerLeague;
import com.pccw.ott.model.OttSnookerLevel;
import com.pccw.ott.model.OttSnookerPerson;
import com.pccw.ott.model.OttSnookerPoint;
import com.pccw.ott.model.OttSnookerRank;
import com.pccw.ott.model.OttSnookerScore;

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
				snookerRank.setRank(personNode.path("rank").asInt());
				snookerRank.setPlayerId(personNode.path("pid").asInt());
				String[] nameArr = personNode.path("name").asText().split("\\|");
				snookerRank.setNameCn(nameArr[0]);
				snookerRank.setNameTr(nameArr[1]);
				snookerRank.setNameEn(nameArr[2]);
				snookerRank.setNationality(personNode.path("na").asText());
				snookerRank.setPtcPoint(personNode.path("ptc_point").asInt());
				snookerRank.setTotalPoint(personNode.path("total_point").asInt());
				ArrayNode pointNode = (ArrayNode) personNode.path("point");
				String[] pointArr = pointNode.get(0).asText().split("\\|");
				snookerRank.setPoint1(Integer.valueOf(pointArr[0]));
				snookerRank.setPoint2(Integer.valueOf(pointArr[1]));
				snookerRank.setPoint3(Integer.valueOf(pointArr[2]));
				OttSnookerPoint snookerPoint = null;
				List<OttSnookerPoint> pointList = new ArrayList<>();
				for (int i = 1; i < pointNode.size(); i++) {
					JsonNode snookerPointNode = pointNode.get(i);
					snookerPoint = new OttSnookerPoint();
					snookerPoint.setLeagueId(snookerPointNode.path("lid").asInt());
					snookerPoint.setLeagueNameCn(snookerPointNode.path("lcn").asText());
					snookerPoint.setLeagueNameEn(snookerPointNode.path("len").asText());
					snookerPoint.setLeagueNameTr(snookerPointNode.path("ltr").asText());
					snookerPoint.setSn(snookerPointNode.path("sn").asInt());
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
	
	public static List<OttSnookerScore> parseJson2SnookerScore(String jsonData) {
		List<OttSnookerScore> list = new ArrayList<>();

		ObjectMapper mapper = new ObjectMapper();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			JsonNode rootNode = mapper.readTree(jsonData);
			JsonNode liveNode = rootNode.path("live");
			Iterator<JsonNode> matchIt = liveNode.path("m").elements();
			OttSnookerScore snookerLive = null;
			while (matchIt.hasNext()) {
				JsonNode matchNode = matchIt.next();
				snookerLive = new OttSnookerScore();
				snookerLive.setMatchId(matchNode.path("mid").asInt());
				snookerLive.setSeasonId(matchNode.path("sid").asInt());
				String mt = matchNode.path("mt").asText();
				if (StringUtils.isNotBlank(mt))
					snookerLive.setMatchTime(sdf.parse(mt));
				snookerLive.setLeagueId(matchNode.path("lid").asInt());
				String[] leagueNameArr = matchNode.path("ln").asText().split("\\|");
				snookerLive.setLeagueNameCn(leagueNameArr[0]);
				snookerLive.setLeagueNameEn(leagueNameArr[1]);
				snookerLive.setLeagueNameTr(leagueNameArr[2]);
				snookerLive.setLeagueType(matchNode.path("lt").asText());
				snookerLive.setMatchLevel1(matchNode.path("mml").asText());
				snookerLive.setMatchLevel2(matchNode.path("ml").asText());
				snookerLive.setMatchGroup(matchNode.path("group").asText());
				snookerLive.setPlayerAId(matchNode.path("playA_id").asInt());
				String[] playerANameArr = matchNode.path("playA_cn").asText().split("\\|");
				snookerLive.setPlayerANameCn(playerANameArr[0]);
				snookerLive.setPlayerANameEn(playerANameArr[1]);
				snookerLive.setPlayerANameTr(playerANameArr[2]);
				snookerLive.setPlayerBId(matchNode.path("playB_id").asInt());
				String[] playerBNameArr = matchNode.path("playB_cn").asText().split("\\|");
				snookerLive.setPlayerBNameCn(playerBNameArr[0]);
				snookerLive.setPlayerBNameEn(playerBNameArr[1]);
				snookerLive.setPlayerBNameTr(playerBNameArr[2]);
				snookerLive.setPlayerAWinNum(matchNode.path("playA_wn").asInt());
				snookerLive.setPlayerBWinNum(matchNode.path("playB_wn").asInt());
				snookerLive.setMaxField(matchNode.path("mf").asInt());
				snookerLive.setCurrentField(matchNode.path("cf").asInt());
				snookerLive.setWinnerId(matchNode.path("wid").asInt());
				snookerLive.setWinReason(matchNode.path("wr").asText());
				snookerLive.setQuiterId(matchNode.path("qid").asInt());
				snookerLive.setQuitReason(matchNode.path("qr").asText());
				snookerLive.setBestPlayer(matchNode.path("bestId").asInt());
				snookerLive.setBestScore(matchNode.get("bestscore").asInt());
				snookerLive.setCurrentPlayer(matchNode.path("curId").asInt());
				snookerLive.setCurrentScore(matchNode.path("curscore").asInt());
				snookerLive.setSort(matchNode.path("group").asInt());
				Iterator<JsonNode> framesIt = matchNode.path("frames").elements();
				List<OttSnookerFrame> frameList = new ArrayList<>();
				OttSnookerFrame frame = null;
				while(framesIt.hasNext()) {
					JsonNode frameNode = framesIt.next();
					frame = new OttSnookerFrame();
					frame.setMatchSort(frameNode.path("match_sort").asInt());
					frame.setScoreA(frameNode.path("score_A").asInt());
					frame.setScoreB(frameNode.path("score_B").asInt());
					frame.setBestPlayer(frameNode.path("best_play").asInt());
					frame.setCscoreA(frameNode.path("cscore_A").asInt());
					frame.setCscoreB(frameNode.path("cscore_B").asInt());
					frameList.add(frame);
				}
				snookerLive.setOttSnookerFrameList(frameList);
			}
			list.add(snookerLive);
		} catch (IOException | ParseException e) {
			logger.error(e.toString());
			e.printStackTrace();
		}

		return list;
	}
	
	public static OttSnookerLeague parseJson2SnookerLeague(String jsonData) {
		OttSnookerLeague ottSnookerLeague = new OttSnookerLeague();
		ObjectMapper mapper = new ObjectMapper();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			JsonNode rootNode = mapper.readTree(jsonData);
			JsonNode leagueNode = rootNode.path("league");
			ottSnookerLeague.setLeagueId(leagueNode.path("lid").asInt());
			ottSnookerLeague.setLeagueNameCn(leagueNode.path("lcn").asText());
			ottSnookerLeague.setLeagueNameEn(leagueNode.path("len").asText());
			ottSnookerLeague.setLeagueNameTr(leagueNode.path("ltr").asText());
			String st = leagueNode.path("st").asText();
			if (StringUtils.isNotBlank(st)) 
				ottSnookerLeague.setStartTime(sdf.parse(st));
			String et = leagueNode.path("et").asText();
			if (StringUtils.isNotBlank(et)) 
				ottSnookerLeague.setEndTime(sdf.parse(et));
			ottSnookerLeague.setColor(leagueNode.path("color").asText());
			ottSnookerLeague.setRemark(leagueNode.path("rmark").asText());
			Iterator<JsonNode> levelIt = leagueNode.path("level").elements();
			List<OttSnookerLevel> ottSnookerLevelList = new ArrayList<>();
			while (levelIt.hasNext()) {
				JsonNode levelNode = levelIt.next();
				OttSnookerLevel level = new OttSnookerLevel();
				level.setLevelRounds(levelNode.path("levelRounds").asText());
				level.setMatchLevels(levelNode.path("matchLevels").asText());
				level.setMatchGroup(levelNode.path("group").asInt());
				level.setRemark(leagueNode.path("remark").asText());
				ottSnookerLevelList.add(level);
			}
			ottSnookerLeague.setOttSnookerLevelList(ottSnookerLevelList);
		} catch (IOException e) {
			e.printStackTrace();
			logger.error(e.toString());
		} catch (ParseException e) {
			e.printStackTrace();
			logger.error(e.toString());
		}
		
		return ottSnookerLeague;
	}
	
	public static List<OttSnookerPerson> parseJson2SnookerPersonList(String jsonData) {
		List<OttSnookerPerson> ottSnookerPersonList = new ArrayList<>();
		ObjectMapper mapper = new ObjectMapper();
		try {
			JsonNode rootNode = mapper.readTree(jsonData);
			Iterator<JsonNode> personIt = rootNode.path("person").path("per").elements();
			while (personIt.hasNext()) {
				JsonNode perNode = personIt.next();
				OttSnookerPerson person = new OttSnookerPerson();
				person.setPlayerId(perNode.path("pid").asLong());
				String[] nameArr = perNode.path("name").asText().split("\\|");
				person.setNameCn(nameArr[0]);
				person.setNameTr(nameArr[1]);
				person.setNameEn(nameArr[2]);
				person.setSex(perNode.path("sex").asText());
				person.setNationality(perNode.path("na").asText());
				ottSnookerPersonList.add(person);
			}
		} catch (IOException e) {
			e.printStackTrace();
			logger.error(e.toString());
		}
		return ottSnookerPersonList;
	}
	
	public static OttSnookerPerson parseJson2SnookerPerson(String jsonData) {
		OttSnookerPerson ottSnookerPerson = new OttSnookerPerson();
		ObjectMapper mapper = new ObjectMapper();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			JsonNode rootNode = mapper.readTree(jsonData);
			JsonNode perNode = rootNode.path("person").path("per");
			ottSnookerPerson.setPlayerId(perNode.path("pid").asLong());
			String[] nameArr = perNode.path("name").asText().split("\\|");
			ottSnookerPerson.setNameCn(nameArr[0]);
			ottSnookerPerson.setNameTr(nameArr[1]);
			ottSnookerPerson.setNameEn(nameArr[2]);
			ottSnookerPerson.setSex(perNode.path("sex").asText());
			ottSnookerPerson.setNationality(perNode.path("na").asText());
			String by = perNode.path("by").asText();
			if (StringUtils.isNotBlank(by))
				ottSnookerPerson.setBirthday(sdf.parse(by));
			ottSnookerPerson.setHeight(perNode.path("ht").asInt());
			ottSnookerPerson.setWeight(perNode.path("wt").asInt());
			ottSnookerPerson.setScore(perNode.path("sc").asInt());
			ottSnookerPerson.setMaxScoreNum(perNode.path("msc").asInt());
			ottSnookerPerson.setCurrentRank(perNode.path("cp").asInt());
			ottSnookerPerson.setHighestRank(perNode.path("hp").asInt());
			ottSnookerPerson.setTransferTime(perNode.path("bt").asText());
			ottSnookerPerson.setTotalMoney(perNode.path("tm").asInt());
			ottSnookerPerson.setWinRecord(perNode.path("ws").asInt());
			ottSnookerPerson.setPoint(perNode.path("ps").asInt());
			ottSnookerPerson.setExperience(perNode.path("ex").asText());
			ottSnookerPerson.setRemark(perNode.path("remark").asText());
		} catch (IOException e) {
			e.printStackTrace();
			logger.error(e.toString());
		} catch (ParseException e) {
			e.printStackTrace();
			logger.error(e.toString());
		}
		return ottSnookerPerson;
	}
	
	public static void main(String[] args) {
		String response = HttpClientUtil.getInstance().readFile("e:/desktop/person.json");
		parseJson2SnookerPersonList(response);
	}

}

