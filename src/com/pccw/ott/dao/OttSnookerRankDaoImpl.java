package com.pccw.ott.dao;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.orm.hibernate5.HibernateCallback;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import com.pccw.ott.model.OttSnookerPoint;
import com.pccw.ott.model.OttSnookerRank;

@Repository("ottSnookerRankDao")
@SuppressWarnings({"unchecked", "rawtypes", "deprecation"})
public class OttSnookerRankDaoImpl extends HibernateDaoSupport implements OttSnookerRankDao {

	@Override
	public void batchSaveRankList(List<OttSnookerRank> list) {
		this.getHibernateTemplate().execute(new HibernateCallback<Object>() {
			@Override
			public Object doInHibernate(Session session) throws HibernateException {
				for (OttSnookerRank ottSnookerRank : list) {
					session.save(ottSnookerRank);
				}
				session.flush();
				session.clear();
				return null;
			}
		});
	}
	
	@Override
	public void batchUpdateSnookerRankList(List<OttSnookerRank> updatedList) {
		
		this.getHibernateTemplate().execute(new HibernateCallback() {
			@Override
			public Object doInHibernate(Session session) throws HibernateException {
				for (OttSnookerRank ottSnookerRank : updatedList) {
					String hql = "update OttSnookerRank set "
							+ "rankTitle = :rankTitle, "
							+ "rankYear = :rankYear, "
							+ "playerId = :playerId, "
							+ "nameCn = :nameCn, "
							+ "nameEn = :nameEn, "
							+ "nameTr = :nameTr, "
							+ "nationality = :nationality, "
							+ "rank = :rank, "
							+ "point1 = :point1, "
							+ "point2 = :point2, "
							+ "point3 = :point3, "
							+ "ptcPoint = :ptcPoint, "
							+ "totalPoint = :totalPoint "
							+ "where rankId = :rankId";
					Query query = session.createQuery(hql);
					query.setParameter("rankTitle", ottSnookerRank.getRankTitle());
					query.setParameter("rankYear", ottSnookerRank.getRankYear());
					query.setParameter("playerId", ottSnookerRank.getPlayerId());
					query.setParameter("nameCn", ottSnookerRank.getNameCn());
					query.setParameter("nameEn", ottSnookerRank.getNameEn());
					query.setParameter("nameTr", ottSnookerRank.getNameTr());
					query.setParameter("nationality", ottSnookerRank.getNationality());
					query.setParameter("rank", ottSnookerRank.getRank());
					query.setParameter("point1", ottSnookerRank.getPoint1());
					query.setParameter("point2", ottSnookerRank.getPoint2());
					query.setParameter("point3", ottSnookerRank.getPoint3());
					query.setParameter("ptcPoint", ottSnookerRank.getPtcPoint());
					query.setParameter("totalPoint", ottSnookerRank.getTotalPoint());
					query.setParameter("rankId", ottSnookerRank.getRankId());
					query.executeUpdate();
				}
				return null;
			}
		});
	}

	@Override
	public void batchDeleteSnookerRankList(List<OttSnookerRank> deletedList) {
		for (OttSnookerRank ottSnookerRank : deletedList) {
			if (null != ottSnookerRank.getSnookerPointList() && ottSnookerRank.getSnookerPointList().size() > 0) {
				OttSnookerRank rank = this.getHibernateTemplate().load(OttSnookerRank.class, ottSnookerRank.getRankId());
				this.getHibernateTemplate().delete(rank);
			} else {
				this.getHibernateTemplate().execute(new HibernateCallback() {
					@Override
					public Object doInHibernate(Session session) throws HibernateException {
						Query query = session.createQuery("delete from OttSnookerRank where rankId = :rankId");
						query.setParameter("rankId", ottSnookerRank.getRankId());
						query.executeUpdate();
						return null;
					}
				});
			}
		}
	}

	@Override
	public List<OttSnookerRank> findByPlayerName(String playerName, int first, int max) {
		return this.getHibernateTemplate().execute(new HibernateCallback<List<OttSnookerRank>>() {
			@Override
			public List<OttSnookerRank> doInHibernate(Session session) throws HibernateException {
				String hql = "from OttSnookerRank ";
				if (StringUtils.isNotBlank(playerName)) {
					hql += "where nameCn like '%" + playerName + "%' or nameEn like '%" + playerName + "%' or nameTr like '%" + playerName + "%'";
				}
				Query query = session.createQuery(hql);
				query.setFirstResult(first);
				query.setMaxResults(max);
				return query.list();
			}
		});
	}
	
