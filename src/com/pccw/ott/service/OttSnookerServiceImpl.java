package com.pccw.ott.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pccw.ott.dao.OttSnookerRankDao;
import com.pccw.ott.model.OttSnookerPoint;
import com.pccw.ott.model.OttSnookerRank;

@Service("ottSnookerService")
@Transactional
public class OttSnookerServiceImpl implements OttSnookerService {

	@Autowired
	private OttSnookerRankDao ottSnookerRankDao;
	
	@Override
	public void batchSaveSnookerRankList(List<OttSnookerRank> list) {
		ottSnookerRankDao.batchSaveRankList(list);
	}

	@Override
	public List<OttSnookerRank> findSnookerRankList(String playerName, int first, int max) {
		return ottSnookerRankDao.findByPlayerName(playerName, first, max);
	}

	@Override
	public Long findSnookerRankListSize(String playerName) {
		return ottSnookerRankDao.findCountByPlayerName(playerName);
	}

	@Override
	public List<OttSnookerPoint> findSnookerPointList(String playerId) {
		return ottSnookerRankDao.findPointByPlayerId(playerId);
	}

	@Override
	public List<OttSnookerRank> findSnookerRankList(String playerName, int first, int max, String sort, String order) {
		return ottSnookerRankDao.findByPlayerName(playerName, first, max, sort, order);
	}

	@Override
	public void deleteSnookerRankById(Long rankId) {
		ottSnookerRankDao.deleteById(rankId);
	}

	@Override
	public void flushSnookerRankData(List<OttSnookerRank> list) {
		cleanUpOldData();
		batchSaveSnookerRankList(list);
	}

	private void cleanUpOldData() {
		ottSnookerRankDao.deleteAllSnookerRank();
	}

	@Override
	public void batchUpdateSnookerRankList(List<OttSnookerRank> updatedList) {
		ottSnookerRankDao.batchUpdateSnookerRankList(updatedList);
	}

	@Override
	public void batchDeleteSnookerRankList(List<OttSnookerRank> deletedList) {
		ottSnookerRankDao.batchDeleteSnookerRankList(deletedList);		
	}

	@Override
	public void batchSaveSnookerPointList(List<OttSnookerPoint> insertedList) {
		ottSnookerRankDao.batchSaveSnookerPointList(insertedList);		
	}

	@Override
	public void batchUpdateSnookerPointList(List<OttSnookerPoint> updatedList) {
		ottSnookerRankDao.batchUpdateSnookerPointList(updatedList);
	}

	@Override
	public void batchDeleteSnookerPointList(List<OttSnookerPoint> deletedList) {
		ottSnookerRankDao.batchDeleteSnookerPointList(deletedList);
	}

}
