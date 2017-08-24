package com.pccw.ott.dao;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
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
	public Date getSysdate() {
		Timestamp timestamp = (Timestamp) this.getHibernateTemplate().execute(new HibernateCallback<Timestamp>() {
			@Override
			public Timestamp doInHibernate(Session session) throws HibernateException {
				return (Timestamp)session.createSQLQuery("select now()").list().get(0);
			}
		});
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd HH:mm:ssss");
			String str = sdf.format(timestamp);
			return sdf.parse(str);
		} catch (ParseException e) {
			logger.error(e.toString());
			return null;
		}
	}

	@Override
	public OttUser findUserByUsernameAndPassword(String username, String password) {
		List<OttUser> list = (List<OttUser>) this.getHibernateTemplate().find("from OttUser u where u.username = ? and u.password = ?", username, password);
		if (list.size() == 1) {
			return list.get(0);
		} else {
			return null;
		}
	}

}
