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
import com.starwars.model.Setting;
import com.starwars.service.StarwarsServiceBase;

@RestController
@Transactional
@RequestMapping("/settings")
public class SettingsRestController extends BaseRestController {

	@Autowired
	private StarwarsServiceBase<Setting, Setting> serviceSettings;

	private final Logger logger = LoggerFactory.getLogger(SettingsRestController.class);

	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public List<Setting> settings() throws DefaultErrorMessage {
		List<Setting> settings = serviceSettings.getAll();
		if (settings != null && settings.size() > 0) {
			return settings;
		} else {
			DefaultErrorMessage e = new DefaultErrorMessage();
			logger.error(e.getMessage(),e);
			throw new DefaultErrorMessage();
		}
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	@ResponseBody
	public Setting setting(@PathVariable Long id) throws DefaultErrorMessage, NotFoundMessage {
		Setting setting = serviceSettings.findById(id);

		if (setting != null) {
			return setting;
		} else {
			throw new NotFoundMessage(id, "setting");
		}
	}
}