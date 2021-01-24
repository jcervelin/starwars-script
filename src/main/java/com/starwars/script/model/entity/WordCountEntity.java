package com.starwars.script.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
public class WordCountEntity {
	
	public WordCountEntity() {
		super();
	}
	
	public WordCountEntity(String word) {
		super();
		this.word = word;
	}

	@Id
	@JsonIgnore
	private Long id;
	private String word;

	private Long count;
	
	@JsonIgnore
	private CharacterEntity character;
	 
}