package com.pccw.ott.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.orm.hibernate5.HibernateCallback;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import com.pccw.ott.model.OttNpvrMapping;

@Repository("ottNpvrMappingDao")
@SuppressWarnings({"unchecked", "rawtypes"})
public class OttNpvrMappingDaoImpl extends HibernateDaoSupport implements OttNpvrMappingDao {

	@Override
	public List<OttNpvrMapping> findByFixtureIdAndSportType(String fixtureId, String sportType) {
		return this.getHibernateTemplate().execute(new HibernateCallback<List<OttNpvrMapping>>() {
			@Override
			public List<OttNpvrMapping> doInHibernate(Session session) throws HibernateException {
				String hql = "from OttNpvrMapping where fixtureId = :fixtureId and sportType = :sportType";
				Query query = session.createQuery(hql);
				query.setParameter("fixtureId", fixtureId);
				query.setParameter("sportType", sportType);
				return query.list();
			}
		});
	}

	@Override
	public List<OttNpvrMapping> findByFixtureId(String fixtureId) {
		return this.getHibernateTemplate().execute(new HibernateCallback<List<OttNpvrMapping>>() {
			@Override
			public List<OttNpvrMapping> doInHibernate(Session session) throws HibernateException {
				String hql = "from OttNpvrMapping where fixtureId = :fixtureId";
				Query query = session.createQuery(hql);
				query.setParameter("fixtureId", fixtureId);
				return query.list();
			}
		});
	}

	@Override
	public List<OttNpvrMapping> findByFixtureIdAndChannelNo(String fixtureId, int channelNo) {
		return this.getHibernateTemplate().execute(new HibernateCallback<List<OttNpvrMapping>>() {
			@Override
			public List<OttNpvrMapping> doInHibernate(Session session) throws HibernateException {
				String hql = "from OttNpvrMapping where fixtureId = :fixtureId and channelNo = :channelNo";
				Query query = session.createQuery(hql);
				query.setParameter("fixtureId", fixtureId);
				query.setParameter("channelNo", channelNo);
				return query.list();
			}
		});
	}

	@Override
	public void batchSave(List<OttNpvrMapping> list) {
		for (OttNpvrMapping ottNpvrMapping : list) {
			this.getHibernateTemplate().save(ottNpvrMapping);
		}
	}

	@Override
	public List<OttNpvrMapping> findByParameters(String sportType, String fixtureId, Integer channelNo) {
		return this.getHibernateTemplate().execute(new HibernateCallback<List<OttNpvrMapping>>() {
			@Override
			public List<OttNpvrMapping> doInHibernate(Session session) throws HibernateException {
				String hql = "from OttNpvrMapping where sportType = :sportType and fixtureId = :fixtureId and channelNo = :channelNo";
				Query query = session.createQuery(hql);
				query.setParameter("sportType", sportType);
				query.setParameter("fixtureId", fixtureId);
				query.setParameter("channelNo", channelNo);
				return query.list();
			}
		});
	}

	@Override
	public List<OttNpvrMapping> findAll() {
		return (List<OttNpvrMapping>) this.getHibernateTemplate().find("from OttNpvrMapping");
	}

	@Override
	public void deleteByParameters(String sportType, String fixtureId) {
		this.getHibernateTemplate().execute(new HibernateCallback() {
			@Override
			public Object doInHibernate(Session session) throws HibernateException {
				Query query = session.createQuery("delete from OttNpvrMapping where sportType = :sportType and fixtureId = :fixtureId");
				query.setParameter("sportType", sportType);
				query.setParameter("fixtureId", fixtureId);
				query.executeUpdate();
				return null;
			}
		});
	}

	@Override
	public void saveActualDateTime(String sportType, String fixtureId, Date actualStartDateTime, Date actualEndDateTime) {
		this.getHibernateTemplate().execute(new HibernateCallback() {
			@Override
			public Object doInHibernate(Session session) throws HibernateException {
				String hql = "update OttNpvrMapping set actualStartDateTime = :actualStartDateTime, actualEndDateTime = :actualEndDateTime where sportType = :sportType and fixtureId = :fixtureId";
				Query query = session.createQuery(hql);
				query.setParameter("actualStartDateTime", actualStartDateTime);
				query.setParameter("actualEndDateTime", actualEndDateTime);
				query.setParameter("sportType", sportType);
				query.setParameter("fixtureId", fixtureId);
				query.executeUpdate();
				return null;
			}
		});
	}

	@Override
	public void updateIsOverride(String sportType, String fixtureId, String isOverride) {
		this.getHibernateTemplate().execute(new HibernateCallback() {
			@Override
			public Object doInHibernate(Session session) throws HibernateException {
				String hql = "update OttNpvrMapping set isOverride = :isOverride where sportType = :sportType and fixtureId = :fixtureId";
				Query query = session.createQuery(hql);
				query.setParameter("isOverride", isOverride);
				query.setParameter("sportType", sportType);
				query.setParameter("fixtureId", fixtureId);
				query.executeUpdate();
				return null;
			}
		});
	}
}
