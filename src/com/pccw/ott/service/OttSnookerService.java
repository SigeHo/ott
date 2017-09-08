package com.pccw.ott.service;

import java.util.List;

import com.pccw.ott.model.OttSnookerPoint;
import com.pccw.ott.model.OttSnookerRank;

public interface OttSnookerService {

	public void batchSaveSnookerRankList(List<OttSnookerRank> list);
	
	public void flushSnookerRankData(List<OttSnookerRank> list);

	public List<OttSnookerRank> findSnookerRankList(String playerName, int first, int max);

	public Long findSnookerRankListSize(String playerName);

	public List<OttSnookerPoint> findSnookerPointList(String playerId);

	public List<OttSnookerRank> findSnookerRankList(String playerName, int first, int max, String sort, String order);

	public void deleteSnookerRankById(Long rankId);

	public void batchUpdateSnookerRankList(List<OttSnookerRank> updatedList);

	public void batchDeleteSnookerRankList(List<OttSnookerRank> deletedList);

	public void batchSaveSnookerPointList(List<OttSnookerPoint> insertedList);

	public void batchUpdateSnookerPointList(List<OttSnookerPoint> updatedList);

	public void batchDeleteSnookerPointList(List<OttSnookerPoint> deletedList);
}
