package com.pccw.ott.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pccw.ott.dao.OttRoleDao;
import com.pccw.ott.model.OttPermission;
import com.pccw.ott.model.OttRole;

@Service("ottRoleService")
@Transactional
public class OttRoleServiceImpl implements OttRoleService {
	
	@Autowired
	private OttRoleDao ottRoleDao;

	@Override
	public List<OttRole> findRoleList(String roleName, int first, int max) {
		return ottRoleDao.findRoleList(roleName, first, max);
	}

	@Override
	public OttRole findExactByRoleName(String roleName) {
		return ottRoleDao.queryByRoleName(roleName);
	}

	@Override
	public void saveOttRole(OttRole role) {
		ottRoleDao.saveRole(role);
	}

	@Override
	public void updateRole(OttRole role) {
		ottRoleDao.updateRole(role);
	}

	@Override
	public void deleteRole(Long roleId) {
		ottRoleDao.deleteRole(roleId);
	}

	@Override
	public List<OttPermission> listRolePermissions(Long roleId) {
		return null;
	}

}
