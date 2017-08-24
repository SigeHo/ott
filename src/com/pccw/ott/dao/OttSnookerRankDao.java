package com.pccw.ott.dao;

import java.util.List;

import com.pccw.ott.model.OttSnookerPoint;
import com.pccw.ott.model.OttSnookerRank;

public interface OttSnookerRankDao {
	
	public void batchSave(List<OttSnookerRank> list);

	public List<OttSnookerRank> findByPlayerName(String playerName, int first, int max);

	public Long findCountByPlayerName(String playerName);

	public List<OttSnookerPoint> findPointByPlayerId(String playerId);

	public List<OttSnookerRank> findByPlayerName(String playerName, int first, int max, String sort, String order);

}
