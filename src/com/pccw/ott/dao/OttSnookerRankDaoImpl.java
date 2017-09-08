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
		this.getHibernateTemplate().execute(new HibernateCallback<Object>() {
			@Override
			public Object doInHibernate(Session session) throws HibernateException {
				for (OttSnookerRank ottSnookerRank : updatedList) {
					OttSnookerRank rank = session.load(OttSnookerRank.class, ottSnookerRank.getRankId());
					rank.setRankTitle(ottSnookerRank.getRankTitle());
					rank.setRankYear(ottSnookerRank.getRankYear());
					rank.setPlayerId(ottSnookerRank.getPlayerId());
					rank.setNameCn(ottSnookerRank.getNameCn());
					rank.setNameEn(ottSnookerRank.getNameEn());
					rank.setNameTr(ottSnookerRank.getNameTr());
					rank.setNationality(ottSnookerRank.getNationality());
					rank.setRank(ottSnookerRank.getRank());
					rank.setPoint1(ottSnookerRank.getPoint1());
					rank.setPoint2(ottSnookerRank.getPoint2());
					rank.setPoint3(ottSnookerRank.getPoint3());
					rank.setPtcPoint(ottSnookerRank.getPtcPoint());
					rank.setTotalPoint(ottSnookerRank.getTotalPoint());
					session.update(rank);
				}
				session.flush();
				session.clear();
				return null;
			}
		});
	}

	@Override
	public void batchDeleteSnookerRankList(List<OttSnookerRank> deletedList) {
		this.getHibernateTemplate().execute(new HibernateCallback<Object>() {
			@Override
			public Object doInHibernate(Session session) throws HibernateException {
				for (OttSnookerRank ottSnookerRank : deletedList) {
					OttSnookerRank rank = session.load(OttSnookerRank.class, ottSnookerRank.getRankId());
					session.delete(rank);
				}
				session.flush();
				session.clear();
				return null;
			}
		});
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

	@SuppressWarnings("unchecked")
	@Override
	public List<OttSnookerPoint> findPointByPlayerId(String playerId) {
		return (List<OttSnookerPoint>) this.getHibernateTemplate()
				.find("from OttSnookerPoint p where p.pointId in (select rp.pointId from OttSnookerRankPoint rp where rp.playerId = ?)", playerId);
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
				Query query = session.createQuery("delete from OttSnookerPoint");
				query.executeUpdate();
				query = session.createQuery("delete from OttSnookerRank");
				query.executeUpdate();
				return null;
			}
		});
	}

	@Override
	public void batchSaveSnookerPointList(List<OttSnookerPoint> insertedList) {
		this.getHibernateTemplate().execute(new HibernateCallback<Object>() {
			@Override
			public Object doInHibernate(Session session) throws HibernateException {
				for (OttSnookerPoint ottSnookerPoint : insertedList) {
					session.save(ottSnookerPoint);
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
