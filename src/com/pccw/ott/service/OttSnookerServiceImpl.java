package com.pccw.ott.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pccw.ott.dao.OttSnookerLeagueDao;
import com.pccw.ott.dao.OttSnookerRankDao;
import com.pccw.ott.dao.OttSnookerScoreDao;
import com.pccw.ott.model.OttSnookerFrame;
import com.pccw.ott.model.OttSnookerLeague;
import com.pccw.ott.model.OttSnookerPoint;
import com.pccw.ott.model.OttSnookerRank;
import com.pccw.ott.model.OttSnookerScore;

@Service("ottSnookerService")
@Transactional
public class OttSnookerServiceImpl implements OttSnookerService {

	@Autowired
	private OttSnookerScoreDao ottSnookerScoreDao;
	
	@Autowired
	private OttSnookerRankDao ottSnookerRankDao;
	
	@Autowired
	private OttSnookerLeagueDao ottSnookerLeagueDao;
	
	@Override
	public void flushSnookerScoreData(List<OttSnookerScore> list) {
		clearSnookerScoreData();
		batchSaveSnookerScoreList(list);
	}
	
	@Override
	public List<OttSnookerScore> findSnookerScoreList(String leagueName, int first, int max, String sort, String order) {
		return ottSnookerScoreDao.findByParam(leagueName, first, max, sort, order);
	}

	@Override
	public Long findSnookerScoreListSize(String leagueName) {
		return ottSnookerScoreDao.findCountByParam(leagueName);
	}
	
	@Override
	public void batchSaveSnookerScoreList(List<OttSnookerScore> list) {
		ottSnookerScoreDao.batchSaveSnookerScoreList(list);
	}
	
	@Override
	public void batchUpdateSnookerScoreList(List<OttSnookerScore> updatedList) {
		ottSnookerScoreDao.batchUpdateSnookerScoreList(updatedList);
	}

	@Override
	public void batchDeleteSnookerScoreList(List<OttSnookerScore> deletedList) {
		ottSnookerScoreDao.batchDeleteSnookerScoreList(deletedList);
	}
	
	@Override
	public void batchSaveSnookerFrameList(OttSnookerScore score, List<OttSnookerFrame> insertedList) {
		ottSnookerScoreDao.batchSaveSnookerFrameList(score, insertedList);
	}

	@Override
	public void batchUpdateSnookerFrameList(List<OttSnookerFrame> updatedList) {
		ottSnookerScoreDao.batchUpdateSnookerFrameList(updatedList);
	}

	@Override
	public void batchDeleteSnookerFrameList(List<OttSnookerFrame> deletedList) {
		ottSnookerScoreDao.batchDeleteSnookerFrameList(deletedList);
	}
	
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
		clearSnookerRankData();
		batchSaveSnookerRankList(list);
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
	public void batchSaveSnookerPointList(OttSnookerRank rank, List<OttSnookerPoint> insertedList) {
		ottSnookerRankDao.batchSaveSnookerPointList(rank, insertedList);		
	}

	@Override
	public void batchUpdateSnookerPointList(List<OttSnookerPoint> updatedList) {
		ottSnookerRankDao.batchUpdateSnookerPointList(updatedList);
	}

	@Override
	public void batchDeleteSnookerPointList(List<OttSnookerPoint> deletedList) {
		ottSnookerRankDao.batchDeleteSnookerPointList(deletedList);
	}

	@Override
	public void flushSnookerLeagueData(OttSnookerLeague league) {
		this.deleteSnookerLeaugeByLeagueId(league.getLeagueId());
		this.saveSnookerLeauge(league);
	}
	
	private void clearSnookerScoreData() {
		ottSnookerScoreDao.deleteAll();
	}
	
	private void clearSnookerRankData() {
		ottSnookerRankDao.deleteAllSnookerRank();
	}
	
	private void deleteSnookerLeaugeByLeagueId(Integer leagueId) {
		ottSnookerLeagueDao.deleteByLeagueId(leagueId);
	}

	private void saveSnookerLeauge(OttSnookerLeague ottSnookerLeague) {
		ottSnookerLeagueDao.save(ottSnookerLeague);
	}



	
}
