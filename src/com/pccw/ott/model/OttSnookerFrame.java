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
@Table(name = "ott_snooker_frame")
public class OttSnookerFrame implements Serializable {

	private static final long serialVersionUID = -7644031061099497124L;

	private Long frameId;
	private String match_sort;
	private Long scoreA;
	private Long scoreB;
	private Long bestPlayerId;
	private Long cscoreA;
	private Long cscoreB;
	private Date lastPublishedDate;

	@Id
	@Column(name = "frame_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getFrameId() {
		return frameId;
	}

	public void setFrameId(Long frameId) {
		this.frameId = frameId;
	}

	@Column(name = "match_sort")
	public String getMatch_sort() {
		return match_sort;
	}

	public void setMatch_sort(String match_sort) {
		this.match_sort = match_sort;
	}

	@Column(name = "score_a")
	public Long getScoreA() {
		return scoreA;
	}

	public void setScoreA(Long scoreA) {
		this.scoreA = scoreA;
	}

	@Column(name = "score_b")
	public Long getScoreB() {
		return scoreB;
	}

	public void setScoreB(Long scoreB) {
		this.scoreB = scoreB;
	}

	@Column(name = "best_player_id")
	public Long getBestPlayerId() {
		return bestPlayerId;
	}

	public void setBestPlayerId(Long bestPlayerId) {
		this.bestPlayerId = bestPlayerId;
	}

	@Column(name = "cscore_a")
	public Long getCscoreA() {
		return cscoreA;
	}

	public void setCscoreA(Long cscoreA) {
		this.cscoreA = cscoreA;
	}

	@Column(name = "cscore_b")
	public Long getCscoreB() {
		return cscoreB;
	}

	public void setCscoreB(Long cscoreB) {
		this.cscoreB = cscoreB;
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
