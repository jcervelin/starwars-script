package com.starwars.service;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.starwars.dao.BaseDao;
import com.starwars.model.Character;
import com.starwars.model.Setting;
import com.starwars.model.WordCount;

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

	public void saveScript(String script) {

		Map<String, Setting> mapMovie = new LinkedHashMap<String, Setting>();
		Map<String, Character> mapCharacter = new LinkedHashMap<String, Character>();

		Setting setting = null;
		Character character = null;

		String[] array = script.split("\n");

		for (String line : Arrays.asList(array)) {
			line.replaceAll("\r", "");
			if (line.startsWith("INT./EXT.") || line.startsWith("INT. ")
					|| line.startsWith("EXT. ")) {
				setting = sWsetting.getSetting(line, mapMovie, setting);
			} else if (line.matches("\\W{22}.*")
				&& setting != null) {
				character = sWcharacter.getSetting(line, mapCharacter, setting);
			} else if (line.matches("\\W{10}.*") && setting != null) {			
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
	}
}