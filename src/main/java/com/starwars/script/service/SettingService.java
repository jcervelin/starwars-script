package com.starwars.script.service;

import com.starwars.script.dao.SettingDao;
import com.starwars.script.exception.DefaultErrorMessage;
import com.starwars.script.exception.ForbiddenMessage;
import com.starwars.script.exception.NotFoundMessage;
import com.starwars.script.model.Character;
import com.starwars.script.model.Setting;
import com.starwars.script.model.entity.SettingEntity;
import com.starwars.script.util.DaoConverter;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.starwars.script.util.DaoConverter.convertSettingsTo;
import static com.starwars.script.util.SWConstants.REGEX_SETTINGS;

@Service
@RequiredArgsConstructor
public class SettingService {
	
	private final SettingDao dao;

	public Map<Integer, Setting> splitScriptIntoSettings(String script) {
	    if (StringUtils.isBlank(script)) {
	        throw new ForbiddenMessage("Script must not be empty");
        }

		Pattern patternSettings = Pattern.compile(REGEX_SETTINGS);
		final Matcher matcherSettings = patternSettings.matcher(script);

		Map<Integer, Setting> settingsPosition = new HashMap<>();
		int index = 0;
		int previousPosition = 0;
		while(matcherSettings.find()) {
			int currentPosition = matcherSettings.start();
			if (index != 0) {
				String newSetting = script.substring(previousPosition, currentPosition);
				final Setting previousSetting = settingsPosition.get(index - 1);
				previousSetting.setContent(newSetting);
			}
			Setting setting = Setting.builder()
					.name(script.substring(currentPosition, matcherSettings.end()))
					.build();
			settingsPosition.put(index, setting);

			previousPosition = currentPosition;
			index++;
		}

		// Recover last item to set its content
		final Setting lastSetting = settingsPosition.get(index - 1);
		lastSetting.setContent(script.substring(previousPosition));

		return settingsPosition;
	}

	public void addSettingsDetail(Setting setting) {
		final Collection<Character> charactersWithPotentialDuplicates = CharacterService.getCharactersFromText(setting.getContent());

		final Collection<Character> mergedCharacters = CharacterService.mergeCharacters(charactersWithPotentialDuplicates);
		mergedCharacters.forEach(CharacterService::countWords);

		setting.setCharacters(mergedCharacters);
	}

	public Setting findById(String id) throws DefaultErrorMessage {
		final SettingEntity settingEntity = dao.findById(id)
				.orElseThrow(NotFoundMessage::new);
		return DaoConverter.convertSettingEntitiesTo(settingEntity);
	}

	public Collection<Setting> getAll() {
		final List<SettingEntity> settingEntities = dao.findAll();
		return DaoConverter.convertSettingEntitiesTo(settingEntities);
	}

	public void save(Collection<Setting> settings) {
		dao.saveAll(convertSettingsTo(settings));
	}

	public boolean isEmpty() {
		return dao.count() == 0;
	}

}