package com.pccw.ott.service;

import java.util.List;

import com.pccw.ott.model.OttPermission;
import com.pccw.ott.model.OttRole;

public interface OttRoleService {

	public List<OttRole> findRoleList(String roleName, int first, int max);

	public OttRole findExactByRoleName(String roleName);

	public void saveOttRole(OttRole role);

	public void updateRole(OttRole role);

	public void deleteRole(Long roleId);

	public List<OttPermission> listRolePermissions(Long roleId);

}
