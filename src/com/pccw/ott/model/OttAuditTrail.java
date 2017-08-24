package com.pccw.ott.model;

import java.io.Serializable;
import java.sql.Clob;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "ott_audit_trail")
public class OttAuditTrail implements Serializable {

	private static final long serialVersionUID = 273537816011626334L;
	private Long id;
	private String atUser;
	private Date atTimestamp;
	private String atTableName;
	private String atEventText;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "at_user")
	public String getAtUser() {
		return atUser;
	}

	public void setAtUser(String atUser) {
		this.atUser = atUser;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "at_timestamp")
	public Date getAtTimestamp() {
		return atTimestamp;
	}

	public void setAtTimestamp(Date atTimestamp) {
		this.atTimestamp = atTimestamp;
	}

	@Column(name = "at_table_name")
	public String getAtTableName() {
		return atTableName;
	}

	public void setAtTableName(String atTableName) {
		this.atTableName = atTableName;
	}

	@Lob
	@Column(name = "at_event_text", columnDefinition="CLOB")
	public String getAtEventText() {
		return atEventText;
	}

	public void setAtEventText(String atEventText) {
		this.atEventText = atEventText;
	}

}
