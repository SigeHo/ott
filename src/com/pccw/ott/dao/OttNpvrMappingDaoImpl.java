package com.pccw.ott.dao;

import java.util.List;

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
}
