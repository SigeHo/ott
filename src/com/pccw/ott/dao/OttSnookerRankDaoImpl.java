package com.pccw.ott.dao;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;
import org.springframework.orm.hibernate5.HibernateCallback;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import com.pccw.ott.model.OttSnookerPoint;
import com.pccw.ott.model.OttSnookerRank;

@Repository("ottSnookerRankDao")
public class OttSnookerRankDaoImpl extends HibernateDaoSupport implements OttSnookerRankDao {

	@Override
	public void batchSave(List<OttSnookerRank> list) {
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

}
