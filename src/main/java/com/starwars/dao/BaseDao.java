package com.starwars.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

@Repository
public abstract class BaseDao<T> {
	
	@PersistenceContext
	protected EntityManager em;

	public abstract Long save(T object);

	public abstract List<T> getAll();
	
	public abstract boolean IsEmpty();
	
	public abstract T findById(Long id);

}