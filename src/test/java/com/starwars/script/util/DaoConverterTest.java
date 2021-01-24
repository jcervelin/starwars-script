package com.starwars.script.util;

import com.starwars.script.model.Character;
import com.starwars.script.model.Setting;
import com.starwars.script.model.entity.CharacterEntity;
import com.starwars.script.model.entity.SettingEntity;
import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;

public class DaoConverterTest {

    @Test
    public void shouldConvertSettingEntitiesTo() {
        Setting expected = createSetting();
        final Setting setting = DaoConverter.convertSettingEntitiesTo(createSettingEntity());
        Assertions.assertThat(setting).isEqualTo(expected);
    }

    @Test
    public void shouldConvertSettingEntitiesToProcessEmptyObjects() {
        final Setting setting = DaoConverter.convertSettingEntitiesTo(SettingEntity.builder().build());
        Assertions.assertThat(setting).isNotNull();
    }

    @Test
    public void shouldConvertSettingEntitiesToList() {
        Setting expected = createSetting();
        final Collection<Setting> settings = DaoConverter.convertSettingEntitiesTo(Collections.singleton(createSettingEntity()));
        Assertions.assertThat(settings).containsExactlyInAnyOrder(expected);
    }

    @Test
    public void shouldConvertSettingEntitiesToProcessEmptyLists() {
        final Collection<Setting> setting = DaoConverter.convertSettingEntitiesTo(Collections.emptySet());
        Assertions.assertThat(setting).isNotNull();
    }

    @Test
    public void shouldConvertSettingTo() {
        SettingEntity expected = createSettingEntity();
        final SettingEntity setting = DaoConverter.convertSettingsTo(createSetting());
        Assertions.assertThat(setting).isEqualTo(expected);
    }

    @Test
    public void shouldConvertSettingToProcessEmptyObjects() {
        final SettingEntity settingEntity = DaoConverter.convertSettingsTo(Setting.builder().build());
        Assertions.assertThat(settingEntity).isNotNull();
    }

    @Test
    public void shouldConvertSettingsToProcessList() {
        SettingEntity expected = createSettingEntity();
        final Collection<SettingEntity> settings = DaoConverter.convertSettingsTo(Collections.singleton(createSetting()));
        Assertions.assertThat(settings).containsExactlyInAnyOrder(expected);
    }

    @Test
    public void shouldConvertSettingsToProcessEmptyList() {
        final Collection<SettingEntity> settings = DaoConverter.convertSettingsTo(Collections.emptySet());
        Assertions.assertThat(settings).isNotNull();
    }

    @Test
    public void shouldConvertCharactersTo() {
        final CharacterEntity expected = getCharacterEntity();
        final CharacterEntity result = DaoConverter.convertCharactersTo(getCharacter());
        Assertions.assertThat(result).isEqualTo(expected);
    }

    @Test
    public void shouldConvertCharactersToProcessEmptyObjects() {
        final CharacterEntity result = DaoConverter.convertCharactersTo(Character.builder().build());
        Assertions.assertThat(result).isNotNull();
    }

    @Test
    public void shouldConvertCharactersListTo() {
        final Collection<CharacterEntity> expected = getCharacterEntities();
        final Collection<CharacterEntity> result = DaoConverter.convertCharactersTo(getCharactersWithoutContent());
        Assertions.assertThat(result).isEqualTo(expected);
    }

    @Test
    public void shouldConvertCharactersToProcessEmptyList() {
        final Collection<CharacterEntity> settings = DaoConverter.convertCharactersTo(Collections.emptySet());
        Assertions.assertThat(settings).isNotNull();
    }

    @Test
    public void convertCharacterEntitiesTo() {
        final Character expected = getCharacterWithoutContent();
        final Character result = DaoConverter.convertCharacterEntitiesTo(getCharacterEntity());
        Assertions.assertThat(result).isEqualTo(expected);
    }

    @Test
    public void shouldConvertCharacterEntitiesToProcessEmptyObjects() {
        final Character result = DaoConverter.convertCharacterEntitiesTo(CharacterEntity.builder().build());
        Assertions.assertThat(result).isNotNull();
    }

    @Test
    public void shouldConvertCharacterEntitiesListTo() {
        final Collection<Character> expected = getCharactersWithoutContent();
        final Collection<Character> result = DaoConverter.convertCharacterEntitiesTo(getCharacterEntities());
        Assertions.assertThat(result).isEqualTo(expected);
    }

