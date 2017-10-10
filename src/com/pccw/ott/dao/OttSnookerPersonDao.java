package com.pccw.ott.dao;

import java.text.ParseException;
import java.util.List;

import com.pccw.ott.model.OttSnookerPerson;

public interface OttSnookerPersonDao {

	public List<OttSnookerPerson> findByPlayerName(String playerName, int first, int max, String sort, String order);

	public Long countByPlayerName(String playerName);

	public void deleteById(Integer playerId);

	public void save(OttSnookerPerson person);

	public void batchSaveSnookerPersonList(List<OttSnookerPerson> insertedList) throws ParseException;

	public void batchUpdateSnookerPersonList(List<OttSnookerPerson> updatedList) throws ParseException;

	public void batchDeleteSnookerPersonList(List<OttSnookerPerson> deletedList);

}
