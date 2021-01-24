package com.starwars.script.controller;

import com.starwars.script.exception.DefaultErrorMessage;
import com.starwars.script.exception.NotFoundMessage;
import com.starwars.script.model.Setting;
import com.starwars.script.model.entity.SettingEntity;
import com.starwars.script.service.SettingService;
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
@RequestMapping("/api/settings")
@RequiredArgsConstructor
@Slf4j
public class SettingsRestController {

	private final SettingService serviceSettings;

	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public Collection<Setting> settings() throws DefaultErrorMessage {
		Collection<Setting> settings = serviceSettings.getAll();
		if (settings != null && settings.size() > 0) {
			return settings;
		} else {
			DefaultErrorMessage e = new DefaultErrorMessage();
			log.error(e.getMessage(),e);
			throw new DefaultErrorMessage();
		}
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	@ResponseBody
	public Setting setting(@PathVariable String id) throws DefaultErrorMessage, NotFoundMessage {
		Setting setting = serviceSettings.findById(id);

		if (setting != null) {
			return setting;
		} else {
			throw new NotFoundMessage(id, "setting");
		}
	}
}