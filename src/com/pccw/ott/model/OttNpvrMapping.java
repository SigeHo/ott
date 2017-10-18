package com.pccw.ott.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ott_npvr_mapping")
public class OttNpvrMapping implements Serializable {

	private static final long serialVersionUID = 3888636093856889410L;

	private Long id;
	private Integer channelNo;
	private String sportType;
	private String fixtureId;
	private String npvrId;

	@Column(name = "id")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "channelNo")
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

}
