package com.pccw.ott.dto;

import java.io.Serializable;

public class OttNpvrSearchDto implements Serializable {

	private static final long serialVersionUID = 4078849227133816133L;

	private String sportType;
	private Integer channelNo;
	private String tournament;
	private String fromDate;
	private String fromTime;
	private String toDate;
	private String toTime;
	private String tvCoverage;

	public String getSportType() {
		return sportType;
	}

	public void setSportType(String sportType) {
		this.sportType = sportType;
	}

	public Integer getChannelNo() {
		return channelNo;
	}

	public void setChannelNo(Integer channelNo) {
		this.channelNo = channelNo;
	}

	public String getTournament() {
		return tournament;
	}

	public void setTournament(String tournament) {
		this.tournament = tournament;
	}

	public String getFromDate() {
		return fromDate;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public String getFromTime() {
		return fromTime;
	}

	public void setFromTime(String fromTime) {
		this.fromTime = fromTime;
	}

	public String getToDate() {
		return toDate;
	}

	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	public String getToTime() {
		return toTime;
	}

	public void setToTime(String toTime) {
		this.toTime = toTime;
	}

	public String getTvCoverage() {
		return tvCoverage;
	}

	public void setTvCoverage(String tvCoverage) {
		this.tvCoverage = tvCoverage;
	}

}
