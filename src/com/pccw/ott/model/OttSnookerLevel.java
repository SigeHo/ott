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
@Table(name = "ott_snooker_level")
public class OttSnookerLevel implements Serializable {

	private static final long serialVersionUID = 3586751348785435459L;

	private Long levelId;
	private String levelRounds;
	private String matchLevels;
	private Integer matchGroup;
	private String remark;
	private Date lastPublishedDate;

	@Column(name = "level_id")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getLevelId() {
		return levelId;
	}

	public void setLevelId(Long levelId) {
		this.levelId = levelId;
	}

	@Column(name = "level_rounds")
	public String getLevelRounds() {
		return levelRounds;
	}

	public void setLevelRounds(String levelRounds) {
		this.levelRounds = levelRounds;
	}

	@Column(name = "match_levels")
	public String getMatchLevels() {
		return matchLevels;
	}

	public void setMatchLevels(String matchLevels) {
		this.matchLevels = matchLevels;
	}

	@Column(name = "match_group")
	public Integer getMatchGroup() {
		return matchGroup;
	}

	public void setMatchGroup(Integer matchGroup) {
		this.matchGroup = matchGroup;
	}

	@Column(name = "remark")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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
