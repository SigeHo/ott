package com.pccw.ott.dao;

import java.util.List;

import com.pccw.ott.model.OttPermission;

public interface OttPermissionDao {
	public List<OttPermission> findPermissionList(String permissionName, int first, int max);

	public OttPermission queryByPermissionName(String permissionName);

	public void insertPermission(OttPermission permission);

	public void updatePermission(OttPermission permission);

	public void deletePermissionById(Long permissionId);

	public List<OttPermission> findAllPermission();

	public OttPermission loadPermissionById(Long permissionId);

	public Long findCountByPermissionName(String permissionName);
}
