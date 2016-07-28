package com.starwars.dao;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.starwars.model.Setting;

@Repository
public class SettingDao extends BaseDao<Setting> {

	public Long save(Setting setting) {
		em.persist(setting);
		return setting.getId();
	}

	public List<Setting> getAll() {
		return em.createQuery("SELECT p FROM Setting p", Setting.class)
				.getResultList();
	}

	public boolean IsEmpty() {
		Query query = em
				.createQuery("SELECT count(*) FROM Setting p", Long.class);
		boolean isEmpty;
		long count = (long) query.getSingleResult();
		if (count > 0) {
			isEmpty = false;
		} else {
			isEmpty = true;
		}
		return isEmpty;
	}

	public Setting findById(Long id) {
		return em.find(Setting.class, id);
	}
}