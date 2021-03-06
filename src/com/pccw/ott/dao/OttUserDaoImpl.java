package com.pccw.ott.dao;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;
import org.springframework.orm.hibernate5.HibernateCallback;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import com.pccw.ott.model.OttUser;


@Repository("ottUserDao")
@SuppressWarnings({ "unchecked", "rawtypes" })
public class OttUserDaoImpl extends HibernateDaoSupport implements OttUserDao {

	@Override
	public OttUser findUserByUsernameAndPassword(String username, String password) {
		List<OttUser> list = this.getHibernateTemplate().execute(new HibernateCallback<List<OttUser>>() {
			@Override
			public List<OttUser> doInHibernate(Session session) throws HibernateException {
				String hql = "from OttUser u where u.username = :username and u.password = :password";
				Query query = session.createQuery(hql);
				query.setParameter("username", username);
				query.setParameter("password", password);
				return query.list();
			}
		});
		if (list.size() == 1) {
			return list.get(0);
		} else {
			return null;
		}
	}

	@Override
	public List<OttUser> findUserByUserName(String usernameForSearch, int first, int max) {
		return this.getHibernateTemplate().execute(new HibernateCallback<List<OttUser>>() {
			@Override
			public List<OttUser> doInHibernate(Session session) throws HibernateException {
				String hql = "from OttUser ";
				if (StringUtils.isNotBlank(usernameForSearch))
					hql += "where username like '%" + usernameForSearch + "%' ";
				Query query = session.createQuery(hql);
				query.setFirstResult(first);
				query.setMaxResults(max);
				return query.list();
			}
		});
	}

	@Override
	public Long findCountByUserName(String usernameForSearch) {
		String hql = "select count(1) from OttUser ";
		if (StringUtils.isNotBlank(usernameForSearch)) {
			hql += "where username like '%" + usernameForSearch + "%'";
		}
		return (Long) this.getHibernateTemplate().find(hql).get(0);
	}

	@Override
	public void deleteUser(Long userId) {
		OttUser user = this.getHibernateTemplate().load(OttUser.class, userId);
		this.getHibernateTemplate().delete(user);
	}

	@Override
	public OttUser findExactByUsername(String username) {
		DetachedCriteria criteria = DetachedCriteria.forClass(OttUser.class);
		criteria.add(Restrictions.eq("username", username));
		List<OttUser> list = (List<OttUser>) this.getHibernateTemplate().findByCriteria(criteria);
		if (list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}
	}

	@Override
	public void saveUser(OttUser user) {
		this.getHibernateTemplate().save(user);
	}

	@Override
	public void updateUser(OttUser user) {
		OttUser originalUser = this.getHibernateTemplate().load(OttUser.class, user.getUserId());
		originalUser.setUsername(user.getUsername());
		originalUser.setUserEmail(user.getUserEmail());
		originalUser.setUserDesc(user.getUserDesc());
		originalUser.setUpdatedBy(user.getUpdatedBy());
		originalUser.setRole(user.getRole());
		this.getHibernateTemplate().update(originalUser);
	}

	@Override
	public void updateUserPassword(Long userId, String newPassword) {
		OttUser originalUser = this.getHibernateTemplate().load(OttUser.class, userId);
		originalUser.setPassword(newPassword);
		originalUser.setUpdatedBy(userId);
		this.getHibernateTemplate().update(originalUser);
	}

}