    @Test
    public void shouldConvertCharacterEntitiesListToProcessEmptyList() {
        final Collection<Character> settings = DaoConverter.convertCharacterEntitiesTo(Collections.emptySet());
        Assertions.assertThat(settings).isNotNull();
    }

    private Setting createSetting() {
        return Setting.builder()
                .name("INT. REBEL BLOCKADE RUNNER")
                .content("INT. REBEL BLOCKADE RUNNER\n" +
                        "\n" +
                        "The nervous Rebel troopers aim their weapons. Suddenly a\n" +
                        "tremendous blast opens up a hole in the main passageway and\n" +
                        "a score of fearsome armored spacesuited stormtroopers make\n" +
                        "their way into the smoke-filled corridor.\n" +
                        "\n" +
                        "In a few minutes the entire passageway is ablaze with\n" +
                        "laserfire. The deadly bolts ricochet in wild random patterns\n" +
                        "creating huge explosions. Stormtroopers scatter and duck\n" +
                        "behind storage lockers. Laserbolts hit several Rebel soldiers\n" +
                        "who scream and stagger through the smoke, holding shattered\n" +
                        "arms and faces.\n" +
                        "\n" +
                        "An explosion hits near the robots.\n" +
                        "\n" +
                        "                      THREEPIO\n" +
                        "          I should have known better than to\n" +
                        "          trust the logic of a half-sized\n" +
                        "          thermocapsulary dehousing assister...\n" +
                        "\n" +
                        "Artoo counters with an angry rebuttal as the battle rages\n" +
                        "around the two hapless robots.\n" +
                        "\n" +
                        "                                                   FADE OUT:\n" +
                        "\n" +
                        "END CREDITS OVER STARS\n" +
                        "\n" +
                        "                          THE END\n")
                .characters(Collections.emptySet())
                .build();
    }

    private SettingEntity createSettingEntity() {
        return SettingEntity.builder()
                .name("INT. REBEL BLOCKADE RUNNER")
                .content("INT. REBEL BLOCKADE RUNNER\n" +
                        "\n" +
                        "The nervous Rebel troopers aim their weapons. Suddenly a\n" +
                        "tremendous blast opens up a hole in the main passageway and\n" +
                        "a score of fearsome armored spacesuited stormtroopers make\n" +
                        "their way into the smoke-filled corridor.\n" +
                        "\n" +
                        "In a few minutes the entire passageway is ablaze with\n" +
                        "laserfire. The deadly bolts ricochet in wild random patterns\n" +
                        "creating huge explosions. Stormtroopers scatter and duck\n" +
                        "behind storage lockers. Laserbolts hit several Rebel soldiers\n" +
                        "who scream and stagger through the smoke, holding shattered\n" +
                        "arms and faces.\n" +
                        "\n" +
                        "An explosion hits near the robots.\n" +
                        "\n" +
                        "                      THREEPIO\n" +
                        "          I should have known better than to\n" +
                        "          trust the logic of a half-sized\n" +
                        "          thermocapsulary dehousing assister...\n" +
                        "\n" +
                        "Artoo counters with an angry rebuttal as the battle rages\n" +
                        "around the two hapless robots.\n" +
                        "\n" +
                        "                                                   FADE OUT:\n" +
                        "\n" +
                        "END CREDITS OVER STARS\n" +
                        "\n" +
                        "                          THE END\n")
                .characters(Collections.emptySet())
                .build();
    }

    private Character getCharacter() {
        return Character.builder()
                .name("VADER")
                .content("VADER\n" +
                        "          Where are those transmissions you\n" +
                        "          intercepted?\n" +
                        "\n" +
                        "Vader lifts the Rebel off his feet by his throat.\n" +
                        "\n" +
                        "                      ")
                .wordCountsMap(new HashMap<>())
                .build();
    }

    private Character getCharacterWithoutContent() {
        return Character.builder()
                .name("VADER")
                .wordCountsMap(new HashMap<>())
                .build();
    }

    private Collection<Character> getCharactersWithoutContent() {
        return Collections.singleton(getCharacterWithoutContent());
    }

    private Collection<CharacterEntity> getCharacterEntities() {
        return Collections.singleton(getCharacterEntity() );
    }

    private CharacterEntity getCharacterEntity() {
        return CharacterEntity.builder()
                .name("VADER")
                .words(new HashMap<>())
                .build();
    }
}