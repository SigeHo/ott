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

import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "ott_snooker_point")
public class OttSnookerPoint implements Serializable {
	private static final long serialVersionUID = 5118656529264702910L;
	private Long pointId;
	private Integer leagueId;
	private String leagueNameCn;
	private String leagueNameEn;
	private String leagueNameTr;
	private Integer sn;
	private Date lastPublishedDate;

	@Id
	@Column(name = "point_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getPointId() {
		return pointId;
	}

	public void setPointId(Long pointId) {
		this.pointId = pointId;
	}

	@Column(name = "league_id")
	public Integer getLeagueId() {
		return leagueId;
	}

	public void setLeagueId(Integer leagueId) {
		this.leagueId = leagueId;
	}

	@Column(name = "league_name_cn")
	public String getLeagueNameCn() {
		return leagueNameCn;
	}

	public void setLeagueNameCn(String leagueNameCn) {
		this.leagueNameCn = leagueNameCn;
	}

	@Column(name = "league_name_en")
	public String getLeagueNameEn() {
		return leagueNameEn;
	}

	public void setLeagueNameEn(String leagueNameEn) {
		this.leagueNameEn = leagueNameEn;
	}

	@Column(name = "league_name_tr")
	public String getLeagueNameTr() {
		return leagueNameTr;
	}

	public void setLeagueNameTr(String leagueNameTr) {
		this.leagueNameTr = leagueNameTr;
	}

	@Column(name = "sn")
	public Integer getSn() {
		return sn;
	}

	public void setSn(Integer sn) {
		this.sn = sn;
	}
	
	@Column(name = "last_published_date")
	@Temporal(TemporalType.TIMESTAMP)
	@CreationTimestamp
	public Date getLastPublishedDate() {
		return lastPublishedDate;
	}

	public void setLastPublishedDate(Date lastPublishedDate) {
		this.lastPublishedDate = lastPublishedDate;
	}

}
