package com.starwars.service;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.starwars.model.Setting;
@Service
public class StarWarsSetting implements StarwarsServiceBase<Setting, Setting>{

	public Setting getSetting(String line, Map<String, Setting> mapMovie, Setting setting) {
		String newLine = null;
		if (line.contains(" - ")) {
			newLine = line.substring(line.indexOf(" "), line.indexOf(" - "))
					.trim();
		} else
			newLine = line.substring(line.indexOf(" ")).trim();

		if (mapMovie.containsKey(newLine)) {
			setting = mapMovie.get(newLine);
		} else {
			setting = new Setting();
			System.out.println(line);
			setting.setName(newLine);
			mapMovie.put(newLine, setting);
		}
		return setting;
		
	}
}