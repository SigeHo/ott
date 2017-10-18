package com.pccw.ott.service;

import java.text.ParseException;
import java.util.List;

import com.pccw.ott.dto.OttNpvrMappingDto;
import com.pccw.ott.dto.OttNpvrSearchDto;
import com.pccw.ott.model.OttNpvrMapping;

public interface OttNpvrMappingService {

	public List<OttNpvrMappingDto> filterByNpvrSearchDto(List<OttNpvrMappingDto> allList, OttNpvrSearchDto npvrSearchDto) throws ParseException;

	public void batchSave(List<OttNpvrMapping> list);

}
