package com.pccw.ott.dao;

import java.util.List;

import com.pccw.ott.model.OttNpvrMapping;

public interface OttNpvrMappingDao {
	
	List<OttNpvrMapping> findByFixtureIdAndSportType(String fixtureId, String sportType);

	List<OttNpvrMapping> findByFixtureId(String fixtureId);

	List<OttNpvrMapping> findByFixtureIdAndChannelNo(String fixtureId, int channelNo);

	void batchSave(List<OttNpvrMapping> list);

	List<OttNpvrMapping> findByParameters(String sportType, String fixtureId, Integer channelNo);

	void deleteByParameters(String sportType, String fixtureId);

	List<OttNpvrMapping> findAll();



}
