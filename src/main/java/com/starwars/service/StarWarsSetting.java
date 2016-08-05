package com.starwars.service;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.starwars.dao.BaseDao;
import com.starwars.exception.DefaultErrorMessage;
import com.starwars.model.Setting;
import static com.starwars.util.SWConstants.*;


@Service
public class StarWarsSetting implements StarwarsServiceBase<Setting, Setting>{
	
	@Autowired
	BaseDao<Setting> dao;
	
	private final Logger logger = LoggerFactory.getLogger(StarWarsSetting.class);

	public Setting getSetting(String line, Map<String, Setting> mapMovie, Setting setting) throws DefaultErrorMessage {
		try {
			String newLine = null;
			if (line.contains(HIFEN)) {
				newLine = line.substring(line.indexOf(BLANK_SPACE), line.indexOf(HIFEN))
						.trim();
			} else
				newLine = line.substring(line.indexOf(BLANK_SPACE)).trim();
	
			if (mapMovie.containsKey(newLine)) {
				setting = mapMovie.get(newLine);
			} else {
				setting = new Setting();
				setting.setName(newLine);
				mapMovie.put(newLine, setting);
			}
			return setting;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new DefaultErrorMessage();
		}
	}

	@Override
	public Setting findById(Long id) throws DefaultErrorMessage {
		try {
			return dao.findById(id);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new DefaultErrorMessage();
		}
	}

	@Override
	public List<Setting> getAll() throws DefaultErrorMessage {
		try {
			return dao.getAll();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new DefaultErrorMessage();
		}
	}
}