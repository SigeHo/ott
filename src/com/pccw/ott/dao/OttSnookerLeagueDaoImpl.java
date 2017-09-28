package com.pccw.ott.dao;

import java.util.List;

import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import com.pccw.ott.model.OttSnookerLeague;

@Repository("ottSnookerLeagueDao")
public class OttSnookerLeagueDaoImpl extends HibernateDaoSupport implements OttSnookerLeagueDao {

	@Override
	public void deleteByLeagueId(Integer leagueId) {
		List<Long> ids = (List<Long>) this.getHibernateTemplate().find("select id from OttSnookerLeague where leagueId = ?", leagueId);
		if (ids.size() == 1) {
			OttSnookerLeague league = this.getHibernateTemplate().load(OttSnookerLeague.class, ids.get(0));
			this.getHibernateTemplate().delete(league);
		}
	}

	@Override
	public void save(OttSnookerLeague ottSnookerLeague) {
		this.getHibernateTemplate().save(ottSnookerLeague);
	}

}
