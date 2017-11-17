package com.pccw.ott.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "ott_npvr_mapping")
public class OttNpvrMapping implements Serializable {

	private static final long serialVersionUID = 3888636093856889410L;

	private Long id;
	private Integer channelNo;
	private String sportType;
	private String fixtureId;
	private String npvrId;
	private Date actualStartDateTime;
	private Date acutalEndDateTime;

	@Column(name = "id")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "channel_no")
	public Integer getChannelNo() {
		return channelNo;
	}

	public void setChannelNo(Integer channelNo) {
		this.channelNo = channelNo;
	}

	@Column(name = "sport_type")
	public String getSportType() {
		return sportType;
	}

	public void setSportType(String sportType) {
		this.sportType = sportType;
	}

	@Column(name = "fixture_id")
	public String getFixtureId() {
		return fixtureId;
	}

	public void setFixtureId(String fixtureId) {
		this.fixtureId = fixtureId;
	}

	@Column(name = "npvr_id")
	public String getNpvrId() {
		return npvrId;
	}

	public void setNpvrId(String npvrId) {
		this.npvrId = npvrId;
	}

	@Column(name = "actual_start_date_time")
	@Temporal(TemporalType.TIMESTAMP)
	public Date getActualStartDateTime() {
		return actualStartDateTime;
	}

	public void setActualStartDateTime(Date actualStartDateTime) {
		this.actualStartDateTime = actualStartDateTime;
	}

	@Column(name = "actual_end_date_time")
	@Temporal(TemporalType.TIMESTAMP)
	public Date getAcutalEndDateTime() {
		return acutalEndDateTime;
	}

	public void setAcutalEndDateTime(Date acutalEndDateTime) {
		this.acutalEndDateTime = acutalEndDateTime;
	}

}
