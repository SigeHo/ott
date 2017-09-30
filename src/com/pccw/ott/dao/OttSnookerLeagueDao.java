package com.pccw.ott.dao;

import java.util.List;

import com.pccw.ott.model.OttSnookerLeague;
import com.pccw.ott.model.OttSnookerLevel;

public interface OttSnookerLeagueDao {
	
	public void deleteByLeagueId(Integer leagueId);
	
	public void save(OttSnookerLeague ottSnookerLeague);

	public List<OttSnookerLeague> findByParam(String leagueName, int first, int max, String sort, String order);

	public Long findCountByParam(String leagueName);

	public void batchSaveSnookerLeagueList(List<OttSnookerLeague> insertedList);

	public void batchUpdateSnookerLeagueList(List<OttSnookerLeague> updatedList);

	public void batchDeleteSnookerLeagueList(List<OttSnookerLeague> deletedList);

	public void batchSaveSnookerLevelList(OttSnookerLeague league, List<OttSnookerLevel> insertedList);

	public void batchUpdateSnookerLevelList(List<OttSnookerLevel> updatedList);

	public void batchDeleteSnookerLevelList(List<OttSnookerLevel> deletedList);

}
