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
@Table(name = "ott_snooker_score")
public class OttSnookerScore implements Serializable {
	private static final long serialVersionUID = -7000740508755967868L;
	private Long scoreId;
	private Integer matchId;
	private Integer seasonId;
	private Date matchTime;
	private String matchTimeStr;
	private Integer leagueId;
	private String leagueNameCn;
	private String leagueNameEn;
	private String leagueNameTr;
	private String leagueType;
	private String matchLevel1;
	private String matchLevel2;
	private String matchGroup;
	private Integer playerAId;
	private Integer playerBId;
	private String playerANameCn;
	private String playerANameEn;
	private String playerANameTr;
	private String playerBNameCn;
	private String playerBNameEn;
	private String playerBNameTr;
	private Integer playerAWinNum;
	private Integer playerBWinNum;
	private Integer maxField;
	private Integer currentField;
	private Integer winnerId;
	private String winReason;
	private Integer quiterId;
	private String quitReason;
	private Integer bestPlayer;
	private Integer bestScore;
	private String status;
	private Integer currentPlayer;
	private Integer currentScore;
	private Integer sort;
	private Date lastPublishedDate;
	private List<OttSnookerFrame> ottSnookerFrameList;

	@Id
	@Column(name = "score_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getScoreId() {
		return scoreId;
	}

	public void setScoreId(Long scoreId) {
		this.scoreId = scoreId;
	}

	@Column(name = "match_id")
	public Integer getMatchId() {
		return matchId;
	}

	public void setMatchId(Integer matchId) {
		this.matchId = matchId;
	}

	@Column(name = "season_id")
	public Integer getSeasonId() {
		return seasonId;
	}

	public void setSeasonId(Integer seasonId) {
		this.seasonId = seasonId;
	}

	@Column(name = "match_time")
	public Date getMatchTime() {
		if (null != matchTime) {
			SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
			setMatchTimeStr(sdf.format(matchTime));
		}
		return matchTime;
	}

	public void setMatchTime(Date matchTime) {
		this.matchTime = matchTime;
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

	@Column(name = "league_type")
	public String getLeagueType() {
		return leagueType;
	}

	public void setLeagueType(String leagueType) {
		this.leagueType = leagueType;
	}

	@Column(name = "match_level1")
	public String getMatchLevel1() {
		return matchLevel1;
	}

	public void setMatchLevel1(String matchLevel1) {
		this.matchLevel1 = matchLevel1;
	}

	@Column(name = "match_level2")
	public String getMatchLevel2() {
		return matchLevel2;
	}

	public void setMatchLevel2(String matchLevel2) {
		this.matchLevel2 = matchLevel2;
	}

	@Column(name = "match_group")
	public String getMatchGroup() {
		return matchGroup;
	}

	public void setMatchGroup(String matchGroup) {
		this.matchGroup = matchGroup;
	}

	@Column(name = "player_a_id")
	public Integer getPlayerAId() {
		return playerAId;
	}

	public void setPlayerAId(Integer playerAId) {
		this.playerAId = playerAId;
	}

	@Column(name = "player_b_id")
	public Integer getPlayerBId() {
		return playerBId;
	}

	public void setPlayerBId(Integer playerBId) {
		this.playerBId = playerBId;
	}

	@Column(name = "player_a_name_cn")
	public String getPlayerANameCn() {
		return playerANameCn;
	}

	public void setPlayerANameCn(String playerANameCn) {
		this.playerANameCn = playerANameCn;
	}

	@Column(name = "player_a_name_en")
	public String getPlayerANameEn() {
		return playerANameEn;
	}

	public void setPlayerANameEn(String playerANameEn) {
		this.playerANameEn = playerANameEn;
	}

	@Column(name = "player_a_name_tr")
	public String getPlayerANameTr() {
		return playerANameTr;
	}

	public void setPlayerANameTr(String playerANameTr) {
		this.playerANameTr = playerANameTr;
	}

	@Column(name = "player_b_name_cn")
	public String getPlayerBNameCn() {
		return playerBNameCn;
	}

	public void setPlayerBNameCn(String playerBNameCn) {
		this.playerBNameCn = playerBNameCn;
	}

	@Column(name = "player_b_name_en")
	public String getPlayerBNameEn() {
		return playerBNameEn;
	}

	public void setPlayerBNameEn(String playerBNameEn) {
		this.playerBNameEn = playerBNameEn;
	}

	@Column(name = "player_b_name_tr")
	public String getPlayerBNameTr() {
		return playerBNameTr;
	}

	public void setPlayerBNameTr(String playerBNameTr) {
		this.playerBNameTr = playerBNameTr;
	}

	@Column(name = "player_a_win_num")
	public Integer getPlayerAWinNum() {
		return playerAWinNum;
	}

	public void setPlayerAWinNum(Integer playerAWinNum) {
		this.playerAWinNum = playerAWinNum;
	}

	@Column(name = "player_b_win_num")
	public Integer getPlayerBWinNum() {
		return playerBWinNum;
	}

	public void setPlayerBWinNum(Integer playerBWinNum) {
		this.playerBWinNum = playerBWinNum;
	}

	@Column(name = "max_field")
	public Integer getMaxField() {
		return maxField;
	}

	public void setMaxField(Integer maxField) {
		this.maxField = maxField;
	}

	@Column(name = "current_field")
	public Integer getCurrentField() {
		return currentField;
	}

	public void setCurrentField(Integer currentField) {
		this.currentField = currentField;
	}

	@Column(name = "winner_id")
	public Integer getWinnerId() {
		return winnerId;
	}

	public void setWinnerId(Integer winnerId) {
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
	public Integer getQuiterId() {
		return quiterId;
	}

	public void setQuiterId(Integer quiterId) {
		this.quiterId = quiterId;
	}

	@Column(name = "quit_reason")
	public String getQuitReason() {
		return quitReason;
	}

	public void setQuitReason(String quitReason) {
		this.quitReason = quitReason;
	}

	@Column(name = "best_player")
	public Integer getBestPlayer() {
		return bestPlayer;
	}

	public void setBestPlayer(Integer bestPlayer) {
		this.bestPlayer = bestPlayer;
	}

	@Column(name = "best_score")
	public Integer getBestScore() {
		return bestScore;
	}

	public void setBestScore(Integer bestScore) {
		this.bestScore = bestScore;
	}

	@Column(name = "status")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "current_player")
	public Integer getCurrentPlayer() {
		return currentPlayer;
	}

	public void setCurrentPlayer(Integer currentPlayer) {
		this.currentPlayer = currentPlayer;
	}

	@Column(name = "current_score")
	public Integer getCurrentScore() {
		return currentScore;
	}

	public void setCurrentScore(Integer currentScore) {
		this.currentScore = currentScore;
	}

	@Column(name = "sort")
	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
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
	@JoinTable(name = "ott_snooker_score_frame", joinColumns = {
			@JoinColumn(name = "score_id", referencedColumnName = "score_id") }, inverseJoinColumns = {
					@JoinColumn(name = "frame_id", referencedColumnName = "frame_id") })
	public List<OttSnookerFrame> getOttSnookerFrameList() {
		return ottSnookerFrameList;
	}

	public void setOttSnookerFrameList(List<OttSnookerFrame> ottSnookerFrameList) {
		this.ottSnookerFrameList = ottSnookerFrameList;
	}

	@Transient
	public String getMatchTimeStr() {
		return matchTimeStr;
	}

	public void setMatchTimeStr(String matchTimeStr) {
		this.matchTimeStr = matchTimeStr;
	}

}
