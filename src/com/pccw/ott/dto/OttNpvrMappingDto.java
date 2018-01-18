package com.pccw.ott.dto;

import java.io.Serializable;

public class OttNpvrMappingDto implements Serializable {

	private static final long serialVersionUID = 6726978821081396451L;

	private String fixtureId;
	private String channelNos;
	private String sportType;
	private String tournament;
	private String startDateTime;
	private String teamA;
	private String teamB;
	private String status;
	private String isOverride;
	private String actualStartDate;
	private String actualStartTime;
	private String actualEndDate;
	private String actualEndTime;
	private String npvrIds;

	public String getFixtureId() {
		return fixtureId;
	}

	public void setFixtureId(String fixtureId) {
		this.fixtureId = fixtureId;
	}

	public String getChannelNos() {
		return channelNos;
	}

	public void setChannelNos(String channelNos) {
		this.channelNos = channelNos;
	}

	public String getSportType() {
		return sportType;
	}

	public void setSportType(String sportType) {
		this.sportType = sportType;
	}

	public String getTournament() {
		return tournament;
	}

	public void setTournament(String tournament) {
		this.tournament = tournament;
	}

	public String getStartDateTime() {
		return startDateTime;
	}

	public void setStartDateTime(String startDateTime) {
		this.startDateTime = startDateTime;
	}

	public String getTeamA() {
		return teamA;
	}

	public void setTeamA(String teamA) {
		this.teamA = teamA;
	}

	public String getTeamB() {
		return teamB;
	}

	public void setTeamB(String teamB) {
		this.teamB = teamB;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getIsOverride() {
		return isOverride;
	}

	public void setIsOverride(String isOverride) {
		this.isOverride = isOverride;
	}

	public String getActualStartDate() {
		return actualStartDate;
	}

	public void setActualStartDate(String actualStartDate) {
		this.actualStartDate = actualStartDate;
	}

	public String getActualStartTime() {
		return actualStartTime;
	}

	public void setActualStartTime(String actualStartTime) {
		this.actualStartTime = actualStartTime;
	}

	public String getActualEndDate() {
		return actualEndDate;
	}

	public void setActualEndDate(String actualEndDate) {
		this.actualEndDate = actualEndDate;
	}

	public String getActualEndTime() {
		return actualEndTime;
	}

	public void setActualEndTime(String actualEndTime) {
		this.actualEndTime = actualEndTime;
	}

	public String getNpvrIds() {
		return npvrIds;
	}

	public void setNpvrIds(String npvrIds) {
		this.npvrIds = npvrIds;
	}

}
