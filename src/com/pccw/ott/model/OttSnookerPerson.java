package com.pccw.ott.model;

import java.io.Serializable;
import java.util.Date;

public class OttSnookerPerson implements Serializable {

	private static final long serialVersionUID = -4155965740118633554L;

	private Long playerId;
	private String nameCn;
	private String nameEn;
	private String nameTr;
	private String sex;
	private String nationality;
	private Date birthday;
	private Integer height;
	private Integer weight;
	private Integer score;
	private Integer maxScoreNum;
	private Integer currentRank;
	private Integer highestRank;
	private String transferTime;
	private Integer totalMoney;
	private Integer winRecord;
	private Integer point;
	private String experience;
	private String remark;

	public Long getPlayerId() {
		return playerId;
	}

	public void setPlayerId(Long playerId) {
		this.playerId = playerId;
	}

	public String getNameCn() {
		return nameCn;
	}

	public void setNameCn(String nameCn) {
		this.nameCn = nameCn;
	}

	public String getNameEn() {
		return nameEn;
	}

	public void setNameEn(String nameEn) {
		this.nameEn = nameEn;
	}

	public String getNameTr() {
		return nameTr;
	}

	public void setNameTr(String nameTr) {
		this.nameTr = nameTr;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public Integer getHeight() {
		return height;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}

	public Integer getWeight() {
		return weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public Integer getMaxScoreNum() {
		return maxScoreNum;
	}

	public void setMaxScoreNum(Integer maxScoreNum) {
		this.maxScoreNum = maxScoreNum;
	}

	public Integer getCurrentRank() {
		return currentRank;
	}

	public void setCurrentRank(Integer currentRank) {
		this.currentRank = currentRank;
	}

	public Integer getHighestRank() {
		return highestRank;
	}

	public void setHighestRank(Integer highestRank) {
		this.highestRank = highestRank;
	}

	public String getTransferTime() {
		return transferTime;
	}

	public void setTransferTime(String transferTime) {
		this.transferTime = transferTime;
	}

	public Integer getTotalMoney() {
		return totalMoney;
	}

	public void setTotalMoney(Integer totalMoney) {
		this.totalMoney = totalMoney;
	}

	public Integer getWinRecord() {
		return winRecord;
	}

	public void setWinRecord(Integer winRecord) {
		this.winRecord = winRecord;
	}

	public Integer getPoint() {
		return point;
	}

	public void setPoint(Integer point) {
		this.point = point;
	}

	public String getExperience() {
		return experience;
	}

	public void setExperience(String experience) {
		this.experience = experience;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
