package com.pccw.ott.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import com.pccw.ott.model.OttPermission;

@Repository("ottPermissionDao")
public class OttPermissionDaoImpl extends HibernateDaoSupport implements OttPermissionDao {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<OttPermission> findPermissionList(String permissionName, int first, int max) {
		DetachedCriteria criteria = DetachedCriteria.forClass(OttPermission.class);
		if (StringUtils.isNotBlank(permissionName)) {
			criteria.add(Restrictions.like("permissionName", permissionName, MatchMode.ANYWHERE));
		}
		List list = this.getHibernateTemplate().findByCriteria(criteria, first, max);
		if (list.size() > 0) {
			return list;
		} else {
			return new ArrayList<>();
		}
	}

	@Override
	public OttPermission queryByPermissionName(String permissionName) {
		DetachedCriteria criteria = DetachedCriteria.forClass(OttPermission.class);
		criteria.add(Restrictions.eq("permissionName", permissionName));
		List<OttPermission> list = (List<OttPermission>) this.getHibernateTemplate().findByCriteria(criteria);
		if (list.size() > 0 && list.size() == 1)
			return list.get(0);
		else
			return null;
	}

	@Override
	public void insertPermission(OttPermission permission) {
		this.getHibernateTemplate().save(permission);
	}

	@Override
	public void updatePermission(OttPermission permission) {
		OttPermission originalPermission = this.getHibernateTemplate().get(OttPermission.class, permission.getPermissionId());
		originalPermission.setPermissionName(permission.getPermissionName());
		originalPermission.setPermissionUrl(permission.getPermissionUrl());
		originalPermission.setPermissionDesc(permission.getPermissionDesc());
		originalPermission.setUpdatedBy(permission.getUpdatedBy());
		this.getHibernateTemplate().update(originalPermission);
	}

	@Override
	public void deletePermissionById(Long permissionId) {
		OttPermission originalPermission = this.getHibernateTemplate().get(OttPermission.class, permissionId);
		this.getHibernateTemplate().delete(originalPermission);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<OttPermission> findAllPermission() {
		return (List<OttPermission>) this.getHibernateTemplate().find("from OttPermission");
	}

	@Override
	public OttPermission loadPermissionById(Long permissionId) {
		return getHibernateTemplate().load(OttPermission.class, permissionId);
	}

	@Override
	public Long findCountByPermissionName(String permissionName) {
		String hql = "select count(*) from OttPermission ";
		if (StringUtils.isNotBlank(permissionName)) {
			hql += "where permissionName like '%" + permissionName + "%'";
		}
		return (Long) this.getHibernateTemplate().find(hql).get(0);
	}

}
