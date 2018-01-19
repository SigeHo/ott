package com.pccw.ott.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.orm.hibernate5.HibernateCallback;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import com.pccw.ott.model.OttSnookerFrame;
import com.pccw.ott.model.OttSnookerScore;

@Repository("ottSnookerScoreDao")
@SuppressWarnings({ "rawtypes", "unchecked" })
public class OttSnookerScoreDaoImpl extends HibernateDaoSupport implements OttSnookerScoreDao {

	@Override
	public void batchSaveSnookerScoreList(List<OttSnookerScore> list) {
		this.getHibernateTemplate().execute(new HibernateCallback<Object>() {
			@Override
			public Object doInHibernate(Session session) throws HibernateException {
				for (OttSnookerScore ottSnookerScore : list) {
					session.save(ottSnookerScore);
				}
				session.flush();
				session.clear();
				return null;
			}
		});
	}

	@Override
	public void batchUpdateSnookerScoreList(List<OttSnookerScore> updatedList) {
		this.getHibernateTemplate().execute(new HibernateCallback<Object>() {
			@Override
			public Object doInHibernate(Session session) throws HibernateException {
				for (OttSnookerScore ottSnookerScore : updatedList) {
					String hql = "update OttSnookerScore set "
							+ "matchId = :matchId, "
							+ "seasonId = :seasonId, "
							+ "matchTime = :matchTime, "
							+ "leagueId = :leagueId, "
							+ "leagueNameCn = :leagueNameCn, "
							+ "leagueNameEn = :leagueNameEn, "
							+ "leagueNameTr = :leagueNameTr, "
							+ "leagueType = :leagueType, "
							+ "matchLevel1 = :matchLevel1, "
							+ "matchLevel2 = :matchLevel2, "
							+ "matchGroup = :matchGroup, "
							+ "playerAId = :playerAId, "
							+ "playerBId = :playerBId, "
							+ "playerANameCn = :playerANameCn, "
							+ "playerANameEn = :playerANameEn, "
							+ "playerANameTr = :playerANameTr, "
							+ "playerBNameCn = :playerBNameCn, "
							+ "playerBNameEn = :playerBNameEn, "
							+ "playerBNameTr = :playerBNameTr, "
							+ "playerAWinNum = :playerAWinNum, "
							+ "playerBWinNum = :playerBWinNum, "
							+ "maxField = :maxField, "
							+ "currentField = :currentField, "
							+ "winnerId = :winnerId, "
							+ "winReason = :winReason, "
							+ "quiterId = :quiterId, "
							+ "quitReason = :quitReason, "
							+ "bestPlayer = :bestPlayer, "
							+ "bestScore = :bestScore, "
							+ "status = :status, "
							+ "currentPlayer = :currentPlayer, "
							+ "currentScore = :currentScore, "
							+ "sort = :sort "
							+ "where scoreId = :scoreId";
					Query query = session.createQuery(hql);
					query.setParameter("matchId", ottSnookerScore.getMatchId());
					query.setParameter("seasonId", ottSnookerScore.getSeasonId());
					SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
					if (StringUtils.isNotBlank(ottSnookerScore.getMatchTimeStr())) {
						try {
							query.setParameter("matchTime", sdf.parse(ottSnookerScore.getMatchTimeStr()));
						} catch (ParseException e) {
							e.printStackTrace();
							logger.error(e.toString());
						}
					}
					query.setParameter("leagueId", ottSnookerScore.getLeagueId());
					query.setParameter("leagueNameCn", ottSnookerScore.getLeagueNameCn());
					query.setParameter("leagueNameEn", ottSnookerScore.getLeagueNameEn());
					query.setParameter("leagueNameTr", ottSnookerScore.getLeagueNameTr());
					query.setParameter("leagueType", ottSnookerScore.getLeagueType());
					query.setParameter("matchLevel1", ottSnookerScore.getMatchLevel1());
					query.setParameter("matchLevel2", ottSnookerScore.getMatchLevel2());
					query.setParameter("matchGroup", ottSnookerScore.getMatchGroup());
					query.setParameter("playerAId", ottSnookerScore.getPlayerAId());
					query.setParameter("playerBId", ottSnookerScore.getPlayerBId());
					query.setParameter("playerANameCn", ottSnookerScore.getPlayerANameCn());
					query.setParameter("playerANameEn", ottSnookerScore.getPlayerANameEn());
					query.setParameter("playerANameTr", ottSnookerScore.getPlayerANameTr());
					query.setParameter("playerBNameCn", ottSnookerScore.getPlayerBNameCn());
					query.setParameter("playerBNameEn", ottSnookerScore.getPlayerBNameEn());
					query.setParameter("playerBNameTr", ottSnookerScore.getPlayerBNameTr());
					query.setParameter("playerAWinNum", ottSnookerScore.getPlayerAWinNum());
					query.setParameter("playerBWinNum", ottSnookerScore.getPlayerBWinNum());
					query.setParameter("maxField", ottSnookerScore.getMaxField());
					query.setParameter("currentField", ottSnookerScore.getCurrentField());
					query.setParameter("winnerId", ottSnookerScore.getWinnerId());
					query.setParameter("winReason", ottSnookerScore.getWinReason());
					query.setParameter("quiterId", ottSnookerScore.getQuiterId());
					query.setParameter("quitReason", ottSnookerScore.getQuitReason());
					query.setParameter("bestPlayer", ottSnookerScore.getBestPlayer());
					query.setParameter("bestScore", ottSnookerScore.getBestScore());
					query.setParameter("status", ottSnookerScore.getStatus());
					query.setParameter("currentPlayer", ottSnookerScore.getCurrentPlayer());
					query.setParameter("currentScore", ottSnookerScore.getCurrentScore());
					query.setParameter("sort", ottSnookerScore.getSort());
					query.setParameter("scoreId", ottSnookerScore.getScoreId());
					query.executeUpdate();
				}
				session.flush();
				session.clear();
				return null;
			}
		});
	}

