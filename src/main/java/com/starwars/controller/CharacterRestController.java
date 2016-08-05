package com.starwars.controller;

import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.starwars.exception.DefaultErrorMessage;
import com.starwars.exception.NotFoundMessage;
import com.starwars.model.Character;
import com.starwars.model.Setting;
import com.starwars.service.StarwarsServiceBase;

@RestController
@Transactional
@RequestMapping("/characters")
public class CharacterRestController extends BaseRestController {
	
	@Autowired
	private StarwarsServiceBase<Character, Setting> serviceCharacter;

	private final Logger logger = LoggerFactory
			.getLogger(CharacterRestController.class);

	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	@ResponseBody
	public Character findById(@PathVariable Long id) throws NotFoundMessage, DefaultErrorMessage {
		logger.info("Id received: " + id);

		Character c = serviceCharacter.findById(id);

		if (c != null) {
			return c;
		} else {
			throw new NotFoundMessage(id, "character");
		}
	}

	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public List<Character> getAll() throws DefaultErrorMessage {
		try {
			List<Character> characters = serviceCharacter.getAll();
			if (characters != null && characters.size() > 0) {
				return characters;
			} else {
				throw new Exception();
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new DefaultErrorMessage();
		}
	}
}