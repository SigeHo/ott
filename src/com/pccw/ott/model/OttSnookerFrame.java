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
	private Integer matchSort;
	private Integer scoreA;
	private Integer scoreB;
	private Integer bestPlayer;
	private Integer cscoreA;
	private Integer cscoreB;
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
	public Integer getMatchSort() {
		return matchSort;
	}

	public void setMatchSort(Integer matchSort) {
		this.matchSort = matchSort;
	}

	@Column(name = "score_a")
	public Integer getScoreA() {
		return scoreA;
	}

	public void setScoreA(Integer scoreA) {
		this.scoreA = scoreA;
	}

	@Column(name = "score_b")
	public Integer getScoreB() {
		return scoreB;
	}

	public void setScoreB(Integer scoreB) {
		this.scoreB = scoreB;
	}

	@Column(name = "best_player")
	public Integer getBestPlayer() {
		return bestPlayer;
	}

	public void setBestPlayer(Integer bestPlayer) {
		this.bestPlayer = bestPlayer;
	}

	@Column(name = "cscore_a")
	public Integer getCscoreA() {
		return cscoreA;
	}

	public void setCscoreA(Integer cscoreA) {
		this.cscoreA = cscoreA;
	}

	@Column(name = "cscore_b")
	public Integer getCscoreB() {
		return cscoreB;
	}

	public void setCscoreB(Integer cscoreB) {
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
