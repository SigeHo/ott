package com.pccw.ott.dao;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.orm.hibernate5.HibernateCallback;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import com.pccw.ott.model.OttRole;

@Repository("ottRoleDao")
public class OttRoleDaoImpl extends HibernateDaoSupport implements OttRoleDao {

	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<OttRole> findRoleList(String roleName, int first, int max) {
		return this.getHibernateTemplate().execute(new HibernateCallback<List<OttRole>>() {
			@Override
			public List<OttRole> doInHibernate(Session session) throws HibernateException {
				String hql = "from OttRole ";
				if (StringUtils.isNotBlank(roleName)) {
					hql += "where roleName like '%" + roleName + "%' ";
				}

				Query query = session.createQuery(hql);
				query.setFirstResult(first);
				query.setMaxResults(max);
				return query.list();
			}
		});
	}

	@Override
	public Long findCountByRoleName(String roleName) {
		String hql = "select count(*) from OttRole ";
		if (StringUtils.isNotBlank(roleName)) {
			hql += "where roleName like '%" + roleName + "%'";
		}
		return (Long) this.getHibernateTemplate().find(hql).get(0);
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
		originalRole.setPermissionList(role.getPermissionList());
		this.getHibernateTemplate().update(originalRole);
	}

	@Override
	public void deleteRole(Long roleId) {
		OttRole role = this.getHibernateTemplate().load(OttRole.class, roleId);
		this.getHibernateTemplate().delete(role);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<OttRole> findAllRole() {
		return (List<OttRole>) this.getHibernateTemplate().find("from OttRole");
	}

	@Override
	public OttRole findRoleById(Long roleId) {
		return this.getHibernateTemplate().load(OttRole.class, roleId);
	}

}
