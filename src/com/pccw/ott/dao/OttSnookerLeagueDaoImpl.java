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

import com.pccw.ott.model.OttSnookerLeague;
import com.pccw.ott.model.OttSnookerLevel;

@Repository("ottSnookerLeagueDao")
@SuppressWarnings({"unchecked", "rawtypes"})
public class OttSnookerLeagueDaoImpl extends HibernateDaoSupport implements OttSnookerLeagueDao {

	@Override
	public void deleteByLeagueId(Integer leagueId) {
		List<OttSnookerLeague> list = (List<OttSnookerLeague>) this.getHibernateTemplate().find("from OttSnookerLeague where leagueId = ?", leagueId);
		if (list.size() > 0) {
			this.getHibernateTemplate().delete(list.get(0));
		}
	}

	@Override
	public void save(OttSnookerLeague ottSnookerLeague) {
		this.getHibernateTemplate().save(ottSnookerLeague);
	}

	@Override
	public List<OttSnookerLeague> findByParam(String leagueName, int first, int max, String sort, String order) {
		return this.getHibernateTemplate().execute(new HibernateCallback<List<OttSnookerLeague>>() {
			@Override
			public List<OttSnookerLeague> doInHibernate(Session session) throws HibernateException {
				String hql = "from OttSnookerLeague ";
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
		String hql = "select count(*) from OttSnookerLeague ";
		if (StringUtils.isNotBlank(leagueName)) {
			hql += "where leagueNameCn like '%" + leagueName + "%' or leagueNameEn like '%" + leagueName + "%' or leagueNameTr like '%" + leagueName
					+ "%' ";
		}
		return (Long) this.getHibernateTemplate().find(hql).get(0);
	}

	@Override
	public void batchSaveSnookerLeagueList(List<OttSnookerLeague> insertedList) {
		this.getHibernateTemplate().execute(new HibernateCallback<Object>() {
			@Override
			public Object doInHibernate(Session session) throws HibernateException {
				for (OttSnookerLeague ottSnookerLeague : insertedList) {
					session.save(ottSnookerLeague);
				}
				session.flush();
				session.clear();
				return null;
			}
		});
	}

	@Override
	public void batchUpdateSnookerLeagueList(List<OttSnookerLeague> updatedList) {
		this.getHibernateTemplate().execute(new HibernateCallback<Object>() {
			@Override
			public Object doInHibernate(Session session) throws HibernateException {
				for (OttSnookerLeague ottSnookerLeague : updatedList) {
					String hql = "update OttSnookerLeague set " 
							+ "leagueId = :leagueId, " 
							+ "leagueNameCn = :leagueNameCn, "
							+ "leagueNameEn = :leagueNameEn, " 
							+ "leagueNameTr = :leagueNameTr, " 
							+ "startTime =: startTime, "
							+ "endTime =: endTime, "
							+ "color =: color, "
							+ "remark =: remark, "
							+ "money =: money, "
							+ "where id = :id";
					Query query = session.createQuery(hql);
					SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
					try {
						if (StringUtils.isNotBlank(ottSnookerLeague.getStartTimeStr())) {
							query.setParameter("startTime", sdf.parse(ottSnookerLeague.getStartTimeStr()));
						}
						if (StringUtils.isNotBlank(ottSnookerLeague.getEndTimeStr())) {
							query.setParameter("EndTime", sdf.parse(ottSnookerLeague.getEndTimeStr()));
						}
					} catch (ParseException e) {
						e.printStackTrace();
						logger.error(e.toString());
					}
					query.setParameter("leagueId", ottSnookerLeague.getLeagueId());
					query.setParameter("leagueNameCn", ottSnookerLeague.getLeagueNameCn());
					query.setParameter("leagueNameEn", ottSnookerLeague.getLeagueNameEn());
					query.setParameter("leagueNameTr", ottSnookerLeague.getLeagueNameTr());
					query.setParameter("startTime", ottSnookerLeague.getStartTime());
					query.setParameter("endTime", ottSnookerLeague.getEndTime());
					query.setParameter("color", ottSnookerLeague.getColor());
					query.setParameter("remark", ottSnookerLeague.getRemark());
					query.setParameter("money", ottSnookerLeague.getMoney());
					query.setParameter("id", ottSnookerLeague.getId());
					query.executeUpdate();
				}
				session.flush();
				session.clear();
				return null;
			}
		});
	}

	@Override
	public void batchDeleteSnookerLeagueList(List<OttSnookerLeague> deletedList) {
		for (OttSnookerLeague ottSnookerLeague : deletedList) {
			if (null != ottSnookerLeague.getOttSnookerLevelList() && ottSnookerLeague.getOttSnookerLevelList().size() > 0) {
				OttSnookerLeague rank = this.getHibernateTemplate().load(OttSnookerLeague.class, ottSnookerLeague.getLeagueId());
				this.getHibernateTemplate().delete(rank);
			} else {
				this.getHibernateTemplate().execute(new HibernateCallback() {
					@Override
					public Object doInHibernate(Session session) throws HibernateException {
						Query query = session.createQuery("delete from OttSnookerLeague where rankId = :rankId");
						query.setParameter("rankId", ottSnookerLeague.getLeagueId());
						query.executeUpdate();
						return null;
					}
				});
			}
		}
	}

	@Override
	public void batchSaveSnookerLevelList(OttSnookerLeague league, List<OttSnookerLevel> insertedList) {
		this.getHibernateTemplate().execute(new HibernateCallback<Object>() {
			@Override
			public Object doInHibernate(Session session) throws HibernateException {
				for (OttSnookerLevel ottSnookerLevel : insertedList) {
					Long levelId = (Long) session.save(ottSnookerLevel);
					Query query = session.createNativeQuery("insert into ott_snooker_league_level (league_id, level_id) values(:leagueId, :levelId)");
					query.setParameter("leagueId", league.getLeagueId());
					query.setParameter("levelId", levelId);
					query.executeUpdate();
				}
				session.flush();
				session.clear();
				return null;
			}
		});
	}

	@Override
	public void batchUpdateSnookerLevelList(List<OttSnookerLevel> updatedList) {
		this.getHibernateTemplate().execute(new HibernateCallback<Object>() {
			@Override
			public Object doInHibernate(Session session) throws HibernateException {
				for (OttSnookerLevel ottSnookerLevel : updatedList) {
					OttSnookerLevel level = session.load(OttSnookerLevel.class, ottSnookerLevel.getLevelId());
					level.setLevelRounds(ottSnookerLevel.getLevelRounds());
					level.setMatchLevels(ottSnookerLevel.getMatchLevels());
					level.setMatchGroup(ottSnookerLevel.getMatchGroup());
					level.setRemark(ottSnookerLevel.getRemark());
					session.update(level);
				}
				session.flush();
				session.clear();
				return null;
			}
		});
	}

	@Override
	public void batchDeleteSnookerLevelList(List<OttSnookerLevel> deletedList) {
		this.getHibernateTemplate().execute(new HibernateCallback<Object>() {
			@Override
			public Object doInHibernate(Session session) throws HibernateException {
				for (OttSnookerLevel OttSnookerLevel : deletedList) {
					session.createNativeQuery("delete from ott_snooker_league_level where level_id = " + OttSnookerLevel.getLevelId()).executeUpdate();
					OttSnookerLevel level = session.load(OttSnookerLevel.class, OttSnookerLevel.getLevelId());
					session.delete(level);
				}
				session.flush();
				session.clear();
				return null;
			}
		});
	}

}
