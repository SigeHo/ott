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
@Table(name = "ott_snooker_rank")
public class OttSnookerRank implements Serializable {

	private static final long serialVersionUID = -4292442577103526145L;
	private Long rankId;
	private String rankTitle;
	private String rankYear;
	private Integer playerId;
	private String nameCn;
	private String nameEn;
	private String nameTr;
	private String nationality;
	private Integer rank;
	private Integer point1;
	private Integer point2;
	private Integer point3;
	private Integer ptcPoint;
	private Integer totalPoint;
	private Date lastUpdatedTime;
	private List<OttSnookerPoint> snookerPointList;

	@Id
	@Column(name = "rank_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getRankId() {
		return rankId;
	}

	public void setRankId(Long rankId) {
		this.rankId = rankId;
	}

	@Column(name = "rank_title")
	public String getRankTitle() {
		return rankTitle;
	}

	public void setRankTitle(String rankTitle) {
		this.rankTitle = rankTitle;
	}

	@Column(name = "rank_year")
	public String getRankYear() {
		return rankYear;
	}

	public void setRankYear(String rankYear) {
		this.rankYear = rankYear;
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

	@Column(name = "nationality")
	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	@Column(name = "rank")
	public Integer getRank() {
		return rank;
	}

	public void setRank(Integer rank) {
		this.rank = rank;
	}

	@Column(name = "point1")
	public Integer getPoint1() {
		return point1;
	}

	public void setPoint1(Integer point1) {
		this.point1 = point1;
	}

	@Column(name = "point2")
	public Integer getPoint2() {
		return point2;
	}

	public void setPoint2(Integer point2) {
		this.point2 = point2;
	}

	@Column(name = "point3")
	public Integer getPoint3() {
		return point3;
	}

	public void setPoint3(Integer point3) {
		this.point3 = point3;
	}

	@Column(name = "ptc_point")
	public Integer getPtcPoint() {
		return ptcPoint;
	}

	public void setPtcPoint(Integer ptcPoint) {
		this.ptcPoint = ptcPoint;
	}

	@Column(name = "total_point")
	public Integer getTotalPoint() {
		return totalPoint;
	}

	public void setTotalPoint(Integer totalPoint) {
		this.totalPoint = totalPoint;
	}

	@Column(name = "last_updated_time")
	@Temporal(TemporalType.TIMESTAMP)
	@CreationTimestamp
	public Date getLastUpdatedTime() {
		return lastUpdatedTime;
	}

	public void setLastUpdatedTime(Date lastUpdatedTime) {
		this.lastUpdatedTime = lastUpdatedTime;
	}

	@OneToMany(cascade = { CascadeType.ALL}, fetch = FetchType.EAGER)
	@JoinTable(name = "ott_snooker_rank_point", joinColumns = {
			@JoinColumn(name = "player_id", referencedColumnName = "player_id") }, inverseJoinColumns = {
					@JoinColumn(name = "point_id", referencedColumnName = "point_id") })
	public List<OttSnookerPoint> getSnookerPointList() {
		return snookerPointList;
	}

	public void setSnookerPointList(List<OttSnookerPoint> snookerPointList) {
		this.snookerPointList = snookerPointList;
	}

}