	@Override
	public List<OttSnookerRank> findByPlayerName(String playerName, int first, int max, String sort, String order) {
		return this.getHibernateTemplate().execute(new HibernateCallback<List<OttSnookerRank>>() {
			@Override
			public List<OttSnookerRank> doInHibernate(Session session) throws HibernateException {
				String hql = "from OttSnookerRank ";
				if (StringUtils.isNotBlank(playerName)) {
					hql += "where nameCn like '%" + playerName + "%' or nameEn like '%" + playerName + "%' or nameTr like '%" + playerName + "%' ";
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
	public Long findCountByPlayerName(String playerName) {
		String hql = "select count(*) from OttSnookerRank ";
		if (StringUtils.isNotBlank(playerName)) {
			hql += "where nameCn like '%" + playerName + "%' or nameEn like '%" + playerName + "%' or nameTr like '%" + playerName + "%'";
		}
		return (Long) this.getHibernateTemplate().find(hql).get(0);
	}

	@Override
	public List<OttSnookerPoint> findPointByPlayerId(String playerId) {
		return (List<OttSnookerPoint>) this.getHibernateTemplate().find("from OttSnookerPoint p where p.pointId in (select rp.pointId from OttSnookerRankPoint rp where rp.playerId = ?)", playerId);
	}

	@Override
	public void deleteById(Long rankId) {
		OttSnookerRank rank = this.getHibernateTemplate().load(OttSnookerRank.class, rankId);
		this.getHibernateTemplate().delete(rank);
	}

	@Override
	public void deleteAllSnookerRank() {
		this.getHibernateTemplate().execute(new HibernateCallback<Object>() {
			@Override
			public Object doInHibernate(Session session) throws HibernateException {
				session.createNativeQuery("truncate ott_snooker_rank_point").executeUpdate();
				session.createNativeQuery("truncate ott_snooker_point").executeUpdate();
				session.createNativeQuery("truncate ott_snooker_rank").executeUpdate();
				return null;
			}
		});
	}

	@Override
	public void batchSaveSnookerPointList(OttSnookerRank rank, List<OttSnookerPoint> insertedList) {
		this.getHibernateTemplate().execute(new HibernateCallback<Object>() {
			@Override
			public Object doInHibernate(Session session) throws HibernateException {
				for (OttSnookerPoint ottSnookerPoint : insertedList) {
					Long pointId = (Long) session.save(ottSnookerPoint);
					Query query = session.createNativeQuery("insert into ott_snooker_rank_point (player_id, point_id) values(:playerId, :pointId)");
					query.setParameter("playerId", rank.getPlayerId());
					query.setParameter("pointId", pointId);
					query.executeUpdate();
				}
				session.flush();
				session.clear();
				return null;
			}
		});
	}

	@Override
	public void batchUpdateSnookerPointList(List<OttSnookerPoint> updatedList) {
		this.getHibernateTemplate().execute(new HibernateCallback<Object>() {
			@Override
			public Object doInHibernate(Session session) throws HibernateException {
				for (OttSnookerPoint ottSnookerPoint : updatedList) {
					OttSnookerPoint point = session.load(OttSnookerPoint.class, ottSnookerPoint.getPointId());
					point.setLeagueId(ottSnookerPoint.getLeagueId());
					point.setLeagueNameCn(ottSnookerPoint.getLeagueNameCn());
					point.setLeagueNameEn(ottSnookerPoint.getLeagueNameEn());
					point.setLeagueNameTr(ottSnookerPoint.getLeagueNameTr());
					point.setSn(ottSnookerPoint.getSn());
					session.update(point);
				}
				session.flush();
				session.clear();
				return null;
			}
		});
	}

	@Override
	public void batchDeleteSnookerPointList(List<OttSnookerPoint> deletedList) {
		this.getHibernateTemplate().execute(new HibernateCallback<Object>() {
			@Override
			public Object doInHibernate(Session session) throws HibernateException {
				for (OttSnookerPoint ottSnookerPoint : deletedList) {
					session.createNativeQuery("delete from ott_snooker_rank_point where point_id = " + ottSnookerPoint.getPointId()).executeUpdate();
					OttSnookerPoint point = session.load(OttSnookerPoint.class, ottSnookerPoint.getPointId());
					session.delete(point);
				}
				session.flush();
				session.clear();
				return null;
			}
		});
	}

}
