package com.pccw.ott.model;

import java.io.Serializable;
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

import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "ott_snooker_fixture")
public class OttSnookerFixture implements Serializable {
	private static final long serialVersionUID = -7000740508755967868L;
	private Long fixtureId;
	private String matchId;
	private String seasonId;
	private Date matchTime;
	private Long leagueId;
	private String leagueNameCn;
	private String leagueNameEn;
	private String leagueNameTr;
	private String leagueType;
	private String matchLevel1;
	private String matchLevel2;
	private String group;
	private String playAId;
	private String playBId;
	private String playANameCn;
	private String playANameEn;
	private String playANameTr;
	private String playBNameCn;
	private String playBNameEn;
	private String playBNameTr;
	private Long playAWinNum;
	private Long playBWinNum;
	private Long maxField;
	private Long currentField;
	private String winnerId;
	private String winReason;
	private String quiterId;
	private String quitReason;
	private String bestPlayerId;
	private Long bestScore;
	private String status;
	private String currentPlayerId;
	private Long currentScore;
	private Long sort;
	private Date lastPublishedDate;
	private List<OttSnookerFrame> frameList;
	

	@Id
	@Column(name ="fixture_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getFixtureId() {
		return fixtureId;
	}

	public void setFixtureId(Long fixtureId) {
		this.fixtureId = fixtureId;
	}

	@Column(name = "match_id")
	public String getMatchId() {
		return matchId;
	}

	public void setMatchId(String matchId) {
		this.matchId = matchId;
	}

	@Column(name = "season_id")
	public String getSeasonId() {
		return seasonId;
	}

	public void setSeasonId(String seasonId) {
		this.seasonId = seasonId;
	}

	@Column(name = "match_time")
	public Date getMatchTime() {
		return matchTime;
	}

	public void setMatchTime(Date matchTime) {
		this.matchTime = matchTime;
	}

	@Column(name = "league_id")
	public Long getLeagueId() {
		return leagueId;
	}

	public void setLeagueId(Long leagueId) {
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

	@Column(name = "league_type")
	public String getLeagueType() {
		return leagueType;
	}

	public void setLeagueType(String leagueType) {
		this.leagueType = leagueType;
	}

	@Column(name = "match_level_1")
	public String getMatchLevel1() {
		return matchLevel1;
	}

	public void setMatchLevel1(String matchLevel1) {
		this.matchLevel1 = matchLevel1;
	}

	@Column(name = "match_level_2")
	public String getMatchLevel2() {
		return matchLevel2;
	}

	public void setMatchLevel2(String matchLevel2) {
		this.matchLevel2 = matchLevel2;
	}

	@Column(name = "group")
	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	@Column(name = "player_a_id")
	public String getPlayAId() {
		return playAId;
	}

	public void setPlayAId(String playAId) {
		this.playAId = playAId;
	}

	@Column(name = "player_b_id")
	public String getPlayBId() {
		return playBId;
	}

	public void setPlayBId(String playBId) {
		this.playBId = playBId;
	}

	@Column(name = "player_a_name_cn")
	public String getPlayANameCn() {
		return playANameCn;
	}

	public void setPlayANameCn(String playANameCn) {
		this.playANameCn = playANameCn;
	}

	@Column(name = "player_a_name_en")
	public String getPlayANameEn() {
		return playANameEn;
	}

	public void setPlayANameEn(String playANameEn) {
		this.playANameEn = playANameEn;
	}

	@Column(name = "player_a_name_tr")
	public String getPlayANameTr() {
		return playANameTr;
	}

	public void setPlayANameTr(String playANameTr) {
		this.playANameTr = playANameTr;
	}

	@Column(name = "player_b_name_cn")
	public String getPlayBNameCn() {
		return playBNameCn;
	}

	public void setPlayBNameCn(String playBNameCn) {
		this.playBNameCn = playBNameCn;
	}

	@Column(name = "player_b_name_en")
	public String getPlayBNameEn() {
		return playBNameEn;
	}

	public void setPlayBNameEn(String playBNameEn) {
		this.playBNameEn = playBNameEn;
	}

	@Column(name = "player_b_name_tr")
	public String getPlayBNameTr() {
		return playBNameTr;
	}

	public void setPlayBNameTr(String playBNameTr) {
		this.playBNameTr = playBNameTr;
	}

	@Column(name = "player_a_win_num")
	public Long getPlayAWinNum() {
		return playAWinNum;
	}

	public void setPlayAWinNum(Long playAWinNum) {
		this.playAWinNum = playAWinNum;
	}

	@Column(name = "player_b_win_num")
	public Long getPlayBWinNum() {
		return playBWinNum;
	}

	public void setPlayBWinNum(Long playBWinNum) {
		this.playBWinNum = playBWinNum;
	}

	@Column(name = "max_field")
	public Long getMaxField() {
		return maxField;
	}

	public void setMaxField(Long maxField) {
		this.maxField = maxField;
	}

	@Column(name = "current_field")
	public Long getCurrentField() {
		return currentField;
	}

	public void setCurrentField(Long currentField) {
		this.currentField = currentField;
	}

	@Column(name = "winner_id")
	public String getWinnerId() {
		return winnerId;
	}

	public void setWinnerId(String winnerId) {
		this.winnerId = winnerId;
	}

	@Column(name = "win_reason")
	public String getWinReason() {
		return winReason;
	}

	public void setWinReason(String winReason) {
		this.winReason = winReason;
	}

	@Column(name = "quiter_id")
	public String getQuiterId() {
		return quiterId;
	}

	public void setQuiterId(String quiterId) {
		this.quiterId = quiterId;
	}

	@Column(name = "quit_reason")
	public String getQuitReason() {
		return quitReason;
	}

	public void setQuitReason(String quitReason) {
		this.quitReason = quitReason;
	}

	@Column(name = "best_player_id")
	public String getBestPlayerId() {
		return bestPlayerId;
	}

	public void setBestPlayerId(String bestPlayerId) {
		this.bestPlayerId = bestPlayerId;
	}

	@Column(name = "best_score")
	public Long getBestScore() {
		return bestScore;
	}

	public void setBestScore(Long bestScore) {
		this.bestScore = bestScore;
	}

	@Column(name = "status")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "current_player_id")
	public String getCurrentPlayerId() {
		return currentPlayerId;
	}

	public void setCurrentPlayerId(String currentPlayerId) {
		this.currentPlayerId = currentPlayerId;
	}

	@Column(name = "current_score")
	public Long getCurrentScore() {
		return currentScore;
	}

	public void setCurrentScore(Long currentScore) {
		this.currentScore = currentScore;
	}

	@Column(name = "sort")
	public Long getSort() {
		return sort;
	}

	public void setSort(Long sort) {
		this.sort = sort;
	}
	
	@Column(name = "las_published_date")
	@Temporal(TemporalType.TIMESTAMP)
	@CreationTimestamp
	public Date getLastPublishedDate() {
		return lastPublishedDate;
	}

	public void setLastPublishedDate(Date lastPublishedDate) {
		this.lastPublishedDate = lastPublishedDate;
	}

	@OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.EAGER)
	@JoinTable(name = "ott_snooker_fixture_frame", joinColumns = {
			@JoinColumn(name = "fixture_id", referencedColumnName = "fixture_id") }, inverseJoinColumns = {
					@JoinColumn(name = "frame_id", referencedColumnName = "frame_id") })
	public List<OttSnookerFrame> getFrameList() {
		return frameList;
	}

	public void setFrameList(List<OttSnookerFrame> frameList) {
		this.frameList = frameList;
	}

}