	@Override
	public void batchDeleteSnookerScoreList(List<OttSnookerScore> deletedList) {
		for (OttSnookerScore ottSnookerScore : deletedList) {
			if (null != ottSnookerScore.getOttSnookerFrameList() && ottSnookerScore.getOttSnookerFrameList().size() > 0) {
				OttSnookerScore rank = this.getHibernateTemplate().load(OttSnookerScore.class, ottSnookerScore.getScoreId());
				this.getHibernateTemplate().delete(rank);
			} else {
				this.getHibernateTemplate().execute(new HibernateCallback() {
					@Override
					public Object doInHibernate(Session session) throws HibernateException {
						Query query = session.createQuery("delete from OttSnookerScore where scoreId = :scoreId");
						query.setParameter("scoreId", ottSnookerScore.getScoreId());
						return query.executeUpdate();
					}
				});
			}
		}
	}

	@Override
	public void deleteAll() {
		this.getHibernateTemplate().execute(new HibernateCallback<Object>() {
			@Override
			public Object doInHibernate(Session session) throws HibernateException {
				session.createNativeQuery("truncate ott_snooker_score_frame").executeUpdate();
				session.createNativeQuery("truncate ott_snooker_frame").executeUpdate();
				session.createNativeQuery("truncate ott_snooker_score").executeUpdate();
				return null;
			}
		});
	}

	@Override
	public List<OttSnookerScore> findByParam(String leagueName, String scoreType, int first, int max, String sort, String order) {
		return this.getHibernateTemplate().execute(new HibernateCallback<List<OttSnookerScore>>() {
			@Override
			public List<OttSnookerScore> doInHibernate(Session session) throws HibernateException {
				String hql = "from OttSnookerScore ";
				hql += "where scoreType = '" + scoreType + "' ";
				if (StringUtils.isNotBlank(leagueName)) {
					hql += "and leagueNameCn like '%" + leagueName + "%' or leagueNameEn like '%" + leagueName + "%' or leagueNameTr like '%"
							+ leagueName + "%' ";
				}
				if (StringUtils.isNotBlank(sort) && StringUtils.isNotBlank(order)) {
					hql += "order by " + sort + " " + order;
				}
				Query query = session.createQuery(hql);
				query.setFirstResult(first);
				query.setMaxResults(max);
				return query.list();
			}
		});
	}

