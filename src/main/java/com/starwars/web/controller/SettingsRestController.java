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
import com.starwars.model.Setting;

@RestController
@Transactional
@RequestMapping("/settings")
public class SettingsRestController extends BaseRestController {

	private final Logger logger = LoggerFactory.getLogger(CharacterRestController.class);

	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public List<Setting> settings() throws Exception {
		try {
			List<Setting> settings = settingDao.getAll();
			if (settings != null && settings.size() > 0) {
				return settings;
			} else {
				throw new Exception();
			}
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			throw new DefaultErrorMessage();
		}
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	@ResponseBody
	public Setting setting(@PathVariable Long id) throws Exception {
		try {
			Setting setting = settingDao.findById(id);

			if (setting != null) {
				return setting;
			} else {
				throw new NotFoundMessage(id, "setting");
			}
		} catch (NotFoundMessage e) {
			logger.error(e.getMessage(),e);
			throw e;
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			throw new DefaultErrorMessage();
		}
	}
}