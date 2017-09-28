package com.pccw.ott.dao;

import java.util.List;

import com.pccw.ott.model.OttSnookerScore;

public interface OttSnookerScoreDao {

	public void batchSaveSnookerScoreList(List<OttSnookerScore> list);
	
	public void batchUpdateSnookerScoreList(List<OttSnookerScore> updatedList);

	public void batchDeleteSnookerScoreList(List<OttSnookerScore> deletedList);

	public void deleteAll();

	public List<OttSnookerScore> findByParam(String leagueName, int first, int max, String sort, String order);

	public Long findCountByParam(String leagueName);

}
