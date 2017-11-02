package com.pccw.ott.service;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import com.pccw.ott.model.OttSnookerFrame;
import com.pccw.ott.model.OttSnookerLeague;
import com.pccw.ott.model.OttSnookerLevel;
import com.pccw.ott.model.OttSnookerPerson;
import com.pccw.ott.model.OttSnookerPoint;
import com.pccw.ott.model.OttSnookerRank;
import com.pccw.ott.model.OttSnookerScore;

public interface OttSnookerService {

	/* Snook Score */
	public void renewSnookerScoreData(List<OttSnookerScore> list, String scoreType);

	public List<OttSnookerScore> findSnookerScoreList(String leagueName, String scoreType, int first, int max, String sort, String order);

	public Long findSnookerScoreListSize(String leagueName, String scoreType);
	
	public void batchSaveSnookerScoreList(List<OttSnookerScore> insertedList);

	public void batchUpdateSnookerScoreList(List<OttSnookerScore> updatedList);

	public void batchDeleteSnookerScoreList(List<OttSnookerScore> deletedList);
	
	public void batchSaveSnookerFrameList(OttSnookerScore score, List<OttSnookerFrame> insertedList);

	public void batchUpdateSnookerFrameList(List<OttSnookerFrame> updatedList);

	public void batchDeleteSnookerFrameList(List<OttSnookerFrame> deletedList);

	/* Snook Rank */
	public void batchSaveSnookerRankList(List<OttSnookerRank> list);

	public void renewSnookerRankData(List<OttSnookerRank> list);

	public List<OttSnookerRank> findSnookerRankList(String playerName, int first, int max);

	public Long findSnookerRankListSize(String playerName);

	public List<OttSnookerPoint> findSnookerPointList(String playerId);

	public List<OttSnookerRank> findSnookerRankList(String playerName, int first, int max, String sort, String order);

	public void deleteSnookerRankById(Long rankId);

	public void batchUpdateSnookerRankList(List<OttSnookerRank> updatedList);

	public void batchDeleteSnookerRankList(List<OttSnookerRank> deletedList);

	public void batchSaveSnookerPointList(OttSnookerRank rank, List<OttSnookerPoint> insertedList);

	public void batchUpdateSnookerPointList(List<OttSnookerPoint> updatedList);

	public void batchDeleteSnookerPointList(List<OttSnookerPoint> deletedList);

	/* Snooker League */
	public List<Map<String, Integer>> getLeagueParams();
	
	public void renewSnookerLeagueData(OttSnookerLeague league);
	
	public List<OttSnookerLeague> findSnookerLeagueList(String leagueName, int first, int max, String sort, String order);

	public Long findSnookerLeagueListSize(String leagueName);

	public void batchSaveSnookerLeagueList(List<OttSnookerLeague> insertedList);

	public void batchUpdateSnookerLeagueList(List<OttSnookerLeague> updatedList);

	public void batchDeleteSnookerLeagueList(List<OttSnookerLeague> deletedList);

	public void batchSaveSnookerLevelList(OttSnookerLeague league, List<OttSnookerLevel> insertedList);

	public void batchUpdateSnookerLevelList(List<OttSnookerLevel> updatedList);

	public void batchDeleteSnookerLevelList(List<OttSnookerLevel> deletedList);
	
	/* Snooker Player */
	
	public void batchRenewSnookerPersonData(List<OttSnookerPerson> personDetailList);
	
	public List<OttSnookerPerson> findSnookerPersonList(String playerName, int first, int max, String sort, String order);

	public Long findSnookerPersonListSize(String playerName);

	public void batchSaveSnookerPersonList(List<OttSnookerPerson> insertedList) throws ParseException;

	public void batchUpdateSnookerPersonList(List<OttSnookerPerson> updatedList) throws ParseException;

	public void batchDeleteSnookerPersonList(List<OttSnookerPerson> deletedList);






}
