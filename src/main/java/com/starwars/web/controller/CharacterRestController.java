package com.starwars.web.controller;

import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.starwars.exception.DefaultErrorMessage;
import com.starwars.exception.NotFoundMessage;
import com.starwars.model.Character;

@RestController
@Transactional
@RequestMapping("/characters")
public class CharacterRestController extends BaseRestController {

	private final Logger logger = LoggerFactory
			.getLogger(CharacterRestController.class);

	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	@ResponseBody
	public Character findById(@PathVariable Long id) throws Exception {
		try {
			logger.info("Id received: " + id);

			Character c = characterDao.findById(id);

			if (c != null) {
				return c;
			} else {
				throw new NotFoundMessage(id, "character");
			}
		} catch (NotFoundMessage e) {
			logger.error(e.getMessage(), e);
			throw e;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new DefaultErrorMessage();
		}
	}

	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public List<Character> getAll() throws Exception {
		try {
			List<Character> characters = characterDao.getAll();
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