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

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "ott_role")
public class OttRole implements Serializable {

	private static final long serialVersionUID = -5608830946852240196L;
	private Long roleId;
	private String roleName;
	private String roleDesc;
	private Long createdBy;
	private Date createDate;
	private Long updatedBy;
	private Date updateDate;
	private List<OttPermission> permissionList;


	@Id
	@Column(name = "role_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	@Column(name = "role_name")
	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	@Column(name = "role_desc")
	public String getRoleDesc() {
		return roleDesc;
	}

	public void setRoleDesc(String roleDesc) {
		this.roleDesc = roleDesc;
	}

	@Column(name = "created_by")
	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name = "create_date")
	@Temporal(TemporalType.TIMESTAMP)
	@CreationTimestamp
	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@Column(name = "updated_by")
	public Long getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(Long updatedBy) {
		this.updatedBy = updatedBy;
	}

	@Column(name = "update_date")
	@Temporal(TemporalType.TIMESTAMP)
	@UpdateTimestamp
	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	@OneToMany(cascade = { CascadeType.PERSIST}, fetch = FetchType.EAGER)
	@JoinTable(name = "ott_role_permission", joinColumns = {
			@JoinColumn(name = "role_id", referencedColumnName = "role_id") }, inverseJoinColumns = {
					@JoinColumn(name = "permission_id", referencedColumnName = "permission_id") })
	public List<OttPermission> getPermissionList() {
		return permissionList;
	}

	public void setPermissionList(List<OttPermission> permissionList) {
		this.permissionList = permissionList;
	}

}
