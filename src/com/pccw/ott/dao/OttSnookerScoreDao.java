package com.pccw.ott.dao;

import java.util.List;
import java.util.Map;

import com.pccw.ott.model.OttSnookerFrame;
import com.pccw.ott.model.OttSnookerScore;

public interface OttSnookerScoreDao {

	public void batchSaveSnookerScoreList(List<OttSnookerScore> list);
	
	public void batchUpdateSnookerScoreList(List<OttSnookerScore> updatedList);

	public void batchDeleteSnookerScoreList(List<OttSnookerScore> deletedList);

	public void deleteAll();

	public List<OttSnookerScore> findByParam(String leagueName, String scoreType, int first, int max, String sort, String order);

	public Long findCountByParam(String leagueName, String scoreType);

	public void batchSaveSnookerFrameList(OttSnookerScore score, List<OttSnookerFrame> insertedList);

	public void batchUpdateSnookerFrameList(List<OttSnookerFrame> updatedList);

	public void batchDeleteSnookerFrameList(List<OttSnookerFrame> deletedList);

	public List<Map<String, Integer>> getLeagueParams();

	public void deleteByMatchId(Integer matchId, String scoreType);

	public void save(OttSnookerScore ottSnookerScore);

	public List<OttSnookerScore> findAllFixture();

}
