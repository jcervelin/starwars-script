package com.starwars.script.service;

import com.starwars.script.dao.CharacterDao;
import com.starwars.script.exception.NotFoundMessage;
import com.starwars.script.model.Character;
import com.starwars.script.model.entity.CharacterEntity;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.starwars.script.service.CharacterService.getCharactersFromText;
import static com.starwars.script.service.CharacterService.mergeCharacters;
import static com.starwars.script.util.FileUtils.convertFileToString;
import static org.mockito.ArgumentMatchers.anyCollection;

@RunWith(MockitoJUnitRunner.class)
public class CharacterServiceTest {

    @InjectMocks
    private CharacterService target;

    @Mock
    private CharacterDao dao;

    @Test
    public void shouldSaveCharacters() {
        final List<Character> mergedCharacters = get3MergedCharacters();
        target.save(mergedCharacters);

        Mockito.verify(dao).saveAll(anyCollection());
    }

    @Test(expected = RuntimeException.class)
    public void shouldPropagateErrorIfSaveCharactersReturnError() {
        final List<Character> mergedCharacters = get3MergedCharacters();
        Mockito.when(dao.saveAll(anyCollection())).thenThrow(RuntimeException.class);
        target.save(mergedCharacters);
    }

    @Test
    public void shouldFindById() {
        String id = "id123";
        final CharacterEntity characterEntity = CharacterEntity.builder()
                .id(id)
                .name("TestName")
                .build();

        final Character expected = Character.builder()
                .id(id)
                .name("TestName")
                .build();

        Mockito.when(dao.findById(id)).thenReturn(Optional.of(characterEntity));

        final Character result = target.findById(id);

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
        final CharacterEntity characterEntity = CharacterEntity.builder()
                .id(id)
                .name("TestName")
                .build();
        final CharacterEntity characterEntity2 = CharacterEntity.builder()
                .id(id2)
                .name("TestName2")
                .build();

        final Character expected = Character.builder()
                .id(id)
                .name("TestName")
                .build();
        final Character expected2 = Character.builder()
                .id(id2)
                .name("TestName2")
                .build();

        Mockito.when(dao.findAll()).thenReturn(Arrays.asList(characterEntity, characterEntity2));

        final Collection<Character> results = target.getAll();

        Assertions.assertThat(results).containsExactlyInAnyOrder(expected, expected2);
    }

    @Test
    public void shouldGetCharactersFromText() {
        final List<Character> expectedCharacters = get6Characters();

        final String text = convertFileToString("src/test/resources/valid_setting.txt");
        final Collection<Character> result = getCharactersFromText(text);
        Assertions.assertThat(result.size()).isEqualTo(6);
        Assertions.assertThat(result).containsExactlyInAnyOrderElementsOf(expectedCharacters);
    }

    @Test
    public void shouldGetEmptyListIfInvalidFile() {

        final String text = convertFileToString("src/test/resources/invalid_setting.txt");
        final Collection<Character> result = getCharactersFromText(text);
        Assertions.assertThat(result).isEmpty();
    }

    @Test
    public void shouldMergeCharactersAndText() {
        List<Character> expectedMergedCharacters = get3MergedCharacters();
        final Collection<Character> mergedCharacters = mergeCharacters(get6Characters());
        Assertions.assertThat(mergedCharacters.size()).isEqualTo(3);
        Assertions.assertThat(mergedCharacters).containsExactlyInAnyOrderElementsOf(expectedMergedCharacters);
    }

    @Test
    public void shouldCountWordsOfCharacter() {
        Character character = Character.builder()
                .name("THREEPIO")
                .content("                      THREEPIO\n" +
                        "          At last! Where have you been you have?\n" +
                        "\n" +
                        "Stormtroopers can be heard battling in the distance.\n")
                .build();

        var entry1 = new HashMap.SimpleEntry<>("been",1);
        var entry2 = new HashMap.SimpleEntry<>("at",1);
        var entry3 = new HashMap.SimpleEntry<>("last",1);
        var entry4 = new HashMap.SimpleEntry<>("have",2);
        var entry5 = new HashMap.SimpleEntry<>("where",1);
        var entry6 = new HashMap.SimpleEntry<>("you",2);

        CharacterService.countWords(character);
        final Map<String, Integer> wordCountsMap = character.getWordCountsMap();

        Assertions.assertThat(wordCountsMap.size()).isEqualTo(6);
        Assertions.assertThat(wordCountsMap).containsExactly(entry1, entry2, entry3, entry4, entry5, entry6);
    }

