package com.starwars.service;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.starwars.model.Character;
import com.starwars.model.Setting;
import com.starwars.model.WordCount;
@Component
public class StarWarsCharacter implements StarwarsServiceBase<Character, Setting>{

	public Character getSetting(String line, Map<String, Character> mapCharacter, Setting setting) {
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
	}

}
