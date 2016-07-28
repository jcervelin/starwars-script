package com.starwars.service;

import java.util.Map;

public interface StarwarsServiceBase <K,V> {
	public K getSetting(String line, Map<String,K> map,V base);
}
