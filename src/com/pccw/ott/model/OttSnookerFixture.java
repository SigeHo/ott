package com.pccw.ott.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonProperty;

//@Entity
//@Table(name = "ott_snooker_fixture")
public class OttSnookerFixture implements Serializable {
	private static final long serialVersionUID = -7000740508755967868L;
	private String matchId;
	private Date matchTime;
	private String leagueId;
	private String leagueNameEn;
	private String leagueNameCn;
	private String leagueNameTr;
	private String leagueType;
	private String mMatchLevel;
	private String matchLevel;
	private String matchGroup;
	private String playAId;
	private String playANameEn;
	private String playANameCn;
	private String playANameTr;
	private String playBId;
	private String playBName;
	private String playBNameEn;
	private String playBNameCn;
	private String playBNameTr;
	private Long playAWinNum;
	private Long playBWinNum;
	private Long maxFrame;
	private Long currentFrameNum;
	private Long currentScoreA;
	private Long currentScoreB;
	private String currentBestPlayer;
	private Long currentCscoreA;
	private Long currentCscoreB;
	private Long currentMatchSort;
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

	@Column(name = "match_id")
	public String getMatchId() {
		return matchId;
	}

	public void setMatchId(String matchId) {
		this.matchId = matchId;
	}

	@Column(name = "match_time")
	public Date getMatchTime() {
		return matchTime;
	}

	public void setMatchTime(Date matchTime) {
		this.matchTime = matchTime;
	}

	@Column(name = "league_id")
	public String getLeagueId() {
		return leagueId;
	}

	public void setLeagueId(String leagueId) {
		this.leagueId = leagueId;
	}

	@Column(name = "league_name_en")
	public String getLeagueNameEn() {
		return leagueNameEn;
	}

	public void setLeagueNameEn(String leagueNameEn) {
		this.leagueNameEn = leagueNameEn;
	}

	@Column(name = "league_name_cn")
	public String getLeagueNameCn() {
		return leagueNameCn;
	}

