package com.pccw.ott.util;

import java.io.IOException;
import java.lang.reflect.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.pccw.ott.dto.OttNpvrMappingDto;
import com.pccw.ott.dto.OttNpvrSearchDto;
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
	
	public static List<OttSnookerScore> parseJson2SnookerScore(String jsonData, String scoreType) {
		List<OttSnookerScore> list = new ArrayList<>();

		ObjectMapper mapper = new ObjectMapper();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		try {
			JsonNode rootNode = mapper.readTree(jsonData);
			JsonNode liveNode = rootNode.path("live");
			Iterator<JsonNode> matchIt = liveNode.path("m").elements();
			OttSnookerScore snookerScore = null;
			while (matchIt.hasNext()) {
				JsonNode matchNode = matchIt.next();
				snookerScore = new OttSnookerScore();
				snookerScore.setScoreType(scoreType);
				snookerScore.setMatchId(matchNode.path("mid").asInt());
				snookerScore.setSeasonId(matchNode.path("sid").asInt());
				String mt = matchNode.path("mt").asText();
				if (StringUtils.isNotBlank(mt))
					snookerScore.setMatchTime(sdf.parse(mt));
				snookerScore.setLeagueId(matchNode.path("lid").asInt());
				String[] leagueNameArr = matchNode.path("ln").asText().split("\\|");
				snookerScore.setLeagueNameCn(leagueNameArr[0]);
				snookerScore.setLeagueNameEn(leagueNameArr[1]);
				snookerScore.setLeagueNameTr(leagueNameArr[2]);
				snookerScore.setLeagueType(matchNode.path("lt").asText());
				snookerScore.setMatchLevel1(matchNode.path("mml").asText());
				snookerScore.setMatchLevel2(matchNode.path("ml").asText());
				snookerScore.setMatchGroup(matchNode.path("group").asText());
				snookerScore.setPlayerAId(matchNode.path("playA_id").asInt());
				String[] playerANameArr = matchNode.path("playA_cn").asText().split("\\|");
				snookerScore.setPlayerANameCn(playerANameArr[0]);
				snookerScore.setPlayerANameEn(playerANameArr[1]);
				snookerScore.setPlayerANameTr(playerANameArr[2]);
				snookerScore.setPlayerBId(matchNode.path("playB_id").asInt());
				String[] playerBNameArr = matchNode.path("playB_cn").asText().split("\\|");
				snookerScore.setPlayerBNameCn(playerBNameArr[0]);
				snookerScore.setPlayerBNameEn(playerBNameArr[1]);
				snookerScore.setPlayerBNameTr(playerBNameArr[2]);
				snookerScore.setPlayerAWinNum(matchNode.path("playA_wn").asInt());
				snookerScore.setPlayerBWinNum(matchNode.path("playB_wn").asInt());
				snookerScore.setMaxField(matchNode.path("mf").asInt());
				snookerScore.setCurrentField(matchNode.path("cf").asInt());
				snookerScore.setWinnerId(matchNode.path("wid").asInt());
				snookerScore.setWinReason(matchNode.path("wr").asText());
				snookerScore.setQuiterId(matchNode.path("qid").asInt());
				snookerScore.setQuitReason(matchNode.path("qr").asText());
				snookerScore.setBestPlayer(matchNode.path("bestId").asInt());
				snookerScore.setBestScore(matchNode.get("bestscore").asInt());
				snookerScore.setCurrentPlayer(matchNode.path("curId").asInt());
				snookerScore.setCurrentScore(matchNode.path("curscore").asInt());
				snookerScore.setSort(matchNode.path("sort").asInt());
				Iterator<JsonNode> framesIt = matchNode.path("frames").elements();
				List<OttSnookerFrame> frameList = new ArrayList<>();
				OttSnookerFrame frame = null;
				while(framesIt.hasNext()) {
					JsonNode frameNode = framesIt.next();
					frame = new OttSnookerFrame();
					frame.setFrameType(scoreType);
					frame.setMatchSort(frameNode.path("match_sort").asInt());
					frame.setSort(frameNode.path("sort").asInt());
					frame.setScoreA(frameNode.path("score_A").asInt());
					frame.setScoreB(frameNode.path("score_B").asInt());
					frame.setBestPlayer(frameNode.path("best_play").asInt());
					frame.setCscoreA(frameNode.path("cscore_A").asInt());
					frame.setCscoreB(frameNode.path("cscore_B").asInt());
					frameList.add(frame);
				}
				snookerScore.setOttSnookerFrameList(frameList);
				list.add(snookerScore);
			}
		} catch (IOException | ParseException e) {
			logger.error(e.getMessage());
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
				person.setPlayerId(perNode.path("pid").asInt());
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
			ottSnookerPerson.setPlayerId(perNode.path("pid").asInt());
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
			String bt = perNode.path("bt").asText();
			if (StringUtils.isNotBlank(bt))
				ottSnookerPerson.setTransferTime(sdf.parse(bt));
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
	
	public static List<OttNpvrMappingDto> parseJson2NpvrMapping(String jsonData) {
		ObjectMapper mapper = new ObjectMapper();
		List<OttNpvrMappingDto> list = new ArrayList<>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			OttNpvrMappingDto npvrMappingDto = null;
			JsonNode rootNode = mapper.readTree(jsonData);
			Iterator<JsonNode> fixturesNodeIt = rootNode.path("data").path("fixtures").path("fixture").elements();
			while (fixturesNodeIt.hasNext()) {
				npvrMappingDto = new OttNpvrMappingDto();
				JsonNode fixtureNode = fixturesNodeIt.next();
				npvrMappingDto.setFixtureId(fixtureNode.path("id").asText());
				npvrMappingDto.setTournament(fixtureNode.path("league").path("tour_name").path("lang").get(0).path("short_name").asText());
				String officialStartTime = fixtureNode.path("official_start_time").asText();
				npvrMappingDto.setStartDateTime(officialStartTime.replace("T", " "));
				JsonNode homeTeamNode = fixtureNode.path("home_team");
				JsonNode awayTeamNode = fixtureNode.path("away_team");
				npvrMappingDto.setTeamA(homeTeamNode.path("name").path("lang").get(0).path("full_name").asText());
				npvrMappingDto.setTeamB(awayTeamNode.path("name").path("lang").get(0).path("full_name").asText());
				JsonNode fixtureStatus = fixtureNode.path("fixture_status");
				npvrMappingDto.setStatus(fixtureStatus.path("match_status_name").path("lang").get(0).path("full_name").asText());
				list.add(npvrMappingDto);
			}
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		return list;
	}
	
	public static List<OttNpvrMappingDto> filterByNpvrSearchDto(String jsonData, OttNpvrSearchDto npvrSearchDto) {
		ObjectMapper mapper = new ObjectMapper();
		List<OttNpvrMappingDto> list = new ArrayList<>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			OttNpvrMappingDto npvrMappingDto = null;
			JsonNode rootNode = mapper.readTree(jsonData);
			Iterator<JsonNode> fixturesNodeIt = rootNode.path("data").path("fixtures").elements();
			while (fixturesNodeIt.hasNext()) {
				JsonNode fixtureNode = fixturesNodeIt.next().path("fixture");
				String tour_name = fixtureNode.path("league").path("tour_name").path("lang").get(0).path("short_name").asText();
				String officialStartTimeStr = fixtureNode.path("official_start_time").asText().replace("T", " ");
				Date officialStartTime = sdf.parse(officialStartTimeStr);
				Date fromDateTime  = sdf.parse(npvrSearchDto.getFromDate() + " " + npvrSearchDto.getFromTime());
				Date toDateTime  = sdf.parse(npvrSearchDto.getToDate() + " " + npvrSearchDto.getToTime());
				if (!npvrSearchDto.getTournament().equals(tour_name)) {
					continue;
				}
				if (!(fromDateTime.before(officialStartTime) && toDateTime.after(officialStartTime))) {
					continue;
				}
				npvrMappingDto = new OttNpvrMappingDto();
				npvrMappingDto.setFixtureId(fixtureNode.path("id").asText());
				npvrMappingDto.setStartDateTime(officialStartTimeStr);
				JsonNode homeTeamNode = fixtureNode.path("home_team");
				JsonNode awayTeamNode = fixtureNode.path("away_team");
				npvrMappingDto.setTeamA(homeTeamNode.path("name").path("lang").get(0).path("full_name").asText());
				npvrMappingDto.setTeamA(awayTeamNode.path("name").path("lang").get(0).path("full_name").asText());
				JsonNode fixtureStatus = fixtureNode.path("fixture_status");
				npvrMappingDto.setStatus(fixtureStatus.path("match_status_name").path("lang").get(0).path("full_name").asText());
				
				list.add(npvrMappingDto);
			}
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		} catch (ParseException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		return list;
	}

	public static List<Map<String, String>> parseJson2SoccerLeagueList(String response) {
		ObjectMapper mapper = new ObjectMapper();
		List<Map<String, String>> leagueList = new ArrayList<>();
		try {
			JsonNode rootNode = mapper.readTree(response);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		return null;
	}

	public static List<String> getInvalidNpvrIds(String response, String[] npvrIdArr) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			JsonNode rootNode = mapper.readTree(response);
			Iterator<JsonNode> docsIt = rootNode.path("data").path("response").path("docs").elements();
			while(docsIt.hasNext()) {
				JsonNode docNode = docsIt.next();
				String id = docNode.path("p_vimProgId").asText();
				int index = ArrayUtils.indexOf(npvrIdArr, id);
				if (index > -1) {
					npvrIdArr = (String[]) ArrayUtils.remove(npvrIdArr, index);
				}
			}
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		return Arrays.asList(npvrIdArr);
	}

}

