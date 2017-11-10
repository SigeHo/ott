package com.pccw.ott.service;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pccw.ott.dao.OttSnookerLeagueDao;
import com.pccw.ott.dao.OttSnookerPersonDao;
import com.pccw.ott.dao.OttSnookerRankDao;
import com.pccw.ott.dao.OttSnookerScoreDao;
import com.pccw.ott.model.OttSnookerFrame;
import com.pccw.ott.model.OttSnookerLeague;
import com.pccw.ott.model.OttSnookerLevel;
import com.pccw.ott.model.OttSnookerPerson;
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
	
	@Autowired
	private OttSnookerPersonDao ottSnookerPersonDao;
	
	@Override
	public void renewSnookerScoreData(List<OttSnookerScore> list, String scoreType) {
		for (OttSnookerScore ottSnookerScore : list) {
			ottSnookerScoreDao.deleteByMatchId(ottSnookerScore.getMatchId(), scoreType);
			ottSnookerScoreDao.save(ottSnookerScore);
		}
	}
	
	@Override
	public List<OttSnookerScore> findSnookerScoreList(String leagueName, String scoreType, int first, int max, String sort, String order) {
		return ottSnookerScoreDao.findByParam(leagueName, scoreType, first, max, sort, order);
	}

	@Override
	public Long findSnookerScoreListSize(String leagueName, String scoreType) {
		return ottSnookerScoreDao.findCountByParam(leagueName, scoreType);
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
	public void renewSnookerRankData(List<OttSnookerRank> list) {
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
	public List<Map<String, Integer>> getLeagueParams() {
		return ottSnookerScoreDao.getLeagueParams();
	}

	@Override
	public void renewSnookerLeagueData(OttSnookerLeague league) {
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

	@Override
	public List<OttSnookerLeague> findSnookerLeagueList(String leagueName, int first, int max, String sort, String order) {
		return ottSnookerLeagueDao.findByParam(leagueName, first, max, sort, order);
	}

	@Override
	public Long findSnookerLeagueListSize(String leagueName) {
		return ottSnookerLeagueDao.findCountByParam(leagueName);
	}

	@Override
	public void batchSaveSnookerLeagueList(List<OttSnookerLeague> insertedList) {
		ottSnookerLeagueDao.batchSaveSnookerLeagueList(insertedList);		
	}

	@Override
	public void batchUpdateSnookerLeagueList(List<OttSnookerLeague> updatedList) {
		ottSnookerLeagueDao.batchUpdateSnookerLeagueList(updatedList);
	}

	@Override
	public void batchDeleteSnookerLeagueList(List<OttSnookerLeague> deletedList) {
		ottSnookerLeagueDao.batchDeleteSnookerLeagueList(deletedList);
	}

	@Override
	public void batchSaveSnookerLevelList(OttSnookerLeague league, List<OttSnookerLevel> insertedList) {
		ottSnookerLeagueDao.batchSaveSnookerLevelList(league, insertedList);		
	}

	@Override
	public void batchUpdateSnookerLevelList(List<OttSnookerLevel> updatedList) {
		ottSnookerLeagueDao.batchUpdateSnookerLevelList(updatedList);		
	}

	@Override
	public void batchDeleteSnookerLevelList(List<OttSnookerLevel> deletedList) {
		ottSnookerLeagueDao.batchDeleteSnookerLevelList(deletedList);		
	}
	
	@Override
	public List<Map<String, Object>> retrieveLeagueListForNpvr() {
		return ottSnookerLeagueDao.findLeagueList();
	}
	
	@Override
	public void batchRenewSnookerPersonData(List<OttSnookerPerson> personDetailList) {
		OttSnookerPerson person = null;
		for (int i = 0; i < personDetailList.size(); i++) {
			person = personDetailList.get(i);
			ottSnookerPersonDao.deleteById(person.getPlayerId());
			ottSnookerPersonDao.save(person);
		}
	}

	@Override
	public List<OttSnookerPerson> findSnookerPersonList(String playerName, int first, int max, String sort, String order) {
		return ottSnookerPersonDao.findByPlayerName(playerName, first, max, sort, order);
	}

	@Override
	public Long findSnookerPersonListSize(String playerName) {
		return ottSnookerPersonDao.countByPlayerName(playerName);
	}

	@Override
	public void batchSaveSnookerPersonList(List<OttSnookerPerson> insertedList) throws ParseException {
		ottSnookerPersonDao.batchSaveSnookerPersonList(insertedList);
	}

	@Override
	public void batchUpdateSnookerPersonList(List<OttSnookerPerson> updatedList) throws ParseException {
		ottSnookerPersonDao.batchUpdateSnookerPersonList(updatedList);
	}

	@Override
	public void batchDeleteSnookerPersonList(List<OttSnookerPerson> deletedList) {
		ottSnookerPersonDao.batchDeleteSnookerPersonList(deletedList);
	}

}
