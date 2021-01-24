package com.starwars.script.service;

import com.starwars.script.dao.CharacterDao;
import com.starwars.script.exception.DefaultErrorMessage;
import com.starwars.script.exception.NotFoundMessage;
import com.starwars.script.model.Character;
import com.starwars.script.model.Setting;
import com.starwars.script.model.entity.CharacterEntity;
import com.starwars.script.util.DaoConverter;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.starwars.script.util.DaoConverter.convertCharacterEntitiesTo;
import static com.starwars.script.util.DaoConverter.convertCharactersTo;
import static com.starwars.script.util.SWConstants.REGEX_CHARACTER;
import static com.starwars.script.util.SWConstants.REGEX_DIALOGS;
import static com.starwars.script.util.SWConstants.REGEX_SETTINGS;
import static java.util.stream.Collectors.groupingBy;

@Component
@RequiredArgsConstructor
public class CharacterService {
	
	private final CharacterDao dao;

	public Character findById(String id) throws DefaultErrorMessage {
		final CharacterEntity characterEntity = dao.findById(id)
				.orElseThrow(() -> new NotFoundMessage(id, "character"));
		return DaoConverter.convertCharacterEntitiesTo(characterEntity);
	}

	public Collection<Character> getAll() {
		final List<CharacterEntity> characterEntities = dao.findAll();
		return convertCharacterEntitiesTo(characterEntities);
	}

	public static Collection<Character> getCharactersFromText(String text) {
		Pattern characterPattern = Pattern.compile(REGEX_CHARACTER);
		final Matcher matcher = characterPattern.matcher(text);
		Map<Integer, Character> characterMap = new HashMap<>();
		int index = 0;
		int previousPosition = 0;
		while(matcher.find()) {
			int currentPosition = matcher.start(1);
			if (index != 0) {
				String newSetting = text.substring(previousPosition, currentPosition);
				final Character previousCharacter = characterMap.get(index - 1);
				previousCharacter.setContent(newSetting);
			}
			Character character = Character.builder()
					.name(matcher.group(1))
					.build();
			characterMap.put(index, character);

			previousPosition = currentPosition;
			index++;
		}
		// Recover last item to set final content
		final Character lastCharacter = characterMap.get(index - 1);
		if (lastCharacter != null) {
			lastCharacter.setContent(text.substring(previousPosition));
		}
		return characterMap.values();
	}

	public static Collection<Character> getDistinctCharactersFromText(String text) {
		return mergeCharacters(getCharactersFromText(text));
	}

	public static Collection<Character> mergeCharacters(Collection<Character> characters) {
		final Map<String, List<Character>> collect = characters.stream().collect(groupingBy(Character::getName));
		final Collection<Character> mergedCharacters = collect.values().stream()
				.map(characterList -> characterList
						.stream()
						.reduce((c1, c2) -> Character.builder().name(c1.getName()).content(c1.getContent() + "\n\n" + c2.getContent()).build())
						.orElse(null)
				).filter(Objects::nonNull).collect(Collectors.toSet());

		return mergedCharacters;
	}

	public static void countWords(Character character) {
		final Map<String, Integer> countWords = countWords(character.getContent());
		character.setWordCountsMap(countWords);
	}

	public static Map<String, Integer> countWords(String text) {
		final String dialog = text.toLowerCase();
		final Matcher matcher = Pattern.compile(REGEX_DIALOGS).matcher(dialog);

		final List<String> results = new ArrayList<>();
		while (matcher.find()) {
			results.addAll(Arrays.asList(
					matcher
							.group(1)
							.trim()
							.split("\\s")));
		}

		Map<String, Integer> words = new HashMap<>();

		for (String word : results) {
			word = word.replaceAll("\\W","");
			if(StringUtils.isNotBlank(word)) {
				if (!words.containsKey(word)) {
					words.put(word, 1);
				} else {
					words.put(word, words.get(word) + 1);
				}
			}
		}
		return words;
	}

	public void save(Collection<Character> characters) {
		dao.saveAll(convertCharactersTo(characters));
	}

}