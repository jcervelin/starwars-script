package com.starwars.script.model.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

@Document
@Data
@Builder
public class CharacterEntity {
	@Id
	private String id;
	private String name;
	private Map<String, Integer> words;
}