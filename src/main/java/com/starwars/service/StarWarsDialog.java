package com.starwars.service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.starwars.dao.BaseDao;
import com.starwars.exception.DefaultErrorMessage;
import com.starwars.model.Character;
import com.starwars.model.WordCount;
@Service
public class StarWarsDialog implements StarwarsServiceBase<Character, Character>{
	
	@Autowired
	private BaseDao<Character> dao;
	
	private final Logger logger = LoggerFactory.getLogger(StarWarsDialog.class);

	public Character getSetting(String line,
			Map<String, Character> mapCharacter,
			Character character) throws DefaultErrorMessage {
		try {
			String lineTrim = line.replaceAll("\r","").replaceAll("\n","").trim();
			String[] arrayStrings = lineTrim.split(" ");
			WordCount wordCount = null;
			for (String word : Arrays.asList(arrayStrings)) {
				String newWord = word.replaceAll("[\\-\\+\\.\\^:,!?()\"]", "")
						.trim().toLowerCase();
				//Without a map it's not possible to get a WordCount object with just the word string
				if (character.getWordCountsMap().containsKey(newWord)) {
					wordCount = character.getWordCountsMap().get(newWord);
					wordCount.setCount(wordCount.getCount() + 1);
				} else {
					if(!newWord.isEmpty()) {
						wordCount = new WordCount();
						wordCount.setCount(1L);
						wordCount.setWord(newWord);
						character.getWordCountsMap().put(newWord, wordCount);
						wordCount.setCharacter(character);
					}
				}
			}
			return character;
		} catch(Exception e) {
			logger.error(e.getMessage(),e);
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