	public void setLeagueNameCn(String leagueNameCn) {
		this.leagueNameCn = leagueNameCn;
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

	@Column(name = "m_match_level")
	public String getmMatchLevel() {
		return mMatchLevel;
	}

	public void setmMatchLevel(String mMatchLevel) {
		this.mMatchLevel = mMatchLevel;
	}

	@Column(name = "match_level")
	public String getMatchLevel() {
		return matchLevel;
	}

	public void setMatchLevel(String matchLevel) {
		this.matchLevel = matchLevel;
	}

	@Column(name = "matchGroup")
	public String getMatchGroup() {
		return matchGroup;
	}

	public void setMatchGroup(String matchGroup) {
		this.matchGroup = matchGroup;
	}

	@Column(name = "play_a_id")
	public String getPlayAId() {
		return playAId;
	}

	public void setPlayAId(String playAId) {
		this.playAId = playAId;
	}

	@Column(name = "play_a_name_en")
	public String getPlayANameEn() {
		return playANameEn;
	}

	public void setPlayANameEn(String playANameEn) {
		this.playANameEn = playANameEn;
	}

	@Column(name = "play_a_name_cn")
	public String getPlayANameCn() {
		return playANameCn;
	}

	public void setPlayANameCn(String playANameCn) {
		this.playANameCn = playANameCn;
	}

	@Column(name = "play_a_name_tr")
	public String getPlayANameTr() {
		return playANameTr;
	}

	public void setPlayANameHk(String playANameTr) {
		this.playANameTr = playANameTr;
	}

	@Column(name = "play_b_id")
	public String getPlayBId() {
		return playBId;
	}

	public void setPlayBId(String playBId) {
		this.playBId = playBId;
	}

	@Transient
	public String getPlayBName() {
		return playBName;
	}

	public void setPlayBName(String playBName) {
		this.playBName = playBName;
	}

	@Column(name = "play_b_name_en")
	public String getPlayBNameEn() {
		return playBNameEn;
	}

	public void setPlayBNameEn(String playBNameEn) {
		this.playBNameEn = playBNameEn;
	}

	@Column(name = "play_b_name_cn")
	public String getPlayBNameCn() {
		return playBNameCn;
	}

	public void setPlayBNameCn(String playBNameCn) {
		this.playBNameCn = playBNameCn;
	}

	@Column(name = "play_b_name_tr")
	public String getPlayBNameTr() {
		return playBNameTr;
	}

	public void setPlayBNameHk(String playBNameTr) {
		this.playBNameTr = playBNameTr;
	}

	@Column(name = "play_a_win_num")
	public Long getPlayAWinNum() { 
		return playAWinNum;
	}

	public void setPlayAWinNum(Long playAWinNum) {
		this.playAWinNum = playAWinNum;
	}

	@Column(name = "play_B_win_num")
	public Long getPlayBWinNum() {
		return playBWinNum;
	}

	public void setPlayBWinNum(Long playBWinNum) {
		this.playBWinNum = playBWinNum;
	}

	@Column(name = "max_frame")
	public Long getMaxFrame() {
		return maxFrame;
	}

	public void setMaxFrame(Long maxFrame) {
		this.maxFrame = maxFrame;
	}

	@Column(name = "current_frame_num")
	public Long getCurrentFrameNum() {
		return currentFrameNum;
	}

	public void setCurrentFrameNum(Long currentFrameNum) {
		this.currentFrameNum = currentFrameNum;
	}

	@Column(name = "current_score_a")
	public Long getCurrentScoreA() {
		return currentScoreA;
	}

	public void setCurrentScoreA(Long currentScoreA) {
		this.currentScoreA = currentScoreA;
	}

	@Column(name = "current_score_b")
	public Long getCurrentScoreB() {
		return currentScoreB;
	}

	public void setCurrentScoreB(Long currentScoreB) {
		this.currentScoreB = currentScoreB;
	}

	@Column(name = "current_best_player")
	public String getCurrentBestPlayer() {
		return currentBestPlayer;
	}

	public void setCurrentBestPlayer(String currentBestPlayer) {
		this.currentBestPlayer = currentBestPlayer;
	}

	@Column(name = "current_cscore_a")
	public Long getCurrentCscoreA() {
		return currentCscoreA;
	}

	public void setCurrentCscoreA(Long currentCscoreA) {
		this.currentCscoreA = currentCscoreA;
	}

	@Column(name = "current_cscore_b")
	public Long getCurrentCscoreB() {
		return currentCscoreB;
	}

	public void setCurrentCscoreB(Long currentCscoreB) {
		this.currentCscoreB = currentCscoreB;
	}

	@Column(name = "current_match_sort")
	public Long getCurrentMatchSort() {
		return currentMatchSort;
	}

	public void setCurrentMatchSort(Long currentMatchSort) {
		this.currentMatchSort = currentMatchSort;
	}

	@Column(name = "winner_id")
	public String getWinnerId() {
		return winnerId;
	}

	public void setWinnerId(String winnerId) {
		this.winnerId = winnerId;
	}

	@JsonProperty("wr")
	@Column(name = "winn_reason")
	public String getWinReason() {
		return winReason;
	}

	public void setWinReason(String winReason) {
		this.winReason = winReason;
	}

	@JsonProperty("qid")
	@Column(name = "quiter_id")
	public String getQuiterId() {
		return quiterId;
	}

	public void setQuiterId(String quiterId) {
		this.quiterId = quiterId;
	}

	@JsonProperty("qr")
	@Column(name = "quit_id")
	public String getQuitReason() {
		return quitReason;
	}

	public void setQuitReason(String quitReason) {
		this.quitReason = quitReason;
	}

	@JsonProperty("bestId")
	@Column(name = "best_player_id")
	public String getBestPlayerId() {
		return bestPlayerId;
	}

	public void setBestPlayerId(String bestPlayerId) {
		this.bestPlayerId = bestPlayerId;
	}

	@JsonProperty("bestscore")
	@Column(name = "best_score")
	public Long getBestScore() {
		return bestScore;
	}

	public void setBestScore(Long bestScore) {
		this.bestScore = bestScore;
	}

	@JsonProperty("st")
	@Column(name = "status")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@JsonProperty("curId")
	@Column(name = "current_player_id")
	public String getCurrentPlayerId() {
		return currentPlayerId;
	}

	public void setCurrentPlayerId(String currentPlayerId) {
		this.currentPlayerId = currentPlayerId;
	}

	@JsonProperty("curscore")
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

}
