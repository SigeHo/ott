package com.pccw.ott.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.orm.hibernate5.HibernateCallback;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

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
					try {
						OttSnookerScore score = session.load(OttSnookerScore.class, ottSnookerScore.getScoreId());
						score.setMatchId(ottSnookerScore.getMatchId());
						score.setSeasonId(ottSnookerScore.getSeasonId());
						String matchTimeStr = ottSnookerScore.getMatchTimeStr();
						if (StringUtils.isNotBlank(matchTimeStr)) {
							SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
							score.setMatchTime(sdf.parse(matchTimeStr));
						}
						score.setLeagueId(ottSnookerScore.getLeagueId());
						score.setLeagueNameCn(ottSnookerScore.getLeagueNameCn());
						score.setLeagueNameEn(ottSnookerScore.getLeagueNameEn());
						score.setLeagueNameTr(ottSnookerScore.getLeagueNameTr());
						score.setLeagueType(ottSnookerScore.getLeagueType());
						score.setMatchLevel1(ottSnookerScore.getMatchLevel1());
						score.setMatchLevel2(ottSnookerScore.getMatchLevel2());
						score.setMatchGroup(ottSnookerScore.getMatchGroup());
						score.setPlayerAId(ottSnookerScore.getPlayerAId());
						score.setPlayerBId(ottSnookerScore.getPlayerBId());
						score.setPlayerANameCn(ottSnookerScore.getPlayerANameCn());
						score.setPlayerANameEn(ottSnookerScore.getPlayerANameEn());
						score.setPlayerANameTr(ottSnookerScore.getPlayerANameTr());
						score.setPlayerBNameCn(ottSnookerScore.getPlayerBNameCn());
						score.setPlayerBNameEn(ottSnookerScore.getPlayerBNameEn());
						score.setPlayerBNameTr(ottSnookerScore.getPlayerBNameTr());
						score.setPlayerAWinNum(ottSnookerScore.getPlayerAWinNum());
						score.setPlayerBWinNum(ottSnookerScore.getPlayerBWinNum());
						score.setMaxField(ottSnookerScore.getMaxField());
						score.setCurrentField(ottSnookerScore.getCurrentField());
						score.setWinnerId(ottSnookerScore.getWinnerId());
						score.setWinReason(ottSnookerScore.getWinReason());
						score.setQuiterId(ottSnookerScore.getQuiterId());
						score.setQuitReason(ottSnookerScore.getQuitReason());
						score.setBestPlayer(ottSnookerScore.getBestPlayer());
						score.setBestScore(ottSnookerScore.getBestScore());
						score.setStatus(ottSnookerScore.getStatus());
						score.setCurrentPlayer(ottSnookerScore.getCurrentPlayer());
						score.setCurrentScore(ottSnookerScore.getCurrentScore());
						score.setSort(ottSnookerScore.getSort());
						session.update(score);
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
				session.flush();
				session.clear();
				return null;
			}
		});
	}

	@Override
	public void batchDeleteSnookerScoreList(List<OttSnookerScore> deletedList) {
		this.getHibernateTemplate().execute(new HibernateCallback<Object>() {
			@Override
			public Object doInHibernate(Session session) throws HibernateException {
				for (OttSnookerScore ottSnookerScore : deletedList) {
					OttSnookerScore score = session.load(OttSnookerScore.class, ottSnookerScore.getScoreId());
					session.delete(score);
				}
				session.flush();
				session.clear();
				return null;
			}
		});
	}

	@Override
	public void deleteAll() {
		this.getHibernateTemplate().execute(new HibernateCallback<Object>() {
			@Override
			public Object doInHibernate(Session session) throws HibernateException {
				Query query = session.createQuery("delete from OttSnookerFrame");
				query.executeUpdate();
				query = session.createQuery("delete from OttSnookerScore");
				query.executeUpdate();
				return null;
			}
		});
	}

	@Override
	public List<OttSnookerScore> findByParam(String leagueName, int first, int max, String sort, String order) {
		return this.getHibernateTemplate().execute(new HibernateCallback<List<OttSnookerScore>>() {
			@Override
			public List<OttSnookerScore> doInHibernate(Session session) throws HibernateException {
				String hql = "from OttSnookerScore ";
				if (StringUtils.isNotBlank(leagueName)) {
					hql += "where leagueNameCn like '%" + leagueName + "%' or leagueNameEn like '%" + leagueName + "%' or leagueNameTr like '%"
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
	public Long findCountByParam(String leagueName) {
		String hql = "select count(*) from OttSnookerScore ";
		if (StringUtils.isNotBlank(leagueName)) {
			hql += "where leagueNameCn like '%" + leagueName + "%' or leagueNameEn like '%" + leagueName + "%' or leagueNameTr like '%" + leagueName
					+ "%' ";
		}
		return (Long) this.getHibernateTemplate().find(hql).get(0);
	}

}