	@Override
	public Long findCountByParam(String leagueName, String scoreType) {
		String hql = "select count(*) from OttSnookerScore ";
		hql += "where scoreType = '" + scoreType + "' ";
		if (StringUtils.isNotBlank(leagueName)) {
			hql += "and leagueNameCn like '%" + leagueName + "%' or leagueNameEn like '%" + leagueName + "%' or leagueNameTr like '%" + leagueName
					+ "%' ";
		}
		return (Long) this.getHibernateTemplate().find(hql).get(0);
	}

	@Override
	public void batchSaveSnookerFrameList(OttSnookerScore score, List<OttSnookerFrame> insertedList) {
		this.getHibernateTemplate().execute(new HibernateCallback<Object>() {
			@Override
			public Object doInHibernate(Session session) throws HibernateException {
				for (OttSnookerFrame ottSnookerFrame : insertedList) {
					Long frameId = (Long) session.save(ottSnookerFrame);
					Query query = session.createNativeQuery("insert into ott_snooker_score_frame (score_id, frame_id) values(:scoreId, :frameId)");
					query.setParameter("scoreId", score.getScoreId());
					query.setParameter("frameId", frameId);
					query.executeUpdate();
				}
				session.flush();
				session.clear();
				return null;
			}
		});
	}

	@Override
	public void batchUpdateSnookerFrameList(List<OttSnookerFrame> updatedList) {
		this.getHibernateTemplate().execute(new HibernateCallback<Object>() {
			@Override
			public Object doInHibernate(Session session) throws HibernateException {
				for (OttSnookerFrame ottSnookerFrame : updatedList) {
					OttSnookerFrame frame = session.load(OttSnookerFrame.class, ottSnookerFrame.getFrameId());
					frame.setMatchSort(ottSnookerFrame.getMatchSort());
					frame.setSort(ottSnookerFrame.getSort());
					frame.setScoreA(ottSnookerFrame.getScoreA());
					frame.setScoreB(ottSnookerFrame.getScoreB());
					frame.setBestPlayer(ottSnookerFrame.getBestPlayer());
					frame.setCscoreA(ottSnookerFrame.getCscoreA());
					frame.setCscoreB(ottSnookerFrame.getCscoreB());
					session.update(frame);
				}
				session.flush();
				session.clear();
				return null;
			}
		});
	}

	@Override
	public void batchDeleteSnookerFrameList(List<OttSnookerFrame> deletedList) {
		this.getHibernateTemplate().execute(new HibernateCallback<Object>() {
			@Override
			public Object doInHibernate(Session session) throws HibernateException {
				for (OttSnookerFrame ottSnookerFrame : deletedList) {
					session.createNativeQuery("delete from ott_snooker_score_frame where frame_id = " + ottSnookerFrame.getFrameId()).executeUpdate();
					OttSnookerFrame frame = session.load(OttSnookerFrame.class, ottSnookerFrame.getFrameId());
					session.delete(frame);
				}
				session.flush();
				session.clear();
				return null;
			}
		});
	}
	
	@Override
	public List<Map<String, Integer>> getLeagueParams() {
		List<Map<String, Integer>> params = new ArrayList<>();
		params = (List<Map<String, Integer>>) this.getHibernateTemplate().execute(new HibernateCallback() {
			@Override
			public Object doInHibernate(Session session) throws HibernateException {
				String hql = "select distinct new map(seasonId as sid, leagueId as lid) from OttSnookerScore where scoreType = 'FIXTURE' order by seasonId";
				Query query = session.createQuery(hql);
				return query.list();
			}
		});
		return params;
	}
	
	@Override
	public void deleteByMatchId(Integer matchId, String scoreType) {
		List<OttSnookerScore> list = (List<OttSnookerScore>) this.getHibernateTemplate().find("from OttSnookerScore where scoreType = ? and matchId = ?", scoreType, matchId);
		if (list.size() > 0) {
			this.getHibernateTemplate().delete(list.get(0));
		}
	}
	
	@Override
	public void save(OttSnookerScore ottSnookerScore) {
		this.getHibernateTemplate().save(ottSnookerScore);
	}
	
@Override
	public List<OttSnookerScore> findAllFixture() {
		return (List<OttSnookerScore>) this.getHibernateTemplate().find("from OttSnookerScore where scoreType = 'FIXTURE'");
	}	

}

