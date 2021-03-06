package com.pccw.ott.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "ott_snooker_league")
public class OttSnookerLeague implements Serializable {

	private static final long serialVersionUID = 8515438242967614307L;

	private Long leagueTId;
	private Integer leagueId;
	private String leagueNameCn;
	private String leagueNameEn;
	private String leagueNameTr;
	private Date startTime;
	private String startTimeStr;
	private Date endTime;
	private String endTimeStr;
	private String color;
	private String remark;
	private Integer money;
	private Date lastPublishedDate;
	private List<OttSnookerLevel> ottSnookerLevelList;

	@Column(name = "league_t_id")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getLeagueTId() {
		return leagueTId;
	}

	public void setLeagueTId(Long leagueTId) {
		this.leagueTId = leagueTId;
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
	
	@Column(name = "start_time")
	@Temporal(TemporalType.TIMESTAMP)
	public Date getStartTime() {
		if (null != startTime) {
			SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
			this.setStartTimeStr(sdf.format(startTime));
		}
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	@Column(name = "end_time")
	@Temporal(TemporalType.TIMESTAMP)
	public Date getEndTime() {
		if (null != endTime) {
			SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
			this.setStartTimeStr(sdf.format(endTime));
		}
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	@Column(name = "color")
	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	@Column(name = "remark")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "money")
	public Integer getMoney() {
		return money;
	}

	public void setMoney(Integer money) {
		this.money = money;
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
	
	@OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.EAGER)
	@JoinTable(name = "ott_snooker_league_level", joinColumns = {
			@JoinColumn(name = "league_t_id", referencedColumnName = "league_t_id") }, inverseJoinColumns = {
					@JoinColumn(name = "level_id", referencedColumnName = "level_id") })
	public List<OttSnookerLevel> getOttSnookerLevelList() {
		return ottSnookerLevelList;
	}

	public void setOttSnookerLevelList(List<OttSnookerLevel> ottSnookerLevelList) {
		this.ottSnookerLevelList = ottSnookerLevelList;
	}
	
	@Transient
	public String getStartTimeStr() {
		return startTimeStr;
	}

	public void setStartTimeStr(String startTimeStr) {
		this.startTimeStr = startTimeStr;
	}

	@Transient
	public String getEndTimeStr() {
		return endTimeStr;
	}

	public void setEndTimeStr(String endTimeStr) {
		this.endTimeStr = endTimeStr;
	}

}
