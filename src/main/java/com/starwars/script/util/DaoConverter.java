package com.starwars.script.util;

import com.starwars.script.model.Setting;
import com.starwars.script.model.Character;
import com.starwars.script.model.entity.CharacterEntity;
import com.starwars.script.model.entity.SettingEntity;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.stream.Collectors;

public class DaoConverter {

    public static Collection<Setting> convertSettingEntitiesTo(Collection<SettingEntity> settingEntities) {
        return emptyIfNull(settingEntities)
                .stream()
                .map(DaoConverter::convertSettingEntitiesTo)
                .collect(Collectors.toSet());
    }

    public static Setting convertSettingEntitiesTo(SettingEntity settingEntity) {
        return Setting
                .builder()
                .id(settingEntity.getId())
                .name(settingEntity.getName())
                .content(settingEntity.getContent())
                .characters(convertCharacterEntitiesTo(settingEntity.getCharacters()))
                .build();
    }

    public static Collection<SettingEntity> convertSettingsTo(Collection<Setting> settings) {
        return emptyIfNull(settings)
                .stream()
                .map(DaoConverter::convertSettingsTo)
                .collect(Collectors.toSet());
    }

    public static SettingEntity convertSettingsTo(Setting setting) {
        final Collection<CharacterEntity> characterEntities = convertCharactersTo(setting.getCharacters());
        return SettingEntity.builder()
                .name(setting.getName())
                .content(setting.getContent())
                .characters(characterEntities)
                .build();
    }

    public static Collection<CharacterEntity> convertCharactersTo(Collection<Character> characters) {
        return emptyIfNull(characters)
                .stream()
                .map(DaoConverter::convertCharactersTo)
                .collect(Collectors.toSet());
    }

    public static CharacterEntity convertCharactersTo(Character character) {
        final HashMap<String, Integer> words = new HashMap<>();
        if (character.getWordCountsMap() != null) {
            words.putAll(character.getWordCountsMap());
        }
        return CharacterEntity
                .builder()
                .name(character.getName())
                .words(words)
                .build();
    }

    public static Collection<Character> convertCharacterEntitiesTo(Collection<CharacterEntity> characterEntities) {
        return emptyIfNull(characterEntities).stream()
                .map(DaoConverter::convertCharacterEntitiesTo)
                .collect(Collectors.toSet());
    }

    public static Character convertCharacterEntitiesTo(CharacterEntity characterEntity) {
        return Character.builder()
                .id(characterEntity.getId())
                .name(characterEntity.getName())
                .wordCountsMap(characterEntity.getWords())
                .build();
    }

    public static <T> Collection<T> emptyIfNull(Collection<T> collection) {
        return collection == null ? Collections.emptyList() : collection;
    }


}
