package com.pccw.ott.dao;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.hibernate5.HibernateCallback;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import com.pccw.ott.model.OttUser;


@Repository("ottUserDao")
public class OttUserDaoImpl extends HibernateDaoSupport implements OttUserDao {

	private static Logger logger = LoggerFactory.getLogger(OttUserDaoImpl.class);

	@Override
	public OttUser findUserByUsernameAndPassword(String username, String password) {
		List<OttUser> list = (List<OttUser>) this.getHibernateTemplate().find("from OttUser u where u.username = ? and u.password = ?", username, password);
		if (list.size() == 1) {
			return list.get(0);
		} else {
			return null;
		}
	}

	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
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
		String hql = "select count(*) from OttUser ";
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

	@SuppressWarnings("unchecked")
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

}
