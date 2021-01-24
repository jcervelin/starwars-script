package com.starwars.script.service;

import com.starwars.script.exception.ForbiddenMessage;
import com.starwars.script.model.Setting;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.HashMap;
import java.util.Map;

import static com.starwars.script.util.FileUtils.convertFileToString;

@RunWith(MockitoJUnitRunner.class)
public class ScriptProcessorServiceTest {

    @InjectMocks
    private ScriptProcessorService target;

    @Mock
    private SettingService settingService;

    @Mock
    private CharacterService characterService;

    @Test
    public void saveScript() {
        final String script = convertFileToString("src/test/resources/screenplay.txt");
        final Setting setting = createSetting();

        Map<Integer, Setting> settingMap = new HashMap<>();
        settingMap.put(1, setting);

        Mockito.when(settingService.splitScriptIntoSettings(script)).thenReturn(settingMap);

        target.saveScript(script);

        Mockito.verify(settingService).addSettingsDetail(setting);
        Mockito.verify(settingService).save(settingMap.values());
        Mockito.verify(characterService).save(Mockito.anyCollection());
    }

    @Test(expected = ForbiddenMessage.class)
    public void shouldSaveScriptThrowException() {
        final String script = convertFileToString("src/test/resources/screenplay.txt");
        final Setting setting = createSetting();

        Map<Integer, Setting> settingMap = new HashMap<>();
        settingMap.put(1, setting);

        Mockito.when(settingService.splitScriptIntoSettings(script)).thenReturn(settingMap);
        Mockito.doThrow(ForbiddenMessage.class).when(settingService).save(settingMap.values());

        target.saveScript(script);
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