package com.pccw.ott.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;
import org.springframework.orm.hibernate5.HibernateCallback;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import com.pccw.ott.model.OttSnookerPerson;

@Repository("ottSnookerPersonDao")
@SuppressWarnings({"unchecked", "rawtypes"})
public class OttSnookerPersonDaoImpl extends HibernateDaoSupport implements OttSnookerPersonDao {

	@Override
	public void deleteById(Integer playerId) {
		this.getHibernateTemplate().execute(new HibernateCallback() {
			@Override
			public Object doInHibernate(Session session) throws HibernateException {
				Query query = session.createNativeQuery("delete from ott_snooker_person where player_id = :playerId");
				query.setParameter("playerId", playerId);
				query.executeUpdate();
				return null;
			}
		});
	}

	@Override
	public void save(OttSnookerPerson person) {
		this.getHibernateTemplate().save(person);
	}
	
	@Override
	public List<OttSnookerPerson> findByPlayerName(String playerName, int first, int max, String sort, String order) {
		DetachedCriteria criteria = DetachedCriteria.forClass(OttSnookerPerson.class);
		if (StringUtils.isNotBlank(playerName)) {
			Disjunction disjunction = Restrictions.disjunction();  
			disjunction.add(Restrictions.like("nameCn", playerName, MatchMode.ANYWHERE));
			disjunction.add(Restrictions.like("nameEn", playerName, MatchMode.ANYWHERE));
			disjunction.add(Restrictions.like("nameTr", playerName, MatchMode.ANYWHERE));
			criteria.add(disjunction);
		}
		if (StringUtils.isNotBlank(sort) && order.equals("desc")) {
			criteria.addOrder(Order.desc(sort));
		} else if (StringUtils.isNotBlank(sort) && order.equals("asc")){
			criteria.addOrder(Order.asc(sort));
		}
		
		return (List<OttSnookerPerson>) this.getHibernateTemplate().findByCriteria(criteria, first, max);
	}

	@Override
	public Long countByPlayerName(String playerName) {
		DetachedCriteria criteria = DetachedCriteria.forClass(OttSnookerPerson.class);
		if (StringUtils.isNotBlank(playerName)) {
			Disjunction disjunction = Restrictions.disjunction();  
			disjunction.add(Restrictions.like("nameCn", playerName, MatchMode.ANYWHERE));
			disjunction.add(Restrictions.like("nameEn", playerName, MatchMode.ANYWHERE));
			disjunction.add(Restrictions.like("nameTr", playerName, MatchMode.ANYWHERE));
			criteria.add(disjunction);
		}
		return (Long) criteria.setProjection(Projections.rowCount()).getExecutableCriteria(this.getHibernateTemplate().getSessionFactory().getCurrentSession()).uniqueResult();
	}

	@Override
	public void batchSaveSnookerPersonList(List<OttSnookerPerson> insertedList) throws ParseException {
		for (OttSnookerPerson ottSnookerPerson : insertedList) {
			if (StringUtils.isNotBlank(ottSnookerPerson.getBirthdayStr())) {
				SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
				ottSnookerPerson.setBirthday(sdf.parse(ottSnookerPerson.getBirthdayStr()));
			}
			this.getHibernateTemplate().save(ottSnookerPerson);
		}
	}

	@Override
	public void batchUpdateSnookerPersonList(List<OttSnookerPerson> updatedList) throws ParseException {
		for (OttSnookerPerson ottSnookerPerson : updatedList) {
			OttSnookerPerson p = this.getHibernateTemplate().load(OttSnookerPerson.class, ottSnookerPerson.getId());
			p.setPlayerId(ottSnookerPerson.getPlayerId());
			p.setNameCn(ottSnookerPerson.getNameCn());
			p.setNameEn(ottSnookerPerson.getNameEn());
			p.setNameTr(ottSnookerPerson.getNameTr());
			p.setSex(ottSnookerPerson.getSex());
			p.setNationality(ottSnookerPerson.getNationality());
			if (StringUtils.isNotBlank(ottSnookerPerson.getBirthdayStr())) {
				SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
				p.setBirthday(sdf.parse(ottSnookerPerson.getBirthdayStr()));
			}
			p.setHeight(ottSnookerPerson.getHeight());
			p.setWeight(ottSnookerPerson.getWeight());
			p.setScore(ottSnookerPerson.getScore());
			p.setMaxScoreNum(ottSnookerPerson.getMaxScoreNum());
			p.setCurrentRank(ottSnookerPerson.getCurrentRank());
			p.setHighestRank(ottSnookerPerson.getHighestRank());
			p.setTransferTime(ottSnookerPerson.getTransferTime());
			p.setTotalMoney(ottSnookerPerson.getTotalMoney());
			p.setWinRecord(ottSnookerPerson.getWinRecord());
			p.setPoint(ottSnookerPerson.getPoint());
			p.setExperience(ottSnookerPerson.getExperience());
			p.setRemark(ottSnookerPerson.getRemark());
			this.getHibernateTemplate().update(p);
		}
	}

	@Override
	public void batchDeleteSnookerPersonList(List<OttSnookerPerson> deletedList) {
		for (OttSnookerPerson ottSnookerPerson : deletedList) {
			OttSnookerPerson p = this.getHibernateTemplate().load(OttSnookerPerson.class, ottSnookerPerson.getId());
			this.getHibernateTemplate().delete(p);
		}
	}
}
