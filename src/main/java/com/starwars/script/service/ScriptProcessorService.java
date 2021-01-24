package com.starwars.script.service;

import com.starwars.script.model.Character;
import com.starwars.script.model.Setting;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class ScriptProcessorService {

	private final SettingService settingService;
	private final CharacterService characterService;

	public void saveScript(String script) {

			final Map<Integer, Setting> settingsMap = settingService.splitScriptIntoSettings(script);

			settingsMap.values()
					.forEach(settingService::addSettingsDetail);

		settingService.save(settingsMap.values());

		final Collection<Character> allDistinctCharacters = CharacterService.getDistinctCharactersFromText(script);
		allDistinctCharacters.forEach(CharacterService::countWords);

		characterService.save(allDistinctCharacters);

	}

	public boolean isEmpty() {
		return settingService.isEmpty();
	}
}