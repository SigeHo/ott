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
import com.pccw.ott.dao.OttSnookerScoreDao;
import com.pccw.ott.dto.OttNpvrMappingDto;
import com.pccw.ott.dto.OttNpvrSearchDto;
import com.pccw.ott.model.OttNpvrMapping;
import com.pccw.ott.model.OttSnookerScore;

@Service("ottNpvrMappingService")
@Transactional
public class OttNpvrMappingServiceImpl implements OttNpvrMappingService {
	
	@Autowired
	private OttNpvrMappingDao ottNpvrMappingDao;
	
	@Autowired
	private OttSnookerScoreDao ottSnookerScoreDao;
	
	@Override
	public List<OttNpvrMappingDto> findByNpvrSearchDto(OttNpvrSearchDto npvrSearchDto) {
		// TODO
		return null;
	}
	
	@Override
	public List<OttNpvrMappingDto> findForSnookerFixture() {
		List<OttNpvrMappingDto> list = new ArrayList<>();
		OttNpvrMappingDto dto = null;
		List<OttSnookerScore> fixtureList = ottSnookerScoreDao.findAllFixture();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		for (OttSnookerScore ottSnookerScore : fixtureList) {
			dto = new OttNpvrMappingDto();
			dto.setFixtureId(ottSnookerScore.getMatchId().toString());
			dto.setSportType("SNOOKER");
//			dto.setTournament(ottSnookerScore.getLeagueId());
			dto.setStartDateTime(sdf.format(ottSnookerScore.getMatchTime()));
			dto.setTeamA(ottSnookerScore.getPlayerANameEn());
			dto.setTeamB(ottSnookerScore.getPlayerBNameEn());
			dto.setStatus(ottSnookerScore.getStatus());
			list.add(dto);
		}
		return list;
	}

	@Override
	public List<OttNpvrMappingDto> filterByNpvrSearchDto(List<OttNpvrMappingDto> allList, OttNpvrSearchDto npvrSearchDto) throws ParseException {
		List<OttNpvrMappingDto> filterList = new ArrayList<>();
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm");
		Date fromDateTime = sdf.parse(npvrSearchDto.getFromDate() + " " + npvrSearchDto.getFromTime());
		Date toDateTime = sdf.parse(npvrSearchDto.getToDate() + " " + npvrSearchDto.getToTime());
		for (OttNpvrMappingDto dto : allList) {
			dto.setSportType(npvrSearchDto.getSportType());
			Date startDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dto.getStartDateTime());
			if (startDateTime.after(fromDateTime) && startDateTime.before(toDateTime)) {
				List<OttNpvrMapping> mappings = null;
				if (null != npvrSearchDto.getChannelNo()) {
					mappings = ottNpvrMappingDao.findByParameters(npvrSearchDto.getSportType(), dto.getFixtureId(), npvrSearchDto.getChannelNo());
					if (!npvrSearchDto.getTvCoverage().equals("no") && mappings.size() > 0) {
						String npvrIds = "";
						String channelNos = "";
						for (int i = 0; i < mappings.size(); i++) {
							if (i == mappings.size() - 1) {
								npvrIds += mappings.get(i).getNpvrId();
								channelNos += mappings.get(i).getChannelNo().toString();
							} else {
								npvrIds += mappings.get(i).getNpvrId() + ",";
								channelNos += mappings.get(i).getChannelNo().toString() + ",";
							}
						}
						dto.setNpvrIds(npvrIds);
						dto.setChannelNos(channelNos);
						filterList.add(dto);
					}
				} else {
					mappings = ottNpvrMappingDao.findByFixtureIdAndSportType(dto.getFixtureId(), npvrSearchDto.getSportType());
					if (StringUtils.isBlank(npvrSearchDto.getTvCoverage())) {
						if (mappings.size() > 0) {
							String npvrIds = "";
							String channelNos = "";
							for (int i = 0; i < mappings.size(); i++) {
								if (i == mappings.size() - 1) {
									npvrIds += mappings.get(i).getNpvrId();
									channelNos += mappings.get(i).getChannelNo().toString();
								} else {
									npvrIds += mappings.get(i).getNpvrId() + ",";
									channelNos += mappings.get(i).getChannelNo().toString() + ",";
								}
							}
							dto.setNpvrIds(npvrIds);
							dto.setChannelNos(channelNos);
						}
						filterList.add(dto);
					} else if (npvrSearchDto.getTvCoverage().equals("yes") && mappings.size() > 0) {
						String npvrIds = "";
						String channelNos = "";
						for (int i = 0; i < mappings.size(); i++) {
							if (i == mappings.size() - 1) {
								npvrIds += mappings.get(i).getNpvrId();
								channelNos += mappings.get(i).getChannelNo().toString();
							} else {
								channelNos += mappings.get(i).getChannelNo().toString() + ",";
							}
						}
						dto.setNpvrIds(npvrIds);
						dto.setChannelNos(channelNos);
						filterList.add(dto);
					} else if (npvrSearchDto.getTvCoverage().equals("no") && mappings.size() == 0) {
						filterList.add(dto);
					}
				}
			}
		}
		return filterList;
	}
	
	@Override
	public void clearNpvrIds(String sportType, String fixtureId) {
		ottNpvrMappingDao.deleteByParameters(sportType, fixtureId);
	}
	
	@Override
	public void batchSave(List<OttNpvrMapping> list) {
		ottNpvrMappingDao.batchSave(list);
	}
	
	@Override
	public void doSaveNpvrIds(String sportType, String fixtureId, List<OttNpvrMapping> mappingList) {
		ottNpvrMappingDao.deleteByParameters(sportType, fixtureId);
		ottNpvrMappingDao.batchSave(mappingList);
	}
	
}
