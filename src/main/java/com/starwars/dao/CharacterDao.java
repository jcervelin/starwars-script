package com.starwars.dao;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.starwars.model.Character;

@Repository
public class CharacterDao extends BaseDao<Character> {

	public Long save(Character character) {
		em.persist(character);
		return character.getId();
	}

	public boolean IsEmpty() {
		Query query = em.createQuery("SELECT count(*) FROM Character c",
				Long.class);
		boolean isEmpty;
		long count = (long) query.getSingleResult();
		if (count > 0) {
			isEmpty = false;
		} else {
			isEmpty = true;
		}
		return isEmpty;
	}

	public Character findById(Long id) {
		return em.find(Character.class, id);
	}

	public List<Character> getAll() {
		return em.createQuery("SELECT c FROM Character c", Character.class)
				.getResultList();
	}
}