package com.pccw.ott.dao;

import java.util.List;

import com.pccw.ott.model.OttSnookerPoint;
import com.pccw.ott.model.OttSnookerRank;

public interface OttSnookerRankDao {
	
	public void batchSaveRankList(List<OttSnookerRank> list);

	public List<OttSnookerRank> findByPlayerName(String playerName, int first, int max);

	public Long findCountByPlayerName(String playerName);

	public List<OttSnookerRank> findByPlayerName(String playerName, int first, int max, String sort, String order);

	public void deleteById(Long rankId);

	public void deleteAllSnookerRank();

	public void batchUpdateSnookerRankList(List<OttSnookerRank> updatedList);

	public void batchDeleteSnookerRankList(List<OttSnookerRank> deletedList);

	public void batchSaveSnookerPointList(OttSnookerRank rank, List<OttSnookerPoint> insertedList);

	public void batchUpdateSnookerPointList(List<OttSnookerPoint> updatedList);

	public void batchDeleteSnookerPointList(List<OttSnookerPoint> deletedList);

}
