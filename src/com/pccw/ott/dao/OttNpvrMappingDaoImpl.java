package com.pccw.ott.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.orm.hibernate5.HibernateCallback;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import com.pccw.ott.model.OttNpvrMapping;

@Repository("ottNpvrMappingDao")
@SuppressWarnings("unchecked")
public class OttNpvrMappingDaoImpl extends HibernateDaoSupport implements OttNpvrMappingDao {

	@Override
	public List<OttNpvrMapping> findByFixtureId(String fixtureId) {
		return (List<OttNpvrMapping>) this.getHibernateTemplate().find("from OttNpvrMapping where fixtureId = ?", fixtureId);
	}

	@Override
	public List<OttNpvrMapping> findByFixtureIdAndChannelNo(String fixtureId, int channelNo) {
		return (List<OttNpvrMapping>) this.getHibernateTemplate().find("from OttNpvrMapping where fixtureId = ? and channelNo = ?", fixtureId, channelNo);
	}
	
	@Override
	public void batchSave(List<OttNpvrMapping> list) {
		for (OttNpvrMapping ottNpvrMapping : list) {
			this.getHibernateTemplate().save(ottNpvrMapping);
		}
	}
	
	@Override
	public List<OttNpvrMapping> findByParameters(String sportType, String fixtureId, Integer channelNo) {
		return (List<OttNpvrMapping>) this.getHibernateTemplate().find("from OttNpvrMapping where sportType = ? and fixtureId = ? and channelNo = ?", sportType, fixtureId, channelNo);
	}
	
	@Override
	public void deleteByParameters(Integer channelNo, String sportType, String fixtureId) {
		this.getHibernateTemplate().execute(new HibernateCallback() {
			@Override
			public Object doInHibernate(Session session) throws HibernateException {
				Query query = session.createQuery("delete from OttNpvrMapping where channelNo = :channelNo and sportType = :sportType and fixtureId = :fixtureId");
				query.setParameter("channelNo", channelNo);
				query.setParameter("sportType", sportType);
				query.setParameter("fixtureId", fixtureId);
				query.executeUpdate();
				return null;
			}
		});
	}
}
