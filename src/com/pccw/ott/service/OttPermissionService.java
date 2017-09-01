package com.pccw.ott.service;

import java.util.List;

import com.pccw.ott.model.OttPermission;

public interface OttPermissionService {
	
	public List<OttPermission> findPermissionList(String permissionName, int first, int max);

	public OttPermission findExactByPermissionName(String permissionName);

	public void saveOttPermission(OttPermission permission);

	public void updatePermission(OttPermission permission);

	public void deletePermission(Long permissionId);

	public List<OttPermission> findAllPermission();

	public OttPermission loadPermissionById(Long valueOf);

}
