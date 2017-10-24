package com.pccw.ott.dao;

import java.util.List;

import com.pccw.ott.model.OttNpvrMapping;

public interface OttNpvrMappingDao {

	List<OttNpvrMapping> findByFixtureId(String fixtureId);

	List<OttNpvrMapping> findByFixtureIdAndChannelNo(String fixtureId, int channelNo);

	void batchSave(List<OttNpvrMapping> list);

	List<OttNpvrMapping> findByParameters(String sportType, String fixtureId, Integer channelNo);

	void deleteByParameters(Integer channelNo, String sportType, String fixtureId);

}
