package com.starwars.script.controller;

import com.starwars.script.exception.DefaultErrorMessage;
import com.starwars.script.exception.NotFoundMessage;
import com.starwars.script.model.Character;
import com.starwars.script.service.CharacterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/api/characters")
@Slf4j
@RequiredArgsConstructor
public class CharacterRestController {
	
	private final CharacterService serviceCharacter;

	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	@ResponseBody
	public Character findById(@PathVariable String id) throws NotFoundMessage, DefaultErrorMessage {
		log.info("Id received: " + id);

		return serviceCharacter.findById(id);
	}

	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public Collection<Character> getAll() {
		Collection<Character> characters = serviceCharacter.getAll();
		if (characters != null && characters.size() > 0) {
			return characters;
		} else {
			throw new NotFoundMessage();
		}
	}
}