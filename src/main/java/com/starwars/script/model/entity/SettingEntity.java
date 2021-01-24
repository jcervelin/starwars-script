package com.starwars.script.model.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Collection;

@Document
@Data
@Builder
public class SettingEntity {
	@Id
	private String id;
	private String name;
	private String content;
    private Collection<CharacterEntity> characters;
	
}
