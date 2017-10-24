package com.pccw.ott.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pccw.ott.dao.OttNpvrMappingDao;
import com.pccw.ott.dto.OttNpvrMappingDto;
import com.pccw.ott.dto.OttNpvrSearchDto;
import com.pccw.ott.model.OttNpvrMapping;

@Service("ottNpvrMappingService")
@Transactional
public class OttNpvrMappingServiceImpl implements OttNpvrMappingService {
	
	@Autowired
	private OttNpvrMappingDao ottNpvrMappingDao;

	@Override
	public List<OttNpvrMappingDto> filterByNpvrSearchDto(List<OttNpvrMappingDto> allList, OttNpvrSearchDto npvrSearchDto) throws ParseException {
		List<OttNpvrMappingDto> filterList = new ArrayList<>();
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm");
		Date fromDateTime = sdf.parse(npvrSearchDto.getFromDate() + " " + npvrSearchDto.getFromTime());
		Date toDateTime = sdf.parse(npvrSearchDto.getToDate() + " " + npvrSearchDto.getToTime());
		for (OttNpvrMappingDto dto : allList) {
			dto.setChannelNo(npvrSearchDto.getChannelNo());
			dto.setSportType(npvrSearchDto.getSportType());
			Date startDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dto.getStartDateTime());
			if (/*dto.getTournament().equals(npvrSearchDto.getTournament()) && */startDateTime.after(fromDateTime) && startDateTime.before(toDateTime)) {
//				List<OttNpvrMapping> mappings = ottNpvrMappingDao.findByFixtureIdAndChannelNo(dto.getFixtureId(), npvrSearchDto.getChannelNo());
				List<OttNpvrMapping> mappings = ottNpvrMappingDao.findByParameters(npvrSearchDto.getSportType().toUpperCase(), dto.getFixtureId(), npvrSearchDto.getChannelNo());
				if (npvrSearchDto.getTvCoverage().equals("no") && mappings.size() == 0) {
					filterList.add(dto);
				} else if (npvrSearchDto.getTvCoverage().equals("yes") && mappings.size() > 0) {
					String npvrIds = "";
					for (int i = 0; i < mappings.size(); i++) {
						if (i == mappings.size() - 1) {
							npvrIds += mappings.get(i).getNpvrId();
						} else {
							npvrIds += mappings.get(i).getNpvrId() + ",";
						}
					}
					dto.setNpvrIds(npvrIds);
					filterList.add(dto);
				} else if (StringUtils.isBlank(npvrSearchDto.getTvCoverage())) {
					if (mappings.size() > 0) {
						String npvrIds = "";
						for (int i = 0; i < mappings.size(); i++) {
							if (i == mappings.size() - 1) {
								npvrIds += mappings.get(i).getNpvrId();
							} else {
								npvrIds += mappings.get(i).getNpvrId() + ",";
							}
						}
						dto.setNpvrIds(npvrIds);
					}
					filterList.add(dto);
				}
			}
		}
		return filterList;
	}
	
	@Override
	public void clearNpvrIds(Integer channelNo, String sportType, String fixtureId) {
		ottNpvrMappingDao.deleteByParameters(channelNo, sportType, fixtureId);
	}
	
	@Override
	public void batchSave(List<OttNpvrMapping> list) {
		ottNpvrMappingDao.batchSave(list);
	}
	
}
