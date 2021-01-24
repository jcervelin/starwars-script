package com.starwars.script.service;

import com.starwars.script.dao.SettingDao;
import com.starwars.script.exception.ForbiddenMessage;
import com.starwars.script.exception.NotFoundMessage;
import com.starwars.script.model.Setting;
import com.starwars.script.model.entity.SettingEntity;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import static com.starwars.script.util.FileUtils.convertFileToString;
import static org.mockito.ArgumentMatchers.anyCollection;

@RunWith(MockitoJUnitRunner.class)
public class SettingServiceTest {

    @InjectMocks
    private SettingService target;

    @Mock
    private SettingDao dao;

    @Test
    public void splitScriptIntoSettings() {
        final String text = convertFileToString("src/test/resources/screenplay_3_settings.txt");

        Setting s1 = Setting.builder()
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
                .build();

        Setting s2 = Setting.builder()
                .name("EXT. SPACECRAFT IN SPACE")
                .content("EXT. SPACECRAFT IN SPACE\n" +
                        "\n" +
                        "The Imperial craft has easily overtaken the Rebel Blockade\n" +
                        "Runner. The smaller Rebel ship is being drawn into the\n" +
                        "underside dock of the giant Imperial starship.\n" +
                        "\n")
                .build();

        Setting s3 = Setting.builder()
                .name("INT. REBEL BLOCKADE RUNNER - MAIN PASSAGEWAY")
                .content("INT. REBEL BLOCKADE RUNNER - MAIN PASSAGEWAY\n" +
                        "\n" +
                        "An explosion rocks the ship as two robots, Artoo-Detoo (R2-\n" +
                        "D2) and See-Threepio (C-3PO) struggle to make their way\n" +
                        "through the shaking, bouncing passageway. Both robots are\n" +
                        "old and battered. Artoo is a short, claw-armed tripod. His\n" +
                        "face is a mass of computer lights surrounding a radar eye.\n" +
                        "Threepio, on the other hand, is a tall, slender robot of\n" +
                        "human proportions. He has a gleaming bronze-like metallic\n" +
                        "surface of an Art Deco design.\n" +
                        "\n" +
                        "Another blast shakes them as they struggle along their way.\n" +
                        "\n" +
                        "                      THREEPIO\n" +
                        "          Did you hear that? They've shut down\n" +
                        "          the main reactor. We'll be destroyed\n" +
                        "          for sure. This is madness!\n" +
                        "\n" +
                        "Rebel troopers rush past the robots and take up positions in\n" +
                        "the main passageway. They aim their weapons toward the door.\n" +
                        "\n" +
                        "                      THREEPIO\n" +
                        "          We're doomed!\n" +
                        "\n" +
                        "The little R2 unit makes a series of electronic sounds that\n" +
                        "only another robot could understand.\n" +
                        "\n" +
                        "                      THREEPIO\n" +
                        "          There'll be no escape for the Princess\n" +
                        "          this time.\n" +
                        "\n" +
                        "Artoo continues making beeping sounds. Tension mounts as\n" +
                        "loud metallic latches clank and the scream of heavy equipment\n" +
                        "are heard moving around the outside hull of the ship.\n" +
                        "\n" +
                        "                      THREEPIO\n" +
                        "          What's that?\n" +
                        "\n")
                .build();

        final Map<Integer, Setting> integerSettingMap = target.splitScriptIntoSettings(text);

        Assertions.assertThat(integerSettingMap.size()).isEqualTo(3);
        Assertions.assertThat(integerSettingMap.values()).containsExactlyInAnyOrder(s1, s2, s3);
    }

    @Test(expected = ForbiddenMessage.class)
    public void shouldSplitScriptIntoSettingsReturnError() {
        target.splitScriptIntoSettings(null);
    }

    @Test
    public void shouldAddSettingsDetails() {
        final Setting setting = createSetting();
        target.addSettingsDetail(setting);
        Assertions.assertThat(setting.getCharacters()).isNotEmpty();
        Assertions.assertThat(setting.getCharacters().stream().findAny().get().getWordCountsMap()).isNotEmpty();
    }

    @Test
    public void shouldFindById() {
        String id = "id123";
        final SettingEntity characterEntity = SettingEntity.builder()
                .id(id)
                .name("TestName")
                .characters(Collections.emptyList())
                .build();

        final Setting expected = Setting.builder()
                .id(id)
                .name("TestName")
                .characters(Collections.emptySet())
                .build();

        Mockito.when(dao.findById(id)).thenReturn(Optional.of(characterEntity));

        final Setting result = target.findById(id);

        Assertions.assertThat(result).isEqualTo(expected);
    }

    @Test(expected = NotFoundMessage.class)
    public void shouldReturnErrorIfIdIsNotFound() {
        String id = "id123";

        Mockito.when(dao.findById(id)).thenReturn(Optional.empty());

        target.findById(id);
    }

    @Test
    public void shouldFindAll() {
        String id = "id123";
        String id2 = "id456";
        final SettingEntity settingEntity = SettingEntity.builder()
                .id(id)
                .characters(Collections.emptySet())
                .name("TestName")
                .build();
        final SettingEntity settingEntity2 = SettingEntity.builder()
                .id(id2)
                .characters(Collections.emptySet())
                .name("TestName2")
                .build();

        final Setting expected = Setting.builder()
                .id(id)
                .characters(Collections.emptySet())
                .name("TestName")
                .build();
        final Setting expected2 = Setting.builder()
                .id(id2)
                .characters(Collections.emptySet())
                .name("TestName2")
                .build();

        Mockito.when(dao.findAll()).thenReturn(Arrays.asList(settingEntity, settingEntity2));

        final Collection<Setting> results = target.getAll();

        Assertions.assertThat(results).containsExactlyInAnyOrder(expected, expected2);
    }

    @Test
    public void shouldReturnEmptyList() {
        Mockito.when(dao.findAll()).thenReturn(Collections.emptyList());

        final Collection<Setting> results = target.getAll();
        Assertions.assertThat(results).isEmpty();
    }

    @Test
    public void shouldSaveCharacters() {
        target.save(Collections.singleton(createSetting()));
        Mockito.verify(dao).saveAll(anyCollection());
    }

    @Test(expected = RuntimeException.class)
    public void shouldPropagateErrorIfSaveCharactersReturnError() {
        Mockito.when(dao.saveAll(anyCollection())).thenThrow(RuntimeException.class);
        target.save(Collections.singleton(createSetting()));
    }


    @Test
    public void shouldIsEmptyReturnTrue() {
        Mockito.when(dao.count()).thenReturn(0L);
        Assertions.assertThat(target.isEmpty()).isTrue();
    }

    @Test
    public void shouldIsEmptyReturnFalse() {
        Mockito.when(dao.count()).thenReturn(1L);
        Assertions.assertThat(target.isEmpty()).isFalse();
    }

    @Test(expected = RuntimeException.class)
    public void shouldIsEmptyReturnException() {
        Mockito.when(dao.count()).thenThrow(RuntimeException.class);
        target.isEmpty();
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
                .build();
    }

}