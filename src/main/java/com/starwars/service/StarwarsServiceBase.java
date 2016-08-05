package com.starwars.service;

import java.util.List;
import java.util.Map;

import com.starwars.exception.DefaultErrorMessage;

public interface StarwarsServiceBase <K,V> {
	public K getSetting(String line, Map<String,K> map,V base) throws DefaultErrorMessage;

	public K findById(Long id) throws DefaultErrorMessage;
	
	public List<K> getAll() throws DefaultErrorMessage;

}
