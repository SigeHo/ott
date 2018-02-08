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
@SuppressWarnings({ "unchecked", "rawtypes" })
public class OttRoleDaoImpl extends HibernateDaoSupport implements OttRoleDao {

	@Override
	public List<OttRole> findRoleList(String roleName, int first, int max) {
		return this.getHibernateTemplate().execute(new HibernateCallback<List<OttRole>>() {
			@Override
			public List<OttRole> doInHibernate(Session session) throws HibernateException {
				String hql = "from OttRole ";
				Query query = session.createQuery(hql);
				query.setFirstResult(first);
				query.setMaxResults(max);
				if (StringUtils.isNotBlank(roleName)) {
					hql += "where roleName like :roleName";
					query.setParameter("roleName", "%" + roleName + "%");
				}

				return query.list();
			}
		});
	}

	@Override
	public Long findCountByRoleName(String roleName) {
		String hql = "select count(1) from OttRole ";
		if (StringUtils.isNotBlank(roleName)) {
			hql += "where roleName like '%" + roleName + "%'";
		}
		return (Long) this.getHibernateTemplate().find(hql).get(0);
	}

	@Override
	public OttRole queryByRoleName(String roleName) {
		String hql = "from OttRole r where r.roleName like '%:roleName%'";
		Session session = this.getHibernateTemplate().getSessionFactory().getCurrentSession();
		Query query = session.createQuery(hql);
		query.setParameter("roleName", roleName);
		List<OttRole> list = query.list();
		if (list.size() > 0)
			return list.get(0);
		return null;
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

	@Override
	public List<OttRole> findAllRole() {
		return (List<OttRole>) this.getHibernateTemplate().find("from OttRole");
	}

	@Override
	public OttRole findRoleById(Long roleId) {
		return this.getHibernateTemplate().load(OttRole.class, roleId);
	}

}
