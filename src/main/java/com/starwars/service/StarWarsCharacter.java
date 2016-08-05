package com.starwars.service;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.starwars.dao.BaseDao;
import com.starwars.exception.DefaultErrorMessage;
import com.starwars.model.Character;
import com.starwars.model.Setting;
import com.starwars.model.WordCount;
@Component
public class StarWarsCharacter implements StarwarsServiceBase<Character, Setting>{
	
	@Autowired
	private BaseDao<Character> dao;
	
	private final Logger logger = LoggerFactory.getLogger(StarWarsCharacter.class);

	public Character getSetting(String line, Map<String, Character> mapCharacter, Setting setting) throws DefaultErrorMessage {
		try {
			Character character = null;
			if (setting.getCharacters() == null) {
				setting.setCharacters(new LinkedHashSet<Character>());
			}
			if (mapCharacter.containsKey(line.trim())) {
				character = mapCharacter.get(line.trim());
				setting.getCharacters().add(character);
			} else {
				character = new Character();
				character.setName(line.trim());
				character.setWordCountsMap(new LinkedHashMap<String, WordCount>());
				mapCharacter.put(line.trim(), character);
				setting.getCharacters().add(character);
			}
			return character;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new DefaultErrorMessage();
		}
	}

	@Override
	public Character findById(Long id) throws DefaultErrorMessage {
		try {
			return dao.findById(id);
		} catch(Exception e) {
			logger.error(e.getMessage(),e);
			throw new DefaultErrorMessage();
		}
	}

	@Override
	public List<Character> getAll() throws DefaultErrorMessage {
		try {
			return dao.getAll();
		} catch(Exception e) {
			logger.error(e.getMessage(),e);
			throw new DefaultErrorMessage();
		}
	}
}