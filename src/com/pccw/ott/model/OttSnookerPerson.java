package com.pccw.ott.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@Entity
@Table(name = "ott_snooker_person")
public class OttSnookerPerson implements Serializable {

	private static final long serialVersionUID = -4155965740118633554L;

	private Long id;
	private Integer playerId;
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
	private Date transferTime;
	private Integer totalMoney;
	private Integer winRecord;
	private Integer point;
	private String experience;
	private String remark;
	private Date lastPublishedDate;
	private String birthdayStr;
	private String transferTimeStr;

	@Column(name = "id")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "player_id")
	public Integer getPlayerId() {
		return playerId;
	}

	public void setPlayerId(Integer playerId) {
		this.playerId = playerId;
	}

	@Column(name = "name_cn")
	public String getNameCn() {
		return nameCn;
	}

	public void setNameCn(String nameCn) {
		this.nameCn = nameCn;
	}

	@Column(name = "name_en")
	public String getNameEn() {
		return nameEn;
	}

	public void setNameEn(String nameEn) {
		this.nameEn = nameEn;
	}

	@Column(name = "name_tr")
	public String getNameTr() {
		return nameTr;
	}

	public void setNameTr(String nameTr) {
		this.nameTr = nameTr;
	}

	@Column(name = "sex")
	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	@Column(name = "nationality")
	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	@Column(name = "birthday")
	@Temporal(TemporalType.DATE)
	public Date getBirthday() {
		if (null != birthday) {
			SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
			this.setBirthdayStr(sdf.format(birthday));
		}
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	@Column(name = "height")
	public Integer getHeight() {
		return height;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}

	@Column(name = "weight")
	public Integer getWeight() {
		return weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}

	@Column(name = "score")
	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	@Column(name = "max_score_num")
	public Integer getMaxScoreNum() {
		return maxScoreNum;
	}

	public void setMaxScoreNum(Integer maxScoreNum) {
		this.maxScoreNum = maxScoreNum;
	}

	@Column(name = "current_rank")
	public Integer getCurrentRank() {
		return currentRank;
	}

	public void setCurrentRank(Integer currentRank) {
		this.currentRank = currentRank;
	}

	@Column(name = "highest_rank")
	public Integer getHighestRank() {
		return highestRank;
	}

	public void setHighestRank(Integer highestRank) {
		this.highestRank = highestRank;
	}

	@Column(name = "transfer_time")
	@Temporal(TemporalType.DATE)
	public Date getTransferTime() {
		if (null != transferTime) {
			SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
			this.setTransferTimeStr(sdf.format(transferTime));
		}
		return transferTime;
	}

	public void setTransferTime(Date transferTime) {
		this.transferTime = transferTime;
	}

	@Column(name = "total_money")
	public Integer getTotalMoney() {
		return totalMoney;
	}

	public void setTotalMoney(Integer totalMoney) {
		this.totalMoney = totalMoney;
	}

	@Column(name = "win_record")
	public Integer getWinRecord() {
		return winRecord;
	}

	public void setWinRecord(Integer winRecord) {
		this.winRecord = winRecord;
	}

	@Column(name = "point")
	public Integer getPoint() {
		return point;
	}

	public void setPoint(Integer point) {
		this.point = point;
	}

	@Column(name = "experience")
	public String getExperience() {
		return experience;
	}

	public void setExperience(String experience) {
		this.experience = experience;
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
	public Date getLastPublishedDate() {
		return lastPublishedDate;
	}

	public void setLastPublishedDate(Date lastPublishedDate) {
		this.lastPublishedDate = lastPublishedDate;
	}

	@Transient
	public String getBirthdayStr() {
		return birthdayStr;
	}

	public void setBirthdayStr(String birthdayStr) {
		this.birthdayStr = birthdayStr;
	}

	@Transient
	public String getTransferTimeStr() {
		return transferTimeStr;
	}

	public void setTransferTimeStr(String transferTimeStr) {
		this.transferTimeStr = transferTimeStr;
	}

}
