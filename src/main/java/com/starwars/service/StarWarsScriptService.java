package com.starwars.service;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.starwars.dao.BaseDao;
import com.starwars.exception.DefaultErrorMessage;
import com.starwars.model.Character;
import com.starwars.model.Setting;
import com.starwars.model.WordCount;

import static com.starwars.util.SWConstants.*;

@Component
@Transactional
public class StarWarsScriptService {

	@Autowired
	private BaseDao<Setting> dao;
	@Autowired
	private StarwarsServiceBase<Setting, Setting> sWsetting;
	@Autowired
	private StarwarsServiceBase<Character, Setting> sWcharacter;
	@Autowired
	private StarwarsServiceBase<Character, Character> sWDialog;

	private final Logger logger = LoggerFactory.getLogger(StarWarsScriptService.class);

	public void saveScript(String script) throws DefaultErrorMessage {
		try {
			Map<String, Setting> mapMovie = new LinkedHashMap<String, Setting>();
			Map<String, Character> mapCharacter = new LinkedHashMap<String, Character>();

			Setting setting = null;
			Character character = null;

			String[] array = script.split(BLANK_LINE_ESCAPE);

			for (String line : array) {
				line = line.replaceAll(CARRIAGE_RETURN_ESCAPE, EMPTY);
				if (line.startsWith(INT_EXT) || line.startsWith(INT)
						|| line.startsWith(EXT)) {
					setting = sWsetting.getSetting(line, mapMovie, setting);
				} else if (line.matches(REGEX_22_BLANK_SPACES)
					&& setting != null) {
					character = sWcharacter.getSetting(line, mapCharacter, setting);
				} else if (line.matches(REGEX_10_BLANK_SPACES) && setting != null) {			
					character = sWDialog.getSetting(line, mapCharacter, character);
				}
			}
			
			// Get words from wordCountsMap object (Map) to WordCounts object (Set)
			// That was made to attach the "Set" in the JPA instead of the "Map"
			// and also to show a JSON more clean and according the specification
			for (Map.Entry<String, Setting> map : mapMovie.entrySet()) {
				if (map.getValue().getCharacters() != null)
					for (Character c : map.getValue().getCharacters()) {
						c.setWordCounts(new LinkedHashSet<WordCount>());
						c.getWordCounts().addAll(c.getWordCountsMap().values());
					}
				dao.save(map.getValue());
			}
		} catch(Exception e) {
			logger.error(e.getMessage(),e);
			throw new DefaultErrorMessage();
		}
	}
	
	public boolean IsEmpty() throws DefaultErrorMessage {
		try {
			return dao.IsEmpty();
		} catch(Exception e) {
			logger.error(e.getMessage(),e);
			throw new DefaultErrorMessage();
		}
	}
}