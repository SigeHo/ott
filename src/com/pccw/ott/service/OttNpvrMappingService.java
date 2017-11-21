package com.pccw.ott.service;

import java.text.ParseException;
import java.util.List;

import com.pccw.ott.dto.OttNpvrMappingDto;
import com.pccw.ott.dto.OttNpvrSearchDto;
import com.pccw.ott.model.OttNpvrMapping;

public interface OttNpvrMappingService {

	public List<OttNpvrMappingDto> findForSnookerFixture();
	
	public List<OttNpvrMappingDto> filterByNpvrSearchDto(List<OttNpvrMappingDto> allList, OttNpvrSearchDto npvrSearchDto) throws ParseException;

	public void batchSave(List<OttNpvrMapping> list);

	public void clearNpvrIds(String sportType, String fixtureId);

	public List<OttNpvrMappingDto> findByNpvrSearchDto(OttNpvrSearchDto npvrSearchDto);

	public void doSaveNpvrIds(String sportType, String fixtureId, List<OttNpvrMapping> mappingList);


}
