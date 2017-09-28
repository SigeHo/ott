package com.pccw.ott.dao;

import com.pccw.ott.model.OttSnookerLeague;

public interface OttSnookerLeagueDao {
	
	public void deleteByLeagueId(Integer leagueId);
	
	public void save(OttSnookerLeague ottSnookerLeague);

}