    @Test
    public void shouldCountWordsReturnEmptyIfDialogNotFound() {
        Character character = Character.builder()
                .name("THREEPIO")
                .content("                      THREEPIO\n" +
                        "          ? & * $\n" +
                        "\n" +
                        "Stormtroopers can be heard battling in the distance.\n")
                .build();

        CharacterService.countWords(character);
        final Map<String, Integer> wordCountsMap = character.getWordCountsMap();

        Assertions.assertThat(wordCountsMap).isEmpty();
    }



    private List<Character> get6Characters() {
        Character vader1 = Character.builder()
                .name("VADER")
                .content("VADER\n" +
                        "          Where are those transmissions you\n" +
                        "          intercepted?\n" +
                        "\n" +
                        "Vader lifts the Rebel off his feet by his throat.\n" +
                        "\n" +
                        "                      ")
                .build();
        Character vader2 = Character.builder()
                .name("VADER")
                .content("VADER\n" +
                        "          What have you done with those plans?\n" +
                        "\n" +
                        "                      ")
                .build();
        Character vader3 = Character.builder()
                .name("VADER")
                .content("VADER\n" +
                        "          If this is a consular ship... were\n" +
                        "          is the Ambassador?\n" +
                        "\n" +
                        "The Rebel refuses to speak but eventually cries out as the\n" +
                        "Dark Lord begins to squeeze the officer's throat, creating a\n" +
                        "gruesome snapping and choking, until the soldier goes limp.\n" +
                        "Vader tosses the dead soldier against the wall and turns to\n" +
                        "his troops.\n" +
                        "\n" +
                        "                      ")
                .build();
        Character vader4 = Character.builder()
                .name("VADER")
                .content("VADER\n" +
                        "          Commander, tear this ship apart until\n" +
                        "          you've found those plans and bring\n" +
                        "          me the Ambassador. I want her alive!\n" +
                        "\n" +
                        "The stormtroopers scurry into the subhallways.\n" +
                        "\n")
                .build();
        Character imperialOfficer = Character.builder()
                .name("IMPERIAL OFFICER")
                .content("IMPERIAL OFFICER\n" +
                        "          The Death Star plans are not in the\n" +
                        "          main computer.\n" +
                        "\n" +
                        "Vader squeezes the neck of the Rebel Officer, who struggles\n" +
                        "in vain.\n" +
                        "\n" +
                        "                      ")
                .build();
        Character rebelOfficer = Character.builder()
                .name("REBEL OFFICER")
                .content("REBEL OFFICER\n" +
                        "          We intercepted no transmissions.\n" +
                        "          Aaah... This is a consular ship.\n" +
                        "          Were on a diplomatic mission.\n" +
                        "\n" +
                        "                      ")
                .build();
        return Arrays.asList(vader1, vader2, vader3, vader4, imperialOfficer, rebelOfficer);
    }

    private List<Character> get3MergedCharacters() {
        Character vader = Character.builder()
                .name("VADER")
                .content("VADER\n" +
                        "          Where are those transmissions you\n" +
                        "          intercepted?\n" +
                        "\n" +
                        "Vader lifts the Rebel off his feet by his throat.\n" +
                        "\n" +
                        "                      \n" +
                        "\n" +
                        "VADER\n" +
                        "          What have you done with those plans?\n" +
                        "\n" +
                        "                      \n" +
                        "\n" +
                        "VADER\n" +
                        "          If this is a consular ship... were\n" +
                        "          is the Ambassador?\n" +
                        "\n" +
                        "The Rebel refuses to speak but eventually cries out as the\n" +
                        "Dark Lord begins to squeeze the officer's throat, creating a\n" +
                        "gruesome snapping and choking, until the soldier goes limp.\n" +
                        "Vader tosses the dead soldier against the wall and turns to\n" +
                        "his troops.\n" +
                        "\n" +
                        "                      \n" +
                        "\n" +
                        "VADER\n" +
                        "          Commander, tear this ship apart until\n" +
                        "          you've found those plans and bring\n" +
                        "          me the Ambassador. I want her alive!\n" +
                        "\n" +
                        "The stormtroopers scurry into the subhallways.\n" +
                        "\n")
                .build();
        Character imperialOfficer = Character.builder()
                .name("IMPERIAL OFFICER")
                .content("IMPERIAL OFFICER\n" +
                        "          The Death Star plans are not in the\n" +
                        "          main computer.\n" +
                        "\n" +
                        "Vader squeezes the neck of the Rebel Officer, who struggles\n" +
                        "in vain.\n" +
                        "\n" +
                        "                      ")
                .build();
        Character rebelOfficer = Character.builder()
                .name("REBEL OFFICER")
                .content("REBEL OFFICER\n" +
                        "          We intercepted no transmissions.\n" +
                        "          Aaah... This is a consular ship.\n" +
                        "          Were on a diplomatic mission.\n" +
                        "\n" +
                        "                      ")
                .build();
        return Arrays.asList(vader, imperialOfficer, rebelOfficer);
    }
}