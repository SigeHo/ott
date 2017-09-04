package com.pccw.ott.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pccw.ott.dao.OttPermissionDao;
import com.pccw.ott.model.OttPermission;

@Service("ottPermissionService")
@Transactional
public class OttPermissionServiceImpl implements OttPermissionService {

	@Autowired
	private OttPermissionDao ottPermissionDao;

	@Override
	public List<OttPermission> findPermissionList(String permissionName, int first, int max) {
		return ottPermissionDao.findPermissionList(permissionName, first, max);
	}

	@Override
	public OttPermission findExactByPermissionName(String permissionName) {
		return ottPermissionDao.queryByPermissionName(permissionName);
	}

	@Override
	public void saveOttPermission(OttPermission permission) {
		ottPermissionDao.insertPermission(permission);
	}

	@Override
	public void updatePermission(OttPermission permission) {
		ottPermissionDao.updatePermission(permission);
	}

	@Override
	public void deletePermission(Long permissionId) {
		ottPermissionDao.deletePermissionById(permissionId);
	}

	@Override
	public List<OttPermission> findAllPermission() {
		return ottPermissionDao.findAllPermission();
	}

	@Override
	public OttPermission loadPermissionById(Long permissionId) {
		return ottPermissionDao.loadPermissionById(permissionId);
	}

	@Override
	public Long findCountByPermissionName(String permissionName) {
		return ottPermissionDao.findCountByPermissionName(permissionName);
	}

}
