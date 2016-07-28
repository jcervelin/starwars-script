package com.starwars.service;

import java.util.Arrays;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.starwars.model.Character;
import com.starwars.model.WordCount;
@Service
public class StarWarsDialog implements StarwarsServiceBase<Character, Character>{

	public Character getSetting(String line,
			Map<String, Character> mapCharacter,
			Character character) {
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
	}
}