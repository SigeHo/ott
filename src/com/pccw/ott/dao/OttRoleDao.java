package com.pccw.ott.dao;

import java.util.List;

import com.pccw.ott.model.OttRole;

public interface OttRoleDao {

	public List<OttRole> findRoleList(String roleName, int first, int max);

	public OttRole queryByRoleName(String roleName);

	public void saveRole(OttRole role);

	public void updateRole(OttRole role);

	public void deleteRole(Long roleId);

}
