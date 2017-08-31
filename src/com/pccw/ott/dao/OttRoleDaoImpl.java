package com.pccw.ott.dao;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import com.pccw.ott.model.OttRole;

@Repository("ottRoleDao")
public class OttRoleDaoImpl extends HibernateDaoSupport implements OttRoleDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<OttRole> findRoleList(String roleName, int first, int max) {
		DetachedCriteria criteria = DetachedCriteria.forClass(OttRole.class);
		if (StringUtils.isNotBlank(roleName)) {
			criteria.add(Restrictions.like("roleName", roleName, MatchMode.ANYWHERE));	
		}
		return (List<OttRole>) this.getHibernateTemplate().findByCriteria(criteria, first, max);
	}

	@SuppressWarnings("unchecked")
	@Override
	public OttRole queryByRoleName(String roleName) {
		List<OttRole> list = (List<OttRole>) this.getHibernateTemplate().find("from OttRole r where r.roleName = ?", roleName);
		if (list.size() == 1) {
			return list.get(0);
		} else {
			return null;
		}
	}

	@Override
	public void saveRole(OttRole role) {
		this.getHibernateTemplate().save(role);
	}

	@Override
	public void updateRole(OttRole role) {
		OttRole originalRole = this.getHibernateTemplate().load(OttRole.class, role.getRoleId());
		originalRole.setRoleName(role.getRoleName());
		originalRole.setRoleDesc(role.getRoleDesc());
		this.getHibernateTemplate().update(originalRole);
	}

	@Override
	public void deleteRole(Long roleId) {
		OttRole role = this.getHibernateTemplate().load(OttRole.class, roleId);
		this.getHibernateTemplate().delete(role);
	}

}
